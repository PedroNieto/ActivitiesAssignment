package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
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
    private GetTopPostsTask backendTask;
    private RedditDBHelper redditDB;
    private String tableName;

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getTopPosts(Context context, PostsIteratorListener postsIteratorListener) {
        this.tableName = RedditDBHelper.TOP_POST_TABLE;
        String url = "https://www.reddit.com/top/.json?limit=50";
        getPosts(context,postsIteratorListener,url);
    }
    public void getHotPosts(Context context, PostsIteratorListener postsIteratorListener) {
        this.tableName = RedditDBHelper.HOT_POST_TABLE;
        String url = "https://www.reddit.com/hot/.json?limit=50";
        getPosts(context,postsIteratorListener,url);
    }
    public void getNewPosts(Context context, PostsIteratorListener postsIteratorListener) {
        this.tableName = RedditDBHelper.NEW_POST_TABLE;
        String url = "https://www.reddit.com/new/.json?limit=50";
        getPosts(context,postsIteratorListener,url);
    }

    private void getPosts(Context context, PostsIteratorListener postsIteratorListener, String apiURL) {
        URL url;
        NetworkInfo networkInfo = ((NewsActivity)context).getNeworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            try {
                url = new URL(apiURL);
                GetTopPostsTask postsTask = new GetTopPostsTask(context, postsIteratorListener, this);
                backendTask = postsTask;
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
            if (this.redditDB == null) {
                this.redditDB = new RedditDBHelper(context, RedditDBHelper.POST_TABLE_VERSION);
            }
            SQLiteDatabase db = redditDB.getReadableDatabase();
            Cursor cursor = db.query(this.tableName,null,null,null,null,null,null,totalItemCount + "," + PAGE_SIZE);
            resultList = getPostFromCursor(cursor);
            cursor.close();
            db.close();
            listener.nextPosts(resultList);
        }
    }

    public void cancelBackendTask(){
        if (backendTask != null) {
            backendTask.cancel(true);
        }
    }

    public void updateDBThumbnail(byte[] imageByte, String postID){
        SQLiteDatabase db = redditDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RedditDBHelper.POST_TABLE_THUMBNAIL, imageByte);
        db.update(getTableName(), values,RedditDBHelper.POST_TABLE_REDDIT_ID + " = ?",new String[] {postID});
        db.close();
    }

    public byte[] getImageOfPost(String postID){
        SQLiteDatabase db = redditDB.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + RedditDBHelper.POST_TABLE_THUMBNAIL + " FROM " +
                getTableName() + " WHERE " + RedditDBHelper.POST_TABLE_REDDIT_ID +
                "=?",new String[] {postID});

        cursor.moveToFirst();
        byte[] imageByte = cursor.getBlob(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_THUMBNAIL));
        cursor.close();
        db.close();
        return imageByte;
    }

    String getTableName(){
        return this.tableName;
    }
}
