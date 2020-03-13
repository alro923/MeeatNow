package org.first.meeatnow.Facebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;

import org.first.meeatnow.LoginActivity;
import org.first.meeatnow.R;

import static org.first.meeatnow.LoginActivity.userEmail;
import static org.first.meeatnow.LoginActivity.userName;
import static org.first.meeatnow.LoginActivity.userPicture;

public class FacebookProfileActivity extends AppCompatActivity {

    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private Button signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_profile);

        profileName = findViewById(R.id.profile_text);
        profileEmail = findViewById(R.id.profile_email);
        profileImage = findViewById(R.id.profile_image);
        signOut=findViewById(R.id.sign_out);
        setDataOnView();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent=new Intent(FacebookProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void setDataOnView() {
        String url = userPicture;
        Glide.with(this).load(url).centerInside().fitCenter()
                .error(R.drawable.ic_launcher_background)
                .into(profileImage);
        profileName.setText(userName);
        profileEmail.setText(userEmail);
    }

}
