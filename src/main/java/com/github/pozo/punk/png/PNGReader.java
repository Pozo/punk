package com.github.pozo.punk.png;

import com.github.pozo.punk.FileReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by pozo on 2016.07.16..
 */
public class PNGReader {
    private final FileReader fileReader;

    public PNGReader(InputStream inputStream) {
        this.fileReader = new FileReader(inputStream);
    }

    public PNGChunkList readChunks() throws IOException {
        final byte[] content = fileReader.readFully();
        final PNGChunkList pngChunkList = new PNGChunkList();

        int chunkStartIndex = PNGChunkList.PNG_FILE_DESCRIPTOR.length; // skip file signature
        final int byteNumber = 4;

        int index = chunkStartIndex;
        int startIndex = index;
        int endIndex = index;

        while (endIndex + byteNumber < content.length) {
            startIndex = index = endIndex;
            endIndex += byteNumber;

            // chunk length
            byte[] rawChunkLength = new byte[byteNumber];
            for (; index < endIndex; index++) {
                rawChunkLength[index + byteNumber - endIndex] = content[index];
            }
            ByteBuffer wrapped = ByteBuffer.wrap(rawChunkLength); // big-endian by default
            int chunkLength = wrapped.getInt();
            // /chunk length

            index = endIndex;
            endIndex += byteNumber;

            // type
            byte[] rawType = new byte[byteNumber];
            for (; index < endIndex; index++) {
                rawType[index + byteNumber - endIndex] = content[index];
            }
            String type = new String(rawType);
            // /type

            index = endIndex;
            endIndex += chunkLength;

            // data
            byte[] data = new byte[chunkLength];
            for (; index < endIndex; index++) {
                data[index + chunkLength - endIndex] = content[index];

            }
            // /data

            index = endIndex;
            endIndex += byteNumber;

            // crc
            byte[] rawCrc = new byte[byteNumber];
            for (; index < endIndex; index++) {
                rawCrc[index + byteNumber - endIndex] = content[index];
            }
            ByteBuffer wrappedCrc = ByteBuffer.wrap(rawCrc); // big-endian by default
            int crc = wrappedCrc.getInt();
            // /crc

            PNGChunkBuilder pngChunkBuilder = new PNGChunkBuilder();
            pngChunkBuilder.setRawSize(rawChunkLength)
                    .setSize(chunkLength)
                    .setRawType(rawType)
                    .setType(type)
                    .setRawCRC(rawCrc)
                    .setCRC(crc)
                    .setData(data);

            PNGChunk pngChunk = pngChunkBuilder.createPNGChunk();
            PNGChunkHolder pngChunkHolder = new PNGChunkHolder(pngChunk, startIndex, endIndex);
            pngChunkList.add(pngChunkHolder);
        }

        return pngChunkList;
    }
}
