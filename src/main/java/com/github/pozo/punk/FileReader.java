package com.github.pozo.punk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pozo on 2016.07.16..
 */
public class FileReader {
    private final InputStream inputStream;

    public FileReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    public byte[] readFully() throws IOException {
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

        return output.toByteArray();
    }
}
