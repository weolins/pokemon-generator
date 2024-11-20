package com.example.pokeo;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.Set;

public class PokemonHelper {
    public static List<Pokemon> fetchPokemonFromGroup(String groupUrl) throws Exception {
        // Fetch group data using the URL
        String groupResponse = Helper.fetchApiData(groupUrl);
        JSONObject groupData = new JSONObject(groupResponse);
        JSONArray pokemonSpecies = groupData.getJSONArray("pokemon_species");

        String eggGroupName = "meow";
        if (groupUrl.contains("egg-group")) {
            eggGroupName = groupData.getString("name");
        }

        // Add all Pokémon URLs to a list
        List<String> pokemonUrls = new ArrayList<>();
        for (int i = 0; i < pokemonSpecies.length(); i++) {
            pokemonUrls.add(pokemonSpecies.getJSONObject(i).getString("url"));
        }

        Set<String> usedUrls = new HashSet<>();

        // Randomly select Pokémon from the list
        Random random = new Random();
        List<Pokemon> pokemonList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String pokemonUrl;
            String finalPokemonName = null;
            String finalPokemonName1 = finalPokemonName;

            do {
                pokemonUrl = pokemonUrls.get(random.nextInt(pokemonUrls.size()));
            } while (usedUrls.contains(pokemonUrl));

            usedUrls.add(pokemonUrl);

            // Fetch additional information for the selected Pokémon
            Pokemon pokemon = fetchPokemonData(pokemonUrl);
            if (groupUrl.contains("egg-group")) {
                pokemon.setEgg(eggGroupName);
            }
            pokemonList.add(pokemon);
        }

        return pokemonList;
    }

    private static String getPokemonNameFromUrl(String pokemonUrl) throws Exception {
        String pokemonInfoResponse = Helper.fetchApiData(pokemonUrl);
        JSONObject pokemonInfo = new JSONObject(pokemonInfoResponse);
        JSONObject firstVariety = pokemonInfo.getJSONArray("varieties").getJSONObject(0);
        JSONObject pokemoni = firstVariety.getJSONObject("pokemon");
        return pokemoni.getString("name");
    }

    private static Pokemon fetchPokemonData(String pokemonUrl) throws Exception {
        String pokemonInfoResponse = Helper.fetchApiData(pokemonUrl);
        JSONObject pokemonInfo = new JSONObject(pokemonInfoResponse);
        String pokemonColor = pokemonInfo.getJSONObject("color").getString("name");
        JSONObject firstVariety = pokemonInfo.getJSONArray("varieties").getJSONObject(0);
        JSONObject pokemoni = firstVariety.getJSONObject("pokemon");
        String finalUrl = pokemoni.getString("url");

        String pokemonDataResponse = Helper.fetchApiData(finalUrl);
        JSONObject pokemonData = new JSONObject(pokemonDataResponse);

        String pokemonName = pokemonData.getString("name");
        int pokedexNumber = pokemonData.getInt("id");
        String image = pokemonData.getJSONObject("sprites").getString("front_default");

        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonName);
        pokemon.setPokedexNumber(pokedexNumber);
        pokemon.setImage(image);
        pokemon.setColor(pokemonColor);

        return pokemon;
    }
}
