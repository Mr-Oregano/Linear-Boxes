
package com.xamser7.lb.ui;

import java.awt.Graphics2D;
import com.xamser7.lb.gfx.Painter;
import com.xamser7.lb.Main;
import java.awt.Font;
import java.awt.Color;

public class UITextButton extends UIObject
{
    private String text;
    private Color c;
    private Font f;
    private float alpha;
    private ClickListener clicker;
    private boolean disabled;

    public UITextButton(final Main main, final String text, final float x, final float y, final Color c, final Font f, final boolean center, final float alpha, final ClickListener clicker)
    {
        super(main, x, y, Painter.getTextWidth(main, text, f), Painter.getTextHeight(main, f));
        this.disabled = false;
        this.y -= this.height;

        if (center)
        {
            this.x -= this.width / 2;
            this.y -= this.height / 2;
        }

        this.text = text;
        this.clicker = clicker;
        this.f = f;
        this.alpha = alpha;
        this.c = c;
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
            Painter.drawString(g, this.text, (int) this.x, (int) this.y + this.height, this.c, this.f, this.alpha / 2.0f, false, false);
            return;
        }

        if (this.hover)
            Painter.drawString(g, this.text, (int) this.x, (int) this.y + this.height, this.c, this.f, this.alpha / 2.0f, false, false);
        else
            Painter.drawString(g, this.text, (int) this.x, (int) this.y + this.height, this.c, this.f, this.alpha, false, false);
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

    public void disabled(final boolean disabled)
    {
        this.disabled = disabled;
    }
}
