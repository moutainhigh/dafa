package designModel.FactoryModel.factoryMethodModel;

import abstracts.Pizza;

public class LDPepperPizza extends Pizza {
    @Override
    public void prepare() {
        setName("LDPepperPizza");
        System.out.println("LDPepperPizza 准备");
    }
}
