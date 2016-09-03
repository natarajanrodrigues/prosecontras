package br.edu.ifpb.entity;

import org.bson.Document;

import javax.persistence.*;

/**
 * Created by susanneferraz on 31/08/16.
 */
@Entity
public class UserProfile implements MongoDbObject<UserProfile> {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String name;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile)) return false;

        UserProfile user = (UserProfile) o;

        if (id != user.id) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public Document toDocument() {

        Document doc = new Document();

        doc.append("id", id);
        doc.append("name", name);
        doc.append("email", email);
        doc.append("password", password);

        return doc;
    }

    @Override
    public UserProfile fromDocument(Document document) {

        this.id = document.getLong("id");
        this.name = document.getString("name");
        this.email = document.getString("email");
        this.password = document.getString("password");

        return this;
    }
}
