package pers.dafacloud.enums;

public enum EV {

    DEV1_IP("dev1Dafa","dev1","dev1Tenant", "52.76.195.164", "", "/users/dev1DafaIP.txt", "/usersTenant/dev1DafaIP.txt", true),
    DEV2_IP("dev2Dafa","dev2","dev2Tenant", "52.77.207.64", "", "/users/dev2DafaIP.txt", "/users/dev2DafaIPTenant.txt", true),
    DEV1("dev1Dafa","dev1","", "caishen02", "/users/dev1Dafa.txt", "", "", false),
    DEV2("dev2Dafa","dev2","", "caishen03", "/users/dev2Dafa.txt", "", "", false),
    PRE("preDafa","pre","", "dafacloud-pre", "/users/preDafa.txt", "", "/users/preTenantDafa.txt", false),
    ;

    public String evName;
    public String evCode;
    public String evPointTenant;
    public String host;
    public String users;
    public String userIP;
    public String userTenantIP;
    public boolean isIP;

    EV(String evName,String evCode,String evPointTenant, String host, String users, String userIP, String userTenantIP, boolean isIP) {
        this.evName = evName;
        this.evCode = evCode;
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
