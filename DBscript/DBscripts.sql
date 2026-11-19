*****To Restart All sequences and Truncate Tables of UAT db ***
--(lr_pending_dues)OneTimeENtry Entities table name)
--Fire this in your db from(
delete from revenue_year ;
delete from token;
delete from challan_dtl;
delete from land_revenue_demand ;
delete from lr_pending_dues; 
delete from echawdi_form1; 
delete from echawdi_form1a; 
delete from echawdi_form1b;
delete from echawdi_form1c;
delete from echawdi_form1d;
delete from echawdi_form1e;
delete from echawdi_form17;  

--sequences

alter SEQUENCE challan_dtl_seq RESTART with 1;
alter SEQUENCE token_seq RESTART with 1;
alter SEQUENCE revenue_year_seq RESTART with 1;
alter SEQUENCE land_revenue_demandseq_gen RESTART with 1;
alter SEQUENCE lr_pending_dues_seq RESTART with 1;
alter SEQUENCE echawdi_form1_seq RESTART with 1;
alter SEQUENCE echawdi_form1a_seq RESTART with 1;
alter SEQUENCE echawdi_form1b_seq RESTART with 1;
alter SEQUENCE echawdi_form1c_seq RESTART with 1;
alter SEQUENCE echawdi_form1d_seq RESTART with 1;
alter SEQUENCE echawdi_form1e_seq RESTART with 1;
alter SEQUENCE echawdi_form17_seq RESTART with 1;

--modifications 
alter table echawdi_form17 drop column perioad_from_date;
alter table echawdi_form17 drop column perioad_to_date;
alter table echawdi_form17 drop column a_liable_for_local_cess ;
alter table echawdi_form17 drop column b_free_of_local_cess  ;
alter table echawdi_form17 drop column makta_khata_no ;
alter table echawdi_form17 drop column period_if_more_than_year  ;
alter table echawdi_form17 drop column period_from_date;
alter table echawdi_form17 drop column period_to_date;
alter table echawdi_form17 drop COLUMN local_cess 
--)To 
****************************************************************



*********Rushi - 23-03-22 - **********
 
updated query for echawdi form1 Report by ccode

