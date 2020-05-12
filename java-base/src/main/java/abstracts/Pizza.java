package abstracts;

import lombok.Data;

@Data
public abstract class Pizza {

    private String name;

    public abstract void prepare();

    public void bake() {
        System.out.println(name + "烘烤");
    }

    public void cut() {
        System.out.println(name + "切块");
    }

    public void box() {
        System.out.println(name + "打包");
    }
}
