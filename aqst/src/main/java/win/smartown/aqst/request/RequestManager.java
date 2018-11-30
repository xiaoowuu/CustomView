package win.smartown.aqst.request;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 类描述：网络请求管理
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public class RequestManager {

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private final static String BASE_URL = "http://v.juhe.cn/";

    private static synchronized OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor())
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    private static Retrofit newRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .client(RequestManager.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = RequestManager.newRetrofit(BASE_URL);
        }
        return retrofit;
    }

}