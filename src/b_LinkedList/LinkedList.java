package b_LinkedList;

/**
 * @Author qq
 * @Date 2021/11/15
 */
public interface LinkedList<E> {
    /**
     * 返回链表的节点个数
     * @return
     */
    int size();

    /**
     * 判断链表中是否包含某个节点对应的元素
     * @param element
     * @return
     */
    boolean contains(E element);

    /**
     * 修改index处的元素
     * @param index
     * @param element 修改后的元素
     * @return 返回修改前的元素
     */
    E set(int index, E element);

    /**
     * 在index处插入节点
     * @param index
     * @param element
     */
    void add(int index, E element);

    /**
     * 在末尾添加节点
     * @param element
     */
    void add(E element);

    /**
     * 删除index处的节点
     * @param index
     * @return
     */
    E remove(int index);

    /**
     * 获取index处的节点
     * @param index
     * @return
     */
    E getElements(int index);

    /**
     * 判断链表是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 清空所有节点
     */
    void clear();

    /**
     * 获取节点索引
     * @param element
     * @return
     */
    int getIndex(E element);
}
