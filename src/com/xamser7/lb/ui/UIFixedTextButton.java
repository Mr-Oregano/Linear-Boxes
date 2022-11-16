
package com.xamser7.lb.ui;

import com.xamser7.lb.gfx.Painter;
import java.awt.Graphics2D;
import com.xamser7.lb.Main;
import com.xamser7.lb.gfx.Style;

public class UIFixedTextButton extends UIObject
{
    private String text;
    private Style hoverStyle;
    private Style idleStyle;
    private ClickListener clicker;
    private boolean disabled;

    public UIFixedTextButton(final Main main, final String text, final float x, final float y, final int width, final int height, final boolean center, final Style hoverStyle, final Style idleStyle, final ClickListener clicker)
    {
        super(main, x, y, width, height);
        this.disabled = false;
        this.y -= this.height;

        if (center)
        {
            this.x -= this.width / 2;
            this.y -= this.height / 2;
        }
        
        this.idleStyle = idleStyle;
        this.hoverStyle = hoverStyle;
        this.text = text;
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
        g.setClip((int) this.x, (int) this.y, this.width, this.height);

        if (this.disabled)
        {
            Painter.drawImage(g, this.x, this.y, this.width, this.height, this.idleStyle.background, this.idleStyle.alpha / 2.0f, false, false);
            Painter.drawString(g, this.text, (int) this.x + 5, (int) this.y + Painter.getTextHeight(this.main, this.idleStyle.font), this.idleStyle.color, this.idleStyle.font, this.idleStyle.alpha, false, false);
            return;
        }

        if (this.hover)
        {
            Painter.drawImage(g, this.x, this.y, this.width, this.height, this.hoverStyle.background, this.hoverStyle.alpha, false, false);
            Painter.drawString(g, this.text, (int) this.x + 5, (int) this.y + Painter.getTextHeight(this.main, this.hoverStyle.font), this.hoverStyle.color, this.hoverStyle.font, this.hoverStyle.alpha, false, false);
        }
        else
        {
            Painter.drawImage(g, this.x, this.y, this.width, this.height, this.idleStyle.background, this.idleStyle.alpha, false, false);
            Painter.drawString(g, this.text, (int) this.x + 5, (int) this.y + Painter.getTextHeight(this.main, this.idleStyle.font), this.idleStyle.color, this.idleStyle.font, this.idleStyle.alpha, false, false);
        }

        g.setClip(0, 0, this.main.getWidth(), this.main.getHeight());
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

    @Override
    public void onClick()
    {
        if (!this.disabled)
            this.clicker.onClick();
    }

    public void setString(final String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return this.text;
    }

    public void disabled(final boolean disabled)
    {
        this.disabled = disabled;
    }
}
