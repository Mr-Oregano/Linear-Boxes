
package com.xamser7.lb.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener
{
    public boolean lock;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean space;
    public boolean esc;

    public KeyManager()
    {
        this.lock = true;
    }

    @Override
    public void keyPressed(final KeyEvent e)
    {
        if (e.getKeyCode() == 38 || e.getKeyCode() == 87)
            this.up = true;

        if (e.getKeyCode() == 40 || e.getKeyCode() == 83)
            this.down = true;

        if (e.getKeyCode() == 37 || e.getKeyCode() == 65)
            this.left = true;

        if (e.getKeyCode() == 39 || e.getKeyCode() == 68)
            this.right = true;

        if (e.getKeyCode() == 32)
            this.space = true;

        if (e.getKeyCode() == 27)
            this.esc = true;
    }

    @Override
    public void keyReleased(final KeyEvent e)
    {
        if (e.getKeyCode() == 38 || e.getKeyCode() == 87)
            this.up = false;

        if (e.getKeyCode() == 40 || e.getKeyCode() == 83)
            this.down = false;

        if (e.getKeyCode() == 37 || e.getKeyCode() == 65)
            this.left = false;

        if (e.getKeyCode() == 39 || e.getKeyCode() == 68)
            this.right = false;

        if (e.getKeyCode() == 32)
            this.space = false;

        if (e.getKeyCode() == 27)
        {
            this.esc = false;
            this.lock = true;
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {}
}
