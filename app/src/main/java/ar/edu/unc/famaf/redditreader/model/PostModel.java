package ar.edu.unc.famaf.redditreader.model;


import java.util.Date;

public class PostModel {
    private String postTitle;
    private String postAuthor;
    private String postCategory;
    private Date postDate;
    private int postCommentCount;
    private int postImageId;


    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

    public void setPostDate(Date postDate) {
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

    public String getPostAuthor() {
        return postAuthor;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public Date getPostDate() {
        return postDate;
    }

    public int getPostCommentCount() {
        return postCommentCount;
    }

    public int getPostImageId() {
        return postImageId;
    }
}
