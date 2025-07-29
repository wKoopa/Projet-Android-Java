package be.helha.koopa.pronotespluquet.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;

import be.helha.koopa.pronotespluquet.R;
import be.helha.koopa.pronotespluquet.models.AppDatabaseHelper;
import be.helha.koopa.pronotespluquet.models.Block;
import be.helha.koopa.pronotespluquet.models.Student;

public class StudentFragment extends Fragment {
    private AppDatabaseHelper dbHelper;
    private EditText editTextMatricule, editTextLastName, editTextFirstName;
    private Spinner spinnerBlock;
    private LinearLayout layoutStudentList;
    private ArrayAdapter<String> blockAdapter;
    private List<Block> blockList;

    public StudentFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(be.helha.koopa.pronotespluquet.R.layout.fragment_student, container, false);
        dbHelper = new AppDatabaseHelper(requireContext());

        editTextMatricule = view.findViewById(R.id.editTextMatricule);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        spinnerBlock = view.findViewById(R.id.spinnerBlock);
        layoutStudentList = view.findViewById(R.id.layoutStudentList);
        Button buttonAddStudent = view.findViewById(R.id.buttonAddStudent);

        // Remplir le spinner avec les blocs existants
        blockList = dbHelper.getAllBlocks();
        blockAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
        blockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (Block b : blockList) {
            blockAdapter.add(b.getBlockName());
        }
        spinnerBlock.setAdapter(blockAdapter);

        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matriculeStr = editTextMatricule.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                String firstName = editTextFirstName.getText().toString().trim();
                int blockPosition = spinnerBlock.getSelectedItemPosition();
                if (matriculeStr.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || blockPosition == -1) {
                    Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                long matricule;
                try {
                    matricule = Long.parseLong(matriculeStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Matricule invalide", Toast.LENGTH_SHORT).show();
                    return;
                }
                long blockId = blockList.get(blockPosition).getBlockId();
                Student student = new Student(matricule, lastName, firstName, blockId);
                dbHelper.addStudent(student);
                editTextMatricule.setText("");
                editTextLastName.setText("");
                editTextFirstName.setText("");
                Toast.makeText(requireContext(), "Élève ajouté", Toast.LENGTH_SHORT).show();
                updateStudentList();
            }
        });

        updateStudentList();
        return view;
    }

    private void updateStudentList() {
        layoutStudentList.removeAllViews();
        List<Student> students = dbHelper.getAllStudents();
        for (Student s : students) {
            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 8, 0, 8);

            TextView tv = new TextView(requireContext());
            tv.setText("[" + s.getStudentId() + "] " + s.getLastName() + " " + s.getFirstName());
            tv.setTextSize(18);
            tv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2));
            row.addView(tv);

            Spinner spinner = new Spinner(requireContext());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            int selectedIndex = 0;
            for (int i = 0; i < blockList.size(); i++) {
                Block b = blockList.get(i);
                adapter.add(b.getBlockName());
                if (b.getBlockId() == s.getBlockId()) selectedIndex = i;
            }
            spinner.setAdapter(adapter);
            spinner.setSelection(selectedIndex);
            spinner.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(spinner);

            spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                boolean first = true;
                @Override
                public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                    if (first) { first = false; return; }
                    long newBlockId = blockList.get(position).getBlockId();
                    if (newBlockId != s.getBlockId()) {
                        dbHelper.updateStudentBlock(s.getStudentId(), newBlockId);
                        updateStudentList();
                    }
                }
                @Override
                public void onNothingSelected(android.widget.AdapterView<?> parent) {}
            });

            // Ajout du bouton poubelle
            Button deleteButton = new Button(requireContext());
            deleteButton.setText("🗑");
            deleteButton.setTextSize(12);
            deleteButton.setPadding(8, 0, 8, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width = 80;
            params.height = 80;
            params.setMargins(8, 0, 0, 0);
            deleteButton.setLayoutParams(params);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.deleteStudent(s.getStudentId());
                    updateStudentList();
                }
            });
            row.addView(deleteButton);

            layoutStudentList.addView(row);
        }
    }
}
