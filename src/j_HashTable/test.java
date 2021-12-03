package j_HashTable;

import e_BST.printer.BinaryTrees;

/**
 * @Author qq
 * @Date 2021/12/2
 */
public class test {
    static void test3(HashMap<Object, Integer> map) {
        map.put(null, 1); // 1
        map.put(new Object(), 2); // 2
        map.put("jack", 3); // 3
        map.put(10, 4); // 4
        map.put(new Object(), 5); // 5
        map.put("jack", 6);
        map.put(10, 7);
        map.put(null, 8);
        map.put(10, null);
        Asserts.test(map.size() == 5);
        Asserts.test(map.get(null) == 8);
        Asserts.test(map.get("jack") == 6);
        Asserts.test(map.get(10) == null);
        Asserts.test(map.get(new Object()) == null);
        Asserts.test(map.containsKey(10));
        Asserts.test(map.containsKey(null));
        Asserts.test(map.containsValue(null));
        Asserts.test(map.containsValue(1) == false);
    }
    static void test4(HashMap<Object, Integer> map) {
        map.put("jack", 1);
        map.put("rose", 2);
        map.put("jim", 3);
        map.put("jake", 4);
        map.remove("jack");
        map.remove("jim");
        for (int i = 1; i <= 10; i++) {
            map.put("test" + i, i);
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            Asserts.test(map.remove(new Key(i)) == i);
        }
        for (int i = 1; i <= 3; i++) {
            map.put(new Key(i), i + 5);
        }
        Asserts.test(map.size() == 19);
        Asserts.test(map.get(new Key(1)) == 6);
        Asserts.test(map.get(new Key(2)) == 7);
        Asserts.test(map.get(new Key(3)) == 8);
        Asserts.test(map.get(new Key(4)) == 4);
        Asserts.test(map.get(new Key(5)) == null);
        Asserts.test(map.get(new Key(6)) == null);
        Asserts.test(map.get(new Key(7)) == null);
        Asserts.test(map.get(new Key(8)) == 8);
        map.print();
    }
    public static void main(String agrs[]){
       HashMap<Object,Integer> map = new HashMap<>();
//        Person p1 = new Person("张三",1.2f,15);
//        Person p2 = new Person("张三",1.2f,15);
//        Person p3 = new Person("王五",1.6f,10);
//        Person p4 = new Person("赵六",1.8f,25);
//        Person p5 = new Person("李四",1.2f,8);
//        map.put(p1,1);
//        map.put(p2,3);
//        map.put(p3,10);
//        map.put(p4,20);
//        map.put(p5,30);
//        Key k1 = null;
//        for(int i = 0; i < 15; i++){
//            Key k = new Key(i);
//            map.put(k,i);
//        }
//        System.out.println(map.containsKey(new Key(10)));//这里也是，用compare 只有内存地址相等才返回true，而如果新new一个value等于5的key,会被视作新的key
//        System.out.println(map.remove(new Key(3)));//用compare这里出现问题啦，本来我认为如果属性value相等，他们就是同一个key，需要覆盖，可是现在并没有被覆盖
//        System.out.println(map.size());
//        System.out.println(map.containsValue(4));
//        map.print();

        test4(map);
    }
}
