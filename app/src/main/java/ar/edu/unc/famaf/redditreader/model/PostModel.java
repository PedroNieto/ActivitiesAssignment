package ar.edu.unc.famaf.redditreader.model;


import java.io.Serializable;

public class PostModel implements Serializable {
    private String postSubReddit;
    private String postTitle;
    private String postImageURL;
    private String postID;
    private String postLink;
    private String postImgPreview;
    private String postAuthor;
    private int postDate;
    private int postCommentCount;

    public void setPostSubReddit(String postSubReddit) {
        this.postSubReddit = postSubReddit;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public void setPostImgPreview(String postImgPreview) {
        this.postImgPreview = postImgPreview;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public void setPostDate(int postDate) {
        this.postDate = postDate;
    }

    public void setPostCommentCount(int postCommentCount) {
        this.postCommentCount = postCommentCount;
    }

    public String getPostSubReddit() {
        return postSubReddit;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostImageURL() {
        return postImageURL;
    }

    public String  getPostID() {
        return postID;
    }

    public String getPostLink() {
        return postLink;
    }

    public String getPostImgPreview() {
        return postImgPreview;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public int getPostDate() {
        return postDate;
    }

    public int getPostCommentCount() {
        return postCommentCount;
    }

}
