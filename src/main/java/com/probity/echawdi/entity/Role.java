package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Kumar Harpude
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role extends PlainAudit{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	public enum ERole {
//		  ROLE_ADMIN
//	}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_roles_seq_gen")
    @SequenceGenerator(name="echawdi_roles_seq_gen", sequenceName="echawdi_roles_seq", allocationSize=1)
	private Long id;

//	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private String name;
}