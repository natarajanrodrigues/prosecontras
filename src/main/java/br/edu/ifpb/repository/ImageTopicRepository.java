package br.edu.ifpb.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoCollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by susanneferraz on 03/09/16.
 */
public class ImageTopicRepository {
    private static final MongoDatabase dataBase = MongoDbConnection.getMongoClient().getDatabase("topicimages");

    public static String saveTopicImage(MultipartFile file, Long topicId) throws IOException {
        GridFSBucket gridFSBucket = GridFSBuckets.create(dataBase);
        GridFSUploadOptions options = new GridFSUploadOptions()
        .metadata(new Document("topicid", topicId))
        .metadata(new Document("content-type", file.getContentType()));
        ObjectId fileId = gridFSBucket.uploadFromStream("topicImage" + topicId + file.getOriginalFilename(), file.getInputStream(), options);

        return fileId.toString();
    }

    public static GridFSDownloadStream getTopicImage(String topicId) {
        GridFSBucket gridFSBucket = GridFSBuckets.create(dataBase);
        //        String fileName = "topicImage" + topicId;
        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(new ObjectId(topicId));
        int fileLength = (int) downloadStream.getGridFSFile().getLength();
        byte[] bytesToWriteTo = new byte[fileLength];
        downloadStream.read(bytesToWriteTo);
        downloadStream.close();
        return downloadStream;
    }

    public static byte[] getTopicImage2(String topicId) {

        GridFSBucket gridFSBucket = GridFSBuckets.create(dataBase);
        //        String fileName = "topicImage" + topicId;
        //        System.out.println(topicId);
        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(new ObjectId(topicId));
        int fileLength = (int) downloadStream.getGridFSFile().getLength();
        byte[] bytesToWriteTo = new byte[fileLength];
        downloadStream.read(bytesToWriteTo);
        downloadStream.close();

        return bytesToWriteTo;

    }


        


}
