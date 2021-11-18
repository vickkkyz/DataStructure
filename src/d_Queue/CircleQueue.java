package d_Queue;

import a_ArrayList.ArrayListImpl;

/**
 * @Author qq
 * @Date 2021/11/17
 */
public class CircleQueue<E> implements Queue<E>{
    //循环队列，用数组来实现
    private int front;//指示队列的头部
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements = (E[]) new Object[DEFAULT_CAPACITY];
    @Override
    public int size() {
        return size;
    }

    @Override
    public E deQueue() {
        E element = elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size --;
        return element;
    }

    @Override
    public void enQueue(E element) {
        ensureCapacity(size);
        //int index = size + front
        //index = index - (index >= elements.length ? elements.length : 0);
        elements[(size + front)% elements.length] = element;
        size ++;
    }
    private void ensureCapacity(int size){
        if(size < elements.length) return;
        int newCapacity = DEFAULT_CAPACITY + (DEFAULT_CAPACITY >> 1);
        E[] newEelement = (E[]) new Object[newCapacity];
        for(int i = 0; i < size; i++){
            newEelement[i] = elements[(front + i)%elements.length];
        }
        elements = newEelement;
        front = 0;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for(int i = 0; i < size; i++){
            elements[(front + i) % elements.length] = null;
        }
        size = 0;
        front = 0;
    }

    @Override
    public E front() {
        return elements[front];
    }

    @Override
    public E rear() {
        return elements[(front + size)%elements.length - 1];
    }
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("size:" + size + " capacity:" + elements.length + " front:" + front + " [");
        for(int i = 0; i < elements.length; i++){
            if(i != 0){
                sb.append(",");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
