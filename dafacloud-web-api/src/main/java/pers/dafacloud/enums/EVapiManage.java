package pers.dafacloud.enums;

public enum EVapiManage {

    DEV1_CMS("pt05.dafacloud-test", false),
    PRE_CMS("pt.dafacloud-pre", false),
    DEV1_IP("52.76.195.164", true),
    DEV2_IP("52.77.207.64", true),
    DEV1("caishen02", true),
    DEV2("caishen03", true),
    PRE("dafacloud-pre", true),
    PRO("dafacloud-master", true),
    PRE2("app2jsknasx", true),
    ;
    public String host;
    public boolean isFront;

    EVapiManage(String host, boolean isFront) {
        this.host = host;
        this.isFront = isFront;
    }

    public static EVapiManage getEVapiManage(String host) {
        for (EVapiManage ev : values()) {
            if (host.contains(ev.host))
                return ev;
        }
        return null;
    }
}