SELECT 
FPIN,
TENURE_NAME,
ASSESSMENT,
NET_CULTI_AREA_H,
NA_AREA_H,
NA_ASSESSMENT,
POTKHARABA_A_H,
POTKHARABA_B_H,
PUBLIC_RIGHTS_OF_WAY_AND_EASEMENTS,
PARTICULARS_OF_ALTERATION,
DESIGNATION,
REMARKS,
ORDER_NO,
ORDER_DATE 
FROM (SELECT
* 
FROM
(
SELECT
F7.PIN AS FPIN,
F7.CCODE,
CONCAT(
CASE
WHEN
F7.PIN1 != '' 
OR F7.PIN1 != NULL 
THEN
F7.PIN1 
END
)AS FPIN1, CONCAT(
CASE
WHEN
F7.PIN2 != '' 
OR F7.PIN2 != NULL 
THEN
'/' || F7.PIN2 
END
)AS FPIN2, CONCAT(
CASE
WHEN
F7.PIN3 != '' 
OR F7.PIN3 != NULL 
THEN
'/' || F7.PIN3 
END
)AS FPIN3, CONCAT(
CASE
WHEN
F7.PIN4 != '' 
OR F7.PIN4 != NULL 
THEN
'/' || F7.PIN4 
END
)AS FPIN4, CONCAT(
CASE
WHEN
F7.PIN5 != '' 
OR F7.PIN5 != NULL 
THEN
'/' || F7.PIN5 
END
)AS FPIN5, CONCAT(
CASE
WHEN
F7.PIN6 != '' 
OR F7.PIN6 != NULL 
THEN
'/' || F7.PIN6 
END
)AS FPIN6, CONCAT(
CASE
WHEN
F7.PIN7 != '' 
OR F7.PIN7 != NULL 
THEN
'/' || F7.PIN7 
END
)AS FPIN7, CONCAT(
CASE
WHEN
F7.PIN8 != '' 
OR F7.PIN8 != NULL 
THEN
F7.PIN8 
END
)AS FPIN8, T.TENURE_NAME, F7A.ASSESSMENT, F7A.NET_CULTI_AREA_H, F7A.NA_AREA_H, F7A.NA_ASSESSMENT, F7A.POTKHARABA_A_H, F7A.POTKHARABA_B_H 
FROM
mhror_eferfar.FORM7_AREA F7A, mhror_eferfar.FORM7 F7, mhror_eferfar.FORM7 T 
WHERE
F7.PIN = F7A.PIN 
AND F7.PIN = F7A.PIN 
AND F7.PIN1 = F7A.PIN1 
AND F7.PIN2 = F7A.PIN2 
AND F7.PIN3 = F7A.PIN3 
AND F7.PIN4 = F7A.PIN4 
AND F7.PIN5 = F7A.PIN5 
AND F7.PIN6 = F7A.PIN6 
AND F7.PIN6 = F7A.PIN6 
AND F7.PIN7 = F7A.PIN7 
AND F7.PIN8 = F7A.PIN8 
AND F7A.CCODE = F7.CCODE 
AND T.TENURE_CODE = F7.TENURE_CODE 
AND F7.MARKED <> 'Y' 
AND CONCAT(F7.PIN, '/', F7.PIN1, '/', F7.PIN2, '/', F7.PIN3, '/', F7.PIN4, '/', F7.PIN5, '/', F7.PIN6, '/', F7.PIN7, '/', F7.PIN8) NOT IN 
(
SELECT
CONCAT(FK.PIN, '/', FK.PIN1, '/', FK.PIN2, '/', FK.PIN3, '/', FK.PIN4, '/', FK.PIN5, '/', FK.PIN6, '/', FK.PIN7, '/', FK.PIN8) 
FROM
mhror_eferfar.FORM7_KHATA FK 
WHERE
FK.CCODE = '271500070197320000' 
AND FK.KHATA_NO = '500001'
)
)
AS T1 
LEFT JOIN
(
SELECT
* 
FROM
(
(
SELECT
   EF1.CCODE, EF1.PUBLIC_RIGHTS_OF_WAY_AND_EASEMENTS, EF1.PARTICULARS_OF_ALTERATION, EF1.DESIGNATION, EF1.REMARKS, EF1.ORDER_NO, EF1.ORDER_DATE, EF1.PIN, EF1.ID, CONCAT(
   CASE
      WHEN
	 EF1.PIN1 != ' ' 
	 OR EF1.PIN1 != NULL 
      THEN
	 EF1.PIN1 
   END
)AS PIN1, CONCAT(
   CASE
      WHEN
	 EF1.PIN2 != ' ' 
	 OR EF1.PIN2 != NULL 
      THEN
	 '/' || EF1.PIN2 
   END
)AS PIN2, CONCAT(
   CASE
      WHEN
	 EF1.PIN3 != ' ' 
	 OR EF1.PIN3 != NULL 
      THEN
	 '/' || EF1.PIN3 
   END
)AS PIN3, CONCAT(
   CASE
      WHEN
	 EF1.PIN4 != ' ' 
	 OR EF1.PIN4 != NULL 
      THEN
	 '/' || EF1.PIN4 
   END
)AS PIN4, CONCAT(
   CASE
      WHEN
	 EF1.PIN5 != ' ' 
	 OR EF1.PIN5 != NULL 
      THEN
	 '/' || EF1.PIN5 
   END
)AS PIN5, CONCAT(
   CASE
      WHEN
	 EF1.PIN6 != ' ' 
	 OR EF1.PIN6 != NULL 
      THEN
	 '/' || EF1.PIN6 
   END
)AS PIN6, CONCAT(
   CASE
      WHEN
	 EF1.PIN7 != ' ' 
	 OR EF1.PIN7 != NULL 
      THEN
	 '/' || EF1.PIN7 
   END
)AS PIN7, CONCAT(
   CASE
      WHEN
	 EF1.PIN8 != ' ' 
	 OR EF1.PIN8 != NULL 
      THEN
	 EF1.PIN8 
   END
) AS PIN8 
FROM
   ECHAWDI_FORM1 EF1)
)
AS T2
)
AS T3 
ON T1.CCODE = T3.CCODE 
AND T1.FPIN = T3.PIN 
AND T1.FPIN1 = T3.PIN1 
AND T1.FPIN2 = T3.PIN2 
AND T1.FPIN3 = T3.PIN3 
AND T1.FPIN4 = T3.PIN4 
AND T1.FPIN5 = T3.PIN5 
AND T1.FPIN6 = T3.PIN6 
AND T1.FPIN7 = T3.PIN7 
AND T1.FPIN8 = T3.PIN8 
WHERE
T1.CCODE ='271500070197320000' 
)A

