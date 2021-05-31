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
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @POST(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_REGISTER)
    Observable<SignupResponseModel> registerUser(@Body UserSignUpModel userSignUpModel);


    @POST(Constants.PATH_OAUTH + Constants.PATH_TOKEN)
    Observable<SignInResponseModel> signInUser(@Body RequestBody signinRequestBody
            /*@Field(Constants.PARAM_KEY_USERNAME) String username,
                                                @Field(Constants.PARAM_KEY_PASSWORD) String password,
                                                @Field(Constants.PARAM_KEY_GRANT_TYPE) String grandType*/
    );

    @POST(Constants.PATH_API + Constants.PATH_USER)
    Observable<ResponseBody> getUserData(@Header(Constants.PARAM_KEY_AUTHORIZATION) String authorization,
                                         @Header(Constants.PARAM_KEY_ACCEPT) String accept);

    @POST(Constants.PATH_OAUTH + Constants.PATH_TOKEN)
    Observable<RefreshTokenResponseModel> refreshToken(@Query(Constants.PARAM_KEY_GRANT_TYPE) String grantType,
                                                       @Query(Constants.PARAM_KEY_REFRESH_TOKEN) String refreshToken);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_MOVIES)
    Observable<MoviePageListModel> getMoviesListByPage(@Query(Constants.PARAM_KEY_PAGE) int pageNumber);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_MOVIES)
    Observable<MoviePageListModel> searchInMoviesByKeyword (@Query(Constants.PARAM_KEY_QUESTION) String keyword,
                                                            @Query(Constants.PARAM_KEY_PAGE) int pageNumber);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_MOVIES +
            "{" + Constants.PATH_MOVIE_ID + "}")
    Observable<MovieModel> getMovieDetail (@Path(Constants.PATH_MOVIE_ID) int movieId);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_GENRES +
            "{" + Constants.PATH_GENRE_ID + "}/" +
            Constants.PATH_MOVIES
    )
    Observable<MoviePageListModel> getMoviePageListByGenre (@Path(Constants.PATH_GENRE_ID) int genreId,
                                                    @Query(Constants.PARAM_KEY_PAGE) int pageNumber);

    @GET(Constants.PATH_API + Constants.PATH_WEB_SERVICE_VERSION + Constants.PATH_GENRES)
    Observable<List<GenreModel>> getGenresList ();



}
