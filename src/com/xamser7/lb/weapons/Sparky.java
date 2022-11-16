
package com.xamser7.lb.weapons;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.weapons.bullets.SmallBullet;
import com.xamser7.lb.Main;

public class Sparky extends Weapon
{
    private static final String DESC = "Launches explosive bullets that surround the\nmap and make it look like a fireworks show.";
    private static final int AMOUNT = 10;

    public Sparky(final Main main)
    {
        super(main, DESC, "Sparky", 18.0, 15.0, 0.75, 50000, 5, false);
        this.damage_max = 2;
        this.recharge_max = 2;
        this.duration_max = 2;
    }

    @Override
    public void fire()
    {
        for (int j = 0; j < 5; ++j)
        {
            final int height;
            final int width = height = 10;
            final int x = Utils.RANDOM.nextInt(this.main.getWidth() - width) + width / 2;
            final int y = Utils.RANDOM.nextInt(this.main.getHeight() - height) + height / 2;
            for (int i = 0; i < AMOUNT; ++i)
            {
                GameState.a.add(new SmallBullet(this.main, (float) x, (float) y, width, height, (float) (i * 360 / AMOUNT), 7.0f, 100.0f, this.damage, this.duration));
            }
        }
    }

    @Override
    public double upgradeDamageAmount(final int level)
    {
        return 7.5 * level + 15.0;
    }

    @Override
    public double upgradeRechargeAmount(final int level)
    {
        return level * (-level - 2) + 18;
    }

    @Override
    public double upgradeDurationAmount(final int level)
    {
        return level * (level / 8.0 + 0.375) + 0.75;
    }

    @Override
    public int upgradeDamageCost(final int level)
    {
        return (int) (this.upgradeDamageAmount(level) * 750.0);
    }

    @Override
    public int upgradeRechargeCost(final int level)
    {
        return (int) ((25.0 - this.upgradeRechargeAmount(level)) * 1800.0);
    }

    @Override
    public int upgradeDurationCost(final int level)
    {
        return (int) (this.upgradeDurationAmount(level) * 15000.0);
    }
}
