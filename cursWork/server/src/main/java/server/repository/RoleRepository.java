package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}