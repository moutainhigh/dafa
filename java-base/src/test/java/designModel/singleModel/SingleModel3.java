package designModel.singleModel;

public class SingleModel3 {

    public static void main(String[] args) {
    }
}

class Singleton3 {
    private static Singleton3 instance;

    private Singleton3() {}

    private static Singleton3 getInstance() {
        if (instance == null) {
            synchronized (Singleton3.class) {
                return new Singleton3();
            }

        }
        return instance;
    }
}
