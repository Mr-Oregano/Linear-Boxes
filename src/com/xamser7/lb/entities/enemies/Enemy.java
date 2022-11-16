
package com.xamser7.lb.entities.enemies;

import com.xamser7.lb.utils.RandomPC;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.gfx.Painter;
import com.xamser7.lb.assets.Assets;
import java.awt.Graphics2D;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.Game;
import com.xamser7.lb.Main;
import com.xamser7.lb.entities.Entity;

public abstract class Enemy extends Entity
{
    public int health;
    protected int MaxHealth;
    protected int prize;

    public Enemy(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float alpha)
    {
        super(main, x, y, width, height, angle, speed, alpha);
        this.health = (int) Math.round(Math.pow(Game.Level / 5.0, 1.31) + 10.0);
        this.prize = (int) Math.round(Math.pow(Game.Level / 5.0, 2.0) + 500.0);
        this.MaxHealth = this.health;
    }

    protected void updatePosition(final boolean hostile, final boolean bounce)
    {
        if (this.health <= 0)
        {
            this.active = false;
        }
        this.x += this.Dx;
        this.y += this.Dy;
        if (bounce)
        {
            this.bounce();
        }
        if (this.x < -this.width || this.x > this.main.getWidth() + this.width || this.y < -this.height || this.y > this.main.getHeight() + this.height)
        {
            GameState.e.remove(this);
        }
        if (this.alpha < 50.0f)
        {
            ++this.alpha;
        }
        if ((this.lifeTime >= 180 || !hostile) && this.alpha < 100.0f)
        {
            ++this.alpha;
        }
        if (hostile && this.getCollision(this, GameState.p.get(0)))
        {
            GameState.p.get(0).active = false;
        }
        ++this.lifeTime;
        if (this.health < 0)
        {
            this.health = 0;
        }
    }

    protected void showHealth(final Graphics2D g)
    {
        if (this.health == this.MaxHealth)
        {
            return;
        }
        Painter.fillRect(g, (int) (this.x + 4.0f * this.main.getWScale()), (int) (this.y + this.height + 6.0f * this.main.getHScale()), (int) (this.health * (this.width - 8.0f) / this.MaxHealth), 4, Assets.algaeGreen, this.alpha, false, false);
    }

    public static void newEnemy(final int amount, final Main main)
    {
        for (int i = 0; i < amount; ++i)
        {
            final int height;
            final int width = height = 60;
            final float x = (float)  (Utils.RANDOM.nextInt(main.getWidth() - width) + width / 2);
            final float y = (float)  (Utils.RANDOM.nextInt(main.getHeight() - height) + height / 2);
            final float Dx = (float) (Utils.RANDOM.nextInt(4) + 4);
            final float Dy = (float) (Utils.RANDOM.nextInt(4) + 4);
            final float angle = (float) Math.toDegrees(Math.atan(Dx / Dy));
            final float speed = (float) (Utils.RANDOM.nextInt(4) + 5);
            final float alpha = 0.0f;
            final int type = RandomPC.nextBoolean(60.0, 15.0, 15.0);
            if (type == 0)
            {
                GameState.e.add(new RedEnemy(main, x, y, width, height, (Dx < 0.0f) ? (angle + 180.0f) : angle, speed, alpha));
            }
            else if (type == 1)
            {
                GameState.e.add(new Zipper(main, x, y, (int) (width * 0.7), (int) (height * 0.7), (Dx < 0.0f) ? (angle + 180.0f) : angle, speed + 5.0f, alpha));
            }
            else if (type == 2)
            {
                GameState.e.add(new Blontro(main, x, y, (int) (width * 1.5), (int) (height * 1.5), (Dx < 0.0f) ? (angle + 180.0f) : angle, speed - 3.0f, alpha));
            }
            else
            {
                GameState.e.add(new BitThief(main, x, y, width - 6, height - 6, (Dx < 0.0f) ? (angle + 180.0f) : angle, speed + 2.0f, alpha));
            }
        }
    }

    public int getHealth()
    {
        return this.health;
    }

    public int getPrize()
    {
        return this.prize;
    }
}