***************End 23-03-22*************************************
*********Rushi - 25-03-22 - alter column**********

alter table mhror_eferfar.form7 alter column district_code type integer  USING (district_code::integer);

alter table mhror_eferfar.form7 alter column taluka_code type integer  USING (taluka_code::integer);

alter table mhror_eferfar.form7_area alter column district_code type integer  USING (district_code::integer);

alter table mhror_eferfar.form7_area alter column taluka_code type integer  USING (taluka_code::integer);

alter table mhror_eferfar.form7_khata alter column district_code type integer  USING (district_code::integer);

alter table mhror_eferfar.form7_khata alter column taluka_code type integer  USING (taluka_code::integer);


alter table mhror_crop.form12_irrsource alter column district_code type integer  USING (district_code::integer);

alter table mhror_crop.form12_irrsource alter column taluka_code type integer  USING (taluka_code::integer);
***************End 25-03-22*************************************

*******************26-03-22*************************************

 delete from land_revenue_collection ;
 alter SEQUENCE land_revenue_collection_seq RESTART with 1;
 alter table challan_dtl drop COLUMN financial_year ;
 alter table land_revenue_collection  drop COLUMN financial_year ;
 alter table land_revenue_demand  drop COLUMN financial_year; 
 ALTER TABLE echawdi_form17 
 ALTER COLUMN assessment TYPE numeric(18,2);
 ALTER TABLE echawdi_form17 
 ALTER COLUMN area_affected TYPE numeric(18,2);
 ALTER TABLE echawdi_form17 
 ALTER COLUMN total_area TYPE numeric(18,2);
 ALTER TABLE echawdi_form17 
 ALTER COLUMN amount_of_lr TYPE numeric(18,2);
 ALTER TABLE echawdi_form17 
 ALTER COLUMN amount_of_lc TYPE numeric(18,2);
 ALTER TABLE echawdi_form17 
 ALTER COLUMN amount_of_vp TYPE numeric(18,2);
 
 alter table echawdi_form_6b DROP  column nature_of_change_in_map;
 alter table echawdi_form_6b DROP  column by_whom_done;
 alter table echawdi_form_6b DROP  column date_of_change;


 ALTER TABLE echawdi_form1e add column date_of_decision_on_encroachment date;
 ALTER TABLE echawdi_form1e add column date_of_encroachment date ;
 ALTER TABLE echawdi_form1e add column area_of_encroached_path text;
 
 
 alter table echawdi_form1 drop column revenue_year;  
 alter table echawdi_form1a  drop column revenue_year;   
 alter table echawdi_form1b drop column revenue_year;   
 alter table echawdi_form1c drop column revenue_year;   
 alter table echawdi_form1d drop column revenue_year;   
 alter table echawdi_form1e drop column revenue_year;   
 alter table echawdi_form2 drop column revenue_year;     
 alter table echawdi_form3 drop column revenue_year;    
 alter table echawdi_form7a drop column revenue_year;    
 alter table echawdi_form7b drop column revenue_year;    
 alter table echawdi_form8c drop column revenue_year; 
 alter table echawdi_form14 drop column revenue_year;  
 alter table echawdi_form15 drop column revenue_year;   
 alter table echawdi_form18 drop column revenue_year;  
 alter table echawdi_form19 drop column revenue_year;    
 alter table echawdi_form21 drop column revenue_year;     
 alter table echawdi_form6d drop column remarks; 
 alter table echawdi_form6b drop column remarks;
 
 alter table echawdi_form17 add COLUMN taluka_code integer; 
 alter table echawdi_form17 add COLUMN district_code integer; 
 alter table echawdi_form1e add COLUMN date_of_decision_on_encroachment date;
 alter table echawdi_form1e add COLUMN date_of_encroachment date;
 
 drop TABLE echawdi_form_6b;  
 drop TABLE echawdi_form_6d; 
