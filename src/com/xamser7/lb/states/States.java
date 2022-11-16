
package com.xamser7.lb.states;

import java.awt.Graphics2D;
import com.xamser7.lb.ui.UIManager;
import com.xamser7.lb.Main;

public abstract class States
{
    private static States currentState;
    private static States prevState;
    protected static Main main;
    protected UIManager ui;

    public States(final Main main)
    {
        States.main = main;
        this.ui = new UIManager(main);
    }

    public abstract void update();

    public abstract void render(final Graphics2D p0);

    public static States getState()
    {
        return States.currentState;
    }

    public static void setState(final States state)
    {
        if (state == States.prevState)
            States.main.getMouseManager().setUIManager(state.ui);

        States.prevState = States.currentState;
        States.currentState = state;
    }

    public static void setPrevState(final States state)
    {
        States.prevState = state;
    }

    public static States getPrevState()
    {
        return States.prevState;
    }

    public UIManager getUIManager()
    {
        return this.ui;
    }
}
