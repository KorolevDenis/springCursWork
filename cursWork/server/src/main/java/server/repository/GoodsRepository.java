package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.Good;

public interface GoodsRepository extends JpaRepository<Good, Long> {
}
