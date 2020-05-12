package designModel.singleModel;

public class SingleModel5 {

    public static void main(String[] args) {

    }
}

class Singleton5 {

    private static volatile Singleton5 instance;

    private Singleton5() { }

    private static class SingletonInstance {
        private static final Singleton5 INSTANCE = new Singleton5();
    }

    public static synchronized Singleton5 getInstance() {
        return SingletonInstance.INSTANCE;
    }

}
