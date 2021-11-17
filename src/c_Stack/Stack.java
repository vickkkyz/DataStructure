package c_Stack;

/**
 * @Author qq
 * @Date 2021/11/17
 */
public interface Stack<E> {
    //栈的特性：入栈是添加到栈顶，出栈也是先出栈顶元素。因此是后进先出

    /**
     * 获取栈中元素个数
     * @return
     */
    int size();

    /**
     * 栈是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * （偷窥一下）栈顶元素
     * @return
     */
    E peek();

    /**
     * 出栈
     * @return
     */
    E pop();

    /**
     * 入栈
     * @return
     */
    void push(E element);

    void clear();
}
