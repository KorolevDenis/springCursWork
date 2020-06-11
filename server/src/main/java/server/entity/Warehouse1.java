package server.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "warehouses1")
public class Warehouse1 {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please provide good count of WR1")
    private Integer goodCount;

    public Warehouse1() {
    }

    public Warehouse1(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }
}

