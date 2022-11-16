
package com.xamser7.lb.ui;

import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import com.xamser7.lb.Main;
import java.util.ArrayList;

public class UIManager
{
    private ArrayList<UIObject> obj;

    public UIManager(final Main main)
    {
        main.getMouseManager().setUIManager(this);
        this.obj = new ArrayList<UIObject>();
    }

    public void update()
    {
        for (final UIObject o : this.obj)
            o.update();
    }

    public void render(final Graphics2D g)
    {
        for (final UIObject o : this.obj)
            o.render(g);
    }

    public void onMouseRelease(final MouseEvent e)
    {
        for (final UIObject o : this.obj)
            o.onMouseRelease(e);
    }

    public void addObject(final UIObject o)
    {
        this.obj.add(o);
    }

    public void removeObject(final UIObject o)
    {
        this.obj.remove(o);
    }
}
