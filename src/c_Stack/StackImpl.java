package c_Stack;

import a_ArrayList.ArrayList;
import a_ArrayList.ArrayListImpl;

/**
 * @Author qq
 * @Date 2021/11/17
 */
public class StackImpl<E> implements Stack<E> {
    //可以用动态数组、链表来模拟
    private ArrayList<E> list = new ArrayListImpl<>();
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public E peek() {
        return list.getElements(list.size() - 1);
    }

    @Override
    public E pop() {
        return list.remove(list.size() - 1);
    }

    @Override
    public void push(E element) {
        list.add(element);
    }

    @Override
    public void clear() {
        list.clear();
    }


}
