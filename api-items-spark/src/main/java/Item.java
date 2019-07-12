import java.util.ArrayList;
import java.util.List;

public class Item {

    private String id;
    private String site_id;
    private String title;
    private Float price;
    private Currency currency_id;
    private String listing_type_id;
    private String stop_time;
    private String thumbnail;
    private ArrayList<String> Tags;

    public Item(String id, String site_id, String title, Float price, Currency currency_id, String listing_type_id, String stop_time, String thumbnail, ArrayList<String> tags) {
        this.id = id;
        this.site_id = site_id;
        this.title = title;
        this.price = price;
        this.currency_id = currency_id;
        this.listing_type_id = listing_type_id;
        this.stop_time = stop_time;
        this.thumbnail = thumbnail;
        Tags = tags;
    }

    public Item() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Currency getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(Currency currency_id) {
        this.currency_id = currency_id;
    }

    public String getListing_type_id() {
        return listing_type_id;
    }

    public void setListing_type_id(String listing_type_id) {
        this.listing_type_id = listing_type_id;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public void setTags(ArrayList<String> tags) {
        Tags = tags;
    }
}
