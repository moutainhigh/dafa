package designModel.adapterModel.ObjectAdapter;

public class VoltageAdapter implements IVoltage5v {
    private Voltage220v v;

    public VoltageAdapter(Voltage220v v) {
        this.v = v;
    }

    @Override
    public int output5v() {
        int srcV;
        int dstV = 0;
        if (v != null) {
            srcV = v.output220v();
            System.out.println("使用对象适配器");
            dstV = srcV / 22;
            System.out.println("适配器电压" + dstV);
        }
        return dstV;
    }
}
