
package com.xamser7.lb.assets;

import com.xamser7.lb.Main;
import com.xamser7.lb.skins.Skin;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.Color;

public class Assets
{
    public static Color gemBlue;
    public static Color darkGem;
    public static Color algaeGreen;
    private static ArrayList<Font> font;
    public static BufferedImage[] skins;
    public static BufferedImage redEnemy;
    public static BufferedImage zipper;
    public static BufferedImage blontro;
    public static BufferedImage bitThief;
    public static BufferedImage quantom;
    public static BufferedImage bullet;
    public static BufferedImage bonus;
    public static BufferedImage superBonus;
    public static BufferedImage menu;
    public static BufferedImage shop_back;
    public static BufferedImage options_back;
    public static BufferedImage stats_back;
    public static BufferedImage lock_overlay;
    public static BufferedImage[] play;
    public static BufferedImage[] shop;
    public static BufferedImage[] options;
    public static BufferedImage[] stats;
    public static BufferedImage[] quit;
    public static BufferedImage[] resume;
    public static BufferedImage[] newgame;
    public static BufferedImage[] mainmenu;
    public static BufferedImage[] pause;
    public static BufferedImage[] back;
    public static BufferedImage[] equip;
    public static BufferedImage[] buy;
    public static BufferedImage[] forward;
    public static BufferedImage[] gunshop;
    public static BufferedImage[] playershop;
    public static BufferedImage[] checkboxDisabled;
    public static BufferedImage[] checkboxEnabled;
    public static BufferedImage[] dropdownMain;
    public static BufferedImage[] dropdownItem;
    private static int width;
    private static int height;

    static
    {
        Assets.font = new ArrayList<Font>();
        Assets.width = 300;
        Assets.height = 300;
    }

