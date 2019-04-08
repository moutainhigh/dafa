package pers.dafacloud.concurrent;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Testws {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket();
        SocketAddress socketAddr = new InetSocketAddress("ws://app.dfcdn5.com/gameServer/?TOKEN=9311d2d8148a4ffb973205672d4d35d8&gameId=2002", 80);
        socket.connect(socketAddr);

            // 根据约定和协议 连接服务器端的socket
            //Socket socket = new Socket("ws://app.dfcdn5.com", 8080);
            // 根据socket对象像服务器端写入数据
            OutputStream os = socket.getOutputStream();
            os.write("TcpClient:你好服务端我正向你发送请求".getBytes());

            // 读取服务器端的数据
            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[200];
            int length = is.read(buffer);
            System.out.println("内容" + new String(buffer, 0, length));
            // 关闭资源
            is.close();
            os.close();
            socket.close();
        }


}
