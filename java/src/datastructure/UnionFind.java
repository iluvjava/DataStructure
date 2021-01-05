package datastructure;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Union find with path compression but without the tree ranking. The
 * representative will be anchored when joing 2 elements in the data structure.
 * </p>
 * Self loop is the root of the tree.
 */
public class UnionFind<T> {

    public static void main(String[] args)
    {
        System.out.println("This shit is running");
        UnionFind<Integer> Uf = new UnionFind<>();
        for (int I = 0; I < 5; I++)
        {
            Uf.add(I + 1);
        }
        Uf.join(2, 5); Uf.join(3, 4);
        TestTools.assertTrue(()-> {return Uf.get(2) == 5;});
        TestTools.assertTrue(()-> {return Uf.get(5) == 5;}); 
        TestTools.assertTrue(() -> {return Uf.get(3) == 4;}); 
        TestTools.assertTrue(()-> Uf.get(4) == 4);
        Uf.join(5, 1);
        TestTools.assertTrue(()-> Uf.get(2) == 1 && Uf.get(5) == 1);
        System.out.println("This shit finished running. ");

    }


    Map<T, T> Forest = new HashMap<>();

    /**
     * Add a new element to the UnionFind data structure. 
     * @param element
     */
    public void add(T element)
    {
        Forest.put(element, element);
    }
    
    /**
     * Join 2 of the element to one group, with a's representative representing the group for b.
     * @param a
     * @param b
     */
    public void join(T a, T b)
    {
        T ReprA = get(a);
        T ReprB = get(b);
        Forest.put(ReprA, ReprB);
        return;
    }

    /**
     * Get the representative of the given element. 
     * @param element
     * @return
     */
    public T get(T element)
    {
        if (Forest.get(element) == element)
        {
            return element;
        }
        T ElementRepresent = get(Forest.get(element));
        Forest.put(element, ElementRepresent);
        return ElementRepresent;
    }
}
