package designModel.FactoryModel.factoryAbsModel;

import abstracts.Pizza;
import designModel.FactoryModel.factoryMethodModel.LDCheesePizza;
import designModel.FactoryModel.factoryMethodModel.LDPepperPizza;

public class LDFactory implements AbsFactory {
    @Override
    public Pizza createPizza(String orderType) {
        System.out.println("抽象工厂模式");
        Pizza pizza = null;
        if(orderType.equals("cheese")){
            return new LDCheesePizza();
        }else if(orderType.equals("pepper")){
            return new LDPepperPizza();
        }
        return pizza;
    }
}
