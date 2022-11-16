
package com.xamser7.lb.input;

import java.awt.event.MouseEvent;
import com.xamser7.lb.ui.UIManager;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener, MouseMotionListener
{
    private boolean leftPressed;
    private boolean rightPressed;
    private int MouseX;
    private int MouseY;
    private UIManager uiManager;

    public void setUIManager(final UIManager uiManager)
    {
        this.uiManager = uiManager;
    }

    public boolean isLeft()
    {
        return this.leftPressed;
    }

    public boolean isRight()
    {
        return this.rightPressed;
    }

    public int getMouseX()
    {
        return this.MouseX;
    }

    public int getMouseY()
    {
        return this.MouseY;
    }

    @Override
    public void mousePressed(final MouseEvent e)
    {
        if (e.getButton() == 1)
            this.leftPressed = true;

        if (e.getButton() == 3)
            this.rightPressed = true;
    }

    @Override
    public void mouseReleased(final MouseEvent e)
    {
        if (this.uiManager != null)
            this.uiManager.onMouseRelease(e);

        if (e.getButton() == 1)
            this.leftPressed = false;

        if (e.getButton() == 3)
            this.rightPressed = false;
    }

    @Override
    public void mouseMoved(final MouseEvent e)
    {
        this.MouseX = e.getX();
        this.MouseY = e.getY();
    }

    @Override
    public void mouseDragged(final MouseEvent e) {}

    @Override
    public void mouseClicked(final MouseEvent e) {}

    @Override
    public void mouseEntered(final MouseEvent e) {}

    @Override
    public void mouseExited(final MouseEvent e) {}
}
