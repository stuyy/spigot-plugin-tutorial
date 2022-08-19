package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "users")
public class User {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private String uniqueId;

    @DatabaseField()
    private String username;

    @OneToOne
    @JoinColumn(name = "home_id")
    private UserHome home;

    public User() {
    }

    public UserHome getHome() {
        return home;
    }

    public void setHome(UserHome home) {
        this.home = home;
    }

    public int getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
