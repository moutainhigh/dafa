package pers.utils.jsonUtils;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/11/30.
 * json转对象，对象转json
 */
public class JsonOfObject {
    //private final static Logger logger = LoggerFactory.getLogger(JsonOfObject.class);
    private final static Gson gson = new Gson();
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
//            logger.error(LogUtil.error(e));
            e.printStackTrace();
        } finally {
            return v;
        }
    }

    public final static JsonObject getJsonObj(String des){
        return parser.parse(des).getAsJsonObject();//JsonObject object = new JsonParser().parse("").getAsJsonObject();
    }
}
