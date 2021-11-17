package a_ArrayList;

/**
 * @Author qq
 * @Date 2021/11/14
 */
public class ArrayListImpl<E> implements ArrayList<E> {
    private int size;//数组的元素个数
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;
    public ArrayListImpl(){
        elements = (E[]) new Object[DEFAULT_CAPACITY];

        //不能创建泛型数组，elements = new E[DEFAULT_CAPACITY];
        // 因为泛型在编译期间被擦除，普通的类型变量在未指定边界的情况下将被擦除成Object类型...
        //java编程思想里提到了这个问题  P386，也可以用下面的方法，但是我的报错Cannot resolve symbol 'newInstance'
        //elements = (E[]) new Array.newInstance(Class<E>,DEFAULT_CAPACITY);
    }
    public ArrayListImpl(int capacity){
        capacity = (capacity < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
        elements = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(E element) {
        return getIndex(element) != -1;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }
    private void rangeCheck(int index){
        if(index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException("index:"+index+",size:"+size);
        }
    }
    private void rangeCheckForAdd(int index){
        if(index < 0 || index > size){
            throw new ArrayIndexOutOfBoundsException("index:"+index+",size:"+size);
        }
    }
    @Override
    public void add(int index, E element) {
        //这个不需要index=size也抛出异常，因为当index=size时，说明是往数组的末尾添加元素
        rangeCheckForAdd(index);
        ensureCapacity(size);
        for(int i = size - 1; i >=index; i--){
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        size ++;
        return;
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
    @Override
    public void add(E element) {
        add(size,element);
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        if(getElements(index) == null){
            throw new IllegalArgumentException("index:"+index+"处元素不存在");
        }
        E element = getElements(index);
        //注意删除元素是将index后面的所有元素往前移动
        for(int i = index + 1; i < size; i++){
            elements[i - 1] = elements[i];
        }
        elements[size] = null;
        size--;
        trim();
        return element;
    }

    /**
     * 缩容操作
     */
    private void trim(){
        int capacity = elements.length;
        //System.out.println(capacity);
        int newCapacity = capacity >> 1;
        //当size大于等于将要缩小为的容量 或者新容量小于10的话，就不要再缩小啦！！

        //等于的情况是为了防止复杂度震荡，当size等于元素个数时，扩容，复杂度为O(n)
        // 然后remove在将这个元素删除，此时size=newCapacity，又会引发缩容，复杂度又是O(n),
        //如果反复持续这种情况，一直缩容扩容，就是复杂度震荡
        if(size >= newCapacity || newCapacity <= DEFAULT_CAPACITY) return;
        //缩容扩容的比例相乘不为1，就可以不写等号
        E newElements[] = (E[]) new Object[newCapacity];
        for(int i = 0; i < size; i++){
            newElements[i] = elements[i];
        }
        elements = newElements;
        //System.out.println("缩容  旧容量为:"+capacity+",新容量为:"+newCapacity);
    }
    @Override
    public E getElements(int index) {
        rangeCheck(index);
        E element = elements[index];
        return element;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++){
            elements[i] = null;//这里为啥要设置为null，直接size为0不可以吗？
        }
        size = 0;
    }

    @Override
    public int getIndex(E element) {
        if(element == null){
            for(int i = 0; i < size; i++){
                if(elements[i] == null) return i;
            }
        }else{
            for(int i = 0; i < size; i++){
                if(element.equals(elements[i])) return i;
            }
        }
        return -1;
    }
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("size="+size+",capacity="+elements.length+" [");
        for(int i = 0; i < size; i++){
            if(i != 0){
                sb.append(",");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
