package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;



public class PostAdapter extends ArrayAdapter{
    private static final int MINUTE_IN_SEC = 60;
    private static final int HOUR_IN_SEC = 3600;
    private static final int DAY_IN_SEC = 86400;
    private static final int MONTH_IN_SEC = 2592000;
    private List<PostModel> postLst;
    private LruCache<Integer, Bitmap> cache = new LruCache<>(512);


    PostAdapter(Context context, int TextViewResourceId, List<PostModel> postLst){
        super(context,TextViewResourceId);
        this.postLst = postLst;
    }
    @Override
    public int getCount(){
        return postLst.size();
    }
    @Override
    public PostModel getItem(int position){
        return postLst.get(position);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_post, parent, false);
            viewHolder = new ViewHolder((ImageView) convertView.findViewById(R.id.postImageView),
                                        (TextView) convertView.findViewById(R.id.postSubRedditTextView),
                                        (TextView) convertView.findViewById(R.id.postDateTextView),
                                        (TextView) convertView.findViewById(R.id.postTitleTextView),
                                        (TextView) convertView.findViewById(R.id.postCommentsCountTextView),
                                        (ProgressBar) convertView.findViewById(R.id.progresBar));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PostModel postModel = postLst.get(position);

        if (postModel != null){
            URL url;
            viewHolder.postSubRedditTextView.setText(postModel.getPostSubReddit());
            String time =formatTime(postModel.getPostDate());

            viewHolder.postDateTextView.setText(time);
            viewHolder.postTitleTextView.setText(postModel.getPostTitle());
            String commentsCount = getContext().getString(R.string.comments_amounts, postModel.getPostCommentCount());
            viewHolder.postCommentsCountTextView.setText(commentsCount);
            try {
                if (postModel.getPostImageURL().startsWith("http")) {
                    url = new URL(postModel.getPostImageURL());
                    Bitmap btm = cache.get(position);
                    if ( btm == null){
                        new ImageDownloader(viewHolder, position).execute(url);
                    }else{
                        viewHolder.postImageView.setImageBitmap(btm);
                        viewHolder.progressBar.setVisibility(View.INVISIBLE);
                    }

                }else{
                    viewHolder.postImageView.setImageResource(R.drawable.reddit);
                    viewHolder.progressBar.setVisibility(View.INVISIBLE);
                }

            }catch (MalformedURLException e){
                e.printStackTrace();
            }

         }
        return convertView;
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

    private class ViewHolder {
        final ImageView postImageView;
        final TextView postSubRedditTextView;
        final TextView postDateTextView;
        final TextView postTitleTextView;
        final TextView postCommentsCountTextView;
        final ProgressBar progressBar;
        ViewHolder(ImageView postImageView, TextView postSubRedditTextView,
                          TextView postDateTextView, TextView postTitleTextView,
                          TextView postCommentsCountTextView, ProgressBar progressBar){
            this.postImageView = postImageView;
            this.postSubRedditTextView = postSubRedditTextView;
            this.postDateTextView = postDateTextView;
            this.postTitleTextView = postTitleTextView;
            this.postCommentsCountTextView = postCommentsCountTextView;
            this.progressBar = progressBar;
        }
    }

    private class ImageDownloader extends AsyncTask<URL,Integer, Bitmap> {

        private ViewHolder viewHolder;
        private int position;
        ImageDownloader(ViewHolder viewHolder, int position){
            this.viewHolder = viewHolder;
            this.position = position;
        }
        @Override
        protected void onPreExecute(){
            viewHolder.progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Bitmap doInBackground(URL... urls){
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
        protected void onPostExecute(Bitmap result) {
            if (result != null){
                viewHolder.postImageView.setImageBitmap(result);
                cache.put(position,result);
                viewHolder.progressBar.setVisibility(View.INVISIBLE);
            }
        }

    }
}
