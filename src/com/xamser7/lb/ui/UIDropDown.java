
package com.xamser7.lb.ui;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.xamser7.lb.Main;
import com.xamser7.lb.gfx.Painter;

public class UIDropDown extends UIObject
{
    private BufferedImage[] main_dropdown;
    private List<UIFixedTextButton> list;
    private UIFixedTextButton selected;
    private boolean disabled;
    private boolean show;
    private int fullheight;

    public UIDropDown(final Main main, final float x, final float y, final double scale, final boolean center, final BufferedImage[] image)
    {
        super(main, x, y, (int) (image[0].getWidth() * scale), (int) (image[0].getHeight() * scale));
        this.list = new ArrayList<UIFixedTextButton>();
        this.selected = null;
        this.disabled = false;
        this.show = false;
        this.fullheight = 0;
        this.main_dropdown = image;
        this.overrideInvalidRegion = true;
        this.fullheight = this.height;

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
        {
            this.checkBounds();

            if (this.show)
                for (final UIFixedTextButton item : this.list)
                    item.update();
        }
    }

    @Override
    public void render(final Graphics2D g)
    {
        g.setClip((int) this.x, (int) this.y, this.width, this.height);

        if (this.disabled)
        {
            g.setComposite(AlphaComposite.getInstance(3, 0.5f));
            g.drawImage(this.main_dropdown[1], (int) this.x, (int) this.y, this.width, this.height, null);
            if (this.selected != null)
            {
                Painter.drawString(g, this.selected.getText(), (int) this.x + 5, (int) this.y + Painter.getTextHeight(this.main, this.selected.getIdleStyle().font), this.selected.getIdleStyle().color, this.selected.getIdleStyle().font, this.selected.getIdleStyle().alpha / 2.0f, false, false);
            }
            return;
        }

        if (this.show)
        {
            for (final UIFixedTextButton item : this.list)
                item.render(g);
        }

        if (this.hover)
        {
            g.drawImage(this.main_dropdown[1], (int) this.x, (int) this.y, this.width, this.height, null);
            if (this.selected != null)
                Painter.drawString(g, this.selected.getText(), (int) this.x + 5, (int) this.y + Painter.getTextHeight(this.main, this.selected.getHoverStyle().font), this.selected.getHoverStyle().color, this.selected.getHoverStyle().font, this.selected.getHoverStyle().alpha, false, false);
        }
        else
        {
            g.drawImage(this.main_dropdown[0], (int) this.x, (int) this.y, this.width, this.height, null);
            if (this.selected != null)
                Painter.drawString(g, this.selected.getText(), (int) this.x + 5, (int) this.y + Painter.getTextHeight(this.main, this.selected.getIdleStyle().font), this.selected.getIdleStyle().color, this.selected.getIdleStyle().font, this.selected.getIdleStyle().alpha, false, false);
        }

        g.setClip(0, 0, this.main.getWidth(), this.main.getHeight());
    }

    @Override
    public void onGlobalClick()
    {
        if (!this.disabled)
        {
            this.show = false;
            UIObject.invalidate(new Rectangle(0, 0, 0, 0));
            
            for (final UIFixedTextButton item : this.list)
            {
                if (item.hover && !this.show)
                    item.onClick();

                item.hover = false;
                item.disabled(true);
            }
        }
    }

    @Override
    public void onClick()
    {
        if (!this.disabled)
        {
            this.show = !this.show;
            if (this.show)
                UIObject.invalidate(new Rectangle((int) this.x, (int) this.y, this.width, this.fullheight));
            else
                UIObject.invalidate(new Rectangle(0, 0, 0, 0));

            for (final UIFixedTextButton item : this.list)
            {
                if (item.hover && !this.show)
                    item.onClick();

                item.hover = false;
                item.disabled(!this.show);
            }
        }
    }

    public void addItem(final UIFixedTextButton item)
    {
        int yOffset = 0;
        
        if (this.list.isEmpty())
            yOffset = Math.round(this.y + this.height);
        else
            yOffset = Math.round(this.list.get(this.list.size() - 1).y + this.list.get(this.list.size() - 1).height);

        this.list.add(item);
        item.overrideInvalidRegion = true;
        item.x = this.x;
        item.y = (float) yOffset;
        item.width = this.width;
        this.fullheight += this.list.get(this.list.size() - 1).height;

        item.disabled(true);
    }

    public void setSelected(final int index)
    {
        if (index > this.list.size() - 1)
            return;

        this.selected = this.list.get(index);
    }

    public UIFixedTextButton getSelected()
    {
        return this.selected;
    }

    public void disabled(final boolean disabled)
    {
        this.disabled = disabled;
    }

    public BufferedImage[] getMainImages()
    {
        return this.main_dropdown;
    }

    public void setMainImages(final BufferedImage[] images)
    {
        this.main_dropdown = images;
    }
}
