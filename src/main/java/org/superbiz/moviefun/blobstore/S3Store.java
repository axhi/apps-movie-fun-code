package org.superbiz.moviefun.blobstore;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.http.MediaType;

import java.util.Optional;

public class S3Store implements BlobStore {
    private final AmazonS3Client s3Client;
    private final String s3BucketName;

    public S3Store(AmazonS3Client s3Client, String s3BucketName) {
        this.s3Client = s3Client;
        this.s3BucketName = s3BucketName;
    }

    @Override
    public void put(Blob blob) {
        ObjectMetadata metadata = new ObjectMetadata();
        s3Client.putObject(s3BucketName, blob.getName(), blob.getInputStream(), metadata);
    }

    @Override
    public Optional<Blob> get(String name) {
        S3Object object = s3Client.getObject(s3BucketName, name);
        Optional<Blob> optionalBlob;

        optionalBlob = Optional.of(
                new Blob(
                        name,
                        object.getObjectContent(),
                        MediaType.IMAGE_JPEG_VALUE
                )
        );

        return optionalBlob;
    }

    @Override
    public void deleteAll() {

    }
}