--*******************26-03-22** bhagyashri***********************************

alter table mhror_crop.form12_irrsource  add COLUMN district_code int;
 alter table mhror_crop.form12_irrsource  add COLUMN taluka_code int;
 
 
 
 SELECT DISTINCT WATER_SUPPLY_NAME     
			FROM   	  
			mhror_master.m_irrsource mi,mhror_crop.form12_irrsource fi     
			WHERE     
			fi.WATER_SUPPLY_CODE = mi.WATER_SUPPLY_CODE   
			AND DISTRICT_CODE = :districtCode             
			AND TALUKA_CODE =:talukaCode  
			AND CCODE = :cCode                             
			AND PIN = :pin                          
			AND CONCAT(                                    
			CASE WHEN PIN1 <> '' THEN PIN1 END,    
			CASE WHEN PIN2 <> '' THEN '/' || PIN2 END,    
			CASE WHEN PIN3 <> '' THEN '/' || PIN3 END,    
			CASE WHEN PIN4 <> '' THEN '/' || PIN4 END,    
			CASE WHEN PIN5 <> '' THEN '/' || PIN5 END,    
			CASE WHEN PIN6 <> '' THEN '/' || PIN6 END,    
			CASE WHEN PIN7 <> '' THEN '/' || PIN7 END,    
			CASE WHEN PIN8 <> '' THEN '/' || PIN8 END )  
			 = :hissaNo
			 			 
--****************************#29/03/2022#*****Ashish Dahale**********************

ALTER TABLE mhror_crop.form12  ALTER COLUMN district_code TYPE integer USING (district_code::integer);  
ALTER TABLE mhror_crop.form12  ALTER COLUMN taluka_code TYPE integer USING (taluka_code::integer);
ALTER TABLE mhror_master.M_VILLAGE_CENSUS_CHECK ALTER COLUMN district_code TYPE integer USING (district_code::integer);
ALTER TABLE mhror_master.M_VILLAGE_CENSUS_CHECK ALTER COLUMN taluka_code TYPE integer USING (taluka_code::integer);
ALTER TABLE mhror_master.M_VILLAGE_CENSUS_CHECK ALTER COLUMN district_code TYPE integer USING (district_code::integer);
ALTER TABLE mhror_master.M_VILLAGE_CENSUS_CHECK ALTER COLUMN taluka_code TYPE integer USING (taluka_code::integer);

ALTER TABLE mhror_eferfar.objection_details  ALTER COLUMN district_code TYPE integer USING (district_code::integer);  
ALTER TABLE mhror_eferfar.objection_details  ALTER COLUMN taluka_code TYPE integer USING (taluka_code::integer);

****************************************************************
30/03/2022 ---<<<<Ashish Dahale>>>>>------

DROP TABLE echawdi_form17;

