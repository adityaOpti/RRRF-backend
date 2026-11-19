package com.probity.echawdi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.probity.echawdi.entity.MutRegisterEntity;

@Repository
public interface MutRegisterRepository extends JpaRepository<MutRegisterEntity,Integer> {
	
	/*@Query(value =
		    "SELECT mut_no, mut_date, isobjection FROM public.dblink(" +
		    "'dbname=' || 'mhrorrai' || ' host=' ||  'localhost' || ' port=' || '5432' || ' user=' || 'postgres' || ' password=' || 'root', " +
		    "'SELECT mut_no, mut_date, isobjection FROM ' || 'mhrorrai_tal' || '.mutregister WHERE trim(ccode) = ''' || '272400110296420000' || '''') " +
		    "AS t(mut_no integer, mut_date date, isobjection boolean)", nativeQuery = true)
	 List<Object[]> FetchAllAdeshNumberandMutationDate(
		    String fhost,
		    String fDbname,
		    String fSchemaName,
		    String fuser,
		    String fpassword,
		    String ccode,
		    String port
		);*/
	
	
	@Query(value = "SELECT mut_no, mut_date " +
            "FROM public.dblink(" +
            "  'dbname=' || 'mhrorrai' || " +
            "  ' host=' || 'localhost' || " +
            "  ' port=' || '5432' || " +
            "  ' user=' || 'postgres' || " +
            "  ' password=' || 'root'," +
            "  'SELECT mr.mut_no, mr.mut_date ' || " +
            "  'FROM mhrorrai_tal.mutregister mr ' || " +
            "  'JOIN mhrorrai_tal.m_mutname mn ON mr.mut_type = mn.mut_type ' || " +
            "  'WHERE trim(mr.ccode) = ''' || '272400110296420000' || ''' ' || " +
            "  'AND mn.mut_type IN (51,53,56,57,58,100,101)'" +
            ") AS t(mut_no integer, mut_date date )", 
    nativeQuery = true)
	 List<Object[]> FetchAllAdeshNumberandMutationDates(
			    String fhost,
			    String fDbname,
			    String fSchemaName,
			    String fuser,
			    String fpassword,
			    String ccode,
			    String port
			);
	 
	 //Dynamic for Adesh

//	 @Query(value = "SELECT mut_no, mut_date  " +
//	            "FROM public.dblink(" +
//	            "  'dbname=' || 'mhrorrai' || " +
//	            "  ' host=' || 'localhost' || " +
//	            "  ' port=' || '5432' || " +
//	            "  ' user=' || 'postgres' || " +
//	            "  ' password=' || 'root'," +
//	            "  'SELECT mr.mut_no, mr.mut_date ' || " +
//	            "  'FROM mhrorrai_tal.mutregister mr ' || " +
//	            "  'JOIN mhrorrai_tal.m_mutname mn ON mr.mut_type = mn.mut_type ' || " +
//	            "  'WHERE trim(mr.ccode) = ''' || '272400110296420000' || ''' ' || " +
//	            "  'AND mn.mut_type IN (51,53,56,57,58,100,101)'" +
//	            ") AS t(mut_no integer, mut_date date)", 
//	    nativeQuery = true)
//		 List<Object[]> FetchAllAdeshNumberandMutationDate(
//				    String fhost,
//				    String fDbname,
//				    String fSchemaName,
//				    String fuser,
//				    String fpassword,
//				    String ccode,
//				    String port
//				);
	 
	 
//	 @Query(value = "SELECT mut_no, mut_date  " +
//	            "FROM public.dblink(" +
//	            "  'dbname=' || ?2 || " +
//	            "  ' host=' || ?1 || " +
//	            "  ' port=' || ?7 || " +
//	            "  ' user=' || ?4 || " +
//	            "  ' password=' || ?5," +
//	            "  'SELECT mr.mut_no, mr.mut_date ' || " +
//	            "  'FROM ?3.mutregister mr ' || " +
//	            "  'JOIN ?3.m_mutname mn ON mr.mut_type = mn.mut_type ' || " +
//	            "  'WHERE trim(mr.ccode) = ''' || ?6 || ''' ' || " +
//	            "  'AND mn.mut_type IN (51,53,56,57,58,100,101)'" +
//	            ") AS t(mut_no integer, mut_date date)", 
//	    nativeQuery = true)
//		 List<Object[]> FetchAllAdeshNumberandMutationDate(
//				    String fhost,
//				    String fDbname,
//				    String fSchemaName,
//				    String fuser,
//				    String fpassword,
//				    String ccode,
//				    String port
//				    
//				);
	 
//	 
//	 @Query(value = "SELECT mut_no, mut_date " +
//             "FROM public.dblink(" +
//             "  'dbname=' || ?2 || " +
//             "  ' host=' || ?1 || " +
//             "  ' port=' || ?7 || " +
//             "  ' user=' || ?4 || " +
//             "  ' password=' || ?5," +
//             "  'SELECT mr.mut_no, mr.mut_date ' || " +
//             "  'FROM ' || ?3 || '.mutregister mr ' || " +  
//             "  'JOIN ' || ?3 || '.m_mutname mn ON mr.mut_type = mn.mut_type ' || " + 
//             "  'WHERE trim(mr.ccode) = ''' || ?6 || ''' ' || " +
//             "  'AND mn.mut_type IN (51,53,56,57,58,100,101)'" +
//             ") AS t(mut_no integer, mut_date date)", 
//      nativeQuery = true)
//List<Object[]> FetchAllAdeshNumberandMutationDate(
//      String fhost,
//      String fDbname,
//      String fSchemaName, 
//      String fuser,
//      String fpassword,
//      String ccode,
//      String port
//);

@Query(value =
"SELECT t.MUT_NO, t.mut_type, t.mut_date, t.SURVEY_HISSA " +
"FROM public.dblink( " +
"  'dbname=' || ?2 || " +
"  ' host=' || ?1 || " +
"  ' port=' || ?7 || " +
"  ' user=' || ?4 || " +
"  ' password=' || ?5, " +
"  'SELECT MR.MUT_NO, MN.mut_type, MR.mut_date, " +
"   STRING_AGG(DISTINCT CONCAT(" +
"     MK.PIN, ''/'', MK.PIN1, ''/'', MK.PIN2, ''/'', MK.PIN3, ''/'', " +
"     MK.PIN4, ''/'', MK.PIN5, ''/'', MK.PIN6, ''/'', MK.PIN7, ''/'', MK.PIN8" +
"   ), '','') AS SURVEY_HISSA " +
"   FROM ' || ?3 || '.MUTREGISTER MR " +
"   JOIN ' || ?3 || '.M_MUTNAME MN ON MR.mut_type = MN.mut_type " +
"   JOIN ' || ?3 || '.MUT_KHAREDI MK ON MR.MUT_NO = MK.MUT_NO AND MR.CCODE = MK.CCODE " +
"   WHERE trim(MR.CCODE) = ''' || ?6 || ''' " +
" AND MN.mut_type IN (51,53,56,57,58,100,101)" +

"   GROUP BY MR.MUT_NO, MN.mut_type, MR.mut_date' " +
") AS t(MUT_NO integer, mut_type integer, mut_date date, SURVEY_HISSA text)",
nativeQuery = true
)
List<Object[]> FetchAllAdeshNumberandMutationDate(
    String host,        
    String dbName,      
    String schema,      
    String user,        
    String password,    
    String ccode,       
    String port         
);
	 
	 //Itar for kalam
	 @Query(value = "SELECT mut_no, mut_date  " +
	            "FROM public.dblink(" +
	            "  'dbname=' || 'mhrorrai' || " +
	            "  ' host=' || 'localhost' || " +
	            "  ' port=' || '5432' || " +
	            "  ' user=' || 'postgres' || " +
	            "  ' password=' || 'root'," +
	            "  'SELECT mr.mut_no, mr.mut_date ' || " +
	            "  'FROM mhrorrai_tal.mutregister mr ' || " +
	            "  'JOIN mhrorrai_tal.m_mutname mn ON mr.mut_type = mn.mut_type ' || " +
	            "  'WHERE trim(mr.ccode) = ''' || '272400110296420000' || ''' ' || " +
	            "  'AND mn.mut_type IN (103,108,102,109,111,112,110,117)'" +
	            ") AS t(mut_no integer, mut_date date)", 
	    nativeQuery = true)
		 List<Object[]> FetchAllAdeshNumberandMutationDateForKalams(
				    String fhost,
				    String fDbname,
				    String fSchemaName,
				    String fuser,
				    String fpassword,
				    String ccode,
				    String port
				);
		 
