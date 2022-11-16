
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.weapons.bullets.Neptune27sBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class Neptune27sGun extends Weapon
{
    private static final String DESC = "You should not have this.";

    private static final Neptune27sBullet[] bullets = new Neptune27sBullet[2];

    public Neptune27sGun(final Main main)
    {
        super(main, DESC, "Neptune27's Gun", Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 1, true);
    }

    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        for (int i = 0; i < bullets.length; ++i)
            bullets[i] = new Neptune27sBullet(this.main, p.getX(), p.getY(), 30, 30, (float) (i * 180), 45.0f, 10f, 100.0f, this.damage, this.duration);

        bullets[0].setNeighbor(bullets[1]);
        bullets[1].setNeighbor(bullets[0]);
        
        GameState.a.add(bullets[0]);
        GameState.a.add(bullets[1]);
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
