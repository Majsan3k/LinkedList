//Maja Lund, maja.lund@live.se

package alda.linear;

/**
 * This is the list interface you should implement. It is a simplified version
 * of <a href="http://docs.oracle.com/javase/8/docs/api/java/util/List.html">
 * java .util.List</a> where you will find the documentation to the methods.
 * Note though that remove(element) and contains(element) takes a parameter of
 * type E, and not Object in this version. Also note that add(element) in the
 * original version returns a boolean which we ignore.
 *
 * Do NOT rename this interface!
 *
 * @author Henrik
 */
public interface ALDAList<E> extends Iterable<E> {

    public void add(E element);

    public void add(int index, E element);

    public E remove(int index);

    public boolean remove(E element);

    public E get(int index);

    public boolean contains(E element);

    public int indexOf(E element);

    public void clear();

    public int size();

}