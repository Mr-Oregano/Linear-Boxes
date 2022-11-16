
package com.xamser7.lb.assets;

import java.awt.Font;

public class FontLoader
{
    private static final String PATH = "/fonts/";

    public static Font loadFont(final String name, final float size, final int style)
    {
        try
        {
            return Font.createFont(0, FontLoader.class.getResourceAsStream(PATH + name)).deriveFont(style, size);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}
