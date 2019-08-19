package pers.utils.contants;

public class Selector {
    private static Profile profile = new TestProfile();

    final static Profile getProfile(){
        return profile;
    }
}
