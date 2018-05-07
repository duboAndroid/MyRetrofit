package netwok;

public class Url {
    public static final int eachPageSize = 10;//分页大小
    public static final int sussessRequest = 1000;//请求成功
    public static final int refreshTokenCode = 9999;//刷新token
    public static final int msgLoginTimeOut = 12;
    public static final String domain = "domain";//七牛前缀
    public static final String token = "token";//token
    public static final String UserName = "UserName";//用户名
    public static final int failRequest = -1;
    public static final String ACCEPT = "application/prs.larabbs.v1+json";//Accept

    //常量
    public static final String One = "1",Two = "2",Three = "3",Four = "4",Five = "5";

    public static final int OpenStorageDetail = 1001,BaiduOCR = 1002;
    //url
    public static final String SERVER_URL = "http://101.132.190.96";

    static String user_login = SERVER_URL + "/api/authorizations";                                  //用户登录
}
