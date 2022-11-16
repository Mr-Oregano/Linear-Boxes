
package com.xamser7.lb.ui;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import com.xamser7.lb.Main;
import java.awt.image.BufferedImage;

public class UIButton extends UIObject
{
    private BufferedImage[] images;
    private ClickListener clicker;
    private boolean disabled;

    public UIButton(final Main main, final float x, final float y, final double scale, final boolean center, final BufferedImage[] image, final ClickListener clicker)
    {
        super(main, x, y, (int) (image[0].getWidth() * scale), (int) (image[0].getHeight() * scale));
        
        this.disabled = false;
        this.images = image;
        this.clicker = clicker;

        if (center)
        {
            this.x -= this.width / 2;
            this.y -= this.height / 2;
        }
    }

    @Override
    public void update()
    {
        if (!this.disabled)
            this.checkBounds();
    }

    @Override
    public void render(final Graphics2D g)
    {
        if (this.disabled)
        {
            g.setComposite(AlphaComposite.getInstance(3, 0.5f));
            g.drawImage(this.images[1], (int) this.x, (int) this.y, this.width, this.height, null);
            return;
        }

        g.setComposite(AlphaComposite.getInstance(3, 1.0f));
        
        if (this.hover)
            g.drawImage(this.images[1], (int) this.x, (int) this.y, this.width, this.height, null);
        else
            g.drawImage(this.images[0], (int) this.x, (int) this.y, this.width, this.height, null);
    }

    @Override
    public void onClick()
    {
        if (!this.disabled)
            this.clicker.onClick();
    }

    public BufferedImage[] getImages()
    {
        return this.images;
    }

    public void setImages(final BufferedImage[] images)
    {
        this.images = images;
    }

    public void disabled(final boolean disabled)
    {
        this.disabled = disabled;
    }
}
