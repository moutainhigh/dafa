package designModel.adapterModel.ObjectAdapter;

/**
 * 被适配的类
 */
public class Voltage220v {
    public int output220v() {
        int src = 220;
        System.out.println("电压 = " + src + "v");
        return src;
    }


}
