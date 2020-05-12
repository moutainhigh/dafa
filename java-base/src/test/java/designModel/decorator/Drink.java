package designModel.decorator;

import lombok.Data;

@Data
public abstract class Drink {
    public String desc;
    public float price = 0.0f;

    public abstract float cost();
}
