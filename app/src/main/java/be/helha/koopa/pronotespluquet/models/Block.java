package be.helha.koopa.pronotespluquet.models;

public class Block {
    private long blockId;
    private String blockName;

    public Block(long blockId, String blockName) {
        this.blockId = blockId;
        this.blockName = blockName;
    }

    public long getBlockId() { return blockId; }
    public String getBlockName() { return blockName; }

    public void setBlockId(long blockId) { this.blockId = blockId; }
    public void setBlockName(String blockName) { this.blockName = blockName; }
}