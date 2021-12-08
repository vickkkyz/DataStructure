package k_Heap;

import e_BST.printer.BinaryTrees;

import java.util.Comparator;

/**
 * @Author qq
 * @Date 2021/12/8
 */
//优先队列，将优先级高的先出队，因此可以利用堆来实现
public class PriorityQueue<E> {
    private BinaryHeap<E> heap;
    private Comparator<E> comparator;

    public PriorityQueue(Comparator<E> comparator){
        heap = new BinaryHeap<>();
        this.comparator = comparator;
    }

    public PriorityQueue(){
        this(null);
    }

    public int size(){
        return heap.size();
    }
    public boolean isEmpty(){
        return heap.isEmpty();
    }
    public void clear(){
       heap.clear();
    }
    public void enQueue(E element){
        heap.add(element);
    }
    public E deQueue(){
        return heap.remove();
    }
    //获取对头元素
    public E getFront(){
        return heap.get();
    }
}
