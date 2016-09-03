package br.edu.ifpb.entity;

import br.edu.ifpb.utils.LocalDateTimeDeserializer;
import br.edu.ifpb.utils.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kieckegard on 01/09/2016.
 */
public class Opinion implements MongoDbObject<Opinion>, Comparable<Opinion>, JsonObject<Opinion> {

    private UserProfile user;
    private LocalDateTime dateTime;
    private List<Positioning> positionings;

    public Opinion() {
        positionings = new LinkedList<>();
    }

    public UserProfile getUser() {
        return user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Positioning> getPositionings() {
        return Collections.unmodifiableList(this.positionings);
    }

    public void addPositioning(Positioning positioning) {
        this.positionings.add(positioning);
    }

    public void removePositioning(Positioning positioning) {
        this.positionings.remove(positioning);
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Document toDocument() {

        Document doc = new Document();
        List<Document> positioningsDoc = new LinkedList<>();

        for(Positioning p : positionings){
            positioningsDoc.add(p.toDocument());
        }

        doc.append("user", user.toDocument());
        doc.append("opinionDateTime", dateTime.toString());
        doc.append("positionings", positioningsDoc);

        return doc;
    }

    @Override
    public Opinion fromDocument(Document document) {

        this.positionings = new LinkedList<>();

        this.user = new UserProfile().fromDocument(document.get("user", Document.class));
        this.dateTime = LocalDateTime.parse(document.getString("opinionDateTime"));

        List<Document> posioningDocuments = (List<Document>) document.get("positionings");

        for(Document doc : posioningDocuments){
            this.positionings.add(new Positioning().fromDocument(doc));
        }

        return this;
    }

    @Override
    public String getJsonString() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
        return gson.toJson(this);
    }

    @Override
    public Opinion fromJsonString(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
        return gson.fromJson(json, Opinion.class);
    }

    @Override
    public String toString() {
        return "Opinion{" +
                "user=" + user +
                ", dateTime=" + dateTime +
                ", positionings=" + positionings +
                '}';
    }

    @Override
    public int compareTo(Opinion o) {
        return o.dateTime.compareTo(this.dateTime);
    }
}
