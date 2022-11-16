
package com.xamser7.lb.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.xamser7.lb.Game;
import com.xamser7.lb.Main;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.gfx.Painter;
import com.xamser7.lb.gfx.Style;
import com.xamser7.lb.ui.ClickListener;
import com.xamser7.lb.ui.UIButton;
import com.xamser7.lb.ui.UIDropDown;
import com.xamser7.lb.ui.UIFixedTextButton;

public class OptionsState extends States
{
    public static boolean disableFullscreenCheck = (Game.FullScreen && Game.WindowDimensions != 2);
    private UIDropDown setWindowDimensions;
    private UIButton checkFullscreen;
    private UIButton checkAntiAliasing;
    private UIButton checkInterpolation;

    public OptionsState(final Main main, final States lastValidState)
    {
        super(main);
        this.setWindowDimensions = null;
        this.checkFullscreen = null;
        this.checkAntiAliasing = null;
        this.checkInterpolation = null;

        this.ui.addObject(new UIButton(main, 68.0f, 52.0f, 0.15, false, Assets.back, new ClickListener()
        {
            @Override
            public void onClick()
            {
                try
                {
                    if (lastValidState != null)
                    {
                        States.setState((States) lastValidState.getClass().getDeclaredConstructor(Main.class).newInstance(main));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }));

        this.ui.addObject(this.checkFullscreen = new UIButton(main, 1030.0f, 200.0f, 0.15, false, Game.FullScreen ? Assets.checkboxEnabled : Assets.checkboxDisabled, new ClickListener()
        {
            @Override
            public void onClick()
            {
                final BufferedImage[] current = OptionsState.this.checkFullscreen.getImages();
                OptionsState.this.checkFullscreen.setImages((current == Assets.checkboxEnabled) ? Assets.checkboxDisabled : Assets.checkboxEnabled);
                Game.FullScreen = !Game.FullScreen;
                main.setFullscreen(Game.FullScreen, true);
                States.setState(new OptionsState(main, lastValidState));
            }
        }));

        if (OptionsState.disableFullscreenCheck)
            this.checkFullscreen.disabled(true);

        this.ui.addObject(this.checkAntiAliasing = new UIButton(main, 530.0f, 352.0f, 0.15, false, Game.AllowAntiAliasing ? Assets.checkboxEnabled : Assets.checkboxDisabled, new ClickListener()
        {
            @Override
            public void onClick()
            {
                final BufferedImage[] current = OptionsState.this.checkAntiAliasing.getImages();
                OptionsState.this.checkAntiAliasing.setImages((current == Assets.checkboxEnabled) ? Assets.checkboxDisabled : Assets.checkboxEnabled);
                Game.AllowAntiAliasing = !Game.AllowAntiAliasing;
            }
        }));

        this.ui.addObject(this.checkInterpolation = new UIButton(main, 530.0f, 412.0f, 0.15, false, Game.AllowInterpolation ? Assets.checkboxEnabled : Assets.checkboxDisabled, new ClickListener()
        {
            @Override
            public void onClick()
            {
                final BufferedImage[] current = OptionsState.this.checkInterpolation.getImages();
                OptionsState.this.checkInterpolation.setImages((current == Assets.checkboxEnabled) ? Assets.checkboxDisabled : Assets.checkboxEnabled);
                Game.AllowInterpolation = !Game.AllowInterpolation;
            }
        }));
        
        this.ui.addObject(this.setWindowDimensions = new UIDropDown(main, 480.0f, 202.0f, 0.4, false, Assets.dropdownMain));
        final Style hoverStyle = new Style(Assets.dropdownItem[1], 100.0f, Assets.darkGem, Assets.getFont(25));
        final Style idleStyle = new Style(Assets.dropdownItem[0], 100.0f, Assets.gemBlue, Assets.getFont(25));
        final UIFixedTextButton button1 = new UIFixedTextButton(main, "1000x558", 0.0f, 0.0f, 0, 40, false, hoverStyle, idleStyle, new ClickListener()
        {
            @Override
            public void onClick()
            {
                if (Game.WindowDimensions == 0)
                {
                    return;
                }
                OptionsState.this.setWindowDimensions.setSelected(0);
                main.setWindowSize(1000, 558, true);
                main.setFullscreen(false, true);
                OptionsState.disableFullscreenCheck = true;
                Game.WindowDimensions = 0;
                States.setState(new OptionsState(main, lastValidState));
                main.setVisible(true);
            }
        });

        final UIFixedTextButton button2 = new UIFixedTextButton(main, "1280x720", 0.0f, 0.0f, 0, 40, false, hoverStyle, idleStyle, new ClickListener()
        {
            @Override
            public void onClick()
            {
                if (Game.WindowDimensions == 1)
                {
                    return;
                }
                OptionsState.this.setWindowDimensions.setSelected(1);
                main.setWindowSize(1280, 720, true);
                main.setFullscreen(false, true);
                OptionsState.disableFullscreenCheck = true;
                Game.WindowDimensions = 1;
                States.setState(new OptionsState(main, lastValidState));
                main.setVisible(true);
            }
        });

        final UIFixedTextButton button3 = new UIFixedTextButton(main, String.valueOf(Main.SCREEN_WIDTH) + "x" + Main.SCREEN_HEIGHT, 0.0f, 0.0f, 0, 40, false, hoverStyle, idleStyle, new ClickListener()
        {
            @Override
            public void onClick()
            {
                if (Game.WindowDimensions == 2)
                {
                    return;
                }
                OptionsState.this.setWindowDimensions.setSelected(2);
                main.setWindowSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, true);
                main.setFullscreen(Game.FullScreen, true);
                OptionsState.this.checkFullscreen.disabled(false);
                OptionsState.disableFullscreenCheck = false;
                Game.WindowDimensions = 2;
                States.setState(new OptionsState(main, lastValidState));
                main.setVisible(true);
            }
        });
        
        this.setWindowDimensions.addItem(button1);
        this.setWindowDimensions.addItem(button2);
        this.setWindowDimensions.addItem(button3);
        this.setWindowDimensions.setSelected(Game.WindowDimensions);
    }

    @Override
    public void update()
    {
        this.ui.update();
    }

    @Override
    public void render(final Graphics2D g)
    {
        Painter.drawBackground(g, OptionsState.main, Assets.options_back, 100.0f);
        Painter.drawString(g, "Window Dimensions:", 200, 227, Assets.gemBlue, Assets.getFont(25), 100.0f, false, true);
        Painter.drawString(g, "Full Screen:", 850, 227, Assets.gemBlue, Assets.getFont(25), (float) (OptionsState.disableFullscreenCheck ? 50 : 100), false, true);
        Painter.drawString(g, "Enable text Anti-aliasing:", 200, 379, Assets.gemBlue, Assets.getFont(25), 100.0f, false, true);
        Painter.drawString(g, "*Disable for slower devices", 210, 397, Color.RED, Assets.getFont(15), 100.0f, false, true);
        Painter.drawString(g, "Enable image interpolation:", 200, 440, Assets.gemBlue, Assets.getFont(25), 100.0f, false, true);
        Painter.drawString(g, "*Disable for slower devices", 210, 458, Color.RED, Assets.getFont(15), 100.0f, false, true);
        this.ui.render(g);
    }
}
