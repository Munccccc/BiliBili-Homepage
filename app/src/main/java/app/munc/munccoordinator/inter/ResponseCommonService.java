package app.munc.munccoordinator.inter;

import app.munc.munccoordinator.info.BiliBiliFanjuInfo;
import app.munc.munccoordinator.info.RankingInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Munc on 2017/12/2.
 */

public interface ResponseCommonService {
    /*    @FormUrlEncoded
    @POST("ProductCate")
    Call<ProductCateInfo> postProduct(@Field("a") String a,
                                      @Field("b") String b);*/


    //@Query注解的作用理解为查询条件，这里表示需要查询的字段为ip
    //ResponseBody是Retrofit自带的返回类，
    @GET("index")
    Call<RankingInfo> rankingGet(@Query("day") String day);


    //@Query注解的作用理解为查询条件，这里表示需要查询的字段为ip
    //ResponseBody是Retrofit自带的返回类，
    @GET("timeline_v2_global")
    Call<BiliBiliFanjuInfo> getfanju();


    /**
     *以下是两个方式拿到了bilibili的服务器数据 想完全抓包获得 SSL授信
     **/

     /*    Retrofit retrofit = new Retrofit.Builder()
     .baseUrl(UrlContent.common)
     .addConverterFactory(GsonConverterFactory.create())
     .build();
     //生成对象的Service   这是个特殊的接口
     ResponseCommonService commonService = retrofit.create(ResponseCommonService.class);
     //调用方法得到Call
     Call<RankingInfo> loginCall = commonService.rankingGet("3");
     //异步执行
     loginCall.enqueue(new Callback<RankingInfo>() {
    @Override public void onResponse(Call<RankingInfo> call, Response<RankingInfo> response) {

    Log.d("aaaaa", response.message() + "   " + response.body());
    }

    @Override public void onFailure(Call<RankingInfo> call, Throwable t) {

    Log.d("bbbbb", t.getMessage());
    Log.d("bbbbb", t.toString());
    }
    });*/


//    暂时不用
/*    OkHttpUtils
            .get()
            .url("https://api.bilibili.com/x/web-interface/ranking/index")
    .addParams("day", "3")
    .build()
    .execute(new com.zhy.http.okhttp.callback.Callback<RankingInfo>() {
        @Override
        public RankingInfo parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
            String body = response.body().string();
            RankingInfo details = new Gson().fromJson(body, RankingInfo.class);
            Log.v("ccccc", body);
            return details;
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            Utils.showToast(MainActivity.this, "失败");
        }

        @Override
        public void onResponse(RankingInfo response, int id) {
            if (response == null) {
                return;
            }

        }
    });*/

}
