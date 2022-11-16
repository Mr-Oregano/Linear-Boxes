
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.weapons.bullets.TrackerBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class Tracker extends Weapon
{
    private static final String DESC = "Fires a single homing missle that locks in on\nthe nearest enemy. But you don't care, \ndo you actually read these descriptions?";

    public Tracker(final Main main)
    {
        super(main, DESC, "Tracker", 9.0, 25.0, 6.0, 150000, 10, false);
        this.damage_max = 2;
        this.recharge_max = 2;
        this.duration_max = 2;
    }

    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        GameState.a.add(new TrackerBullet(this.main, p.getX(), p.getY(), 30, 30, (float) Utils.RANDOM.nextInt(360), 25.0f, 10f, 100.0f, this.damage, this.duration));
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return level * (level * 15.0 / 4.0 + 1.25) + 25.0;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return level * (-0.25 * level - 0.75) + 9.0;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return level * (0.5 * level + 0.5) + 6.0;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 2000.0);
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return (int) ((25.0 - this.upgradeRechargeAmount(level)) * 5000.0);
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return (int) (this.upgradeDurationAmount(level) * 12000.0);
    }
}
