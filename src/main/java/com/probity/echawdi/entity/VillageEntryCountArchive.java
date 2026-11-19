package com.probity.echawdi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name="e_village_entry_count_archive")
public class VillageEntryCountArchive extends PlainAudit{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private Long id;

	@Column(name="division_code")
	private String division;
	
	@Column(name="district_code")
	private String district;

	@Column(name="taluka_code")
	private String taluka;
	
	@Column(name="total_phase1_login")
	private Long totalPhase1Login;

	@Column(name="total_entry")
	private Long totalEntry;

	@Column(name="archive_date_time")
	private LocalDateTime archiveDateTime;
}