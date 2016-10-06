package ar.edu.unc.famaf.redditreader.backend;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public List<PostModel> getTopPosts() {
        PostModel post0 = new PostModel();
        PostModel post1 = new PostModel();
        PostModel post2 = new PostModel();
        PostModel post3 = new PostModel();
        PostModel post4 = new PostModel();

        post0.setPostTitle("Titulo post0");
        post1.setPostTitle("Titulo post1");
        post2.setPostTitle("Titulo post2");
        post3.setPostTitle("Titulo post3");
        post4.setPostTitle("Titulo post4");

        post0.setPostAuthor("Autor post0");
        post1.setPostAuthor("Autor post1");
        post2.setPostAuthor("Autor post2");
        post3.setPostAuthor("Autor post3");
        post4.setPostAuthor("Autor post4");

        post0.setPostCommentCount(12);
        post1.setPostCommentCount(123);
        post2.setPostCommentCount(124);
        post3.setPostCommentCount(1255);
        post4.setPostCommentCount(165332);

        post0.setPostDate(new Date(1475791058));
        post1.setPostDate(new Date(1475791058));
        post2.setPostDate(new Date(1475791058));
        post3.setPostDate(new Date(1475791058));
        post4.setPostDate(new Date(1475791058));

        post0.setPostCategory("r/Categoria0");
        post1.setPostCategory("r/Categoria1");
        post2.setPostCategory("r/Categoria2");
        post3.setPostCategory("r/Categoria3");
        post4.setPostCategory("r/Categoria4");

        post0.setPostImageId(R.drawable.reddit);
        post1.setPostImageId(R.drawable.reddit);
        post2.setPostImageId(R.drawable.reddit);
        post3.setPostImageId(R.drawable.reddit);
        post4.setPostImageId(R.drawable.reddit);

        List<PostModel> postModelList = new ArrayList<PostModel>();
        postModelList.add(post0);
        postModelList.add(post1);
        postModelList.add(post2);
        postModelList.add(post3);
        postModelList.add(post4);

        return postModelList;
    }
}
