package pers.utils.jsonUtils;

import net.sf.json.JSONObject;

public class JsonObjectBuilder {
    private JSONObject result = new JSONObject();

    public static JsonObjectBuilder custom() {
        return new JsonObjectBuilder();
    }

    public JsonObjectBuilder put(Object key, Object value) {
        result.put(key, value);
        return this;
    }

    public JsonObjectBuilder put(Object key) {
        result.put(key, "");
        return this;
    }

    public JSONObject bulid() {
        return this.result;
    }


}
