package designModel.FactoryModel.factorysimpleModel;

import abstracts.Pizza;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OrderPizza {

    /**
     * 传统 如果增加pizza ,order 也是需要做修改
     *
     * */
    //public OrderPizza() {
    //    Pizza pizza;
    //    String orderType;
    //    do {
    //        orderType = getType();
    //        if(orderType.equals("cheese")){
    //            pizza = new CheesePizza();
    //            pizza.setName("cheese");
    //        }else if(orderType.equals("pepper")){
    //            pizza = new PepperPizza();
    //            pizza.setName("pepper");
    //        }else {
    //            break;
    //        }
    //        pizza.bake();
    //        pizza.cut();
    //        pizza.box();
    //
    //    }while (true);
    //}

    /**
     * 简单工厂模式,再增加OrderPizza类 就可以不用修改，只需要修改SimpleFactory即可
     */
    SimpleFactory simpleFactory;
    Pizza pizza = null;

    OrderPizza(SimpleFactory simpleFactory) {
        setFactory(simpleFactory);
    }

    public void setFactory(SimpleFactory simpleFactory) {
        String orderType = "";
        this.simpleFactory = simpleFactory;
        do {
            orderType = getType();
            pizza = this.simpleFactory.createPizza(orderType);
            if (pizza != null) {
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            } else {
                System.out.println("订购失败");
                break;
            }
        } while (true);

    }


    private String getType() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("输入 pizza 种类：");
            String str = br.readLine();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
