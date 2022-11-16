
package com.xamser7.lb.gfx;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import com.xamser7.lb.Game;
import com.xamser7.lb.Main;

public class Painter
{
    private static DecimalFormat formatter;
    public static AffineTransform origTransform;

    public static void drawImage(final Graphics2D g, float x, float y, int width, int height, final BufferedImage image, final float alpha, final boolean center, final boolean normalize)
    {
        if (normalize)
        {
            x = (float) Math.round(x * Main.current.getWScale());
            y = (float) Math.round(y * Main.current.getHScale());
            width = Math.round(width * Main.current.getWScale());
            height = Math.round(height * Main.current.getHScale());
        }

        if (center)
        {
            x -= width / 2;
            y -= height / 2;
        }

        g.setComposite(AlphaComposite.getInstance(3, alpha / 100.0f));
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }

    public static void drawBackground(final Graphics2D g, final Main main, final BufferedImage image, final float alpha)
    {
        g.setComposite(AlphaComposite.getInstance(3, alpha / 100.0f));
        g.drawImage(image, 0, 0, main.getWidth(), main.getHeight(), null);
    }

    public static void fillRect(final Graphics2D g, int x, int y, int width, int height, final Color c, final float alpha, final boolean center, final boolean normalize)
    {
        if (normalize)
        {
            x = Math.round(x * Main.current.getWScale());
            y = Math.round(y * Main.current.getHScale());
            width = Math.round(width * Main.current.getWScale());
            height = Math.round(height * Main.current.getHScale());
        }

        if (center)
        {
            x -= width / 2;
            y -= height / 2;
        }

        g.setColor(c);
        g.setComposite(AlphaComposite.getInstance(3, alpha / 100.0f));
        g.fillRect(x, y, width, height);
    }

    public static void drawString(final Graphics2D g, final String text, int x, int y, final Color c, final Font font, final float alpha, final boolean center, final boolean normalize)
    {
        if (normalize)
        {
            x = Math.round(x * Main.current.getWScale());
            y = Math.round(y * Main.current.getHScale());
        }

        if (Game.AllowAntiAliasing)
        {
            final RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHints(rh);
        }

        g.setColor(c);
        g.setFont(font);
        g.setComposite(AlphaComposite.getInstance(3, alpha / 100.0f));
        final FontMetrics fm = g.getFontMetrics(font);
        
        if (center)
        {
            x -= fm.stringWidth(text) / 2;
            y = y - fm.getHeight() / 2 + fm.getAscent();
        }

        y -= fm.getHeight();
        String[] split;
        
        for (int length = (split = text.split("\n")).length, i = 0; i < length; ++i)
        {
            final String line = split[i];
            g.drawString(line, x, y += fm.getHeight());
        }
        
        if (Game.AllowAntiAliasing)
        {
            final RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHints(rh2);
        }
    }

    public static int getTextWidth(final Main main, final String text, final Font f)
    {
        final FontMetrics fm = main.getGraphics().getFontMetrics(f);
        return Math.round(fm.stringWidth(text) / Main.current.getWScale());
    }

    public static int getTextHeight(final Main main, final Font f)
    {
        final FontMetrics fm = main.getGraphics().getFontMetrics(f);
        return fm.getHeight();
    }

    public static String Format(final double number, final String format)
    {
        Painter.formatter = new DecimalFormat(format);
        return Painter.formatter.format(number);
    }

    public static void setTransform(final Graphics2D g)
    {
        Painter.origTransform = g.getTransform();
    }

    public static void resetTransform(final Graphics2D g)
    {
        g.setTransform(Painter.origTransform);
    }
}
