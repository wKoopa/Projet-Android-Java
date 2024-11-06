package be.helha.koopa.pronotespluquet.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import be.helha.koopa.pronotespluquet.R;
import be.helha.koopa.pronotespluquet.models.SchoolClass;

public class ClassFragment extends androidx.fragment.app.Fragment{

    private static final String ARG_CLASS_NAME = "className";
    private String className;
    protected SchoolClass mClass;
    private TextView mClassTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            className = getArguments().getString(ARG_CLASS_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_fragment, container, false);

        // Set the class name in the TextView
        TextView classNameTextView = view.findViewById(R.id.classNameTextView);
        classNameTextView.setText(className);

        // Handle the "Évaluation" button click
        Button evaluationButton = view.findViewById(R.id.evaluationButton);
        evaluationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action when the "Évaluation" button is clicked
            }
        });

        return view;
    }
    public static ClassFragment newInstance(String className) {
        ClassFragment fragment = new ClassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CLASS_NAME, className);
        fragment.setArguments(args);
        return fragment;
    }
    public void createNewClassFragment(String className) {
        ClassFragment classFragment = ClassFragment.newInstance(className);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.linearLayout, classFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
