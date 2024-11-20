package com.example.pokeo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.pokeo.databinding.FragmentFirstBinding;

import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    private RadioGroup radioGroup;
    private TextView textViewDesc;
    private List<Integer> imageViewIds = List.of(
            R.id.poke1, R.id.poke2, R.id.poke3, R.id.poke4, R.id.poke5, R.id.poke6
    );
    private List<Integer> textViewIds = List.of(
            R.id.poke1_name, R.id.poke2_name, R.id.poke3_name,
            R.id.poke4_name, R.id.poke5_name, R.id.poke6_name
    );

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup = view.findViewById(R.id.radio_group);
        textViewDesc = view.findViewById(R.id.textview_desc);
        Button generateButton = view.findViewById(R.id.button_generate);

        generateButton.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                textViewDesc.setText("Please select a generation method.");
                return;
            }

            RadioButton selectedButton = view.findViewById(selectedId);
            String selectedMethod = selectedButton.getText().toString();
            generatePokemon(selectedMethod);
        });

        binding.buttonOtherGames.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );
    }

    private void generatePokemon(String method) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Pokemon> pokemonList = null;

                try {
                    // Make the API call based on the selected method
                    if (method.equals("True Random")) {
                        textViewDesc.post(() -> textViewDesc.setText("Pokémon are being generated randomly."));
                        pokemonList = Games.randome();
                    } else if (method.equals("Colors")) {
                        pokemonList = Games.colorer();
                        if (pokemonList != null && !pokemonList.isEmpty()) {
                            String color = pokemonList.get(0).getColor();
                            String finalColor = "Your random color is: " + color;
                            textViewDesc.post(() -> textViewDesc.setText(finalColor));
                        }
                    } else {
                        pokemonList = Games.egger();
                        if (pokemonList != null && !pokemonList.isEmpty()) {
                            String eggGroup = pokemonList.get(0).getEgg();
                            String finalEggGroup = "Your random egg group is: " + eggGroup;
                            textViewDesc.post(() -> textViewDesc.setText(finalEggGroup));
                        }
                    }
                } catch (Exception e) {
                    String errorMessage = "Error generating Pokémon: " + e.getMessage();
                    textViewDesc.post(() -> textViewDesc.setText(errorMessage));
                }

                if (pokemonList != null) {
                    final List<Pokemon> finalPokemonList = pokemonList;
                    requireActivity().runOnUiThread(() -> updatePokemonUI(finalPokemonList));
                }
            }
        }).start();
    }

    private void updatePokemonUI(List<Pokemon> pokemonList) {
        for (int i = 0; i < pokemonList.size(); i++) {
            Pokemon pokemon = pokemonList.get(i);

            // Update Pokémon Image
            de.hdodenhof.circleimageview.CircleImageView imageView = requireView().findViewById(imageViewIds.get(i));
            Glide.with(this).load(pokemon.getImage()).into(imageView);

            // Update Pokémon Name
            TextView nameView = requireView().findViewById(textViewIds.get(i));
            nameView.setText("#" + pokemon.getPokedexNumber() + ": " + pokemon.getName());
        }
    }
}
