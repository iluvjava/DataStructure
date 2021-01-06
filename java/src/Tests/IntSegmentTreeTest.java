package Tests;

import datastructure.IntSegmentTree;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IntSegmentTreeTest
{
    @Test
    public void TestSettingUpTree1()
    {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        IntSegmentTree tree = new IntSegmentTree(arr);
        System.out.println("No errors are thrown when setting up the tree.");
        int[] TreeArray = tree.getTree();
        int[] Expected = {0, 15, 6, 9, 3, 3, 4, 5, 1, 2};
        boolean good = true;
        for (int II = 0; II < Expected.length; II++)
        {
            if (Expected[II] != TreeArray[II]) good = false;
        }
        assertTrue(good);

        System.out.println("The internal tree has been setted up correctly for a simple instance. ");
    }

    @Test
    public void TestSettingUpTree2()
    {
        int[] arr = new int[]{1, 1, 1, 1};
        IntSegmentTree tree = new IntSegmentTree(arr);
        System.out.println("No errors are thrown when setting up the tree.");
        int[] TreeArray = tree.getTree();
        int[] Expected = {0, 4, 2, 2, 1, 1, 1, 1};
        boolean good = true;
        for (int II = 0; II < Expected.length; II++)
        {
            if (Expected[II] != TreeArray[II]) good = false;
        }
        assertTrue(good);
        System.out.println("The internal tree has been setted up correctly for a simple instance. ");
        return;
    }

    @Test
    public void TestSettingUpTree3()
    {
        for (int II = 1; II <= 1024; II++)
        {
            int[] arr = new int[II];
            System.out.printf("Setting up for tree with array size: %d.\n", II);
            IntSegmentTree tree = new IntSegmentTree(arr);
        }
        System.out.println("Test ended. ");
    }

    @Test
    public void TestSimpleIntervalQuery1()
    {
        int[] arr = new int[]{1, 1, 1, 1};
        IntSegmentTree tree = new IntSegmentTree(arr);
        assertTrue(tree.queryInterval(0, 4) == 4);
        assertTrue(tree.queryInterval(0, 1) == 1);
    }

    @Test
    public void TestSimpleIntervalQuery2()
    {
        int[] arr = new int[]{1, 1, 1, 1, 1};
        IntSegmentTree tree = new IntSegmentTree(arr);
        assertTrue(tree.queryInterval(0, 4) == 4);
        assertTrue(tree.queryInterval(2, 4) == 2);
    }

    @Test
    public void TestComplexIntervalQuery()
    {
        for (int II = 1; II <= 256; II++)
        {
            int[] arr = new int[II];
            for (int JJ = 0; JJ < arr.length; JJ++) arr[JJ] = 1;
            System.out.printf("Setting up for tree with array size: %d.\n", II);
            IntSegmentTree tree = new IntSegmentTree(arr);
            for (int JJ = 0; JJ < arr.length; JJ++)
                for (int KK = JJ + 1; KK <= arr.length; KK++)
                {
                    assertTrue(tree.queryInterval(JJ, KK) == KK - JJ);
                }
        }
        System.out.println("Test ended.");

    }

    @Test
    public void TestExceptionForIntervalQuery()
    {
        int[] arr = new int[]{1, 1, 1, 1,1, 1};
        IntSegmentTree tree = new IntSegmentTree(arr);
        try
        {
            tree.queryInterval(0, 10);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Behavior Expected, passed.");
        }

    }


}
