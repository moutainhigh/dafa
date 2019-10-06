package dafagame;


import com.alibaba.nacos.common.util.Md5Utils;
import org.apache.commons.lang.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

//import io.netty.util.internal.StringUtil;

/**
 * @Author jianguo
 * @Date 2019/09/23
 */
public class AESUtils {


    private static final String encodeRules1 = "fzsrdg=fGYUVawef+43BKJ234maenkju99oni9u9yh234=-lnvlrnmvchemLKMKNJBVGjbjjhbjhbg4sf83mteg7==glBUYEaw4g4fqvsv1234rsb";
    private static final String encodeRules2 = "sdfgvm-=98678*&^cemnnLKNEnkljnENkjEKNEV+lknvrklsKJNVjkSVNkjkjnlKJNNKJjftyjCXQAZljkhhbJKHbHJKBVNKJJSBKJJNSKJENVv=+Ev";
    private static final String encodeRules3 = "TSEWHCVCBLuniuyGFDECLUADGCtyACEuy*^Rjvd+_vd-+_Dve=+GHCTEb,jJK NJVhbJKHEBQAZAztesWXZHKTVtygCGFRDytfTRdRDDGfGHfdrGRfdrkEVJHrv";
    private static final String encodeRules4 = "zwSExdfCgfregHVtCfg^$3GMh kj)+KJ vgCRrehB,jhJ(JVB<CFGNKJ>cnfggfcxnJ<BdfxyuguiluhTYerhsTFKYTRHbhj,jfytVJvregfcghjCXHDFS";
    private static final String encodeRules5 = "QIASNiDBhjdSHBD&TD%$^DS_)DF+_+_DSc98up89SDVbkuySDV=-od8v7y6754dvblyudsv54u6SDVbkuyfv 56u&BLvsybktsdkuygvgvykSVp9j0-SIDVU7hy";
    private static final String encodeRules6 = "LJgheuihglyEVbul,EVCLiysdvBHJ<.SDVUYB<LSDV_09iSD&y089SDCH(IgtrgtrbrvYUBEGUYgbuEKV09esuv9*UE*7yo*SEVybkuySDEV <hjSDVE dc";
    private static final String encodeRules7 = "gULYGB&CI^7uESVUYlSDBCINVLUESVUILHnGVEULcgyuEGSVUkELGIUVGHUKYEWGVKUYGEUYKVGkEGVUYKguygbuyclyibvjrbvuieyg89WEY77t67-9i9";
    private static final String encodeRules8 = "%^&I(CE-eriv0[odvhnouiSEVbuk,r0e-wiovIVHGiuYKUGVEf98E&^Vr56EV89i+E_Vo8hy0hkuVS=-=oedvHGKUYEGvuygSDYVUguysuyuyGSEVKyugku";
    private static final String encodeRules9 = "QBCW<JbjuILSDVHLIUaer;guv;9dfaw84fh9iu83y87y&^AT76f320r9j(*Ho8(*HEf&*SDOYvHLUISDVeuilbuSEV=-oSEV0-o387tge76gSEVgulysdviul";
    private static final String encodeRules0 = "btijlnr euuregvdfbdrGHKEJKeklkBJHVG+_)(E^&56%^56$%g7yBYcjtXHRTKFGY5f6s43SYtTYJ+_)io=gkuyfcthXthrk,uvyCHTRserxYFJTGRDesgEdytDFrty";

    public static String getEncodeRules(Integer hashCode, String userName) {
        switch (hashCode % 10) {
            case 1:
                return encodeRules1 + userName;
            case 2:
                return encodeRules2 + userName;
            case 3:
                return encodeRules3 + userName;
            case 4:
                return encodeRules4 + userName;
            case 5:
                return encodeRules5 + userName;
            case 6:
                return encodeRules6 + userName;
            case 7:
                return encodeRules7 + userName;
            case 8:
                return encodeRules8 + userName;
            case 9:
                return encodeRules9 + userName;
            default:
                return encodeRules0 + userName;
        }
    }

    public static String getEncodeRulesRegister(String userName) {
        return Md5Utils.getMD5(userName.getBytes());
    }


    /**
     * 加密
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static String AESEncodeDatabase(String content, String userName) throws Exception {
        return AESUtils.AESEncode(content, AESUtils.initKeyRegister(AESUtils.getEncodeRules(userName.hashCode(), userName)));
    }

    /**
     * 解密
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static String AESDecodeDatabase(String content, String userName) throws Exception {
        return AESUtils.AESDecode(content, AESUtils.initKeyRegister(AESUtils.getEncodeRules(userName.hashCode(), userName)));
    }


    /**
     * 注册获取密钥
     *
     * @return
     * @throws Exception
     */
    public static byte[] initKeyRegister(String encodeRules) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
//2.根据ecnodeRules规则初始化密钥生成器
//生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encodeRules.getBytes());
        keygen.init(128, random);
//3.产生原始对称密钥
        SecretKey original_key = keygen.generateKey();
//4.获得原始对称密钥的字节数组
        return original_key.getEncoded();
    }

    /**
     * 加密
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static String AESEncode(String content, byte[] encodeRules) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            if (StringUtils.isEmpty(content)) {
                return content;
            }
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(encodeRules, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String AES_encode = new String(Base64.getEncoder().encodeToString(byte_AES));
            //11.将字符串返回
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//如果有错就返加nulll
        return null;
    }


    /**
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static String AESDecode(String content, byte[] encodeRules) {
        try {
            if (StringUtils.isEmpty(content)) {
                return content;
            }
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(encodeRules, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = Base64.getDecoder().decode(content);
            /*
             * 解密
             */
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        //如果有错就返加nulll
        return null;
    }

    public static void main(String[] args) throws Exception{
        System.out.println(AESUtils.AESEncode("0d11081f25c023608c9f4281cf01239b", "01922cbeae89ad4d79ab769e84e7c5da".getBytes()));
        System.out.println(MD5Util.getMd5("0701"));

        Md5Utils.getMD5("duke123".getBytes());
        AESUtils.AESEncode(MD5Util.getEncryptionInformation("duke123"),"0701".getBytes());

    }


    /**
     * byte数组转化为16进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }
}


