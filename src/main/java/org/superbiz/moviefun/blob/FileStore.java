package org.superbiz.moviefun.blob;

import org.apache.tika.io.IOUtils;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class FileStore implements BlobStore {

    @Override
    public void put(Blob blob) throws IOException {
        File file = new File("covers/" + blob.getName());
        file.delete();
        file.getParentFile().mkdirs();
        file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        IOUtils.copy(blob.getInputStream(), fileOutputStream);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        File file = new File("covers/" + name);
        Optional<Blob> optionalBlob = Optional.empty();

        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            optionalBlob = Optional.of(new Blob(
                    name,
                    inputStream,
                    MediaType.IMAGE_JPEG_VALUE
            ));
        }

        return optionalBlob;
    }

    @Override
    public void deleteAll() {

    }
}
