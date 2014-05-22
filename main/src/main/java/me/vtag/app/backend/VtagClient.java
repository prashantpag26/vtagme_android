package me.vtag.app.backend;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import java.lang.reflect.Type;
import java.util.List;

import ly.apps.android.rest.client.DefaultRestClientImpl;
import ly.apps.android.rest.client.RestClient;
import ly.apps.android.rest.client.RestClientFactory;
import ly.apps.android.rest.client.RestServiceFactory;
import ly.apps.android.rest.converters.impl.JacksonBodyConverter;
import ly.apps.android.rest.converters.impl.JacksonQueryParamsConverter;
import me.vtag.app.VtagApplication;
import me.vtag.app.backend.vos.LoginVO;
import me.vtag.app.backend.models.AuthPreferences;
import me.vtag.app.pages.BaseLoginPageFragment;
import me.vtag.app.pages.social.SocialUser;

/**
 * Created by nmannem on 30/10/13.
 */
public class VtagClient {
    private static final String BASE_URL = "http://10.63.8.204:8080";
    //private static final String BASE_URL = "http://www.vtag.me";
    //private static final String BASE_URL = "http://192.168.0.4:8080";

    private AsyncHttpClient client;
    private static VtagClient instance = null;
    private CookieStore cookies;

    private static VtagAPI api;

    /**
     * Returns singleton instance
     */
    public static VtagClient getInstance() {
        if (instance == null) instance = new VtagClient();
        return instance;
    }

    public VtagClient() {
        client  = new AsyncHttpClient();
        cookies = null;
    }

    public void initalize(Context context) {
        cookies = new PersistentCookieStore(context);
        client.setCookieStore(cookies);
        RestClient restClient = new DefaultRestClientImpl(client, new VtagQueryParamsConverter(), new JacksonBodyConverter());
        api = RestServiceFactory.getService(BASE_URL, VtagAPI.class, restClient);
    }

    public VtagAPI getApi() {
        return api;
    }

    public static VtagAPI getAPI() {
        return api;
    }

    private void refreshCookies() {
        List<Cookie> cookieList = cookies.getCookies();
        String phpSession = "";
        if (cookieList.isEmpty()) {
            Log.i("TAG", "None");
        } else {
            for (Cookie cookie : cookieList) {
                phpSession = phpSession + cookie.getName()+"=\""+cookie.getValue()+"\";";
            }
            Log.i("session", phpSession);
        }
        client.addHeader("Cookie", phpSession);
    }
}
