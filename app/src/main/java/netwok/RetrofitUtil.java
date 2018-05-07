package netwok;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RetrofitUtil {
    static Retrofit retrofit;
    static ChexingServerService chexingServerService;
    public static Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit = new Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                    .baseUrl(Url.SERVER_URL)
                    .build();
        }
        return retrofit;
    }
    public static ChexingServerService getServerService(){
        if (chexingServerService==null){
            chexingServerService = getRetrofit().create(ChexingServerService.class);
        }
        return chexingServerService;
    }

    public static void getDataRetrofit(Observable observable, final CallBackRetrofit callBackRetrofit){
        observable.subscribeOn(Schedulers.newThread()) //请求在新的线程中执行
                .observeOn(Schedulers.io())          //请求完成后在io线程中执行
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody baseBean) {
                        //DubToastUtils.showToastNew("call");
//                        saveUserInfo(loginBean);//保存用户信息到本地
                        callBackRetrofit.callback(true,baseBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //DubToastUtils.showToastNew("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        //DubToastUtils.showToastNew("onError");
                        callBackRetrofit.onFail(false,-1+"","加载失败");
                    }

                    @Override
                    public void onNext(ResponseBody baseBean) {
                        callBackRetrofit.onResult(true,baseBean);
                        /*//请求成功
                        //DubToastUtils.showToastNew("onNext");
                        if (baseBean.getCode()==Url.sussessRequest){
                            callBackRetrofit.onResult(true,baseBean);
                        }else{
                            callBackRetrofit.onFail(false,baseBean.getCode()+"",baseBean.getMsg());
                        }*/
                    }
                });
    }
}