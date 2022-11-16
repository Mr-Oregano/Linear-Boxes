
package com.xamser7.lb.entities;

import com.xamser7.lb.ui.UIStyleButton;
import com.xamser7.lb.gfx.Style;
import java.awt.Graphics2D;
import com.xamser7.lb.skins.Skin;
import com.xamser7.lb.Game;
import com.xamser7.lb.gfx.Painter;
import com.xamser7.lb.ui.UIButton;
import com.xamser7.lb.states.ShopState;
import com.xamser7.lb.ui.ClickListener;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.Main;
import com.xamser7.lb.ui.UIComponentPane;
import com.xamser7.lb.ui.UITextButton;
import com.xamser7.lb.states.States;

public class PlayerShop extends States
{
    private static int MoneyCost;
    private static int ExpCost;
    private static int RangeCost;
    private static int SpeedCost;
    private UITextButton upgrade1;
    private UITextButton upgrade2;
    private UITextButton upgrade3;
    private UITextButton upgrade4;
    private UIComponentPane skinPane;

    public PlayerShop(final Main main)
    {
        super(main);
        this.setMoneyCost();
        this.setExpCost();
        this.setRangeCost();
        this.setSpeedCost();
        
        this.ui.addObject(new UIButton(main, 68.0f, 52.0f, 0.15, false, Assets.back, new ClickListener()
        {
            @Override
            public void onClick()
            {
                ShopState.newWindow(new MainShop(main));
            }
        }));

        this.ui.addObject(this.upgrade1 = new UITextButton(main, "$" + Painter.Format(PlayerShop.MoneyCost, "#,###") + " | +1.3%", 853.0f, 302.0f, Assets.gemBlue, Assets.getFont(25), false, 100.0f, new ClickListener()
        {
            @Override
            public void onClick()
            {
                if (Game.Money >= PlayerShop.MoneyCost)
                {
                    Game.Money -= PlayerShop.MoneyCost;
                    Game.MoneyRate += 0.013;
                    ++Game.RateLevel;
                    PlayerShop.this.setMoneyCost();
                    PlayerShop.this.upgrade1.setString("$" + Painter.Format(PlayerShop.MoneyCost, "#,###") + " | +1.3%");
                }
            }
        }));

        this.ui.addObject(this.upgrade2 = new UITextButton(main, "$" + Painter.Format(PlayerShop.ExpCost, "#,###") + " | +0.15%", 853.0f, 334.0f, Assets.gemBlue, Assets.getFont(25), false, 100.0f, new ClickListener()
        {
            @Override
            public void onClick()
            {
                if (Game.Money >= PlayerShop.ExpCost)
                {
                    Game.Money -= PlayerShop.ExpCost;
                    Game.ExpRate += 0.0015;
                    PlayerShop.this.setExpCost();
                    PlayerShop.this.upgrade2.setString("$" + Painter.Format(PlayerShop.ExpCost, "#,###") + " | +0.15%");
                }
            }
        }));

        this.ui.addObject(this.upgrade3 = new UITextButton(main, (Game.Range < 200) ? ("$" + Painter.Format(PlayerShop.RangeCost, "#,###") + " | +1.00p") : "MAX", 853.0f, 366.0f, Assets.gemBlue, Assets.getFont(25), false, 100.0f, new ClickListener()
        {
            @Override
            public void onClick()
            {
                if (Game.Money >= PlayerShop.RangeCost)
                {
                    Game.Money -= PlayerShop.RangeCost;
                    ++Game.Range;
                    PlayerShop.this.setRangeCost();
                    PlayerShop.this.upgrade3.setString("$" + Painter.Format(PlayerShop.RangeCost, "#,###") + " | +1.00p");
                    if (Game.Range >= 300)
                    {
                        PlayerShop.this.upgrade3.setString("MAX");
                        PlayerShop.this.upgrade3.disabled(true);
                    }
                }
            }
        }));

        this.ui.addObject(this.upgrade4 = new UITextButton(main, (Game.PlayerSpeed < 16) ? ("$" + Painter.Format(PlayerShop.SpeedCost, "#,###") + " | +1.00ppf") : "MAX", 853.0f, 398.0f, Assets.gemBlue, Assets.getFont(25), false, 100.0f, new ClickListener()
        {
            @Override
            public void onClick()
            {
                if (Game.Money >= PlayerShop.SpeedCost)
                {
                    Game.Money -= PlayerShop.SpeedCost;
                    ++Game.PlayerSpeed;
                    PlayerShop.this.setSpeedCost();
                    PlayerShop.this.upgrade4.setString("$" + Painter.Format(PlayerShop.SpeedCost, "#,###") + " | +1.00ppf");
                    if (Game.PlayerSpeed >= 16)
                    {
                        PlayerShop.this.upgrade4.setString("MAX");
                        PlayerShop.this.upgrade4.disabled(true);
                    }
                }
            }
        }));

        if (Game.PlayerSpeed >= 16)
            this.upgrade4.disabled(true);

        if (Game.Range >= 300)
            this.upgrade3.disabled(true);

        this.ui.addObject(this.skinPane = new UIComponentPane(main, 283.0f, 482.0f, 900, 275, false));
        for (int i = 0; i < Skin.list.size(); ++i)
            this.setPlayerSkins(i);
    }

