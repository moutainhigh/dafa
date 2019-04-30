package pers.dafacloud.utils.enums;

public enum Path {

    login("/v1/users/login")
    ,info("b1")
    ,betting("/v1/betting/addBetting")
    ,rebate("/v1/users/rebate")
    ,getServerTimeMillisecond("/v1/betting/getServerTimeMillisecond")
    ;


    public String value;
    //构造方法
    Path(String value) {
        this.value = Environment.DEFAULT.url+value;
    }

    public static void main(String[] args) {
        Path path = Path.login;
        System.out.println(path.value);
        System.out.println(Path.login);
    }

}
