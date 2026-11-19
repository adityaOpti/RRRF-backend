package com.probity.echawdi.utils;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author Ashish Dahale
 *
 */

public interface CommonConstants {

	public static final String DEFAULT_PAGE_NUMBER = "0";
	public  static final String DEFAULT_PAGE_SIZE = "1000";
	public static final String DEFAULT_SORT_BY = "id";
	public static final String DEFAULT_SORT_DIRECTION = "asc";

	//    	  uat/prod run
	//	public static final String mhrHost 		= "10.10.248.153";
		public static final String mhrHost 		= "10.10.248.168";
		
		public static final String HostEhakka = "10.10.248.154";
		
		//public static final String AugDatamhrHost 		= "10.10.248.153";
		//public static final String AugDatamhrHost 		= "10.10.248.239"; 
		public static final String AugDatamhrHost 		= "10.48.65.111";
		public static final String CarryForwordPendingPreYearRevenueYear = 	"2025-26";
	   // public static final String AugDataPort 			= 	"5435";239 
	    public static final String AugDataPort 			= 	"5434";  /// Need to update static data port
	    public static final String DefaultDataPort 			= 	"5432";
	   // public static final String AugDataPort 			= 	"5432";
		public static final String mhrorUser 	= "echawadi";
		public static final String mhrorPassword= "echwadi@Nic22";
		public static final String echUser 		= "postgres";
		//public static final String echPassword 	= "@$h!$h_1061";
		public static final String echPassword 	= "jduri%D^7f9gf";
		
      public static final String secretKey 		= 	"wX#hN%4&V";
      public static final String secretKeyLogin   = "wXhN%8T@hS$Z@Q";
      public static final String netCultiArea 		= 	"2025";
      public static final String currentYear 		= 	"2026-27";
      
//      	public static final String KokAurSlave = "10.10.248.140";
//  		public static final String NasNagSlave = "10.10.248.194";
//  		public static final String PunAmtSlave = "10.10.248.195";
      
      	//for the new three roles
      	public static final String MANDAL_ADHIKARI_PREFIX = "C";
		public static final String TAHSILDAR_PREFIX = "TAH";
		public static final String COLLECTOR_PREFIX = "DDE";
  		
  		public static final String KokAurSlave = "10.48.65.110";
  		public static final String NasNagSlave = "10.48.65.110";
  		public static final String PunAmtSlave = "10.48.65.110";
  		
  		
  		
      
  	
      
	//    local run 
//	public static final String mhrHost 			= 	"localhost";
//	public static final String mhrorUser 		= 	"postgres";
//	public static final String mhrorPassword 	= 	"root";
//	public static final String echUser 			= 	"postgres";
//	public static final String echPassword 		= 	"root";
//	public static final String secretKey 		= 	"wX#hN%4&V";
//	public static final String secretKeyLogin   = "wXhN%8T@hS$Z@Q";

	public interface FileName {
		String FILEPATH_CONFIG = "filepath-config";
	}

	public static final HashMap<String ,String> types= new HashMap<String ,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("इनाम वर्ग-१" ,  "सरंजामइनाम");
			put("इनाम वर्ग-२" ,  "जात");
			put("इनाम वर्ग-३" ,  "देवस्थानइनाम");
			put("इनाम वर्ग-४" ,  "देशपांडे/देशमुख/कुलकर्णी");
			put("इनाम वर्ग-५" ,  "परगना");
			put("इनाम वर्ग-६अ", "OPTION");
			put("इनाम वर्ग-६ब", "OPTION");
			put("इनाम वर्ग-७" ,  "संकीर्ण");
		}};

		public interface NoticeFees {
			BigDecimal NOTICE_FEES = BigDecimal.valueOf(25);
		}

		public interface Matcher{
			String validFileMatcher=".jpg|.jpeg|.bmp|.gif|.png|.pdf";
			String imageMatcher=".jpg|.png|.jpeg|.bmp|.gif";
			String pdfMather=".pdf";
		}

		public interface DateFormats {
			String dateformat= "dd/MM/yyyy";
			String datetimeformat= "dd/MM/yyyy HH:mm:ss";
			String datetimeformat3= "yyyy-MM-dd HH:mm:ss";
			String datetimeformat5= "yyyy-MM-dd";
			String FILE_NAME_DATE_FORMAT = "dd_MM_yyyy_HH_mm_ss";
		}

		public interface DIStatus{
			String Completed = "COMPLETED";
			String InComplete = "INCOMPLETE";

		}

		public interface NotificationCode{
			String ADDED = "ADDED";
			String UPDATED = "UPDATED";
			String APPROVED = "APPROVED";
			String REJECTED = "REJECTED";
			String DELETE = "DELETE";
			String DELETED = "DELETED";
			String ALREADYDELETED = "ALREADYDELETED";
			String SWR = "SWR";
			String COMPLETED = "COMPLETED";
		}
}