package spiderZssh.Entity;

public class productlistEntity {

        private String id;
        private String productId;
        private String webpage;

    public productlistEntity(String productId, String webpage) {
        this.productId = productId;
        this.webpage = webpage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }
}
