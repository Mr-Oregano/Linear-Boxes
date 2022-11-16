
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.weapons.bullets.SimpleBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class Xamser7sGun extends Weapon
{
    private static String DESC = "You should not have this.";

    public Xamser7sGun(final Main main)
    {
        super(main, DESC, "Xamser7's Gun", 0.0, Double.POSITIVE_INFINITY, 5.0, 0, 1, true);
    }

    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        GameState.a.add(new SimpleBullet(this.main, p.getX(), p.getY(), 15, 15, (float) Utils.RANDOM.nextInt(360), 15.0f, 100.0f, this.damage, this.duration));
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return 0.0;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return 5.0;
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
