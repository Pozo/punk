package com.github.pozo.punk;

import com.github.pozo.punk.png.PNGChunk;
import com.github.pozo.punk.png.PNGChunkList;
import com.github.pozo.punk.png.PNGReader;
import com.github.pozo.punk.png.PNGWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
