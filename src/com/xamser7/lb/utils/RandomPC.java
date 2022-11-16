
package com.xamser7.lb.utils;

public class RandomPC
{
    public static int nextBoolean(final double... pc)
    {
        final int rand = Utils.RANDOM.nextInt(10000);

        for (int i = 0; i < pc.length; ++i)
        {
            final int n = i;
            final double n2 = pc[n] + ((i > 0) ? pc[i - 1] : 0.0);
            pc[n] = n2;

            if (n2 * 100.0 > rand)
                return i;
        }
        
        return pc.length;
    }
}
