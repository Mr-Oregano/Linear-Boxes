package com.xamser7.lb.weapons.bullets;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import com.xamser7.lb.Main;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.entities.enemies.Enemy;
import com.xamser7.lb.states.GameState;

public class Neptune27sBullet extends Bullet
{
    private static final int MAX_RADIUS = 40;
    private Enemy target;
    private Neptune27sBullet neighbor;
    private boolean atPlayer;
    private int radius;

    private float idleSpeed;

    public Neptune27sBullet(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float idleSpeed, final float alpha, final double damage, final double duration)
    {
        super(main, x, y, width, height, angle, speed, alpha, damage, duration);
        this.atPlayer = true;
        this.idleSpeed = idleSpeed;
    }

    @Override
    public void update()
    {
        final Player p = GameState.p.get(0);
        if (this.target == null || !GameState.e.contains(this.target) || this.target.getAlpha() < 100.0f)
            this.targetEnemy();

        if (this.target != null)
        {
            this.atPlayer = false;
            final double cx = this.target.getX() - this.x;
            final double cy = this.target.getY() - this.y;
            
            // Move bullet to location of target if radius is less than speed
            if (Math.hypot(cx, cy) < this.speed)
            {
                this.x = this.target.getX() - this.speed / 2.0f;
                this.y = this.target.getY() - this.speed / 2.0f;
            }
            else
            {
                double angle = (float) Math.atan2(cy, cx);
                this.y += (float) (Math.sin(angle) * this.speed);
                this.x += (float) (Math.cos(angle) * this.speed);
            }

            this.collision();
            this.defaultUpdate();
            return;
        }

        if (this.atPlayer)
        {
            this.x = (float) (int) (p.getX() + Math.cos(Math.toRadians(this.getAngle())) * this.radius - this.width / 2);
            this.y = (float) (int) (p.getY() + Math.sin(Math.toRadians(this.getAngle())) * this.radius - this.height / 2);
            this.setAngle((float) (int) (this.getAngle() + this.idleSpeed * 0.5));
            this.radius += (int) ((this.radius < MAX_RADIUS + p.getWidth()) ? (this.idleSpeed * 0.5) : 0.0);
            this.radius = Math.min(this.radius, MAX_RADIUS + p.getWidth());
            this.defaultUpdate();
            this.targetEnemy();
            return;
        }

        final double cx = p.getX() - this.x;
        final double cy = p.getY() - this.y;
        double angle = (float) Math.atan2(cy, cx);
        this.x += (float) (Math.cos(angle) * this.speed);
        this.y += (float) (Math.sin(angle) * this.speed);
        
        if (!this.atPlayer && Math.hypot(cx, cy) < p.getWidth() / 2)
        {
            this.atPlayer = true;
            this.radius = 0;

            if (this.neighbor != null)
                this.setAngle(this.neighbor.getAngle() - 180.0f);
            else
                this.setAngle((float) (int) Math.toDegrees(angle));
        }
    }

    @Override
    public void render(final Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(3, this.alpha / 100.0f));
        g.drawImage(Assets.bullet, (int) this.x, (int) this.y, this.width, this.height, null);
    }

    public void setNeighbor(final Neptune27sBullet neighbor)
    {
        this.neighbor = neighbor;
    }

    public Neptune27sBullet getNeighbor()
    {
        return this.neighbor;
    }

    private void targetEnemy()
    {
        double min = Double.MAX_VALUE;
        this.target = null;
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

    @Override
    public void onCollision() {}
}
