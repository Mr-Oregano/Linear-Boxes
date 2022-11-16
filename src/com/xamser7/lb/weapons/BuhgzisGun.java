package com.xamser7.lb.weapons;

import com.xamser7.lb.Main;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.weapons.bullets.BuhgzisBullet;

public class BuhgzisGun extends Weapon
{
    private static final String DESC = "You should not have this.";

    public BuhgzisGun(Main main)
    {
        super(main, DESC, "Buhgzi's Gun", Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 1, true);
    }

    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        GameState.a.add(new BuhgzisBullet(this.main, p.getX(), p.getY(), 10, 10, (float) Utils.RANDOM.nextInt(360), 1f, 100.0f, this.damage, this.duration));
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
