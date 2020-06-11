package server.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "warehouses2")
public class Warehouse2 {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please provide good count of WR2")
    private Integer goodCount;

//    @ManyToOne(fetch = FetchType.EAGER)
//    private Good good;

    public Warehouse2() {
    }

    public Warehouse2(Integer goodCount) {
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

//    public void setGood(Good good) {
//        this.good = good;
//    }

    public Integer getGoodCount() {
        return goodCount;
    }

//    public Good getGood() {
//        return good;
//    }
}