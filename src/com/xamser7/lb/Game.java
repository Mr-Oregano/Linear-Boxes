
package com.xamser7.lb;

import java.awt.Color;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.gfx.Painter;
import java.awt.Graphics2D;
import java.time.LocalDate;
import java.io.IOException;
import com.xamser7.lb.utils.ByteWriter;
import com.xamser7.lb.utils.InvalidSaveFileException;
import com.xamser7.lb.utils.UnknownSaveFileVersionException;

import java.io.FileNotFoundException;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import com.xamser7.lb.skins.Skin;
import com.xamser7.lb.weapons.Weapon;
import com.xamser7.lb.utils.ByteReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import com.xamser7.lb.utils.Utils;
import java.io.File;

public class Game
{
    private static final short LB_VER_MAJOR = 2;
    private static final short LB_VER_MINOR = 4;
    
    private static final short DTLB_VERSION = 0x3D;
    private static final File SAVE_DIRECTORY = new File("saves");

    // Flags
    private static final byte DEV_BIT = 0x0;
    private static final byte DEV_MASK = 1 << DEV_BIT;

    private static final byte ANTIALIASING_BIT = 0x1;
    private static final byte ANTIALIASING_MASK = 1 << ANTIALIASING_BIT;

    private static final byte INTERPOLATION_BIT = 0x2;
    private static final byte INTERPOLATION_MASK = 1 << INTERPOLATION_BIT;
    
    private static final byte FULLSCREEN_BIT = 0x3;
    private static final byte FULLSCREEN_MASK = 1 << FULLSCREEN_BIT;

    // Inventory
    public static int Money;
    public static int Range;
    public static int Level;
    public static int Exp;
    public static int MaxExp;
    public static int RateLevel;
    public static int PlayerSpeed;
    public static double MoneyRate;
    public static double ExpRate;

    // Stats
    public static int HighestScoreReached;
    public static float LongestTimeSurvived;
    public static float AverageScore;
    public static float AverageTimeSurvived;
    public static int TotalBits;
    public static int TotalEnemiesKilled;
    public static int TotalGames;
    public static long CumulativeMoneyEarned;
    public static long TotalScore;
    public static float TotalTimeSurvived;

    // Settings
    public static boolean Dev = true;
    public static boolean AllowAntiAliasing;
    public static boolean AllowInterpolation;
    public static boolean FullScreen;
    public static int WindowDimensions;

    private static Main main;
    private static File saveFile;

