package dubo.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//Path 示例源码见 Example01.java
public class PostActivity extends AppCompatActivity {
    public interface BlogService {
        /**
         * 2 .接口定义
         */
        @POST("/api/in_orders")
        @Headers({"Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTAxLjEzMi4xOTAuOTYvYXBpL2F1dGhvcml6YXRpb25zIiwiaWF0IjoxNTI1NjgyMDY1LCJleHAiOjE1MjU2OTI4NjUsIm5iZiI6MTUyNTY4MjA2NSwianRpIjoieXNKdXoxRkZOeFYwVmtOViIsInN1YiI6MSwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.Swj2RxfBI9fWobcEWLpKUvdt-GXQQf-fg0JbyhUAIbY",
                "Accept: application/prs.larabbs.v1+json"})
        @FormUrlEncoded
        Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1 创建Retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://101.132.190.96")
                        .build();

                // 3.BlogService的代理对象
                BlogService service = retrofit.create(BlogService.class);
                Map<String, Object> map = new HashMap<>();
                map.put("supplier_id", "");
                //map.put("age", "123456");
                map.put("number", "京D1115");

                // 4.接口调用   第一种写法
                final Call<ResponseBody> call2 = service.testFormUrlEncoded2(map);
                //ResponseBodyPrinter.printResponseBody(call2);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            Response<ResponseBody> response = call2.execute();
                            if (response.isSuccessful()) {
                                //handler.sendMessage(msg);
                                System.out.println(response.body().string());
                            } else {
                                System.err.println("HttpCode:" + response.code());
                                System.err.println("Message:" + response.message());
                                System.err.println(response.errorBody().string());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                // 4.接口调用   第二种写法
                /*call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            System.out.println(response.body().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });*/
            }
        });
    }
}
