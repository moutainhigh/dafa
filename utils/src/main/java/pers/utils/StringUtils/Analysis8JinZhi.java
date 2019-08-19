package pers.utils.StringUtils;

import java.io.UnsupportedEncodingException;

public class Analysis8JinZhi {


    public static void main(String[] args) throws Exception {
        System.out.println(getOct("\\346\\235\\234\\345\\205\\213\\345\\205\\205\\345\\200\\274"));
    }

    /**
     * 8进制转中文
     * */
    public static String getOct(String s) throws UnsupportedEncodingException
    {
        String[] as = s.split("\\\\");
        byte[] arr = new byte[as.length - 1];
        for (int i = 1; i < as.length; i++)
        {
            int sum = 0;
            int base = 64;
            for (char c : as[i].toCharArray())
            {
                sum += base * ((int)c - '0');
                base /= 8;
            }
            if (sum >= 128) sum = sum - 256;
            arr[i - 1] = (byte)sum;
        }
        return new String(arr,"UTF-8"); //如果还有乱码，这里编码方式你可以修改下，比如试试看unicode gbk等等
    }



}
