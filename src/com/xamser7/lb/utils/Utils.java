
package com.xamser7.lb.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Utils
{
    public static final Random RANDOM = new Random();

    public static String loadFileAsString(final String name)
    {
        final StringBuilder s = new StringBuilder();

        try
        {
            final BufferedReader br = new BufferedReader(new FileReader(name));
            String line;
            while ((line = br.readLine()) != null)
                s.append(String.valueOf(line) + "\n");
                
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        
        return s.toString();
    }

    public static int parseInt(final String number)
    {
        try
        {
            return Integer.parseInt(number);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static File newFile(final File file)
    {
        try
        {
            file.createNewFile();
            return file;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void QuietClose(final Closeable closeable)
    {
        try
        {
            closeable.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String parseString(final boolean b)
    {
        return b ? "true" : "false";
    }

    public static boolean parseBoolean(final String bit)
    {
        try
        {
            return Boolean.parseBoolean(bit);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static double parseDouble(final String number)
    {
        try
        {
            return Double.parseDouble(number);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0.0;
        }
    }
}
