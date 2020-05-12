package designModel.adapterModel.interFaceAdapter;

public class Client {
    public static void main(String[] args) {
        new AbsAdapter() {
            @Override
            public void f1() {
                super.f1();
                System.out.println("使用了m1 方法");
            }
        };
    }
}
