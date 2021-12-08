package l_Trie;

/**
 * @Author qq
 * @Date 2021/12/8
 */
public class test {
    public static void main(String args[]){
        TrieImpl<Integer> trie = new TrieImpl<>();
        trie.add("zhangsan",10);
        trie.add("lisi",20);
        trie.add("wangwu",30);
        trie.add("zhaoliu",40);
        trie.add("zhaohua",50);
        System.out.println(trie.contains("zhang"));
        System.out.println(trie.contains("zhangsan"));
        System.out.println(trie.starswith("zhao"));
        System.out.println(trie.get("zhaoliu"));
        System.out.println(trie.add("zhaoliu",100));
        System.out.println(trie.get("zhaoliu"));
        System.out.println(trie.remove("lisi"));
        System.out.println(trie.get("lisi"));
    }
}
