package org.first.meeatnow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.first.meeatnow.Chat.ChatService;
import org.first.meeatnow.Chat.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewChatActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText peopleEditText;
    private EditText dateEditText;
    private EditText linkEditText;
    private Button okButton;
    private Retrofit retrofit;
    private ChatService chatService;
    private User user = new User("jkey20", "https://api.github.com/users/jkey20/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        nameEditText = findViewById(R.id.chatname_edittext);
        peopleEditText = findViewById(R.id.chatpeople_edittext);
        dateEditText = findViewById(R.id.chatdate_edittext);
        linkEditText = findViewById(R.id.chatlink_edittext);
        okButton = findViewById(R.id.ok_button);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/jkey20/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatService = retrofit.create(ChatService.class);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatas();
                postDatas(user);
            }
        });
    }

    public void postDatas(User user){
        Call<User> call = chatService.postRepos("jkey20",user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    String str = "response code: " + response.code() + "\n ID:" + response.body().login + "\n URL" + response.body().html_url;
                    Log.e("TAGPOST", str);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("!!!!POST", t.getLocalizedMessage());
            }
        });
    }


    public void getDatas(){
        Call<User> call = chatService.getRepos("jkey20");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body() != null){
                    String str = "response code: " + response.code() + "\n ID:" + response.body().login + "\n URL" + response.body().html_url;
                    Log.e("TAGGET", str);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("!!!!GET", t.getLocalizedMessage());
            }
        });
    }
}
