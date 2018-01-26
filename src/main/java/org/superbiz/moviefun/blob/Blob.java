package org.superbiz.moviefun.blob;

import java.io.InputStream;
import java.util.Objects;

public class Blob {
    private final String name;
    private final InputStream inputStream;
    private final String contentType;

    public Blob(String name, InputStream inputStream, String contentType) {
        this.name = name;
        this.inputStream = inputStream;
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Blob{" +
                "name='" + name + '\'' +
                ", inputStream=" + inputStream +
                ", contentType='" + contentType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blob blob = (Blob) o;
        return Objects.equals(name, blob.name) &&
                Objects.equals(inputStream, blob.inputStream) &&
                Objects.equals(contentType, blob.contentType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, inputStream, contentType);
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public String getName() {
        return name;
    }
}
