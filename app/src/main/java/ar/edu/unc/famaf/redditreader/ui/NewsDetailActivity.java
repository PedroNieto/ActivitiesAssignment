package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class NewsDetailActivity extends AppCompatActivity {
    final String FRAGMENT_TAG = "detail_fragment_tag";
    final String POST_DETAIL_TAG = "detail_post_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            FragmentTransaction ft = fm.beginTransaction();
            NewsDetailActivityFragment newsDetailActivityFragment = new NewsDetailActivityFragment();
            ft.add(R.id.fragment_conteiner, newsDetailActivityFragment, FRAGMENT_TAG);
            ft.commit();
        }
    }
    public void urlClick(View view) {
        String url = (String) ((TextView) view).getText();
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }
    public PostModel getPost(){
        return (PostModel) getIntent().getSerializableExtra(POST_DETAIL_TAG);
    }


}
