package com.android.movielist.webservice.responsemodels;

public class RefreshTokenResponseModel extends SignInResponseModel{

    public RefreshTokenResponseModel() {
    }

    public RefreshTokenResponseModel(String tokenType, int expiresIn, String accessToken, String refreshToken) {
        super(tokenType, expiresIn, accessToken, refreshToken);
    }
}
