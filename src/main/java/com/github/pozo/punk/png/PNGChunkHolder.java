package com.github.pozo.punk.png;

/**
 * Created by pozo on 2016.07.16..
 */
public class PNGChunkHolder {
    private int beginIndex;
    private int endIndex;

    private final PNGChunk pngChunk;

    public PNGChunkHolder(PNGChunk pngChunk) {
        this.pngChunk = pngChunk;
    }

    public PNGChunkHolder(PNGChunk pngChunk, int beginIndex, int endIndex) {
        this(pngChunk);
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public PNGChunk getPngChunk() {
        return pngChunk;
    }

    @Override
    public String toString() {
        return "PNGChunkHolder{" +
                "beginIndex=" + beginIndex +
                ", endIndex=" + endIndex +
                ", pngChunk=" + pngChunk +
                '}';
    }
}
