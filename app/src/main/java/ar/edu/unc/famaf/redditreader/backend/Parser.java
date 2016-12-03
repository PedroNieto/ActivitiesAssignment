package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;


class Parser {
     List<PostModel> readJsonStream(InputStream in) throws IOException{
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
        ArrayList<PostModel> postList = new ArrayList<>();
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
        PostModel postModel = new PostModel();

        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "subreddit":
                    postModel.setPostSubReddit(reader.nextString());
                    break;
                case "title":
                    postModel.setPostTitle(reader.nextString());
                    break;
                case "created_utc":
                    postModel.setPostDate(reader.nextInt());
                    break;
                case "num_comments":
                    postModel.setPostCommentCount(reader.nextInt());
                    break;
                case "thumbnail":
                    postModel.setPostImageURL(reader.nextString());
                    break;
                case "id":
                    postModel.setPostID(reader.nextString());
                    break;
                case "url":
                    postModel.setPostLink(reader.nextString());
                    break;
                case "author":
                    postModel.setPostAuthor(reader.nextString());
                    break;
                case "preview":
                    postModel.setPostImgPreview(parseJsonPreview(reader));
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        return postModel;
    }

    private String parseJsonPreview(JsonReader reader) throws IOException{
        String result = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("images")) {
                result = parseJsonImages(reader);
            } else {
                    reader.skipValue();
            }
        }
        reader.endObject();
        return result;
    }

    private String parseJsonImages(JsonReader reader) throws IOException{
        String result = null;
        reader.beginArray();
        while (reader.hasNext()) {
            result = parseJsonImagesData(reader);
        }
        reader.endArray();
        return result;
    }

    private String parseJsonImagesData(JsonReader reader) throws IOException {
        String result = null;
        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if (name.equals("source")) {
                result = parseJsonGetUrl(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return result;
    }

    private String parseJsonGetUrl(JsonReader reader) throws IOException {
        String result = null;
        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if (name.equals("url")) {
                result = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return result;
    }
}

