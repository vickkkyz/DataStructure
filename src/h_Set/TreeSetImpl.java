package h_Set;

import d_Queue.Queue;
import d_Queue.QueueImpl;
import g_RBTree.RBTree;

/**
 * @Author qq
 * @Date 2021/11/30
 */
//红黑树实现集合
public class TreeSetImpl<E> implements Set<E> {
    RBTree<E> rbTree = new RBTree<>();

    @Override
    public int size() {
        return rbTree.size();
    }

    @Override
    public boolean contains(E element) {
        return rbTree.contains(element);
    }

    @Override
    public void clear() {
        rbTree.clear();
    }

    @Override
    public boolean isEmpty() {
        return rbTree.isEmpty();
    }

    @Override
    public void add(E element) {
        rbTree.add(element);
    }

    @Override
    public void remove(E element) {
        rbTree.remove(element);
    }
    public void string(){
        StringBuffer sb = new StringBuffer();
        rbTree.inOrder();
    }
}
