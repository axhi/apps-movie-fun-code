package org.superbiz.moviefun;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvUtils {

    public <T> List<T> readFromCsv(ObjectReader objectReader, String path) {
        try {
            List<T> results = new ArrayList<>();

            MappingIterator<T> iterator = objectReader.readValues(readFile(path));

            while (iterator.hasNext()) {
                results.add(iterator.nextValue());
            }

            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFile(String path) {
        InputStream resourceAsStream = getClassLoader().getResourceAsStream(path);
        Scanner scanner = new Scanner(resourceAsStream).useDelimiter("\\A");

        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            return "";
        }

    }

    private ClassLoader getClassLoader() {
        return this.getClass().getClassLoader();
    }
}
