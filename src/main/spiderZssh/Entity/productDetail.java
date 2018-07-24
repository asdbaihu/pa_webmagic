package spiderZssh.Entity;

public class productDetail {
    private String id;
    private String productId;
    private String parentId;
    private String productName;
    private String product_cate;//总分类下的商品分类

    public productDetail(String productId, String parentId, String productName, String product_cate) {
        this.productId = productId;
        this.parentId = parentId;
        this.productName = productName;
        this.product_cate = product_cate;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProduct_cate() {
        return product_cate;
    }

    public void setProduct_cate(String product_cate) {
        this.product_cate = product_cate;
    }
}
