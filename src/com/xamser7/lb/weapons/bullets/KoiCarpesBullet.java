package com.xamser7.lb.weapons.bullets;

import com.xamser7.lb.Main;
import com.xamser7.lb.utils.Utils;

public class KoiCarpesBullet extends TrackerBullet
{
    private static final float GROWTH_AMOUNT = 1.1f;

    public KoiCarpesBullet(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float idleSpeed, final float alpha, final double damage, final double duration)
    {
        super(main, x, y, width, height, angle, speed, idleSpeed, alpha, damage, duration);
    }

    @Override
    public void onCollision()
    {
        if (width < main.getWidth() && height < main.getHeight())
        {
            width  *= GROWTH_AMOUNT;
            height *= GROWTH_AMOUNT;
        }

        this.setAngle(Utils.RANDOM.nextFloat(360));
        this.target = null;
    }
}
