package j_HashTable;

import d_Queue.QueueImpl;
import e_BST.printer.BinaryTreeInfo;
import e_BST.printer.BinaryTrees;

import java.util.Objects;

/**
 * @Author qq
 * @Date 2021/12/2
 */
public class HashMap<K,V> implements Map<K,V> {
    private int size;//HashTable中的元素的个数，而不是索引对应的数组的尺寸
    private Node[] table;
    public static final boolean BLACK = true;
    public static final boolean RED = false;
    private static final int DEFATE_CAPACITY = 1 << 4;

    public HashMap(){
        table = new Node[DEFATE_CAPACITY];
    }

    private static class Node<K,V>{
        K key;
        V value;
        int hash;
        boolean color = RED;
        Node<K,V> parent;
        Node<K,V> left;
        Node<K,V> right;
        public Node(K key, V value, Node<K,V> parent) {
            this.key = key;
            this.value = value;
            hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
            this.parent = parent;
        }
        public String toString(){
            StringBuffer sb = new StringBuffer();
            sb.append(this.key);
            sb.append("_" + this.value);
            return sb.toString();
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
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;
        QueueImpl<Node<K,V>> queue = new QueueImpl<>();
        for(int i = 0; i < table.length; i++){
            if (table[i] == null) continue;
            Node<K,V> node = table[i];
            queue.enQueue(node);
            while(!queue.isEmpty()){
                Node<K,V> node1 = queue.deQueue();
//                if(node1.value == value){
//                    return true;
//                }
                if(Objects.equals(value, node1.value)){
                    return true;
                }
                if(node1.left != null){
                    queue.enQueue(node.left);
                }
                if(node1.right != null){
                    queue.enQueue(node.right);
                }
            }
        }
        return false;
    }

    //V为如果要添加的值在原来的哈希表里面有，就覆盖它，并返回被覆盖的node的value
    @Override
    public V put(K key, V value) {
        Node<K,V> root = table[index(key)];
        if(root == null){
            table[index(key)] = new Node<K,V>(key,value,null);
            afterAdd(table[index(key)]);//维护红黑树
            size ++;
            return null;
        }
        int h1 = key == null ? 0 : key.hashCode();
        h1 = h1 ^ (h1 >>> 16);
        Node<K,V> node = root;
        Node<K,V> NodeParent = root;
        K k1 = key;
        int res = 0;
        boolean searched = false; // 是否已经搜索过这个key
        Node<K,V> resNode = null;
        while(node != null){
            K k2 = node.key;
            int h2 = node.hash;
            NodeParent = node;
            //res = compare(key,k2,h1,h2);
            if(h1 > h2){
                res = 1;
            }else if(h1 < h2){
                res = -1;
            }else{//哈希值相等
                if(Objects.equals(key,k2)){
                    res = 0;
                }else{//不equals
                    if(searched){
                        res = System.identityHashCode(k1) - System.identityHashCode(k2);
                    }else if(k1 != null && k2 != null && k1.getClass() == k2.getClass() && k1 instanceof Comparable && ((Comparable)k1).compareTo(k2) != 0){//类型相同
                        res = ((Comparable)k1).compareTo(k2);//等于0并不能完全说明他们是同一个对象，因为万一这个类实现了比较接口，则会按照这个比较里的规则决定他们是不是同一个key,但是真正决定是不是同一个key的是equals
                    }else{
                        if((node.left != null && (resNode = node(node.left,k1)) != null) || (node.right != null && (resNode = node(node.right,k1)) != null)){
                            node = resNode;
                            res = 0;//已经找到这个节点
                        }else{
                            //以node为根结点的树中都扫描完了，没有相同的key，因此会根据内存地址，决定往左子树中比较还是往右子树中比较
                            //但是现在实际上整棵树都已经扫描过了没有这个key，为了避免重复扫描，设置了一个searched标志是否扫描过
                            searched = true;
                            res = System.identityHashCode(k1) - System.identityHashCode(k2);
                        }
                    }
                }
            }

            if(res > 0){//往二叉树的右边加
                node = node.right;
            }else if(res < 0){
                node = node.left;
            }else{
                V oldvalue = node.value;
                node.key = key;
                node.value = value;
                return oldvalue;
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
        return null;
    }

    @Override
    public V get(K key) {
        Node<K,V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key) {
        Node<K,V> node = node(key);
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
            int index = index(key);
            if(replacement == null){//度为0
                table[index] = null;
            }else{//度为1
                if(node.left != null){
                    table[index] = node.left;
                    node.left.parent = null;
                }else if(node.right != null){
                    table[index] = node.right;
                    node.right.parent = null;
                }
            }
            size --;
            afterDelete(node);
        }
        return oldvalue;
    }

    @Override
    public void clear() {
        if(size == 0) return;
        for(int i = 0; i < table.length; i++){
            table[i] = null;
        }
        size = 0;
    }

    public void print(){
        if(size == 0) return;
        for(int i = 0; i < table.length; i++){
            Node<K,V> root = table[i];
            if(root != null){
                System.out.println("root:" + root.key);
            }
            BinaryTrees.print(new BinaryTreeInfo(){
                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K,V>)node).left;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K,V>)node).right;
                }

