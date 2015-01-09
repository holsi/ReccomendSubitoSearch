package subito;

public class Item implements Comparable<Item>{

	private String title;
	private String description;
	private String image;
	private Double price;
	private String link;
	private double score;
	
	
	public Item(String title, String description, String image, Double price, String link) {
		super();
		this.title = title;
		this.description = description;
		this.image = image;
		this.price = price;
		this.link = link;
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
	
	

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	


	@Override
	public int compareTo(Item o) {
		return (int) (o.getScore()-this.score);
	}

	private double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
		
	}

	@Override
	public String toString() {
		return "Item [title=" + title + ", description=" + description
				+ ", image=" + image + ", price=" + price + ", link=" + link
				+ ", score=" + score + "]";
	}

	
	
	
	
}