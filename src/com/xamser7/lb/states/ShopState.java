
package com.xamser7.lb.states;

import com.xamser7.lb.Game;
import com.xamser7.lb.gfx.Painter;
import com.xamser7.lb.assets.Assets;
import java.awt.Graphics2D;
import com.xamser7.lb.entities.MainShop;
import com.xamser7.lb.Main;

public class ShopState extends States
{
    private static States window;
    public static ShopState current;

    public ShopState(final Main main)
    {
        super(main);
        ShopState.current = this;
        newWindow(new MainShop(main));
    }

    @Override
    public void update()
    {
        ShopState.window.update();
    }

    @Override
    public void render(final Graphics2D g)
    {
        Painter.drawBackground(g, ShopState.main, Assets.shop_back, 100.0f);
        Game.showMoney(ShopState.main, g);
        Game.showLevel(ShopState.main, g);
        ShopState.window.render(g);
    }

    public static void newWindow(final States state)
    {
        ShopState.window = state;
    }
}
