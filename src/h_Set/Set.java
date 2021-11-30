package h_Set;

/**
 * @Author qq
 * @Date 2021/11/30
 */
//不存放重复的元素
public interface Set<E> {
    int size();
    boolean contains(E element);
    void clear();
    boolean isEmpty();
    void add(E element);
    void remove(E element);

}
