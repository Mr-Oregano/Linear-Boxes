
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.weapons.bullets.ShieldBullet;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class ShieldGun extends Weapon
{
    public static final int AMOUNT = 20;
    private static final String DESC = "Uses an advanced form of electro-magnetic\nenergy to generate a shield of bullets that\ncircle the player! If that makes any sense...";

    public ShieldGun(final Main main)
    {
        super(main, DESC, "Shield", 60.0, 5.0, 30.0, 10000000, 30, false);
        this.damage_max = 2;
        this.recharge_max = 2;
        this.duration_max = 2;
    }

    @Override
    public void fire()
    {
        final Player p = GameState.p.get(0);
        for (int i = 0; i < ShieldGun.AMOUNT; ++i)
        {
            final ShieldBullet bullet = new ShieldBullet(this.main, p.getX(), p.getY(), 15, 15, (float) (i * 360 / ShieldGun.AMOUNT), 5.0f, 100.0f, this.damage, this.duration);
            bullet.setRadius(40 + p.getWidth());
            GameState.a.add(bullet);
        }
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return level * (level * 2.5 + 2.5) + 5.0;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return level * (level * -0.5 - 1.5) + 60.0;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return 5.0 * level + 30.0;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 120000.0);
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return (int) ((100.0 - this.upgradeRechargeAmount(level)) * 120000.0);
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return (int) (this.upgradeDurationAmount(level) * 120000.0);
    }
}
