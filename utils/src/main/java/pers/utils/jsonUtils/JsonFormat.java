package pers.utils.jsonUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonFormat {
    /**
     * json格式化输出
     */
    public static String formatPrint(String value) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(value);
            return JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            return "json转换失败: " + value;
        }
    }
}
