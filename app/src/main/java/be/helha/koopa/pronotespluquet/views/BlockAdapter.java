package be.helha.koopa.pronotespluquet.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import be.helha.koopa.pronotespluquet.models.Block;
import be.helha.koopa.pronotespluquet.R;
import java.util.List;

/**
 * Adapter for displaying blocks in a RecyclerView.
 * Handles click on each block.
 */
public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder> {

    public interface OnBlockClickListener {
        void onBlockClick(Block block);
    }

    private List<Block> blockList;
    private OnBlockClickListener listener;

    /**
     * Adapter constructor.
     * @param blockList The list of blocks to display.
     * @param listener Callback for when a block is clicked.
     */
    public BlockAdapter(List<Block> blockList, OnBlockClickListener listener) {
        this.blockList = blockList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_block, parent, false);
        return new BlockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockViewHolder holder, int position) {
        Block block = blockList.get(position);
        holder.textViewBlockItem.setText(block.getBlockName());
        holder.textViewBlockItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBlockClick(block);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return blockList.size();
    }

    /**
     * Updates the list of blocks displayed.
     */
    public void setBlockList(List<Block> newBlocks) {
        this.blockList = newBlocks;
        notifyDataSetChanged();
    }

    static class BlockViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBlockItem;

        public BlockViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBlockItem = itemView.findViewById(R.id.textViewBlockItem);
        }
    }
}