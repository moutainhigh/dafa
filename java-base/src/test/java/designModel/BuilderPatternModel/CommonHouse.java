package designModel.BuilderPatternModel;

/**
 * 具体建造者(工人)
 */
public class CommonHouse extends HouseBuilder {

    @Override
    public void buildbaise() {
        System.out.println("CommonHouse buildbaise");
    }

    @Override
    public void buildwall() {
        System.out.println("CommonHouse buildwall");
    }

    @Override
    public void buildroofed() {
        System.out.println("CommonHouse buildroofed");
    }
}
