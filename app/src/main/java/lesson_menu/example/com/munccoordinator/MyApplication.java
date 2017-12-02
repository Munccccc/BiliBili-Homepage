package lesson_menu.example.com.munccoordinator;

import android.app.Application;

/**
 * Created by GD on 2017/12/2.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
/*        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();

        OkHttpUtils.initClient(okHttpClient);*/

    }
}
