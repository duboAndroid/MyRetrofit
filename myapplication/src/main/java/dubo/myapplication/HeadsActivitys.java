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
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Header和Headers 示例源码见 Example04.java
 *
 * [Retrofit注解详解 之 FormUrlEncoded/Field/FieldMap注解]源码
 */
public class HeadsActivitys extends Activity{
    public interface BlogService {
        @GET("/headers?showAll=true")
        @Headers({"CustomHeader1: customHeaderValue1", "CustomHeader2: customHeaderValue2"})
        Call<ResponseBody> testHeader(@Header("CustomHeader3") String customHeaderValue3);

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
        Call<ResponseBody> call1 = service.testHeader("ikidou");
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
