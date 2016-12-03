package ar.edu.unc.famaf.redditreader.ui;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class NewsDetailActivityFragment extends Fragment {
    private static final int MINUTE_IN_SEC = 60;
    private static final int HOUR_IN_SEC = 3600;
    private static final int DAY_IN_SEC = 86400;
    private static final int MONTH_IN_SEC = 2592000;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_detail_activity, container, false);
        PostModel post = ((NewsDetailActivity)getActivity()).getPost();
        setPostDetailData(view, post);
        return view;
    }
    private void setPostDetailData(View view, PostModel post){
        ((TextView)view.findViewById(R.id.title_text)).setText(post.getPostTitle());
        ((TextView)view.findViewById(R.id.subreddit_text)).setText(post.getPostSubReddit());
        ((TextView)view.findViewById(R.id.author_text)).setText(post.getPostAuthor());
        ((TextView)view.findViewById(R.id.date_text)).setText(formatTime(post.getPostDate()));
        if (post.getPostLink() != null){
            ((TextView)view.findViewById(R.id.url_source)).setText(post.getPostLink());
        }
        ifImgPreviewLoad(view, post);
    }

    private void ifImgPreviewLoad(View view, PostModel post) {
        if(post.getPostImgPreview() != null){
            try {
                URL imgURL = new URL(post.getPostImgPreview());
                new DownloadImg(view).execute(imgURL);

            }catch (MalformedURLException e){
                e.printStackTrace();
            }
        }
    }

    private class DownloadImg extends AsyncTask<URL, Integer, Bitmap>{
        private View view;
        DownloadImg(View view){
            this.view = view;
        }
        @Override
        protected Bitmap doInBackground(URL...urls){
            URL url = urls[0];
            Bitmap bitmap;
            HttpURLConnection connection;

            try{
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is,null, null);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            if (bitmap != null) {
                ((ImageView)view.findViewById(R.id.preview_img)).setImageBitmap(bitmap);
            }
        }


    }
    private String formatTime(int postDate) {
        long now = System.currentTimeMillis()/1000;
        long duration = now-postDate;
        if(duration < MINUTE_IN_SEC){
            return getContext().getString(R.string.time_of_post_seconds);
        }else if (duration < HOUR_IN_SEC) {
            return getContext().getString(R.string.time_of_post_minutes, duration/60);
        }else if (duration < DAY_IN_SEC) {
            return getContext().getString(R.string.time_of_post_hours, duration/3600);
        }else if (duration < MONTH_IN_SEC){
            return getContext().getString(R.string.time_of_post_days, duration/86400);
        }else {
            return getContext().getString(R.string.time_of_post_more);
        }

    }
 }
