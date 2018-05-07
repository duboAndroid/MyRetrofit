package netwok;

import okhttp3.ResponseBody;

public abstract class CallBackRetrofit {
    public abstract void onResult(boolean isSuccess, ResponseBody baseBean);
    public abstract void onFail(boolean isSuccess, String code, String msg);
    public abstract void callback(boolean isSuccess, ResponseBody baseBean);
}
