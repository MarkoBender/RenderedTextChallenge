import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;



public class MyParser {

	//Returns the cheapest pizza from an array
	private static PizzaModel getCheapest(ArrayList<PizzaModel> pizzaGroup){
		PizzaModel cheapestPizza = pizzaGroup.get(0);
		for(PizzaModel p : pizzaGroup){
			if(p.getPrice()<= cheapestPizza.getPrice())
				cheapestPizza = p;
		}
		return cheapestPizza;
	}
	
	public static void main(String[] args) throws IOException {
			
		
			// Read all pizzas
			byte[] jsonData = Files.readAllBytes(Paths.get("pizzas.json"));
			ObjectMapper objectMapper = new ObjectMapper();
			MenuModel menuModel = objectMapper.readValue(jsonData, MenuModel.class);

			ArrayList<PizzaModel> allPizzas = new ArrayList<PizzaModel>();
			// Process each one and create PizzaModel object
			for(Map<String, Object> pizza : menuModel.getPizzas()){
				if(!pizza.containsKey("nil")){
					PizzaModel tempPizza = new PizzaModel();
					tempPizza.setPrice((Integer)pizza.get("price"));
					
					for (String key : pizza.keySet()){
						if(!key.equals("price")){
							tempPizza.setName(key);
							LinkedHashMap contenet = (LinkedHashMap) pizza.get(key);
							List<String> list = (List<String>)contenet.get("ingredients");
							tempPizza.setIngredients(list);
						}
					}	
					allPizzas.add(tempPizza);
				}
			}
			
			//group1, group2, group3, group4
			ArrayList<ArrayList<PizzaModel>> groupLists  = new ArrayList<ArrayList<PizzaModel>>();
			groupLists.add( new ArrayList<PizzaModel>());
			groupLists.add( new ArrayList<PizzaModel>());
			groupLists.add( new ArrayList<PizzaModel>());
			groupLists.add( new ArrayList<PizzaModel>());
			

			// classify PizzaModel objects
			for(PizzaModel currPizza : allPizzas){
				if(currPizza.containsMeat())
					groupLists.get(0).add(currPizza);
				if(currPizza.multipleCheeseTypes())
					groupLists.get(1).add(currPizza);
				if(currPizza.contains("olives") & currPizza.containsMeat())
					groupLists.get(2).add(currPizza);
				if(currPizza.contains("mozzarella") & currPizza.contains("mushrooms"))
					groupLists.get(3).add(currPizza);
			}
			

			// calculate whats needed...
			ArrayList<Double> percentages = new ArrayList<Double>();
			ArrayList<PizzaModel> cheapestList = new ArrayList<PizzaModel>();
			for(int i = 0; i < 4; i++){
				percentages.add(100*(double)groupLists.get(i).size() / (double)allPizzas.size() );
				cheapestList.add(getCheapest(groupLists.get(i)));
			}


			// Create JSON reponse
			JsonArray jsonCheapest = new JsonArray();
			for(int i = 0; i < 4; i++){
				JsonArray ingredients = new JsonArray();
				for(String ing : cheapestList.get(i).getIngredients())
					ingredients.add(new JsonPrimitive(ing));
				JsonObject body = new JsonObject();
				body.add("ingredients", ingredients);
				JsonObject cheapest = new JsonObject();
				cheapest.add(cheapestList.get(i).getName(), body);
				cheapest.addProperty("price", cheapestList.get(i).getPrice());
				
				
				JsonObject groupInfo = new JsonObject();
				groupInfo.addProperty("percentage", percentages.get(i) + "%");
				groupInfo.add("cheapest", cheapest);
				JsonObject group = new JsonObject();			
				group.add("group_" + i, groupInfo);
				
				jsonCheapest.add(group);	
			}

			JsonObject personal_info = new JsonObject();
			personal_info.addProperty("full_name", "Marko Bender");
			personal_info.addProperty("email", "marko.bender@live.com");
			personal_info.addProperty("code_link", "https://github.com/MarkoBender/RenderedTextChallenge");
			
			JsonObject response = new JsonObject();
			response.add("personal_info", personal_info);
			response.add("answer", jsonCheapest);
				
			
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(response);
			System.out.println(prettyJson);
			
			//Used Postman to send http request
			
			
	}

}
