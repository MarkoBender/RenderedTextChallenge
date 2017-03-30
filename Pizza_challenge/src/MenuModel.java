import java.util.List;
import java.util.Map;


public class MenuModel {

	private List<Map<String,Object>> pizzas;
	
	
	
	public MenuModel(List<Map<String, Object>> pizzas) {
		super();
		this.pizzas = pizzas;
	}



	public List<Map<String, Object>> getPizzas() {
		return pizzas;
	}



	public void setPizzas(List<Map<String, Object>> pizzas) {
		this.pizzas = pizzas;
	}



	public MenuModel(){}
	
}
