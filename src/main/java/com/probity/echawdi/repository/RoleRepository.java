package com.probity.echawdi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.Role;

/**
 * @author Kumar Harpude
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
  Optional<Role> findByName(String name);
  
  Page<Role> findAllByActiveFlag(Character activeFlag, Pageable pageable);
  
  
}