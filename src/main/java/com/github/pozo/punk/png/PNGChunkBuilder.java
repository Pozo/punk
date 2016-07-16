package com.github.pozo.punk.png;

public class PNGChunkBuilder {
    private byte[] rawSize;
    private int size;
    private byte[] rawType;
    private String type;
    private byte[] rawCRC;
    private int crc;
    private byte[] data;

    public PNGChunkBuilder setRawSize(byte[] rawSize) {
        this.rawSize = rawSize;
        return this;
    }

    public PNGChunkBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public PNGChunkBuilder setRawType(byte[] rawType) {
        this.rawType = rawType;
        return this;
    }

    public PNGChunkBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public PNGChunkBuilder setRawCRC(byte[] rawCRC) {
        this.rawCRC = rawCRC;
        return this;
    }

    public PNGChunkBuilder setCRC(int crc) {
        this.crc = crc;
        return this;
    }

    public PNGChunkBuilder setData(byte[] data) {
        this.data = data;
        return this;
    }

    public PNGChunk createPNGChunk() {
        return new PNGChunk(rawSize, size, rawType, type, rawCRC, crc, data);
    }
}