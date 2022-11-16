
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.weapons.bullets.FragBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class ShatterShot extends Weapon
{
    private static String DESC = "Fires a bullet that bursts into fragments\nupon hitting an enemy.";

    public ShatterShot(final Main main)
    {
        super(main, DESC, "Shatter Shot", 15.0, 14.0, 6.0, 550000, 15, false);
        this.damage_max = 2;
        this.recharge_max = 2;
        this.duration_max = 2;
    }

    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        final FragBullet bullet = new FragBullet(this.main, p.getX(), p.getY(), 25, 25, (float) Math.random() * 360.0f, 8.0f, 100.0f, this.damage, this.duration);
        bullet.setFragAmount(360);
        bullet.setFragWeight(0.4f);
        bullet.setFragSpeed(10.0f);
        GameState.a.add(bullet);
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return level * (level + 1) + 14;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return level * (-level - 1) + 15;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return 3 * level + 6;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 11000.0);
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return (int) ((20.0 - this.upgradeRechargeAmount(level)) * 11000.0);
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return (int) (this.upgradeDurationAmount(level) * 17000.0);
    }
}
