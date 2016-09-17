package ar.edu.unc.famaf.redditreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    private final int SIGN_IN_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            NewsActivityFragment newsfragment = (NewsActivityFragment)
                    getSupportFragmentManager().findFragmentById(R.id.news_activity_fragment_id);
            Intent intent = new Intent(this, LoginActivity.class);

            startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == SIGN_IN_REQUEST_CODE){
//                String resultData = data.getExtras().getString(R.string.username_key);
                String resultData = data.getExtras().getString("username");

                TextView textView = (TextView) findViewById(R.id.loginStatusTextView);
                textView.setText("User " + resultData + " logged in");
            }
        }
    }
}
