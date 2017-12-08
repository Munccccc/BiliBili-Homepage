package app.munc.munccoordinator.inter;

import app.munc.munccoordinator.info.AnimInfo;
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


    @GET("timeline_v2_global")
    Call<BiliBiliFanjuInfo> getfanju();

    //这里有两种 一种Jsonp一种json 这里用json
/*    @GET("region")
    Call<AnimInfo> regionGet(@Query("jsonp") String jsonp,
                             @Query("ps") String ps,
                             @Query("rid") String rid,
                             @Query("callback") String callback);*/

    @GET("region")
    Call<AnimInfo> regionGet(@Query("json") String json,
                             @Query("ps") String ps,
                             @Query("rid") String rid,
                             @Query("callback") String callback);

}
