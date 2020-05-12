package designModel.BuilderPatternModel;

/**
 * 建造者
 */
public abstract class HouseBuilder {

    private House house = new House();

    private String baise;
    private String wall;
    private String roofed;

    public abstract void buildbaise();

    public abstract void buildwall();

    public abstract void buildroofed();

    public House buildHouse() {
        return house;
    }

}
