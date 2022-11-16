
package com.xamser7.lb.bits;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import com.xamser7.lb.Game;
import com.xamser7.lb.Main;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.states.GameState;

public class BonusBit extends Bit
{
    public BonusBit(final Main main, final float x, final float y, final int width, final int height, final float alpha)
    {
        super(main, x, y, width, height, alpha);
    }

    @Override
    public void update()
    {
        if (this.getCollision(this, GameState.p.get(0)))
        {
            ++Game.TotalBits;
            GameState.getGame().addScore(1000);
            GameState.b.remove(this);
        }
        
        this.updatePosition();
    }

    @Override
    public void render(final Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(3, this.alpha / 100.0f));
        g.drawImage(Assets.bonus, (int) this.x, (int) this.y, this.width, this.height, null);
    }
}
