package e_BST;

import e_BST.printer.BinaryTreeInfo;
import e_BST.printer.BinaryTrees;

import java.util.Comparator;

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
        Integer[] array = new Integer[]{5,88,7,4,16,25,43,20,10};
        BST<Integer> bst = new BSTImpl<>();
        for(int i = 0; i < array.length; i++){
            bst.add(array[i]);
        }
        BinaryTrees.println((BinaryTreeInfo) bst);
        System.out.println("前序:");
        ((BSTImpl<Integer>) bst).preOrder();
        System.out.println("中序:");
        ((BSTImpl<Integer>) bst).inOrder();
        System.out.println("后序:");
        ((BSTImpl<Integer>) bst).postOrder();
        System.out.println("层序:");
        ((BSTImpl<Integer>) bst).levelOrder();
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
//        BinaryTrees.println((BinaryTreeInfo) bst);

    }
}
