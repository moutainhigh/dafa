package designModel.FactoryModel.factorysimpleModel;

public class PizzaStore {

    public static void main(String[] args) {
        //传统
        //new OrderPizza();
        //简单工厂模式
        new OrderPizza(new SimpleFactory());
        //静态工厂模式
        new OrderPizza2();

    }
}
