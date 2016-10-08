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
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_post, null);
        }
        PostModel postModel = postLst.get(position);
        if (postModel != null){
            ImageView postImagePreview = (ImageView) convertView.findViewById(R.id.postImageView);
//            TextView postCategory = (TextView) convertView.findViewById(R.id.postCategoriesTextView);
            TextView postTitle = (TextView) convertView.findViewById(R.id.postCategoriesTextView); //TODO Asegurar que sea correcto
            TextView postDate = (TextView) convertView.findViewById(R.id.postDateTextView);
//            TextView postContent = (TextView) convertView.findViewById(R.id.postContentTextView);
            TextView postAuthor = (TextView) convertView.findViewById(R.id.postContentTextView);
            TextView postCommentsCount = (TextView) convertView.findViewById(R.id.postCommentsCountTextView);
            /*TODO title --- category?? cambiar donde corresponda Chequear que es cada campo */

            postImagePreview.setImageResource(postModel.getPostImageId());
            postTitle.setText(postModel.getPostTitle());
            postDate.setText(Resources.getSystem().getString(R.string.time_of_post, postModel.getPostDate()));
            postAuthor.setText(postModel.getPostAuthor());
            postCommentsCount.setText(postModel.getPostCommentCount());
        }
        return null;
    }
}
