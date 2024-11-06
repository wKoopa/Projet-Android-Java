package be.helha.koopa.pronotespluquet.models;

public class Notes {

    private int mNote;
    private int mMax;
    private int mWeighting;

    public Notes(int mNote) {
        this.mMax = 20;
        this.mNote = mNote;
    }

    public int getMax() {
        return mMax;
    }

    public int getNote() {
        return mNote;
    }

    public int getWeighting() {
        return mWeighting;
    }

    public void setWeighting(int mWeighting) {
        this.mWeighting = mWeighting;
    }

    public void setNote(int mNote) {
        this.mNote = mNote;
    }

    public void setMax(int mMax) {
        this.mMax = mMax;
    }
}
