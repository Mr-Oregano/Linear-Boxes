
package com.xamser7.lb.skins;

import com.xamser7.lb.Game;
import java.util.ArrayList;
import com.xamser7.lb.Main;
import java.util.List;

public abstract class Skin
{
    public static List<Skin> list;
    public static Skin selected;
    protected Main main;
    protected int ID;
    protected int unlock;
    protected boolean devSkin;

    static
    {
        Skin.list = new ArrayList<Skin>();
    }

    public Skin(final Main main, final int unlock, final boolean devSkin)
    {
        this.main = main;
        this.unlock = unlock;
        this.devSkin = devSkin;
        if ((devSkin && Game.Dev) || !devSkin)
        {
            Skin.list.add(this);
        }
        this.ID = Skin.list.size() - 1;
    }

    public int getID()
    {
        return this.ID;
    }

    public int getUnlock()
    {
        return this.unlock;
    }

    public void setUnlock(final int unlock)
    {
        this.unlock = unlock;
    }

    public boolean isOwned()
    {
        return Game.Level > this.unlock - 2;
    }

    public static void loadSkins(final Main main)
    {
        new DeepBlue(main);
        new Flame(main);
        new Dark(main);
        new Neon(main);
        new Retro(main);
        new Star(main);
        new Dynamic(main);
        new Snowflake(main);
        new SunFlower(main);
        new Desert(main);
        new Frozen(main);
        new Vortex(main);
        new Signal(main);
        new StoneDiamond(main);
        new WinterStream(main);
        new HellFire(main);
        new Void(main);
    }
}
