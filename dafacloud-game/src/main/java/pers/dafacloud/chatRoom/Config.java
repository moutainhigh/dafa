package pers.dafacloud.chatRoom;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.HandshakeResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * websocket设置请求头
 * */
public class Config extends ClientEndpointConfig.Configurator {

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
        //headers.put("Pragma", Arrays.asList("no-cache"));
        headers.put("Origin", Arrays.asList("http://"+StartChatRoomWs.host));
        //headers.put("host", Arrays.asList("m.caishen02.com"));
        //headers.put("Accept-Encoding", Arrays.asList("gzip, deflate, br"));
        //headers.put("Accept-Language", Arrays.asList("en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4"));
        headers.put("User-Agent", Arrays.asList("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"));
        headers.put("Upgrade", Arrays.asList("websocket"));
        //headers.put("Cache-Control", Arrays.asList("no-cache"));
        //headers.put("Connection", Arrays.asList("Upgrade"));
        //headers.put("Sec-WebSocket-Version", Arrays.asList("13"));
    }

    @Override
    public void afterResponse(HandshakeResponse hr) {
        //Map<String, List<String>> headers = hr.getHeaders();
        //log.info("headers -> "+headers);
    }

}

