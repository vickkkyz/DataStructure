package d_Queue;

import b_LinkedList.DoubleLinkList.DoubleLinkedListImpl;
import b_LinkedList.LinkedList;

/**
 * @Author qq
 * @Date 2021/11/17
 */
public class QueueImpl<E> implements Queue<E> {
    //先进先出，因此最好用双向链表
    //还有一种队列：双端队列，即可以在队首和队尾对元素进行操作...
    private LinkedList<E> list = new DoubleLinkedListImpl<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public E deQueue() {
        return list.remove(0);
    }

    @Override
    public void enQueue(E element) {
        list.add(element);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public E front() {
        return list.getElements(0);
    }

    @Override
    public E rear() {
        return list.getElements(list.size() - 1);
    }
}
