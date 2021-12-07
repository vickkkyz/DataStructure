package k_Heap;

import e_BST.printer.BinaryTrees;

import java.util.Comparator;

/**
 * @Author qq
 * @Date 2021/12/7
 */
public class test {
    public static void main(String agrs[]){
        BinaryHeap<Integer> bh = new BinaryHeap<>();
//        小顶堆只需要实现比较器即可
//        BinaryHeap<Integer> bh = new BinaryHeap<>((Comparator<Integer>) (o, t1) -> t1-o);
        int[] data = {86, 75, 48, 84, 33, 7, 69, 13, 95, 10, 55, 96, 16, 36};
        for(int i = 0; i < data.length; i++){
            bh.add(data[i]);
        }
        System.out.println(bh.get());
        bh.remove();
        System.out.println(bh.get());
        BinaryTrees.println(bh);
        bh.add(125);
        bh.add(15);
        BinaryTrees.println(bh);
        bh.replace(22);
        BinaryTrees.println(bh);
    }
}
