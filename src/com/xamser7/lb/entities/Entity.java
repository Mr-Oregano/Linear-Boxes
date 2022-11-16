
package com.xamser7.lb.entities;

import java.awt.Graphics2D;
import com.xamser7.lb.Main;

public abstract class Entity
{
    protected float x;
    protected float y;
    protected float Dx;
    protected float Dy;
    protected float alpha;
    protected float speed;
    protected int width;
    protected int height;
    protected int lifeTime;
    protected int raw_width;
    protected int raw_height;
    protected Main main;
    public boolean active;
    private float angle;

    public Entity(final Main main, float x, float y, int width, int height, final float angle, final float speed, final float alpha)
    {
        this.main = main;
        this.raw_width = width;
        this.raw_height = height;
        x *= main.getWScale();
        y *= main.getHScale();
        width *= main.getWScale();
        height *= main.getHScale();
        this.x = x - width / 2;
        this.y = y - height / 2;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.speed = speed;
        this.alpha = alpha;
        this.active = true;
        this.lifeTime = 0;
        this.Dx = (float) Math.cos(Math.toRadians(angle)) * speed;
        this.Dy = (float) Math.sin(Math.toRadians(angle)) * speed;
        this.Dx *= main.getHScale();
        this.Dy *= main.getHScale();
    }

    public abstract void update();

    public abstract void render(final Graphics2D p0);

    public boolean getCollision(final Entity e1, final Entity e2)
    {
        return e1.alpha == 100.0f && e2.alpha == 100.0f && e1.x <= e2.x + e2.width && e2.x <= e1.x + e1.width && e1.y <= e2.y + e2.height && e2.y <= e1.y + e1.height;
    }

    public void bounce()
    {
        this.x = Math.max(Math.min((float) (this.main.getWidth() - this.width), this.x), 0.0f);
        this.y = Math.max(Math.min((float) (this.main.getHeight() - this.height), this.y), 0.0f);
        
        if (this.x <= 0.0f || this.x >= this.main.getWidth() - this.width)
            this.Dx = -this.Dx;

        if (this.y <= 0.0f || this.y >= this.main.getHeight() - this.height)
            this.Dy = -this.Dy;
    }

    private float xComp()
    {
        final float xC = (float) Math.cos(Math.toRadians(this.angle));
        return xC * this.speed;
    }

    private float yComp()
    {
        final float yC = (float) Math.sin(Math.toRadians(this.angle));
        return yC * this.speed;
    }

    public boolean isActive()
    {
        return this.active;
    }

    public float getX()
    {
        return this.x + this.width / 2;
    }

    public float getY()
    {
        return this.y + this.height / 2;
    }

    public float getDx()
    {
        return this.Dx;
    }

    public float getDy()
    {
        return this.Dy;
    }

    public float getAngle()
    {
        return this.angle;
    }

    public void setAngle(final float angle)
    {
        this.angle = angle % 360.0f;
        this.Dx = this.xComp();
        this.Dy = this.yComp();
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public float getAlpha()
    {
        return this.alpha;
    }

    public void setAlpha(final float alpha)
    {
        this.alpha = alpha;
    }
}
