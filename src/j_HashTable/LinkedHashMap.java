package j_HashTable;

/**
 * @Author qq
 * @Date 2021/12/6
 */
public class LinkedHashMap<K,V> extends HashMap<K,V> implements Map<K,V>{
    LinkedNode<K,V> first;
    LinkedNode<K,V> last;

    private static class LinkedNode<K,V> extends Node<K,V>{
        LinkedNode<K,V> prev;
        LinkedNode<K,V> next;

        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }

    protected Node<K,V> createNode(K key, V value, Node<K,V> parent) {
        return new LinkedNode<K,V>(key,value,parent);
    }

    protected void addChain(Node<K,V> node){
        LinkedNode<K,V> linkedNode = (LinkedNode<K, V>) node;
        if(first == null){
            first = linkedNode;
        }else{
            linkedNode.prev = last;
        }
        if(last == null){
            last = linkedNode;
        }else{
            last.next = linkedNode;
            last = linkedNode;
        }
    }

    /**
     * @param willNode 将要被删除的节点
     * @param removeNode 实际被删除的节点
     */
    protected void removeChain(Node<K,V> willNode,Node<K,V> removeNode){
        LinkedNode<K,V> node1 = (LinkedNode<K, V>) willNode;
        LinkedNode<K,V> node2 = (LinkedNode<K, V>) removeNode;
        if(node1 != node2){//删除的是度为2的节点,需要先交换节点位置，因为实际删除和想要删除的节点不同
            LinkedNode<K,V> prev1 = node1.prev;
            LinkedNode<K,V> next1 = node1.next;
            LinkedNode<K,V> prev2 = node2.prev;
            LinkedNode<K,V> next2 = node2.next;
            //交换node1和node2的位置
            node1.next = next2;
            node1.prev = prev2;
            node2.next = next1;
            node2.prev = prev1;
            if(prev1 == null){
                first = node2;
            }else{
                prev1.next = node2;
            }
            if(next1 == null){
                last = node2;
            }else{
                next1.prev = node2;
            }

            if(prev2 == null){
                first = node1;
            }else{
                prev2.next = node1;
            }
            if(next2 == null){
                last = node1;
            }else{
                next2.prev = node1;
            }
        }
        LinkedNode<K,V> prev = node2.prev;
        LinkedNode<K,V> next = node2.next;
        if(prev == null){
            first = next;
        }else{
            prev.next = next;
        }
        if(next == null){
            last = prev;
        }else{
            next.prev = prev;
        }
    }
    public String toString(){
        StringBuffer sb = new StringBuffer();
        LinkedNode<K,V> node = first;
        while(node != null){
            sb.append("key:" + node.key + ",value:" + node.value + "\n");
            node = node.next;
        }
        return sb.toString();
    }

    public void clear(){
        super.clear();
        first = null;
        last = null;
    }

    public boolean containsKey(K key){
        return node(key) != null;
    }

    public boolean containsValue(V value){
        return nodeValue(value) != null;
    }

    private LinkedNode<K,V> nodeValue(V value){
        LinkedNode<K,V> node = first;
        while(node != null){
            if(node.value == value){
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private LinkedNode<K,V> node(K key){
        LinkedNode<K,V> node = first;
        while(node != null){
            if(node.key == key){
                return node;
            }
            node = node.next;
        }
        return null;
    }
}
