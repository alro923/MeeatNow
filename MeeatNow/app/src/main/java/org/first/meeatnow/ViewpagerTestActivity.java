package org.first.meeatnow;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.first.meeatnow.ViewPager.TabFragment1;
import org.first.meeatnow.ViewPager.TabFragment2;
import org.first.meeatnow.ViewPager.TabFragment3;
import org.first.meeatnow.ViewPager.TabFragment4;
import org.first.meeatnow.ViewPager.TabFragment5;


public class ViewpagerTestActivity extends AppCompatActivity {
    public static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_test);
        CheckTypesTask task = new CheckTypesTask();
        task.execute();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);
        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int)v.getTag();
                mViewPager.setCurrentItem(tag);
            }
        };
    }

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TabFragment1();
                case 1:
                    return new TabFragment2();
                case 2:
                    return new TabFragment3();
                case 3:
                    return new TabFragment4();
                case 4:
                    return  new TabFragment5();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(ViewpagerTestActivity.this);

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
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }

}
