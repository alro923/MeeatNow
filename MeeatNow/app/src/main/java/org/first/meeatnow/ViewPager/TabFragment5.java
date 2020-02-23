package org.first.meeatnow.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.first.meeatnow.MapActivity;
import org.first.meeatnow.R;

import static org.first.meeatnow.ViewpagerTestActivity.mViewPager;

public class TabFragment5 extends Fragment {
    boolean isOpenDialog = false;
    private ImageView imageView;
    public TabFragment5() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isOpenDialog) {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable()  {
                public void run() {
                    showDialog();
                }
            }, 2000); // 2초후
        }
        else{

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment5, container, false);
        imageView = view.findViewById(R.id.GlideImage5_imageview);
        String url = "https://cdn.dominos.co.kr/admin/upload/goods/20200119_Dzj9GV1R.jpg";
        Glide.with(this).load(url).centerInside().fitCenter()
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                showDialog();
            }
        }, 1000); // 2초후

    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("주변 음식점 찾기");
        builder.setMessage("주변에 음식점을 찾고\n"
                + "같이 먹을 사람을 찾아보시겠어요?");
        builder.setCancelable(true);
        builder.setPositiveButton("네!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mViewPager.setCurrentItem(0);
            }
        });
        builder.create().show();
    }

    @Override
    public void onPause() {
        super.onPause();
        isOpenDialog = true;
    }
}