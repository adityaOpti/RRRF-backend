package com.probity.echawdi.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.entity.DashboardSupervisoryDtl;
import com.probity.echawdi.entity.DivisionDistrictTalukaCount;
import com.probity.echawdi.entity.LGDMapper;
import com.probity.echawdi.entity.LgdMapperMis;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.VillageEntryCountLive;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.ChallanRepository;
import com.probity.echawdi.repository.DashboardSupervisoryDtlRepository;
import com.probity.echawdi.repository.DivisionDistrictTalukaCountRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.repository.LgdMapperMisRepository;
import com.probity.echawdi.repository.LgdMapperRepository;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.repository.VillageEntryCountLiveRepository;
import com.probity.echawdi.repository.VillageRepository;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class Scheduler {

	@Autowired
	private VillageRepository villageRepository;
	@Autowired
	private MVillageMapperRepository mVillageMapperRepository;
	@Autowired
	private LgdMapperRepository lgdMapperRepository;
	@Autowired
	private LgdMapperMisRepository lgdMapperMisRepository;
	@Autowired
	private VillageEntryCountLiveRepository villageEntryCountLiveRepository;
	@Autowired
	private ChallanRepository challanRepository;
	@Autowired
	DivisionDistrictTalukaCountRepository divisionDistrictTalukaCountRepository;
	@Autowired
	DashboardSupervisoryDtlRepository dashboardSupervisoryDtlRepository;
	
	//for mis Net_vasuli
	@Autowired
	private LandRevenueDemandRepository landRevenueDemandRepository;
	
//	@Autowired
//	private RestTemplate restTemplate;

 	     @Scheduled(cron = "00 35 23 * * ?")
	@WithDatabase(DataSourceType.echmst)
	public void getLiveCount() {
		try {
			System.out.println("Schedular started<====>");
			List<MVillageMapper> mapper=mVillageMapperRepository.findAll();
			//List<MVillageMapper> mapper=mVillageMapperRepository.findByDistrictCodeAndTalukaCode("24","11");
			Response response = new Response();
			List<VillageEntryCountLive> villageEntryCountLives = new ArrayList<VillageEntryCountLive>();
			int cnt=1;
			for(MVillageMapper mapR:mapper) { 
				Long result=villageRepository.getTalukaCount(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,mapR.getDistrictCode(),mapR.getTalukaCode());
				System.out.println("total entry count<====>"+result);
				System.out.println("db<====>"+mapR.getEchDbName());
				System.out.println("schema<====>"+mapR.getEchSchemaName());
				cnt++;
				System.out.println("Count<====>"+cnt);
				VillageEntryCountLive countLive=new VillageEntryCountLive();
				countLive=villageEntryCountLiveRepository.findByDistrictAndTaluka(mapR.getDistrictCode(),mapR.getTalukaCode());
				if(result!=null) {
					countLive.setTotalEntry(result);
				}
				System.out.println("countLive<====>"+countLive);
				villageEntryCountLives.add(countLive);
			}
			System.out.println("Schedular end <====>");
			response.setVillageEntryCountLive(villageEntryCountLives);
			villageEntryCountLiveRepository.saveAll(response.getVillageEntryCountLive());
//			restTemplate.postForEntity("http://localhost:8091/echawdi/auth/persistTalukasCount", response, boolean.class);
			log.info("Job Executed Successfully...!!!");
		}catch (Exception e) {
			log.info("Error Occured During Execution...!!!");
			e.printStackTrace();
		}
	}
 	
	
	


 	  @Scheduled(cron ="00 40 23 * * ?")
	@WithDatabase(DataSourceType.echmst)
	public void getLoginCount() {
		try {
			List<Object[]> loginCounts=mVillageMapperRepository.getLoginCount();
			Response response = new Response();
			List<VillageEntryCountLive> villageEntryCountLives = new ArrayList<VillageEntryCountLive>();
			if(loginCounts.size()>0) {
				loginCounts.stream().map(r->{
					VillageEntryCountLive countLive=new VillageEntryCountLive();
					System.out.println(r[0].toString()+"---------Dis and Tal code--------"+r[1].toString());
					countLive=villageEntryCountLiveRepository.findByDistrictAndTaluka(r[0].toString(),r[1].toString());
					countLive.setTotalPhase1Login(Long.valueOf(r[2]==null ? "0": r[2].toString()));
					System.out.println(" sandeep----countLive<====>"+countLive);
					villageEntryCountLives.add(countLive);
					return r;
				}).collect(Collectors.toList());
				response.setVillageEntryCountLive(villageEntryCountLives);
				villageEntryCountLiveRepository.saveAll(response.getVillageEntryCountLive());
//				restTemplate.postForEntity("http://localhost:8091/echawdi/auth/persistTalukasCount", response, boolean.class);
				log.info("Job Executed Successfully...!!!");
			}else {
				log.info("No Records to Update...!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error...!!!");
		}
	}
	
 	    @Scheduled(cron = "00 45 23 * * ?")
	@WithDatabase(DataSourceType.echmst)
	public void getVillageOnBoardVillagesList() {
		try {
			List<Object[]> loginCounts=mVillageMapperRepository.getOnBoardVillagesCount();
			Response response = new Response();
			List<DivisionDistrictTalukaCount> divisionEntryCountLives = new ArrayList<DivisionDistrictTalukaCount>();
			if(loginCounts.size()>0) {
				loginCounts.stream().map(r->{
					DivisionDistrictTalukaCount countLive=new DivisionDistrictTalukaCount();
					System.out.println(r[0].toString()+"---------Dis and Tal code--------"+r[1].toString());
					countLive=divisionDistrictTalukaCountRepository.findByDistrictCodeAndTalukaCode(r[0].toString(),r[1].toString());
//					countLive.setTotalPhase1Villages( r[2] ==null ? BigDecimal.ZERO :BigDecimal.valueOf(Double.valueOf( r[2].toString())));
				countLive.setTotalPhase1Villages(Long.valueOf(r[2]==null? "0": r[2].toString()));
					divisionEntryCountLives.add(countLive);
					return r;
				}).collect(Collectors.toList());
				response.setDivisionDistrictTalukaCount(divisionEntryCountLives);
				divisionDistrictTalukaCountRepository.saveAll(response.getDivisionDistrictTalukaCount());
//				restTemplate.postForEntity("http://localhost:8091/echawdi/auth/persistTalukasCount", response, boolean.class);
				log.info("Job Executed Successfully...!!!");
			}else {
				log.info("No Records to Update...!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error...!!!");
		} 
	}
	
	

  	  @Scheduled(cron = "00 55 23 * * ?")
	@WithDatabase(DataSourceType.echmst)
	public List<LGDMapper> getChallanGeneratedCount() {
		try {
			List<MVillageMapper> mapper=mVillageMapperRepository.findAll();
			Response response = new Response();
			List<LGDMapper> lstLGDMapper = new ArrayList<LGDMapper>();
			for(MVillageMapper mapR:mapper) {
				System.out.println("countLive<====>EchSchemaName"+mapR.getEchSchemaName());
				List<Object[]> lstChallan=challanRepository.findBycCodeAndRevenueYearAndActiveFlag(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword);
				System.out.println("countLive<====>challan"+lstChallan);
				
				if(lstChallan != null && lstChallan.size() > 0) {
					
					for(Iterator iterator = lstChallan.iterator(); iterator.hasNext();) {
						Object [] obj = (Object[]) iterator.next();
						if(obj != null && obj[0] != null) {
							LGDMapper lgdMapper = lgdMapperRepository.findBycCode(obj[0].toString(), mapR.getTalukaCode());
							if(lgdMapper != null) {
								lgdMapper.setIsChallanGenerated('Y');
								lstLGDMapper.add(lgdMapper);
							}
						
						}
					}
				}
			}
			
	
			if(lstLGDMapper != null & lstLGDMapper.size() > 0) {
				lgdMapperRepository.saveAll(lstLGDMapper);
			}
			response.setLgdMapperDao(lstLGDMapper);

			
			
			log.info("Job Executed Successfully...!!!");
		}catch (Exception e) {
			log.info("Error Occured During Execution...!!!");
			e.printStackTrace();
		}
		return null;	
	}
	
	 @WithDatabase(DataSourceType.echmst)
		public List<LgdMapperMis> getDyslrDataCount() {
			try {
				List<MVillageMapper> mapper=mVillageMapperRepository.findAll();
				Response response = new Response();
				List<LgdMapperMis> lstLGDMapperMis = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMiskajapa = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMismslr = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMisnoticeFee = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMiskhataMerge = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMisaddLandrevenue = new ArrayList<LgdMapperMis>();
				int loopvar=0;
				for(MVillageMapper mapR:mapper) 
				{
					//if(mapR.getEchSchemaName().equals("echrai_tal") && mapR.getCCode().equals("272400110296420000")) 
					//{
						
					System.out.println("countLive<====>EchSchemaName  "+mapR.getEchSchemaName());
				//	System.out.println(" <====>ccode  "+mapR.getCCode());
					++loopvar;
					
					System.out.println(" <====>villageCount  "+loopvar); 
					
					List<Object[]> lstChallan=lgdMapperMisRepository.findBycCodeAndActiveFlagMISDyslr(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword);
				
					System.out.println("countLive<====>lr Dues"+lstChallan + "----------");
					
					if(lstChallan != null && lstChallan.size() > 0) {
						
						for(Iterator iterator = lstChallan.iterator(); iterator.hasNext();) {
							Object [] obj = (Object[]) iterator.next();
							if(obj != null && obj[0] != null) {
								LgdMapperMis lgdMappermis = lgdMapperMisRepository.findBycCodeforDyslrSave(obj[0].toString(),mapR.getTalukaCode());
								if(lgdMappermis != null) {
									lgdMappermis.setDyslrWorkStarted('Y');
									lstLGDMapperMis.add(lgdMappermis);
								}
							
							}
						}
					}
					
					if(lstLGDMapperMis != null & lstLGDMapperMis.size() > 0) {
						lgdMapperMisRepository.saveAll(lstLGDMapperMis);
					} 
					 
				//}
				}
				 
				response.setLgdMapperMisDao(lstLGDMapperMis);

				
				
				log.info("Job Executed Successfully...!!!");
			}catch (Exception e) {
				log.info("Error Occured During Execution...!!!");
				e.printStackTrace();
			}
			return null;	
		}
	 
	 
//	@Scheduled(cron = "00 55 23 * * ?")
		@WithDatabase(DataSourceType.echmst)
		public List<LgdMapperMis> getMISDataCount() {
			try {
				List<MVillageMapper> mapper=mVillageMapperRepository.findAll();
				Response response = new Response();
				List<LgdMapperMis> lstLGDMapperMis = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMiskajapa = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMismslr = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMisnoticeFee = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMiskhataMerge = new ArrayList<LgdMapperMis>();
				List<LgdMapperMis> lstLGDMapperMisaddLandrevenue = new ArrayList<LgdMapperMis>();
				int loopvar=0;
				for(MVillageMapper mapR:mapper) 
				{
					
					System.out.println("countLive<====>EchSchemaName  "+mapR.getEchSchemaName());
					System.out.println(" <====>ccode  "+mapR.getCCode());
					++loopvar;
					
					System.out.println(" <====>villageCount  "+loopvar); 
					//lrDuesCount                                    
					List<Object[]> lstChallan=lgdMapperMisRepository.findBycCodeAndRevenueYearAndActiveFlagMIS(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,CommonConstants.currentYear);
				
					System.out.println("countLive<====>lr Dues"+lstChallan + "----------");
					if(lstChallan != null && lstChallan.size() > 0) {
						for(Iterator iterator = lstChallan.iterator(); iterator.hasNext();) {
							Object [] obj = (Object[]) iterator.next();
							if(obj != null && obj[0] != null) {
								LgdMapperMis lgdMappermis = lgdMapperMisRepository.findBycCode(obj[0].toString(),mapR.getTalukaCode());
								if(lgdMappermis != null) {
									lgdMappermis.setIsLrDuesAdded('Y');
									//lgdMappermis.setIsGaonNamunaCompleted('Y');
									lstLGDMapperMis.add(lgdMappermis);
								}
							
							}
						}
					}
					
					if(lstLGDMapperMis != null & lstLGDMapperMis.size() > 0) {
						lgdMapperMisRepository.saveAll(lstLGDMapperMis);
					}
										 
				
					// Kajapa Count                                  
					List<Object[]> lstKajapa=lgdMapperMisRepository.findBycCodeAndRevenueYearAndActiveFlagMISkajapa(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,CommonConstants.currentYear);
				
					System.out.println("countLive<====>KAJAPA  "+lstKajapa + "----------");
					
					if(lstKajapa != null && lstKajapa.size() > 0) {
						
						for(Iterator iterator = lstKajapa.iterator(); iterator.hasNext();) {
							Object [] obj = (Object[]) iterator.next();
							if(obj != null && obj[0] != null) {
								LgdMapperMis lgdMappermis = lgdMapperMisRepository.findBycCodeKajapa(obj[0].toString(),mapR.getTalukaCode());
								if(lgdMappermis != null) {
									lgdMappermis.setIsKajapaAdded('Y');
									lstLGDMapperMiskajapa.add(lgdMappermis);
								}
							
							}
						}
					}
					
					if(lstLGDMapperMiskajapa != null & lstLGDMapperMiskajapa.size() > 0) {
						lgdMapperMisRepository.saveAll(lstLGDMapperMiskajapa);
					}
						
					List<Object[]> lstMSlandrevenue=lgdMapperMisRepository.findBycCodeAndRevenueYearAndActiveFlagMISmslr(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,CommonConstants.currentYear);
					
					System.out.println("countLive<====>MS Landrevenue  "+lstMSlandrevenue + "----------");
					
					if(lstMSlandrevenue != null && lstMSlandrevenue.size() > 0) {
						
						for(Iterator iterator = lstMSlandrevenue.iterator(); iterator.hasNext();) {
							Object [] obj = (Object[]) iterator.next();
							if(obj != null && obj[0] != null) {
								LgdMapperMis lgdMappermis = lgdMapperMisRepository.findBycCodemislr(obj[0].toString(),mapR.getTalukaCode());
								if(lgdMappermis != null) {
									lgdMappermis.setIsMsLrAdded('Y');
									lstLGDMapperMismslr.add(lgdMappermis);
								}
							
							}
						}
					}
					if(lstLGDMapperMismslr != null & lstLGDMapperMismslr.size() > 0) {
						lgdMapperMisRepository.saveAll(lstLGDMapperMismslr);
					}
					
					
				List<Object[]> lstNoticefee=lgdMapperMisRepository.findBycCodeAndActiveFlagMISnoticefee(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,CommonConstants.currentYear);
					
					System.out.println("countLive<====>NOtice fee "+lstNoticefee + "----------");
					
					if(lstNoticefee != null && lstNoticefee.size() > 0) {
						
						for(Iterator iterator = lstNoticefee.iterator(); iterator.hasNext();) {
							Object [] obj = (Object[]) iterator.next();
							if(obj != null && obj[0] != null) {
								LgdMapperMis lgdMappermis = lgdMapperMisRepository.findBycCodeNoticefee(obj[0].toString(),mapR.getTalukaCode());
								if(lgdMappermis != null) {
									lgdMappermis.setIsNoticeAdded('Y');
									lstLGDMapperMisnoticeFee.add(lgdMappermis);
								}
							
							}
						}
					}
					
					if(lstLGDMapperMisnoticeFee != null & lstLGDMapperMisnoticeFee.size() > 0) {
						lgdMapperMisRepository.saveAll(lstLGDMapperMisnoticeFee);
					}
					
						List<Object[]> lstKhataMerge=lgdMapperMisRepository.findBycCodeAndRevenueYearAndActiveFlagMISkhataMerge(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,Integer.parseInt(mapR.getTalukaCode()));
					
					System.out.println("countLive<====>kahata Merge"+lstKhataMerge + "----------");
					
					if(lstKhataMerge != null && lstKhataMerge.size() > 0) {
						
						for(Iterator iterator = lstKhataMerge.iterator(); iterator.hasNext();) {
							Object [] obj = (Object[]) iterator.next();
							if(obj != null && obj[0] != null) {
								LgdMapperMis lgdMappermis = lgdMapperMisRepository.findBycCodeKhataMerge(obj[0].toString(),mapR.getTalukaCode());
								if(lgdMappermis != null) {
									lgdMappermis.setIsKhataMergeAdded('Y');
									lstLGDMapperMiskhataMerge.add(lgdMappermis);
								}
							
							}
						}
					}
					if(lstLGDMapperMiskhataMerge != null & lstLGDMapperMiskhataMerge.size() > 0) {
						lgdMapperMisRepository.saveAll(lstLGDMapperMiskhataMerge);
					}
					
					
					
					List<Object[]> lstAddlr=lgdMapperMisRepository.findBycCodeAndRevenueYearAndActiveFlagMISAddlrrevenue(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,CommonConstants.currentYear);
					
					System.out.println("countLive<====>Add land reve"+lstAddlr + "----------");
					
					if(lstAddlr != null && lstAddlr.size() > 0) {
						
						for(Iterator iterator = lstAddlr.iterator(); iterator.hasNext();) {
							Object [] obj = (Object[]) iterator.next();
							if(obj != null && obj[0] != null) {
								LgdMapperMis lgdMappermis = lgdMapperMisRepository.findBycCodeAddlrrevenue(obj[0].toString(),mapR.getTalukaCode());
								if(lgdMappermis != null) {
									lgdMappermis.setIsAddLandAdded('Y');
									lstLGDMapperMisaddLandrevenue.add(lgdMappermis);
								}
							}
						}
					}
					
					if(lstLGDMapperMisaddLandrevenue != null & lstLGDMapperMisaddLandrevenue.size() > 0) {
						lgdMapperMisRepository.saveAll(lstLGDMapperMisaddLandrevenue);
					}	
					
				}
				
		
				
				response.setLgdMapperMisDao(lstLGDMapperMis);

				
				
				log.info("Job Executed Successfully...!!!");
			}catch (Exception e) {
				log.info("Error Occured During Execution...!!!");
				e.printStackTrace();
			}
			return null;	
		}
	/***************----------------------------------------------------------*/
	@WithDatabase(DataSourceType.echmst)
	public List<LGDMapper> getKhataCountForMis(String talukaCode, String districtCode) {
		try {
				//List<MVillageMapper> mapper=mVillageMapperRepository.findAll();
				//Response response = new Response();
			//	List<LGDMapper> lstLGDMapper = new ArrayList<LGDMapper>();
			List<LGDMapper> mapper=lgdMapperRepository.findAll();
			Response response = new Response();
			List<LGDMapper> lstLGDMapper = new ArrayList<LGDMapper>();
				int loopvar=0;
				int loopvartwo=0;
			for(LGDMapper mapR:mapper) 
			{
				++loopvartwo;
				System.out.println(" <====>outer  "+loopvartwo); 
				System.out.println(" <====>talukaCode  "+talukaCode+"---tttt="+mapR.getTalukaCode()); 
				System.out.println(" <====>talukaCode  "+districtCode+"----dddd"+mapR.getDistrictCode()); 
				//  if(mapR.getMhrDbName().equals("mhrornas") || mapR.getMhrDbName().equals("mhrorahm") || mapR.getMhrDbName().equals("mhrornan") || mapR.getMhrDbName().equals("mhrorjal")) 
				if((mapR.getTalukaCode().trim().equals(talukaCode) ) && (mapR.getDistrictCode().trim().equals(districtCode))   ) 
				{
				/* if(mapR.getDistrictCode().equals("20") ) 
				   {
					  System.out.println("HERE-----Dist Code---"+mapR.getDistrictCode());
				   }
				  if(mapR.getDistrictCode().trim().equals("20") ) 
				   {
					  System.out.println("HERE222-----Dist Code---"+mapR.getDistrictCode());
				   }
				  if(mapR.getTalukaCode().equals("1") ) 
				   {
					  System.out.println("here-----taluka Code---"+mapR.getDistrictCode());
				   }
				  if(mapR.getTalukaCode().trim().equals("1") ) 
				   {
					  System.out.println("HERE222-----taluka Code---"+mapR.getDistrictCode());
				   }*/
				//System.out.println("countLive<====>EchSchemaName  "+mapR.getMhrDbName());
				 System.out.println(" <====>ccode  "+mapR.getCCode());
				 System.out.println("Dist Code---"+mapR.getDistrictCode());
				 
				++loopvar;
				
				System.out.println(" <====>villageCount  "+loopvar); 
				MVillageMapper mVillage = mVillageMapperRepository.serchByschemabyTaluka(mapR.getTalukaCode().trim(),mapR.getDistrictCode().trim());
				
				System.out.println(" <====>DB  "+mVillage.getMhrDbName());
				 System.out.println("Schema---"+mVillage.getMhrSchemaName());
				List<Object[]> lstChallan=lgdMapperRepository.findBycCodeForKhataCount(CommonConstants.AugDatamhrHost,mVillage.getMhrDbName(),mVillage.getMhrSchemaName(),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,mapR.getCCode().trim(),CommonConstants.AugDataPort);
			
				//List<Object[]> lstChallan=lgdMapperRepository.findBycCodeForKhataCount(CommonConstants.AugDatamhrHost,mapR.getMhrDbName(),mapR.getMhrSchemaName(),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,mapR.getCCode(),CommonConstants.AugDataPort);
			
				//System.out.println("countLive<====>Khata Count "+lstChallan + "----------");
				
				if(lstChallan != null && lstChallan.size() > 0) {
					
					for(Iterator iterator = lstChallan.iterator(); iterator.hasNext();) 
					{
						Object [] obj = (Object[]) iterator.next();
						
						if(obj != null && obj[0] != null) {
							LGDMapper lgdMappermis = lgdMapperRepository.findBycCodeforkhataCountForsave(mapR.getCCode().trim(),mapR.getTalukaCode().trim());
							if(lgdMappermis != null) 
							{
								 System.out.println(" Loop Value= "+obj[0]);
								
								Integer valueint = ((BigInteger) obj[0]).intValue();
								Integer valueintsatbara = ((BigInteger) obj[1]).intValue();
								lgdMappermis.setKhataCount(valueint);
								lgdMappermis.setSathbaraCount(valueintsatbara);
								//lgdMappermis.setKhataCount((Integer)obj[0]);
								lstLGDMapper.add(lgdMappermis);
							}						
						}
					}
				}
				
				if(lstLGDMapper != null & lstLGDMapper.size() > 0) {
					lgdMapperRepository.saveAll(lstLGDMapper);
				}
			 }	 
				// response.setLgdMapperDao(lstLGDMapper);
			}
			 
			response.setLgdMapperDao(lstLGDMapper);
 
			log.info("Job Executed Successfully...!!!");
		}catch (Exception e) {
			log.info("Error Occured During Execution...!!!");
			e.printStackTrace();
		}
		return null;	
	}
	
	/*************-------------------------------------------------------------******************/
	/***************----------------------------------------------------------*/
	
	/*@WithDatabase(DataSourceType.echmst)
	public List<LGDMapper> getKhataCountForDemand(String talukaCode, String districtCode) {
		try {
				 
			List<LGDMapper> mapper=lgdMapperRepository.findAll();
			Response response = new Response();
			List<LGDMapper> lstLGDMapper = new ArrayList<LGDMapper>();
				int loopvar=0;
				int loopvartwo=0;
			for(LGDMapper mapR:mapper) 
			{
				++loopvartwo;
				System.out.println(" <====>outer  "+loopvartwo); 
				System.out.println(" <====>talukaCode  "+talukaCode+"---tttt="+mapR.getTalukaCode()); 
				System.out.println(" <====>talukaCode  "+districtCode+"----dddd"+mapR.getDistrictCode()); 
				//  if(mapR.getMhrDbName().equals("mhrornas") || mapR.getMhrDbName().equals("mhrorahm") || mapR.getMhrDbName().equals("mhrornan") || mapR.getMhrDbName().equals("mhrorjal")) 
				if((mapR.getTalukaCode().trim().equals(talukaCode) ) && (mapR.getDistrictCode().trim().equals(districtCode))   ) 
				{
				 
				 
				 
				++loopvar;
				
				System.out.println(" <====>villageCount  "+loopvar); 
				MVillageMapper mVillage = mVillageMapperRepository.serchByschemabyTaluka(mapR.getTalukaCode().trim(),mapR.getDistrictCode().trim());
				
				System.out.println(" <====>DB  "+mVillage.getMhrDbName());
				 System.out.println("Schema---"+mVillage.getMhrSchemaName());
				List<Object[]> lstChallan=lgdMapperRepository.findBycCodeForDemandCount(mVillage.getEchHost(),mVillage.getEchDbName(),mVillage.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,mapR.getCCode().trim(),CommonConstants.DefaultDataPort);
			
				 //////////
				
				if(lstChallan != null && lstChallan.size() > 0) {
					
					for(Iterator iterator = lstChallan.iterator(); iterator.hasNext();) 
					{
						Object [] obj = (Object[]) iterator.next();
						
						if(obj != null && obj[0] != null) {
							 
								 System.out.println(" Loop Value= "+obj[0]);
								
								Integer valueint = ((BigInteger) obj[0]).intValue();
								 
								lgdMappermis.setKhataCount(valueint);
								 
								//lgdMappermis.setKhataCount((Integer)obj[0]);
								lstLGDMapper.add(lgdMappermis);
							 						
						}
					}
				}
				
				if(lstLGDMapper != null & lstLGDMapper.size() > 0) {
					lgdMapperRepository.saveAll(lstLGDMapper);
				}
			 }	 
				// response.setLgdMapperDao(lstLGDMapper);
			}
			 
			response.setLgdMapperDao(lstLGDMapper);
 
			log.info("Job Executed Successfully...!!!");
		}catch (Exception e) {
			log.info("Error Occured During Execution...!!!");
			e.printStackTrace();
		}
		return null;	
	}
	
	*/
	/*************-------------------------------------------------------------******************/
	@WithDatabase(DataSourceType.echmst)
	public List<LGDMapper> getDemandGenerationCount(String talukaCode,String districtCode) {
		try {
			
				//List<LGDMapper> mapper=lgdMapperRepository.findAll();
		
				List<LGDMapper> mapper = lgdMapperRepository.getSelectedLgdMapperData(
						
						);
				
				  for (LGDMapper lgd : mapper) {
			            System.out.println(lgd);
			        }
					
				
		
				Response response = new Response();
				List<LGDMapper> lstLGDMapper = new ArrayList<LGDMapper>();
				int loopvar=0;
				for(LGDMapper mapR:mapper) 
				{
					++loopvar;
					
					// if(mapR.getCCode().equals("272400110296420000")) 
					 if((mapR.getTalukaCode().trim().equals(talukaCode)) && (mapR.getDistrictCode().trim().equals(districtCode) )) 
					 {	
						 //if((mapR.getTalukaCode().trim().equals(talukaCode) ) && (mapR.getDistrictCode().trim().equals(districtCode))   ) 
						// {	
						System.out.println("Count ="+loopvar+" Ccode "+mapR.getCCode());
						//System.out.println("countLive<====>EchSchemaName"+mapR.getEchSchemaName());
					//System.out.println("countLive<====>EchSchemaName"+mapR.getCCode());
					//Integer demandGenrationCount=(Integer)lgdMapperRepository.findBycCodeNamune(mapR.getCCode(),mapR.getTalukaCode());\
					
					MVillageMapper mVillage = mVillageMapperRepository.serchByschemabyTaluka(mapR.getTalukaCode().trim(),mapR.getDistrictCode().trim());
					
					mVillageMapperRepository.findAll();
					System.out.println("Before Demand QQQQ");
					//land_revenue_demand count
					//int demandGenrationCount = lgdMapperRepository.getDemandGeneration(mVillage.getEchHost(),mVillage.getEchDbName(),mVillage.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword, mapR.getCCode(), CommonConstants.currentYear);
					// Need to add revenue year
					int demandGenrationCount=0;
					if( (talukaCode.equals("1")&& districtCode.equals("16")) || (talukaCode.equals("2")&& districtCode.equals("16")) || (talukaCode.equals("4")&& districtCode.equals("16")) || (talukaCode.equals("5")&& districtCode.equals("16")) )
					{
						demandGenrationCount = lgdMapperRepository.getDemandGenerationUpdated(mVillage.getEchHost(),mVillage.getEchDbName(),mVillage.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword, mapR.getCCode());
						
					}
					else
					{
						  demandGenrationCount = lgdMapperRepository.getDemandGeneration(mVillage.getEchHost(),mVillage.getEchDbName(),mVillage.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword, mapR.getCCode());
					}
					
					System.out.println("lrd Count "+demandGenrationCount);
					if(demandGenrationCount==0)
					{
						continue;// If no record for demand then go to next
					}
					//khataMergeDtl count
					//int kMergeDtlCount = lgdMapperRepository.getKhataMergeDtlCount(mVillage.getEchHost(),mVillage.getEchDbName(),mVillage.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword , mapR.getCCode());
					//System.out.println("khataMergeDtlCount "+kMergeDtlCount);
					
					 
					//KhataMerge count
					//int kMergeCount = lgdMapperRepository.getKhataMergeCount(mVillage.getEchHost(),mVillage.getEchDbName(),mVillage.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword, mapR.getCCode());
					//System.out.println("khataMerge "+kMergeCount);
					
									
					//Object finalLrdCount = new Object();				
					//int finalLrdCount = ( demandGenrationCount + kMergeDtlCount ) - kMergeCount;
					
					int f7kCount =   lgdMapperRepository.getKhataF7k(mapR.getCCode(),mapR.getTalukaCode());
					System.out.println("satic Khata "+f7kCount);
					
				//	System.out.println("countLive<====>challan"+lstChallan);
					
							if( demandGenrationCount >= f7kCount) 
							{
								 
								LGDMapper lgdMapper = lgdMapperRepository.findByIsDemandCompleted(mapR.getCCode(),mapR.getTalukaCode());
								if(lgdMapper != null) {
									lgdMapper.setIsDemandCompleted('Y');
									lstLGDMapper.add(lgdMapper);
								}
							
							}
							else
							{
								LGDMapper lgdMapper = lgdMapperRepository.findByIsDemandNotCompleted(mapR.getCCode(),mapR.getTalukaCode());
								if(lgdMapper != null) {
									lgdMapper.setIsDemandCompleted('N');
									lstLGDMapper.add(lgdMapper);
								}
							}
						}
					}
				// }
				
				
		
				if(lstLGDMapper != null & lstLGDMapper.size() > 0) {
					lgdMapperRepository.saveAll(lstLGDMapper);
				}
				response.setLgdMapperDao(lstLGDMapper);
	
				
				
				log.info("Job Executed Successfully...!!!");
			}catch (Exception e) {
				log.info("Error Occured During Execution...!!!");
				e.printStackTrace();
			}
		
		return null;	
	}
	
	@WithDatabase(DataSourceType.echmst)
	public List<LGDMapper> getGaonNamumeCompleted(String talukaCode,String districtCode)
	{
		try {
			
			List<LGDMapper> mapper=lgdMapperRepository.findAll();
			Response response = new Response();
			List<LGDMapper> lstLGDMapper = new ArrayList<LGDMapper>();
			int loopvar=0;
			for(LGDMapper mapR:mapper) 
			{
				++loopvar;
				System.out.println("Count ="+loopvar+" Ccode "+mapR.getCCode());
				// if(mapR.getCCode().equals("272400110296420000"))
				// {	
				if((mapR.getTalukaCode().trim().equals(talukaCode)) && (mapR.getDistrictCode().trim().equals(districtCode) )) 
				 {
			 // System.out.println("countLive<====>EchSchemaName"+mapR.getEchSchemaName());
				//System.out.println("countLive<====>EchSchemaName"+mapR.getCCode());
				int lstGaonNamynaCount=(Integer)lgdMapperRepository.findBycCodeNamune(mapR.getCCode(),mapR.getTalukaCode());
				
			//	System.out.println("countLive<====>challan"+lstChallan);				
				if(lstGaonNamynaCount >=0) 
				{ 
						if( lstGaonNamynaCount  == 17) 
						{
							 
							LGDMapper lgdMapper = lgdMapperRepository.findBycCodeGaonNamuna(mapR.getCCode(),mapR.getTalukaCode());
							if(lgdMapper != null) {
								lgdMapper.setIsGaonNamunaAdded('Y');
								lstLGDMapper.add(lgdMapper);
							}
						
						}
						else
						{
							LGDMapper lgdMapper = lgdMapperRepository.findBycCodeGaonNamunaNtype(mapR.getCCode(),mapR.getTalukaCode());
							if(lgdMapper != null) {
								lgdMapper.setIsGaonNamunaAdded('N');
								lstLGDMapper.add(lgdMapper);
							}
						}
					//}
				}
			  }
			}
			
	
			if(lstLGDMapper != null & lstLGDMapper.size() > 0) {
				lgdMapperRepository.saveAll(lstLGDMapper);
			}
			response.setLgdMapperDao(lstLGDMapper);

			
			
			log.info("Job Executed Successfully...!!!");
		}catch (Exception e) {
			log.info("Error Occured During Execution...!!!");
			e.printStackTrace();
		}
		return null;	
	}
	

	
	
	/*************-------------------------------------------------------------******************/
	
/* 100% vasuli zaleli sankhya   */
	
	@WithDatabase(DataSourceType.echmst)
	public List<LGDMapper> getTotalVasuliCompletedCount(String talukaCode,String districtCode) {
		try {
			
				List<LGDMapper> mapper=lgdMapperRepository.findAll();
				Response response = new Response();
				List<LGDMapper> lstLGDMapper = new ArrayList<LGDMapper>();
				int loopvar=0;
				for(LGDMapper mapR:mapper) 
				{
					++loopvar;
					// if(mapR.getDistrictCode().trim().equals(districtCode) ) 
					//if( (mapR.getTalukaCode().trim().equals(talukaCode)) && (mapR.getDistrictCode().trim().equals(districtCode))   ) 
					//if(mapR.getDistrictCode().trim().equals(districtCode) ) 
					//if( (mapR.getTalukaCode().trim().equals(talukaCode)) && (mapR.getDistrictCode().trim().equals(districtCode))   ) 
					if(mapR.getDistrictCode().trim().equals(districtCode) ) 
					{
						// System.out.println("Before Village-----------------------");
						 
					 // if(mapR.getCCode().equals("270300020017780000")) {
					
						System.out.println("Count ="+loopvar+" Ccode "+mapR.getCCode());
				  //System.out.println("countLive<====>EchSchemaName"+mapR.getEchSchemaName());
					//System.out.println("countLive<====>EchSchemaName"+mapR.getCCode());
					//Integer demandGenrationCount=(Integer)lgdMapperRepository.findBycCodeNamune(mapR.getCCode(),mapR.getTalukaCode());\
					
					if(mapR.getCCode().equals("AB1600010207130000") || mapR.getCCode().equals("AB1600010207440000") || mapR.getCCode().equals("AB1600010207620000")|| mapR.getCCode().equals("AB1600020207840000")|| mapR.getCCode().equals("AB1600020207910000")|| mapR.getCCode().equals("AB1600020208320000")|| mapR.getCCode().equals("AB1600020208340000")|| mapR.getCCode().equals("AB1600020208400000")|| mapR.getCCode().equals("AB1600020208450000")|| mapR.getCCode().equals("AB1600020208800000")|| mapR.getCCode().equals("AB1600020208830000")|| mapR.getCCode().equals("AB1600020208840000")|| mapR.getCCode().equals("AB1600020208890000")|| mapR.getCCode().equals("AB1600020208900000")|| mapR.getCCode().equals("AB1600020208940000")|| mapR.getCCode().equals("AB1600020208950000")|| mapR.getCCode().equals("AB1600020208970000")|| mapR.getCCode().equals("AB1600040210560000")|| mapR.getCCode().equals("AB1600040210750000")|| mapR.getCCode().equals("AB1600040210820000")|| mapR.getCCode().equals("AB1600040210920000")|| mapR.getCCode().equals("AB1600040210950000")|| mapR.getCCode().equals("AB1600040211130000")|| mapR.getCCode().equals("AB1600040211460000")|| mapR.getCCode().equals("AB1600040211970000")|| mapR.getCCode().equals("AB1600050212190000")|| mapR.getCCode().equals("AB1600050212270000")|| mapR.getCCode().equals("AB1600050212500000")|| mapR.getCCode().equals("AB1600050212870000")|| mapR.getCCode().equals("AB1600050213330000")|| mapR.getCCode().equals("AB1600050213340000") )
					{
						continue;
					}
						
					MVillageMapper mVillage = mVillageMapperRepository.serchByschemabyTaluka(mapR.getTalukaCode().trim(),mapR.getDistrictCode().trim());
					
					mVillageMapperRepository.findAll();
					System.out.println("Before Demand QQQQ");
					
					int vasuliCompletedCount = lgdMapperRepository.getVasuliCompleted(mVillage.getEchHost(),mVillage.getEchDbName(),mVillage.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword, mapR.getCCode());
					
					System.out.println("lrd Count "+vasuliCompletedCount);
					if(vasuliCompletedCount <=4)
					{
						continue;// If no record for demand then go to next
					}
					// ( vasuliCount + kMergeDtlCount ) - kMergeCount;
					
					int f7kCount =   lgdMapperRepository.getKhataF7k(mapR.getCCode(),mapR.getTalukaCode());
					System.out.println("khataMergeDtlCount"+f7kCount);					
				
							if( vasuliCompletedCount >= f7kCount) 
							{
								 
								LGDMapper lgdMapper = lgdMapperRepository.findByIsVasuliCompleted(mapR.getCCode(),mapR.getTalukaCode());
								if(lgdMapper != null) {
									lgdMapper.setIsVasuliCompleted('Y');
									lstLGDMapper.add(lgdMapper);
								}
							
							}
							else
							{
								LGDMapper lgdMapper = lgdMapperRepository.findByIsVasuliNotCompleted(mapR.getCCode(),mapR.getTalukaCode());
								if(lgdMapper != null) {
									lgdMapper.setIsVasuliCompleted('N');
									lstLGDMapper.add(lgdMapper);
								}
							}
					   }
						//}
					 }
			
				
				
		
				if(lstLGDMapper != null & lstLGDMapper.size() > 0) {
					lgdMapperRepository.saveAll(lstLGDMapper);
				}
				response.setLgdMapperDao(lstLGDMapper);
	
				
				
				log.info("Job Executed Successfully...!!!");
			}catch (Exception e) {
				log.info("Error Occured During Execution...!!!");
				e.printStackTrace();
			}
		
		return null;	
	}

  /*   end of  100% vasuli zaleli sankhya    */
	
//	@Scheduled(cron = "00 01 00 * * ?")
	@WithDatabase(DataSourceType.echmst)
	public List<LGDMapper> getDashboard() {
		try {
			List<MVillageMapper> mapper=mVillageMapperRepository.findAll();
			Response response = new Response();
			List<DashboardSupervisoryDtl>  lstDashboardSupervisoryDtl= new ArrayList<DashboardSupervisoryDtl>();
			for(MVillageMapper mapR:mapper) {
				System.out.println("countLive<====>EchSchemaName"+mapR.getEchSchemaName());
				List<Object[]> lstChallan=challanRepository.findBycCodeAndRevenueYearAndActiveFlag(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword);
				System.out.println("countLive<====>challan"+lstChallan);
				
				if(lstChallan != null && lstChallan.size() > 0) {
					
					for(Iterator iterator = lstChallan.iterator(); iterator.hasNext();) {
						Object [] obj = (Object[]) iterator.next();
						if(obj != null && obj[0] != null) {
							DashboardSupervisoryDtl dashboardSupervisoryDtl=new DashboardSupervisoryDtl();
							LGDMapper lgd = lgdMapperRepository.findByIsChallanGenerated(obj[0].toString());
							if(lgd != null) {
								if(!dashboardSupervisoryDtlRepository.existsBycCode(obj[0].toString())) {
								dashboardSupervisoryDtl.setCCode(lgd.getCCode());
								dashboardSupervisoryDtl.setDivisionCode(lgd.getDivisionCode());
								dashboardSupervisoryDtl.setDistrictCode(lgd.getDistrictCode());
								dashboardSupervisoryDtl.setDistrictName(lgd.getDistrictNameMarathi());
								dashboardSupervisoryDtl.setTalukaCode(lgd.getTalukaCode());
								dashboardSupervisoryDtl.setTalukaName(lgd.getTalukaMameMarathi());
								dashboardSupervisoryDtl.setVillageName(lgd.getLgdVillageMame());
								lstDashboardSupervisoryDtl.add(dashboardSupervisoryDtl);
								}
							}
						}
					}
				}
			}
			if(lstDashboardSupervisoryDtl != null & lstDashboardSupervisoryDtl.size() > 0) {
				dashboardSupervisoryDtlRepository.saveAll(lstDashboardSupervisoryDtl);
			}
			response.setDashboardSupervisoryDtl(lstDashboardSupervisoryDtl);
			log.info("Job Executed Successfully...!!!");
		}catch (Exception e) {
			log.info("Error Occured During Execution...!!!");
			e.printStackTrace();
		}
		return null;	
	}
	
	
//	@Scheduled(cron = "00 30 00 * * ?")
	@WithDatabase(DataSourceType.echmst)
	public void getCountOfKhataNumbersa() {
		try {
			System.out.println("Schedular started<====>");
			List<MVillageMapper> mapper=mVillageMapperRepository.findAll();
			Response response = new Response();
			List<DivisionDistrictTalukaCount> khataNoCountLives = new ArrayList<DivisionDistrictTalukaCount>();
			int cnt=1;
			for(MVillageMapper mapR:mapper) { 
				Long result=villageRepository.getKhataCount(CommonConstants.AugDatamhrHost,mapR.getMhrDbName(),mapR.getMhrSchemaName(),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,mapR.getDistrictCode(),mapR.getTalukaCode());
				System.out.println("total entry count<====>"+result);
				System.out.println("db<====>"+mapR.getMhrDbName());
				System.out.println("schema<====>"+mapR.getMhrSchemaName());
				cnt++;
				System.out.println("Count<====>"+cnt);
				DivisionDistrictTalukaCount countLive=new DivisionDistrictTalukaCount();
				countLive=divisionDistrictTalukaCountRepository.findByDistrictCodeAndTalukaCode(mapR.getDistrictCode(),mapR.getTalukaCode());
				if(result!=null) {
					countLive.setTotalKhataNumbers(result);
				}
				System.out.println("countLive<====>"+countLive);
				khataNoCountLives.add(countLive);
			}
			System.out.println("Schedular end <====>");
			response.setDivisionDistrictTalukaCount(khataNoCountLives);
			divisionDistrictTalukaCountRepository.saveAll(response.getDivisionDistrictTalukaCount());
//			restTemplate.postForEntity("http://localhost:8091/echawdi/auth/persistTalukasCount", response, boolean.class);
			log.info("Job Executed Successfully...!!!");
		}catch (Exception e) {
			log.info("Error Occured During Execution...!!!");
			e.printStackTrace();
		}
	}

	
	
	//@Scheduled(cron = "0 * * * * ?")
	@WithDatabase(DataSourceType.echmst)
	public List<LgdMapperMis> getMISNetVasuli() {
		try {
			List<MVillageMapper> mapper=mVillageMapperRepository.findAll();

			Response response = new Response();

			List<LgdMapperMis> totalDemandAmt = new ArrayList<LgdMapperMis>();

			List<LgdMapperMis> totalCollectedAmt = new ArrayList<LgdMapperMis>();
			
			Integer count=0;
			for(MVillageMapper mapR:mapper) 
			{	
				if( mapR.getDistrictCode().equals("22"))
				{	
				List<Object[]> lstChallan=landRevenueDemandRepository.findBycCodeForTotalDemandAmt(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,CommonConstants.currentYear);
			
				if(lstChallan != null && lstChallan.size() > 0) {
					for(Iterator iterator = lstChallan.iterator(); iterator.hasNext();) {
						
						Object [] obj = (Object[]) iterator.next();
						
						if(obj != null && obj[0] != null) {
							
						
							LgdMapperMis lgdMappermis= lgdMapperMisRepository.findBycCodeAndTalukaCode(obj[0].toString(), mapR.getTalukaCode());
	
							
						
							System.out.println("NetVasuliCounter ---- :"+count++);
							
							if(lgdMappermis != null) {
						 
								Integer  netJm = ((Number) obj[1]).intValue();
								
								Integer netSankirn  = ((Number) obj[2]).intValue();
							
								lgdMappermis.setTotalDemandJm(netJm);
								lgdMappermis.setTotalDemandSankirn(netSankirn);
				
								totalDemandAmt.add(lgdMappermis);
							}
						
						}
					}
				}
				
				if(totalDemandAmt != null & totalDemandAmt.size() > 0) {
					lgdMapperMisRepository.saveAll(totalDemandAmt);

				}
				
				
				List<Object[]> totalCollected=landRevenueDemandRepository.findBycCodeForCollectedAmt(mapR.getEchHost(),mapR.getEchDbName(),mapR.getEchSchemaName(),CommonConstants.echUser,CommonConstants.echPassword,CommonConstants.currentYear);
				
				
				
				if(totalCollected != null && totalCollected.size() > 0) {
					for(Iterator iterator = totalCollected.iterator(); iterator.hasNext();) {
						
						Object [] obj = (Object[]) iterator.next();
						
						if(obj != null && obj[0] != null) {
							
						
							LgdMapperMis lgdMappermis = lgdMapperMisRepository.findBycCodeAndTalukaCode(obj[0].toString(), mapR.getTalukaCode());
							
							if(lgdMappermis != null) {
							
								Integer collectedJm = ((Number) obj[1]).intValue();
								Integer collectedSankirn  = ((Number) obj[2]).intValue();
								
								lgdMappermis.setTotalCollectedJm(collectedJm);
								
								lgdMappermis.setTotalCollectedSankirn(collectedSankirn);
								
	
								totalCollectedAmt.add(lgdMappermis);
							}
						
						}
					}
				}
				
				if(totalCollectedAmt != null & totalCollectedAmt.size() > 0) {
					lgdMapperMisRepository.saveAll(totalCollectedAmt);

				}
									 
			
			response.setLgdMapperMisDao(totalCollectedAmt);
			}
			}	
			
			log.info("Job Executed Successfully...!!!");
		}catch (Exception e) {
			log.info("Error Occured During Execution...!!!");
			e.printStackTrace();
		}
		return null;	
	}
	
	
	
	
	
	
	
	
	
//	@Scheduled(cron = "00 45 23 * * ?")
//	@WithDatabase(DataSourceType.echmst)
//	public Response getDataToMakeLive() {
//		Response response=new Response();
//		response.setLgdMapperDao(lgdMapperRepository.findByIsLoggedInIsNull().stream().map(r->{
//			LGDMapperDao dao=new LGDMapperDao();
//			BeanUtils.copyProperties(r, dao);
//			return dao;
//			}).collect(Collectors.toList()));
//		return response;
//	}
	
//	@Scheduled(cron = "00 40 23 * * ?")
//	@WithDatabase(DataSourceType.echmst)
//	public ResponseEntity<?> persistDataToMakeLive(){
//		Response response=new Response();
//		try{
//			lgdMapperRepository.saveAll(response.getLgdMapperDao().stream().map(r->{
//				LGDMapper mapper =new LGDMapper();
//				BeanUtils.copyProperties(r, mapper);
//				return mapper;
//			}).collect(Collectors.toList()));
//			return ResponseEntity.ok("Data Persisted Successfully");
//		}catch(Exception e) {
//			e.printStackTrace();
//			return  (ResponseEntity<?>) ResponseEntity.internalServerError();
//		}
//	}
	
}