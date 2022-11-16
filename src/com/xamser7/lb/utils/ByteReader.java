
package com.xamser7.lb.utils;

import java.io.IOException;
import java.io.DataInputStream;

public class ByteReader
{
    private ByteReader() {}

    public static short read_uint8(final DataInputStream stream)
    {
        try
        {
            final byte value = stream.readByte();
            return unsigned(value);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static byte read_int8(final DataInputStream stream)
    {
        try
        {
            final byte value = stream.readByte();
            return value;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static int read_uint16(final DataInputStream stream)
    {
        final byte[] buffer = byte_buffer(stream, 2);
        short value = 0;

        for (int i = 0; i < buffer.length; ++i)
            value |= (short) (unsigned(buffer[i]) << (buffer.length - i - 1) * 8);

        return unsigned(value);
    }

    public static short read_int16(final DataInputStream stream)
    {
        final byte[] buffer = byte_buffer(stream, 2);
        short value = 0;

        for (int i = 0; i < buffer.length; ++i)
            value |= (short) (unsigned(buffer[i]) << (buffer.length - i - 1) * 8);

        return value;
    }

    public static long read_uint32(final DataInputStream stream)
    {
        final byte[] buffer = byte_buffer(stream, 4);
        int value = 0;
        
        for (int i = 0; i < buffer.length; ++i)
            value |= unsigned(buffer[i]) << (buffer.length - i - 1) * 8;

        return unsigned(value);
    }

    public static int read_int32(final DataInputStream stream)
    {
        final byte[] buffer = byte_buffer(stream, 4);
        int value = 0;
        for (int i = 0; i < buffer.length; ++i)
            value |= unsigned(buffer[i]) << (buffer.length - i - 1) * 8;

        return value;
    }

    public static long read_int64(final DataInputStream stream)
    {
        final byte[] buffer = byte_buffer(stream, 8);
        long value = 0L;
        for (int i = 0; i < buffer.length; ++i)
            value |= (long) unsigned(buffer[i]) << (buffer.length - i - 1) * 8;

        return value;
    }

    public static String read_string(final DataInputStream stream, final int length)
    {
        final byte[] buffer = byte_buffer(stream, length);
        return new String(buffer);
    }

    public static float read_float(final DataInputStream stream)
    {
        return Float.intBitsToFloat(read_int32(stream));
    }

    public static double read_double(final DataInputStream stream)
    {
        return Double.longBitsToDouble(read_int64(stream));
    }

    public static short unsigned(final byte value)
    {
        if (value > -1)
            return value;

        return (short) (value + 256);
    }

    public static int unsigned(final short value)
    {
        if (value > -1)
            return value;

        return value + 65536;
    }

    public static long unsigned(final int value)
    {
        if (value > -1)
            return value;

        return value + 4294967296L;
    }

    private static byte[] byte_buffer(final DataInputStream stream, final int size)
    {
        try
        {
            final byte[] buffer = new byte[size];
            for (int i = 0; i < size; ++i)
                buffer[i] = stream.readByte();

            return buffer;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
