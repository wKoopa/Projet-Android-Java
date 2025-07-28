package be.helha.koopa.pronotespluquet.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import be.helha.koopa.pronotespluquet.R;
import be.helha.koopa.pronotespluquet.models.Course;
import be.helha.koopa.pronotespluquet.models.CourseDatabaseHelper;

public class CourseFragment extends Fragment {

    private CourseDatabaseHelper dbHelper;
    private EditText editTextCourseName, editTextBlock;
    private Button buttonAddCourse;
    private TextView textViewCourses;

    public CourseFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        dbHelper = new CourseDatabaseHelper(requireContext());

        editTextCourseName = view.findViewById(R.id.editTextCourseName);
        editTextBlock = view.findViewById(R.id.editTextBlock);
        buttonAddCourse = view.findViewById(R.id.buttonAddCourse);
        textViewCourses = view.findViewById(R.id.textViewCourses);

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

        displayCourses();

        return view;
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
