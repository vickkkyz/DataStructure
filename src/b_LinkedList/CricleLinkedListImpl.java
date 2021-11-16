package b_LinkedList;

/**
 * @Author qq
 * @Date 2021/11/16
 */

//单向循环链表
public class CricleLinkedListImpl<E> implements LinkedList<E>{
    private int size;
    private Node<E> first;
    private static class Node<E>{
        E element;
        Node<E> next;
        public Node(E element, Node<E> next){
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
        Node<E> node = first;
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
        if(index == 0){
            Node<E> node;
            if(size == 0){//空链表
                node = new Node<>(element,null);
                node.next = node;
            }else{
                Node<E> last = getNode(size - 1);
                node = new Node<>(element,first);
                last.next = node;
            }
            first = node;
        }else{
            Node<E> prev = getNode(index - 1);
            Node<E> next = prev.next;
            Node<E> node = new Node<>(element,next);
            prev.next = node;
        }
        size ++;
    }

    private Node<E> getNode(int index){
        Node<E> node = first;
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
            node = first;
            Node<E> last = getNode(size - 1);
            last.next = node.next;
            first = node.next;
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
        Node<E> node = first;
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
        first = null;
    }

    @Override
    public int getIndex(E element) {
        Node<E> node = first;
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
        Node<E> node = first;
        sb.append("size="+size+" [");
        for(int i = 0; i < size; i++){
            if(i != 0){
                sb.append(",");
            }
            sb.append(node.element+"->"+node.next.element);
            node = node.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
