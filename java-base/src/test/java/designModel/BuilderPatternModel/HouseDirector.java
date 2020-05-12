package designModel.BuilderPatternModel;

/**
 * 指挥者
 */
public class HouseDirector {
    HouseBuilder houseBuilder;

    public HouseDirector(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    public House constructHouse() {
        houseBuilder.buildbaise();
        houseBuilder.buildroofed();
        houseBuilder.buildwall();
        return houseBuilder.buildHouse();
    }

}
