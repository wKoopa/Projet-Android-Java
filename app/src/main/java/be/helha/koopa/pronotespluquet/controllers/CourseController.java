package be.helha.koopa.pronotespluquet.controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.List;

import be.helha.koopa.pronotespluquet.R;
import be.helha.koopa.pronotespluquet.models.Course;

public class CourseController extends AppCompatActivity {
    // private EditText editTextCourseName;
    // private Button buttonAddCourse;
    // private TextView textViewCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Assure-toi d’avoir ce fichier XML dans res/layout

        // editTextCourseName = findViewById(R.id.editTextCourseName);
        // buttonAddCourse = findViewById(R.id.buttonAddCourse);
        // textViewCourses = findViewById(R.id.textViewCourses);

        // buttonAddCourse.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         String name = editTextCourseName.getText().toString().trim();
        //         if (!name.isEmpty()) {
        //             dbHelper.addCourse(name, 0); // 0 ou un blockId fictif, car on ne gère pas le bloc ici
        //             displayCourses();
        //             editTextCourseName.setText("");
        //         }
        //     }
        // });

        // Display the courses list on start
        displayCourses();
    }

    private void displayCourses() {
        // Correction de l’appel à getAllCourses()
        List<Course> courses = null; // 0 = blockId fictif
        StringBuilder builder = new StringBuilder();
        for (Course c : courses) {
            builder.append(c.getCourseName())
                    .append(" (BlockId: ")
                    .append(c.getBlockId())
                    .append(")\n");
        }
        // textViewCourses.setText(builder.toString());
    }
}