package datastructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 *     A segment tree over an given array of integers, supports different type of objective function
 * </p>
 * <p>
 *      Index 0 contains a dummy node.
 * </p>
 * <p>
 *     This implementation is not memory efficient, there should be a better way to pack all the nodes of the tree
 *     into an array that is tighter or something. (use a map or whatever)
 * </p>
 * <p>
 *     The shape of the tree is uniquely determine by how the intervals are splitted...
 * </p>
 *
 */
public class IntSegmentTree
{
    public static void main(String[] args)
    {

    }

    final int array_length;         // The original length of the array we are modeling using the tree.
    final int farr[];               // the function value over all the nodes
    Function<Integer[], Integer> f; // the function that applies recursively to the tree.

    // mapping the index of the element in the original array to index of the element in the tree array.
    protected Map<Integer, Integer> leafnode_posi = new HashMap<Integer, Integer>();

    /**
     * Provide a function and an array of integers to construct the Integer segment tree.
     * @param argin
     * The integer array we want to construct the segment tree on.
     * @param f
     * A function that accepts 2 integers, and it can compute for an array of element correctly using divide
     * and conquer.
     */
    public IntSegmentTree(int[] argin, Function<Integer[], Integer> f)
    {
        if (argin.length == 0) throw new RuntimeException("Array cannot be empty.");
        farr = new int[4*argin.length];
        array_length = argin.length;
        this.f = f;
        construct(1, new MyInterval(0, argin.length), argin);
    }

    public IntSegmentTree(int[] argin)
    {
        this(argin,(Integer[] arr) -> {return arr[0] + arr[1];});
    }

    /**
     * Find the value of a function "f" applied on an interval [a, b) on the array that this segment tree is contructed.
     * @param a The left boundary index of the interval, inclusive.
     * @param b The right boundary index of the interval, exclusive.
     * @return
     * The return value of the function applied on that interval.
     * @exception IndexOutOfBoundsException
     * Happens when the interval given has boundary that is larger than the range of the array.
     * @exception RuntimeException
     * Triggered by defected/singleton interval.
     */
    public int queryInterval(int a, int b)
    {
        if (a < 0 || b > array_length) throw new IndexOutOfBoundsException();
        if (a >= b) throw new RuntimeException("Defected interval nto allowed.");
        return queryInterval(1, new MyInterval(0, array_length), new MyInterval(a, b));
    }

    /**
     * Choose an element in the array and update them.
     * @param index
     *  The index of the element in the array.
     * @param value
     *  The new value for that element.
     */
    public void updateElementAt(int index, int value)
    {
        if (index < 0 || index >= farr.length) throw new ArrayIndexOutOfBoundsException();
        /*
            1. starts at the root node of that singleton interval for the index.
            2. update its parent from bottom to the top.
         */
        int Node = leafnode_posi.get(index);
        farr[Node] = value;
        while (Node != 1)
        {
            int ParentNode = getParent(Node);
            int LeftChildNode = getLeftChild(ParentNode);
            int RightChildNode = getRightChild(ParentNode);
            farr[ParentNode] = f.apply(new Integer[]{farr[LeftChildNode], farr[RightChildNode]});
            Node = ParentNode;
        }
    }

    protected int queryInterval(int node, MyInterval nodeInterval, MyInterval queryInterval)
    {

        if (nodeInterval.equals(queryInterval)){return farr [node];} // base case

        MyInterval LeftChildInterval = nodeInterval.splitLeft(), RightChildInterval = nodeInterval.splitRight();
        boolean SubintervalOfLeft = queryInterval.subsetOf(LeftChildInterval);
        boolean SubintervalOfRight = queryInterval.subsetOf(RightChildInterval);

        int ResultLeft = 0, ResultRight = 0;
        if (SubintervalOfLeft)
        {
            ResultLeft = queryInterval(getLeftChild(node), LeftChildInterval, queryInterval);
        }
        if (SubintervalOfRight)
        {
            ResultRight = queryInterval(getRightChild(node), RightChildInterval, queryInterval);
        }
        if (SubintervalOfLeft != SubintervalOfRight)
        {
            return SubintervalOfLeft? ResultLeft: ResultRight;
        }
        else // subinterval of both intervals, or neither: which is an error that is already thrown.
        {
            // Break the query interval.
            MyInterval LeftQueryInterval = new MyInterval(queryInterval.a, LeftChildInterval.b);
            MyInterval RightQueryInterval = new MyInterval(RightChildInterval.a, queryInterval.b);
            ResultLeft = queryInterval(getLeftChild(node), LeftChildInterval, LeftQueryInterval);
            ResultRight = queryInterval(getRightChild(node), RightChildInterval, RightQueryInterval);
        }
        return f.apply(new Integer[]{ResultLeft, ResultRight});
    }
    protected int getLeftChild(int node)
    {
        return 2*node;
    }
    protected int getRightChild(int node){return 2*node + 1; }
    protected int getParent(int node)
    {
        return node/2;
    }

    /**
     * Given the array we want to model, this construct the segment tree internal array.
     * @param node The index of the node that we are looking at recursively.
     * @param interval The interval of the current node.
     * @param array The array we are trying to put into the tree.
     */
    protected void construct(int node, MyInterval interval, int[] array)
    {
        if (interval.a + 1 >= interval.b)
        {
            farr[node] = array[interval.a];
            leafnode_posi.put(interval.a, node);
            return;
        }

        int LeftChildIndex = getLeftChild(node), RightChildIndex = getRightChild(node);
        construct (LeftChildIndex, interval.splitLeft(), array);
        construct (RightChildIndex, interval.splitRight(), array);
        farr[node] = f.apply(new Integer[]
                                {
                                    farr[LeftChildIndex],
                                    farr[RightChildIndex]
                                }
                            );
    }

    public int[] getTree()
    {
        return Arrays.copyOf(farr, farr.length);
    }

}


