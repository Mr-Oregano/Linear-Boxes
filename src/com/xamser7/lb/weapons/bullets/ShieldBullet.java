package com.xamser7.lb.weapons.bullets;

import com.xamser7.lb.assets.Assets;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.Main;

public class ShieldBullet extends Bullet
{
    private int maxRadius;
    private int radius;

    public ShieldBullet(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float alpha, final double damage, final double duration)
    {
        super(main, x, y, width, height, angle, speed, alpha, damage, duration);
    }

    @Override
    public void update()
    {
        final Player player = GameState.p.get(0);
        this.x = player.getX() + this.Dx * this.radius / this.speed - this.width / 2;
        this.y = player.getY() + this.Dy * this.radius / this.speed - this.height / 2;
        this.setAngle(this.getAngle() + this.speed);
        this.collision();
        this.defaultUpdate();
        this.radius += ((this.radius < this.maxRadius) ? 5 : 0);
    }

    @Override
    public void render(final Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(3, this.alpha / 100.0f));
        g.drawImage(Assets.bullet, (int) this.x, (int) this.y, this.width, this.height, null);
    }

    public int getRadius()
    {
        return this.maxRadius;
    }

    public void setRadius(final int radius)
    {
        this.maxRadius = radius;
    }

    @Override
    public void onCollision() {}
}
