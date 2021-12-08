package l_Trie;

import j_HashTable.HashMap;

/**
 * @Author qq
 * @Date 2021/12/8
 */
public class TrieImpl<V> implements Trie<V> {
    private int size;
    private Node<V> root;

    private static class Node<V>{
        HashMap<Character,Node<V>> chlidern;
        Node<V> parent;
        Character character;
        boolean word;//是否是单词的最后一个字符
        V value;
        public Node(Node<V> parent){
            this.parent = parent;
        }
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(String str) {
        return node(str) != null && node(str).word;
    }

    @Override
    public V add(String str,V value) {
        CheckString(str);
        if(root == null){
            root = new Node<>(null);
        }
        int len = str.length();
        Node<V> node = root;
        for(int i = 0; i < len; i++) {
            char c = str.charAt(i);
            boolean empty = node.chlidern == null;
            //node没有孩子，就赋值为null，有孩子，就获取孩子的节点，但是孩子节点可能为null，因为不一定有c对应的孩子
            Node<V> chlidNode = empty ? null : node.chlidern.get(c);
            if(chlidNode == null){
                chlidNode = new Node<>(node);
                chlidNode.character = c;
                node.chlidern = empty ? new HashMap<>() : node.chlidern;
                node.chlidern.put(c,chlidNode);
            }
            node = chlidNode;
        }
        //已经有这个单词
        if(node.word){
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        size++;
        node.word = true;
        node.value = value;
        return null;
    }

    @Override
    public V remove(String str) {
        CheckString(str);
        CheckRoot(root);
        Node<V> node = node(str);
        if(!node.word || node == null) return null;

        size --;
        V oldValue = node.value;
        //有子节点
        if(node.chlidern != null && !node.chlidern.isEmpty()){
            node.word = false;
            node.value = null;
            return oldValue;
        }
        //无子节点
        Node<V> parent;
        while((parent = node.parent) != null){
            parent.chlidern.remove(node.character);//把这个孩子删除
            if(node.word || !parent.chlidern.isEmpty()) break;//parent还有孩子 或者是一个单词的结尾
            node = parent;
        }
        return oldValue;
    }

    private void CheckRoot(Node<V> root){
        if(root == null){
           throw new IllegalArgumentException("root is null");
        }
    }
    @Override
    public V get(String str) {
        return node(str) != null ? node(str).value : null;
    }

    @Override
    public boolean starswith(String prefix) {
        return node(prefix) != null;
    }

    //找到str的最后一个字符对应的节点
    private Node<V> node(String str){
        CheckString(str);
        int len = str.length();
        Node<V> node = root;
        if(node == null) return null;
        for(int i = 0; i < len; i++){
            char c = str.charAt(i);
            //node没有孩子或者node的孩子不是这个字符
            if(node.chlidern == null || node.chlidern.get(c) == null){
               return null;
            }
            node = node.chlidern.get(c);
        }
        return node;
    }

    private void CheckString(String str){
        if(str == null || str.length() == 0){
            throw new IllegalArgumentException("str must be not null");
        }
    }
}
