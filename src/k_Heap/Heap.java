package k_Heap;

/**
 * @Author qq
 * @Date 2021/12/7
 */
public interface Heap<E> {
    int size();
    boolean isEmpty();
    void clear();
    E get();//获取堆顶元素
    E remove();//删除堆顶元素
    void add(E element);
    E replace(E element);//删除堆顶元素，同时添加一个新的元素,返回要删除的节点
}
