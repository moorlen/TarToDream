package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Record extends Model {

    @Required
    public String startDate;

    @Required
    public String endDate;

    @Lob
    @Required
    public String text;

    @Required
    public String type;

}
