package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crop_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // private String cropName;
    
    @Column(nullable = false, unique = true)
    private String cropName;

}
