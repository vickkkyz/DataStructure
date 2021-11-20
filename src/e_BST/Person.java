package e_BST;

import java.util.Comparator;

/**
 * @Author qq
 * @Date 2021/11/19
 */
public class Person {
    private int age;
    private String name;

    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public Person(){}
    public Person(String name,int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString(){
        return "name:"+name+",age:"+age;
    }
}
