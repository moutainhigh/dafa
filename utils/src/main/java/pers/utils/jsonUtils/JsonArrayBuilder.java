package pers.utils.jsonUtils;

import net.sf.json.JSONArray;

public class JsonArrayBuilder {

    private JSONArray jsonArray = new JSONArray();

    public static JsonArrayBuilder custom() {
        return new JsonArrayBuilder();
    }

    public JsonArrayBuilder addObject(Object o) {
        jsonArray.add(o);
        return this;
    }

    public JSONArray bulid() {
        return this.jsonArray;
    }

}
