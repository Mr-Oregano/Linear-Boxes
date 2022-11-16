
package com.xamser7.lb.weapons;

import com.xamser7.lb.Game;
import com.xamser7.lb.Main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class Weapon
{
    public static final int DEV_WEAPON_COUNT = 5;

    public static ArrayList<Weapon> list = new ArrayList<Weapon>();

    protected static Weapon selected;

    protected double damage;
    protected double recharge;
    protected double duration;

    protected short damage_level;
    protected short recharge_level;
    protected short duration_level;
    
    protected byte damage_max;
    protected byte recharge_max;
    protected byte duration_max;
    
    protected int cost;
    protected int unlock;
    protected boolean owned;
    protected boolean devWeapon;
    
    protected String name;
    protected String desc;
    
    protected Main main;

    public Weapon(final Main main, final String desc, final String name, final double recharge, final double damage, final double duration, final int cost, final int unlock, final boolean devWeapon)
    {
        this.name = name;
        this.desc = desc;
        this.recharge = recharge;
        this.damage = damage;
        this.duration = duration;
        this.cost = cost;
        this.unlock = unlock;
        this.devWeapon = devWeapon;
        this.main = main;
        
        if ((devWeapon && Game.Dev) || !devWeapon)
        {
            Weapon.list.add(this);
        }
    }

    public abstract void fire();

    public double getRecharge()
    {
        return this.recharge;
    }

    public double getDamage()
    {
        return this.damage;
    }

    public double getDuration()
    {
        return this.duration;
    }

    public void setRecharge(final double recharge)
    {
        this.recharge = recharge;
    }

    public void setDamage(final double damage)
    {
        this.damage = damage;
    }

    public void setDuration(final double duration)
    {
        this.duration = duration;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public boolean isOwned()
    {
        return this.owned;
    }

    public void setOwned(final boolean owned)
    {
        this.owned = owned;
    }

    public int getID()
    {
        return Weapon.list.indexOf(this);
    }

    public int getCost()
    {
        return this.cost;
    }

    public boolean isDamageMax()
    {
        return this.damage_level >= this.damage_max;
    }

    public boolean isRechargeMax()
    {
        return this.recharge_level >= this.recharge_max;
    }

    public boolean isDurationMax()
    {
        return this.duration_level >= this.duration_max;
    }

    public short getDamageLevel()
    {
        return this.damage_level;
    }

    public short getRechargeLevel()
    {
        return this.recharge_level;
    }

    public short getDurationLevel()
    {
        return this.duration_level;
    }

    public final void upgradeDamage(final short level)
    {
        if (level > this.damage_max)
        {
            return;
        }
        this.damage = this.upgradeDamageAmount(level);
        this.damage_level = level;
    }

    public final void upgradeRecharge(final short level)
    {
        if (level > this.recharge_max)
        {
            return;
        }
        this.recharge = this.upgradeRechargeAmount(level);
        this.recharge_level = level;
    }

    public final void upgradeDuration(final short level)
    {
        if (level > this.duration_max)
        {
            return;
        }
        this.duration = this.upgradeDurationAmount(level);
        this.duration_level = level;
    }

    public final void upgradeDamage()
    {
        if (this.damage_level > this.damage_max)
        {
            return;
        }
        final short damage_level = (short) (this.damage_level + 1);
        this.damage_level = damage_level;
        this.damage = this.upgradeDamageAmount(damage_level);
    }

    public final void upgradeRecharge()
    {
        if (this.recharge_level > this.recharge_max)
        {
            return;
        }
        final short recharge_level = (short) (this.recharge_level + 1);
        this.recharge_level = recharge_level;
        this.recharge = this.upgradeRechargeAmount(recharge_level);
    }

    public final void upgradeDuration()
    {
        if (this.duration_level > this.duration_max)
        {
            return;
        }
        final short duration_level = (short) (this.duration_level + 1);
        this.duration_level = duration_level;
        this.duration = this.upgradeDurationAmount(duration_level);
    }

    public abstract double upgradeDamageAmount(final int p0);

    public abstract double upgradeRechargeAmount(final int p0);

    public abstract double upgradeDurationAmount(final int p0);

    public abstract int upgradeDamageCost(final int p0);

    public abstract int upgradeRechargeCost(final int p0);

    public abstract int upgradeDurationCost(final int p0);

    public static Weapon getSelected()
    {
        return Weapon.selected;
    }

    public static void setSelected(final Weapon gun)
    {
        Weapon.selected = gun;
    }

    public static void loadGuns(final Main main)
    {
        new SimpleGun(main);
        new DoubleShot(main);
        new ShotGun(main);
        new PinPointer(main);
        new Sparky(main);
        new Tracker(main);
        new SonicBoomer(main);
        new ShieldGun(main);
        new Xamser7sGun(main);
        new ShatterShot(main);
        new DestrosGun(main);
        new Neptune27sGun(main);
        new Crossbow(main);
        new KoiCarpesGun(main);
        new BuhgzisGun(main);
    }

    public static void organize()
    {
        Collections.sort(list, new Comparator<Weapon>() 
        {
            @Override
            public int compare(Weapon o1, Weapon o2)
            {
                int dev1 = o1.devWeapon ? 1 : 0;
                int dev2 = o2.devWeapon ? 1 : 0;

                if (dev1 != dev2)
                    return dev1 - dev2;

                return o1.unlock - o2.unlock;
            }
        });
    }
}
