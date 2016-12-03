package ar.edu.unc.famaf.redditreader.model;


import java.io.Serializable;

public class PostModel implements Serializable {
    private String postSubReddit;
    private String postTitle;
    private int postDate;
    private int postCommentCount;
    private String postImageURL;
    private String postID;
    private String postLink;
    private String postImgPreview;
    private String postAuthor="a";

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

    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
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

    public String getPostImageURL() {
        return postImageURL;
    }

    public String  getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public String getPostImgPreview() {
        return postImgPreview;
    }

    public void setPostImgPreview(String postImgPreview) {
        this.postImgPreview = postImgPreview;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }
}
