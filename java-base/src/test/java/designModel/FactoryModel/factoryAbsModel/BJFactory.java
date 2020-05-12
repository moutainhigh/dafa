package designModel.FactoryModel.factoryAbsModel;

import abstracts.Pizza;
import designModel.FactoryModel.factoryMethodModel.BJCheesePizza;
import designModel.FactoryModel.factoryMethodModel.BJPepperPizza;

public class BJFactory implements AbsFactory {
    @Override
    public Pizza createPizza(String orderType) {
        System.out.println("抽象工厂模式");
        Pizza pizza = null;
        if(orderType.equals("cheese")){
            return new BJCheesePizza();
        }else if(orderType.equals("pepper")){
            return new BJPepperPizza();
        }
        return pizza;
    }
}
