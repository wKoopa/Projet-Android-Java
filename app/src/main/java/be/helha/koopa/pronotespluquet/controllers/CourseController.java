package be.helha.koopa.pronotespluquet.controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.List;

import be.helha.koopa.pronotespluquet.R;
import be.helha.koopa.pronotespluquet.models.CourseDatabaseHelper;
import be.helha.koopa.pronotespluquet.models.Course;

public class CourseController extends AppCompatActivity {
    private CourseDatabaseHelper dbHelper;
    private EditText editTextCourseName, editTextBlock;
    private Button buttonAddCourse;
    private TextView textViewCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Assure-toi d’avoir ce fichier XML dans res/layout

        dbHelper = new CourseDatabaseHelper(this);

        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextBlock = findViewById(R.id.editTextBlock);
        buttonAddCourse = findViewById(R.id.buttonAddCourse);
        textViewCourses = findViewById(R.id.textViewCourses);

        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextCourseName.getText().toString().trim();
                String block = editTextBlock.getText().toString().trim();

                if (!name.isEmpty() && !block.isEmpty()) {
                    dbHelper.addCourse(name, block);
                    displayCourses();
                    editTextCourseName.setText("");
                    editTextBlock.setText("");
                }
            }
        });

        // Display the courses list on start
        displayCourses();
    }

    private void displayCourses() {
        List<Course> courses = dbHelper.getAllCourses();
        StringBuilder builder = new StringBuilder();
        for (Course c : courses) {
            builder.append(c.getCourseName())
                    .append(" (")
                    .append(c.getBlock())
                    .append(")\n");
        }
        textViewCourses.setText(builder.toString());
    }
}