package pers.utils.jsonUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonFormat {
    //json格式化输出
    public static String formatPrint(String value) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(value);
            String pretty = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);
            return pretty;
        } catch (Exception e) {
            return "json转换失败: " + value;
        }
    }
}
