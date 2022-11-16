
package com.xamser7.lb;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;

import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.input.KeyManager;
import com.xamser7.lb.input.MouseManager;
import com.xamser7.lb.states.MenuState;
import com.xamser7.lb.states.OptionsState;
import com.xamser7.lb.states.States;

public class Main implements Runnable
{
    public static Main current;

    public static final int FPS = 60;
    public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final int GAME_WIDTH = 1367;
    public static final int GAME_HEIGHT = 765;

    private boolean refreshDisplay;
    private boolean refreshFullscreen;
    private boolean FullScreenFlag;
    private int width;
    private int height;
    private String title;

    private float wScale;
    private float hScale;
    private Frame window;
    private Canvas canvas;

    private Thread thread;
    private BufferStrategy b;
    private Graphics2D g;

    private KeyManager keyManager;
    private MouseManager mouseManager;

    private boolean run;
    private int Frame;

    public static void main(final String[] args)
    {
        final Main main = new Main("Linear Boxes  |  v0.2.5");
        Game.loadFiles(main);
        main.setFullscreen(Game.FullScreen && Game.WindowDimensions == 2, false);
        int width = 0;
        int height = 0;

        switch (Game.WindowDimensions)
        {
        case 0:
        {
            width = 1000;
            height = 558;
            OptionsState.disableFullscreenCheck = true;
            break;
        }
        case 1:
        {
            width = 1280;
            height = 720;
            OptionsState.disableFullscreenCheck = true;
            break;
        }
        case 2:
        {
            width = Main.SCREEN_WIDTH;
            height = Main.SCREEN_HEIGHT;
            break;

        }
        }

        main.setWindowSize(width, height, false);
        main.init();
        main.start();
    }

    public Main(final String title)
    {
        this.refreshDisplay = false;
        this.refreshFullscreen = false;
        this.FullScreenFlag = false;
        Main.current = this;
        this.title = title;

        this.window = new Frame(title);
        this.window.setResizable(false);
        this.window.setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
        this.window.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(final WindowEvent e)
            {
                Main.this.stop();
            }
        });

        this.canvas = new Canvas();
        this.canvas.setFocusable(false);
        this.canvas.setBackground(Color.BLACK);
        this.window.add(this.canvas);
        this.window.addKeyListener(this.keyManager = new KeyManager());
        this.window.addMouseListener(this.mouseManager = new MouseManager());
        this.window.addMouseMotionListener(this.mouseManager);
        this.canvas.addMouseListener(this.mouseManager);
        this.canvas.addMouseMotionListener(this.mouseManager);
    }

    private void update()
    {
        if (States.getState() != null)
        {
            States.getState().update();
        }
    }

    private void render()
    {
        this.b = this.canvas.getBufferStrategy();
        this.g = (Graphics2D) this.b.getDrawGraphics();

        if (Game.AllowInterpolation)
        {
            final RenderingHints rh = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            this.g.setRenderingHints(rh);
        }

        this.g.clearRect(0, 0, this.width, this.height);
        
        if (States.getState() != null)
        {
            States.getState().render(this.g);
        }

        this.b.show();
        this.g.dispose();
    }

    @Override
    public void run()
    {
        double delta = 0.0;
        final double tpu = 1_000_000_000 / FPS;
        long lastTime = System.nanoTime();

        while (this.run)
        {
            final long now = System.nanoTime();
            delta += (now - lastTime) / tpu;
            lastTime = now;
            
            while (delta >= 1.0)
            {
                if (this.refreshFullscreen)
                {
                    this.setFullscreen(this.FullScreenFlag, false);
                    this.refreshFullscreen = false;
                    if (!this.refreshDisplay)
                        this.setVisible(true);
                }

                if (this.refreshDisplay)
                {
                    this.setWindowSize(this.width, this.height, false);
                    while (!this.window.isValid());

                    this.refreshDisplay = false;
                    this.setVisible(true);
                }

                this.update();
                this.render();
                delta = 0.0;
                ++this.Frame;
            }
        }

        this.stop();
    }

    public void init()
    {
        this.canvas.createBufferStrategy(3);
        final SplashScreen splash = new SplashScreen(this.canvas);
        this.setVisible(true);
        splash.show();
        Assets.init();
        States.setState(new MenuState(this));
        splash.close();
    }

    public synchronized void start()
    {
        this.run = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    public synchronized void stop()
    {
        try
        {
            Game.saveFile();
            System.exit(0);
            this.thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getTitle()
    {
        return this.title;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public float getWScale()
    {
        return this.wScale;
    }

    public float getHScale()
    {
        return this.hScale;
    }

    public int getFrame()
    {
        return this.Frame;
    }

    public void setFrame(final int Frame)
    {
        this.Frame = Frame;
    }

    public boolean isRunning()
    {
        return this.run;
    }

    public void setRunning(final boolean run)
    {
        this.run = run;
    }

    public KeyManager getKeyManager()
    {
        return this.keyManager;
    }

    public MouseManager getMouseManager()
    {
        return this.mouseManager;
    }

    public void setWindowSize(final int width, final int height, final boolean postRefresh)
    {
        this.width = width;
        this.height = height;
        this.wScale = width / (float) GAME_WIDTH;
        this.hScale = height / (float) GAME_HEIGHT;

        if (postRefresh)
        {
            this.refreshDisplay = true;
            return;
        }
        
        this.window.setSize(width, height);
        this.window.setLocationRelativeTo(null);
        this.canvas.setPreferredSize(new Dimension(width, height));
        this.window.pack();
        this.canvas.createBufferStrategy(3);
    }

    public void setFullscreen(final boolean fullscreen, final boolean postRefresh)
    {
        if (postRefresh)
        {
            this.refreshFullscreen = true;
            this.FullScreenFlag = fullscreen;
            return;
        }
        if (this.window.isDisplayable())
        {
            this.window.dispose();
        }
        this.window.setLocationRelativeTo(null);
        this.window.setUndecorated(fullscreen);
    }

    public void setVisible(final boolean visible)
    {
        this.window.setVisible(visible);
    }

    public Graphics2D getGraphics()
    {
        return this.g;
    }
}
