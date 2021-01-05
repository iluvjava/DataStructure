package datastructure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is for testing stuff.
 */
public class TestTools {

    public static double medianFind(int i, int j, List<Integer> arr) 
    {
        List<Integer> Section = IntStream.range(i, j).mapToObj((k) -> arr.get(k)).collect(Collectors.toList());
        Collections.sort(Section);
        int Size = Section.size();
        double M1 = Section.get(Size / 2 - 1), M2 = Section.get(Size / 2);
        return Size % 2 == 0 ? (M1 + M2 + 0.0) / 2 : M2;
    }

    public static List<Integer> getShuffled(int n, int maxElement) 
    {
        List<Integer> Ans = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Ans.add((int) (Math.random() * maxElement));
        }
        Collections.shuffle(Ans);
        return Ans;
    }

    /**
     * Pass in a callable and assert whether it returns true.
     * @param callee
     */
    public static void assertTrue(Callable<Boolean> callee) {
        try 
        {
            boolean b = callee.call();
            if (!b) throw new AssertionError();
        } 
        catch(AssertionError ae)
        {
            ae.printStackTrace();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
    }
}