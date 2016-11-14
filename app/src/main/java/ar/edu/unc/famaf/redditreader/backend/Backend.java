package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.ui.PostsIteratorListener;


public class Backend {
    private static Backend ourInstance = new Backend();
    private List<PostModel> postModelList = null;
    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getTopPosts(Context context, PostsIteratorListener postsIteratorListener) {
        URL url;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            try {
                url = new URL("https://www.reddit.com/top/.json?limit=50");
                GetTopPostsTask postsTask = new GetTopPostsTask(context, postsIteratorListener);
                postsTask.execute(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else{
            getNextPosts(context,postsIteratorListener, 0,0);
        }
    }
    private List<PostModel> getPostFromCursor(Cursor cursor){
        List <PostModel> result = new ArrayList<>();
        PostModel postModel;
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
    public void getNextPosts(Context context, final PostsIteratorListener listener, int page, int totalItemCount) {
        List<PostModel> resultList=null;
        if (page == 0 && totalItemCount == 0) {
            RedditDBHelper redditDB = new RedditDBHelper(context, RedditDBHelper.POST_TABLE_VERSION);
            SQLiteDatabase db = redditDB.getReadableDatabase();
            Cursor cursor = db.query(RedditDBHelper.POST_TABLE, RedditDBHelper.colums, null, null, null, null, null);
            postModelList = getPostFromCursor(cursor);
            resultList = postModelList.subList((page) * 5, (page + 2) * 5);
        }else {
            if(totalItemCount != 50) {
                resultList = postModelList.subList((page) * 5, (page + 1) * 5);
            }
        }
        if(totalItemCount != 50) {
            listener.nextPosts(resultList);
        }
    }
}
