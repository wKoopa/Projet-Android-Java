package be.helha.koopa.pronotespluquet.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import be.helha.koopa.pronotespluquet.R;
import be.helha.koopa.pronotespluquet.models.Course;
import be.helha.koopa.pronotespluquet.models.AppDatabaseHelper;

/**
 * Fragment that displays and manages the courses for a given block.
 */
public class CourseFragment extends Fragment {

    // Arguments keys for passing data to the fragment
    private static final String ARG_BLOCK_ID = "blockId";
    private static final String ARG_BLOCK_NAME = "blockName";

    // Variables for block info received from arguments
    private long blockId = -1;
    private String blockName = "";

    // UI components
    private AppDatabaseHelper dbHelper;
    private EditText editTextCourseName;
    private Button buttonAddCourse;
    private TextView textViewCourses;
    private TextView textViewTitle; // Optional: to display the block name

    /**
     * Empty constructor (required for fragments)
     */
    public CourseFragment() { }

    /**
     * Factory method to create a new instance of CourseFragment with block arguments.
     */
    public static CourseFragment newInstance(long blockId, String blockName) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_BLOCK_ID, blockId);
        args.putString(ARG_BLOCK_NAME, blockName);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is created. Retrieves blockId and blockName from arguments.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            blockId = getArguments().getLong(ARG_BLOCK_ID, -1);
            blockName = getArguments().getString(ARG_BLOCK_NAME, "");
        }
    }

    /**
     * Sets up the UI and logic for displaying and adding courses in the selected block.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        dbHelper = new AppDatabaseHelper(requireContext());

        // Utilisation du bouton retour défini dans le layout XML
        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Initialize UI components
        editTextCourseName = view.findViewById(R.id.editTextCourseName);
        buttonAddCourse = view.findViewById(R.id.buttonAddCourse);
        textViewCourses = view.findViewById(R.id.textViewCourses);

        // Optional: if you have a TextView for block title, display it
        textViewTitle = view.findViewById(R.id.textViewBlockTitle);
        if (textViewTitle != null && !blockName.isEmpty()) {
            textViewTitle.setText("Cours de " + blockName);
        }

        // Add course button: adds a new course to the current block
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = editTextCourseName.getText().toString().trim();
                if (!courseName.isEmpty() && blockId != -1) {
                    // Add the course in the selected block
                    dbHelper.addCourse(courseName, blockId);
                    displayCourses();
                    editTextCourseName.setText("");
                }
            }
        });

        // Show the courses for the current block
        displayCourses();

        return view;
    }

    /**
     * Fetches and displays all courses belonging to the selected block.
     */
    private void displayCourses() {
        List<Course> courses = dbHelper.getCoursesByBlockId(blockId);
        StringBuilder builder = new StringBuilder();
        for (Course c : courses) {
            builder.append(c.getCourseName()).append("\n");
        }
        textViewCourses.setText(builder.toString());
    }
}