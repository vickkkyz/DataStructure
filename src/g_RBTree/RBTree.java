package g_RBTree;

/**
 * @Author qq
 * @Date 2021/11/29
 */


/**
 * 红黑树必须满足一下性质
 * ---------------------
 * 1.根节点必须是 BLACK
 * 2.节点必须是 RED或者BLACK
 * 3.叶子节点(外部节点，空节点，假象出来的节点)都是BLACK
 * 4.RED节点的子节点都是BLACK
 * RED节点的parent都是BLACK
 * 从根节点到叶子节点的所有路径不能包含两个连续的RED节点
 * 5.从任一节点到叶子结点的所有路径包含相同数目的BLACK节点
 * --------------------
 *
 * 红黑树和4阶B树具有等价性
 * BLACK节点和它的RED子节点融合在一起形成1个B树节点
 * 红黑树的BLACK节点个数与4阶B树的节点总个数相等
 */
public class RBTree<E> extends BSTImpl<E> {
    public static final boolean BLACK = true;
    public static final boolean RED = false;

    private static class RBNode<E> extends Node<E>{
        boolean color = RED;
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }
        public String toString(){
            StringBuffer sb = new StringBuffer();
            if(this.color == RED){
                sb.append("RED_");
            }else{
                sb.append("BLACK_");
            }
            sb.append(this.element);
            return sb.toString();
        }
    }

    @Override
    protected void afterAdd(Node<E> node){
        Node<E> parent = node.parent;
        if(parent == null){
            //添加的是根结点
            BLACKcolor(node);
            return;
        }
        if(isBlack(parent)){
            return;
        }
        //parent.color = red
        Node<E> uncle = sibling(parent);
        Node<E> grand = parent.parent;
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

    @Override
    protected void afterDelete(Node<E> node){
        //1.删除的是红色的节点，不会影响红黑树的性质 直接删
        //2.删除的是黑色节点，但是用于替代它的节点是红色（此时node是用于替代的节点）
        // 如果要删除的是度为2的节点，并且有2个红色的子节点，则它的前驱节点（子节点）的值会覆盖上去，然后真正删除的是它的前驱节点，红色叶子，不用管
        //如果要删除的是度为1的节点，则最后是它的子节点替代它，传进来的node其实是replacement，由于要维护红黑树的性质，如果它的子节点是红色，则染为黑色
        if(isRed(node)){
            BLACKcolor(node);
            return;
        }
        Node<E> parent = node.parent;
        //删除的是根结点
        if(parent == null) return;

        //3.删除的是黑色叶子节点，会产生下溢出，看它的兄弟节点能不能借给它节点
        //它的兄弟节点是红色 不能借
        boolean left = parent.left == null || isLeftChild(node);//afterDelete是删除之后的操作，node肯定是叶子节点，此时node.parent的孩子指针已经指向null，因此不能根据parent来判断node是左孩子还是右孩子
        //因此我们可以反向思考，如果parent的左子树为空，说明刚刚断开的就是左子树的那个指针，因此node就是左孩子
        Node<E> sibling = left ? parent.right : parent.left;
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

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<E>(element,parent);
    }

    //返回该节点的颜色
    private boolean nodeColor(Node<E> node){
        return node == null ? BLACK : ((RBNode<E>)node).color;
    }
    private boolean isRed(Node<E> node){
        return nodeColor(node) == RED;
    }
    private boolean isBlack(Node<E> node){
        return nodeColor(node) == BLACK;
    }
    //给节点染色
    private Node<E> color(Node<E> node,boolean color){
        ((RBNode<E>)node).color = color;
        return node;
    }
    //染成红色
    private Node<E> REDcolor(Node<E> node){
        return color(node,RED);
    }
    private Node<E> BLACKcolor(Node<E> node){
        return color(node,BLACK);
    }
    //获取兄弟节点
    private Node<E> sibling(Node<E> node){
        Node<E> parent = node.parent;
        if(isLeftChild(node)){
            return parent.right;
        }else if(isRightChild(node)){
            return parent.left;
        }else{
            return null;
        }
    }
    private boolean isLeftChild(Node<E> node){
        return node.parent != null && node.parent.left == node;
    }
    private boolean isRightChild(Node<E> node){
        return node.parent != null && node.parent.right == node;
    }
    /**
     * 左旋
     * @param grand 要旋转的节点
     *  parent grand的右孩子
     */
    private void leftRevolve(Node<E> grand){
        Node<E> grandParent = grand.parent;
        Node<E> parent = grand.right;
        Node<E> parentLeft = parent.left;
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
    private void rightRevolve(Node<E> grand){
        Node<E> grandParent = grand.parent;
        Node<E> parent= grand.left;
        Node<E> parentRight = parent.right;
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


}