package com.github.pozo.punk;

import com.github.pozo.punk.png.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

/**
 * Created by pozo on 2016.07.14..
 */
public class Punk {
    private static final String CHUNK_NAME = "puNk";

    public void encode(InputStream injectable, InputStream targetPNG, OutputStream target) throws IOException {
        final FileReader fileReader = new FileReader(injectable);
        final PNGReader png = new PNGReader(targetPNG);

        final PNGChunkList pngChunks = png.readChunks();
        final int beforeLastIndex = pngChunks.size() - 1;

        final byte[] fileContent = fileReader.readFully();
        final PNGChunk pngChunk = new PNGChunk(CHUNK_NAME, fileContent);

        pngChunks.insert(beforeLastIndex, pngChunk);

        byte[] bytes = pngChunks.toByteArray();

        PNGWriter pngWriter = new PNGWriter(target);
        pngWriter.writeFully(bytes);
    }
    public void decode(InputStream source, OutputStream target) throws IOException {

    }
}
