package designModel.FactoryModel.factorysimpleModel;

import abstracts.Pizza;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OrderPizza2 {

    /**
     * 静态工厂模式,再增加OrderPizza类 就可以不用修改，只需要修改SimpleFactory即可
     */
    Pizza pizza = null;
    String orderType = "";

    public OrderPizza2() {
        do {
            orderType = getType();
            pizza = SimpleFactory.createPizza2(orderType);
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
