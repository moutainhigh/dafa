package designModel.FactoryModel.factoryMethodModel;

import abstracts.Pizza;

public class LDCheesePizza extends Pizza {
    @Override
    public void prepare() {
        setName("LDCheesePizza");
        System.out.println("LDCheesePizza 准备");
    }
}
