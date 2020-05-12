package designModel.improve;

public class BadFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("不是特别擅长飞翔");
    }
}
