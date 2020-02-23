package org.first.meeatnow;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.elasticviews.ElasticButton;

import org.first.meeatnow.GPS.GpsTracker;
import org.first.meeatnow.ListView.ListViewAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapActivity extends AppCompatActivity {
    Balloon balloon;
    private Button gpsBtn;
    private Button getBtn;
    private TextView result;
    private ElasticButton newChatButton;
    private ElasticButton findChatButton;
    String finalAddress;
    String a;
    public static ArrayList<String> title = new ArrayList<>();
    public static ArrayList<String> address = new ArrayList<>();
    public static ArrayList<String> telephone = new ArrayList<>();
    private GpsTracker gpsTracker;
    private double lat;
    private double lon;
    String mlResult = " 치킨";
    ListView listView;
    ListViewAdapter adapter;
    LinearLayout page;
    boolean isUp = false;
    boolean isBalloonUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final Animation translateup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);

        final Animation translatedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);

        result = findViewById(R.id.map_textview);
        getBtn = findViewById(R.id.map_button);
        gpsBtn = findViewById(R.id.gpstest_button);
        newChatButton = findViewById(R.id.newchat_button);
        findChatButton = findViewById(R.id.findchat_button);

        page = findViewById(R.id.page);
        adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.LLL);
        listView.setAdapter(adapter);
        gpsTracker = new GpsTracker(MapActivity.this);

        double lat = gpsTracker.getLatitude();
        double lon = gpsTracker.getLongitude();
        String currentAddress = getCurrentAddress(lat, lon);

        int index[] = new int[3];
        int finalIndex;
        index[0] = currentAddress.indexOf("동");
        index[1] = currentAddress.indexOf("읍");
        index[2] = currentAddress.indexOf("면");
        if (index[0] != -1) {
            finalIndex = index[0];
        } else if (index[1] != -1) {
            finalIndex = index[1];
        } else {
            finalIndex = index[2];
        }
        finalAddress = currentAddress.substring(0, finalIndex + 1);
        Log.e("TAG", "lat : " + lat + "\nlon : " + lon);
        Log.e("TAG", currentAddress);
        Log.e("TAG", finalAddress);

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

        balloon = new Balloon.Builder(this)
                .setArrowSize(10)
                .setIconDrawable(ContextCompat.getDrawable(this, R.drawable.arrow))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setArrowOrientation(ArrowOrientation.BOTTOM)
                .setArrowPosition(0.5f)
                .setArrowVisible(true)
                .setWidthRatio(0.5f)
                .setHeight(65)
                .setTextSize(10f)
                .setCornerRadius(4f)
                .setAlpha(0.9f)
                .setText("먼저 오픈채팅을 생성하셨나요?")
                .setBalloonAnimation(BalloonAnimation.FADE)
                .build();


        newChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBalloonUp) {
                    Intent intent = new Intent(getApplicationContext(), NewChatActivity.class);
                    startActivity(intent);
                } else {
                    balloon.showAlignTop(newChatButton);
                    isBalloonUp = true;
                    balloon.dismissWithDelay(1000L);
                }
            }
        });

        findChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindChatActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int i = listView.getCheckedItemPosition();
                if (isUp) {
                    //
                } else {
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateup);
                    isUp = true;
                }
            }
        });
    }

    public void go(View view) {
        Intent intent = new Intent(getApplicationContext(), JStest.class);
        startActivity(intent);
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            getsad();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //result.setText(a);
            for (int i = 0; i < title.size(); i++) {
                adapter.addItem(title.get(i), address.get(i), telephone.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void getsad() {
        String clientId = "ieh85w7IOd064SRtVCsP";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "yk25nVge3r";//애플리케이션 클라이언트 시크릿값";

        try {
            String text = URLEncoder.encode(finalAddress + mlResult, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/local?query=" + text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());


            JSONObject jsonObject = (JSONObject) obj;
            JSONArray getArray = (JSONArray) jsonObject.get("items");
            for (int i = 0; i < getArray.size(); i++) {
                JSONObject object = (JSONObject) getArray.get(i);

                String getTitle = (String) object.get("title");
                address.add((String) object.get("address"));
                telephone.add((String) object.get("telephone"));

                String titleFilter1 = getTitle.replaceAll("<b>", "");
                String titleFilter2 = titleFilter1.replaceAll("</b>", "");
                title.add(titleFilter2);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }


}
