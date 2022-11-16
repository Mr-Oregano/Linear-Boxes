
package com.xamser7.lb.bits;

import com.xamser7.lb.utils.RandomPC;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.Game;
import com.xamser7.lb.Main;
import com.xamser7.lb.entities.Entity;

public abstract class Bit extends Entity
{
    private double angle;
    private int duration;
    private static float pickup_range;

    public Bit(final Main main, final float x, final float y, final int width, final int height, final float alpha)
    {
        super(main, x, y, width, height, 0.0f, 0.0f, alpha);
        this.duration = 10;
        Bit.pickup_range = Game.Range * main.getWScale();
    }

    protected void updatePosition()
    {
        if (this.lifeTime <= 60 * this.duration && this.alpha < 100.0f)
            this.alpha += 4.0f;

        if (this.lifeTime > 60 * this.duration)
            this.alpha -= 2.0f;

        if (this.alpha <= 0.0f)
            GameState.b.remove(this);

        final Player player = GameState.p.get(0);
        final double cx = player.getX() - this.x;
        final double cy = player.getY() - this.y;
        final double distance = Math.sqrt(cx * cx + cy * cy);

        if (this.lifeTime <= 60 * this.duration && distance < Bit.pickup_range + player.getWidth())
        {
            this.angle = ((cx == 0.0) ? 90.0 : Math.toDegrees(Math.atan(cy / cx)));
            this.angle += ((cx > 0.0) ? 180 : 0);
            
            if (distance <= player.getWidth() / 2 - 20)
            {
                this.alpha = 100.0f;
                this.x = player.getX();
                this.y = player.getY();
                return;
            }

            double speed = 4.0 * (Bit.pickup_range + player.getWidth() / 2) / distance;
            speed = Math.min(speed, player.getWidth() / 2 - 20);
            this.x -= (float) (Math.cos(Math.toRadians(this.angle)) * speed);
            this.y -= (float) (Math.sin(Math.toRadians(this.angle)) * speed);
        }

        ++this.lifeTime;
    }

    public static void newBit(final int amount, final Main main)
    {
        for (int i = 0; i < amount; ++i)
        {
            final int type = RandomPC.nextBoolean(80.0);
            final int height;
            final int width = height = 15;
            final float x = (float) (Utils.RANDOM.nextInt(main.getWidth() - width * 2) + width);
            final float y = (float) (Utils.RANDOM.nextInt(main.getHeight() - height * 2) + height);
            final float alpha = 0.0f;

            if (type == 0)
                GameState.b.add(new BonusBit(main, x, y, width, height, alpha));

            else if (type == 1)
                GameState.b.add(new SuperBit(main, x, x, width, height, alpha));
        }
    }
}
