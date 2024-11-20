package com.example.pokeo;
import java.util.List;

public class Pokemon {
    private String name;
    private int pokedexNumber;
    private String image;
    private String egg = "meow";
    private String color;

    public String getName() {
        return name;
    }
    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public String getImage() {
        return image;
    }

    public String getEgg() {
        return egg;
    }
    public String getColor() {
        return color;
    }

    public void setName(String name) {
        String[] nameParts = name.split(" "); // Split into words in case the name has multiple parts (e.g., "Pikachu yellow")
        List<String> capitalizedParts = Helper.capitalize(nameParts); // Use the capitalize method from Helper
        this.name = String.join(" ", capitalizedParts); // Join the parts back into a single string
    }

    public void setPokedexNumber(int pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setEgg(String egg) {
        String[] eggParts = egg.split(" "); // Split into words in case the name has multiple parts (e.g., "Pikachu yellow")
        List<String> capitalizedParts = Helper.capitalize(eggParts); // Use the capitalize method from Helper
        this.egg = String.join(" ", capitalizedParts);
    }

    public void setColor(String color) {
        String[] colorParts = color.split(" "); // Split into words in case the name has multiple parts (e.g., "Pikachu yellow")
        List<String> capitalizedParts = Helper.capitalize(colorParts); // Use the capitalize method from Helper
        this.color = String.join(" ", capitalizedParts);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "Name='" + name + '\'' +
                ", PokedexNumber=" + pokedexNumber +
                ", Image='" + image + '\'' +
                '}';
    }
}
