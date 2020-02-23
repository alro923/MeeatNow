package org.first.meeatnow;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "TEST";
    public static GoogleSignInClient googleSignInClient;
    private SignInButton googleSignInButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    public static String userEmail;
    public static String userName;
    public static String userPicture;
    private SessionCallback callback;
    public static boolean isLoggedInWithFacebook;
    public static boolean isLoggedInWithKakao;
    public static GoogleSignInAccount googleAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        LoginButton facebookLoginButton = findViewById(R.id.login_button_facebook);
        facebookLoginButton.setReadPermissions("public_profile", "email");

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                accessToken = loginResult.getAccessToken();
                isLoggedInWithFacebook = accessToken != null && !accessToken.isExpired();
                requestMe(accessToken);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        googleSignInButton = findViewById(R.id.login_button_google);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });


    }

    public void goToMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void requestMe(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    userEmail = object.getString("email");
                    userName = object.getString("name");
                    JSONObject jobj1 = object.optJSONObject("picture");
                    JSONObject jobj2 = jobj1.optJSONObject("data");
                    userPicture = jobj2.getString("url");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                goToMain();

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,picture");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        googleAccount = task.getResult(ApiException.class);
                        goToMain();
                    } catch (ApiException e) {
                        Log.w(TAG, "signInResult: failed code = " + e.getStatusCode());
                    }

                default:

                    break;
            }
    }

    @Override
    public void onStart() {
        super.onStart();
        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedInWithFacebook = accessToken != null && !accessToken.isExpired();
        googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleAccount != null) {
            goToMain();
        } else if (isLoggedInWithFacebook) {
            requestMe(accessToken);
        } else {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }


    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            goToMain();
            isLoggedInWithKakao = true;
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

}
