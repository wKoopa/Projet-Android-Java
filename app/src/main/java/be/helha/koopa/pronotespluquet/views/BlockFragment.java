package be.helha.koopa.pronotespluquet.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import be.helha.koopa.pronotespluquet.R;
import be.helha.koopa.pronotespluquet.models.Block;
import be.helha.koopa.pronotespluquet.models.AppDatabaseHelper;
import be.helha.koopa.pronotespluquet.view.BlockAdapter;

import java.util.List;

/**
 * Fragment that displays the list of blocks using a RecyclerView.
 * Allows the user to add new blocks and click on a block to show its courses.
 */
public class BlockFragment extends Fragment {

    private AppDatabaseHelper dbHelper;
    private EditText editTextBlockName;
    private Button buttonAddBlock;
    private RecyclerView recyclerViewBlocks;
    private BlockAdapter adapter;

    public BlockFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_block, container, false);

        // Initialize database helper and UI widgets
        dbHelper = new AppDatabaseHelper(requireContext());
        editTextBlockName = view.findViewById(R.id.editTextBlockName);
        buttonAddBlock = view.findViewById(R.id.buttonAddBlock);
        recyclerViewBlocks = view.findViewById(R.id.recyclerViewBlocks);

        // Set up RecyclerView (vertical list)
        recyclerViewBlocks.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Adapter with click callback
        adapter = new BlockAdapter(dbHelper.getAllBlocks(), new BlockAdapter.OnBlockClickListener() {
            @Override
            public void onBlockClick(Block block) {
                // Navigation vers les cours du bloc sélectionné
                ((be.helha.koopa.pronotespluquet.controllers.MainActivity) requireActivity()).openCoursesForBlock(block.getBlockId(), block.getBlockName());
            }
        });
        recyclerViewBlocks.setAdapter(adapter);

        // Add block button logic
        buttonAddBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blockName = editTextBlockName.getText().toString().trim();
                if (!blockName.isEmpty()) {
                    dbHelper.addBlock(blockName);
                    updateBlockList();
                    editTextBlockName.setText("");
                }
            }
        });

        updateBlockList();

        return view;
    }

    /**
     * Updates the list of blocks displayed in the RecyclerView.
     */
    private void updateBlockList() {
        List<Block> blocks = dbHelper.getAllBlocks();
        adapter.setBlockList(blocks);
    }
}