
package com.xamser7.lb.entities.enemies;

import com.xamser7.lb.assets.Assets;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.Main;

public class BitThief extends Enemy
{
    private boolean bounce;
    private int duration;

    public BitThief(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float alpha)
    {
        super(main, x, y, width, height, angle, speed, alpha);
        this.bounce = true;
        this.duration = Utils.RANDOM.nextInt(6) + 8;
    }

    @Override
    public void update()
    {
        if (this.active)
        {
            if (this.lifeTime > 60 * this.duration)
            {
                this.bounce = false;
            }
            this.updatePosition(false, this.bounce);
            for (int i = 0; i < GameState.b.size(); ++i)
            {
                if (this.getCollision(this, GameState.b.get(i)))
                {
                    GameState.b.remove(GameState.b.get(i));
                    this.prize += 500;
                }
            }
        }
        else
        {
            if (this.alpha == 100.0f)
            {
                GameState.getGame().addScore(this.prize);
            }
            this.alpha -= 2.0f;
            if (this.alpha <= 0.0f)
            {
                GameState.e.remove(this);
            }
        }
    }

    @Override
    public void render(final Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(3, this.alpha / 100.0f));
        g.drawImage(Assets.bitThief, (int) this.x, (int) this.y, this.width, this.height, null);
        this.showHealth(g);
    }
}
