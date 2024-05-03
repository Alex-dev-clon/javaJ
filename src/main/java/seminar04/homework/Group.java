package seminar04.homework;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "student_groups")
public class Group {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String groupName;

    @Column(name = "description")
    private String description;

    public Group() {
    }

    public Group(Long id, String groupName, String description) {
        this.id = id;
        this.groupName = groupName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group group)) return false;
        return Objects.equals(getId(), group.getId()) && Objects.equals(getGroupName(), group.getGroupName()) && Objects.equals(getDescription(), group.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGroupName(), getDescription());
    }
}