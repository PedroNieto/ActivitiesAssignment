package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.ui.PostAdapter;

/**
 * Created by pedro on 25/10/16.
 */

public class    GetTopPostsTask extends AsyncTask<URL, Integer, List<PostModel>>{
    private List<PostModel> postList;

    public List<PostModel> getPostList(){
        return this.postList;
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

        this.postList = postModels;



    }
}
