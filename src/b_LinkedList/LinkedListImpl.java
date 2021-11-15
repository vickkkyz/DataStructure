package b_LinkedList;

/**
 * @Author qq
 * @Date 2021/11/15
 */
public class LinkedListImpl<E> implements LinkedList<E> {

    private int size;
    private Node<E> dummyHead = new Node<>(null,null);

    private static class Node<E>{
        E element;
        Node<E> next;
        public Node(E element,Node<E> next){
            this.element = element;
            this.next = next;
        }
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
        E old = getElements(index);
        Node<E> node = dummyHead.next;
        int count = 0;
        while (node != null){
            if(count == index){
                node.element = element;
                break;
            }
            node = node.next;
            count++;
        }
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        /*if(index == 0){
            Node<E> head = dummyHead.next;
            Node<E> node = new Node<>(element,head);
            dummyHead.next = node;
        }else{
            Node<E> prev = getNode(index - 1);
            Node<E> next = prev.next;
            Node<E> node = new Node<>(element,next);
            prev.next = node;
        }*/
        Node<E> prev = index == 0 ? dummyHead : getNode(index - 1);
        Node<E> next = prev.next;
        Node<E> node = new Node<>(element,next);
        prev.next = node;
        size ++;
    }

    private Node<E> getNode(int index){
        Node<E> node = dummyHead.next;
        for(int i = 0; i < index; i++){
            node = node.next;
        }
        return node;
    }
    @Override
    public void add(E element) {
        add(size,element);
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
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node;
        if(index == 0){
            node = dummyHead.next;
            dummyHead.next = dummyHead.next.next;
        }else{
            Node<E> prev = getNode(index - 1);
            node = prev.next;
            prev.next = node.next;
        }
        size --;
        return node.element;
    }

    @Override
    public E getElements(int index) {
        rangeCheck(index);
        Node<E> node = dummyHead.next;
        int count = 0;
        while(node != null){
            if(count == index){
                return node.element;
            }
            node = node.next;
            count++;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        dummyHead.next = null;
    }

    @Override
    public int getIndex(E element) {
        Node<E> node = dummyHead.next;
        if(element == null){
            int count = 0;
            while(node != null){
                if(node.element == null){
                    return count;
                }
                node = node.next;
                count ++;
            }
        }else{
            int count = 0;
            while(node != null){
                if(element.equals(node.element)){
                    return count;
                }
                node = node.next;
                count ++;
            }
        }
        return -1;
    }
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        Node<E> node = dummyHead.next;
        sb.append("size="+size+" [");
        for(int i = 0; i < size; i++){
            if(i != 0){
                sb.append(",");
            }
            sb.append(node.element);
            node = node.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
