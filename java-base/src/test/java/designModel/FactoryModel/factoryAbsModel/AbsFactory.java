package designModel.FactoryModel.factoryAbsModel;

import abstracts.Pizza;

public interface AbsFactory {

    public Pizza createPizza(String orderType);
}
