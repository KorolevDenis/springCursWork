package server.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goods")
public class Good {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please provide good name")
    private String name;

    @NotNull(message = "Please provide good priority")
    private Float priority;

    @OneToMany(targetEntity= Sale.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "good_id", nullable = false)
    private List<Sale> sales = new ArrayList<>();

    @OneToMany(targetEntity=Warehouse1.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "good_id", nullable = false)
    private List<Warehouse1> warehouse1Goods = new ArrayList<>();

    @OneToMany(targetEntity=Warehouse2.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "good_id", nullable = false)
    private List<Warehouse2> warehouse2Goods = new ArrayList<>();

    public Good() {
    }

    public Good(String name, Float priority, List<Sale> sales,
                List<Warehouse1> warehouse1Goods, List<Warehouse2> warehouse2Goods) {
        this.name = name;
        this.priority = priority;
        this.sales = sales;
        this.warehouse1Goods = warehouse1Goods;
        this.warehouse2Goods = warehouse2Goods;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public Long getId() {
        return id;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(Float priority) {
        this.priority = priority;
    }

    public void setWarehouse1Goods(List<Warehouse1> warehouse1Goods) {
        this.warehouse1Goods = warehouse1Goods;
    }

    public void setWarehouse2Goods(List<Warehouse2> warehouse2Goods) {
        this.warehouse2Goods = warehouse2Goods;
    }

    public String getName() {
        return name;
    }

    public Float getPriority() {
        return priority;
    }

    public List<Warehouse1> getWarehouse1Goods() {
        return warehouse1Goods;
    }

    public List<Warehouse2> getWarehouse2Goods() {
        return warehouse2Goods;
    }
}
