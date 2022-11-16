
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.weapons.bullets.SimpleBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class ShotGun extends Weapon
{
    private static final String DESC = "Fires 5 clustered bullets in a random direction";

    public ShotGun(final Main main)
    {
        super(main, DESC, "Shotgun", 8.0, 4.0, 5.0, 7000, 2, false);
        this.damage_max = 2;
        this.recharge_max = 2;
        this.duration_max = 2;
    }

    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        final int rand = Utils.RANDOM.nextInt(4);
        for (int i = 0; i < 5; ++i)
        {
            GameState.a.add(new SimpleBullet(this.main, p.getX(), p.getY(), 15, 15, (float) (rand * 90 + 18 * (i - 2)), 10.0f, 100.0f, this.damage, this.duration));
        }
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return level * (level * 1.5 + 2.5) + 4.0;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return -1.5 * level + 8.0;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return level + 5;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 225.0);
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return (int) ((16.0 - this.upgradeRechargeAmount(level)) * 250.0);
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return (int) (this.upgradeDurationAmount(level) * 430.0);
    }
}
