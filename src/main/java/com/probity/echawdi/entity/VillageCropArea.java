package com.probity.echawdi.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "village_crop_area")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VillageCropArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double version;
    private Long createdUserId;
    private LocalDateTime createDtTm;
    private Long updateUserid;
    private LocalDateTime updateDtTm;

    private Integer districtCode;
    private Integer talukaCode;
    private String ccode;

    private Double irrigationArea;
    private Double koradwahuArea;
    private Double otherArea;
    private Double totalCropArea;

    private Character activeFlag;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private CropMaster crop;

    @ManyToOne
    @JoinColumn(name = "adarsha_id")
    private GramAdarshTaktaEntity gramAdarshaTakta;
    
}
