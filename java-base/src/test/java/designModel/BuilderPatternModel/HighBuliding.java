package designModel.BuilderPatternModel;

/**
 * 具体建造者(工人)
 * */
public class HighBuliding extends HouseBuilder {

    @Override
    public void buildbaise() {
        System.out.println("HighBuliding buildbaise");
    }

    @Override
    public void buildwall() {
        System.out.println("HighBuliding buildwall");
    }

    @Override
    public void buildroofed() {
        System.out.println("HighBuliding buildroofed");
    }
}
