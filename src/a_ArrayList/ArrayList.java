package a_ArrayList;

/**
 * @Author qq
 * @Date 2021/11/14
 */
public interface ArrayList<E> {
    /**
     * 返回数字的元素个数
     * @return
     */
    int size();

    /**
     * 判断数组中是否包含某个元素
     * @param element
     * @return
     */
    boolean contains(E element);

    /**
     * 修改数组index处的元素
     * @param index
     * @param element 修改后的元素
     * @return 返回修改前的元素
     */
    E set(int index, E element);

    /**
     * 在index处插入元素
     * @param index
     * @param element
     */
    void add(int index, E element);

    /**
     * 在数组末尾添加元素
     * @param element
     */
    void add(E element);

    /**
     * 删除index处的元素
     * @param index
     * @return
     */
    E remove(int index);

    /**
     * 获取index处的元素
     * @param index
     * @return
     */
    E getElements(int index);

    /**
     * 判断数组是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 清空所有元素
     */
    void clear();

    /**
     * 获取元素索引
     * @param element
     * @return
     */
    int getIndex(E element);

}
