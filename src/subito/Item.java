package subito;

public class Item {

	private String title;
	private String description;
	private String image;
	private Double price;
	
	
	public Item(String title, String description, String image, Double price) {
		super();
		this.title = title;
		this.description = description;
		this.image = image;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Item [title=" + title + ", description=" + description
				+ ", image=" + image + ", price=" + price + "]";
	}
	
	
	
}