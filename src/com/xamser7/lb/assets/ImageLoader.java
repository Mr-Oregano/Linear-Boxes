
package com.xamser7.lb.assets;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageLoader
{
    private static final String PATH = "/textures/";

    public static BufferedImage loadImage(final String name)
    {
        try
        {
            return ImageIO.read(ImageLoader.class.getResourceAsStream(PATH + name));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}
