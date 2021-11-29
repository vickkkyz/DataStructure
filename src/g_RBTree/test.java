package g_RBTree;

import e_BST.printer.BinaryTrees;

/**
 * @Author qq
 * @Date 2021/11/29
 */
public class test {
    public static void main(String args[]){
        Integer[] array = new Integer[]{4, 91, 72, 13, 14, 80, 83, 30};
        RBTree<Integer> rbTree = new RBTree<>();
        for(int i = 0; i < array.length; i++){
            rbTree.add(array[i]);
        }
        BinaryTrees.println(rbTree);
        for(int i = 0; i < array.length; i++){
            System.out.println(array[i] + "-----------");
            rbTree.remove(array[i]);
            BinaryTrees.println(rbTree);

        }
//        BinaryTrees.println(rbTree);
//        System.out.println("-----------");
//        rbTree.remove(97);
//        BinaryTrees.println(rbTree);
//        rbTree.remove(91);
//        BinaryTrees.println(rbTree);
//        rbTree.remove(82);
//        BinaryTrees.println(rbTree);
//        rbTree.remove(52);
//        BinaryTrees.println(rbTree);
//        rbTree.remove(45);
//        BinaryTrees.println(rbTree);
//        rbTree.remove(46);
//        BinaryTrees.println(rbTree);
//        rbTree.remove(16);
//        BinaryTrees.println(rbTree);
//        rbTree.remove(69);
//        BinaryTrees.println(rbTree);
    }
}
