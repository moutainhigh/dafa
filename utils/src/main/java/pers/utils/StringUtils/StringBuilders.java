package pers.utils.StringUtils;

public class StringBuilders {

    StringBuilder sb =new StringBuilder();


    public static StringBuilders custom(){
        return new StringBuilders();
    }

    public StringBuilders add(Object name,Object value){
        sb.append(name);
        sb.append(":");
        sb.append(value);
        sb.append("，，");
        return this;
    }

    public StringBuilders add(String value){
        sb.append(value);
        sb.append(",");
        return this;
    }

    public String build(){
        return sb.substring(0,sb.length()-1);
    }

    public static void main(String[] args) {
        String s = StringBuilders.custom()
                .add("20312发牌通知")
                .add("a","1")
                .add("b","2")
                .build();
        System.out.println(s);
    }
}
