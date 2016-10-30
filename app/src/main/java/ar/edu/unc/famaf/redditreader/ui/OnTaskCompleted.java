package ar.edu.unc.famaf.redditreader.ui;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by pedro on 29/10/16.
 */

public interface OnTaskCompleted {
    void onTaskCompleted(List<PostModel> result);
}
