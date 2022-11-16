
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.weapons.bullets.DestrosBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class DestrosGun extends Weapon
{
    private static String DESC = "You should not have this.";

    public DestrosGun(final Main main)
    {
        super(main, DESC, "Destro's Gun", 0.5, Double.POSITIVE_INFINITY, 10.0, 0, 1, true);
    }

    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        final DestrosBullet bullet = new DestrosBullet(this.main, p.getX(), p.getY(), 15, 15, (float) Math.random() * 360.0f, 8.0f, 100.0f, this.damage, this.duration);
        bullet.setFragAmount(10);
        bullet.setFragWeight(1.0f);
        bullet.setFragSpeed(6.0f);
        GameState.a.add(bullet);
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return 0;
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return 0;
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return 0;
    }
}
