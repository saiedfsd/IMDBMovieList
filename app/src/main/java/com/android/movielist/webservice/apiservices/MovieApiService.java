package com.android.movielist.webservice.apiservices;

import com.android.movielist.webservice.Constants;
import com.android.movielist.webservice.requestmodels.UserSignUpModel;
import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieModel;
import com.android.movielist.webservice.responsemodels.MoviePageListModel;
import com.android.movielist.webservice.responsemodels.RefreshTokenResponseModel;
import com.android.movielist.webservice.responsemodels.SignInResponseModel;
import com.android.movielist.webservice.responsemodels.SignupResponseModel;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {


    @POST(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_REGISTER)
    Call<SignupResponseModel> registerUser(@Body UserSignUpModel userSignUpModel);


    @POST(Constants.PATH_OAUTH + Constants.PATH_TOKEN)
    Call<SignInResponseModel> signInUser(@Body RequestBody signInRequestBody);

    @POST(Constants.PATH_OAUTH + Constants.PATH_TOKEN)
    Call<RefreshTokenResponseModel> refreshToken(@Body RequestBody refreshTokenRequestBody);

    @POST(Constants.PATH_API + Constants.PATH_USER)
    Call<ResponseBody> getUserData(@Header(Constants.PARAM_KEY_AUTHORIZATION) String authorization,
                                   @Header(Constants.PARAM_KEY_ACCEPT) String accept);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_MOVIES)
    Call<MoviePageListModel> getMoviesListByPage(@Query(Constants.PARAM_KEY_PAGE) int pageNumber);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_MOVIES)
    Call<MoviePageListModel> searchInMoviesByKeyword (@Query(Constants.PARAM_KEY_PAGE) int pageNumber,
                                                      @Query(Constants.PARAM_KEY_QUESTION) String keyword);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_MOVIES +
            "{" + Constants.PATH_MOVIE_ID + "}")
    Call<MovieModel> getMovieDetail (@Path(Constants.PATH_MOVIE_ID) int movieId);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_GENRES +
            "{" + Constants.PATH_GENRE_ID + "}/" +
            Constants.PATH_MOVIES
    )
    Call<MoviePageListModel> getMoviePageListByGenre (@Path(Constants.PATH_GENRE_ID) int genreId,
                                                      @Query(Constants.PARAM_KEY_PAGE) int pageNumber);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_GENRES)
    Call<List<GenreModel>> getGenresList ();



}
