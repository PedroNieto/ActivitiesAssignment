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
        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if (name.equals("data")){
                postList = readJsonData(reader);
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();

        return postList;
    }

    private ArrayList<PostModel> readJsonData(JsonReader reader)throws IOException{
        ArrayList<PostModel> postList = null;

        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("children")) {
                postList = readJsonChildren(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return postList;
    }

    private ArrayList<PostModel> readJsonChildren(JsonReader reader)throws IOException{
        ArrayList<PostModel> postList = new ArrayList<PostModel>();
        reader.beginArray();

        while (reader.hasNext()){
            postList.add(readJsonPostData(reader));
        }

        reader.endArray();

        return postList;
    }

    private PostModel readJsonPostData(JsonReader reader) throws IOException {
        PostModel postModel = null;
        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("data")) {
                postModel = getPostFromJsonData(reader);
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();

        return postModel;
    }

    private PostModel getPostFromJsonData(JsonReader reader) throws IOException {
        PostModel postModel;
        String postSubReddit = null;
        String postTitle = null;
        int postDate = 0; //TODO
        int postCommentCount = 0; //TODO
        String postImageURL = null;


        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("subreddit")){
                postSubReddit = reader.nextString();
            } else if (name.equals("title")){
                postTitle = reader.nextString();
            } else if (name.equals("created")){
                postDate = reader.nextInt();
            } else if (name.equals("num_comments")){
                postCommentCount = reader.nextInt();
            } else if (name.equals("thumbnail")) {
                postImageURL = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        postModel = new PostModel(postSubReddit, postTitle, postDate, postCommentCount, postImageURL);

        return postModel;
    }
}

