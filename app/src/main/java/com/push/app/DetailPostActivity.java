package com.push.app;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.push.app.adapter.PostFragmentAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;


public class DetailPostActivity extends ActionBarActivity {
    /**
     * The actionbar
     */
    private Toolbar mToolbar;
    ViewPager mPager;
    PageIndicator mIndicator;
    private PostFragmentAdapter mAdapter;
    private int postPosition;
    private String mPostTitle;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        Bundle m = getIntent().getExtras();
        postPosition = m.getInt("postPosition");
        mPostTitle = m.getString("postTitle");
        desc = m.getString("description");

        //initialise the actionBar
        initActionBar();

        mAdapter = new PostFragmentAdapter(this, getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(2);
        int pageCount = mPager.getAdapter().getCount();
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager, postPosition);
        if(pageCount == 1) {
            View mIndicatorView = (View)mIndicator;
            mIndicatorView.setVisibility(View.GONE);
        }
    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_share){
            doShare();
        }else if(id==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void doShare() {
        final Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, mPostTitle);
        share.putExtra(Intent.EXTRA_SUBJECT, desc);

        try {
            startActivity(Intent.createChooser(share, "Select an action"));
        } catch (android.content.ActivityNotFoundException ex) {
            // (handle error)
        }
    }


}
