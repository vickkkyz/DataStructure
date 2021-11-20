package e_BST;

/**
 * @Author qq
 * @Date 2021/11/18
 */
public interface BST<E> {
    int size();
    boolean isEmpty();
    void add(E element);
    void remove(E element);
    void clear();
    boolean contains(E element);
}
