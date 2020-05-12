package designModel.FactoryModel.factorysimpleModel;

import abstracts.Pizza;

public class CheesePizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("制作奶酪pizza 准备");
    }
}
