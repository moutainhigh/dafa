package designModel.FactoryModel.factoryAbsModel;

import abstracts.Pizza;

public class PizzaStroe {

    public static void main(String[] args) {
        new OrderPizza(new BJFactory());

        AbsFactory absFactory = new LDFactory();
        Pizza pizza = absFactory.createPizza("aaa");

    }
}