    @Override
    public void update()
    {
        this.ui.update();
    }

    @Override
    public void render(final Graphics2D g)
    {
        Painter.drawImage(g, 343.0f, 282.0f, 150, 150, Assets.skins[Skin.selected.getID()], 100.0f, false, true);
        Painter.drawString(g, "Money rate: " + Painter.Format(Game.MoneyRate, "#.##%"), 523, 302, Assets.algaeGreen, Assets.getFont(25), 100.0f, false, true);
        Painter.drawString(g, "Exp rate: " + Painter.Format(Game.ExpRate, "#.##%"), 523, 332, Assets.algaeGreen, Assets.getFont(25), 100.0f, false, true);
        Painter.drawString(g, "Bit pickup range: " + Painter.Format(Game.Range, "#,###") + "p", 523, 362, Assets.algaeGreen, Assets.getFont(25), 100.0f, false, true);
        Painter.drawString(g, "Player speed: " + Painter.Format(Game.PlayerSpeed, "#,###") + "ppf", 523, 392, Assets.algaeGreen, Assets.getFont(25), 100.0f, false, true);
        Painter.drawString(g, "Skins: ", 303, 467, Assets.algaeGreen, Assets.getFont(25), 100.0f, false, true);
        this.ui.render(g);
    }

    private void setPlayerSkins(final int ID)
    {
        final Style idleStyle = new Style(null, 100.0f, null, null);
        final Style hoverStyle = new Style(null, 50.0f, null, null);
        final Style disabledStyle = new Style(null, 30.0f, Assets.gemBlue, Assets.getFont(15));
        
        disabledStyle.text = "Lv " + Skin.list.get(ID).getUnlock();
        disabledStyle.overlay = Assets.lock_overlay;
        UIStyleButton skinButton = null;

        this.skinPane.add(skinButton = new UIStyleButton(PlayerShop.main, Assets.skins[ID], 0.0f, 0.0f, 75, 75, false, hoverStyle, idleStyle, disabledStyle, new ClickListener()
        {
            @Override
            public void onClick()
            {
                Skin.selected = Skin.list.get(ID);
            }
        }));

        if (!Skin.list.get(ID).isOwned())
            skinButton.disabled(true);
    }

    private void setMoneyCost()
    {
        PlayerShop.MoneyCost = (int) Math.pow(Game.RateLevel, 1.5) * 50;
    }

    private void setExpCost()
    {
        PlayerShop.ExpCost = (int) Math.pow((Game.ExpRate - 0.055) / 0.002 + 1.0, 2.0) * 75;
    }

    private void setRangeCost()
    {
        PlayerShop.RangeCost = (int) Math.pow(Game.Range - 65, 1.7) * 50 + 1000;
    }

    private void setSpeedCost()
    {
        PlayerShop.SpeedCost = (int) Math.pow(Game.PlayerSpeed - 5, 3.9) * 50 + 2000;
    }
}
