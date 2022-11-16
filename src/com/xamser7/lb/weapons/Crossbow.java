
package com.xamser7.lb.weapons;

import com.xamser7.lb.entities.enemies.Enemy;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.weapons.bullets.PierceBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class Crossbow extends Weapon
{
    private static final String DESC = "Fires a high velocity dart at the closest enemy. \nAble to pierce multiple enemies.";

    private static final int BULLET_COUNT = 5;

    private static final float BULLET_SEP = 10;

    public Crossbow(final Main main)
    {
        super(main, DESC, "Crossbow", 4.0, 20.0, Double.POSITIVE_INFINITY, 275000, 12, false);
        this.damage_max = 2;
        this.recharge_max = 2;
    }

    @Override
    public void fire()
    {
        Enemy target = null;
        final Player p = GameState.p.get(0);
        double min_distance = Double.MAX_VALUE;
        for (final Enemy e : GameState.e)
        {
            if (e.getAlpha() < 100.0f)
                continue;

                final double distance = Math.hypot(e.getX() - p.getX(), e.getY() - p.getY());
            if (distance >= min_distance)
                continue;

            min_distance = distance;
            target = e;
        }

        float angle = (float) Math.random() * 360.0f;

        if (target != null)
        {
            double cx = target.getX() - p.getX();
            double cy = target.getY() - p.getY();
            angle = (float) Math.atan2(cy, cx);
        }

        for (int i = 0; i < BULLET_COUNT * BULLET_SEP; i += BULLET_SEP)
        {
            float xOffset = (float) Math.cos(angle) * i;
            float yOffset = (float) Math.sin(angle) * i;
            GameState.a.add(new PierceBullet(this.main, p.getX() + xOffset, p.getY() + yOffset, 15, 15, (float) Math.toDegrees(angle), 50.0f, 100.0f, this.damage, this.duration));
        }
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return level * (level * 5 + 5) + 20;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return 4 - level;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 5000.0);
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return (int) ((10.0 - this.upgradeRechargeAmount(level)) * 25000.0);
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return 0;
    }
}
