public class LineItem extends Product{
    int quantity;

    public LineItem(String productName, double unitPrice, int quantity)
    {
        super(productName,unitPrice);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
