package a_ArrayList;

/**
 * @Author qq
 * @Date 2021/11/14
 */
public class test {
    public static void main(String args[]){
        int a = 10;
        ArrayList<Integer> list = new ArrayListImpl<>(10);
        for(int i = 0; i < 11; i ++){
            list.add(i);
        }
        /*for(int i = 0; i < 11; i ++){
            list.remove(0);
        }*/
        System.out.println(list.getIndex(9));
        list.remove(0);
        list.add(2,100);
        list.add(3,null);
        list.set(1,5);
        System.out.println(list.getIndex(100));
        System.out.println(list.contains(null));
        System.out.println(list);

        list.clear();
        System.out.println(list.getIndex(100));
        System.out.println(list);
    }
}
