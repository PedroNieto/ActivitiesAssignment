package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.NetworkInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.ui.NewsActivity;
import ar.edu.unc.famaf.redditreader.ui.PostsIteratorListener;


public class Backend {
    private  static final int POST_MAX_COUNT = 50;
    private  static final int PAGE_SIZE = 5;
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getTopPosts(Context context, PostsIteratorListener postsIteratorListener) {
        URL url;
        NetworkInfo networkInfo = ((NewsActivity)context).getNeworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            try {
                url = new URL("https://www.reddit.com/top/.json?limit=50");
                GetTopPostsTask postsTask = new GetTopPostsTask(context, postsIteratorListener);
                postsTask.execute(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else{
            getNextPosts(context,postsIteratorListener,0);
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
            postModel.setPostAuthor(cursor.getString(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_AUTHOR)));
            postModel.setPostLink(cursor.getString(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_LINK)));
            postModel.setPostImgPreview(cursor.getString(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_IMG_PREV_URL)));
            result.add(postModel);
            cursor.moveToNext();
        }
        return result;
    }
    public void getNextPosts(Context context, final PostsIteratorListener listener, int totalItemCount) {
        List<PostModel> resultList;
        if(totalItemCount != POST_MAX_COUNT) {
            RedditDBHelper redditDB = new RedditDBHelper(context, RedditDBHelper.POST_TABLE_VERSION);
            SQLiteDatabase db = redditDB.getReadableDatabase();
            Cursor cursor = db.query(RedditDBHelper.POST_TABLE,null,null,null,null,null,null,totalItemCount + "," + PAGE_SIZE);
            resultList = getPostFromCursor(cursor);
            cursor.close();
            db.close();
            listener.nextPosts(resultList);
        }
    }
}
