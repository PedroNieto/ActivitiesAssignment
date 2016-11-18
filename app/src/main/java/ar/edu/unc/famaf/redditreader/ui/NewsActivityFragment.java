package ar.edu.unc.famaf.redditreader.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        Backend.getInstance().getTopPosts(getContext(),this);

        return view;
    }

    @Override
    public void nextPosts(List<PostModel> posts) {
        if (posts != null) {
            postModelList.addAll(posts);
            adapter.notifyDataSetChanged();
        }
    }
}
