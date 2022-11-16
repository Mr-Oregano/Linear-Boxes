
package com.xamser7.lb.ui;

import java.awt.Graphics2D;
import com.xamser7.lb.Main;
import java.util.ArrayList;

public class UIComponentPane extends UIObject
{
    private ArrayList<UIObject> components;
    private int rows;
    private int cols;

    public UIComponentPane(final Main main, final float x, final float y, final int width, final int height, final boolean center)
    {
        super(main, x, y, width, height);

        if (center)
        {
            this.x -= this.width >> 1;
            this.y -= this.height >> 1;
        }

        this.components = new ArrayList<UIObject>();
    }

    @Override
    public void update()
    {
        this.checkBounds();
        this.updateAllComponents();
    }

    @Override
    public void render(final Graphics2D g)
    {
        this.renderAllComponents(g);
    }

    public void add(final UIObject o)
    {
        if (o.width * (this.cols + 1) > this.width)
        {
            this.cols = 0;
            ++this.rows;
        }

        o.x = this.x + o.width * this.cols++;
        o.y = this.y + this.rows * o.height;
        this.components.add(o);
    }

    public void updateAllComponents()
    {
        for (final UIObject o : this.components)
            o.update();
    }

    public void renderAllComponents(final Graphics2D g)
    {
        for (final UIObject o : this.components)
            o.render(g);
    }

    @Override
    public void onClick()
    {
        for (int i = 0; i < this.components.size(); ++i)
        {
            final UIObject o = this.components.get(i);
            if (o.hover && this.main.getMouseManager().isLeft())
                o.onClick();
            else
                o.onGlobalClick();
        }
    }

    public int size()
    {
        return this.components.size();
    }

    public ArrayList<UIObject> getList()
    {
        return this.components;
    }

    public UIObject get(final int index)
    {
        return this.components.get(index);
    }

    public UIObject getLast()
    {
        return this.components.get(this.components.size() - 1);
    }

    public UIObject getFirst()
    {
        return this.components.get(0);
    }
}
