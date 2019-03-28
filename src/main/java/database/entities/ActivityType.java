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
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getActivity_name() {
        return activity_name;
    }
    
    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }
    
    public Integer getCo2_savings() {
        return co2_savings;
    }
    
    public void setCo2_savings(Integer co2_savings) {
        this.co2_savings = co2_savings;
    }
    
}
