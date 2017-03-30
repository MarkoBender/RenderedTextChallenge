import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PizzaModel {

	private List<String> ingredients;
	private String name;
	private int price;
	
	
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public PizzaModel(){}
	
	public PizzaModel(List<String> ingredients, String name, int price) {
		super();
		this.ingredients = ingredients;
		this.name = name;
		this.price = price;
	}
	
	
	public boolean containsMeat(){

		List<String> meatTypes = Arrays.asList("ham", "cocktail_sausages", "salami", "shrimps", "mussels", 
				"tuna","calamari","crab_meat","anchovies","minced_meat","sausage","kebab","minced_beef");
		
		for(String ingredient : ingredients){
			for(String meat : meatTypes){
				if(ingredient.equals(meat))
					return true;
			}
		}
		return false;
	}
	
	
	public boolean multipleCheeseTypes(){

		int numberOfTypes = 0;
		
		for(String ingredient : ingredients){
			if(ingredient.contains("cheese"))
				numberOfTypes++;
		}
		return (numberOfTypes>1);
	}
	
	
	public boolean contains(String wanted){
		
		for(String ingredient : ingredients){
			if(ingredient.contains(wanted))
				return true;
		}
		return false;
	}
	


	
	
	
	
}
