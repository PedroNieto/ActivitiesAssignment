package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by pedro on 25/10/16.
 */

public class Parser {
    public List<PostModel> readJsonStream(InputStream in) throws IOException{
        ArrayList<PostModel> postList = null;
        JsonReader reader = new JsonReader(new InputStreamReader(in,"UTF-8"));
        String postSubReddit = null;
        String postTitle = null;
        int postDate = 0; //TODO
        int postCommentCount = 0; //TODO
        String postImageURL = null;
        reader.beginArray();
        /*Reads elements*/
        while (reader.hasNext()){
            JsonReader Oreader = reader;
            Oreader.beginObject();
            /*Read object*/
            while (Oreader.hasNext()){
                String name = Oreader.nextName();
                if(name.equals("subreddit")){
                    postSubReddit = Oreader.nextString();
                } else if (name.equals("title")){
                    postTitle = Oreader.nextString();
                } else if (name.equals("created")){
                    postDate = Oreader.nextInt();
                } else if (name.equals("num_comments")){
                    postCommentCount = Oreader.nextInt();
                } else if (name.equals("thumbnail")) {
                    postImageURL = Oreader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            Oreader.endObject();
            PostModel newPost = new PostModel(postSubReddit, postTitle, postDate, postCommentCount, postImageURL);
            postList.add(newPost);
        }
        reader.endArray();

        return postList;
    }
}
