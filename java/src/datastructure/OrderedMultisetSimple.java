package datastructure;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 
 * This class deal with a multiset, it aims to improve speed when there are a lot of multiples of the 
 * same elements in the sequence.
 */
public class OrderedMultisetSimple<T extends Comparable> {

    
    public static void main(String[] args)
    {
        System.out.println("This shit is running. ");
        int[] Sequence = new int[]{1, 2, 3, 3, 2, 3, 2, 1, 2, 3, 2, 1};
        List<Integer> Casted = IntStream.range(0, Sequence.length)
                                .mapToObj(I -> (Integer) Sequence[I])
                                .collect(Collectors.toList());
        OrderedMultisetSimple<Integer> MultiSet = new OrderedMultisetSimple<>(Casted);
        while (MultiSet.contains(3))
        {
            MultiSet.remove(3);
        }
        System.out.println(MultiSet);
        MultiSet.add(4, 4, 4, 4,5, 1);
        System.out.println(MultiSet);

    }


    protected TreeMap<T, Integer> FreqTable;

    public OrderedMultisetSimple(List<T> sequence)
    {
        FreqTable = sequence
                            .stream()
                            .collect(
                                Collectors.groupingBy(
                                    (e) -> e, 
                                    TreeMap::new,
                                    Collectors.summingInt(e-> 1)
                                )
                        );
    }

    public void add(T e)
    {
       FreqTable.put(e, FreqTable.getOrDefault(e, 0) + 1);
    }

    public void add(T... e)
    {
        for (T item :e)
        {
            add(item);
        }
    }

    public boolean contains(T e)
    {
        return FreqTable.containsKey(e);
    }

    public void remove(T e)
    {
        if (!FreqTable.containsKey(e)) throw new RuntimeException();
        if (FreqTable.get(e) == 1)
        {
            FreqTable.remove(e);
        }
        else
        {
            FreqTable.put(e, FreqTable.get(e) - 1);
        }
    }

    public int frequencyOf(T e)
    {
        return 0;
    }

    @Override
    public String toString()
    {
        return FreqTable.toString();
    }
    
}
