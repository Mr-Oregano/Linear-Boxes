
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.weapons.bullets.SimpleBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class SimpleGun extends Weapon
{
    private static final String DESC = "Fires a single bullet in a random direction,\nnot very accurate.";

    public SimpleGun(final Main main)
    {
        super(main, DESC, "The Simple Gun", 6.0, 5.0, 4.0, 0, 1, false);
        this.damage_max = 2;
        this.recharge_max = 2;
        this.duration_max = 2;
    }
    
    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        GameState.a.add(new SimpleBullet(this.main, p.getX(), p.getY(), 15, 15, (float) Utils.RANDOM.nextInt(360), 10f, 100.0f, this.damage, this.duration));
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return level * (level * 2.5 + 2.5) + 5.0;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return 6.0 - level;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return 1.5 * level + 4.0;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 25.0);
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return (int) ((10.0 - this.upgradeRechargeAmount(level)) * 50.0);
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return (int) (this.upgradeDurationAmount(level) * 50.0);
    }
}
