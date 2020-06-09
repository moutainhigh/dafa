package pers.utils.Md5HA1;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCrossDomainUtil {
	
	//初始向量（偏移）
    public static final String GAMEAPP_VIPARA = "0123456789101112";
    //私钥  （密钥）
    private static final String GAMEAPP_ASE_KEY="d,a.f@a%t&o(k)e?";

    private static Cipher cipherEncrypt = null;
        
    private static IvParameterSpec zeroIv = new IvParameterSpec(GAMEAPP_VIPARA.getBytes());
    
    //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
    private static SecretKeySpec key = new SecretKeySpec(GAMEAPP_ASE_KEY.getBytes(), "AES");

    private static Cipher initCipher(int opmode) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
        //初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
        cipher.init(opmode, key, zeroIv);
        return cipher;
    }
    
    public static void main(String[] args) throws Exception {
    	String info = "444";
		System.out.println(gameEncrypt(info));
		System.out.println(decrypt("S2Rcp/FUqCSW3KNnrBGMmuAtZAKzNm4t91nDTQnoKi7UGuMSyyJEa+/2IVniZh51"));
	}

    /**
     * 加密
     * @param cleartext 加密前的字符串
     * @return 加密后的字符串
     */
    public static String gameEncrypt(String cleartext) {
        try {
            //加密后的字节数组
            if(cipherEncrypt == null){
            	cipherEncrypt = initCipher(Cipher.ENCRYPT_MODE);
            }
            byte[] encryptedData = cipherEncrypt.doFinal(cleartext.getBytes("UTF-8"));
            //对加密后的字节数组进行base64编码
            byte[] base64Data = org.apache.commons.codec.binary.Base64.encodeBase64(encryptedData);
            //将base64编码后的字节数组转化为字符串并返回
            return new String(base64Data);
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String decrypt(String content){  
        try {  
        	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
            //初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] encrypted1 = org.apache.commons.codec.binary.Base64.decodeBase64(content);
            try {  
                byte[] original = cipher.doFinal(encrypted1);  
                String originalString = new String(original);  
                return originalString;  
            } catch (Exception e) {  
                System.out.println(e.toString());  
                return null;  
            }  
        } catch (Exception ex) {  
            return null;  
        }  
    } 
}
