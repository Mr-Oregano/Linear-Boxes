
package com.xamser7.lb.weapons.bullets;

import com.xamser7.lb.Game;
import com.xamser7.lb.entities.enemies.Enemy;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.Main;
import com.xamser7.lb.entities.Entity;

public abstract class Bullet extends Entity
{
    protected boolean active;
    protected double damage;
    protected double duration;

    public Bullet(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float alpha, final double damage, final double duration)
    {
        super(main, x / main.getWScale(), y / main.getHScale(), width, height, angle, speed, alpha);
        this.damage = damage;
        this.duration = duration;
        this.active = true;
    }

    protected void defaultUpdate()
    {
        if (this.lifeTime > 60.0 * this.duration || !GameState.p.get(0).isActive())
        {
            this.alpha -= 2.0f;
            if (this.alpha <= 0.0f)
                GameState.a.remove(this);
        }
        else if (this.alpha < 100.0f)
        {
            this.alpha += 5.0f;
            this.alpha = Math.min(this.alpha, 100.0f);
        }
        
        ++this.lifeTime;
    }

    public void collision()
    {
        for (int i = 0; i < GameState.e.size(); ++i)
        {
            if (this.getCollision(this, GameState.e.get(i)))
            {
                onCollision();
                
                final Enemy enemy = GameState.e.get(i);
                enemy.health -= (int) this.damage;
                if (enemy.health <= 0)
                {
                    enemy.health = 0;
                    ++Game.TotalEnemiesKilled;
                }
            }
        }
    }

    public void onCollision()
    {
        GameState.a.remove(this);
        this.active = false;
    }
}
