package e_BST;

import e_BST.printer.BinaryTrees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @Author qq
 * @Date 2021/11/19
 */
public class test {
    private static class PersonCom implements Comparator<Person>{
        @Override
        public int compare(Person person, Person t1) {
            return person.getName().length() - t1.getName().length();
        }
    }
    private static class PersonCom2 implements Comparator<Person>{
        @Override
        public int compare(Person person, Person t1) {
            return person.getAge() - t1.getAge();
        }
    }
    public static void main(String args[]){
//        Integer[] array = new Integer[]{5,3,4,1,8,2};
//        BSTImpl<Integer> bst = new BSTImpl<>();
//        for(int i = 0; i < array.length; i++){
//            bst.add(array[i]);
//        }
        BSTImpl<Integer> bst = new BSTImpl<>();
        for(int i = 0; i < 15; i++){
            bst.add((int) (Math.random() * 100));
        }
        BinaryTrees.println(bst);
        System.out.println(bst.height()); 
//        BinaryTrees.println(bst);
//        bst.nonRecursivePre();
//          BinaryTrees.println(bst);
//          bst.nonRecursiveIn();
//        BinaryTrees.println(bst);
//        bst.nonRecursivePost();
//        bst.inOrder();
//        System.out.println("前驱"+bst.getElement(bst.predecessor(20)));
//        System.out.println("后继"+bst.getElement(bst.successor(16)));
//        bst.remove(25);
//        BinaryTrees.println(bst);
        //bst.remove();
//        BinaryTrees.println(bst);
//        System.out.println("前序:");
//        bst.preOrder();
//        System.out.println("中序:");
//        bst.inOrder();
//        System.out.println("后序:");
//        bst.postOrder();
//        System.out.println("层序:");
//        bst.levelOrder();
        /*
         当同一个类，如果实现了Comparable接口，需要在这个类中实现这个比较方法，对于同一个类，比较规则是一样的
         如果他们想实现不同的比较规则，就需要使用Comparator
         */

//        PersonCom2 pp2 = new PersonCom2();
//        BST<Person> bst = new BSTImpl<>(pp2);
//        PersonCom pp1 = new PersonCom();
//        BST<Person> bst = new BSTImpl<>(pp1);
//        Person p1 = new Person("xiaoming",10);
//        Person p2 = new Person("lisi",15);
//        Person p3 = new Person("huahua",8);
//        Person p4 = new Person("ll",25);
//        Person p5 = new Person("zhangguanlidia",30);
//        Person p6 = new Person("zhangguanh",14);
//        bst.add(p1);
//        bst.add(p2);
//        bst.add(p3);
//        bst.add(p4);
//        bst.add(p5);
//        bst.add(p6);
//        BinaryTrees.println(bst);


    }
}
