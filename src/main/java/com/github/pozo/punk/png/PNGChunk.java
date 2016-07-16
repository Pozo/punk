package com.github.pozo.punk.png;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * Created by pozo on 2016.07.16..
 */
public class PNGChunk {
    private byte[] rawSize;
    private int size;

    private byte[] rawType;
    private final String type;

    private byte[] rawCRC;
    private int CRC;

    private final byte[] data;

    public PNGChunk(String type, byte[] data) {
        this.data = data;

        this.type = type;
        this.rawType = type.getBytes();

        calculateSize();
        calculateCRC();
    }

    PNGChunk(byte[] rawSize, int size, byte[] rawType, String type, byte[] rawCRC, int CRC, byte[] data) {
        this.rawSize = rawSize;
        this.size = size;
        this.rawType = rawType;
        this.type = type;
        this.rawCRC = rawCRC;
        this.CRC = CRC;
        this.data = data;
    }

    public void calculateSize() {
        this.size = data.length;
        this.rawSize = ByteBuffer.allocate(Integer.BYTES).putInt(this.size).array();
    }

    public void calculateCRC() {
        final CRC32 crc = new CRC32();

        crc.update(rawType);
        crc.update(data);

        this.CRC = (int) crc.getValue();
        this.rawCRC = ByteBuffer.allocate(Integer.BYTES).putInt(this.CRC).array();
    }

    public byte[] getRawSize() {
        return rawSize;
    }

    public int getSize() {
        return size;
    }

    public byte[] getRawType() {
        return rawType;
    }

    public String getType() {
        return type;
    }

    public byte[] getRawCRC() {
        return rawCRC;
    }

    public int getCRC() {
        return CRC;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "com.github.pozo.punk.png.PNGChunk{" +
                ", rawSize=" + Arrays.toString(rawSize) +
                ", size=" + size +
                ", rawType=" + Arrays.toString(rawType) +
                ", type='" + type + '\'' +
                ", rawCRC=" + Arrays.toString(rawCRC) +
                ", CRC=" + CRC +
                ", data=" + data +
                '}';
    }
}
