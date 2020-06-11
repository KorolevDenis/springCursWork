package server.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please provide good count of sale")
    private Integer goodCount;

    @NotNull(message = "Please provide creation date of sale")
    private String createDate;

//    @ManyToOne(fetch = FetchType.EAGER)
//    private Good good;

    public Sale() {
    }

    public Sale(Integer goodCount, String createDate) {
        this.goodCount = goodCount;
        this.createDate = createDate;
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

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public String getCreateDate() {
        return createDate;
    }
}
