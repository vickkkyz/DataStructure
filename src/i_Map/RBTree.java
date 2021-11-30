package i_Map;

import d_Queue.Queue;
import d_Queue.QueueImpl;
import g_RBTree.BSTImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * @Author qq
 * @Date 2021/11/30
 */
public class RBTree<K,V> {
    public static final boolean BLACK = true;
    public static final boolean RED = false;

    private int size;
    private Comparator<K> comparator;
    private Comparator<V> comparatorV;
    public RBTree(){
        this(null);
    }
    public RBTree(Comparator<K> comparator){
        this.comparator = comparator;
    }

    private Node<K,V> root;
    private static class Node<K,V>{
        K key;
        V value;
        boolean color = RED;
        Node<K,V> parent;
        Node<K,V> left;
        Node<K,V> right;
        public Node(K key,V value,Node<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
        public String toString(){
            StringBuffer sb = new StringBuffer();
            sb.append("key:" + this.key);
            sb.append("value" + this.value);
            return sb.toString();
        }
    }

    protected Node<K,V> createNode(K key, V value,Node<K,V> parent) {
        return new Node<K,V>(key,value,parent);
    }
    
    public V put(K key,V value) {
        if(root == null){//添加的是第一个节点
            root = createNode(key,value,null);
            afterAdd(root);
            size ++;
            return value;
        }
        Node<K,V> node = root;
        Node<K,V> NodeParent = root;
        int res = 0;
        while(node != null){
            NodeParent = node;
            res = compare(key,node.key);
            if(res > 0){//往二叉树的右边加
                node = node.right;
            }else if(res < 0){
                node = node.left;
            }else{
                node.key = key;
                node.value = value;
                return value;
            }
        }
        Node<K,V> newNode = createNode(key,value,NodeParent);
        if(res > 0){
            NodeParent.right = newNode;
        }else{
            NodeParent.left = newNode;
        }
        size ++;
        afterAdd(newNode);
        return value;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    protected void afterAdd(Node<K,V> node){
        Node<K,V> parent = node.parent;
        if(parent == null){
            //添加的是根结点
            BLACKcolor(node);
            return;
        }
        if(isBlack(parent)){
            return;
        }
        //parent.color = red
        Node<K,V> uncle = sibling(parent);
        Node<K,V> grand = parent.parent;
        //node的uncle节点是红色
        if(isRed(uncle)){
            REDcolor(grand);
            BLACKcolor(parent);
            BLACKcolor(uncle);
            afterAdd(grand);
            return;
        }
        //node的uncle节点是黑色
        if(isLeftChild(parent)){//L
            if(isLeftChild(node)){//LL
                BLACKcolor(parent);
                rightRevolve(grand);
            }else{//LR
                BLACKcolor(node);
                leftRevolve(parent);
                rightRevolve(grand);
            }
            REDcolor(grand);
        }else{
            if(isLeftChild(node)){//RL
                BLACKcolor(node);
                rightRevolve(parent);
                leftRevolve(grand);

            }else{//RR
                BLACKcolor(parent);
                leftRevolve(grand);
            }
            REDcolor(grand);
        }
    }
    public boolean contains(K key) {
        return getNode(key) != null;
    }
    public boolean containsValue(V value) {
        return getNodeValue(value) != null;
    }

    private Node<K,V> getNodeValue(V value){
        checkelementnotnull(value);
        Node<K,V> node = root;
        while(node != null){
            int cmp = compareV(value,node.value);
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
    private Node<K,V> getNode(K key){
        checkelementnotnull(key);
        Node<K,V> node = root;
        while(node != null){
            int cmp = compare(key,node.key);
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
    protected void afterDelete(Node<K,V> node){
        //1.删除的是红色的节点，不会影响红黑树的性质 直接删
        //2.删除的是黑色节点，但是用于替代它的节点是红色（此时node是用于替代的节点）
        // 如果要删除的是度为2的节点，并且有2个红色的子节点，则它的前驱节点（子节点）的值会覆盖上去，然后真正删除的是它的前驱节点，红色叶子，不用管
        //如果要删除的是度为1的节点，则最后是它的子节点替代它，传进来的node其实是replacement，由于要维护红黑树的性质，如果它的子节点是红色，则染为黑色
        if(isRed(node)){
            BLACKcolor(node);
            return;
        }
        Node<K,V> parent = node.parent;
        //删除的是根结点
        if(parent == null) return;

        //3.删除的是黑色叶子节点，会产生下溢出，看它的兄弟节点能不能借给它节点
        //它的兄弟节点是红色 不能借
        boolean left = parent.left == null || isLeftChild(node);//afterDelete是删除之后的操作，node肯定是叶子节点，此时node.parent的孩子指针已经指向null，因此不能根据parent来判断node是左孩子还是右孩子
        //因此我们可以反向思考，如果parent的左子树为空，说明刚刚断开的就是左子树的那个指针，因此node就是左孩子
        Node<K,V> sibling = left ? parent.right : parent.left;
        if(left){//与右孩子的操作反向就可以
            if(isRed(sibling)){
                //兄弟不能借节点，那么让兄弟的儿子当做兄弟（它肯定是黑的） 借兄弟儿子的
                leftRevolve(parent);
                REDcolor(parent);
                BLACKcolor(sibling);
                sibling = parent.right;

            }
            //----------有一个问题，为什么sibling不会为null?--------
            //node的兄弟节点是黑色
            //sibling至少有一个红色节点
            if(isRed(sibling.left) || isRed(sibling.right)){
                if(isBlack(sibling.right) && isRed(sibling.left)){
                    if(isRed(parent)){//上去的节点的颜色要和原来parent的颜色一样
                        REDcolor(sibling.left);
                    }else{
                        BLACKcolor(sibling.left);
                    }
                    BLACKcolor(parent);
                    rightRevolve(sibling);
                    leftRevolve(parent);
                }else if(isRed(sibling.right) && isBlack(sibling.left)){
                    if(isRed(parent)){//上去的节点的颜色要和原来parent的颜色一样
                        REDcolor(sibling);
                    }else{
                        BLACKcolor(sibling);
                    }
                    BLACKcolor(parent);
                    BLACKcolor(sibling.right);
                    leftRevolve(parent);
                }else if(isRed(sibling.right) && isRed(sibling.left)){
                    if(isRed(parent)){//上去的节点的颜色要和原来parent的颜色一样
                        REDcolor(sibling);
                    }else{
                        BLACKcolor(sibling);
                    }
                    BLACKcolor(parent);
                    BLACKcolor(sibling.right);
                    leftRevolve(parent);
                }
            }else{//sibling是一个黑色叶子节点，父节点下来与子节点合并
                boolean pcolor = isBlack(parent);
                BLACKcolor(parent);
                REDcolor(sibling);
                if(pcolor){
                    afterDelete(parent);
                }

            }
        }else{//删除的节点是右孩子
            if(isRed(sibling)){
                //兄弟不能借节点，那么让兄弟的儿子当做兄弟（它肯定是黑的） 借兄弟儿子的
                rightRevolve(parent);
                REDcolor(parent);
                BLACKcolor(sibling);
                sibling = parent.left;
            }
            //node的兄弟节点是黑色
            //sibling至少有一个红色节点
            if(isRed(sibling.left) || isRed(sibling.right)){
                if(isBlack(sibling.left) && isRed(sibling.right)){
                    if(isRed(parent)){//上去的节点的颜色要和原来parent的颜色一样
                        REDcolor(sibling.right);
                    }else{
                        BLACKcolor(sibling.right);
                    }
                    BLACKcolor(parent);
                    leftRevolve(sibling);
                    rightRevolve(parent);
                }else if(isRed(sibling.left) && isBlack(sibling.right)){
                    if(isRed(parent)){//上去的节点的颜色要和原来parent的颜色一样
                        REDcolor(sibling);
                    }else{
                        BLACKcolor(sibling);
                    }
                    BLACKcolor(parent);
                    BLACKcolor(sibling.left);
                    rightRevolve(parent);
                }else if(isRed(sibling.left) && isRed(sibling.right)){
                    if(isRed(parent)){//上去的节点的颜色要和原来parent的颜色一样
                        REDcolor(sibling);
                    }else{
                        BLACKcolor(sibling);
                    }
                    BLACKcolor(parent);
                    BLACKcolor(sibling.left);
                    rightRevolve(parent);
                }
            }else{//sibling是一个黑色叶子节点，父节点下来与子节点合并
                boolean pcolor = isBlack(parent);
                BLACKcolor(parent);
                REDcolor(sibling);
                if(pcolor){
                    afterDelete(parent);
                }

            }
        }


    }
    private boolean hasTwoChildern(Node<K,V> node){
        if(node.right != null && node.left != null){
            return true;
        }
        return false;
    }
    /**
     * 寻找node的前驱节点  比node小的第一个节点
     */
    public Node<K,V> predecessor(K key){
        //左子树的最大节点
        checkelementnotnull(key);
        Node<K,V> node = getNode(key);
        if(node == null) return null;
        if(node.left != null){
            Node<K,V> node1 = node.left;
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
    public V remove(K key) {
        checkelementnotnull(key);
        Node<K,V> node = getNode(key);
        V oldvalue = node.value;
        if(node == null) return oldvalue;
        //删除的是度为2的节点，要找到它的前驱节点（或者它的后继结点），然后将前驱结点的值赋值到这个要删除的节点，然后将前驱节点删除
        //前驱节点一定是度为1或者0的节点
        if(hasTwoChildern(node)){
            Node<K,V> prenode = predecessor(key);
            node.key = prenode.key;
            node = prenode;//现在node是prenode,要把node删了
        }

        //删除的是度为1的节点
        Node<K,V> replacement = node.left != null ? node.left : node.right;
        if(node.parent != null){
            if(replacement != null){//度为1
                replacement.parent = node.parent;
                if(node.parent.left == node){
                    node.parent.left = replacement;
                }else if(node.parent.right == node){
                    node.parent.right = replacement;
                }
                size --;
                afterDelete(replacement);
            }else{//度为0
                if(node.parent.left == node){
                    node.parent.left = null;
                }else if(node.parent.right == node){
                    node.parent.right = null;
                }
                size --;
                afterDelete(node);
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
            size --;
            afterDelete(node);
        }
        return oldvalue;
    }

    public V getV(K key){
        return getNode(key).value;

    }
    List<Node<K,V>> list = new ArrayList<>();
    public String toString(){
        StringBuffer sb = new StringBuffer();
        inOrder(root);
        for(int i = 0; i < list.size(); i++){
            Node<K,V> node = list.get(i);
            K key = node.key;
            V value = node.value;
            sb.append("key:" + key + ",value:" + value);
            if(i != list.size() - 1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private void inOrder(Node<K,V> node){
        if(node == null){
            return;
        }
        inOrder(node.left);
        list.add(node);
        inOrder(node.right);
    }
    public void clear(){
        root = null;
        size = 0;
    }
    private void checkelementnotnull(Object key){
        if(key == null){
            throw new IllegalArgumentException("key must be not null");
        }
    }
    private int compare(K e1,K e2){
        if(comparator != null){//传入比较器，用户可以自定义比较规则
            return comparator.compare(e1,e2);
        }
        //没有传比较器，就按照java官方的比较器，泛型E必须实现Comparable<E>接口，重写int compareTo(T var1);方法
        return ((Comparable<K>)e1).compareTo(e2);
    }
    private int compareV(V e1,V e2){
        if(comparator != null){//传入比较器，用户可以自定义比较规则
            return comparatorV.compare(e1,e2);
        }
        //没有传比较器，就按照java官方的比较器，泛型E必须实现Comparable<E>接口，重写int compareTo(T var1);方法
        return ((Comparable<V>)e1).compareTo(e2);
    }
    /**
     * 左旋
     * @param grand 要旋转的节点
     *  parent grand的右孩子
     */
    private void leftRevolve(Node<K,V> grand){
        Node<K,V> grandParent = grand.parent;
        Node<K,V> parent = grand.right;
        Node<K,V> parentLeft = parent.left;
        grand.right = parentLeft;
        parent.left = grand;
        parent.parent = grandParent;
        if(grandParent != null){
            if(isLeftChild(grand)){
                grandParent.left = parent;
            }else if(isRightChild(grand)){
                grandParent.right = parent;
            }
        }else{
            root = parent;
        }
        if(parentLeft != null){
            parentLeft.parent = grand;
        }
        grand.parent = parent;
    }

    /**
     * 右旋
     * @param grand 要旋转的节点
     * parent grand的左孩子
     */
    private void rightRevolve(Node<K,V> grand){
        Node<K,V> grandParent = grand.parent;
        Node<K,V> parent= grand.left;
        Node<K,V> parentRight = parent.right;
        grand.left = parentRight;
        parent.right = grand;
        parent.parent = grandParent;
        if(grandParent != null){
            if(isLeftChild(grand)){
                grandParent.left = parent;
            }else if(isRightChild(grand)){
                grandParent.right = parent;
            }
        }else{
            root = parent;
        }
        if(parentRight != null){
            parentRight.parent = grand;
        }
        grand.parent = parent;
    }

    //返回该节点的颜色
    private boolean nodeColor(Node<K,V> node){
        return node == null ? BLACK : node.color;
    }
    private boolean isRed(Node<K,V> node){
        return nodeColor(node) == RED;
    }
    private boolean isBlack(Node<K,V> node){
        return nodeColor(node) == BLACK;
    }
    //给节点染色
    private Node<K,V> color(Node<K,V> node, boolean color){
        node.color = color;
        return node;
    }
    //染成红色
    private Node<K,V> REDcolor(Node<K,V> node){
        return color(node,RED);
    }
    private Node<K,V> BLACKcolor(Node<K,V> node){
        return color(node,BLACK);
    }
    //获取兄弟节点
    private Node<K,V> sibling(Node<K,V> node){
        Node<K,V> parent = node.parent;
        if(isLeftChild(node)){
            return parent.right;
        }else if(isRightChild(node)){
            return parent.left;
        }else{
            return null;
        }
    }
    private boolean isLeftChild(Node<K,V> node){
        return node.parent != null && node.parent.left == node;
    }
    private boolean isRightChild(Node<K,V> node){
        return node.parent != null && node.parent.right == node;
    }

}
