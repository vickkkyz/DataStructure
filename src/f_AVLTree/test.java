package f_AVLTree;

import e_BST.printer.BinaryTrees;

/**
 * @Author qq
 * @Date 2021/11/27
 */
public class test {
    public static void main(String args[]){
        Integer[] array = new Integer[]{6, 30, 43, 37, 41, 3, 47, 75, 51, 29, 89};
//        BSTImpl<Integer> bst = new BSTImpl<>();
//        for(int i = 0; i < array.length; i++){
//            bst.add(array[i]);
//        }
//        BinaryTrees.println(bst);
//        System.out.println("-------------");
//        bst.remove(43);
//        BinaryTrees.println(bst);
//        System.out.println("-------------");
//        AVLTreeImpl<Integer> avlTree = new AVLTreeImpl<>();
//        for(int i = 0; i < array.length; i++){
//            avlTree.add(array[i]);
////            BinaryTrees.println(avlTree);
////            System.out.println("-------------");
//        }
//        BinaryTrees.println(avlTree);

        AVLTreeImpl<Integer> avlTree = new AVLTreeImpl<>();
        for(int i = 0; i < array.length; i++){
            avlTree.add(array[i]);
        }
        BinaryTrees.println(avlTree);
        System.out.println("-------------");
        avlTree.remove(41);
        BinaryTrees.println(avlTree);
        System.out.println("-------------");
        avlTree.remove(51);
        BinaryTrees.println(avlTree);
    }
}