package br.edu.ifpb.entity;

import org.bson.Document;

import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * Created by susanneferraz on 31/08/16.
 */
@Entity
public class Topic implements MongoDbObject<Topic> {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String photoPath;
    private String description;
    private LocalDateTime postedDateTime;
    @Transient
    private Float forPercentual;
    @Transient
    private Float againstPercentual;
    @ManyToOne
    private UserProfile actor;

    public Topic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    
    public LocalDateTime getPostedDateTime() {
        return this.postedDateTime;
    }

    public void setPostedDateTime(LocalDateTime postedDateTime) {
        this.postedDateTime = postedDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public UserProfile getActor() {
        return this.actor;
    }
    
    public void setActor(UserProfile actor) {
        this.actor = actor;
    }

    public Float getForPercentual() {
        return this.forPercentual;
    }

    public void setForPercentual(Float forPercentual) {
        this.forPercentual = forPercentual;
    }

    public Float getAgainstPercentual() {
        return this.againstPercentual;
    }

    public void setAgainstPercentual(Float againstPercentual) {
        this.againstPercentual = againstPercentual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;

        Topic topic = (Topic) o;

        if (id != null ? !id.equals(topic.id) : topic.id != null) return false;
        if (name != null ? !name.equals(topic.name) : topic.name != null) return false;
        if (photoPath != null ? !photoPath.equals(topic.photoPath) : topic.photoPath != null) return false;
//        if (postedDateTime != null ? !postedDateTime.equals(topic.postedDateTime) : topic.postedDateTime != null) return false;
        return description != null ? description.equals(topic.description) : topic.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (photoPath != null ? photoPath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
//        result = 31 * result + (postedDateTime != null ? postedDateTime.hashCode() : 0);
        return result;
    }

    @Override
    public Document toDocument() {

        Document doc = new Document();

        doc.append("id", this.id);
        doc.append("user", this.actor.toDocument());
        doc.append("postedDateTime", this.postedDateTime.toString());
        doc.append("photoPath", this.photoPath);
        doc.append("description", this.description);

        return doc;
    }

    @Override
    public Topic fromDocument(Document document) {

        this.id = document.getLong("id");
        this.actor = new UserProfile().fromDocument(document.get("user", Document.class));
        this.postedDateTime = LocalDateTime.parse(document.getString("postedDateTime"));
        this.photoPath = document.getString("photoPath");
        this.description = document.getString("description");

        return this;
    }
}
