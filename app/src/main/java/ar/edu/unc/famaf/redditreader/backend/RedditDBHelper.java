package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;


public class RedditDBHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "db01.db";
    public static final int POST_TABLE_VERSION = 1;
    public static final String POST_TABLE = "post";
    public static final String POST_TABLE_TITLE = "post_title";
    public static final String POST_TABLE_SUBREDDIT = "post_subreddit";
    public static final String POST_TABLE_DATE = "post_date";
    public static final String POST_TABLE_COMMENTS_COUNT = "post_comments_count";
    public static final String POST_TABLE_THUMBNAIL = "post_thumbnail";
    public static final String POST_TABLE_THUMBNAIL_URL = "post_thumbnail_url";
    public static final String POST_TABLE_REDDIT_ID = "reddit_id";

    public static String[] colums = {POST_TABLE_TITLE, POST_TABLE_SUBREDDIT,
            POST_TABLE_DATE, POST_TABLE_COMMENTS_COUNT, POST_TABLE_THUMBNAIL_URL,
            POST_TABLE_REDDIT_ID};

    public RedditDBHelper(Context context, int version){
        super(context, DATA_BASE_NAME, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create table "
                + POST_TABLE+  " ( _id integer primary key autoincrement,"
                + POST_TABLE_REDDIT_ID+ " text not null,"
                + POST_TABLE_TITLE+ " text not null,"
                + POST_TABLE_SUBREDDIT+ " text not null,"
                + POST_TABLE_DATE+ " integer not null,"
                + POST_TABLE_COMMENTS_COUNT+ " integer not null,"
                + POST_TABLE_THUMBNAIL_URL+ " text not null,"
                + POST_TABLE_THUMBNAIL+ " blob);";
        db.execSQL(createSentence);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + POST_TABLE);
        this.onCreate(db);
    }

    public static byte[] getBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0, stream);
        return stream.toByteArray();
    }
    public static Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