		 //dynamic for kalam
//		 @Query(value = "SELECT mut_no, mut_date " +
//		            "FROM public.dblink(" +
//		            "  'dbname=' || 'mhrorrai' || " +
//		            "  ' host=' || 'localhost' || " +
//		            "  ' port=' || '5432' || " +
//		            "  ' user=' || 'postgres' || " +
//		            "  ' password=' || 'root'," +
//		            "  'SELECT mr.mut_no, mr.mut_date ' || " +
//		            "  'FROM mhrorrai_tal.mutregister mr ' || " +
//		            "  'JOIN mhrorrai_tal.m_mutname mn ON mr.mut_type = mn.mut_type ' || " +
//		            "  'WHERE trim(mr.ccode) = ''' || '272400110296420000' || ''' ' || " +
//		            "  'AND mn.mut_type IN (103,108,102,109,111,112,110,117)'" +
//		            ") AS t(mut_no integer, mut_date date )", 
//		    nativeQuery = true)
//		 List<Object[]> FetchAllAdeshNumberandMutationDateForKalam(
//				    String fhost,
//				    String fDbname,
//				    String fSchemaName,
//				    String fuser,
//				    String fpassword,
//				    String ccode,
//				    String port
//				);
		 
		 
//		 
//		 @Query(value = "SELECT mut_no, mut_date " +
//	               "FROM public.dblink(" +
//	               "  'dbname=' || ?2 || " +
//	               "  ' host=' || ?1 || " +
//	               "  ' port=' || ?7 || " +
//	               "  ' user=' || ?4 || " +
//	               "  ' password=' || ?5," +
//	               "  'SELECT mr.mut_no, mr.mut_date ' || " +
//	               "  'FROM ' || ?3 || '.mutregister mr ' || " +  
//	               "  'JOIN ' || ?3 || '.m_mutname mn ON mr.mut_type = mn.mut_type ' || " + 
//	               "  'WHERE trim(mr.ccode) = ''' || ?6 || ''' ' || " +
//	               "  'AND mn.mut_type IN (103,108,102,109,111,112,110,117)'" +
//	               ") AS t(mut_no integer, mut_date date)", 
//	        nativeQuery = true)
//		 List<Object[]> FetchAllAdeshNumberandMutationDateForKalam(
//				    String fhost,
//				    String fDbname,
//				    String fSchemaName,
//				    String fuser,
//				    String fpassword,
//				    String ccode,
//				    String port
//				);
		 

@Query(value =
"SELECT t.MUT_NO, t.mut_type, t.mut_date, t.SURVEY_HISSA " +
"FROM public.dblink( " +
"  'dbname=' || ?2 || " +
"  ' host=' || ?1 || " +
"  ' port=' || ?7 || " +
"  ' user=' || ?4 || " +
"  ' password=' || ?5, " +
"  'SELECT MR.MUT_NO, MN.mut_type, MR.mut_date, " +
"   STRING_AGG(DISTINCT CONCAT(" +
"     MK.PIN, ''/'', MK.PIN1, ''/'', MK.PIN2, ''/'', MK.PIN3, ''/'', " +
"     MK.PIN4, ''/'', MK.PIN5, ''/'', MK.PIN6, ''/'', MK.PIN7, ''/'', MK.PIN8" +
"   ), '','') AS SURVEY_HISSA " +
"   FROM ' || ?3 || '.MUTREGISTER MR " +
"   JOIN ' || ?3 || '.M_MUTNAME MN ON MR.mut_type = MN.mut_type " +
"   JOIN ' || ?3 || '.MUT_KHAREDI MK ON MR.MUT_NO = MK.MUT_NO AND MR.CCODE = MK.CCODE " +
"   WHERE trim(MR.CCODE) = ''' || ?6 || ''' " +
 " AND MN.mut_type IN (103,108,102,109,111,112,110,117)" +

"   GROUP BY MR.MUT_NO, MN.mut_type, MR.mut_date' " +
") AS t(MUT_NO integer, mut_type integer, mut_date date, SURVEY_HISSA text)",
nativeQuery = true
)
List<Object[]> FetchAllAdeshNumberandMutationDateForKalam(
    String host,        
    String dbName,      
    String schema,      
    String user,        
    String password,    
    String ccode,       
    String port         
);

//
//		 //static query for itar
//		 @Query(value = "SELECT mut_no, mut_date " +
//		            "FROM public.dblink(" +
//		            "  'dbname=' || 'mhrorrai' || " +
//		            "  ' host=' || 'localhost' || " +
//		            "  ' port=' || '5432' || " +
//		            "  ' user=' || 'postgres' || " +
//		            "  ' password=' || 'root'," +
//		            "  'SELECT mr.mut_no, mr.mut_date ' || " +
//		            "  'FROM mhrorrai_tal.mutregister mr ' || " +
//		            "  'JOIN mhrorrai_tal.m_mutname mn ON mr.mut_type = mn.mut_type ' || " +
//		            "  'WHERE trim(mr.ccode) = ''' || '272400110296420000' || ''' ' || " +
//		            "  'AND mn.mut_type IN (8)'" +
//		            ") AS t(mut_no integer, mut_date date )", 
//		    nativeQuery = true)
//			 List<Object[]> FetchAllAdeshNumberandMutationDateForItars(
//					    String fhost,
//					    String fDbname,
//					    String fSchemaName,
//					    String fuser,
//					    String fpassword,
//					    String ccode,
//					    String port
//					);
@Query(value =
"SELECT t.MUT_NO, t.mut_type, t.mut_date, t.SURVEY_HISSA " +
"FROM public.dblink( " +
"  'dbname=' || ?2 || " +
"  ' host=' || ?1 || " +
"  ' port=' || ?7 || " +
"  ' user=' || ?4 || " +
"  ' password=' || ?5, " +
"  'SELECT MR.MUT_NO, MN.mut_type, MR.mut_date, " +
"   STRING_AGG(DISTINCT CONCAT(" +
"     MK.PIN, ''/'', MK.PIN1, ''/'', MK.PIN2, ''/'', MK.PIN3, ''/'', " +
"     MK.PIN4, ''/'', MK.PIN5, ''/'', MK.PIN6, ''/'', MK.PIN7, ''/'', MK.PIN8" +
"   ), '','') AS SURVEY_HISSA " +
"   FROM ' || ?3 || '.MUTREGISTER MR " +
"   JOIN ' || ?3 || '.M_MUTNAME MN ON MR.mut_type = MN.mut_type " +
"   JOIN ' || ?3 || '.MUT_KHAREDI MK ON MR.MUT_NO = MK.MUT_NO AND MR.CCODE = MK.CCODE " +
"   WHERE trim(MR.CCODE) = ''' || ?6 || ''' " +
//"   AND MN.mut_type IN (8,53,56,57,58,100,101) " +
" AND MN.mut_type NOT IN (8,103,108,102,109,111,112,110,117,51,53,56,57,58,100,101) " +
"   GROUP BY MR.MUT_NO, MN.mut_type, MR.mut_date' " +
") AS t(MUT_NO integer, mut_type integer, mut_date date, SURVEY_HISSA text)",
nativeQuery = true
)
List<Object[]> fetchMutationSurveyHissaForOtherFerfar(
    String host,        // ?1
    String dbName,      // ?2
    String schema,      // ?3
    String user,        // ?4
    String password,    // ?5
    String ccode,       // ?6
    String port         // ?7
);

			 
			 
