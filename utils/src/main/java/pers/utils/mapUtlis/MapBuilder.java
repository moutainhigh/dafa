package pers.utils.mapUtlis;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {

    private Map<String, String> map = new HashMap<>();

    public static  MapBuilder custom(){
        return  new MapBuilder();
    }

    public MapBuilder put(String key,String value){
        map.put(key,value);
        return this;
    }

    public Map<String, String> build(){
        return this.map;
    }

}
