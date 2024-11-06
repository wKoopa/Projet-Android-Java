package be.helha.koopa.pronotespluquet.controllers;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import be.helha.koopa.pronotespluquet.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton createButton = findViewById(R.id.imageButton4);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClassDialog(); // Ouvre le dialogue pour choisir le nom de la classe
            }
        });
    }

    // Méthode pour afficher le dialogue
    public void showClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nom de la classe");

        // Ajouter un champ de texte pour entrer le nom de la classe
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Bouton "Créer"
        builder.setPositiveButton("Créer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String className = input.getText().toString();
                createNewClassFragment(className); // Crée un nouveau fragment avec le nom saisi
            }
        });

        // Bouton "Annuler"
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    // Méthode pour créer et afficher un nouveau fragment
    public void createNewClassFragment(String className) {
        ClassFragment classFragment = ClassFragment.newInstance(className);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linearLayout, classFragment)
                .addToBackStack(null)
                .commit();
    }

}
