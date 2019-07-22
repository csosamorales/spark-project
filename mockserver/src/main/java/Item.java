public class Item {

    int id;
    String title;
    String category_id;
    float price;
    String currency_id;
    int quantity;
    String condition;
    String[] picture;

    public Item(int id) {
        this.id = id;
        title = "Titulo de item";
        category_id = "MLA" + id;
        price = 100;
        currency_id = "ARS";
        quantity = 10;
        condition = "New";
        picture = new String[]{"img1.jpg", "img2.jpg"};
    }
}
