package org.tensorflow.demo.datainterface;

import org.tensorflow.demo.models.ImageSearchListVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpService {

    @GET("https://www.googleapis.com/customsearch/v1?key=AIzaSyAC1D8WmoJhJY6UWLzUpQ2eZfKBF4wfw-Y&cx=010652324153482008826:igm5nfa5pfg&searchType=image&fileType=png,jpg&alt=json&num=10")
    Call<ImageSearchListVO> getImageSearchlList(@Query("q") String q, @Query("start") String start);

}
