package ar.edu.unc.famaf.redditreader.model;


public class PostModel {
    private String postTitle;
    private String postAuthor;
    private String postCategory;
    private String postContent;
    private int postDate;
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

    public void setPostContent(String postContent) {
        this.postContent = postContent;
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

    public String getPostAuthor() {
        return postAuthor;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public String getPostContent() {
        return postContent;
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
