package k_Heap;

import e_BST.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * @Author qq
 * @Date 2021/12/7
 */
//大根堆
public class BinaryHeap<E> implements Heap<E>, BinaryTreeInfo {
    private E[] elements;
    private int size;
    private Comparator comparator;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(E[] elements,Comparator comparator){
        this.comparator = comparator;
        if(elements == null || elements.length == 0){
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        }else{
            int capacity = elements.length < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : elements.length;
            this.elements = (E[]) new Object[capacity];
            for(int i = 0; i < elements.length; i++){
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }

    public BinaryHeap(Comparator comparator){
        this(null,comparator);
    }

    public BinaryHeap(){
        this(null,null);
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if(size == 0) return;
        for(int i = 0; i < size; i++){
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public E get() {
        IndexOut();
        return elements[0];
    }

    private void IndexOut(){
        if(size == 0) {
            throw new IndexOutOfBoundsException("数组为空");
        }
    }
    @Override
    public E remove() {
        IndexOut();
        E top = elements[0];
        elements[0] = elements[size - 1];
        elements[size - 1] = null;
        siftDown(0);
        size--;
        return top;
    }

    @Override
    public void add(E element) {
        illegalElement(element);
        ensureCapacity(size);
        if(size == 0){
            size++;
            elements[0] = element;
            return;
        }
        size++;
        elements[size - 1] = element;
        siftUp(size - 1);
    }

    @Override
    public E replace(E element) {
        illegalElement(element);
        E root = null;
        if(size ==0){
            elements[0] = element;
            size ++;
        }else{
            root =  elements[0];//保存原来的堆顶元素
            elements[0] = element;//新添加的元素
            siftDown(0);
        }
        return root;
    }

    /**
     * 判断是否需要扩容
     * @param size 当前的元素个数
     */
    private void ensureCapacity(int size){
        int capacity = elements.length;
        //当前元素个数小于数组容量，不用扩容
        if(size < capacity){
            return;
        }
        int newCapacity = capacity + ( capacity >> 1);//位运算，右移一位相当于乘1/2
        E NewElements[] = (E[]) new Object[newCapacity];
        for(int i = 0; i < size; i++){
            NewElements[i] = elements[i];
        }
        elements = NewElements;//elements指向NewElements
        //System.out.println("旧容量为:"+capacity+",新容量为:"+newCapacity);
    }

    //上浮index处的元素，使它满足二叉堆的性质
    private void siftUp(int index){
        illegalIndex(index);
        //只要是非根节点，都可以上浮
        E element = elements[index];
        while(index > 0){
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];
            if(compare(element,parent) < 0) break;
            elements[index] = parent;
            index = parentIndex;
        }
        elements[index] = element;
    }

    private int compare(E e1,E e2){
        return comparator != null ? comparator.compare(e1,e2) : ((Comparable)e1).compareTo(e2);
    }
    private void illegalIndex(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("数组下标不合法");
        }
    }
    private void illegalElement(E element){
        if(element == null){
            throw new IllegalArgumentException("element must be not null");
        }
    }

    //下沉index处的元素，使它满足二叉堆的性质
    private void siftDown(int index){
        illegalIndex(index);
        //第一个叶子节点的索引等于非叶子节点的数量
        //第一个叶子节点的索引是节点总数的一半
        int half = size >> 1;
        E element = elements[index];
        while(index < half){
            //1.只有左孩子
            int leftIndex = (index << 1) + 1; //左孩子索引
            E leftChild = elements[leftIndex];
            //2.有两个孩子
            int rightIndex = leftIndex + 1;
            if(rightIndex < size){
                E rightChild = elements[rightIndex];
                if(compare(rightChild,leftChild) > 0){
                    leftChild = rightChild;
                    leftIndex = rightIndex;
                }
            }
            if(compare(element,leftChild) > 0) break;
            elements[index] = leftChild;
            index = leftIndex;
        }
        elements[index] = element;
    }

    private void heapify(){
        int half = size >> 1;
        //自下而上
        for(int i = half - 1; i >= 0; i--){
            siftDown(i);
        }
    }

    @Override
    public Object root() {
        return 0;
    }
    //默认传进来的是索引
    @Override
    public Object left(Object node) {
        int index = (int)node;
        index = (index << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = (int)node;
        index = (index << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        int index = (int) node;
        return elements[index];
    }
}
