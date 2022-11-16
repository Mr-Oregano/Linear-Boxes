
package com.xamser7.lb.states;

import com.xamser7.lb.gfx.Painter;
import java.awt.Color;
import java.awt.Graphics2D;
import com.xamser7.lb.ui.UIButton;
import com.xamser7.lb.ui.ClickListener;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.Main;

public class PauseState extends States
{
    public PauseState(final Main main)
    {
        super(main);
        this.ui.addObject(new UIButton(main, 683.0f, 330.0f, 0.5, true, Assets.resume, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(States.getPrevState());
            }
        }));

        this.ui.addObject(new UIButton(main, 683.0f, 382.0f, 0.5, true, Assets.newgame, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new GameState(main));
            }
        }));
        
        this.ui.addObject(new UIButton(main, 683.0f, 434.0f, 0.5, true, Assets.mainmenu, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new MenuState(main));
            }
        }));
    }

    @Override
    public void update()
    {
        this.ui.update();
        if (PauseState.main.getKeyManager().esc && PauseState.main.getKeyManager().lock)
        {
            PauseState.main.getKeyManager().lock = false;
            States.setState(States.getPrevState());
        }
    }

    @Override
    public void render(final Graphics2D g)
    {
        States.getPrevState().render(g);
        Painter.fillRect(g, 0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT, Color.BLACK, 75.0f, false, true);
        this.ui.render(g);
    }
}
