package j_HashTable;

/**
 * @Author qq
 * @Date 2021/12/2
 */
public class Person {
    private int age;
    private float heigh;
    private String name;
    public Person(String name,float heigh,int age){
        this.name = name;
        this.age = age;
        this.heigh = heigh;
    }

    @Override
    public boolean equals(Object k1){
        //内存地址相同，肯定相等
        if(this == k1) return true;
        if(k1 == null || k1.getClass() != getClass()) return false;
        Person p1 = (Person) k1;
        return p1.age == age && p1.name == name && p1.heigh == heigh;
    }

    @Override
    public int hashCode(){
        int hash = Integer.hashCode(age);
        hash = hash * 31 + Float.hashCode(heigh);
        hash = hash * 31 + name == null ? 0 : name.hashCode();
        return hash;
    }

}
