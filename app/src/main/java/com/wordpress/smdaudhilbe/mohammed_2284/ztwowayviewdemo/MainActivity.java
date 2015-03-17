package com.wordpress.smdaudhilbe.mohammed_2284.ztwowayviewdemo;

//  demo of twoWayView usage @ https://github.com/quasar-academy/RecyclerView


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fragment.LayoutFragment;


public class MainActivity extends ActionBarActivity {

    private final String ARG_SELECTED_LAYOUT_ID = "selectedLayoutId";
    private final int DEFAULT_LAYOUT = R.layout.layout_list;
    private int mSelectedLayoutId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mSelectedLayoutId = DEFAULT_LAYOUT;
        if (savedInstanceState != null) {
            mSelectedLayoutId = savedInstanceState.getInt(ARG_SELECTED_LAYOUT_ID);
        }

        addLayoutTab(getSupportActionBar(), R.layout.layout_list, R.drawable.ic_list, "list");
        addLayoutTab(getSupportActionBar(), R.layout.layout_grid, R.drawable.ic_grid, "grid");
        addLayoutTab(getSupportActionBar(), R.layout.layout_staggered_grid, R.drawable.ic_staggered, "staggered");
        addLayoutTab(getSupportActionBar(), R.layout.layout_spannable_grid, R.drawable.ic_spannable, "spannable");
    }

    private void addLayoutTab(ActionBar actionBar, int layoutId, int iconId, String tag) {
        ActionBar.Tab tab = actionBar.newTab()
                .setText("")
                .setIcon(iconId)
                .setTabListener(new TabListener(layoutId, tag));
        actionBar.addTab(tab, layoutId == mSelectedLayoutId);
    }

    //  Tab click listener
    public class TabListener implements ActionBar.TabListener {
        private LayoutFragment mFragment;
        private final int mLayoutId;
        private final String mTag;

        public TabListener(int layoutId, String tag) {
            mLayoutId = layoutId;
            mTag = tag;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            mFragment = (LayoutFragment) getSupportFragmentManager().findFragmentByTag(mTag);
            if (mFragment == null) {
                mFragment = (LayoutFragment) LayoutFragment.newInstance(mLayoutId);
                ft.add(R.id.content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }

            mSelectedLayoutId = mFragment.getLayoutId();
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
            }
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Toast mToast = null;

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            mToast = Toast.makeText(getApplicationContext(), "SettingsClicked", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
