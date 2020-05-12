package designModel.decorator;

public class Chocolate extends Decorator {

    public Chocolate(Drink drink) {
        super(drink);
        setDesc("巧克力");
        setPrice(1.0f); //调味品的价格
    }
}
