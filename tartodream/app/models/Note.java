package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Note extends Model {
    @OneToMany
    public List<Record> recordList;

    @Required
    public User user;
}
