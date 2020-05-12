package designModel.adapterModel.classAdapter;

public class VoltageAdapter extends Voltage220v implements IVoltage5v {
    @Override
    public int output5v() {
        int srcV = output220v();
        int dstV = srcV / 22;
        return dstV;
    }
}
