package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.RoleMenu;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long>{
	  Page<RoleMenu> findAllByActiveFlag(Character activeFlag, Pageable pageable);
	  
	  @Query(value="select menu_id from mhror_master.role_menu where role_id=?"
			 + "",nativeQuery = true)
	  List<Double> getByRoleKey(Long rolekey);
}