    public static void loadFiles(final Main main)
    {
        Game.main = main;

        if (!SAVE_DIRECTORY.exists())
            SAVE_DIRECTORY.mkdir();

        for (File file : SAVE_DIRECTORY.listFiles())
        {
            if (!file.isFile())
                continue;

            System.out.printf("Attempting to use save file: '%s'...\n", file.getName());

            try
            {
                readGameData(file);
                saveFile = file;
                break;
            }
            catch (InvalidSaveFileException e)
            {
                // Attempt to read legacy file
                if (legacy_ReadGameData(file))
                {
                    saveFile = file;
                    break;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        // Create new save file if no file was found.
        if (saveFile != null)
            return;

        System.out.println("Creating new save file.");
        String saveName = String.format("v%d.%d_%s_%s.dtlb", LB_VER_MAJOR, LB_VER_MINOR, LocalDate.now(), Long.toHexString(System.nanoTime()));

        try
        {
            saveFile = Utils.newFile(new File(SAVE_DIRECTORY.getPath() + "/" + saveName));

            final FileOutputStream stream = new FileOutputStream(saveFile);
            final DataOutputStream data = new DataOutputStream(stream);

            writeDefaultData(data);
            readGameData(saveFile);
        }
        catch (IOException e)
        {
            System.err.println("FATAL: Could not create save file.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void readGameData(final File file) throws FileNotFoundException
    {
        final FileInputStream stream = new FileInputStream(file);
        final DataInputStream data = new DataInputStream(stream);

        if (!ByteReader.read_string(data, 4).equals("dtlb"))
            throw new InvalidSaveFileException(String.valueOf(saveFile.getAbsolutePath()) + " could not be used. Path specified is either corrupted or unreadable.");

        final int version = ByteReader.read_uint16(data);
        
        switch (version)
        {
        case DTLB_VERSION:
            readGameData_vCurrent(data);
            break;

        case 0x3C:
            readGameData_v0x3C(data);
            break;

        default:
            throw new UnknownSaveFileVersionException(String.valueOf(saveFile.getAbsolutePath()) + " uses an unsupported version of the EBGA file format. Reading aborted!");
        }

        Utils.QuietClose(data);
    }

    private static void readGameData_vCurrent(final DataInputStream data)
    {
        final int flags = ByteReader.read_int16(data);

        Dev = ((flags & DEV_MASK) > 0);
        AllowAntiAliasing = ((flags & ANTIALIASING_MASK) > 0);
        AllowInterpolation = ((flags & INTERPOLATION_MASK) > 0);
        FullScreen = ((flags & FULLSCREEN_MASK) > 0);
        WindowDimensions = (flags & 0x30) >>> 4;

        Money = ByteReader.read_int32(data);
        ExpRate = ByteReader.read_double(data);
        MoneyRate = ByteReader.read_double(data);
        Range = ByteReader.read_int32(data);
        Level = ByteReader.read_int32(data);
        Exp = ByteReader.read_int32(data);
        RateLevel = ByteReader.read_int32(data);
        PlayerSpeed = ByteReader.read_int32(data);
        MaxExp = calcMaxExp(Level);

        // Weapons
        if (Weapon.list.isEmpty())
            Weapon.loadGuns(main);

        Weapon.organize();

        final int weapon_flags = ByteReader.read_int32(data);
        for (int i = 0; i < Weapon.list.size(); ++i)
        {
            Weapon.list.get(i).setOwned((weapon_flags & 1 << i) > 0);
            Weapon.list.get(i).upgradeRecharge(ByteReader.read_int16(data));
            Weapon.list.get(i).upgradeDamage(ByteReader.read_int16(data));
            Weapon.list.get(i).upgradeDuration(ByteReader.read_int16(data));
        }

        if (Skin.list.isEmpty())
            Skin.loadSkins(main);

        // Skins
        final short selections = ByteReader.read_int16(data);
        Weapon.setSelected(Weapon.list.get(selections & 0xFF));
        Skin.selected = Skin.list.get((selections & 0xFF00) >> 8);

        // Stats
        HighestScoreReached = ByteReader.read_int32(data);
        LongestTimeSurvived = ByteReader.read_float(data);
        TotalScore = ByteReader.read_int64(data);
        TotalTimeSurvived = ByteReader.read_float(data);
        TotalBits = ByteReader.read_int32(data);
        TotalEnemiesKilled = ByteReader.read_int32(data);
        TotalGames = ByteReader.read_int32(data);
        CumulativeMoneyEarned = ByteReader.read_int64(data);
        AverageScore = TotalScore / (float) ((TotalGames == 0) ? 1 : TotalGames);
        AverageTimeSurvived = TotalTimeSurvived / ((TotalGames == 0) ? 1 : TotalGames);
    }

    private static void readGameData_v0x3C(final DataInputStream data)
    {
        final int flags = ByteReader.read_int16(data);
        
        Dev = ((flags & DEV_MASK) > 0);
        AllowAntiAliasing = ((flags & ANTIALIASING_MASK) > 0);
        AllowInterpolation = ((flags & INTERPOLATION_MASK) > 0);
        FullScreen = ((flags & FULLSCREEN_MASK) > 0);
        WindowDimensions = (flags & 0x30) >>> 4;

        Money = ByteReader.read_int32(data);
        ExpRate = ByteReader.read_double(data);
        MoneyRate = ByteReader.read_double(data);
        Range = ByteReader.read_int32(data);
        Level = ByteReader.read_int32(data);
        Exp = ByteReader.read_int32(data);
        RateLevel = ByteReader.read_int32(data);
        PlayerSpeed = ByteReader.read_int32(data);
        MaxExp = calcMaxExp(Level);
        
        // Weapons
        if (Weapon.list.isEmpty())
            Weapon.loadGuns(main);

        Weapon.organize();

        final int weapon_flags = ByteReader.read_int32(data);

        // New update added two more developer weapons.
        // TODO: In the future way want to consider assigning weapon IDs to prevent this.
        //
        int weaponCount = Weapon.list.size();
            weaponCount -= Dev ? 2 : 0;

        for (int i = 0; i < weaponCount; ++i)
        {
            boolean owned = (weapon_flags & (1 << i)) > 0;
            int id = i;

            if (i < 4 && Dev)
                id = Weapon.list.size() - i - 1;

            Weapon.list.get(id).setOwned(owned);
            Weapon.list.get(id).upgradeRecharge(ByteReader.read_int16(data));
            Weapon.list.get(id).upgradeDamage(ByteReader.read_int16(data));
            Weapon.list.get(id).upgradeDuration(ByteReader.read_int16(data));
        }

        if (Skin.list.isEmpty())
            Skin.loadSkins(main);

        // Selections
        final short selections = ByteReader.read_int16(data);
        Weapon.setSelected(Weapon.list.get(selections & 0xFF));
        Skin.selected = Skin.list.get((selections & 0xFF00) >> 8);

        // Stats
        HighestScoreReached = ByteReader.read_int32(data);
        LongestTimeSurvived = ByteReader.read_float(data);
        TotalScore = ByteReader.read_int64(data);
        TotalTimeSurvived = ByteReader.read_float(data);
        TotalBits = ByteReader.read_int32(data);
        TotalEnemiesKilled = ByteReader.read_int32(data);
        TotalGames = ByteReader.read_int32(data);
        CumulativeMoneyEarned = ByteReader.read_int64(data);
        AverageScore = TotalScore / (float) ((TotalGames == 0) ? 1 : TotalGames);
        AverageTimeSurvived = TotalTimeSurvived / ((TotalGames == 0) ? 1 : TotalGames);
    }

    private static boolean legacy_ReadGameData(final File file)
    {
        try
        {
            final String fileStr = Utils.loadFileAsString(file.getPath());

            if (fileStr.contains("--version:0.2.3"))
                legacy_ReadGameData_v023(fileStr);
            else
                legacy_ReadGameData_v022(fileStr);

            saveFile = file;
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static void legacy_ReadGameData_v023(String data)
    {
        data = data.substring(data.indexOf("--version:0.2.3") + 16);
        final String[] tokens = data.split("\\s+");
        
        Dev = Utils.parseBoolean(tokens[0]);
        Money = Utils.parseInt(tokens[1]);
        ExpRate = Utils.parseDouble(tokens[2]);
        MoneyRate = Utils.parseDouble(tokens[3]);
        Range = Utils.parseInt(tokens[4]);
        Level = Utils.parseInt(tokens[6]);
        Exp = Utils.parseInt(tokens[7]);
        RateLevel = Utils.parseInt(tokens[8]);
        PlayerSpeed = 5;
        MaxExp = calcMaxExp(Level);
        
        AllowAntiAliasing = true;
        AllowInterpolation = true;
        FullScreen = true;
        WindowDimensions = 2;
        
        if (Weapon.list.isEmpty())
            Weapon.loadGuns(main);

        if (Skin.list.isEmpty())
            Skin.loadSkins(main);

        Weapon.setSelected(Weapon.list.get(Utils.parseInt(tokens[5])));
        for (int i = 0; i < (Dev ? 9 : 8); ++i)
            Weapon.list.get(i).setOwned(Utils.parseBoolean(tokens[i + 9]));

        if (Dev)
        {
            for (int i = Weapon.list.size() - Weapon.DEV_WEAPON_COUNT; i < Weapon.list.size(); ++i)
                Weapon.list.get(i).setOwned(true);
        }

        Weapon.organize();
        Skin.selected = Skin.list.get(0);
    }

    private static void legacy_ReadGameData_v022(final String data)
    {
        final String[] tokens = data.split("\\s+");

        Dev = Utils.parseBoolean(tokens[0]);
        Money = Utils.parseInt(tokens[1]);
        ExpRate = Utils.parseDouble(tokens[2]);
        MoneyRate = Utils.parseDouble(tokens[3]);
        Range = Utils.parseInt(tokens[4]);
        Level = Utils.parseInt(tokens[6]) - 1;
        Exp = Utils.parseInt(tokens[7]);
        RateLevel = Utils.parseInt(tokens[8]);
        PlayerSpeed = 5;
        
        AllowAntiAliasing = true;
        AllowInterpolation = true;
        FullScreen = true;
        WindowDimensions = 2;
        
        MaxExp = calcMaxExp(Level);

        if (Weapon.list.isEmpty())
            Weapon.loadGuns(main);

        if (Skin.list.isEmpty())
            Skin.loadSkins(main);

        Weapon.setSelected(Weapon.list.get(Utils.parseInt(tokens[5])));

        for (int i = 0; i < (Dev ? 9 : 8); ++i)
            Weapon.list.get(i).setOwned(Utils.parseBoolean(tokens[i + 9]));

        if (Dev)
        {
            for (int i = Weapon.list.size() - Weapon.DEV_WEAPON_COUNT; i < Weapon.list.size(); ++i)
                Weapon.list.get(i).setOwned(true);
        }

        Weapon.organize();
        Skin.selected = Skin.list.get(0);
    }

    private static void writeDefaultData(final DataOutputStream data)
    {
        ByteWriter.write_string(data, "dtlb");
        ByteWriter.write_int16(data, (short) DTLB_VERSION);
        ByteWriter.write_int16(data, (short) (0x2E | (Dev ? 1 : 0))); // Flags

        ByteWriter.write_int32(data, Dev ? 999999999 : 0); // Money
        ByteWriter.write_double(data, 0.055); // Exp Rate
        ByteWriter.write_double(data, 0.005); // Money Rate
        ByteWriter.write_int32(data, 65); // Range
        ByteWriter.write_int32(data, Dev ? 99 : 0); // Level
        ByteWriter.write_int32(data, 0); // Exp
        ByteWriter.write_int32(data, 1); // Rate Level
        ByteWriter.write_int32(data, 5); // Player Speed

        // Weapons
        Weapon.loadGuns(main);

        int devWeaponBit = Weapon.list.size() - 5;
        int devWeaponFlags = (1 << Weapon.DEV_WEAPON_COUNT) - 1;
        int devWeaponMask = devWeaponFlags << devWeaponBit;

        ByteWriter.write_int32(data, Dev ? 1 | devWeaponMask : 1);  // Weapon flags
        
        for (int i = 0; i < Weapon.list.size(); ++i)
        {
            ByteWriter.write_int16(data, (short) 0); // Recharge level
            ByteWriter.write_int16(data, (short) 0); // Damage level
            ByteWriter.write_int16(data, (short) 0); // Duration level
        }

        // Selections
        ByteWriter.write_int16(data, (short) 0);

        // Stats
        ByteWriter.write_int32(data, 0);
        ByteWriter.write_float(data, 0.0f);
        ByteWriter.write_int64(data, 0L);
        ByteWriter.write_float(data, 0.0f);
        ByteWriter.write_int32(data, 0);
        ByteWriter.write_int32(data, 0);
        ByteWriter.write_int32(data, 0);
        ByteWriter.write_int64(data, 0L);
        
        Utils.QuietClose(data);
    }

    public static void saveFile() throws FileNotFoundException
    {
        final FileOutputStream stream = new FileOutputStream(saveFile);
        final DataOutputStream data = new DataOutputStream(stream);
        
        ByteWriter.write_string(data, "dtlb");
        ByteWriter.write_int16(data, (short) DTLB_VERSION);
        
        int flags = 0;
        flags |= (Dev ? 1 : 0) << DEV_BIT;
        flags |= (AllowAntiAliasing ? 1 : 0) << ANTIALIASING_BIT;
        flags |= (AllowInterpolation ? 1 : 0) << INTERPOLATION_BIT;
        flags |= (FullScreen ? 1 : 0) << FULLSCREEN_BIT;
        flags |= WindowDimensions << 4;

        ByteWriter.write_int16(data, (short) flags);

        ByteWriter.write_int32(data, Money);
        ByteWriter.write_double(data, ExpRate);
        ByteWriter.write_double(data, MoneyRate);
        ByteWriter.write_int32(data, Range);
        ByteWriter.write_int32(data, Level);
        ByteWriter.write_int32(data, Exp);
        ByteWriter.write_int32(data, RateLevel);
        ByteWriter.write_int32(data, PlayerSpeed);

        // Weapons
        int weapon_flags = 0;
        for (int i = 0; i < Weapon.list.size(); ++i)
            if (Weapon.list.get(i).isOwned())
                weapon_flags |= 1 << i;

        ByteWriter.write_int32(data, weapon_flags);
        
        for (int i = 0; i < Weapon.list.size(); ++i)
        {
            ByteWriter.write_int16(data, Weapon.list.get(i).getRechargeLevel());
            ByteWriter.write_int16(data, Weapon.list.get(i).getDamageLevel());
            ByteWriter.write_int16(data, Weapon.list.get(i).getDurationLevel());
        }

        // Selections
        short selections = 0;
        selections |= (short) Weapon.getSelected().getID();
        selections |= (short) (Skin.selected.getID() << 8);

        // Stats
        ByteWriter.write_int16(data, selections);
        ByteWriter.write_int32(data, HighestScoreReached);
        ByteWriter.write_float(data, LongestTimeSurvived);
        ByteWriter.write_int64(data, TotalScore);
        ByteWriter.write_float(data, TotalTimeSurvived);
        ByteWriter.write_int32(data, TotalBits);
        ByteWriter.write_int32(data, TotalEnemiesKilled);
        ByteWriter.write_int32(data, TotalGames);
        ByteWriter.write_int64(data, CumulativeMoneyEarned);

        Utils.QuietClose(data);
    }

    public static void showMoney(final Main main, final Graphics2D g)
    {
        Painter.drawString(g, Painter.Format(Money, "$#,###"), 963, 118, Assets.gemBlue, Assets.getFont(40), 100.0f, false, true);
    }

    public static void showLevel(final Main main, final Graphics2D g)
    {
        Painter.fillRect(g, 897, 0, (int) (Exp / (float) MaxExp * 471.0f), 70, Assets.gemBlue, 100.0f, false, true);
        Painter.drawString(g, Integer.toString(Level + 1), 868, 33, Color.WHITE, Assets.getFont(40), 100.0f, true, true);
    }

    public static void nextLevel()
    {
        Exp = 0;
        ++Level;
        MaxExp = calcMaxExp(Level);
    }

    public static int calcMaxExp(final int x)
    {
        return (int) (Math.pow(x, 1.4) * 350.0) + 1000;
    }
}
