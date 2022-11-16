
package com.xamser7.lb.ui;

import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import com.xamser7.lb.Main;
import java.awt.Rectangle;

public abstract class UIObject
{
    protected static Rectangle invalidRegion;
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected boolean hover;
    protected Main main;
    protected boolean overrideInvalidRegion;

    static
    {
        UIObject.invalidRegion = new Rectangle(0, 0, 0, 0);
    }

    public UIObject(final Main main, final float x, final float y, final int width, final int height)
    {
        this.x = x * main.getWScale();
        this.y = y * main.getHScale();
        this.width = (int) (width * main.getWScale());
        this.height = (int) (height * main.getHScale());
        this.main = main;
        this.hover = false;
    }

    public abstract void update();

    public abstract void render(final Graphics2D p0);

    public abstract void onClick();

    public void onGlobalClick()
    {}

    private boolean getBounds()
    {
        return this.main.getMouseManager().getMouseX() >= this.x 
            && this.main.getMouseManager().getMouseX() < this.x + this.width 
            && this.main.getMouseManager().getMouseY() >= this.y 
            && this.main.getMouseManager().getMouseY() < this.y + this.height 
            && (this.main.getMouseManager().getMouseX() <= UIObject.invalidRegion.x 
            || this.main.getMouseManager().getMouseX() > UIObject.invalidRegion.x + UIObject.invalidRegion.width 
            || this.main.getMouseManager().getMouseY() <= UIObject.invalidRegion.y 
            || this.main.getMouseManager().getMouseY() > UIObject.invalidRegion.y + UIObject.invalidRegion.height 
            || this.overrideInvalidRegion);
    }

    public void checkBounds()
    {
        this.hover = this.getBounds();
    }

    public void onMouseRelease(final MouseEvent e)
    {
        if (this.hover && this.main.getMouseManager().isLeft())
            this.onClick();
        else
            this.onGlobalClick();
    }

    public static void invalidate(final Rectangle rect)
    {
        UIObject.invalidRegion = rect;
    }

    public float getX()
    {
        return this.x;
    }

    public float getY()
    {
        return this.y;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public boolean isHover()
    {
        return this.hover;
    }
}
