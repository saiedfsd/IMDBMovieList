package com.android.movielist.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import com.android.movielist.App;
import com.android.movielist.R;
import com.android.movielist.db.ImdbMoviesDB;
import com.android.movielist.db.entities.GenreEntity;
import com.android.movielist.webservice.Constants;
import com.android.movielist.webservice.RetrofitHandler;
import com.android.movielist.webservice.apiservices.MovieApiService;
import com.android.movielist.webservice.requestmodels.UserSignUpModel;
import com.android.movielist.webservice.responsemodels.GenreModel;
import com.android.movielist.webservice.responsemodels.MovieModel;
import com.android.movielist.webservice.responsemodels.MoviePageListModel;
import com.android.movielist.webservice.responsemodels.RefreshTokenResponseModel;
import com.android.movielist.webservice.responsemodels.SignInResponseModel;
import com.android.movielist.webservice.responsemodels.SignupResponseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
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

    @Inject
    public ImdbMoviesDB database;

    @Inject
    public MovieApiService movieApiService;

    private boolean filtersIsCollapsed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AndroidInjection.inject(this);
        ((App)getApplicationContext()).getAppComponent().injectActivity(this);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);


        btnFiltersCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filtersIsCollapsed) {
                    filtersIsCollapsed = false;
                    btnFiltersCollapse.setImageResource(R.drawable.arrow_up_ico);
                    filtersParent.setVisibility(View.VISIBLE);
                } else {
                    filtersIsCollapsed = true;
                    btnFiltersCollapse.setImageResource(R.drawable.arrow_down_ico);
                    filtersParent.setVisibility(View.GONE);
                }
            }
        });

        RequestBody signInBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("grant_type", "password")
                .addFormDataPart("password", "123456789")
                .addFormDataPart("username", "saied.fsd@gmail.com")
                .build();

        Call<MoviePageListModel> genres = movieApiService.getMoviesListByPage(0);
        genres.enqueue(new Callback<MoviePageListModel>() {
            @Override
            public void onResponse(Call<MoviePageListModel> call, Response<MoviePageListModel> response) {
                Log.e("fsd",response.message());
            }

            @Override
            public void onFailure(Call<MoviePageListModel> call, Throwable t) {
                Log.e("fsd",t.getMessage());
            }
        });

    }



}