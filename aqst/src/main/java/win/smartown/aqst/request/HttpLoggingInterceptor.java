package win.smartown.aqst.request;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import win.smartown.aqst.util.LogUtil;

/**
 * 类描述：请求打印拦截器
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public class HttpLoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.currentTimeMillis();
        Request request = chain.request();
        Response response = chain.proceed(request);
        long t2 = System.currentTimeMillis();
        LogUtil.logInfo("okhttp-request-url", request.url().toString());
        LogUtil.logInfo("okhttp-request-header", request.headers().toString());
        LogUtil.logInfo("okhttp-request-params", getRequestParams(request.body()));
        MediaType mediaType = response.body().contentType();
        String responseString = getResponseBody(response.body());
        LogUtil.logInfo("okhttp-response", responseString);
        LogUtil.logInfo("okhttp-time", String.format(Locale.getDefault(), "%d ms", t2 - t1));
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, responseString))
                .build();
    }

    private String getRequestParams(@Nullable RequestBody requestBody) throws IOException {
        if (requestBody == null) {
            return "";
        }
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readString(Charset.forName("UTF-8"));
    }

    private String getResponseBody(@Nullable ResponseBody responseBody) throws IOException {
        if (responseBody == null) {
            return "";
        }
        return responseBody.string();
    }
}
