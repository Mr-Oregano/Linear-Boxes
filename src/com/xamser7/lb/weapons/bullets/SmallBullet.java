
package com.xamser7.lb.weapons.bullets;

import com.xamser7.lb.assets.Assets;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import com.xamser7.lb.Main;

public class SmallBullet extends Bullet
{
    public SmallBullet(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float alpha, final double damage, final double duration)
    {
        super(main, x, y, width, height, angle, speed, alpha, damage, duration);
    }

    @Override
    public void update()
    {
        this.x += this.Dx;
        this.y += this.Dy;
        this.collision();
        this.defaultUpdate();
    }

    @Override
    public void render(final Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(3, this.alpha / 100.0f));
        g.drawImage(Assets.bullet, (int) this.x, (int) this.y, this.width, this.height, null);
    }
}
