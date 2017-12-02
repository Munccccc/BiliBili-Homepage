package lesson_menu.example.com.munccoordinator.inter;

import lesson_menu.example.com.munccoordinator.info.RankingInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by GD on 2017/12/2.
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



/*    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.bilibili.com/x/web-interface/ranking/")
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
            .build();

    //baseurl:  https://api.bilibili.com/x/web-interface/ranking/

    //生成对象的Service   这是个特殊的接口
    ResponseCommonService commonService = retrofit.create(ResponseCommonService.class);
    //调用方法得到Call
    Call<RankingInfo> loginCall = commonService.rankingGet("day=3");
    //异步执行
    loginCall.enqueue(new Callback<RankingInfo>() {
        @Override
        public void onResponse(Call<RankingInfo> call, Response<RankingInfo> response) {

            Log.d("aaaaa", response.message() + "   " + response.body());
        }

        @Override
        public void onFailure(Call<RankingInfo> call, Throwable t) {

            Log.d("bbbbb", t.getMessage());
            Log.d("bbbbb", t.toString());
        }
    });*/
}
