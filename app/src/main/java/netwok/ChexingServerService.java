package netwok;


import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ChexingServerService {
    @POST("/api/authorizations")
    @FormUrlEncoded
    @Headers({"Accept:"+ Url.ACCEPT})
    Observable<ResponseBody> userLogin(@Field("username") String userName, @Field("password") String password);
    //Call<ResponseBody> userLogin(@Field("username") String userName, @Field("password") String password);

}
