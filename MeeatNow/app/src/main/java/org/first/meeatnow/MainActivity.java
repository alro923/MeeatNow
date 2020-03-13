package org.first.meeatnow;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.skydoves.elasticviews.ElasticButton;
import com.tomer.fadingtextview.FadingTextView;

import org.first.meeatnow.Facebook.FacebookProfileActivity;
import org.first.meeatnow.Google.GoogleProfileActivity;
import org.first.meeatnow.Kakao.KakaoProfileActivity;

import static org.first.meeatnow.LoginActivity.googleAccount;
import static org.first.meeatnow.LoginActivity.isLoggedInWithFacebook;
import static org.first.meeatnow.LoginActivity.isLoggedInWithKakao;

public class MainActivity extends AppCompatActivity {
    private FadingTextView fadingTextView;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private final int GET_GALLERY_IMAGE = 200;
    private final int GET_CAMERA_IMAGE = 20;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private boolean isMachineRunningComplete;
    private ElasticButton openCameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.gallery_imageview);
        openCameraButton = findViewById(R.id.camera_button);
        fadingTextView = findViewById(R.id.fading_textview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("TAG", "권한 설정 완료");
            } else {
                Log.d("TAG", "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        if (checkLocationServicesStatus()) {
            checkRunTimePermission();
        } else {
            showDialogForLocationServiceSetting();
        }


        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, GET_CAMERA_IMAGE);
            }
        });
    }

    public void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isExistCameraData(requestCode, resultCode, data)) {

            setCameraPicture(data);
            runMachineRunning();

        } else if (isExistGalleryData(requestCode, resultCode, data)) {

            setGalleryPicture(data);
            runMachineRunning();
            if (isMachineRunningComplete) {
                goToViewPager();
            } else {
                showRetryDialog();
            }
            // 머신러닝 후 intent로 Glide로 이미지 띄우는 페이지로 이동
            //  runMachineRunning();
        } else if (requestCode == GPS_ENABLE_REQUEST_CODE) {
            //사용자가 GPS 활성 시켰는지 검사
            if (checkLocationServicesStatus()) {
                if (checkLocationServicesStatus()) {

                    Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                    checkRunTimePermission();
                    return;
                }
            }
        }
    }


    private boolean isExistGalleryData(int requestCode, int resultCode, Intent data) {
        return requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null;
    }

    private boolean isExistCameraData(int requsetCode, int resultCode, Intent data) {
        return requsetCode == GET_CAMERA_IMAGE && resultCode == RESULT_OK && data.hasExtra("data");
    }

    private void setGalleryPicture(Intent data) {
        Uri selectedImageUri = data.getData();
        imageView.setImageURI(selectedImageUri);
    }

    private void runMachineRunning() {
        if (isMachineRunningComplete) {
            goToViewPager();
        } else {
            showRetryDialog();
        }
    }



    public void goToProfilePage(View view) {
        if (isLoggedInWithFacebook) {
            Intent intent = new Intent(getApplicationContext(), FacebookProfileActivity.class);
            startActivity(intent);
        } else if (isLoggedInWithKakao) {
            Intent intent = new Intent(getApplicationContext(), KakaoProfileActivity.class);
            startActivity(intent);
        } else if (googleAccount != null) {
            Intent intent = new Intent(getApplicationContext(), GoogleProfileActivity.class);
            startActivity(intent);
        }
    }


    private void setCameraPicture(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private void showRetryDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("머신러닝 결과");
        alertDialogBuilder.setMessage("음식 인식에 실패했습니다. 다시 시도하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isMachineRunningComplete = true;
                        runMachineRunning();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }


    public void goToViewPager() {
        Intent intent = new Intent(getApplicationContext(), ViewpagerTestActivity.class);
        startActivity(intent);
    }

}