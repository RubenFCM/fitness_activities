package entidades;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Diet")
public class Diet {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Column(name = "Name", unique = true, length = 60,nullable = false)
    @Type(type = "text")
    @Expose()
    private String name;

    @Column(name = "Description")
    @Type(type="text")
    @Expose
    private String description;

    @Column(name = "Calories")
    @Expose
    private double calories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_diet",
        joinColumns = @JoinColumn(name ="Diet_id"),
        inverseJoinColumns =@JoinColumn(name = "User_id")
    )
    private List<Users> users =new ArrayList<>();

    public Diet() {
    }

    public Diet(Long id, String name, String description, double calories, List<Users> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
