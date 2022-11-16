
package com.xamser7.lb.gfx;

import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Style
{
    public BufferedImage background;
    public BufferedImage overlay;
    public String text;
    public float alpha;
    public Color color;
    public Font font;

    public Style(final BufferedImage background, final float alpha, final Color color, final Font font)
    {
        this.background = background;
        this.alpha = alpha;
        this.color = color;
        this.font = font;
    }
}
