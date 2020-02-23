package org.first.meeatnow.Kakao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.helper.log.Logger;

import org.first.meeatnow.LoginActivity;
import org.first.meeatnow.MainActivity;
import org.first.meeatnow.R;

import java.util.ArrayList;
import java.util.List;

public class KakaoProfileActivity extends AppCompatActivity {
    static final String TAG = MainActivity.class.getSimpleName();
    TextView nameTextView;
    TextView emailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_profile);
        Button signOutButton = findViewById(R.id.sign_out_kakao);
        nameTextView = findViewById(R.id.profile_text);
        emailTextView = findViewById(R.id.profile_email);

        requestMe();
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogout();
            }
        });

    }

    private void onClickLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                redirectLoginActivity(); // login 액티비티로 이동
            }
        });
    }

    private void requestMe() {
        // https://developers.kakao.com/apps/361201/settings/user
        // 앱 생성된 이름 선택(테스트 앱) → 사용자 관리 → 접근권한관리항목 설정
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(MeV2Response response) {

                nameTextView.setText(response.getNickname());
                emailTextView.setText(response.getKakaoAccount().getEmail());
            }

        });
    }

    private void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
