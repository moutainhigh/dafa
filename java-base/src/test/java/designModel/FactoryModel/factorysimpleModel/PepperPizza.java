package designModel.FactoryModel.factorysimpleModel;

import abstracts.Pizza;

public class PepperPizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("制作胡椒pizza 准备");
    }
}
