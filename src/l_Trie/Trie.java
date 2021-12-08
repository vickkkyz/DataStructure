package l_Trie;

/**
 * @Author qq
 * @Date 2021/12/8
 */
public interface Trie<V> {
    //字典树默认K为字符串
    int size();
    void clear();
    boolean isEmpty();
    boolean contains(String str);
    V add(String str,V value);
    V remove(String str);
    V get(String str);
    boolean starswith(String prefix);

}
