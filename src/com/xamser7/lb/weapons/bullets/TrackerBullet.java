
package com.xamser7.lb.weapons.bullets;

import com.xamser7.lb.assets.Assets;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.Main;
import com.xamser7.lb.entities.enemies.Enemy;

public class TrackerBullet extends Bullet
{
    protected Enemy target;

    private float speed;

    public TrackerBullet(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float idleSpeed, final float alpha, final double damage, final double duration)
    {
        super(main, x, y, width, height, angle, idleSpeed, alpha, damage, duration);
        this.speed = speed;
    }

    @Override
    public void update()
    {
        if (this.target == null || !GameState.e.contains(this.target))
            this.targetEnemy();

        if (this.target == null)
        {
            this.x += this.Dx;
            this.y += this.Dy;
            this.bounce();
            this.targetEnemy();
            return;
        }

        final double cx = this.target.getX() - this.x;
        final double cy = this.target.getY() - this.y;

        // Move bullet to location of target if radius is less than speed
        if (Math.hypot(cx, cy) < speed)
        {
            this.x = this.target.getX() - this.speed / 2.0f;
            this.y = this.target.getY() - this.speed / 2.0f;
        }
        else
        {
            double angle = (float) Math.atan2(cy, cx);
            this.x += (float) Math.cos(angle) * this.speed;
            this.y += (float) Math.sin(angle) * this.speed;
        }

        this.collision();
        this.defaultUpdate();
    }

    @Override
    public void render(final Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(3, this.alpha / 100.0f));
        g.drawImage(Assets.bullet, (int) this.x, (int) this.y, this.width, this.height, null);
    }

    public void targetEnemy()
    {
        double min = Double.MAX_VALUE;
        for (final Enemy e : GameState.e)
        {
            if (e.getAlpha() < 100.0f)
                continue;

            final double distance = Math.hypot(e.getX() - this.x, e.getY() - this.y);
            if (distance >= min)
                continue;

            min = distance;
            this.target = e;
        }
    }
}
