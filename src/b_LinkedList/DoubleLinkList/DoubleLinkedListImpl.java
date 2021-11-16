package b_LinkedList.DoubleLinkList;

import b_LinkedList.LinkedList;

/**
 * @Author qq
 * @Date 2021/11/16
 */

//双向链表
public class DoubleLinkedListImpl<E> implements LinkedList<E> {
    private int size;
    private Node<E> first;
    private Node<E> last;//first last是在栈中的，指向堆空间中的节点

    private static class Node<E>{
        E element;
        Node<E> next;
        Node<E> prev;
        public Node(Node<E> prev,E element, Node<E> next){
            this.prev = prev;
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
        if(index < (size >> 1) ){
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
        }else{
            Node<E> node = last;
            int count = size - 1;
            while (node != null){
                if(count == index){
                    node.element = element;
                    break;
                }
                node = node.prev;
                count--;
            }
        }
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if(index == size){//往链表尾部添加节点
            Node<E> node = new Node<>(last,element,null);
            if(last == null){
                last = node;
            }else{
                last.next = node;
                last = node;//注意需要加上这句话！！！不然只有头和尾元素
            }
            if(first == null){
                first = node;
            }
        }else{
            Node<E> next = getNode(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(prev,element,next);
            if(prev == null){
                first = node;
            }else{
                prev.next = node;
            }
            if(next == null){
                last = node;
            }else{
                next.prev = node;
            }
        }

        size ++;
    }

    private Node<E> getNode(int index){
        rangeCheck(index);
        Node<E> node = first;
        if(index < (size >> 1)){
            for(int i = 0; i < index; i++){
                node = node.next;
            }
        }else{
            for(int i = size - 1; i > index; i--){
                node = node.prev;
            }
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
        Node<E> node = getNode(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if(prev == null){
            first = next;
            //prev.prev = null;
        }else{
            prev.next = next;
        }
        if(next == null){
            last = prev;
        }else{
            next.prev = prev;
        }
        size --;
        return node.element;
    }

    @Override
    public E getElements(int index) {
        return getNode(index).element;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
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
        for(int i = 0; i < size && node!= null; i++){
            if(i != 0){
                sb.append(", ");
            }
            if(node.prev == null){
                sb.append("null");
            }else{
                sb.append(node.prev.element);
            }
            sb.append("_");

            sb.append(node.element);
            sb.append("_");
            if(node.next == null){
                sb.append("null");
            }else{
                sb.append(node.next.element);
            }

            node = node.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
