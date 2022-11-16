
package com.xamser7.lb.states;

import java.awt.Graphics2D;

import com.xamser7.lb.Game;
import com.xamser7.lb.Main;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.gfx.Painter;
import com.xamser7.lb.ui.ClickListener;
import com.xamser7.lb.ui.UIButton;

public class MenuState extends States
{
    private States thisMain;

    public MenuState(final Main main)
    {
        super(main);
        this.thisMain = this;
        
        this.ui.addObject(new UIButton(main, 45.0f, 115.0f, 0.5, false, Assets.play, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new GameState(main));
            }
        }));
        this.ui.addObject(new UIButton(main, 45.0f, 167.0f, 0.5, false, Assets.shop, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new ShopState(main));
            }
        }));
        this.ui.addObject(new UIButton(main, 45.0f, 219.0f, 0.5, false, Assets.options, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new OptionsState(main, MenuState.this.thisMain));
            }
        }));
        this.ui.addObject(new UIButton(main, 45.0f, 271.0f, 0.5, false, Assets.stats, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new StatState(main));
            }
        }));
        this.ui.addObject(new UIButton(main, 45.0f, 323.0f, 0.5, false, Assets.quit, new ClickListener()
        {
            @Override
            public void onClick()
            {
                main.stop();
            }
        }));
    }

    @Override
    public void update()
    {
        this.ui.update();
    }

    @Override
    public void render(final Graphics2D g)
    {
        Painter.drawBackground(g, MenuState.main, Assets.menu, 100.0f);
        Game.showLevel(MenuState.main, g);
        Game.showMoney(MenuState.main, g);
        this.ui.render(g);
    }
}
