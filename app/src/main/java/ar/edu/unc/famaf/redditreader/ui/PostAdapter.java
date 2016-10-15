package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;



public class PostAdapter extends ArrayAdapter{
    private List<PostModel> postLst;

    public PostAdapter(Context context, int TextViewResourceId, List<PostModel> postLst){
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
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_post, null);
            viewHolder = new ViewHolder((ImageView) convertView.findViewById(R.id.postImageView),
                                        (TextView) convertView.findViewById(R.id.postSubRedditTextView),
                                        (TextView) convertView.findViewById(R.id.postDateTextView),
                                        (TextView) convertView.findViewById(R.id.postTitleTextView),
                                        (TextView) convertView.findViewById(R.id.postCommentsCountTextView));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PostModel postModel = postLst.get(position);
        if (postModel != null){
            viewHolder.postImageView.setImageResource(postModel.getPostImageId());
            viewHolder.postSubRedditTextView.setText(postModel.getPostSubReddit());
            String time = getContext().getString(R.string.time_of_post, postModel.getPostDate());
            viewHolder.postDateTextView.setText(time);
            viewHolder.postTitleTextView.setText(postModel.getPostTitle());
            String commentsCount = getContext().getString(R.string.comments_amounts, postModel.getPostCommentCount());
            viewHolder.postCommentsCountTextView.setText(commentsCount);
         }
        return convertView;
    }
    private class ViewHolder {
        public final ImageView postImageView;
        public final TextView postSubRedditTextView;
        public final TextView postDateTextView;
        public final TextView postTitleTextView;
        public final TextView postCommentsCountTextView;
        public ViewHolder(ImageView postImageView, TextView postSubRedditTextView,
                          TextView postDateTextView, TextView postTitleTextView,
                          TextView postCommentsCountTextView){
            this.postImageView = postImageView;
            this.postSubRedditTextView = postSubRedditTextView;
            this.postDateTextView = postDateTextView;
            this.postTitleTextView = postTitleTextView;
            this.postCommentsCountTextView = postCommentsCountTextView;
        }
    }

    private class ImageDownloader extends AsyncTask<URL,Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(URL... urls){
            URL url = urls[0];
            Bitmap bitmap = null;
            HttpsURLConnection connection =null;
            try{
                connection = (HttpsURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is,null, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
