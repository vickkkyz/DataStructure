package i_Map;

/**
 * @Author qq
 * @Date 2021/11/30
 */
//红黑树实现map
public class MapImpl<K,V> implements Map<K,V> {

    RBTree<K,V> rbTree = new RBTree<>();
    @Override
    public int size() {
        return rbTree.size();
    }

    @Override
    public boolean isEmpty() {
        return rbTree.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        return rbTree.contains(key);
    }

    @Override
    public boolean containsValue(V value) {
        return rbTree.containsValue(value);
    }

    @Override
    public V put(K key, V value) {
        return rbTree.put(key,value);
    }

    @Override
    public V get(K key) {
        return rbTree.getV(key);
    }

    @Override
    public V remove(K key) {
        return rbTree.remove(key);
    }

    @Override
    public void clear() {
        rbTree.clear();
    }
    public String toString(){
        return rbTree.toString();
    }
}
