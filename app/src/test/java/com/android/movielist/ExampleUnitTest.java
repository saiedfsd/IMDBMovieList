package com.android.movielist;

import com.android.movielist.webservice.RetrofitHandler;
import com.android.movielist.webservice.apiservices.MovieApiService;
import com.android.movielist.webservice.requestmodels.UserSignUpModel;
import com.android.movielist.webservice.responsemodels.SignupResponseModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

//    UserSignUpModel userSignUpModel;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

   /* @Test
    public void testApiResponse() {
        MovieApiService mockedApiInterface = Mockito.mock(MovieApiService.class);
        Call<UserSignUpModel> mockedCall = (Call<UserSignUpModel>) Mockito.mock(Call.class);

        UserSignUpModel userSignUpModel = new UserSignUpModel();
        userSignUpModel.setEmail("saied@gmail.com");
        userSignUpModel.setPassword("123456789");
        userSignUpModel.setName("saeed");

        Mockito.when(mockedApiInterface.registerUser(userSignUpModel)).thenReturn();

        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Call<UserSignUpModel> callback = (Call<UserSignUpModel>) invocation.getArgument(0, Callback.class);

                callback.(mockedCall, Response.success(new SignupResponseModel()));
                // or callback.onResponse(mockedCall, Response.error(404. ...);
                // or callback.onFailure(mockedCall, new IOException());

                return null;
            }
        }).when(mockedCall).enqueue(any(Callback.class));

        // inject mocked ApiInterface to your presenter
        // and then mock view and verify calls (and eventually use ArgumentCaptor to access call parameters)
    }*/
}