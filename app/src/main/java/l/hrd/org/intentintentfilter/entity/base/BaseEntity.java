package l.hrd.org.intentintentfilter.entity.base;

/**
 * Created by RATHANA on 10/24/2017.
 */

public class BaseEntity {
    private int id;
    private String name;
    public BaseEntity() {}
    public BaseEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
