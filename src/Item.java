public enum Item {

    MARS(25, "Mars"),
    TWIX(35, "Twix"),
    BOUNTY(45, "Bounty");

    private int price;
    private String product;

    Item(int price, String product) {
        this.price = price;
        this.product = product;
    }

    public int getPrice(){
        return price;
    }

    public String getProduct(){
        return product;
    }
}
