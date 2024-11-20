package com.example.pokeo;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Helper {

    public static List<String> capitalize(String[] pokes) {
        List<String> finalList = new ArrayList<>(); //Initialize a new list that is empty.
        for (String word : pokes) {
            if (word != null && !word.isEmpty()) { //If the word isn't empty, replace the first word with a capital letter.
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
                finalList.add(capitalizedWord); //Add it to the final list.
            } else {
                finalList.add(word); //If empty, add the word itself.
            }
        }
        return finalList;
    }

    public static void displayer(List<String> pokes) {
        StringBuilder hee = new StringBuilder("Your choices are ");
        for (String poke : pokes) {
            hee.append(poke).append(", ");
        }
        hee = new StringBuilder(hee.substring(0, hee.length() - 2) + ".");
        System.out.println(hee);
    }

    public static String fetchApiData(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Open an HTTP connection
        conn.setRequestMethod("GET"); // Set the request method to GET

        // Read the response from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();     // Return the response as a string
    }
}
