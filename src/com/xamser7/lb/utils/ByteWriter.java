
package com.xamser7.lb.utils;

import java.io.IOException;
import java.io.DataOutputStream;

public class ByteWriter
{
    private ByteWriter()
    {}

    public static void write_int8(final DataOutputStream stream, final byte value)
    {
        try
        {
            stream.writeByte(value);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void write_int16(final DataOutputStream stream, final short value)
    {
        try
        {
            stream.writeShort(value);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void write_int32(final DataOutputStream stream, final int value)
    {
        try
        {
            stream.writeInt(value);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void write_int64(final DataOutputStream stream, final long value)
    {
        try
        {
            stream.writeLong(value);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void write_string(final DataOutputStream stream, final String value)
    {
        try
        {
            stream.writeBytes(value);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void write_float(final DataOutputStream stream, final float value)
    {
        write_int32(stream, Float.floatToIntBits(value));
    }

    public static void write_double(final DataOutputStream stream, final double value)
    {
        write_int64(stream, Double.doubleToLongBits(value));
    }
}