		//itar Dynamic
//			 @Query(value = "SELECT mut_no, mut_date " +
//				        "FROM public.dblink(" +
//				        "  'dbname=' || :fDbname || " +
//				        "  ' host=' || :fhost || " +
//				        "  ' port=' || :port || " +
//				        "  ' user=' || :fuser || " +
//				        "  ' password=' || :fpassword," +
//				        "  'SELECT mr.mut_no, mr.mut_date " +
//				        "   FROM ' || :fSchemaName || '.mutregister mr " +
//				        "   JOIN ' || :fSchemaName || '.m_mutname mn ON mr.mut_type = mn.mut_type " +
//				        "   WHERE trim(mr.ccode) = ''' || :ccode || ''' " +
//				        "   AND mn.mut_type  IN (8)') " +
//				        "AS t(mut_no integer, mut_date date)", 
//				        nativeQuery = true)
//			 List<Object[]> FetchAllAdeshNumberandMutationDateForItar(
//					    String fhost,
//					    String fDbname,
//					    String fSchemaName,
//					    String fuser,
//					    String fpassword,
//					    String ccode,
//					    String port
//					);

@Query(value = "SELECT t.mut_no, t.mut_date, " +
        "STRING_AGG(DISTINCT CONCAT(" +
        "t.pin, '/', t.pin1, '/', t.pin2, '/', t.pin3, '/', " +
        "t.pin4, '/', t.pin5, '/', t.pin6, '/', t.pin7, '/', t.pin8" +
        "), ',') AS survey_hissa " +

        "FROM public.dblink( " +
        "   'dbname=' || ?2 || " +
        "   ' host=' || ?1 || " +
        "   ' port=' || ?7 || " +
        "   ' user=' || ?4 || " +
        "   ' password=' || ?5, " +

        "   'SELECT mr.mut_no, mr.mut_date, " +
        "           mk.pin, mk.pin1, mk.pin2, mk.pin3, mk.pin4, " +
        "           mk.pin5, mk.pin6, mk.pin7, mk.pin8 " +
        "    FROM ' || ?3 || '.mutregister mr " +
        "    JOIN ' || ?3 || '.mut_kharedi mk " +
        "         ON mr.mut_no = mk.mut_no AND mr.ccode = mk.ccode " +
        "    JOIN ' || ?3 || '.m_mutname mn " +
        "         ON mr.mut_type = mn.mut_type " +
        "    WHERE trim(mr.ccode) = ''' || ?6   || ''' " +
        "      AND mn.mut_type IN (8)' " +
        ") AS t( " +
        "   mut_no INTEGER, " +
        "   mut_date DATE, " +
        "   pin TEXT, pin1 TEXT, pin2 TEXT, pin3 TEXT, pin4 TEXT, " +
        "   pin5 TEXT, pin6 TEXT, pin7 TEXT, pin8 TEXT " +
        ") " +
        "GROUP BY t.mut_no, t.mut_date",
    nativeQuery = true
)
List<Object[]> FetchAllAdeshNumberandMutationDateForItar(
     String fhost,
     String fDbname,
    String fSchemaName,
    String fuser,
     String fpassword,
    String ccode,
     String port
);

			 
			 
			 
			 
			/* @Query(value = "SELECT mut_no, mut_date " +
			            "FROM public.dblink(" +
			            "  'dbname=' || 'mhrorrai' || " +
			            "  ' host=' || 'localhost' || " +
			            "  ' port=' || '5432' || " +
			            "  ' user=' || 'postgres' || " +
			            "  ' password=' || 'root'," +
			            "  'SELECT mr.mut_no, mr.mut_date ' || " +
			            "  'FROM mhrorrai_tal.mutregister mr ' || " +
			            "  'JOIN mhrorrai_tal.m_mutname mn ON mr.mut_type = mn.mut_type ' || " +
			            "  'WHERE trim(mr.ccode) = ''' || '272400110296420000' || ''' ' || " +
			            "  'AND mn.mut_type NOT IN (8,103,108,102,109,111,112,110,117,51,53,56,57,58,100,101)'" +
			            ") AS t(mut_no integer, mut_date date )", 
			    nativeQuery = true)
				 List<Object[]> FetchAllAdeshNumberandMutationDateForOtherFerfarS(
						    String fhost,
						    String fDbname,
						    String fSchemaName,
						    String fuser,	
						    String fpassword,
						    String ccode,
						    String port
						);*/
				 
				 
				 //dynamic FetchAllAdeshNumberandMutationDateForOtherFerfar
				 @Query(value = "SELECT mut_no, mut_date " +
					        "FROM public.dblink(" +
					        "  'dbname=' || :fDbname || " +
					        "  ' host=' || :fhost || " +
					        "  ' port=' || :port || " +
					        "  ' user=' || :fuser || " +
					        "  ' password=' || :fpassword," +
					        "  'SELECT mr.mut_no, mr.mut_date " +
					        "   FROM ' || :fSchemaName || '.mutregister mr " +
					        "   JOIN ' || :fSchemaName || '.m_mutname mn ON mr.mut_type = mn.mut_type " +
					        "   WHERE trim(mr.ccode) = ''' || :ccode || ''' " +
					        "   AND mn.mut_type NOT IN (8,103,108,102,109,111,112,110,117,51,53,56,57,58,100,101)') " +
					        "AS t(mut_no integer, mut_date date)", 
					        nativeQuery = true)
					List<Object[]> FetchAllAdeshNumberandMutationDateForOtherFerfar(
					        @Param("fhost") String fhost,
					        @Param("fDbname") String fDbname,
					        @Param("fSchemaName") String fSchemaName,
					        @Param("fuser") String fuser,
					        @Param("fpassword") String fpassword,
					        @Param("ccode") String ccode,
					        @Param("port") String port
					);

			 

//							
//									@Query(
//								    value = "SELECT DISTINCT t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name " +
//								            "FROM ( " +
//								            "    SELECT ccode, mut_no, mut_date, mut_type, mut_name " +
//								            "    FROM public.dblink( " +
//								            "        'dbname=' || :fDbName || ' host=' || :fHost || ' port=' || :port || ' user=' || :fUser || ' password=' || :fPassword, " +
//								            "        'SELECT ccode, m.mut_no, m.mut_date, m.mut_type, m.mut_name FROM ' || :fSchemaName || '.mutregister m' " +
//								            "    ) AS Fer1 (ccode text, mut_no integer, mut_date date, mut_type integer, mut_name character varying) " +
//								            ") AS t1 " +
//								            "JOIN ( " +
//								            "    SELECT ccode, mut_no, mut_date, mut_type, mut_name, table_name " +
//								            "    FROM public.dblink( " +
//								            "        'dbname=' || :fDbName || ' host=' || :fHost || ' port=' || :port || ' user=' || :fUser || ' password=' || :fPassword, " +
//								            "        'SELECT ccode, mut_no, mut_date, mut_type, mut_name, table_name FROM ' || :fSchemaName || '.mutregister_audit k' " +
//								            "    ) AS a (ccode text, mut_no integer, mut_date date, mut_type integer, mut_name character varying, table_name character varying) " +
//								            ") AS t2 ON t1.ccode = t2.ccode AND t1.mut_no = t2.mut_no " +
//								            "WHERE t1.mut_type <> t2.mut_type " +
//								            "AND t1.mut_date BETWEEN '2024-10-17' AND '2025-07-31' " +
//								            "UNION " +
//								            "SELECT DISTINCT t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name " +
//								            "FROM ( " +
//								            "    SELECT ccode, mut_no, mut_date, mut_type, mut_name, table_name " +
//								            "    FROM public.dblink( " +
//								            "        'dbname=' || :fDbName || ' host=' || :fHost || ' port=' || :port || ' user=' || :fUser || ' password=' || :fPassword, " +
//								            "        'SELECT ccode, m.mut_no, m.mut_date, m.mut_type, m.mut_name, table_name FROM ' || :fSchemaName || '.mutregister m' " +
//								            "    ) AS Fer1 (ccode text, mut_no integer, mut_date date, mut_type integer, mut_name character varying, table_name character varying) " +
//								            ") AS t1 " +
//								            "JOIN ( " +
//								            "    SELECT ccode, mut_no, mut_date, mut_type, mut_name, table_name " +
//								            "    FROM public.dblink( " +
//								            "        'dbname=' || :fDbName || ' host=' || :fHost || ' port=' || :port || ' user=' || :fUser || ' password=' || :fPassword, " +
//								            "        'SELECT ccode, mut_no, mut_date, mut_type, mut_name, table_name FROM ' || :fSchemaName || '.mutregister_audit k' " +
//								            "    ) AS a (ccode text, mut_no integer, mut_date date, mut_type integer, mut_name character varying, table_name character varying) " +
//								            ") AS t2 ON t1.ccode = t2.ccode AND t1.mut_no = t2.mut_no " +
//								            "WHERE t2.table_name = 'SRO' AND t1.table_name <> 'SRO' " +
//								            "AND t1.mut_date BETWEEN '2024-10-17' AND '2025-07-31' ",
//								    nativeQuery = true
//								)
//								List<Object[]> fetchReEntryFerfarDetails(
//								    @Param("fHost") String fHost,
//								    @Param("fDbName") String fDbName,
//								    @Param("fSchemaName") String fSchemaName,
//								    @Param("fUser") String fUser,
//								    @Param("fPassword") String fPassword,
//								    @Param("port") String port
//								);
					
