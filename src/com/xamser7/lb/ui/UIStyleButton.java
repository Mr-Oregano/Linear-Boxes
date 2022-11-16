
package com.xamser7.lb.ui;

import com.xamser7.lb.gfx.Painter;
import java.awt.Graphics2D;
import com.xamser7.lb.Main;
import com.xamser7.lb.gfx.Style;
import java.awt.image.BufferedImage;

public class UIStyleButton extends UIObject
{
    private BufferedImage image;
    private Style disabledStyle;
    private Style hoverStyle;
    private Style idleStyle;
    private ClickListener clicker;
    private boolean disabled;

    public UIStyleButton(final Main main, final BufferedImage image, final float x, final float y, final int width, final int height, final boolean center, final Style hoverStyle, final Style idleStyle, final Style disabledStyle, final ClickListener clicker)
    {
        super(main, x, y, width, height);
        this.disabled = false;
        this.y -= this.height;
        
        if (center)
        {
            this.x -= this.width / 2;
            this.y -= this.height / 2;
        }

        this.image = image;
        this.idleStyle = idleStyle;
        this.hoverStyle = hoverStyle;
        this.disabledStyle = disabledStyle;
        this.clicker = clicker;
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
            Painter.drawImage(g, this.x, this.y, this.width, this.height, this.disabledStyle.overlay, this.disabledStyle.alpha, false, false);
            Painter.drawString(g, this.disabledStyle.text, (int) this.x + this.width / 2, (int) this.y + this.height / 2 - 15, this.disabledStyle.color, this.disabledStyle.font, 100.0f, true, false);
            return;
        }

        if (this.hover)
            Painter.drawImage(g, this.x, this.y, this.width, this.height, this.image, this.hoverStyle.alpha, false, false);
        else
            Painter.drawImage(g, this.x, this.y, this.width, this.height, this.image, this.idleStyle.alpha, false, false);
    }

    @Override
    public void onClick()
    {
        if (!this.disabled)
            this.clicker.onClick();
    }

    public void setHoverStyle(final Style hoverStyle)
    {
        this.hoverStyle = hoverStyle;
    }

    public Style getHoverStyle()
    {
        return this.hoverStyle;
    }

    public void setIdleStyle(final Style idleStyle)
    {
        this.idleStyle = idleStyle;
    }

    public Style getIdleStyle()
    {
        return this.idleStyle;
    }

    public void disabled(final boolean disabled)
    {
        this.disabled = disabled;
    }
}
