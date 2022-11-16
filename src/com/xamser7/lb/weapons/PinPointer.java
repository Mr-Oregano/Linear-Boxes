
package com.xamser7.lb.weapons;

import com.xamser7.lb.entities.enemies.Enemy;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.weapons.bullets.SimpleBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class PinPointer extends Weapon
{
    private static final String DESC = "Like The Simple Gun but targets and aims\ntowards the closest enemy.";

    public PinPointer(final Main main)
    {
        super(main, DESC, "Pin-Pointer Gun", 6.0, 10.0, 4.0, 12000, 2, false);
        this.damage_max = 2;
        this.recharge_max = 2;
        this.duration_max = 2;
    }

    @Override
    public void fire()
    {
        Enemy target = null;
        final Player p = GameState.p.get(0);
        double min_distance = Double.MAX_VALUE;
        for (final Enemy e : GameState.e)
        {
            final double distance = Math.hypot(e.getX() - p.getX(), e.getY() - p.getY());
            if (distance < min_distance)
            {
                min_distance = distance;
                target = e;
            }
        }
        if (target == null)
        {
            GameState.a.add(new SimpleBullet(this.main, p.getX(), p.getY(), 15, 15, (float) (int) (Math.random() * 360.0), 10.0f, 100.0f, this.damage, this.duration));
            return;
        }
        final double cx = target.getX() - p.getX();
        final double cy = target.getY() - p.getY();
        float angle = (float) Math.toDegrees(Math.atan(cy / cx));
        angle += ((cx < 0.0) ? 180 : 0);
        GameState.a.add(new SimpleBullet(this.main, p.getX(), p.getY(), 15, 15, angle, 10.0f, 100.0f, this.damage, this.duration));
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return level * (level * 2.5 + 2.5) + 10.0;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return level * (level * -0.5 - 0.5) + 6.0;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return 2 * level + 4;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 200.0);
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return (int) ((10.0 - this.upgradeRechargeAmount(level)) * 500.0);
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return (int) (this.upgradeDurationAmount(level) * 500.0);
    }
}
