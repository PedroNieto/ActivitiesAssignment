package ar.edu.unc.famaf.redditreader.backend;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.ui.NewsActivity;
import ar.edu.unc.famaf.redditreader.ui.OnTaskCompleted;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getTopPosts(OnTaskCompleted listener) {
        List<PostModel> postModelList = null;
        URL url = null;
        try {
            url = new URL("https://www.reddit.com/r/all/top/.json?limit=50");
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        GetTopPostsTask postsTask = new GetTopPostsTask(listener);

        postsTask.execute(url);
    }
}