					@Query(value ="SELECT t.mut_no, t.mut_date, t.mut_type, t.mut_name, t.survey_hissa " +
					        "FROM public.dblink( " +
					        "  'dbname=' || ?2 || " +
					        "  ' host=' || ?1 || " +
					        "  ' port=' || ?6 || " +
					        "  ' user=' || ?4 || " +
					        "  ' password=' || ?5, " +

					        "  'SELECT DISTINCT t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name, " +
					        "   STRING_AGG(DISTINCT CONCAT(t1.pin, ''/'', t1.pin1, ''/'', t1.pin2, ''/'', t1.pin3, ''/'', " +
					        "   t1.pin4, ''/'', t1.pin5, ''/'', t1.pin6, ''/'', t1.pin7, ''/'', t1.pin8), '','') " +

					        "   FROM ( " +
					        "       SELECT m.ccode, m.mut_no, m.mut_date, m.mut_type, m.mut_name, " +
					        "              mk.pin, mk.pin1, mk.pin2, mk.pin3, mk.pin4, mk.pin5, mk.pin6, mk.pin7, mk.pin8 " +
					        "       FROM ' || ?3 || '.mutregister m, ' || ?3 || '.mut_kharedi mk " +
					        "       WHERE m.mut_no = mk.mut_no AND m.ccode = mk.ccode " +
					        "   ) t1 " +

					        "   JOIN ( " +
					        "       SELECT ccode, mut_no, mut_date, mut_type, mut_name, table_name " +
					        "       FROM ' || ?3 || '.mutregister_audit " +
					        "   ) t2 ON t1.ccode = t2.ccode AND t1.mut_no = t2.mut_no " +

					        "   WHERE t1.mut_type <> t2.mut_type " +
					        "   AND t1.mut_date BETWEEN ''2024-10-17'' AND ''2025-07-31'' " +
					        "   GROUP BY t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name " +

					        "   UNION " +

					        "   SELECT DISTINCT t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name, " +
					        "   STRING_AGG(DISTINCT CONCAT(t1.pin, ''/'', t1.pin1, ''/'', t1.pin2, ''/'', t1.pin3, ''/'', " +
					        "   t1.pin4, ''/'', t1.pin5, ''/'', t1.pin6, ''/'', t1.pin7, ''/'', t1.pin8), '','') " +

					        "   FROM ( " +
					        "       SELECT m.ccode, m.mut_no, m.mut_date, m.mut_type, m.mut_name, m.table_name, " +
					        "              mk.pin, mk.pin1, mk.pin2, mk.pin3, mk.pin4, mk.pin5, mk.pin6, mk.pin7, mk.pin8 " +
					        "       FROM ' || ?3 || '.mutregister m, ' || ?3 || '.mut_kharedi mk " +
					        "       WHERE m.mut_no = mk.mut_no AND m.ccode = mk.ccode " +
					        "   ) t1 " +

					        "   JOIN ( " +
					        "       SELECT ccode, mut_no, mut_date, mut_type, mut_name, table_name " +
					        "       FROM ' || ?3 || '.mutregister_audit " +
					        "   ) t2 ON t1.ccode = t2.ccode AND t1.mut_no = t2.mut_no " +

					        "   WHERE t2.table_name = ''SRO'' " +
					        "   AND t1.table_name <> ''SRO'' " +
					        "   AND t1.mut_date BETWEEN ''2024-10-17'' AND ''2025-07-31'' " +
					        "   GROUP BY t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name' " +
					        ") AS t(mut_no integer, mut_date date, mut_type integer, mut_name varchar, survey_hissa text)",
					    nativeQuery = true
					)
					List<Object[]> fetchReEntryFerfarDetails(
					    String host,     
					    String dbName,   
					    String schema,   
					    String user,     
					    String password, 
					    String port    
					);

	
			
//								@Query(value = "SELECT application_id, district_code, taluka_code, ccode, application_date, status_msg, status_code, rej_reason " +
//								        "FROM public.dblink(" +
//								        "  'dbname=' || :fDbname || " +
//								        "  ' host=' || :fhost || " +
//								        "  ' port=' || :port || " +
//								        "  ' user=' || :fuser || " +
//								        "  ' password=' || :fpassword, " +
//								        "  'SELECT district_code, taluka_code, ccode, application_date, application_id, status_msg, status_code, rej_reason " +
//								        "   FROM ' || :fSchemaName || '.pde_application " +
//								        "   WHERE district_code = '''24''' AND taluka_code = '''11''' " +
//								        "   AND status_msg = '''Rejected''' AND status_code = '''3'''') " +
//								        "AS a (district_code text, taluka_code text, ccode text, application_date date, " +
//								        "      application_id bigint, status_msg character varying, status_code character, rej_reason character varying)",
//								        nativeQuery = true)
//								List<Object[]> fetchEhkkaInpsection(@Param("fDbname") String dbname,
//								                                                  @Param("fhost") String host,
//								                                                  @Param("port") String port,
//								                                                  @Param("fuser") String user,
//								                                                  @Param("fpassword") String password,
//								                                                  @Param("fSchemaName") String schemaName);
//								
//								
//								@Query(value = "SELECT application_id, application_date, rej_reason, district_code, taluka_code, ccode, status_msg, status_code " +
//								        "FROM public.dblink(" +
//								        "  'dbname=' || 'pdedb_lr' || " +
//								        "  ' host=' || 'localhost' || " +
//								        "  ' port=' || '5432' || " +
//								        "  ' user=' || 'postgres' || " +
//								        "  ' password=' || :fpassword," +
//								        "  $$SELECT application_id, application_date, rej_reason, district_code, taluka_code, ccode,status_msg, status_code " +
//								        "    FROM mhror_pde.pde_application " +
//								        "    WHERE district_code = '24' AND taluka_code = '11' " +
//								        "    AND status_msg = 'Rejected' AND status_code = '3'$$" +
//								        ") AS a (" +
//								        "   application_id bigint, application_date date, rej_reason character varying, district_code text, taluka_code text, ccode text,  " +
//								        "  status_msg character varying, status_code character " +
//								        ")",
//								        nativeQuery = true)
//								List<Object[]> fetchEhkkaInpsectionS(
//										
//										);

								
								
