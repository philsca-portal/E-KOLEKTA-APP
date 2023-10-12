package com.ekolekta.e_kolekta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface myapi {

    @FormUrlEncoded
    @POST("inserting.php")
    Call<Data> adddata(@Field("tag") String tag,@Field("type") String type,@Field("date") String date,
                       @Field("image") String image,@Field("loc") String loc);
}
