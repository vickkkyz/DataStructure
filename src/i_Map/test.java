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
    }
}
