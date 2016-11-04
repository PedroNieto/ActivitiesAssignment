package ar.edu.unc.famaf.redditreader.backend;

import java.net.MalformedURLException;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.ui.OnTaskCompleted;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getTopPosts(OnTaskCompleted listener) {
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
