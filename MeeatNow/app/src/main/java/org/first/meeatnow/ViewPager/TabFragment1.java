package org.first.meeatnow.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.first.meeatnow.R;

public class TabFragment1 extends Fragment {
    private ImageView imageView;
    public TabFragment1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);
        imageView = view.findViewById(R.id.GlideImage1_imageview);
        String url = "http://www.pizzabig.co.kr/theme/basic/img/sub0201_img03.jpg";
        Glide.with(this).load(url).centerInside().fitCenter()
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
        return view;
    }
}