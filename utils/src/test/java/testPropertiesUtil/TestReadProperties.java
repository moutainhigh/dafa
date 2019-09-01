package testPropertiesUtil;

import pers.utils.propertiesUtils.PropertiesUtil;

public class TestReadProperties {

    public static void main(String[] args) {

        String s= PropertiesUtil.getProperty("testCookie.properties","host");
        String s2= PropertiesUtil.getProperty("testCookie.properties","host");
        System.out.println(s);
        System.out.println(s2);

    }
}
