
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.weapons.bullets.SimpleBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class SonicBoomer extends Weapon
{
    public static final int AMOUNT = 180;
    public static final String DESC = "Unleash a wave of bullets that explodes\naround the player.";

    public SonicBoomer(final Main main)
    {
        super(main, DESC, "Sonic Boomer", 30.0, 35.0, 1.5, 1000000, 20, false);
        this.damage_max = 2;
        this.recharge_max = 2;
        this.duration_max = 2;
    }

    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        for (int i = 0; i < AMOUNT; ++i)
        {
            GameState.a.add(new SimpleBullet(this.main, p.getX(), p.getY(), 10, 10, (float) (i * 360 / AMOUNT), 10.0f, 100.0f, this.damage, this.duration));
        }
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return level * (level * 5 + 10) + 35;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return level * (-2.5 * level - 2.0) + 30.0;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return level * (level - 0.5) + 2.5;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 5000.0);
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return (int) ((40.0 - this.upgradeRechargeAmount(level)) * 10000.0);
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 50000.0);
    }
}
