package d_Queue;

/**
 * @Author qq
 * @Date 2021/11/17
 */
public interface Queue<E> {
    int size();

    /**
     * 出队
     * @return
     */
    E deQueue();

    /**
     * 入队
     */
    void enQueue(E element);

    boolean isEmpty();

    void clear();

    /**
     * 查看队首元素
     * @return
     */
    E front();

    /**
     * 查看队尾元素
     * @return
     */
    E rear();
}
