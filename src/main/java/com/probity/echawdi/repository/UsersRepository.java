//package com.probity.echawdi.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.probity.echawdi.entity.Users;
//
///**
// * @author Ashish Dahale
// *
// */
//@Repository
//public interface UsersRepository extends JpaRepository<Users, Long> {
//
//    Optional<Users> findByServarthId(String servarthId);
//    
//    @Query(value="select  "
//    		+ "servarthid,fullname,email_id,district_code, "
//    		+ "taluka_code, "
//    		+ "( "
//    		+ "select taluka_name from dblink ( "
//    		+ "'dbname='|| 'linkgedb_emuroll' ||' host='|| '10.10.248.168' ||' port=5432 user='||'echawadi'||' password='||'echwadi@Nic22','select district_code,taluka_code,taluka_name from '|| 'rcis_uni' || '.m_taluka_census t') "
//    		+ "AS F7 (district_code text,taluka_code text,taluka_name text) "
//    		+ "where trim(taluka_code)=trim(a.taluka_code) and trim(district_code)=trim(a.district_code) "
//    		+ ")taluka_name, "
//    		+ "marathiname,desg,password "
//    		+ "from "
//    		+ "( "
//    		+ "select * from dblink ( "
//    		+ "'dbname='|| 'linkgedb_emuroll' ||' host='|| '10.10.248.168' ||' port=5432 user='||'echawadi'||' password='||'echwadi@Nic22', "
//    		+ "'select servarthid,fullname,email_id,district_code,taluka_code,marathiname,desg,password from '|| 'rcis_uni' || '.fpusers1 f') "
//    		+ "AS F7 (servarthid text,fullname text,email_id text,district_code text,taluka_code text,marathiname text,desg text,password text) "
//    		+ "where trim(servarthid)=?1  "
//    		+ ")a"
//    		,nativeQuery = true)
//    List<Object[]> getUser(String servarthid);
////    @Query(value="select  "
////    		+ "servarthid,fullname,email_id,district_code, "
////    		+ "taluka_code, "
////    		+ "( "
////    		+ "select taluka_name from dblink ( "
////    		+ "'dbname='|| 'linkgedb_emuroll' ||' host='|| 'localhost' ||' port=5432 user='||'postgres'||' password='||'root','select district_code,taluka_code,taluka_name from '|| 'rcis_uni' || '.m_taluka_census t') "
////    		+ "AS F7 (district_code text,taluka_code text,taluka_name text) "
////    		+ "where trim(taluka_code)=trim(a.taluka_code) and trim(district_code)=trim(a.district_code) "
////    		+ ")taluka_name, "
////    		+ "marathiname,desg,password "
////    		+ "from "
////    		+ "( "
////    		+ "select * from dblink ( "
////    		+ "'dbname='|| 'linkgedb_emuroll' ||' host='|| 'localhost' ||' port=5432 user='||'postgres'||' password='||'root', "
////    		+ "'select servarthid,fullname,email_id,district_code,taluka_code,marathiname,desg,password from '|| 'rcis_uni' || '.fpusers1 f') "
////    		+ "AS F7 (servarthid text,fullname text,email_id text,district_code text,taluka_code text,marathiname text,desg text,password text) "
////    		+ "where trim(servarthid)=?1  "
////    		+ ")a"
////    		,nativeQuery = true)
////    List<Object[]> getUser(String servarthid);
//}