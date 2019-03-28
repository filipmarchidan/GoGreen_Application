package database.entities;



import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "activitytypes")
public class ActivityType {

    @Id
    private Integer id;

    @Column(name = "activity_name", nullable = false)
    private String activity_name;

    @Column(name = "co2_savings", nullable = false)
    private Integer co2_savings;


}
