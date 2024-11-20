package com.example.pokeo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokeo.databinding.FragmentSecondBinding;

import java.util.Random;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Navigate back to the first fragment
        binding.buttonSecond.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );

        // Generate button logic
        binding.buttonGenerate.setOnClickListener(v -> {
            if (binding.radioMonotype.isChecked()) {
                runMonotype();
            } else if (binding.radioLetters.isChecked()) {
                runLetterer();
            }
        });
    }

    private void runMonotype() {
       String randomMonotype = Games.monotyper();

        // Update UI
        binding.textviewMessage.setText("Your random monotype is:");
        binding.textviewLarge.setText(randomMonotype);
        binding.textviewNote.setText("(You can only use Pokemon of that type)");
    }

    private void runLetterer() {
        char randomLetter = Games.letterer();
        // Update UI
        binding.textviewMessage.setText("Your random letter is:");
        binding.textviewLarge.setText(String.valueOf(randomLetter));
        binding.textviewNote.setText("(You can only use Pokemon whose names start with this letter)");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
