package netwok;


import baseBean.LoginBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ChexingServerService {
    @POST("/api/authorizations")
    @FormUrlEncoded
    @Headers({"Accept:"+ Url.ACCEPT})
    Observable<LoginBean> userLogin(@Field("username") String userName, @Field("password") String password);

}
