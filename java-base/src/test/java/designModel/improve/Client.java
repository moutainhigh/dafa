package designModel.improve;

/**
 * 策略模式
 */
public class Client {
    public static void main(String[] args) {
        BjDuck bjDuck = new BjDuck();
        bjDuck.fly();
        bjDuck.setFlyBehavior(new BadFlyBehavior());
        bjDuck.fly();
    }

}
