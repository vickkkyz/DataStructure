package d_Queue;

/**
 * @Author qq
 * @Date 2021/11/17
 */
public class test {
    public static void main(String args[]){
        //Queue<Integer> queue = new QueueImpl<>();
//        Queue<Integer> queue = new QueueByStack<>();
//        queue.enQueue(11);
//        queue.enQueue(22);
//        System.out.println("---"+queue.deQueue());
//        queue.enQueue(33);
//        queue.enQueue(44);
//        System.out.println("size:"+queue.size());
//        System.out.println("队首是"+queue.front());
//        System.out.println("队尾是"+queue.rear());
//        while (!queue.isEmpty()){
//            System.out.println(queue.deQueue());
//        }


//        ---11
//        size:3
//        队首是22
//        队尾是44
//        22
//        33
//        44
//整体注释代码 Ctrl+/
//整体右移代码 选中+tab

        Queue<Integer> queue = new CircleQueue<>();
        queue.enQueue(11);
        queue.enQueue(22);
        queue.enQueue(33);
        queue.enQueue(44);
        queue.enQueue(55);
        queue.enQueue(66);
        queue.enQueue(77);
        queue.enQueue(88);
        queue.enQueue(99);
        queue.deQueue();
        queue.deQueue();
        //size:9 capacity:10 front:2 [null,null,33,44,55,66,77,88,99,null]

        queue.enQueue(100);
        queue.enQueue(110);
        //size:9 capacity:10 front:2 [110,null,33,44,55,66,77,88,99,100]

        queue.deQueue();
        System.out.println(queue.front());
        queue.enQueue(120);
        queue.enQueue(130);
        //size:11 capacity:15 front:0 [33,44,55,66,77,88,99,100,110,120,130,null,null,null,null]
        System.out.println(queue.rear());
        System.out.println(queue);
    }
}
