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
import retrofit2.http.HTTP;
import retrofit2.http.Path;

/**
 * [Retrofit注解详解 之 HTTP注解]源码
 */
public class GetActivityInner extends Activity{
    public interface BlogService {
        /**
         * method 表示请求的方法，区分大小写，retrofit 不会做处理
         * path表示路径
         * hasBody表示是否有请求体
         */
        @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
        Call<ResponseBody> getBlog(@Path("id") int id);
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

    public static void sendData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .build();

        BlogService service = retrofit.create(BlogService.class);
        final Call<ResponseBody> call = service.getBlog(2);
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Response<ResponseBody> response = call.execute();
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


