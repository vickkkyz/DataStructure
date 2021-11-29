package g_RBTree;

import e_BST.printer.BinaryTrees;

/**
 * @Author qq
 * @Date 2021/11/29
 */
public class test {
    public static void main(String args[]){
        Integer[] array = new Integer[]{46, 97, 65, 20, 45, 82, 91, 62, 35, 43, 52, 16, 56};
        RBTree<Integer> rbTree = new RBTree<>();
        for(int i = 0; i < array.length; i++){
            rbTree.add(array[i]);
        }
        BinaryTrees.println(rbTree);
        System.out.println("-----------");
        rbTree.remove(97);
        BinaryTrees.println(rbTree);
        rbTree.remove(91);
        BinaryTrees.println(rbTree);
        rbTree.remove(82);
        BinaryTrees.println(rbTree);
        rbTree.remove(52);
        BinaryTrees.println(rbTree);
        rbTree.remove(45);
        BinaryTrees.println(rbTree);
        rbTree.remove(46);
        BinaryTrees.println(rbTree);
//        rbTree.remove(69);
//        BinaryTrees.println(rbTree);
    }
}