                @Override
                public Object string(Object node) {
                    return node;
                }

            });
            System.out.println("");
            System.out.println("---------------------------------------");
        }
    }
    private Node<K,V> createNode(K key, V value, Node<K,V> parent) {
        return new Node<K,V>(key,value,parent);
    }

    private Node<K,V> node(K key){
        //首先要根据key算出它在哪个索引下
        Node<K,V> root = table[index(key)];
        return root == null ? null : node(root,key);
    }
    //在以node为根结点的子树中，根据key找出对应的节点
    private Node<K,V> node(Node<K,V> node,K key){
        K k1 = key;
        int h1 = k1 == null ? 0 : k1.hashCode();
        h1 = h1 ^ (h1 >>> 16);
        Node<K,V> resNode;//查找结果
        while(node != null){
            K k2 = node.key;
            int h2 = node.hash;
            if(h1 > h2){
                node = node.right;
            }else if(h1 < h2){
                node = node.left;
            }else{//哈希值相等
                if(Objects.equals(k1,k2)){
                    return node;
                }else{
                    if(k1 != null && k2 != null && k1.getClass() == k2.getClass() && k1 instanceof Comparable &&((Comparable)k1).compareTo(k2) != 0){//类型相同
                            int cmp = ((Comparable)k1).compareTo(k2);
                            if(cmp > 0){
                                node = node.right;
                            }else if(cmp < 0){
                                node = node.left;
                            }
                    }else{
                        //哈希值相等
                        //1.类型不同，或者k1为null 或者k2为null
                        //2.类型相同，k1 k2都不null，但是不具备可比较性
                        if(node.left != null && (resNode = node(node.left,k1)) != null){
                            return resNode;
                        }else{
                            node = node.right;//左边没找到，只能往右边找
                        }
                    }
                }
            }
        }
        return null;
    }

    //获取key对应的桶数组的索引
    private int index(K key){
        if(key == null){
            return 0;
        }
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }

  /*  private int compare(K k1,K k2,int h1,int h2){
        //先比较hash值，谁的哈希值大，谁放右边
        int res = h1 - h2;
        if(res != 0) return res;
        //哈希值相等，不一定equals，因为算哈希值的时候是将所有数据类型都转化为二进制，
        //然后拿二进制进行移位运算的，这就有可能一个float类型的成员变量的二进制数和一个另一个对象的int型的二进制数相等，
        // 因此最终算出来的哈希值相等，但是他们却不是key,因此要判断在哈希值相等的情况下，是否equals

        //hash值不相等，比较是否equals
        if(Objects.equals(k1,k2)) return 0;

        //k1和k2的哈希值相等，但不equals
        if(k1 != null && k2 != null){
            //按照类名进行比较
            String name1 = k1.getClass().getName();
            String name2 = k2.getClass().getName();
            res = name1.compareTo(name2);
            if(res != 0) return res;

            //他们是同一个类
            if(k1 instanceof Comparable){//k1 k2可比较
                return ((Comparable)k1).compareTo(k2);
            }
        }
        //k1和k2的哈希值相等，但不equals，也不是同一个类型，不可比较
        //比较内存地址
        //不能一开始就比较内存地址，需要先按照规定的equals比较，不行的再来下面
        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }
*/
    private void afterAdd(Node<K,V> node){
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
            table[index(grand.key)] = parent;
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
            table[index(grand.key)] = parent;
        }
        if(parentRight != null){
            parentRight.parent = grand;
        }
        grand.parent = parent;
    }
    private void afterDelete(Node<K,V> node){
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
        Node<K,V> node = node(key);
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
