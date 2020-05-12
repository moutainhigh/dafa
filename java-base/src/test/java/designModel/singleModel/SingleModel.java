package designModel.singleModel;

public class SingleModel {

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton0 = Singleton.getInstance();
        System.out.println(singleton==singleton0);//true
        System.out.println(singleton.hashCode());
        System.out.println(singleton0.hashCode());
    }
}

class Singleton {
    private Singleton() {}

    private final static Singleton instance = new Singleton();

    static Singleton getInstance() {
        return instance;
    }
}
