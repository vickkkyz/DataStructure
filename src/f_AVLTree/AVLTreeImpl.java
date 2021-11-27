package f_AVLTree;


/**
 * @Author qq
 * @Date 2021/11/27
 */
public class AVLTreeImpl<E> extends BSTImpl<E>{

    private static class AVLNode<E> extends Node<E>{
        int height = 1;//以avlnode为根节点的树的高度  初始时1 因为只有一个节点
        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }
        //算出节点的平衡因子
        public int BalanceFactor(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            return leftHeight - rightHeight;
        }
        public void updateHeight(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            height =  1 + Math.max(leftHeight,rightHeight);
        }

    }

    /**
     * 添加之后要恢复平衡
     * @param node 刚刚添加的那个节点
     */
    @Override
    protected void afterAdd(Node<E> node){
        while((node = node.parent) != null){
            if(isBalanced(node)){//node平衡
                updateHeight(node);//用到这个结点才更新这个节点的高度，以便可以直接判断上层节点的高度，时间复杂度低
            }else{//失衡 需要恢复平衡
                //RecoversyBalance(node);
                RecoversyTogether(node);
                break;
            }
        }
    }
    @Override
    protected void afterDelete(Node<E> node){
        while((node = node.parent) != null){
            if(isBalanced(node)){//node平衡
                updateHeight(node);//用到这个结点才更新这个节点的高度，以便可以直接判断上层节点的高度，时间复杂度低
            }else{//失衡 需要恢复平衡
                RecoversyBalance(node);
                //RecoversyTogether(node);
            }
        }
    }

    /**
     * 恢复平衡
     * @param grand 离添加节点最近的那个失衡结点
     */
    private void RecoversyBalance(Node<E> grand){
        Node<E> parent = tallerChild(grand);
        Node<E> node = tallerChild(parent);
        if(isLeftChild(parent)){
            if(isLeftChild(node)){//LL
                rightRevolve(grand,parent);
            }else{//LR
                leftRevolve(parent,node);
                rightRevolve(grand,node);//注意这里！！！上一行已经旋转过一次，此时parent已经变成node，不要写成parent
            }
        }else{
            if(isLeftChild(node)){//RL
                rightRevolve(parent,node);
                leftRevolve(grand,node);//注意这里！！！上一行已经旋转过一次，此时parent已经变成node，不要写成parent
            }else{//RR
                leftRevolve(grand,parent);
            }
        }

    }

    /**
     * 找到规律，统一旋转，
     * @param grand
     */
    private void RecoversyTogether(Node<E> grand){
        Node<E> parent = tallerChild(grand);
        Node<E> node = tallerChild(parent);
        if(isLeftChild(parent)){
            if(isLeftChild(node)){//LL
                RecoversyTogether(grand,node.left,node,node.right,parent,parent.right,grand,grand.right);
            }else{//LR
                RecoversyTogether(grand,parent.left,parent,node.left,node,node.right,grand,grand.right);
            }
        }else{
            if(isLeftChild(node)){//RL
                RecoversyTogether(grand,grand.left,grand,node.left,node,node.right,parent,parent.right);
            }else{//RR
                RecoversyTogether(grand,grand.left,grand,parent.left,parent,node.left,node,node.right);
            }
        }
    }

    /**
     *
     * @param r 旋转前子树的根结点
     * @param a
     * @param b
     * @param c
     * @param d 旋转后子树的根结点
     * @param e
     * @param f
     * @param g
     * 无论是哪种旋转，他们的最终形态是一样的，都是 a-b-c-d-e-f-g
     */
    private void RecoversyTogether(Node<E> r,
                                   Node<E> a,Node<E> b,Node<E> c,
                                   Node<E> d,
                                   Node<E> e,Node<E> f,Node<E> g){
       //设置根节点d
        d.parent = r.parent;
        if(isLeftChild(r)){
            r.parent.left = d;
        }else if(isRightChild(r)){
            r.parent.right = d;
        }else{
            root = d;
        }
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
        //a-b-c
        b.right = c;
        if(c != null){
            c.parent = b;
        }
        b.left = a;
        if(a != null){
            a.parent = b;
        }
        //e-f-g
        f.right = g;
        if(g != null){
            g.parent = f;
        }
        f.left = e;
        if(e != null){
            e.parent = f;
        }
        updateHeight(b);
        updateHeight(f);
        updateHeight(d);
    }

    /**
     * 左旋
     * @param grand 要旋转的节点
     * @param parent grand的右孩子
     */
    private void leftRevolve(Node<E> grand,Node<E> parent){
        Node<E> grandParent = grand.parent;
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
        updateHeight(grand);
        updateHeight(parent);//最后一定注意要更新高度，因为grand和parent的左右子树发生了变化！！并且先更新最低的，再更新高的
    }

    /**
     * 右旋
     * @param grand 要旋转的节点
     * @param parent grand的左孩子
     */
    private void rightRevolve(Node<E> grand,Node<E> parent){
        Node<E> grandParent = grand.parent;
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
        //更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    private Node<E> tallerChild(Node<E> node){
        //注意判断node的左右子树是否为null
        int leftHeight = node.left == null ? 0 :((AVLNode<E>)node.left).height;
        int rightHeight = node.right == null ? 0 :((AVLNode<E>)node.right).height;
        if(leftHeight > rightHeight){
            return node.left;
        }else if(leftHeight < rightHeight){
            return node.right;
        }else{
            //左右子树高度相等，那就看grand节点是左子树还是右子树,返回同方向的
            boolean flag = isLeftChild(node.left);
            return flag == true ? node.left : node.right;
        }
    }
    private boolean isLeftChild(Node<E> node){
        return node.parent != null && node.parent.left == node;
    }
    private boolean isRightChild(Node<E> node){
        return node.parent != null && node.parent.right == node;
    }

    //如果avl不重写这个方法到时候node转换成avl的时候会报错
    //java.lang.ClassCastException:
    // class f_AVLTree.BSTImpl$Node cannot be cast to class f_AVLTree.AVLTreeImpl$AVLNode (f_AVLTree.BSTImpl$Node and f_AVLTree.AVLTreeImpl$AVLNode are in unnamed module of loader 'app')
    @Override
    protected Node<E> createNode(E element,Node<E> parent){
        return new AVLNode<E>(element,parent);
    }
    private void updateHeight(Node<E> node){
        ((AVLNode<E>)node).updateHeight();
    }
    private boolean isBalanced(Node<E> node){
        //判断平衡的条件是左子树高度减去右子树的高度的绝对值小于等于1
        return Math.abs(((AVLNode<E>)node).BalanceFactor()) <= 1;//向下转型的前提是这个node本来就是子类类型
    }
}
