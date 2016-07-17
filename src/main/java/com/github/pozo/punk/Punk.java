package com.github.pozo.punk;

import com.github.pozo.punk.png.*;

import java.io.*;
import java.util.Iterator;

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

    public void decode(InputStream targetPNG, OutputStream target) throws IOException {
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(target);

        final PNGReader png = new PNGReader(targetPNG);
        final PNGChunkList pngChunks = png.readChunks();

        boolean hasPunkChunk = false;

        final Iterator<PNGChunkHolder> iterator = pngChunks.iterator();

        while(iterator.hasNext() && !hasPunkChunk) {
            final PNGChunkHolder pngChunk = iterator.next();

            final String type = pngChunk.getPngChunk().getType();
            final byte[] data = pngChunk.getPngChunk().getData();

            if(CHUNK_NAME.equals(type)) {
                bufferedOutputStream.write(data);
                bufferedOutputStream.close();

                hasPunkChunk = true;
            }
        }

        if(!hasPunkChunk) {
            bufferedOutputStream.close();
            throw new IOException("puNk chunk is not found");
        }

    }
}
