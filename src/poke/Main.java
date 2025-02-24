package poke;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

    public static void numberOfPokemon(JSONArray jsonArray) {
        System.out.println(jsonArray.size());
    }

    public static void pokemonWithMin10Kg(JSONArray jsonArray) {
        for (Object obj : jsonArray) {
            JSONObject pokemon = (JSONObject) obj;

            String name = (String) pokemon.get("name");
            String weight = (String) pokemon.get("weight");

            double doubleWeight = StringToDouble(weight);

            if (doubleWeight > 10) {
                System.out.println(name + " : " + weight);
            }
        }
    }

    public static void pokemonOrderedByWeight(JSONArray jsonArray) {
        List<JSONObject> pokeList = new ArrayList<>();
        
        for (Object obj : jsonArray) {
            pokeList.add((JSONObject) obj);
        }
        
        Collections.sort(pokeList, new Comparator<JSONObject>() {
            public int compare(JSONObject a, JSONObject b) {
                // Convert from String to Double for correct comparison
                double weightA = StringToDouble((String) a.get("weight"));
                double weightB = StringToDouble((String) b.get("weight"));
                return Double.compare(weightA, weightB);
            }
        });

        System.out.println("Sorted by weight:");
        for (JSONObject pokemon : pokeList) {
            System.out.println(pokemon.get("name") + " : " + pokemon.get("weight"));
        }
    }

    public static double StringToDouble(String myString) {
        String[] splitString = myString.split(" ");

        double doubleString = Double.parseDouble(splitString[0]);
        
        return doubleString;
    }

    public static void main(String[] args) throws Exception {
        try {
            // Read file
            FileReader reader = new FileReader("src\\pokedex.json");
            
            // Parse JSON file
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray jsonArray = (JSONArray) jsonObject.get("pokemon");

            // Process the JSON data
            // ...
            numberOfPokemon(jsonArray);
            pokemonWithMin10Kg(jsonArray);
            pokemonOrderedByWeight(jsonArray);
            
            // Close reader
            reader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}