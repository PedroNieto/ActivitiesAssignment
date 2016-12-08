package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;


public class RedditDBHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "db04.db";
    static final int POST_TABLE_VERSION = 4;
    static final String TOP_POST_TABLE = "post_top";
    static final String HOT_POST_TABLE = "post_hot";
    static final String NEW_POST_TABLE = "post_new";
    static final String POST_TABLE_TITLE = "post_title";
    static final String POST_TABLE_SUBREDDIT = "post_subreddit";
    static final String POST_TABLE_DATE = "post_date";
    static final String POST_TABLE_COMMENTS_COUNT = "post_comments_count";
    static final String POST_TABLE_THUMBNAIL = "post_thumbnail";
    static final String POST_TABLE_THUMBNAIL_URL = "post_thumbnail_url";
    static final String POST_TABLE_REDDIT_ID = "reddit_id";
    static final String POST_TABLE_AUTHOR = "post_author";
    static final String POST_TABLE_LINK = "post_link";
    static final String POST_TABLE_IMG_PREV_URL = "post_img_prev_url";


    RedditDBHelper(Context context, int version){
        super(context, DATA_BASE_NAME, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        createDBTable(TOP_POST_TABLE, db);
        createDBTable(HOT_POST_TABLE, db);
        createDBTable(NEW_POST_TABLE, db);
    }
    private void createDBTable(String nameTable, SQLiteDatabase db) {
        String createSentence = "create table "
                + nameTable+  " ( _id integer primary key autoincrement,"
                + POST_TABLE_REDDIT_ID+ " text not null,"
                + POST_TABLE_TITLE+ " text not null,"
                + POST_TABLE_SUBREDDIT+ " text not null,"
                + POST_TABLE_DATE+ " integer not null,"
                + POST_TABLE_COMMENTS_COUNT+ " integer not null,"
                + POST_TABLE_THUMBNAIL_URL+ " text not null,"
                + POST_TABLE_AUTHOR+ " text not null,"
                + POST_TABLE_LINK+ " text,"
                + POST_TABLE_IMG_PREV_URL+ " text,"
                + POST_TABLE_THUMBNAIL+ " blob);";
        db.execSQL(createSentence);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TOP_POST_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + HOT_POST_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + NEW_POST_TABLE);
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
