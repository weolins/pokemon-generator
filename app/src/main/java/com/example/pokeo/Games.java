package com.example.pokeo;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class Games {
    public static char letterer() {
        Random random = new Random();
        int num = random.nextInt(26) + 65; // ASCII range for A-Z is 65 to 90
        return (char) num;
    }

    public static String monotyper() {
        String[] types = {
                "Fire", "Water", "Grass", "Normal", "Electric", "Fighting",
                "Ground", "Rock", "Flying", "Psychic", "Dark", "Fairy",
                "Ghost", "Ice", "Bug", "Poison", "Dragon", "Steel"};
        Random random = new Random();
        int index = random.nextInt(types.length); // Random index between 0 and types.length-1
        return types[index];
    }

    public static List<Pokemon> egger() throws Exception {
        String apiUrl = "https://pokeapi.co/api/v2/egg-group/";
        String response = Helper.fetchApiData(apiUrl);
        JSONObject data = new JSONObject(response);
        JSONArray results = data.getJSONArray("results");

        // Store egg group names and URLs in lists
        List<String> eggs = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject group = results.getJSONObject(i);
            eggs.add(group.getString("name"));
            urls.add(group.getString("url"));
        }

        // Select a random egg group (excluding "ditto")
        Random random = new Random();
        int amt;
        String egg;
        do {
            amt = random.nextInt(eggs.size());
            egg = eggs.get(amt);
        } while (egg.equals("ditto"));
        System.out.println("Your egg group is: " + egg);

        // Fetch the Pokémon from the selected egg group
        return PokemonHelper.fetchPokemonFromGroup(urls.get(amt));
    }

    public static List<Pokemon> colorer() throws Exception {
        String apiUrl = "https://pokeapi.co/api/v2/pokemon-color/";
        String response = Helper.fetchApiData(apiUrl);

        // Parse JSON response to get color names and URLs
        JSONObject data = new JSONObject(response);
        JSONArray results = data.getJSONArray("results");

        // Store color names and URLs in lists
        List<String> colors = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject color = results.getJSONObject(i);
            colors.add(color.getString("name"));
            urls.add(color.getString("url"));
        }

        // Select a random color
        Random random = new Random();
        int amt = random.nextInt(colors.size());
        String color = colors.get(amt);
        System.out.println("Your color is: " + color);

        // Fetch the Pokémon from the selected color group
        return PokemonHelper.fetchPokemonFromGroup(urls.get(amt));
    }

    public static List<Pokemon> randome() throws Exception {
        List<Pokemon> pokemonList = new ArrayList<>();
        Random rand = new Random();

        Set<Integer> usedIds = new HashSet<>();

        for (int i = 0; i < 6; i++) {
            int randomNumber;

            do {
                randomNumber = rand.nextInt(1015) + 1;
            } while (usedIds.contains(randomNumber));  // Regenerate if ID already used

            usedIds.add(randomNumber);
            String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + randomNumber;

            // Fetch data using the fetchApiData function
            String response = Helper.fetchApiData(apiUrl);

            // Parse the response (assuming the response is in JSON format)
            JSONObject jsonObject = new JSONObject(response);

            // Extract name, pokedex number, and image URL from the response
            String name = jsonObject.getString("name");
            int pokedexNumber = jsonObject.getInt("id");
            String image = jsonObject.getJSONObject("sprites").getString("front_default");

            // Create a new Pokemon object
            Pokemon pokemon = new Pokemon();
            pokemon.setName(name);
            pokemon.setPokedexNumber(pokedexNumber);
            pokemon.setImage(image);
            pokemonList.add(pokemon);
        }

        return pokemonList;
    }

}