		@Query(value = "SELECT application_id, application_date, rej_reason, district_code, taluka_code, ccode, status_msg, status_code " +
		 "FROM public.dblink(" +
		 "  'dbname=' || 'pdedb_lr' || " +
		 "  ' host=' || ?1 || " +
		 "  ' port=' || ?4 || " +
		 "  ' user=' || ?2 || " +
		 "  ' password=' || ?3," +
		 "  $$SELECT application_id, application_date, rej_reason, district_code, taluka_code, ccode,status_msg, status_code " +
		 "    FROM mhror_pde.pde_application " +
		 "    WHERE district_code = '9' AND taluka_code = '13' " +
		 "    AND status_msg = 'Rejected' AND status_code = '3'$$" +
		 ") AS a (" +
		 "   application_id bigint, application_date date, rej_reason character varying, district_code text, taluka_code text, ccode text,  " +
		 "  status_msg character varying, status_code character " +
			")",
		 nativeQuery = true)
		List<Object[]> fetchEhkkaInpsection(
										   String host,
	                                       String user,
	                                       String password,
	                                       String port
                                         
                                         );

								
								
		
		//Static query for tantrik
		 @Query(value = "SELECT t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name, t2.or_code1, t2.or_code4 " +
		            "FROM ( " +
		            "   SELECT ccode, mut_no, mut_date, mut_type, mut_name, circle_remark " +
		            "   FROM public.dblink( " +
		            "       'dbname=' || 'mhrorrai' || ' host=' || 'localhost' || ' port=' || '5432' || " +
		            "       ' user=' || 'postgres' || ' password=' || 'root', " +
		            "       'SELECT ccode, m.mut_no, m.mut_date, m.mut_type, m.mut_name, m.circle_remark " +
		            "        FROM ' || 'mhrorrai_tal' || '.mutregister m " +
		            "        WHERE (circle_remark LIKE ''%पूर्वावलोकन%'' OR circle_remark LIKE ''%तांत्रिक%'') " +
		            "        AND mut_date::date BETWEEN ''2024-10-17'' AND ''2025-07-31'' ' " +
		            "   ) AS Fer1(ccode text, mut_no integer, mut_date date, mut_type integer, mut_name varchar, circle_remark varchar) " +
		            ") AS t1 " +
		            "JOIN ( " +
		            "   SELECT ccode, mut_no, or_code1, or_code4 " +
		            "   FROM public.dblink( " +
		            "       'dbname=' || 'mhrorrai' || ' host=' || 'localhost' || ' port=' || '5432' || " +
		            "       ' user=' || 'postgres' || ' password=' || 'root', " +
		            "       'SELECT k.ccode, k.mut_no, k.or_code1, k.or_code4 " +
		            "        FROM ' || 'mhrorrai_tal' || '.mut_kharedi k " +
		            "        WHERE or_code1 = 4 AND or_code4 = 2 ' " +
		            "   ) AS a(ccode text, mut_no integer, or_code1 integer, or_code4 integer) " +
		            ") AS t2 " +
		            "ON t1.ccode = t2.ccode AND t1.mut_no = t2.mut_no",
		            nativeQuery = true)
		    List<Object[]> fetchalltantrikMutationDatas(
		    		String fhost,
				    String fDbname,
				    String fSchemaName,
				    String fuser,
				    String fpassword,
				    String ccode,
				    String port
				);
		    
		    
		    //Dynamic
//		    @Query(value = "SELECT t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name, t2.or_code1, t2.or_code4 " +
//		            "FROM ( " +
//		            "   SELECT ccode, mut_no, mut_date, mut_type, mut_name, circle_remark " +
//		            "   FROM public.dblink( " +
//		            "       'dbname=' || ?2 || ' host=' || ?1 || ' port=' ||  '5432'|| " +
//		            "       ' user=' || ?4 || ' password=' || ?5, " +
//		            "       'SELECT ccode, m.mut_no, m.mut_date, m.mut_type, m.mut_name, m.circle_remark " +
//		            "        FROM ' || ?3 || '.mutregister m " +
//		            "        WHERE (circle_remark LIKE ''%पूर्वावलोकन%'' OR circle_remark LIKE ''%तांत्रिक%'') " +
//		            "        AND mut_date::date BETWEEN ''2024-10-17'' AND ''2025-07-31'' ' " +
//		            "   ) AS Fer1(ccode text, mut_no integer, mut_date date, mut_type integer, mut_name varchar, circle_remark varchar) " +
//		            ") AS t1 " +
//		            "JOIN ( " +
//		            "   SELECT ccode, mut_no, or_code1, or_code4 " +
//		            "   FROM public.dblink( " +
//		            "       'dbname=' || ?2 || ' host=' || ?1 || ' port=' ||  '5432' || " +
//		            "       ' user=' || ?4 || ' password=' || ?5, " +
//		            "       'SELECT k.ccode, k.mut_no, k.or_code1, k.or_code4 " +
//		            "        FROM ' || ?3 || '.mut_kharedi k " +
//		            "        WHERE or_code1 = 4 AND or_code4 = 2 ' " +
//		            "   ) AS a(ccode text, mut_no integer, or_code1 integer, or_code4 integer) " +
//		            ") AS t2 " +
//		            "ON t1.ccode = t2.ccode AND t1.mut_no = t2.mut_no",
//		            nativeQuery = true)
//		    List<Object[]> fetchalltantrikMutationData(
//		    		String fhost,
//				    String fDbname,
//				    String fSchemaName,
//				    String fuser,
//				    String fpassword,
//				    String ccode,
//				    String port
//				);
		    

		    @Query(value =
		    		"SELECT t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name, t2.or_code1, t2.or_code4, " +
		    		" STRING_AGG(DISTINCT CONCAT(t2.PIN, '/', t2.PIN1, '/', t2.PIN2, '/', t2.PIN3, '/', " +
		    		" t2.PIN4, '/', t2.PIN5, '/', t2.PIN6, '/', t2.PIN7, '/', t2.PIN8), ',') AS SURVEY_HISSA " +
		    		" FROM ( " +
		    		"   SELECT ccode, mut_no, mut_date, mut_type, mut_name " +
		    		"   FROM public.dblink( " +
		    		"     'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?7 || " +
		    		"     ' user=' || ?4 || ' password=' || ?5, " +
		    		"     'SELECT ccode, m.mut_no, m.mut_date, m.mut_type, m.mut_name " +
		    		"      FROM ' || ?3 || '.mutregister m " +
		    		"      WHERE (circle_remark LIKE ''%पूर्वावलोकन%'' OR circle_remark LIKE ''%तांत्रिक%'') " +
		    		"      AND ccode = ''' || ?6 || ''' " +
		    		"      AND mut_date::date BETWEEN ''2024-10-17'' AND ''2025-07-31'' ' " +
		    		"   ) AS Fer1(ccode text, mut_no integer, mut_date date, mut_type integer, mut_name varchar) " +
		    		" ) AS t1 " +
		    		" JOIN ( " +
		    		"   SELECT ccode, mut_no, or_code1, or_code4, pin, pin1, pin2, pin3, pin4, pin5, pin6, pin7, pin8 " +
		    		"   FROM public.dblink( " +
		    		"     'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?7 || " +
		    		"     ' user=' || ?4 || ' password=' || ?5, " +
		    		"     'SELECT k.ccode, k.mut_no, k.or_code1, k.or_code4, k.pin, k.pin1, k.pin2, k.pin3, k.pin4, k.pin5, k.pin6, k.pin7, k.pin8 " +
		    		"      FROM ' || ?3 || '.mut_kharedi k " +
		    		"      WHERE k.or_code1 = 4 AND k.or_code4 = 2 " +
		    		"      AND k.ccode = ''' || ?6 || ''' ' " +
		    		"   ) AS a(ccode text, mut_no integer, or_code1 integer, or_code4 integer, pin text, pin1 text, pin2 text, pin3 text, pin4 text, pin5 text, pin6 text, pin7 text, pin8 text) " +
		    		" ) AS t2 " +
		    		" ON t1.ccode = t2.ccode AND t1.mut_no = t2.mut_no " +
		    		" GROUP BY t1.mut_no, t1.mut_date, t1.mut_type, t1.mut_name, t2.or_code1, t2.or_code4",
		    		nativeQuery = true)
		    		List<Object[]> fetchalltantrikMutationData(
		    		    String fhost,        
		    		    String fDbname,      
		    		    String fSchemaName,  
		    		    String fuser,        
		    		    String fpassword,    
		    		    String ccode,        
		    		    String port         
		    		);

		    
		    
		    
//			
//		    @Query(value = "SELECT mut_no, stay_order_date, stay_remark " +
//		            "FROM public.dblink( " +
//		            "  'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?7 || " +
//		            "  ' user=' || ?4 || ' password=' || ?5, " +
//		            "  'SELECT mut_no, stay_order_date, stay_remark FROM ' || ?3 || '.tbl_stay_on_objection " +
//		            "   WHERE ccode = ''' || ?6 || ''' ' " +  // include ccode to fix positional gap
//		            ") AS t(mut_no integer, stay_order_date date, stay_remark varchar)",
//		            nativeQuery = true)
//		    List<Object[]> fetchAllStayFerfarMutationData(
//		            String fhost,       // ?1
//		            String fDbname,     // ?2
//		            String fSchemaName, // ?3
//		            String fuser,       // ?4
//		            String fpassword,   // ?5
//		            String ccode,       // ?6
//		            String port         // ?7
//		    );
		    		
