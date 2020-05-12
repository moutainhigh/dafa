package designModel.FactoryModel.factoryAbsModel;

import abstracts.Pizza;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OrderPizza {

   private AbsFactory absFactory;

   public   OrderPizza(AbsFactory absFactory){
       setFactory(absFactory);
   }

    public void setFactory(AbsFactory absFactory){
        Pizza pizza = null;
        String orderType = "";
        this.absFactory = absFactory;
        do {
            orderType =getType();
            pizza = absFactory.createPizza(orderType);
            if(pizza!=null){
                pizza.prepare();
            }else {
                System.out.println("订购失败");
                break;
            }
        }while (true);
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

