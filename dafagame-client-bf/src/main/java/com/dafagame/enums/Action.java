package com.dafagame.enums;

public enum Action {
    Compare(1, 0.1),
    DarkCardAdd(1, 0.04),
    ClearCardFollow(1, 0.04),
    ClearCardAdd(1, 0.06),
    DarkCardFollow(1, 0.02),
    ;

    public final int x;

    public final double values;

    Action(int x, double values) {
        this.x = x;
        this.values = values;
    }


    public static double getValues(int index) {
        if (index > Action.values().length || index < 0) {
            throw new IllegalArgumentException("index必须在[0,4]范围内");
        }
        return values()[index].x * values()[index].values;
    }
}