		    		@Query(value ="SELECT t.mut_no, t.stay_order_date, t.stay_remark, " +
							"STRING_AGG(DISTINCT CONCAT(t.pin, '/', t.pin1, '/', t.pin2, '/', t.pin3, '/', " +
							"t.pin4, '/', t.pin5, '/', t.pin6, '/', t.pin7, '/', t.pin8), ',') AS survey_number " +
							"FROM public.dblink( " +
							"  'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?7 || " +
							"  ' user=' || ?4 || ' password=' || ?5, " +
							"  'SELECT so.mut_no, so.stay_order_date, so.stay_remark, mr.ccode, " +
							"          mk.pin, mk.pin1, mk.pin2, mk.pin3, mk.pin4, " +
							"          mk.pin5, mk.pin6, mk.pin7, mk.pin8 " +
							"   FROM ' || ?3 || '.tbl_stay_on_objection so " +
							"   JOIN ' || ?3 || '.mutregister mr " +
							"        ON so.mut_no = mr.mut_no " +
							"   LEFT JOIN ' || ?3 || '.mut_kharedi mk " +
							"        ON mr.mut_no = mk.mut_no AND mr.ccode = mk.ccode " +
							"   WHERE mr.ccode = ''' || ?6 || ''' ' " +
							") AS t( " +
							"   mut_no INTEGER, " +
							"   stay_order_date DATE, " +
							"   stay_remark VARCHAR, " +
							"   ccode TEXT, " +
							"   pin TEXT, pin1 TEXT, pin2 TEXT, pin3 TEXT, pin4 TEXT, " +
							"   pin5 TEXT, pin6 TEXT, pin7 TEXT, pin8 TEXT " +
							") " +
							"GROUP BY t.mut_no, t.stay_order_date, t.stay_remark",
							nativeQuery = true
							)
							List<Object[]> fetchAllStayFerfarMutationData(
							    String fhost,       
							    String fDbname,    
							    String fSchemaName, 
							    String fuser,       
							    String fpassword,   
							    String ccode,       
							    String port        
							);
					
			
		    
//		    @Query(value =
//				    "select T4.ccode, T4.mut_type, T4.mut_no, T4.mut_date, T4.mut_name from ( " +
//				    "    select T2.mut_no, T1.ccode from ( " +
//				    "        select ccode, pin, pin1, pin2, pin3, pin4, pin5, pin6, pin7, pin8, tenure_code " +
//				    "        from public.dblink( " +
//				    "            'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?6 || ' user=' || ?4 || ' password=' || ?5, " +
//				    "            'select f7.ccode, f7.pin, f7.pin1, f7.pin2, f7.pin3, f7.pin4, f7.pin5, f7.pin6, f7.pin7, f7.pin8, f7.tenure_code " +
//				    "             from ' || ?3 || '.form7 f7 where f7.tenure_code = ''2'' or (f7.tenure_code = ''1'' and f7.tenure_sub_code != ''0'') and marked != ''Y''' " +
//				    "        ) AS F7K( " +
//				    "            ccode text, pin text, pin1 text, pin2 text, pin3 text, pin4 text, pin5 text, pin6 text, pin7 text, pin8 text, tenure_code integer " +
//				    "        ) " +
//				    "    ) AS T1 " +
//				    "    join ( " +
//				    "        select ccode, pin, pin1, pin2, pin3, pin4, pin5, pin6, pin7, pin8, mut_no " +
//				    "        from public.dblink( " +
//				    "            'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?6 || ' user=' || ?4 || ' password=' || ?5, " +
//				    "            'select k.ccode, k.pin, k.pin1, k.pin2, k.pin3, k.pin4, k.pin5, k.pin6, k.pin7, k.pin8, k.mut_no " +
//				    "             from ' || ?3 || '.mut_kharedi k' " +
//				    "        ) AS F7K( " +
//				    "            ccode text, pin text, pin1 text, pin2 text, pin3 text, pin4 text, pin5 text, pin6 text, pin7 text, pin8 text, mut_no integer " +
//				    "        ) " +
//				    "    ) AS T2 " +
//				    "    on T1.ccode = T2.ccode " +
//				    "    and T1.pin = T2.pin and T1.pin1 = T2.pin1 " +
//				    "    and T1.pin2 = T2.pin2 and T1.pin3 = T2.pin3 " +
//				    "    and T1.pin4 = T2.pin4 and T1.pin5 = T2.pin5 " +
//				    "    and T1.pin6 = T2.pin6 and T1.pin7 = T2.pin7 " +
//				    "    and T1.pin8 = T2.pin8 " +
//				    ") AS T3 " +
//				    "join ( " +
//				    "    SELECT ccode, mut_type, mut_no, mut_date, mut_name " +
//				    "    FROM public.dblink( " +
//				    "        'dbname=' || ?2 || ' host=' || ?1 || ' port=' || ?6 || ' user=' || ?4 || ' password=' || ?5, " +
//				    "        'SELECT m.ccode, m.mut_no, m.mut_date, m.mut_type, m.mut_name " +
//				    "         FROM ' || ?3 || '.mutregister m' " +
//				    "    ) AS a( " +
//				    "        ccode text, mut_no integer, mut_date date, mut_type integer, mut_name character varying " +
//				    "    ) " +
//				    "    where CAST(mut_date AS date) BETWEEN '2024-10-17' and '2025-07-31' " +
//				    ") AS T4 " +
//				    "ON T3.ccode = T4.ccode and T3.mut_no = T4.mut_no",
//				    nativeQuery = true)
//				List<Object[]> fetchWarg2FherfarData(
//				    String fhost,
//				    String fDbname,
//				    String fSchemaName,
//				    String fuser,
//				    String fpassword,
//				    String port
//				);
		    
