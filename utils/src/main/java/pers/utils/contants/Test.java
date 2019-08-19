package pers.utils.contants;

public class Test {
    private final static Profile profile = Selector.getProfile();

    public final static void main(String[] args){
        System.out.println(profile.gitUrl());
    }
}
