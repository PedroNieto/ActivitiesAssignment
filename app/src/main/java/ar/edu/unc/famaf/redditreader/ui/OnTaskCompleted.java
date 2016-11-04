package ar.edu.unc.famaf.redditreader.ui;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;



public interface OnTaskCompleted {
    void onTaskCompleted(List<PostModel> result);
}
