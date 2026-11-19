//package com.probity.echawdi.serviceImpl;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Locale;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.probity.echawdi.entity.UploadExcel;
//import com.probity.echawdi.service.UploadExcelService;
//
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.read.biff.BiffException;
//@Service
//public class UploadExcelServiceImpl implements UploadExcelService{
//
//	
//	@Override
//	public UploadExcel uploadexcelfile(MultipartFile multipartFile, HttpServletRequest request, Locale locale) throws SQLException {
//		
//		//		String fileName=multipartFile.getOriginalFilename();
//		//		UploadExcel uploadExcel=new UploadExcel();
//		////		uploadExcel.setId(null);
//		//		uploadExcel.setDistrictName(null);
//		//		uploadExcel.setTalukaName(null);
//		//		uploadExcel.setDistrictCode(null);
//		//		uploadExcel.setTalukaCode(null);
//		//		uploadExcel.setCCode(null);
//		//		uploadExcel.setLgdCode(null);
//		//		uploadExcel.setVillageName(null);
//		//		uploadExcel.setIsApplicable(null);
//		//		uploadExcel.setActiveFlag(null);
//		//		return uploadExcelRepository.save(uploadExcel);
//		Workbook workbook = null;
//		try {
//			String EXCEL_FILE_LOCATION = "‪C:\\Users\\ue\\Desktop\\VillagesLst.xls";
////			workbook = Workbook.getWorkbook(new File(multipartFile.getResource().getFile().getP, EXCEL_FILE_LOCATION));
//			workbook = Workbook.getWorkbook(new File(EXCEL_FILE_LOCATION));
//			Sheet sheet = workbook.getSheet(0);
//			Connection c = null;
//			Statement stmt = null;
//			try {
//				Class.forName("org.postgresql.Driver");
//				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/echmst1","postgres", "root");
//				c.setAutoCommit(false);
//				System.out.println("Opened database successfully");
//
//				stmt = c.createStatement();
//
//			} catch (Exception e) {
//				System.err.println( e.getClass().getName()+": "+ e.getMessage() );
//				System.exit(0);
//			}
//
//			for (int row=2;row < sheet.getRows();row++){ 
//				if(sheet.getCell(2, row) !=null && sheet.getCell(2, row).getContents() != null && !sheet.getCell(2, row).getContents().trim().equals("")) {
//					String sql="insert into m_village_master("
//							+ "id,district_name,taluka_name,district_code,taluka_code,ccode,igd,village_name"
//							+ ")"
//							+ "values("
//							+  sheet.getCell(0,  row).getContents() + ", "
//							+ "'"+sheet.getCell(1,  row).getContents() + "', "
//							+ "'"+sheet.getCell(2,  row).getContents() + "', "
//							+ "'"+sheet.getCell(3,  row).getContents() + "', "
//							+ "'"+sheet.getCell(4,  row).getContents() + "', "
//							+ "'"+sheet.getCell(5,  row).getContents() + "', "
//							+ "'"+sheet.getCell(6,  row).getContents() + "', "
//							+ "'"+sheet.getCell(7,  row).getContents() + "', "
//							+ ");";
//					System.out.println("--sql---"+sql);
//					stmt.executeUpdate(sql);
//				}
//			}
//			stmt.close();
//			c.commit();
//			c.close();
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		} catch (BiffException e) {
//			e.printStackTrace();
//		}finally {
//
//			if (workbook != null) {
//				workbook.close();
//			}
//
//		}
//		return null;
//	}
//
//}
