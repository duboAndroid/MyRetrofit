/*
 * Copyright 2016 ikidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dubo.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Query、QueryMap、Url 示例源码见 Example05.java
 *
 * [Retrofit注解详解 之 FormUrlEncoded/Field/FieldMap注解]源码
 */
public class QueryActivity extends Activity{
    public interface BlogService {
        /**
         * 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
         * 对于Query和QueryMap，如果不是String（或Map的第二个泛型参数不是String）时
         * 会被默认会调用toString转换成String类型
         * Url支持的类型有 okhttp3.HttpUrl, String, java.net.URI, android.net.Uri
         * {@link retrofit2.http.QueryMap} 用法和{@link retrofit2.http.FieldMap} 用法一样，不再说明
         */
        @GET //当有URL注解时，这里的URL就省略了
        Call<ResponseBody> testUrlAndQuery(@Url String url, @Query("showAll") boolean showAll);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    public void sendData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .build();

        BlogService service = retrofit.create(BlogService.class);

        //演示 @Headers 和 @Header
        Call<ResponseBody> call1 = service.testUrlAndQuery("headers",false);
        printResponseBody(call1);
    }

    public void printResponseBody(final Call<ResponseBody> call2){
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
    }
}
