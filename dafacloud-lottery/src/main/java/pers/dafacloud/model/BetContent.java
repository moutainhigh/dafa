package pers.dafacloud.model;

public class BetContent {
    //Betting.bet("bettingData=[{\"lotteryCode\":\"1205\",\"playDetailCode\":\"1205A11\",\"bettingNumber\":\"1,-,-\",\"bettingCount\":1,\"bettingAmount\":2,\"bettingPoint\":\"3\",\"bettingUnit\":1,\"bettingIssue\":\"20190426124\",\"graduationCount\":1}]");
    //彩种
    private String lotteryCode;//
    //玩法
    private String playDetailCode;
    //号码
    private String bettingNumber;
    //注数
    private int bettingCount;
    //金额
    private double bettingAmount;
    //返点
    private String bettingPoint;
    //金额模式
    private double bettingUnit;
    //期号
    private String bettingIssue;
    //倍数
    private int graduationCount;

    //描述
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getPlayDetailCode() {
        return playDetailCode;
    }

    public void setPlayDetailCode(String playDetailCode) {
        this.playDetailCode = playDetailCode;
    }

    public String getBettingNumber() {
        return bettingNumber;
    }

    public void setBettingNumber(String bettingNumber) {
        this.bettingNumber = bettingNumber;
    }

    public int getBettingCount() {
        return bettingCount;
    }

    public void setBettingCount(int bettingCount) {
        this.bettingCount = bettingCount;
    }

    public double getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(double bettingAmount) {
        this.bettingAmount = bettingAmount;
    }

    public String getBettingPoint() {
        return bettingPoint;
    }

    public void setBettingPoint(String bettingPoint) {
        this.bettingPoint = bettingPoint;
    }

    public double getBettingUnit() {
        return bettingUnit;
    }

    public void setBettingUnit(double bettingUnit) {
        this.bettingUnit = bettingUnit;
    }

    public String getBettingIssue() {
        return bettingIssue;
    }

    public void setBettingIssue(String bettingIssue) {
        this.bettingIssue = bettingIssue;
    }

    public int getGraduationCount() {
        return graduationCount;
    }

    public void setGraduationCount(int graduationCount) {
        this.graduationCount = graduationCount;
    }

    @Override
    public String toString() {
        return "bettingData=[{" +
                "\"lotteryCode\":\"" + lotteryCode  +
                "\",\"playDetailCode\":\"" + playDetailCode  +
                "\",\"bettingNumber\":\"" + bettingNumber  +
                "\",\"bettingCount\":" + bettingCount +
                ",\"bettingAmount\":" + subZeroAndDot(String.valueOf(bettingAmount)) +
                ",\"bettingPoint\":\"" + subZeroAndDot(String.valueOf(bettingPoint))  +
                "\",\"bettingUnit\":" + subZeroAndDot(String.valueOf(bettingUnit)) +
                ",\"bettingIssue\":\"" + bettingIssue  +
                "\",\"graduationCount\":" + graduationCount +
                "}]";
    }
    /**
     * 使用java正则表达式去掉double多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
