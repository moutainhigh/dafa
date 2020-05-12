package designModel.singleModel;

public class SingleModel2 {


}

class Singleton2 {
    private Singleton2() {}

    private static Singleton2 instance;

    static {
        instance = new Singleton2();
    }

    static Singleton2 getInstance() {
        return instance;
    }
}