package org.first.meeatnow.Chat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    public static final String API_URL ="http://jsonplaceholder.typicode.com/";

    @GET("comments")
    Call<ResponseBody> getComment(@Query("postId")int id);

    @POST("comments")
    Call<ResponseBody>getPostComment(@Query("postId") int postId);

}
