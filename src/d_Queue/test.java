package d_Queue;

/**
 * @Author qq
 * @Date 2021/11/17
 */
public class test {
    public static void main(String args[]){
        //Queue<Integer> queue = new QueueImpl<>();
        Queue<Integer> queue = new QueueByStack<>();
        queue.enQueue(11);
        queue.enQueue(22);
        System.out.println("---"+queue.deQueue());
        queue.enQueue(33);
        queue.enQueue(44);
        System.out.println("size:"+queue.size());
        System.out.println("队首是"+queue.front());
        System.out.println("队尾是"+queue.rear());
        while (!queue.isEmpty()){
            System.out.println(queue.deQueue());
        }


//        ---11
//        size:3
//        队首是22
//        队尾是44
//        22
//        33
//        44
//整体注释代码 Ctrl+/
//整体右移代码 选中+tab

    }
}
