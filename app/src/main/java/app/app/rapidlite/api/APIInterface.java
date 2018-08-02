package app.app.rapidlite.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

import static app.app.rapidlite.attributes.Constant.LOGIN_URL;

public interface APIInterface {

    @FormUrlEncoded
    @POST(LOGIN_URL)
    Call<LoginResponse> login(@Field("Username") String grant_type,
                              @Field("Password") String Scope);



}
