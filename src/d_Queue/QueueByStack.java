package d_Queue;

import c_Stack.Stack;
import c_Stack.StackImpl;

/**
 * @Author qq
 * @Date 2021/11/17
 */
public class QueueByStack<E> implements Queue<E>{
    //需要用到两个栈，一个保存入队的元素，一个保存出队的元素
    private Stack<E> inStack = new StackImpl<>();
    private Stack<E> outStack = new StackImpl<>();

    @Override
    public int size() {
        return inStack.size() + outStack.size();
    }

    @Override
    public E deQueue() {
        if(outStack.isEmpty()){
            while(!inStack.isEmpty()){
                outStack.push(inStack.pop());
            }
        }
        return outStack.pop();
    }

    @Override
    public void enQueue(E element) {
        inStack.push(element);
    }

    @Override
    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    @Override
    public void clear() {
        inStack.clear();
        outStack.clear();
    }

    @Override
    public E front() {
        if(outStack.isEmpty()){
            while(!inStack.isEmpty()){
                outStack.push(inStack.pop());
            }
        }
        return outStack.peek();
    }

    @Override
    public E rear() {
        return inStack.peek();
    }
}
