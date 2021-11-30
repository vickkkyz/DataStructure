package h_Set;

/**
 * @Author qq
 * @Date 2021/11/30
 */
public class test {
    public static void main(String args[]){
//        Set<Integer> set = new SetImpl<>();
        TreeSetImpl<Integer> set = new TreeSetImpl<>();
        set.add(11);
        set.add(11);
        set.add(12);
        set.add(5);
        set.add(11);
        set.add(11);
        set.add(7);
        set.add(5);
        set.string();
    }
}
