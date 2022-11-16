
package com.xamser7.lb;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.xamser7.lb.assets.ImageLoader;

public class SplashScreen
{
    private BufferedImage image;
    private Canvas canvas;
    private Graphics2D g;
    private BufferStrategy b;

    public SplashScreen(final Canvas canvas)
    {
        this.image = ImageLoader.loadImage("splash.png");
        this.canvas = canvas;
        this.b = canvas.getBufferStrategy();
    }

    public void show()
    {
        float alpha = -20.0f;
        final double tpu = 1.6666666E7;
        long lastTime = System.nanoTime();

        while (alpha < 100.0f)
        {
            final long now = System.nanoTime();
            alpha += (float) ((now - lastTime) / tpu);
            lastTime = now;
            if (alpha < 0.0)
                continue;

            alpha = Math.min(alpha, 100.0f);
            this.g = (Graphics2D) this.b.getDrawGraphics();
            
            if (Game.AllowInterpolation)
            {
                final RenderingHints rh = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                this.g.setRenderingHints(rh);
            }

            this.g.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            this.displaySplash(alpha);
            this.b.show();
            this.g.dispose();
        }
    }

    public void close()
    {
        float alpha = 100.0f;
        final double tpu = 1.6666666E7;
        long lastTime = System.nanoTime();
        while (alpha > 0.0f)
        {
            final long now = System.nanoTime();
            alpha -= (float) ((now - lastTime) / tpu);
            lastTime = now;
            alpha = Math.max(alpha, 0.0f);
            this.g = (Graphics2D) this.b.getDrawGraphics();

            if (Game.AllowInterpolation)
            {
                final RenderingHints rh = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                this.g.setRenderingHints(rh);
            }
            
            this.g.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
            this.displaySplash(alpha);
            this.b.show();
            this.g.dispose();
        }
    }

    private void displaySplash(final float alpha)
    {
        this.g.setComposite(AlphaComposite.getInstance(3, alpha / 100.0f));
        this.g.drawImage(this.image, this.canvas.getWidth() - this.image.getWidth() >> 1, this.canvas.getHeight() - this.image.getHeight() >> 1, this.image.getWidth(), this.image.getHeight(), null);
    }
}
