package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class NewsActivity extends AppCompatActivity implements OnTaskCompleted{

    private final int SIGN_IN_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activtyNetwork = cm.getActiveNetworkInfo();
        if (activtyNetwork != null && activtyNetwork.isConnectedOrConnecting()) {
            Backend.getInstance().getTopPosts(this);
        }else{
            onTaskCompleted();
        }
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
    private  List<PostModel> getPostFromDB(SQLiteDatabase db){
        List <PostModel> result = new ArrayList<>();
        PostModel postModel;
        Cursor cursor = db.query(RedditDBHelper.POST_TABLE, RedditDBHelper.colums,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            postModel = new PostModel();
            postModel.setPostTitle(cursor.getString(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_TITLE)));
            postModel.setPostSubReddit(cursor.getString(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_SUBREDDIT)));
            postModel.setPostDate(cursor.getInt(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_DATE)));
            postModel.setPostCommentCount(cursor.getInt(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_COMMENTS_COUNT)));
            postModel.setPostImageURL(cursor.getString(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_THUMBNAIL_URL)));
            postModel.setPostID(cursor.getString(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_REDDIT_ID)));
            result.add(postModel);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }
    public void onTaskCompleted(){
        SQLiteDatabase db = new RedditDBHelper(this, RedditDBHelper.POST_TABLE_VERSION).getReadableDatabase();
        List <PostModel> result = getPostFromDB(db);
        if (result != null) {
            PostAdapter adapter = new PostAdapter(this, R.layout.item_post, result);
            ListView postModelLV = (ListView) findViewById(R.id.post_list_view);
            postModelLV.setAdapter(adapter);
        }else{
            Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
        }
    }
}
