package datastructure;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Models an generic array, with a generic divide and conquer function
 * @param <T>
 *     Just any paramterized type, that is.
 */
public class GenericSegTree<T>
{
    protected Map<MyInterval, T> farr;
    protected Function<Tuple<T>, T> f;
}

/**
 * Tuple is for modeling the generic lambada function used internally for the segment tree.
 * @param <T>
 */
class Tuple<T>
{
    public T a, b;

}
