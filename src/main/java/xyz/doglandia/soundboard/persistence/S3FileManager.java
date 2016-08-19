package xyz.doglandia.soundboard.persistence;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import xyz.doglandia.soundboard.util.Sensitive;

import java.io.File;

/**
 * Created by tdk10 on 8/15/2016.
 */
public class S3FileManager implements FilesManager {

    private static final String S3_BUCKET_NAME = "soundboard-app";

    private AmazonS3 s3Client;

    public S3FileManager() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(Sensitive.AWS_ACCESS_KEY_ID, Sensitive.AWS_SECRET_ACCESS_KEY);
        s3Client = new AmazonS3Client(awsCreds);
    }


    @Override
    public String uploadFile(String key, File downloadedSoundFile) {

        s3Client.putObject(S3_BUCKET_NAME, key, downloadedSoundFile);

        return s3Client.getUrl(S3_BUCKET_NAME, key).toString();
    }
}
