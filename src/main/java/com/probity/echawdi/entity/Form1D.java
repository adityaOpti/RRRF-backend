/**
 * 
 */
package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.probity.echawdi.audit.FormsEntity;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rushikesh Durgude
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="echawdi_form1d")
public class Form1D extends FormsEntity{

	private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="echawdi_form1d_seq_gen")
    @SequenceGenerator(name="echawdi_form1d_seq_gen", sequenceName="echawdi_form1d_seq", allocationSize=1)
	private Long id;
    
	public String  rules;

}
