package designModel.bridgeModel;

public class XiaoMI implements Brand {


    @Override
    public void open() {
        System.out.println("xiaomi 手机开机");
    }

    @Override
    public void close() {
        System.out.println("xiaomi 手机关机");
    }

    @Override
    public void call() {
        System.out.println("xiaomi 手机打电话");
    }
}
