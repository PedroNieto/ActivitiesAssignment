package ar.edu.unc.famaf.redditreader.model;


public class PostModel {
    private String postSubReddit;
    private String postTitle;
    private int postDate;
    private int postCommentCount;
    private int postImageId;



    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostSubReddit(String postSubReddit) {
        this.postSubReddit = postSubReddit;
    }

    public void setPostDate(int postDate) {
        this.postDate = postDate;
    }

    public void setPostCommentCount(int postCommentCount) {
        this.postCommentCount = postCommentCount;
    }

    public void setPostImageId(int postImageId) {
        this.postImageId = postImageId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostSubReddit() {
        return postSubReddit;
    }


    public int getPostDate() {
        return postDate;
    }

    public int getPostCommentCount() {
        return postCommentCount;
    }

    public int getPostImageId() {
        return postImageId;
    }
}
