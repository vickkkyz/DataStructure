package h_Set;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author qq
 * @Date 2021/11/30
 */
//利用链表来实现set集合
public class SetImpl<E> implements Set<E>{

    List<E> list = new LinkedList<>();
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }

    @Override
    public void add(E element) {
//        if(contains(element)) return;
//        list.add(element);
        if(contains(element)){
            int index = list.indexOf(element);
            list.set(index,element);
            return;
        }
        list.add(element);
    }

    @Override
    public void remove(E element) {
        list.remove(element);
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < list.size(); i++){
            if(i != 0){
                sb.append(",");
            }
            sb.append(list.get(i));
        }
        return sb.toString();
    }
}
