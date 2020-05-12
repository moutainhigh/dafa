package designModel.singleModel;

public class SingleModel4 {

    public static void main(String[] args) {
    }
}

class Singleton4 {
    private Singleton4() {}

    private static volatile Singleton4 instance;

    static Singleton4 getInstance() {
        if (instance == null) {
            synchronized (Singleton4.class) {
                if (instance == null) {
                    instance = new Singleton4();
                }
            }
        }
        return instance;
    }
}
