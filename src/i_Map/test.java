package i_Map;

/**
 * @Author qq
 * @Date 2021/11/30
 */
public class test {
    public static void main(String args[]){
        MapImpl<String,Integer> map = new MapImpl<>();
        map.put("张三",20);
        map.put("李四",15);
        map.put("王五",10);
        map.put("赵六",100);
        System.out.println(map);
        System.out.println("size------" + map.size());
        map.remove("张三");
        System.out.println("------");
        System.out.println(map);
        System.out.println("size------" + map.size());
        System.out.println(map.get("李四"));
        System.out.println(map.containsKey("李四"));
        System.out.println(map.containsValue(100));
    }
}
