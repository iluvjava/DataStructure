package datastructure;
import java.util.List;
import java.util.TreeMap;
import java.lang.System;

public class MedianKeeper2 {

    public static void main(String[] args)
    {
        {
            int[] arr = {1, 4, 3, 2, 5, 4, 3, 2, 1, 5};
            MedianKeeper2 Keeper = new MedianKeeper2();
            for (int I = 0; I < arr.length; I++)
            {
                Keeper.add(arr[I]);
            }
            System.out.println("This is the median: ");
            System.out.println(Keeper.getMedian());
            System.out.println(Keeper);
        }

        {
            
            int K = 100;
            while (K-- >= 10)
            {
                List<Integer> RandomShuffled = TestTools.getShuffled((int)1e+4, 500);
                MedianKeeper2 Keeper = new MedianKeeper2();
                for (int I = 0; I < K; I++)
                {
                    Keeper.add(RandomShuffled.get(I));
                }
                for (int I = 0; I < RandomShuffled.size() - K; I++)
                {
                    double Median1 = Keeper.getMedian();
                    double Median2 = TestTools.medianFind(I, I + K, RandomShuffled);
                    // System.out.println("Median1 is: " + Median1);
                    // System.out.println("Median2 is: " + Median2);
                    if (Median2 != Median1)
                    {
                        System.out.println("The answers different");
                        throw new RuntimeException();
                    }
                    Keeper.remove(RandomShuffled.get(I));
                    Keeper.add(RandomShuffled.get(I + K));
                }
            }
        }

        {

            int[] arr = new int[]{-1, -2, -3};
            MedianKeeper2 Keeper = new MedianKeeper2();
            for (int I = 0; I < arr.length; I++)
            {
                Keeper.add(arr[I]);
                System.out.println(Keeper.getMedian());
            }
        }
    }

    int Larger;
    int LessThanOrEqual;
    TreeMap<Integer, Integer> Min = new TreeMap<>();
    TreeMap<Integer, Integer> Max = new TreeMap<>();

    public double getMedian()
    {
        int Size = Larger + LessThanOrEqual;
        if (Larger + LessThanOrEqual == 1)
        {
            return Min.lastKey();
        }
        long M1 = Min.lastKey(), M2 = Max.firstKey();
        return Size % 2 == 0? (M1 + M2 + 0.0)/2 : M1;
    }

    protected void balanceItself()
    {
        int Total = LessThanOrEqual + Larger;
        
        while (LessThanOrEqual - (Total % 2) != Larger)
        {
            if (LessThanOrEqual - (Total % 2) > Larger)
            {
                int ToMove = Min.lastKey();
                removeFromMin(ToMove);
                addToMax(ToMove);
            }
            else
            {
                int ToMove = Max.firstKey();
                removeFromMax(ToMove);
                addToMin(ToMove);
            }
        }
    }

    public void add (int a)
    {
        int Total = Larger + LessThanOrEqual;
    
        if (Total == 0)
        {
            addToMin(a);
            return;
        }
        else
        {
            int MinTop = Min.lastKey();
            if (a > MinTop)
            {
                addToMax(a);
            }
            else
            {
                addToMin(a);    
            }
        }
        balanceItself();
    }

    public void remove(int a)
    {
        if (Min.containsKey(a))
        {
            removeFromMin(a);
            balanceItself();
            return;
        }
        
        if (Max.containsKey(a))
        {
            removeFromMax(a);
            balanceItself();
            return;
        }
        throw new IllegalArgumentException("Can't remove something that is not present");
    }

    protected void addToMin(int a)
    {
        addToMap(Min, a);
        LessThanOrEqual++;
    }

    protected void addToMax(int a)
    {
        addToMap(Max, a);
        Larger++;
    }

    protected void removeFromMin(int a)
    {
        removeFromMap(Min, a);
        LessThanOrEqual--;
    }

    protected void removeFromMax(int a)
    {
        removeFromMap(Max, a);
        Larger--;
    }

    public static<T> void addToMap(TreeMap<T, Integer> treeMap, T stuffToAdd)
    {
        if (treeMap.containsKey(stuffToAdd))
        {
            treeMap.put(stuffToAdd, treeMap.get(stuffToAdd) + 1);
        }
        else
        {
            treeMap.put(stuffToAdd, 1);
        }
    }

    public static <T> void removeFromMap(TreeMap<T, Integer> treeMap, T stuffToRemove)
    {
        if (treeMap.get(stuffToRemove) == 1)
        {
            treeMap.remove(stuffToRemove);
        }
        else
        {
            treeMap.put(stuffToRemove, treeMap.get(stuffToRemove) - 1);
        }

    }

    public String toString()
    {
        StringBuilder Sb = new StringBuilder();
        Sb.append(Min.toString());
        Sb.append("\n");
        Sb.append(Max.toString());
        return Sb.toString();
    }
    
}
