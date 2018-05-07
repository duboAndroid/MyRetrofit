package dubo.myretrofit;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static Context context;
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //ViewTarget.setTagId(R.id.tag_glide);
        context = this;
        //ExceptionHandlerHelper.getInstance().init(this);
        instance = this;
        //initImageLoaderCongruation();//ImageLoader
        inttOkhttp();//okhttpUtils
        //CrashReport.initCrashReport(getApplicationContext(), "6ebd4aa6eb", true);//Bugly
        //DubPreferenceUtils.putString(context, Url.currentVersion, StephenToolUtils.getCurrentVersionName(context));//记录版本
    }

    private void inttOkhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //.addInterceptor(new ArgsInterceptor()) //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 返回全局content
     */
    public static MyApplication getInstance() {
        return instance;
    }
}