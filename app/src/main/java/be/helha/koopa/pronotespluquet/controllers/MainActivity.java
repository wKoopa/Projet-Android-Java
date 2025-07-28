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
import be.helha.koopa.pronotespluquet.views.CourseFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the CourseFragment to the activity
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new CourseFragment())
                .commit();
    }
}

