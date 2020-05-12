package designModel.FactoryModel.factoryMethodModel;

import abstracts.Pizza;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class OrderPizza {

    /**
     * 抽象方法
     */
    abstract Pizza createPizza(String orderType);

    /**
     * 构造器
     */
    public OrderPizza() {
        System.out.println("父类的构造方法");
        Pizza pizza;
        String orderType;
        do {
            orderType = getType();
            pizza = createPizza(orderType);//由工厂子类完成
            pizza.prepare();
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
