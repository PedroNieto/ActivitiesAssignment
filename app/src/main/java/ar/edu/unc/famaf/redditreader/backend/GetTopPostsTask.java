package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.ui.OnTaskCompleted;



class    GetTopPostsTask extends AsyncTask<URL, Integer, List<PostModel>>{
    private Context context;

    GetTopPostsTask(Context context){
        this.context = context;
    }
    @Override
    protected List<PostModel> doInBackground(URL... urls) {
        List<PostModel> list = null;
        try {
            HttpsURLConnection connection = (HttpsURLConnection) urls[0].openConnection();
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();
            Parser parser= new Parser();
            list = parser.readJsonStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;

    }

    @Override
    protected void onPostExecute(List<PostModel> postModels) {
        super.onPostExecute(postModels);
        OnTaskCompleted onTaskCompleted =(OnTaskCompleted) context;
        RedditDBHelper redditDB = new RedditDBHelper(context, RedditDBHelper.POST_TABLE_VERSION);
        SQLiteDatabase db = redditDB.getWritableDatabase();
        db.delete(RedditDBHelper.POST_TABLE, null, null);
        ContentValues values = new ContentValues();
        for (PostModel postModel : postModels) {
            values.put(RedditDBHelper.POST_TABLE_TITLE, postModel.getPostTitle());
            values.put(RedditDBHelper.POST_TABLE_SUBREDDIT, postModel.getPostSubReddit());
            values.put(RedditDBHelper.POST_TABLE_DATE, postModel.getPostDate());
            values.put(RedditDBHelper.POST_TABLE_COMMENTS_COUNT, postModel.getPostCommentCount());
            values.put(RedditDBHelper.POST_TABLE_THUMBNAIL_URL, postModel.getPostImageURL());
            values.put(RedditDBHelper.POST_TABLE_REDDIT_ID, postModel.getPostID());
            db.insert(RedditDBHelper.POST_TABLE, null, values);
        }
        onTaskCompleted.onTaskCompleted();

    }
}
