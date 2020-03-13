package org.first.meeatnow.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.first.meeatnow.R;

public class TabFragment2 extends Fragment {
    private ImageView imageView;
    public TabFragment2() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment2, container, false);
        imageView = view.findViewById(R.id.GlideImage2_imageview);
        String url = "https://www.pizzaetang.com/resources/images/menu/menuinfo/PZ_TT2_l.png";
        Glide.with(this).load(url).centerInside().fitCenter()
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
        return view;
    }
}