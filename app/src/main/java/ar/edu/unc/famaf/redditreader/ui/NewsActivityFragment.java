package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class NewsActivityFragment extends Fragment implements PostsIteratorListener {
    PostAdapter adapter;
    ListView postModelLV = null;
    private List<PostModel> postModelList = new ArrayList<>();
    OnPostItemSelectedListener mCallback;

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        postModelLV = (ListView) view.findViewById(R.id.post_list_view);
        adapter = new PostAdapter(getActivity(), R.layout.item_post, postModelList);
        final PostsIteratorListener postsIteratorListener = this;
        postModelLV.setOnScrollListener(new EndlessScrollListener(){
            @Override
            public boolean onLoadMore(int page, int totalItemsCount){
                Backend.getInstance().getNextPosts(getContext(),postsIteratorListener, totalItemsCount);
                return true;
            }
        });
        postModelLV.setAdapter(adapter);
        postModelLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO llamar a metodo de la interface
                mCallback.onPostItemPicked(adapter.getItem(position));
            }
        });
        Backend.getInstance().getTopPosts(getContext(),this);

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mCallback = (OnPostItemSelectedListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new ClassCastException(context.toString()+" must implement OnPostItemSelected");
        }
    }
    @Override
    public void nextPosts(List<PostModel> posts) {
        if (posts != null) {
            postModelList.addAll(posts);
            adapter.notifyDataSetChanged();
        }
    }
}
