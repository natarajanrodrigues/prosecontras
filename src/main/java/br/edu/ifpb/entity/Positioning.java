package br.edu.ifpb.entity;

import br.edu.ifpb.enums.Status;
import org.bson.Document;

/**
 * Created by kieckegard on 01/09/2016.
 */
public class Positioning implements MongoDbObject<Positioning> {

    private Topic topic;
    private String comment;
    private Status status;

    public Positioning() {

    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Document toDocument() {

        Document doc = new Document();

        doc.append("topic", this.topic.toDocument());
        doc.append("comment", this.comment);
        doc.append("status", this.status.toString());

        return doc;
    }

    @Override
    public Positioning fromDocument(Document document) {

        this.topic = new Topic().fromDocument(document.get("topic", Document.class));
        this.comment = document.getString("comment");
        this.status = Status.valueOf(document.getString("status"));

        return this;
    }

    @Override
    public String toString() {
        return "Positioning{" +
                "topic=" + topic +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}
