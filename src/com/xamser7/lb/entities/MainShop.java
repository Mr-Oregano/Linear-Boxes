
package com.xamser7.lb.entities;

import java.awt.Graphics2D;
import com.xamser7.lb.states.ShopState;
import com.xamser7.lb.weapons.WeaponShop;
import com.xamser7.lb.ui.UIButton;
import com.xamser7.lb.ui.ClickListener;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.Main;
import com.xamser7.lb.states.States;

public class MainShop extends States
{
    public MainShop(final Main main)
    {
        super(main);
        this.ui.addObject(new UIButton(main, 68.0f, 52.0f, 0.15, false, Assets.back, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(States.getPrevState());
            }
        }));
        this.ui.addObject(new UIButton(main, 533.0f, 422.0f, 1.0, true, Assets.gunshop, new ClickListener()
        {
            @Override
            public void onClick()
            {
                ShopState.newWindow(new WeaponShop(main));
            }
        }));
        this.ui.addObject(new UIButton(main, 833.0f, 422.0f, 1.0, true, Assets.playershop, new ClickListener()
        {
            @Override
            public void onClick()
            {
                ShopState.newWindow(new PlayerShop(main));
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
        this.ui.render(g);
    }
}
