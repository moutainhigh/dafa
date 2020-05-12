package designModel.adapterModel.classAdapter;

public class Phone {

     void charging(IVoltage5v iVoltage5v) {
        if (iVoltage5v.output5v() == 10) {
            System.out.println("手机充电");
        } else if (iVoltage5v.output5v() > 10) {
            System.out.println("电压大于10");
        }

    }
}