    public static void init()
    {
        Assets.gemBlue = new Color(92, 179, 255);
        Assets.darkGem = new Color(0, 92, 176);
        Assets.algaeGreen = new Color(100, 233, 134);
        final SpriteSheet entity = new SpriteSheet(ImageLoader.loadImage("entity.png"));
        final SpriteSheet skins = new SpriteSheet(ImageLoader.loadImage("skins.png"));
        final SpriteSheet gui = new SpriteSheet(ImageLoader.loadImage("gui.png"));
        final SpriteSheet background = new SpriteSheet(ImageLoader.loadImage("background.png"));
        Assets.skins = new BufferedImage[Skin.list.size()];
        for (int i = 0; i < Assets.skins.length; ++i)
        {
            Assets.skins[i] = skins.crop(Assets.width * (i % 5), Assets.height * (i / 5), Assets.width, Assets.height);
        }
        Assets.quantom = entity.crop(0, 0, Assets.width, Assets.height);
        Assets.redEnemy = entity.crop(Assets.width, 0, Assets.width, Assets.height);
        Assets.zipper = entity.crop(0, Assets.height * 2, Assets.width, Assets.height);
        Assets.blontro = entity.crop(Assets.width, Assets.height * 2, Assets.width, Assets.height);
        Assets.bitThief = entity.crop(Assets.width * 2, Assets.height * 2, Assets.width, Assets.height);
        Assets.bullet = entity.crop(Assets.width * 2, 0, Assets.width, Assets.height);
        Assets.bonus = entity.crop(0, Assets.height, Assets.width, Assets.height);
        Assets.superBonus = entity.crop(Assets.width, Assets.height, Assets.width, Assets.height);
        Assets.play = new BufferedImage[2];
        for (int i = 0; i < Assets.play.length; ++i)
        {
            Assets.play[i] = gui.crop(i * Assets.width, 0, Assets.width, (int) (Assets.height * 0.35));
        }
        Assets.shop = new BufferedImage[2];
        for (int i = 0; i < Assets.shop.length; ++i)
        {
            Assets.shop[i] = gui.crop(i * 292, 105, 280, (int) (Assets.height * 0.35));
        }
        Assets.quit = new BufferedImage[2];
        for (int i = 0; i < Assets.quit.length; ++i)
        {
            Assets.quit[i] = gui.crop(i * Assets.width, 210, Assets.width, (int) (Assets.height * 0.35));
        }
        Assets.options = new BufferedImage[2];
        for (int i = 0; i < Assets.options.length; ++i)
        {
            Assets.options[i] = gui.crop(i * 450, 1140, 450, (int) (Assets.height * 0.35));
        }
        Assets.stats = new BufferedImage[2];
        for (int i = 0; i < Assets.stats.length; ++i)
        {
            Assets.stats[i] = gui.crop(i * 300, 1245, 300, (int) (Assets.height * 0.35));
        }
        Assets.resume = new BufferedImage[2];
        for (int i = 0; i < Assets.resume.length; ++i)
        {
            Assets.resume[i] = gui.crop(i * 405, 315, 405, (int) (Assets.height * 0.35));
        }
        Assets.newgame = new BufferedImage[2];
        for (int i = 0; i < Assets.newgame.length; ++i)
        {
            Assets.newgame[i] = gui.crop(i * 570, 420, 570, (int) (Assets.height * 0.35));
        }
        Assets.mainmenu = new BufferedImage[2];
        for (int i = 0; i < Assets.mainmenu.length; ++i)
        {
            Assets.mainmenu[i] = gui.crop(i * 315, 525, 315, (int) (Assets.height * 0.35));
        }
        Assets.pause = new BufferedImage[2];
        for (int i = 0; i < Assets.pause.length; ++i)
        {
            Assets.pause[i] = gui.crop(i * 270 + 600, 0, 270, Assets.height);
        }
        Assets.back = new BufferedImage[2];
        for (int i = 0; i < Assets.back.length; ++i)
        {
            Assets.back[i] = gui.crop(i * 270 + 1140, 0, 270, Assets.height);
        }
        Assets.equip = new BufferedImage[2];
        for (int i = 0; i < Assets.equip.length; ++i)
        {
            Assets.equip[i] = gui.crop(i * 330, 630, 330, (int) (Assets.height * 0.35));
        }
        Assets.buy = new BufferedImage[2];
        for (int i = 0; i < Assets.buy.length; ++i)
        {
            Assets.buy[i] = gui.crop(i * 225, 735, 225, (int) (Assets.height * 0.35));
        }
        Assets.forward = new BufferedImage[2];
        for (int i = 0; i < Assets.forward.length; ++i)
        {
            Assets.forward[i] = gui.crop(i * 270 + 1140, Assets.height, 270, Assets.height);
        }
        Assets.gunshop = new BufferedImage[2];
        for (int i = 0; i < Assets.gunshop.length; ++i)
        {
            Assets.gunshop[i] = gui.crop(i * Assets.width, 840, Assets.width, Assets.height);
        }
        Assets.playershop = new BufferedImage[2];
        for (int i = 0; i < Assets.playershop.length; ++i)
        {
            Assets.playershop[i] = gui.crop(i * Assets.width + Assets.width * 2, 840, Assets.width, Assets.height);
        }
        Assets.lock_overlay = gui.crop(Assets.width * 4, 840, Assets.width, Assets.height);
        Assets.checkboxDisabled = new BufferedImage[2];
        for (int i = 0; i < Assets.checkboxDisabled.length; ++i)
        {
            Assets.checkboxDisabled[i] = gui.crop(i * Assets.width + 1680, 0, Assets.width, Assets.height);
        }
        Assets.checkboxEnabled = new BufferedImage[2];
        for (int i = 0; i < Assets.checkboxEnabled.length; ++i)
        {
            Assets.checkboxEnabled[i] = gui.crop(i * Assets.width + 1680, Assets.height, Assets.width, Assets.height);
        }
        Assets.dropdownMain = new BufferedImage[2];
        for (int i = 0; i < Assets.dropdownMain.length; ++i)
        {
            Assets.dropdownMain[i] = gui.crop(i * 750 + 660, 630, 750, (int) (Assets.height * 0.35));
        }
        Assets.dropdownItem = new BufferedImage[2];
        for (int i = 0; i < Assets.dropdownItem.length; ++i)
        {
            Assets.dropdownItem[i] = gui.crop(i * 750 + 2160, 630, 750, (int) (Assets.height * 0.35));
        }
        Assets.menu = background.crop(0, 0, 1920, 1080);
        Assets.shop_back = background.crop(0, 1080, 1920, 1080);
        Assets.options_back = background.crop(0, 2160, 1920, 1080);
        Assets.stats_back = background.crop(0, 3240, 1920, 1080);
    }

    public static Font getFont(int size)
    {
        size *= Main.current.getWScale();
        for (int i = 0; i < Assets.font.size(); ++i)
        {
            if (Assets.font.get(i).getSize() == size)
            {
                return Assets.font.get(i);
            }
        }
        final Font newFont = FontLoader.loadFont("Berlin.ttf", (float) size, 0);
        Assets.font.add(newFont);
        return newFont;
    }

    public static Font getFontBold(int size)
    {
        size *= Main.current.getWScale();
        for (int i = 0; i < Assets.font.size(); ++i)
        {
            if (Assets.font.get(i).getSize() == size)
            {
                return Assets.font.get(i);
            }
        }
        final Font newFont = FontLoader.loadFont("Berlin.ttf", (float) size, 1);
        Assets.font.add(newFont);
        return newFont;
    }
}
