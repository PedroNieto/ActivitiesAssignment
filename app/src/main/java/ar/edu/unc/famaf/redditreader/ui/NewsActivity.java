package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;



import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class NewsActivity extends AppCompatActivity implements OnPostItemSelectedListener {

    private final int SIGN_IN_REQUEST_CODE = 0;
    private DrawerLayout mDrawerLayout;
    private NewsActivityFragment mFragment;
    private int menuItemID;
    private final String MENU_ITEM_TAG = "menu_item_id_tag";
    private final String FRAGMENT_TAG = "fragment_tag";
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        setContentView(R.layout.activity_news);
        if (savedInstanceState == null) {
            NewsActivityFragment newsActivityFragment = new NewsActivityFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_news_conteiner, newsActivityFragment).commit();
            mFragment = newsActivityFragment;
        } else {
            mFragment =(NewsActivityFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG);
            menuItemID = savedInstanceState.getInt(MENU_ITEM_TAG);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        cancelBackendTask();
                        changeContent(menuItem.getItemId());
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MENU_ITEM_TAG, menuItemID);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_TAG, mFragment);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_in) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SIGN_IN_REQUEST_CODE) {
                String resultData = data.getExtras().getString("username");
                Toast.makeText(getApplicationContext(), getString(R.string.success_login_message, resultData), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_message, Toast.LENGTH_LONG).show();
        }
    }

    private void cancelBackendTask(){
        mFragment.getBackend().cancelBackendTask();
    }
    @Override
    public void onPostItemPicked(PostModel post){
        Intent intent = new Intent(this, NewsDetailActivity.class);
        String POST_DETAIL_TAG = "detail_post_tag";
        intent.putExtra(POST_DETAIL_TAG, post);
        startActivity(intent);
    }

    public void changeContent(int menuItemID){
        this.menuItemID = menuItemID;
        mFragment.cleanData();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(mFragment);
        ft.attach(mFragment);
        ft.commit();
    }

    public NetworkInfo getNeworkInfo() {
        return connectivityManager.getActiveNetworkInfo();
    }

    public int getMenuItemID(){
        return menuItemID;
    }
}
