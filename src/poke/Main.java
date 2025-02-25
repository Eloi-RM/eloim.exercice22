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

    public static JSONArray getPokemonJson() throws Exception {
        try {
            FileReader reader = new FileReader("src\\pokedex.json");
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray jsonArray = (JSONArray) jsonObject.get("pokemon");

            reader.close();
            
            return jsonArray;
 
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new JSONArray();
        }  
    }

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

    public static JSONObject getPokemon(String pokemonName) throws Exception {
        JSONArray jsonArray = getPokemonJson();

        JSONObject desiredPoke = new JSONObject();

        for (Object obj : jsonArray) {
            JSONObject pokemon = (JSONObject) obj;

            if (pokemon.containsKey("name") && pokemon.get("name").equals(pokemonName)) {
                desiredPoke = pokemon;
            }
        }

        return desiredPoke;
    }
    
    public static void pokemonEvolutiontree(String pokemonName) throws Exception {
        String EvolutionTree = pokemonName;

        JSONObject desiredPoke = getPokemon(pokemonName);

        String pokeName = (String) desiredPoke.get("name");

        if (desiredPoke.containsKey("prev_evolution")) {
            
            JSONArray prevEvoCateg = (JSONArray) desiredPoke.get("prev_evolution");
            JSONObject prevEvo = (JSONObject) prevEvoCateg.get(0);
            String prevEvoName = (String) prevEvo.get("name");
            
            EvolutionTree = prevEvoName  + " -> " + pokeName;

            JSONObject prePrevPoke = getPokemon(prevEvoName);
            
            if (prePrevPoke.containsKey("prev_evolution")) {
                JSONArray prePrevEvoCateg = (JSONArray) prePrevPoke.get("prev_evolution");
                JSONObject prePrevEvo = (JSONObject) prePrevEvoCateg.get(0);
                String prePrevEvoName = (String) prePrevEvo.get("name");
                
                EvolutionTree = prePrevEvoName + " -> " + prevEvoName  + " -> " + pokeName;
            }
        }

        if (desiredPoke.containsKey("next_evolution")) {
            
            JSONArray nextEvoCateg = (JSONArray) desiredPoke.get("next_evolution");
            JSONObject nextEvo = (JSONObject) nextEvoCateg.get(0);
            String nextEvoName = (String) nextEvo.get("name");
            
            EvolutionTree += " -> " + nextEvoName;

            JSONObject nextNextPoke = getPokemon(nextEvoName);
            
            if (nextNextPoke.containsKey("prev_evolution")) {
                JSONArray nextNextEvoCate = (JSONArray) nextNextPoke.get("prev_evolution");
                JSONObject nextNextEvo = (JSONObject) nextNextEvoCate.get(0);
                String nextNextEvoName = (String) nextNextEvo.get("name");
                
                EvolutionTree =  pokeName + " -> " + nextEvoName  + " -> " +  nextNextEvoName;
            }
        }

        System.out.println(EvolutionTree);
    }
    public static void main(String[] args) throws Exception {

        JSONArray jsonArray = getPokemonJson();

        // numberOfPokemon(jsonArray);
        // pokemonWithMin10Kg(jsonArray);
        // pokemonOrderedByWeight(jsonArray);
        pokemonEvolutiontree("Ivysaur");
             
    }
}