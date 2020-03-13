package org.first.meeatnow.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.first.meeatnow.R;

public class TabFragment4 extends Fragment {
    private ImageView imageView;
    public TabFragment4() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment4, container, false);
        imageView = view.findViewById(R.id.GlideImage4_imageview);
        String url = "http://handmadepizza.co.kr/wp-content/uploads/%EB%B2%A0%EC%9D%B4%EC%BB%A8%EC%B2%B4%EB%8B%A4%EC%B9%98%EC%A6%88%EC%8D%B8%EB%84%AC-copy-800x400.jpg";
        Glide.with(this).load(url).centerInside().fitCenter()
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
        return view;
    }
}