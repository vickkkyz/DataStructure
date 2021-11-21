package e_BST;

import c_Stack.Stack;
import c_Stack.StackImpl;
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
        checkelementnotnull(element);
        Node<E> node = getNode(element);
        if(node == null) return;
        //删除的是度为2的节点，要找到它的前驱节点（或者它的后继结点），然后将前驱结点的值赋值到这个要删除的节点，然后将前驱节点删除
        //前驱节点一定是度为1或者0的节点
        if(hasTwoChildern(node)){
            Node<E> prenode = predecessor(element);
            node.element = prenode.element;
            node = prenode;//现在node是prenode,要把node删了
        }
        //删除的是度为1的节点
        Node<E> replacement = node.left != null ? node.left : node.right;
        if(node.parent != null){
            if(replacement != null){//度为1
                replacement.parent = node.parent;
                if(node.parent.left == node){
                    node.parent.left = replacement;
                }else if(node.parent.right == node){
                    node.parent.right = replacement;
                }
            }else{//度为0
                if(node.parent.left == node){
                    node.parent.left = null;
                }else if(node.parent.right == node){
                    node.parent.right = null;
                }
            }
        }else{//删除的是根结点
            if(replacement == null){//度为0
                root = null;
            }else{//度为1
                if(node.left != null){
                    root = node.left;
                    node.left.parent = null;
                }else if(node.right != null){
                    root = node.right;
                    node.right.parent = null;
                }
            }
        }
    }

    private boolean hasTwoChildern(Node<E> node){
        if(node.right != null && node.left != null){
            return true;
        }
        return false;
    }
    /**
     * 寻找node的前驱节点  比node小的第一个节点
     */
    public Node<E> predecessor(E element){
        //左子树的最大节点
        checkelementnotnull(element);
        Node<E> node = getNode(element);
        if(node == null) return null;
        if(node.left != null){
            Node<E> node1 = node.left;
            while(node1.right != null){
                node1 = node1.right;
            }
            return node1;//左子树中的最右边的结点
        }
        //node没有左子树
        //如果node.parent==null 说明它没有前驱节点，想象没有左子树的根节点。
        // 反之，需要找到node.parent.parent... 最终node在它父亲结点的右子树中，结束遍历，说明找到了第一个比最开始node小的那个节点，即node.parent，
        // 因为如果node一直在父亲结点的左子树的话，父亲结点的值比node大，这不是前驱节点了
        while(node.parent != null && node.parent.left == node){
            node = node.parent;
        }
        return node.parent;
    }
    /**
     * 寻找node的后继节点  比node大的第一个节点
     */
    public Node<E> successor(E element){
        //右子树的最小节点
        checkelementnotnull(element);
        Node<E> node = getNode(element);
        if(node == null) return null;
        if(node.right != null){
            Node<E> node1 = node.right;
             while(node1.left != null){
                node1 = node1.left;
            }
            return node1;//左子树中的最右边的结点
        }
        //node没有右子树
        while(node.parent != null && node.parent.right == node){
            node = node.parent;
        }
        return node.parent;
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
     * 前序遍历 非递归
     */
    public void nonRecursivePre(){
        nonRecursivePre(root);
    }
    private void nonRecursivePre(Node<E> node){
        if(node == null){
            return;
        }
        Stack<Node<E>> stack = new StackImpl<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node<E> node1 = stack.pop();
            System.out.println(node1.element);
            if(node1.right != null){
                stack.push(node1.right);
            }
            if(node1.left != null){
                stack.push(node1.left);
            }
        }
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
     * 中序非递归
     */
//    public void nonRecursiveIn(){
//        nonRecursiveIn(root);
//    }
//    private void nonRecursiveIn(Node<E> node){
//        if(node == null){
//            return;
//        }
//
//    }
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
        size = 0;
        root = null;
    }

    @Override
    public boolean contains(E element) {
        return getNode(element) != null;
    }

    //根据element获取这个结点
    private Node<E> getNode(E element){
        checkelementnotnull(element);
        Node<E> node = root;
        while(node != null){
            int cmp = compare(element,node.element);
            if(cmp > 0){
                node = node.right;
            }else if(cmp < 0){
                node = node.left;
            }else{
                return node;
            }
        }
        return null;
    }
    private void checkelementnotnull(E element){
        if(element == null){
            throw new IllegalArgumentException("element must be not null");
        }
    }

    public E getElement(Node<E> node){
        if(node == null){
            return null;
        }
        return node.element;
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
