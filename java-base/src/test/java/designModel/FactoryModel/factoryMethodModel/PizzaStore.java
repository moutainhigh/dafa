package designModel.FactoryModel.factoryMethodModel;

import abstracts.Pizza;

public class PizzaStore {

    public static void main(String[] args) {
        //new BJOrderPizza();
        //new LDOrderPizza();

        OrderPizza orderPizza = new BJOrderPizza();
        Pizza p = orderPizza.createPizza("order");

    }
}
