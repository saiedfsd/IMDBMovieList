package com.android.movielist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import com.android.movielist.db.ImdbMoviesDB;
import com.android.movielist.db.entities.GenreEntity;
import com.android.movielist.webservice.Constants;
import com.android.movielist.webservice.RetrofitHandler;
import com.android.movielist.webservice.requestmodels.UserSignUpModel;
import com.android.movielist.webservice.responsemodels.MovieModel;
import com.android.movielist.webservice.responsemodels.RefreshTokenResponseModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {


    @BindView(R.id.btn_collapse_filters)
    public ImageButton btnFiltersCollapse;
    @BindView(R.id.btn_search)
    public ImageButton btnSearch;
    @BindView(R.id.btn_sort)
    public ImageButton btnSort;
    @BindView(R.id.btn_genres)
    public ImageButton btnGenres;
    @BindView(R.id.filters_parent)
    public ConstraintLayout filtersParent;

    private boolean filtersIsCollapsed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityMovieListBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);
//        setSearchFilterModel(new SearchFilters());
        setContentView(R.layout.activity_movie_list);
        addFilters();
        ButterKnife.bind(this);
        btnFiltersCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filtersIsCollapsed){
                    filtersIsCollapsed = false;
                    btnFiltersCollapse.setImageResource(R.drawable.arrow_up_ico);
                    filtersParent.setVisibility(View.VISIBLE);
                }else{
                    filtersIsCollapsed = true;
                    btnFiltersCollapse.setImageResource(R.drawable.arrow_down_ico);
                    filtersParent.setVisibility(View.GONE);
                }
            }
        });

        ImdbMoviesDB db = Room
                .databaseBuilder(getApplicationContext(), ImdbMoviesDB.class, "imdb.db")
                .build();

        db.genreDao().getGenreById(1)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GenreEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        Log.e("FSD","onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull GenreEntity genreEntity) {
//                        Log.e("FSD","onNext");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        Log.e("FSD","onError :" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
//                        Log.e("FSD","onComplete");
                    }
                });

        RetrofitHandler retrofitHandler = new RetrofitHandler();
        UserSignUpModel userSignUpModel = new UserSignUpModel();
        userSignUpModel.setEmail("saied@gmail.com");
        userSignUpModel.setPassword("123456789");
        userSignUpModel.setName("saeed");

        RequestBody signInBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("grant_type","password")
                .addFormDataPart("password","123456789")
                .addFormDataPart("username","saied.1991.fsd@gmail.com")
                .build();

        RequestBody refresh = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(Constants.PARAM_KEY_GRANT_TYPE,Constants.PARAM_VALUE_REFRESH_TOKEN)
                .addFormDataPart(Constants.PARAM_KEY_REFRESH_TOKEN,getString(R.string.refresh_token))
                .build();

        /*retrofitHandler.getMovieApiService().registerUser(userSignUpModel)
                .observeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .subscribe(new io.reactivex.Observer<SignupResponseModel>() {
                    @Override
                    public void onSubscribe(io.reactivex.disposables.Disposable d) {
                        Log.e("FSD","onSubscribe :" + d.toString());
                    }

                    @Override
                    public void onNext(SignupResponseModel value) {
                        Log.e("FSD","onNext :" + value.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("FSD","onError :" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("FSD","onComplete :");
                    }
                });*/

        /*retrofitHandler.getMovieApiService().signInUser(body)
                .observeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .subscribe(new io.reactivex.Observer<SignInResponseModel>() {
                    @Override
                    public void onSubscribe(io.reactivex.disposables.Disposable d) {
                        Log.e("FSD","onSubscribe :" + d.toString());
                    }

                    @Override
                    public void onNext(SignInResponseModel value) {
                        Log.e("FSD","onNext :" + value.getAccessToken());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("FSD","onError :" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("FSD","onComplete :");
                    }
                });*/

        Call<RefreshTokenResponseModel> refreshToken = retrofitHandler.getMovieApiService().refreshToken(refresh/*Constants.PARAM_VALUE_REFRESH_TOKEN
                ,getString(R.string.refresh_token)*/);

   /*     refreshToken.enqueue(new Callback<RefreshTokenResponseModel>() {
            @Override
            public void onResponse(Call<RefreshTokenResponseModel> call, Response<RefreshTokenResponseModel> response) {
                Log.e("fsd",response.message() + " : " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<RefreshTokenResponseModel> call, Throwable t) {
                Log.e("fsd",t.getMessage());
            }
        });*/

        Call<MovieModel> movies = retrofitHandler.getMovieApiService().getMovieDetail(4);

        movies.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                Log.e("fsd",response.message() + " : " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Log.e("fsd",t.getMessage());
            }
        });
    }


    private void addFilters() {
       /* String[] filtersArray = getResources().getStringArray(R.array.search_filters);
        for(int i = 0 ; i < filtersArray.length; i++){
            FilterModel filterModel = new FilterModel();
            filterModel.setFilterName(filtersArray[i]);
            filterModel.setIsSelect(false);
            searchFilters.addFilter(filterModel);
        }*/
    }

  /*  public SearchFilters getSearchFilterModel() {
        return searchFilters;
    }

    public void setSearchFilterModel(SearchFilters searchFilters) {
        this.searchFilters = searchFilters;
    }*/

    public void onFilterItemClickListener(FilterModel filterModel){
        filterModel.setIsSelect(!filterModel.isSelect());
    }

}