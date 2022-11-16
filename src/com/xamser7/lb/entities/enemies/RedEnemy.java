
package com.xamser7.lb.entities.enemies;

import com.xamser7.lb.assets.Assets;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import com.xamser7.lb.states.GameState;
import com.xamser7.lb.Main;

public class RedEnemy extends Enemy
{
    public RedEnemy(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float alpha)
    {
        super(main, x, y, width, height, angle, speed, alpha);
    }

    @Override
    public void update()
    {
        if (this.active)
        {
            this.updatePosition(true, true);
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
        g.drawImage(Assets.redEnemy, (int) this.x, (int) this.y, this.width, this.height, null);
        this.showHealth(g);
    }
}
