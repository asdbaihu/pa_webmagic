package spiderZssh.Entity;

public class category {
    private String id ;
    private String categoryId;//parentid
    private String categoryNmae;

    public category(String categoryId, String categoryNmae) {
        this.categoryId = categoryId;
        this.categoryNmae = categoryNmae;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryNmae() {
        return categoryNmae;
    }

    public void setCategoryNmae(String categoryNmae) {
        this.categoryNmae = categoryNmae;
    }
}
