package designModel.improve;

import lombok.Data;

@Data
public abstract class Duck {
    //属性，策略接口
    FlyBehavior flyBehavior;

    public void fly(){
        if(flyBehavior!=null){
            flyBehavior.fly();
        }
    }
}
