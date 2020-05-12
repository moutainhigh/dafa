package designModel.decorator;

/**
 * https://www.jianshu.com/p/9922bf82be34
 * */
public class Client {

    //装饰者模式
    public static void main(String[] args) {

        Drink drink =  new LongBlack();
        System.out.println(drink.getDesc() + drink.getPrice());

        drink = new Milk(drink);
        System.out.println(drink.getDesc() + drink.getPrice());

        drink = new Chocolate(drink);
        System.out.println(drink.getDesc() + " : "+ drink.cost());

        System.out.println("==========================================");

        Decorator decorator = new Milk(drink);
        System.out.println(decorator.getDesc());

        decorator = new Chocolate(decorator);
        System.out.println(decorator.getDesc() + " : "+ decorator.cost());

        decorator = new Chocolate(decorator);
        System.out.println(decorator.getDesc() + " : "+ decorator.cost());


    }
}
