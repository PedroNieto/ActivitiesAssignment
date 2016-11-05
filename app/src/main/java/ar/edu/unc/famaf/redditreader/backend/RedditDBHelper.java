package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RedditDBHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "db01.db";
    private static final String POST_TITLE_TABLE = "post";

    public RedditDBHelper(Context context, int version){
        super(context, DATA_BASE_NAME, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create table "
                + POST_TITLE_TABLE+  " ( _id integer primary key autoincrement,"
                + ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
