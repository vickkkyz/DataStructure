package c_Stack;

/**
 * @Author qq
 * @Date 2021/11/17
 */
public class test {
    public static void main(String args[]){
        Stack<Integer> stack = new StackImpl<>();
        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.push(44);
        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }

    }
}
