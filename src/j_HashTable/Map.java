package j_HashTable;

/**
 * @Author qq
 * @Date 2021/11/30
 */
public interface Map<K,V> {
    int size();
    boolean isEmpty();
    boolean containsKey(K key);
    boolean containsValue(V value);
    V put(K key, V value);
    V get(K key);
    V remove(K key);
    void clear();
}
