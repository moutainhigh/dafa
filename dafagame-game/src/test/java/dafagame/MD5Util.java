package dafagame;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 *
 * @author jianguo
 */
public class MD5Util {

    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @return
     * @Comment SHA1实现
     * @Author Ron
     * @Date 2017年9月13日 下午3:30:36
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 对于电话号码进行加密
     *
     * @param phone
     * @return
     */
    public static String getMd5Phone(String phone) {
        return phone.substring(0, phone.length() - 9) + MD5Util.getMd5(phone.hashCode() + MD5Util.getMd5(phone.substring(2, phone.length() - 2))) + phone.substring(phone.length() - 2, phone.length());
    }

    /**
     * 对密码或是安全密码加密
     *
     * @param encryption
     * @param encryption
     * @return
     */
    public static String getEncryptionInformation(String encryption) throws Exception {
        return MD5Util.getMd5(MD5Util.getMd5(encryption) + MD5Util.shaEncode(encryption));
    }

    /**
     * 针对于上传上来的密码进行解密处理
     *
     * @param plainText
     * @param userName
     * @param random
     * @param type      1登录/2注册
     * @return
     */
    public static String getComparisonDataBase(String plainText, String userName, String random, int type) throws Exception {
        if (type == 1) {
            return MD5Util.getMd5(AESUtils.AESDecodeDatabase(plainText, userName) + random);
        } else {
            return AESUtils.AESDecode(plainText, random.getBytes());
        }
    }

    /**
     * 针对于上传上来的密码进行加密处理
     *
     * @param plainText
     * @param userName
     * @return
     */
    public static String handleComparisonDataBase(String plainText, String userName) throws Exception {
        return AESUtils.AESEncodeDatabase(plainText, userName);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(
                MD5Util.getComparisonDataBase("+zVtEvOh+vUepycfuWvmfs6QHLrL/z12FAyEqK9q7BHe4Ksu5aPTVDWlo+L5BKbx",
                        "", MD5Util.getMd5("0848"), 2));
        System.out.println(MD5Util.getEncryptionInformation("888316"));


        System.out.println(AESUtils.AESEncode(MD5Util.getEncryptionInformation("duke123"), MD5Util.getMd5("").getBytes()));
        System.out.println(MD5Util.getEncryptionInformation("duke123"));

    }

}
