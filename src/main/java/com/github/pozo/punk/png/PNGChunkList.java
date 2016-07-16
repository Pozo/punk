package com.github.pozo.punk.png;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by pozo on 2016.07.16..
 */
public class PNGChunkList implements Iterable<PNGChunkHolder> {
    public static final byte[] PNG_FILE_DESCRIPTOR = new byte[]{ (byte)137, 80, 78, 71, 13, 10, 26, 10};
    private static final int FIRST_INDEX = 0;

    private final ArrayList<PNGChunkHolder> pngChunks = new ArrayList<PNGChunkHolder>();

    public void add(PNGChunkHolder pngChunkHolder) {
        pngChunks.add(pngChunkHolder);
    }

    public void insert(PNGChunk pngChunk) {
        this.insert(FIRST_INDEX, pngChunk);
    }

    public void insert(int index, PNGChunk pngChunk) {
        final PNGChunkHolder pngChunkHolder = new PNGChunkHolder(pngChunk);
        // last
        final int lastIndex = pngChunks.size() - 1;

        if (index > FIRST_INDEX && index == lastIndex) {
            // has prev
            final int previousIndex = index - 1;
            final PNGChunkHolder previous = pngChunks.get(previousIndex);

            setChunkIndicesFrom(index, previous.getEndIndex(), pngChunkHolder);

            pngChunks.add(index, pngChunkHolder);

        } else if (index > FIRST_INDEX && index < lastIndex) {
            // has prev and next
            final int previousIndex = index - 1;
            final PNGChunkHolder previous = pngChunks.get(previousIndex);

            setChunkIndicesFrom(index, previous.getEndIndex(), pngChunkHolder);

            pngChunks.add(index, pngChunkHolder);

        } else if (index == FIRST_INDEX && index < lastIndex) {
            // has next
            final int chunkStartIndex = PNG_FILE_DESCRIPTOR.length; // skip file signature
            setChunkIndicesFrom(index, chunkStartIndex, pngChunkHolder);

            pngChunks.add(index, pngChunkHolder);

        } else if (pngChunks.size() == 1) {

        } else {
            throw new IndexOutOfBoundsException("List size is " + pngChunks.size() + " and the index is " + index);
        }
    }

    private void setChunkIndicesFrom(int index, int previousEndIndex, PNGChunkHolder pngChunkHolder) {
        final int newChunkSize = pngChunkHolder.getPngChunk().getSize();

        pngChunkHolder.setBeginIndex(previousEndIndex);
        pngChunkHolder.setEndIndex(previousEndIndex + newChunkSize);

        for (int i = index; i < pngChunks.size(); i++) {
            final PNGChunkHolder chunkHolder = pngChunks.get(i);
            final int beginIndexWithOffset = chunkHolder.getBeginIndex() + newChunkSize;
            final int endIndexWithOffset = chunkHolder.getEndIndex() + newChunkSize;

            chunkHolder.setBeginIndex(beginIndexWithOffset);
            chunkHolder.setEndIndex(endIndexWithOffset);
        }
    }

    public void remove(int index) {
        pngChunks.remove(index);
    }

    public void clear() {
        pngChunks.clear();
    }

    public Iterator<PNGChunkHolder> iterator() {
        return pngChunks.iterator();
    }

    public void forEach(Consumer<? super PNGChunkHolder> consumer) {
        pngChunks.forEach(consumer);
    }

    public Spliterator<PNGChunkHolder> spliterator() {
        return pngChunks.spliterator();
    }

    public int size() {
        return pngChunks.size();
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byteArrayOutputStream.write(PNG_FILE_DESCRIPTOR);

        for (PNGChunkHolder chunk : pngChunks) {
            final PNGChunk pngChunk = chunk.getPngChunk();

            byteArrayOutputStream.write(pngChunk.getRawSize());
            byteArrayOutputStream.write(pngChunk.getRawType());
            byteArrayOutputStream.write(pngChunk.getData());
            byteArrayOutputStream.write(pngChunk.getRawCRC());
            byteArrayOutputStream.flush();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
