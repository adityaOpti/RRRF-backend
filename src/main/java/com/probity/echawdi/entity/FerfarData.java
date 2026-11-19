package com.probity.echawdi.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "ferfar_data")
public class FerfarData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String districtCode;
    private String talukaCode;
    private String circleCode;
    private String villageCode;
    private String ccode;
    private String mutNo;
    private LocalDate mutDate;
    private String documentReference;
    private String remark;

    private String createdBy;
    private String updatedBy;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ferfar_type_id", referencedColumnName = "id", nullable = false)
    private FerfarTypeMaster ferfarType;
}
