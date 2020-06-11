package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.entity.Good;
import server.repository.GoodsRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    GoodsRepository goodsRepository;

    @GetMapping(value = "/getGood")
    public @ResponseBody Good getGood(long id) {
        Good good = goodsRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Please provide correct good_id"));;

        return good;
    }

    @GetMapping(value = "/getGoods")
    public List<Good> getGoods() {
        return goodsRepository.findAll();
    }


    @PostMapping(value = "/addGood")
    public @ResponseBody Good addGood(@Valid @RequestBody Good good) {
        Good save = goodsRepository.save(good);

        return save;
    }

    @DeleteMapping(value = "/deleteGood")
    public String deleteGood(long id) {
        goodsRepository.deleteById(id);

        return "delete";
    }
}

