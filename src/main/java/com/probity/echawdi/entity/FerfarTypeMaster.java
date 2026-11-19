package com.probity.echawdi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ferfar_type_master")
@Entity
public class FerfarTypeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_code", length = 20, nullable = false, unique = true)
    private String typeCode;

    @Column(name = "type_name", nullable = false)
    private String typeName;
}
