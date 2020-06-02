package pers.dafacloud.enums;

public enum EV {

    DEV1_IP("dev1Dafa","dev1Tenant", "52.76.195.164", "", "/users/dev1DafaIP.txt", "/users/dev1DafaIPTenant.txt", true),
    DEV2_IP("dev2Dafa","dev2Tenant", "52.77.207.64", "", "/users/dev2DafaIP.txt", "/users/dev2DafaIPTenant.txt", true),
    DEV1("dev1Dafa","", "caishen02", "/users/dev1Dafa.txt", "", "", false),
    DEV2("dev2Dafa","", "caishen03", "/users/dev2Dafa.txt", "", "", false),
    PRE("preDafa","", "dafacloud-pre", "/users/preDafa.txt", "", "", false),
    ;

    public String evName;
    public String evPointTenant;
    public String host;
    public String users;
    public String userIP;
    public String userTenantIP;
    public boolean isIP;

    EV(String evName,String evPointTenant, String host, String users, String userIP, String userTenantIP, boolean isIP) {
        this.evName = evName;
        this.evPointTenant = evPointTenant;
        this.host = host;
        this.users = users;
        this.userIP = userIP;
        this.userTenantIP = userTenantIP;
        this.isIP = isIP;
    }

    public static EV getEV(String host) {
        for (EV ev : values()) {
            if (host.contains(ev.host))
                return ev;
        }
        return null;
    }
}
