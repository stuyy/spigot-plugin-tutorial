package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField()
    private String uniqueId;

    @DatabaseField()
    private String username;

    public User() { }
}
