package app.munc.munccoordinator.inter;

import app.munc.munccoordinator.info.AnimInfo;
import app.munc.munccoordinator.info.BiliBiliFanjuInfo;
import app.munc.munccoordinator.info.RankingInfo;
import app.munc.munccoordinator.info.homepage.IndexInfo;
import app.munc.munccoordinator.info.homepage.TopbarInfo;
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


    @GET("region")
    Call<AnimInfo> regionGet(@Query("json") String json,
                             @Query("ps") String ps,
                             @Query("rid") String rid,
                             @Query("callback") String callback);

    /**
     * bilibili顶部bar
     * actionKey	appkey
     * appkey	27eb53fc9058f8c3
     * build	6190
     * device	phone
     * mobi_app	iphone
     * platform	ios
     * sign	81bdd12d15122808c51f46da70625659
     * ts	1513660529
     */
    @GET("topbar")
    Call<TopbarInfo> getTopBar(@Query("actionKey") String actionKey,
                               @Query("appkey") String appkey,
                               @Query("build") String build,
                               @Query("device") String device,
                               @Query("mobi_app") String mobi_app,
                               @Query("platform") String platform,
                               @Query("sign") String sign,
                               @Query("ts") String ts);

    /**
     * bilibili首页推荐
     * actionKey	appkey
     * appkey	27eb53fc9058f8c3
     * banner_hash
     * build	6190
     * device	phone
     * idx	1503941147
     * login_event	1
     * mobi_app	iphone
     * network	wifi
     * open_event	cold
     * platform	ios
     * pull	1
     * sign	5d327d602091bbd9c0f7932e2ad1c7ec
     * style	1
     * ts	1513669395
     */
    @GET("index")
    Call<IndexInfo> getIndex(@Query("actionKey") String actionKey,
                             @Query("appkey") String appkey,
                             @Query("banner_hash") String banner_hash,
                             @Query("build") String build,
                             @Query("device") String device,
                             @Query("idx") String idx,
                             @Query("login_event") String login_event,
                             @Query("mobi_app") String mobi_app,
                             @Query("network") String network,
                             @Query("open_event") String open_event,
                             @Query("platform") String platform,
                             @Query("pull") String pull,
                             @Query("sign") String sign,
                             @Query("style") String style,
                             @Query("ts") String ts);

    //不要轮播图的
    @GET("index")
    Call<IndexInfo> getIndex2(@Query("actionKey") String actionKey,
                              @Query("appkey") String appkey,
                              @Query("banner_hash") String banner_hash,
                              @Query("build") String build,
                              @Query("device") String device,
                              @Query("idx") String idx,
                              @Query("mobi_app") String mobi_app,
                              @Query("network") String network,
                              @Query("open_event") String open_event,
                              @Query("platform") String platform,
                              @Query("pull") String pull,
                              @Query("sign") String sign,
                              @Query("style") String style,
                              @Query("ts") String ts);
}
