package b_LinkedList;

import b_LinkedList.DoubleLinkList.DoubleLinkedListImpl;

/**
 * @Author qq
 * @Date 2021/11/14
 */
public class test {
    public static void main(String args[]){
        LinkedList<Integer> list = new CricleLinkedListImpl<>();
        //LinkedList<Integer> list = new DoubleLinkedListImpl<>();
        //LinkedList<Integer> list = new LinkedListImpl<>();
        for(int i =0; i <10; i++){
            list.add(i);
        }
        System.out.println(list);
        list.add(1,100);
        System.out.println(list.getElements(3));
        System.out.println(list);
        list.remove(3);
        System.out.println(list);
        list.set(9,555);
        System.out.println(list);
        System.out.println(list.getIndex(555));
        list.clear();
        System.out.println(list);
       // list.getElements(2);
    }
}
