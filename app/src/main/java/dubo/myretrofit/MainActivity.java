package dubo.myretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import baseBean.LoginBean;
import netwok.ChexingServerService;
import netwok.Url;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*findViewById(R.id.loging).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChexingServerService serverService = RetrofitUtil.getServerService();
                Observable observable = serverService.userLogin("13012340001","123456");
                RetrofitUtil.getDataRetrofit(observable, new CallBackRetrofit() {
                    @Override
                    public void onResult(boolean isSuccess, ResponseBody baseBean) {
                        if (isSuccess){

                        }
                    }

                    @Override
                    public void onFail(boolean isSuccess, String code, String msg) {
                        if (isSuccess){

                        }
                    }

                    @Override
                    public void callback(boolean isSuccess, ResponseBody baseBean) {
                        if (isSuccess){

                        }
                    }
                });
            }
        });*/

        findViewById(R.id.loging).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }


    private void sendData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Url.SERVER_URL+"/")
                .build();
        ChexingServerService serverService = retrofit.create(ChexingServerService.class);
        serverService.userLogin("13012340001","123456")      //获取Observable对象
                .subscribeOn(Schedulers.newThread())    //请求在新线程中执行
                .observeOn(Schedulers.io())             //请求完成后在io线程中执行
                .doOnNext(new Action1<LoginBean>() {
                    @Override
                    public void call(LoginBean baseBean) {
                        Log.i("TAG",baseBean.toString());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        Log.i("TAG",loginBean.toString());
                    }
                });
    }
}
