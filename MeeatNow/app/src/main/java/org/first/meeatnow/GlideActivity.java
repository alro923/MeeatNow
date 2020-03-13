package org.first.meeatnow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.first.meeatnow.Glide.GlideAdapter;
import org.first.meeatnow.Glide.GlideDTO;

import java.util.ArrayList;

public class GlideActivity extends AppCompatActivity {

    ArrayList<String> glideURL = new ArrayList<>();


    private RecyclerView glideRecyclerView;
    private ArrayList<GlideDTO> glideData;
    private GlideAdapter glideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        CheckTypesTask task = new CheckTypesTask();
        task.execute();
        glideRecyclerView = findViewById(R.id.recyclerView);
        glideRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        glideData = new ArrayList<>();

        glideAdapter = new GlideAdapter(this, glideData);
        glideRecyclerView.setAdapter(glideAdapter);

        getGlideURLFromServer();

    }


    // 서버에서 글라이드 URL을 받아옴
    private void getGlideURLFromServer() {
        // 임시 데이터 셋
        glideURL.add("http://www.pizzabig.co.kr/theme/basic/img/sub0201_img03.jpg");
        glideURL.add("https://www.pizzaetang.com/resources/images/menu/menuinfo/PZ_TT2_l.png");
        glideURL.add("https://image.chosun.com/sitedata/image/201911/11/2019111100790_0.jpg");
        glideURL.add("http://handmadepizza.co.kr/wp-content/uploads/%EB%B2%A0%EC%9D%B4%EC%BB%A8%EC%B2%B4%EB%8B%A4%EC%B9%98%EC%A6%88%EC%8D%B8%EB%84%AC-copy-800x400.jpg");
        glideURL.add("https://cdn.dominos.co.kr/admin/upload/goods/20200119_Dzj9GV1R.jpg");

    }

    // GlideDTO에 URL 세팅
    private void setGlideData() {
        glideData.clear();
        for (int i = 0; i < glideURL.size(); i++) {
            glideData.add(new GlideDTO(glideURL.get(i)));
        }

    }

    public void goToMapAcitivty(View view) {
        // 임시 인텐트 셋
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(GlideActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 5; i++) {
                    asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setGlideData();
            glideAdapter.notifyDataSetChanged();
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }

}
