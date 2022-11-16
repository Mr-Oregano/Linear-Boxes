
package com.xamser7.lb.assets;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    private BufferedImage sheet;

    public SpriteSheet(final BufferedImage sheet)
    {
        this.sheet = sheet;
    }

    public BufferedImage crop(final int x, final int y, final int width, final int height)
    {
        return this.sheet.getSubimage(x, y, width, height);
    }

    public BufferedImage getSheet()
    {
        return this.sheet;
    }
}
