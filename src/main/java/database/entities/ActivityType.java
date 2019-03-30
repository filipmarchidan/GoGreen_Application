package database.entities;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "activitytypes")
public class ActivityType {

    @Id
    @Getter
    @Setter
    private Integer id;

    @Column(name = "activity_name", nullable = false)
    @Getter
    @Setter
    private String activity_name;

    @Column(name = "co2_savings", nullable = false)
    @Getter
    @Setter
    private Integer co2_savings;
    
}