		    @Query(
				    value =
				        "SELECT t.ccode, t.mut_type, t.mut_no, t.mut_date, t.mut_name, t.survey_hissa " +
				        "FROM public.dblink( " +
				        "  'dbname=' || ?2 || " +
				        "  ' host=' || ?1 || " +
				        "  ' port=' || ?6 || " +
				        "  ' user=' || ?4 || " +
				        "  ' password=' || ?5, " +

				        "  'SELECT T4.ccode, T4.mut_type, T4.mut_no, T4.mut_date, T4.mut_name, " +
				        "   STRING_AGG(DISTINCT CONCAT(T3.pin, ''/'', T3.pin1, ''/'', T3.pin2, ''/'', T3.pin3, ''/'', " +
				        "   T3.pin4, ''/'', T3.pin5, ''/'', T3.pin6, ''/'', T3.pin7, ''/'', T3.pin8), '','') " +

				        "   FROM ( " +
				        "       SELECT T2.mut_no, T1.ccode, T2.pin, T2.pin1, T2.pin2, T2.pin3, " +
				        "              T2.pin4, T2.pin5, T2.pin6, T2.pin7, T2.pin8 " +
				        "       FROM ( " +
				        "           SELECT ccode, pin, pin1, pin2, pin3, pin4, pin5, pin6, pin7, pin8, tenure_code " +
				        "           FROM ' || ?3 || '.form7 f7 " +
				        "           WHERE f7.tenure_code = ''2'' " +
				        "              OR (f7.tenure_code = ''1'' AND f7.tenure_sub_code <> ''0'') " +
				        "             AND marked <> ''Y'' " +
				        "       ) T1 " +
				        "       JOIN ( " +
				        "           SELECT ccode, pin, pin1, pin2, pin3, pin4, pin5, pin6, pin7, pin8, mut_no " +
				        "           FROM ' || ?3 || '.mut_kharedi " +
				        "       ) T2 " +
				        "       ON T1.ccode = T2.ccode " +
				        "      AND T1.pin = T2.pin AND T1.pin1 = T2.pin1 " +
				        "      AND T1.pin2 = T2.pin2 AND T1.pin3 = T2.pin3 " +
				        "      AND T1.pin4 = T2.pin4 AND T1.pin5 = T2.pin5 " +
				        "      AND T1.pin6 = T2.pin6 AND T1.pin7 = T2.pin7 " +
				        "      AND T1.pin8 = T2.pin8 " +
				        "   ) T3 " +

				        "   JOIN ( " +
				        "       SELECT ccode, mut_type, mut_no, mut_date, mut_name " +
				        "       FROM ' || ?3 || '.mutregister " +
				        "       WHERE CAST(mut_date AS date) BETWEEN ''2024-10-17'' AND ''2025-07-31'' " +
				        "   ) T4 " +
				        "   ON T3.ccode = T4.ccode AND T3.mut_no = T4.mut_no " +

				        "   GROUP BY T4.ccode, T4.mut_type, T4.mut_no, T4.mut_date, T4.mut_name' " +
				        ") AS t(ccode text, mut_type integer, mut_no integer, mut_date date, mut_name varchar, survey_hissa text)",
				    nativeQuery = true
				)
				List<Object[]> fetchWarg2FherfarData(
				    String host,    
				    String dbName,   
				    String schema,   
				    String user,    
				    String password, 
				    String port     
				);

				
				 /* Original Query
				  * @Query(value = "SELECT application_id, application_date " +
					        "FROM public.dblink( " +
					        "  'dbname=' || 'pdedb_lr' || ' host=' || ?1 || ' port=' || ?4 || " +
					        "  ' user=' || ?2 || ' password=' || ?3, " +
					        "  'SELECT application_id, application_date FROM ' || 'mhror_pde' || '.pde_application " +
					        "   WHERE ccode = ''' || ?5 || ''' " +
					        "     AND district_code = ''' || ?6 || ''' " +
						    "     AND taluka_code = ''' || ?7 || ''' " +
					        "   AND status_msg=''Received'' AND status_code=''1'' " +
					        "   AND CURRENT_DATE - application_date < 30' " +
					        ") AS t(application_id bigint, application_date date)", 
					        nativeQuery = true)
					List<Object[]> fetchPendingApplicationsForTalathiUnderThirtyDays(
					        String fhost,
					        String fuser,
					        String fpassword,
					        String port,
					        String ccode,
					        String districtCode,
					        String talukaCode
					        
					); */
					
					
					 @Query(value = "SELECT application_id, application_date " +
						        "FROM public.dblink( " +
						        "  'dbname=' || 'pdedb_lr' || ' host=' || ?1 || ' port=' || ?4 || " +
						        "  ' user=' || ?2 || ' password=' || ?3, " +
						        "  'SELECT application_id, application_date FROM ' || 'mhror_pde' || '.pde_application " +
						        "   WHERE  " +
						        "    district_code = ''' || ?5 || ''' " +
							    "     AND taluka_code = ''' || ?6 || ''' " +
						        "   AND status_msg=''Received'' AND status_code=''1'' ' " +
						   //   "   AND CURRENT_DATE - application_date < 30' " +
						        ") AS t(application_id bigint, application_date date)", 
						        nativeQuery = true)
						List<Object[]> fetchPendingApplicationsForTalathiUnderThirtyDays(
						        String fhost,
						        String fuser,
						        String fpassword,
						        String port,
						       // String ccode,
						        String districtCode,
						        String talukaCode
						        
						);
					
					
					
					/*  @Query(value = "SELECT application_id, application_date " +
					        "FROM public.dblink( " +
					        "  'dbname=' || 'pdedb_lr' || ' host=' || ?1 || ' port=' || ?4 || " +
					        "  ' user=' || ?2 || ' password=' || ?3, " +
					        "  'SELECT application_id, application_date FROM ' || 'mhror_pde' || '.pde_application " +
					        "   WHERE ccode = ''' || ?5 || ''' " +
					        "     AND district_code = ''' || ?6 || ''' " +
						    "     AND taluka_code = ''' || ?7 || ''' " +
					        "   AND status_msg = ''Received'' AND status_code = ''1'' " +
					        "   AND application_date BETWEEN CURRENT_DATE - INTERVAL ''30 days'' " +
					        "                           AND CURRENT_DATE - INTERVAL ''90 days''' " +
					        ") AS t(application_id bigint, application_date date)",
					        
					        nativeQuery = true)
					List<Object[]> fetchPendingApplicationsForTalathiInMidPendingRange(
							String fhost,
					        String fuser,
					        String fpassword,
					        String port,
					        String ccode,
					        String districtCode,
					        String talukaCode
					            
					); */

					
					@Query(value = "SELECT application_id, application_date " +
					        "FROM public.dblink( " +
					        "  'dbname=' || 'pdedb_lr' || ' host=' || ?1 || ' port=' || ?4 || " +
					        "  ' user=' || ?2 || ' password=' || ?3, " +
					        "  'SELECT application_id, application_date FROM ' || 'mhror_pde' || '.pde_application " +
					        "   WHERE  " +
					        "     district_code = ''' || ?5 || ''' " +
						    "     AND taluka_code = ''' || ?6 || ''' " +
					        "   AND status_msg = ''Received'' AND status_code = ''1'' ' " +
					     //   "   AND application_date BETWEEN CURRENT_DATE - INTERVAL ''30 days'' " +
					     //   "                           AND CURRENT_DATE - INTERVAL ''90 days''' " +
					        ") AS t(application_id bigint, application_date date)",
					        
					        nativeQuery = true)
					List<Object[]> fetchPendingApplicationsForTalathiInMidPendingRange(
							String fhost,
					        String fuser,
					        String fpassword,
					        String port,
					      //  String ccode,
					        String districtCode,
					        String talukaCode
					            
					);


					@Query(value = "SELECT " +
					        "application_id, application_date, status_msg, status_code, corr_f_remark, rej_reason " +
					        "FROM public.dblink( " +
					        "'dbname=' || 'pdedb_lr' || ' host=' || ?1 || ' port=' || ?4 || ' user=' || ?2 || ' password=' || ?3, " +
					        "'SELECT district_code, taluka_code, ccode, mut_no, mut_name, mut_type, application_date, application_id, status_msg, status_code, corr_f_remark, rej_reason FROM mhror_pde.pde_application' " +
					        ") AS a ( " +
					        "district_code character(2), taluka_code character(2), ccode text, mut_no integer, mut_name character varying, mut_type integer, application_date date, " +
					        "application_id bigint, status_msg character varying, status_code character, corr_f_remark character varying, rej_reason character varying " +
					        ") " +
					        "WHERE district_code =?5  AND taluka_code =?6  AND status_msg = 'Received' AND status_code = '1' " +
					        "AND CURRENT_DATE - application_date > 1",
					        nativeQuery = true)
					List<Object[]> fetchPendingApplicationsOver180Days(
					        String host,
					        String user,
					        String password,
					        String port,
					       // String ccode ,
					        String districtCode, 
					        String talukaCode     

					);
					
					
					/* Main original Query
					 * @Query(value = "SELECT " +
					        "application_id, application_date, status_msg, status_code, corr_f_remark, rej_reason " +
					        "FROM public.dblink( " +
					        "'dbname=' || 'pdedb_lr' || ' host=' || ?1 || ' port=' || ?4 || ' user=' || ?2 || ' password=' || ?3, " +
					        "'SELECT district_code, taluka_code, ccode, mut_no, mut_name, mut_type, application_date, application_id, status_msg, status_code, corr_f_remark, rej_reason FROM mhror_pde.pde_application' " +
					        ") AS a ( " +
					        "district_code character(2), taluka_code character(2), ccode text, mut_no integer, mut_name character varying, mut_type integer, application_date date, " +
					        "application_id bigint, status_msg character varying, status_code character, corr_f_remark character varying, rej_reason character varying " +
					        ") " +
					        "WHERE district_code =?6  AND taluka_code =?7 AND ccode=?5 AND status_msg = 'Received' AND status_code = '1' " +
					        "AND CURRENT_DATE - application_date > 180",
					        nativeQuery = true)
					List<Object[]> fetchPendingApplicationsOver180Days(
					        String host,
					        String user,
					        String password,
					        String port,
					        String ccode ,
					        String districtCode, 
					        String talukaCode     

					);*/
					
