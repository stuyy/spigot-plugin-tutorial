package entities;

import com.j256.ormlite.field.DatabaseField;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name = "user_home")
public class UserHome {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private double x;

    @DatabaseField()
    private double y;

    @DatabaseField()
    private double z;

    @OneToOne(mappedBy = "users")
    private User user;

    public UserHome() {
    }

    public UserHome(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
