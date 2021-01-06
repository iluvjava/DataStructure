package datastructure;

import java.util.Objects;

/**
 * <p>
 *     A support class for the segment tree class, it's used to represent an interval
 *     for each of the node, and because array is discrete, it's going to be represented using integers.
 * </p>
 *
 *
 */
class MyInterval
{
    final int a, b;

    /**
     *
     * @param a left boundary, inclusive.
     * @param b right boundary, exclusive.
     */
    public MyInterval(int a, int b)
    {
        this.a = a; this.b = b;
    }

    public MyInterval splitLeft()
    {
        if ((a + b) % 2 == 1)
        return new MyInterval(a, (a + b)/2 + 1);
        else
            return new MyInterval(a, (a + b)/2);
    }

    public MyInterval splitRight()
    {
        if ((a+b)%2 == 1)
        return new MyInterval((a + b)/ 2 + 1, b);
        else
            return new MyInterval((a + b)/2, b);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyInterval that = (MyInterval) o;
        return a == that.a && b == that.b;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(a, b);
    }

    public boolean subsetOf(MyInterval anotherInterval)
    {
        return this.a >= anotherInterval.a && this.b <= anotherInterval.b;
    }
}

