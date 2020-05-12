package designModel.FactoryModel.factoryMethodModel;

import abstracts.Pizza;

public class BJPepperPizza extends Pizza {
    @Override
    public void prepare() {
        setName("BJPepperPizza");
        System.out.println("BJPepperPizza 准备");
    }
}
