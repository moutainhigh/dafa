package designModel.decorator;

public class Decorator extends Drink {
    private Drink drink;

    public Decorator(Drink drink) {
        this.drink = drink;
    }

    @Override
    public float cost() {
        return super.getPrice() + drink.cost();
    }

    @Override
    public String getDesc() {
        return drink.getDesc() + " " + " & " + super.desc + super.getPrice();
    }
}
