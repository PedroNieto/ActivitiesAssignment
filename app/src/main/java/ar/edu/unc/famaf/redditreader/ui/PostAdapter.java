package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

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

    public int getCount(){
        return postLst.size();
    }

    public PostModel getItem(int position){
        return postLst.get(position);
    }

    public long getItemId(int position){
        return postLst.;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        return null;
    }
}
