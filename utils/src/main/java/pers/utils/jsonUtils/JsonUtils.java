package pers.utils.jsonUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/11/30.
 */
public class JsonUtils {
    //private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private final static Gson gson = new Gson();
    private final static ObjectMapper mapper = new ObjectMapper();
    private final static JsonParser parser = new JsonParser();
    public final static String objToJson(Object obj){
        String json = gson.toJson(obj);
        return json;
    }

    public final static <V>V jsonToObj(Class<V> vClass,String json){
        V v = null;
        try {
            v = gson.fromJson(json,vClass);
        } catch (Exception e) {
            //logger.error(LogUtil.error(e));
            e.printStackTrace();
        } finally {
            return v;
        }
    }

    public final static LinkedTreeMap objToLinkedMap(Object object) {
        return mapper.convertValue(object,LinkedTreeMap.class);
    }

    public final static <V>V linkedMapToObj(Class<V> vClass,LinkedTreeMap linkedTreeMap) {
        return jsonToObj(vClass,gson.toJson(linkedTreeMap));
    }

    public final static <V>JsonObject toJson(LinkedTreeMap linkedTreeMap){
        return parser.parse(gson.toJson(linkedTreeMap)).getAsJsonObject();
    }
}

