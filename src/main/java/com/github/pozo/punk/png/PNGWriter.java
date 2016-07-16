package com.github.pozo.punk.png;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by pozo on 2016.07.16..
 */
public class PNGWriter {

    private OutputStream outputStream;

    public PNGWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeFully(byte[] bytes) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        byte[] buffer = new byte[8192];

        int bytesRead;
        while ((bytesRead = byteArrayInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
    }
}
