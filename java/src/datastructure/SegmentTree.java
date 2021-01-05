package datastructure;

import java.util.function.Function;

/**
 * <p>
 *     A segment tree over an given array of integers, supports different type of objective function
 * </p>
 *
 *
 */
public class SegmentTree
{
    public static void main(String[] args)
    {

    }

    final int arr[];
    Function<MyTuple, Integer> f;

    public SegmentTree(int[] argin)
    {
        arr = argin;
    }

    public int queryInterval(int a, int b)
    {
        throw new RuntimeException("Not Yet Implemented");
    }

    public void changeElementAt(int a)
    {
        if (a < 0 || a >= arr.length) throw new ArrayIndexOutOfBoundsException();
    }

    protected int getChild(int a)
    {
        throw new RuntimeException("Not Yet Implemented");
    }
    protected int getParent(int a)
    {
        throw new RuntimeException("Not Yet Implemented");
    }

}