					/* Original Query main
					 * @Query(value = "SELECT application_id, application_date " +
					 
					        "FROM public.dblink( " +
					        "  'dbname=' || 'pdedb_lr' || ' host=' || ?1 || ' port=' || ?4 || " +
					        "  ' user=' || ?2 || ' password=' || ?3, " +
					        "  'SELECT application_id, application_date FROM ' || 'mhror_pde' || '.pde_application " +
					        "   WHERE ccode = ''' || ?5 || ''' " +
					        "     AND district_code = ''' || ?6 || ''' " +
						    "     AND taluka_code = ''' || ?7 || ''' " +
					        "   AND status_msg = ''Received'' AND status_code = ''1'' " +
					        "   AND application_date BETWEEN CURRENT_DATE - INTERVAL ''90 days'' " +
					        "                           AND CURRENT_DATE - INTERVAL ''180 days''' " +
					        ") AS t(application_id bigint, application_date date)",
					        nativeQuery = true)
						List<Object[]> fetchPendingApplications90To180Days(
						        String host,
						        String user,
						        String password,
						        String port,
						        String ccode,
						        String districtCode, 
						        String talukaCode    

						);*/
						
						
						@Query(value = "SELECT application_id, application_date " +
						        "FROM public.dblink( " +
						        "  'dbname=' || 'pdedb_lr' || ' host=' || ?1 || ' port=' || ?4 || " +
						        "  ' user=' || ?2 || ' password=' || ?3, " +
						        "  'SELECT application_id, application_date FROM ' || 'mhror_pde' || '.pde_application " +
						        "   WHERE  " +
						        "      district_code = ''' || ?5 || ''' " +
							    "     AND taluka_code = ''' || ?6 || ''' " +
						        "   AND status_msg = ''Received'' AND status_code = ''1'' ' " +
						     //   "   AND application_date BETWEEN CURRENT_DATE - INTERVAL ''90 days'' " +
						     //   "                           AND CURRENT_DATE - INTERVAL ''180 days''' " +
						        ") AS t(application_id bigint, application_date date)",
						        nativeQuery = true)
							List<Object[]> fetchPendingApplications90To180Days(
							        String host,
							        String user,
							        String password,
							        String port,
							       // String ccode,
							        String districtCode, 
							        String talukaCode    

							);
						
						
			/*@Query(value = "SELECT application_id, remark, remark_type_name, ehakkatype " + "FROM public.dblink( "
					+ "  'dbname=echmst host=' || ?1 || ' port=' || ?4 || " + "  ' user=' || ?2 || ' password=' || ?3, " +

					"  'SELECT e.application_id, " + "          e.remark, " + "          rt.remark_type_name, "
					+ "          etm.e_hakka_type ' || " +

					"  'FROM public.e_hakka_inspection e ' || "
					+ "  'INNER JOIN public.remark_type rt ON rt.id = e.type_of_remark ' || "
					+ "  'LEFT JOIN public.e_hakka_type_master etm ON etm.id = e.e_hakka_type ' || " +

					"  'WHERE e.ccode = ''' || ?5 || ''' ' || " + "  'AND e.district_code = ' || ?6 || ' ' || "
					+ "  'AND e.taluka_code = ' || ?7 || ' ' || " + "  'AND e.e_hakka_type = 5 ' || "
					+ "  'AND e.active_flag = ''Y'' ' " + ") AS a ( " + "  application_id INTEGER, " + "  remark TEXT, "
					+ "  remark_type_name TEXT, " + "  ehakkatype TEXT " + ")", nativeQuery = true)
			List<Object[]> fetchEHakkaTypeFiveData(String fhost, String fuser, String fpassword, String port, String ccode,
								Integer districtCode, Integer talukaCode); */
			
			
	
				
			@Query(value = "SELECT application_id, remark, remark_type_name, ehakkatype "
			       // + "from public.dblink('dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||' port='|| ?4 ||' user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
			        + "from public.dblink('dbname='|| 'echmst' ||' host='|| '10.48.65.110' ||' port='|| ?4 ||' user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
			        + "  'SELECT e.application_id, "
			        + "          e.remark, "
			        + "          rt.remark_type_name, "
			        + "          etm.e_hakka_type ' || "
			        + "  'FROM public.e_hakka_inspection e ' || "
			        + "  'INNER JOIN public.remark_type rt ON rt.id = e.type_of_remark ' || "
			        + "  'LEFT JOIN public.e_hakka_type_master etm ON etm.id = e.e_hakka_type ' || "
			        + "  'WHERE e.ccode = ''' || ?1 || ''' ' || "
			        + "  'AND e.district_code = ' || ?2 || ' ' || "
			        + "  'AND e.taluka_code = ' || ?3 || ' ' || "
			        + "  'AND e.e_hakka_type = 5 ' || "
			        + "  'AND e.active_flag = ''Y'' ' "
			        + ") AS a ( "
			        + "  application_id BIGINT, "
			        + "  remark TEXT, "
			        + "  remark_type_name TEXT, "
			        + "  ehakkatype TEXT "
			        + ")",
			        nativeQuery = true)
			List<Object[]> fetchEHakkaTypeFiveData(
			        String ccode,
			        String districtCode,
			        String talukaCode,
			        String port
			);
			

			/*@Query(value = "SELECT application_id, remark, remark_type_name, e_hakka_type_name " + "FROM public.dblink( "
					+ "  'dbname=echmst host=' || ?1 || ' port=' || ?4 || " + "  ' user=' || ?2 || ' password=' || ?3, "
					+ "  'SELECT e.application_id, e.remark, rt.remark_type_name, etm.e_hakka_type ' || "
					+ "  'FROM public.e_hakka_inspection e ' || "
					+ "  'INNER JOIN public.remark_type rt ON rt.id = e.type_of_remark ' || "
					+ "  'LEFT JOIN public.e_hakka_type_master etm ON etm.id = e.e_hakka_type ' || "
					+ "  'WHERE e.ccode = ''' || ?5 || ''' ' || " + "  'AND e.district_code = ' || ?6 || ' ' || "
					+ "  'AND e.taluka_code = ' || ?7 || ' ' || " + "  'AND e.e_hakka_type = ' || ?8 || ' ' || "
					+ "  'AND e.active_flag = ''Y'' ' "
					+ ") AS a (application_id INTEGER, remark TEXT, remark_type_name TEXT, e_hakka_type_name TEXT)", nativeQuery = true)
			List<Object[]> fetchEHakkaApplicationData(String fhost, String fuser, String fpassword, String port, String ccode,
								Integer districtCode, Integer talukaCode, Long eHakkaType);   */
			
	
			
			@Query(value = "SELECT application_id, remark, remark_type_name, e_hakka_type_name " 
			      //  + "from public.dblink('dbname='|| 'echmst' ||' host='|| '10.10.248.108' ||' port='|| ?5 ||' user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
			        + "from public.dblink('dbname='|| 'echmst' ||' host='|| '10.48.65.110' ||' port='|| ?5 ||' user='|| 'postgres' ||' password='|| 'jduri%D^7f9gf', "
							+ "  'SELECT e.application_id, e.remark, rt.remark_type_name, etm.e_hakka_type ' || "
								+ "  'FROM public.e_hakka_inspection e ' || "
								+ "  'INNER JOIN public.remark_type rt ON rt.id = e.type_of_remark ' || "
								+ "  'LEFT JOIN public.e_hakka_type_master etm ON etm.id = e.e_hakka_type ' || "
								+ "  'WHERE e.ccode = ''' || ?1 || ''' ' || " + "  'AND e.district_code = ' || ?2 || ' ' || "
								+ "  'AND e.taluka_code = ' || ?3 || ' ' || " + "  'AND e.e_hakka_type = ' || ?4 || ' ' || "
								+ "  'AND e.active_flag = ''Y'' ' "
								+ ") AS a (application_id bigint, remark TEXT, remark_type_name TEXT, e_hakka_type_name TEXT)", nativeQuery = true)
						List<Object[]> fetchEHakkaApplicationData(String ccode,
											String districtCode, String talukaCode, Long eHakkaType,String port);
					

}