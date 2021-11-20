package e_BST;

import d_Queue.Queue;
import d_Queue.QueueImpl;
import e_BST.printer.BinaryTreeInfo;

import java.util.Comparator;


/**
 * @Author qq
 * @Date 2021/11/18
 */
//二叉树规定传入的E必须具有可比较性 BSTImpl<E extends Comparable> 即E必须实现了Comparable 规定比较方法
public class BSTImpl<E> implements BST<E>, BinaryTreeInfo {
    private int size;
    private Node<E> root;
    private Comparator<E> comparator;
    public BSTImpl(){
        this(null);
    }
    public BSTImpl(Comparator<E> comparator){
        this.comparator = comparator;
    }

    private static class Node<E>{
        E element;
        Node<E> parent;
        Node<E> left;
        Node<E> right;
        public Node(E element,Node<E> parent){
            this.element = element;
            this.parent = parent;
        }
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(E element) {
        if(root == null){//添加的是第一个节点
            root = new Node<>(element,null);
            return;
        }
        Node<E> node = root;
        Node<E> NodeParent = root;
        int res = 0;
        while(node != null){
            NodeParent = node;
            res = compare(element,node.element);
            if(res > 0){//往二叉树的右边加
                node = node.right;
            }else if(res < 0){
                node = node.left;
            }else{
                node.element = element;
                return;
            }
        }
        Node<E> newNode = new Node<>(element,NodeParent);
        if(res > 0){
            NodeParent.right = newNode;
        }else{
            NodeParent.left = newNode;
        }
    }

    private int compare(E e1,E e2){
        if(comparator != null){//传入比较器，用户可以自定义比较规则
            return comparator.compare(e1,e2);
        }
        //没有传比较器，就按照java官方的比较器，泛型E必须实现Comparable<E>接口，重写int compareTo(T var1);方法
        return ((Comparable<E>)e1).compareTo(e2);
//        Integer实现了Comparable<Integer>
//        public int compareTo(Integer anotherInteger) {
//            return compare(this.value, anotherInteger.value);
//        }
//        public static int compare(int x, int y) {
//            return x < y ? -1 : (x == y ? 0 : 1);
//        }
    }
    @Override
    public void remove(E element) {

    }

    /**
     * 前序遍历 递归
     */
    public void preOrder(){
        preOrder(root);
    }
    private void preOrder(Node<E> node){
        if(node == null){
            return;
        }
        System.out.println(node.element);
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 中序遍历 递归
     */
    public void inOrder(){
        inOrder(root);
    }
    private void inOrder(Node<E> node){
        if(node == null){
            return;
        }
        inOrder(node.left);
        System.out.println(node.element);
        inOrder(node.right);
    }

    /**
     * 后序遍历 递归
     */
    public void postOrder(){
        postOrder(root);
    }
    private void postOrder(Node<E> node){
        if(node == null){
            return;
        }
        preOrder(node.left);
        preOrder(node.right);
        System.out.println(node.element);
    }

    /**
     * 层序遍历 利用队列
     */
    public void levelOrder(){
        levelOrder(root);
    }
    private void levelOrder(Node<E> node){
        if(node == null){
            return;
        }
        Queue<Node<E>> queue = new QueueImpl<>();
        queue.enQueue(node);
        while(!queue.isEmpty()){
            Node<E> node1 = queue.deQueue();
            System.out.println(node1.element);
            if(node1.left != null){
                queue.enQueue(node1.left);
            }
            if(node1.right != null){
                queue.enQueue(node1.right);
            }
        }
    }
    @Override
    public void clear() {

    }

    @Override
    public boolean contains(E element) {
        return false;
    }

    /*
    以下是打印二叉树.....根据老师的printer包
     */
    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>)node;
        String parentString = "null";
        if (myNode.parent != null) {
            parentString = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentString + ")";
    }

}
