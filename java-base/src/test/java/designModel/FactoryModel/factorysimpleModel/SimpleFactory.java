package designModel.FactoryModel.factorysimpleModel;

import abstracts.Pizza;

public class SimpleFactory {

    public Pizza createPizza(String orderType){
        Pizza pizza =null;
        System.out.println("简单工厂模式");
        if(orderType.equals("cheese")){
            pizza = new CheesePizza();
            pizza.setName("cheese");
        }else if(orderType.equals("pepper")){
            pizza = new PepperPizza();
            pizza.setName("pepper");
        }
        return pizza;
    }
    /**
     * 静态的
     * */
    public static Pizza createPizza2(String orderType){
        Pizza pizza =null;
        System.out.println("简单工厂模式");
        if(orderType.equals("cheese")){
            pizza = new CheesePizza();
            pizza.setName("cheese");
        }else if(orderType.equals("pepper")){
            pizza = new PepperPizza();
            pizza.setName("pepper");
        }
        return pizza;
    }
}
