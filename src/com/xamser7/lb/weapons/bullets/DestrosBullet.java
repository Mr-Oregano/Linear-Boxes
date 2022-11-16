
package com.xamser7.lb.weapons.bullets;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import com.xamser7.lb.Main;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.states.GameState;

public class DestrosBullet extends Bullet
{
    private int fragAmount;
    private float fragWeight;
    private float fragSpeed;

    public DestrosBullet(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float alpha, final double damage, final double duration)
    {
        super(main, x, y, width, height, angle, speed, alpha, damage, duration);
    }

    @Override
    public void update()
    {
        this.x += this.Dx;
        this.y += this.Dy;
        this.bounce();
        this.collision();
        this.defaultUpdate();
    }

    @Override
    public void onCollision()
    {
        super.onCollision();
        
        final int fragWidth = (int) (this.raw_width * this.fragWeight);
        final int fragHeight = (int) (this.raw_height * this.fragWeight);
        final double fragDamage = this.damage * this.fragWeight;
        final double fragDuration = this.duration * this.fragWeight;

        for (int i = 0; i < this.fragAmount; ++i)
        {
            final float angle = (float) (Math.random() * 30.0 - 15.0) + 360 * i / this.fragAmount;
            final float speed = (float) Math.random() * 6.0f + this.fragSpeed;
            final DestrosBullet bullet = new DestrosBullet(this.main, this.x, this.y, fragWidth, fragHeight, angle, speed, 0.0f, fragDamage, fragDuration);
            bullet.setFragAmount(10);
            bullet.setFragWeight(1.0f);
            bullet.setFragSpeed(6.0f);
            GameState.a.add(bullet);
        }
    }

    @Override
    public void render(final Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(3, this.alpha / 100.0f));
        g.drawImage(Assets.bullet, (int) this.x, (int) this.y, this.width, this.height, null);
    }

    public void setFragAmount(final int amount)
    {
        this.fragAmount = amount;
    }

    public void setFragWeight(final float weight)
    {
        this.fragWeight = weight;
    }

    public void setFragSpeed(final float speed)
    {
        this.fragSpeed = speed;
    }

    public int getFragAmount()
    {
        return this.fragAmount;
    }

    public float getFragWeight()
    {
        return this.fragWeight;
    }

    public float getFragSpeed()
    {
        return this.fragSpeed;
    }
}
