package designModel.FactoryModel.factoryMethodModel;

import abstracts.Pizza;

public class BJCheesePizza extends Pizza {

    public BJCheesePizza(){
        System.out.println("子类默认构造方法");
    }

    @Override
    public void prepare() {
        setName("BJCheesePizza");
        System.out.println("BJCheesePizza 准备");
    }
}
