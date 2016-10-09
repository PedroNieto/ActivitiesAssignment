package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by pedro on 06/10/16.
 */

public class PostAdapter extends ArrayAdapter {
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
                                        (TextView) convertView.findViewById(R.id.postCategoriesTextView),
                                        (TextView) convertView.findViewById(R.id.postDateTextView),
                                        (TextView) convertView.findViewById(R.id.postContentTextView),
                                        (TextView) convertView.findViewById(R.id.postCommentsCountTextView));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PostModel postModel = postLst.get(position);
        if (postModel != null){
            viewHolder.postImageView.setImageResource(postModel.getPostImageId());
            viewHolder.postCategoryTextView.setText(postModel.getPostTitle());
            String time = getContext().getString(R.string.time_of_post, postModel.getPostDate());
            viewHolder.postDateTextView.setText(time);
            viewHolder.postContentTextView.setText(postModel.getPostAuthor());
            String commentsCount = getContext().getString(R.string.comments_amounts, postModel.getPostCommentCount());
            viewHolder.postCommentsCountTextView.setText(commentsCount);
            /*TODO title --- category?? cambiar donde corresponda Chequear que es cada campo */
        }
        return convertView;
    }
    private class ViewHolder {
        public final ImageView postImageView;
        public final TextView postCategoryTextView;
        public final TextView postDateTextView;
        public final TextView postContentTextView;
        public final TextView postCommentsCountTextView;
        public ViewHolder(ImageView postImageView, TextView postCategoryTextView,
                          TextView postDateTextView, TextView postContentTextView,
                          TextView postCommentsCountTextView){
            this.postImageView = postImageView;
            this.postCategoryTextView = postCategoryTextView;
            this.postDateTextView = postDateTextView;
        cd An    this.postContentTextView = postContentTextView;
            this.postCommentsCountTextView = postCommentsCountTextView;
        }
    }
}
