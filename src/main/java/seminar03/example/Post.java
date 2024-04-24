package seminar03.example;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Post implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private Date createdDate;
    private String content;
    private int likes;
    private List<String> comments;
    private Person person;

    public Post() {
    }

    public Post(int id, Date createdDate, String content, int likes, List<String> comments, Person person) {
        this.id = id;
        this.createdDate = createdDate;
        this.content = content;
        this.likes = likes;
        this.comments = comments;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return "Post{" +
                "id=" + id +
                ", createdDate=" + format.format(createdDate) +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", comments=" + comments +
                ", person=" + person +
                '}';
    }
}