CREATE TABLE echawdi_form17
(
  id bigint NOT NULL,
  version integer NOT NULL,
  create_dt_tm timestamp without time zone,
  created_user_id bigint,
  update_userid bigint,
  update_dt_tm timestamp without time zone,
  revenue_year text,
  district_code integer,
  taluka_code integer,
  ccode character varying(255),
  khata_no character varying(255),
  person_liable character varying(255),
  survey_hissa character varying(255),
  total_area numeric(18,2),
  uom_of_total_area character varying(255),
  case_no character varying(255),
  area_affected numeric(18,2),
  uom_of_area_affected character varying(255),
  nature_of_case character varying(255),
  period_if_more_than_year character varying(255),
  period_from_date date,
  period_to_date date,
  local_cess character varying(255),
  other_local_cess character varying(255),
  other_local_cess_desc character varying(255),
  local_cess_amount numeric(19,2),
  other_local_cess_amount numeric(19,2),
  amount_of_jm numeric(18,2),
  amount_of_zp numeric(18,2),
  amount_of_gp numeric(18,2),
  assessment numeric(18,2),
  remarks character varying(255),
  active_flag character(1),
  is_amount_paid character(1),
  CONSTRAINT echawdi_form17_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE echawdi_form17
  OWNER TO postgres;

  
  alter table if exists echawdi_form17 add column is_demand_generated char(1)
---><><><><------------------------------------------->>>>

----------------31/03/22--------------------Ashish Dahale
--Note: to test form1 properly find user by this queries
  
select ccode,pin,potkharaba_a_h from mhror_eferfar.form7_area
where 
ccode='271500070196850000' and potkharaba_a_h < 0  

select * from mhror_master.fpusers1 where servarthid in(select username from mhror_master.m_officermast where ccode='271500070196850000')

---------------------------------------------

----------02/04/2022----Indexing of all tables 

create index form7_district_code_idx on mhror_eferfar.form7(district_code);
create index form7_taluka_code_idx on mhror_eferfar.form7(taluka_code);
create index form7_ccode_idx on mhror_eferfar.form7(ccode);
create index form7_khata_no_idx on mhror_eferfar.form7(khata_no);
create index form7_pin_idx on mhror_eferfar.form7(pin);
create index form7_mult_idx on mhror_eferfar.form7(district_code, taluka_code, ccode, khata_no, pin);

create index form7khata_district_code_idx on mhror_eferfar.form7_khata(district_code);
create index form7khata_taluka_code_idx on mhror_eferfar.form7_khata(taluka_code);
create index form7khata_ccode_idx on mhror_eferfar.form7_khata(ccode);
create index form7khata_khata_no_idx on mhror_eferfar.form7_khata(khata_no);
create index form7khata_pin_idx on mhror_eferfar.form7_khata(pin);
create index form7khata_mult_idx on mhror_eferfar.form7_khata(district_code, taluka_code, ccode, khata_no, pin);

create index form7area_district_code_idx on mhror_eferfar.form7_area(district_code);
create index form7area_taluka_code_idx on mhror_eferfar.form7_area(taluka_code);
create index form7area_ccode_idx on mhror_eferfar.form7_area(ccode);
create index form7area_pin_idx on mhror_eferfar.form7_area(pin);
create index form7area_mult_idx on mhror_eferfar.form7_area(district_code, taluka_code, ccode,pin);

create index form12_district_code_idx on mhror_crop.form12(district_code);
create index form12_taluka_code_idx on mhror_crop.form12(taluka_code);
create index form12_ccode_idx on mhror_crop.form12(ccode);
create index form12_numeric_pin_idx on mhror_crop.form12(numeric_pin);
create index form12_pin_idx on mhror_crop.form12(pin);
create index form12_mult_idx on mhror_crop.form12(district_code, taluka_code, ccode, numeric_pin, pin);

create index form12irrsource_district_code_idx on mhror_crop.form12_irrsource(district_code);
create index form12irrsource_taluka_code_idx on mhror_crop.form12_irrsource(taluka_code);
create index form12irrsource_ccode_idx on mhror_crop.form12_irrsource(ccode);
create index form12irrsource_numeric_pin_idx on mhror_crop.form12_irrsource(numeric_pin);
create index form12irrsource_pin_idx on mhror_crop.form12_irrsource(pin);
create index form12irrsource_mult_idx on mhror_crop.form12_irrsource(district_code, taluka_code, ccode, numeric_pin, pin);

create index m_village_census_district_code_idx on mhror_master.m_village_census(district_code);
create index m_village_census_taluka_code_idx on mhror_master.m_village_census(taluka_code);
create index m_village_census_ccode_idx on mhror_master.m_village_census(ccode);
create index m_village_census_mult_idx on mhror_master.m_village_census(district_code, taluka_code, ccode);

create index lgd_mapper_district_code_idx on mhror_master.eferfar_lgd_mapper(district_code);
create index lgd_mapper_taluka_code_idx on mhror_master.eferfar_lgd_mapper(taluka_code);
create index lgd_mapper_ccode_idx on mhror_master.eferfar_lgd_mapper(ccode);
create index lgd_mapper_mult_idx on mhror_master.eferfar_lgd_mapper(district_code, taluka_code, ccode);

create index land_revenue_demand_district_code_idx on public.land_revenue_demand(district_code);
create index land_revenue_demand_taluka_code_idx on public.land_revenue_demand(taluka_code);
create index land_revenue_demand_ccode_idx on public.land_revenue_demand(ccode);
create index land_revenue_demand_khata_no_idx on public.land_revenue_demand(khata_no);
create index land_revenue_demand_revenue_year_idx on public.land_revenue_demand(revenue_year);
create index land_revenue_demand_mult_idx on public.land_revenue_demand(district_code, taluka_code, ccode, khata_no, revenue_year);

create index ote_district_code_idx on public.lr_pending_dues(district_code);
create index ote_taluka_code_idx on public.lr_pending_dues(taluka_code);
create index ote_ccode_idx on public.lr_pending_dues(ccode);
create index ote_khata_no_idx on public.lr_pending_dues(khata_no);
create index ote_revenue_year_idx on public.lr_pending_dues(revenue_year);
create index ote_mult_idx on public.lr_pending_dues(district_code, taluka_code, ccode, khata_no, revenue_year);

create index form17_district_code_idx on public.echawdi_form17(district_code);
create index form17_taluka_code_idx on public.echawdi_form17(taluka_code);
create index form17_ccode_idx on public.echawdi_form17(ccode);
create index form17_khata_no_idx on public.echawdi_form17(khata_no);
create index form17_revenue_year_idx on public.echawdi_form17(revenue_year);
create index form17_mult_idx on public.echawdi_form17(district_code, taluka_code, ccode, khata_no, revenue_year);
------------------------------------------------

alter table land_revenue_demand drop survey_hissa_no ;
alter table land_revenue_demand drop unit_of_measurement ;

alter table land_revenue_demand 
ALTER COLUMN khata_owner_name  TYPE text;

alter table land_revenue_demand 
ALTER COLUMN survey_hissa  TYPE text;


-------------------------added by bhagyashri lolage---------------------------

ALTER TABLE MHROR_EFERFAR.MUT_KHAREDI  ALTER COLUMN taluka_code TYPE integer USING (taluka_code::integer);

ALTER TABLE MHROR_EFERFAR.MUT_KHAREDI  ALTER COLUMN district_code TYPE integer USING (district_code::integer);

**************************add by Rushi *********************************
 alter table echawdi_form2 alter column period_to type date  USING (period_to::timestamp without time zone);
 alter table echawdi_form2 alter column period_from type date  USING (period_from::timestamp without time zone);
 alter table echawdi_form6d alter column date_of_change type date  USING (date_of_change::timestamp without time zone);
********************************************End*******************************
    
----------------Ashish--=>13-04-2022 10:10

alter table mhror_eferfar.mutregister  alter column district_code type integer  USING (district_code::integer);
alter table mhror_eferfar.mutregister alter column taluka_code type integer  USING (taluka_code::integer);

alter table mhror_eferfar.mut_kharedi_buyer alter column district_code type integer  USING (district_code::integer);
alter table mhror_eferfar.mut_kharedi_buyer alter column taluka_code type integer  USING (taluka_code::integer);


--------------------Ashish ==> To find & update user according village: Ashish @18-04-2022

--Find user by ccode(dont change user_typeif you want talathi login)
select servarthid,password,user_type from mhror_master.fpusers1 where servarthid in(select username from mhror_master.m_officermast where  user_type='1' and ccode='270900090109360000' );

--To update default password of that user by servarthid:
update mhror_master.fpusers1 set password ='0e7517141fb53f21ee439b355b5a1d0a' where servarthid='REVASAM9502'

--Query for to check is record already  exists for form6D  Ashish @22-04-2022
SELECT 
CASE 
WHEN EXISTS
(
SELECT * FROM(
SELECT CCODE,PIN,
CONCAT(
case when pin1 != ' '   then   pin1 end,
case when pin2 != ' '  then  '/'||pin2 end,
case when pin3 != ' '  then  '/'||pin3 end,
case when pin4 != ' '  then  '/'||pin4 end,
case when pin5 != ' '  then  '/'||pin5 end,
case when pin6 != ' '  then  '/'||pin6 end,
case when pin7 != ' '  then  '/'||pin7 end,
case when pin8 != ' '  then  '/'||pin8 end) as hissaNo
FROM ECHAWDI_FORM6D 
WHERE 
CCODE='272500130318050000' 
AND PIN='30'
AND mutation_entry_in_village_form6='14592'
AND ACTIVE_FLAG='Y') A WHERE hissaNo= '1'
)
THEN true
ELSE false 
END




--Ashish 29042022


alter table land_revenue_demand alter column cheque_date type timestamp without time zone  USING (cheque_date::timestamp without time zone);


--@Ashish Dahale 07-05-2022

-- drop role instance from everywhere
drop VIEW users;
alter table mhror_master.fpusers1 drop column role_id;
drop table roles;

--create role again
CREATE TABLE mhror_master.roles
(
  id integer NOT NULL,
  name character varying(20),
  CONSTRAINT roles_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE roles
  OWNER TO postgres;
  
--add role in mhror_master.roles
1;"ROLE_ADMIN"

--add role_id in users
alter table mhror_master.fpusers1 add column role_id bigint references mhror_master.roles
-- update role_id of each user
update mhror_master.fpusers1 SET role_id=1

--create users view 
CREATE OR REPLACE VIEW users AS 
 SELECT u.servarthid,
    u.password,
    u.fullname,
    u.desg,
    u.district_code,
    u.taluka_code,
    u.mobileno,
    u.marathiname,
    u.user_level,
    u.user_status,
    u.order_no,
    u.remarks,
    u.transc_id,
    u.email_id,
    r.name AS rolename,
    d.district_name,
    t.taluka_name,
    u.desg_code
   FROM mhror_master.fpusers1 u,
    mhror_master.m_taluka_census t,
    mhror_master.m_district_census d,
    mhror_master.roles r
  WHERE t.taluka_code = u.taluka_code AND d.district_code = u.district_code AND d.district_code = t.district_code AND r.id = u.role_id;

ALTER TABLE users
  OWNER TO postgres;

alter table if exists mhror_master.roles add column version int4 
update mhror_master.roles SET version=0 
alter table mhror_master.roles ALTER column version SET NOT NULL 

--drop another tables
drop table revenue_year;
drop table menu;
drop table echawdi_cess;
drop table refreshtoken;
drop table token;
drop table todo;



--change datatype of menu columns @Ashish 

alter table mhror_master.menu  alter column id type numeric  USING (id::numeric);
alter table mhror_master.menu  alter column routes type numeric  USING (routes::numeric);
alter table mhror_master.role_menu   alter column menu_id type numeric  USING (menu_id::numeric);





----Ashish alter  11-05-2022

alter table MHROR_EFERFAR.MUTREGISTER alter column district_code type integer  USING (district_code::integer);

alter table MHROR_EFERFAR.MUTREGISTER alter column taluka_code type integer  USING (taluka_code::integer);

-----------Rushikesh Durgude 24-05-2022 (NIC DB)-----------

alter table mhror_eferfar.mut_kharedi alter column district_code type integer  USING (district_code::integer);

alter table mhror_eferfar.mut_kharedi alter column taluka_code type integer  USING (taluka_code::integer);

----------------------------------------------------------------








--check imported form1,1c 1basic 

select  distinct on(ccode) msv.village_name,
(select count(*) as form1 from mhror_eferfar.form1 where ccode=msv.ccode and CAST(taluka_code AS text)=mtc.taluka_code and CAST(district_code AS text)=mdc.district_code ),
(select count(*) as form1basic from mhror_eferfar.form1_basic    where ccode=msv.ccode  and CAST(taluka_code AS text)=mtc.taluka_code and CAST(district_code AS text)=mdc.district_code),
(select count(*) as form1c from mhror_eferfar.form1c   where ccode=msv.ccode  and CAST(taluka_code AS text)=mtc.taluka_code and CAST(district_code AS text)=mdc.district_code),
mdc.district_name,mtc.taluka_name
from 
mhror_master.m_village_census msv, MHROR_MASTER.M_DISTRICT_CENSUS mdc,MHROR_MASTER.M_TALUKA_CENSUS mtc
where msv.district_code=mdc.district_code and msv.taluka_code=mtc.taluka_code
	