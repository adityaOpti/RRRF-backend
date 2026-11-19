<input type="hidden" id="userList" th:field="*{userList}" th:value="${userList}">

		<script>
				$(document).ready(function(){
					$('#fileReceiverSenderDepartmentKey').change(function() {
						getUsersList();
					});
	
					function getUsersList(){
						var deptId = document.getElementById("fileReceiverSenderDepartmentKey");
						var selectedDeptId = null;
						
						if(deptId != null && deptId != 'undefined' && deptId != ""){
							selectedDeptId = deptId.options[deptId.selectedIndex].value;
							console.log("selectedDeptId",selectedDeptId);
						}
						
						if(selectedDeptId != null && selectedDeptId != 'undefined' && selectedDeptId.trim() != ""){
							$.ajax({
						        url: contextRoot+'api/departments/getUseList?_csrf='+$('[name=_csrf]').val(),
						        dataType:"json",
						        type: "post",
						        data: { 
						        		"deptId": selectedDeptId
						        },
						        success: function(data){
						        	userList.value = data[0];
									console.log("userList",data[0]);
									console.log("userList==+",userList.value);
						        }
						    });
						} else {
							userList.value = null;
						}
					}
				});	
		</script>

		
		--get District Name
select * from
echpun_bar.public.dblink (
'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'select district_code,district_name from '|| 'mhrorpun_bar' || '.m_district_census d') 
AS F7 (district_code text,district_name text)
where district_code='25'


--getVillageList
select * from
public.dblink (
'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'SELECT v.ccode, o.username, o.user_type, v.village_code, v.village_name FROM '
||'mhrorpun_bar'||'.m_village_census v,'||'mhrorpun_bar'||'.m_officermast o') 
AS data (ccode text,username text,user_type text,village_code text,village_name text)
where user_Type = '1' AND trim(username) = 'REVVSTM8001'


--find hissano
select CONCAT(
case when pin1 <> '' then   		   pin1 end,
case when pin2 <> '' then concat('/' , pin2) end,
case when pin3 <> '' then concat('/' , pin3) end,
case when pin4 <> '' then concat('/' , pin4) end,
case when pin5 <> '' then concat('/' , pin5) end,
case when pin6 <> '' then concat('/' , pin6) end,
case when pin7 <> '' then concat('/' , pin7) end,
case when pin8 <> '' then concat('/' , pin8) end)
as Hissa_No  from public.dblink
('dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'SELECT cCode,marked,F7.pin,F7.pin1,F7.pin2,F7.pin3,F7.pin4,F7.pin5,F7.pin6,F7.pin7,F7.pin8
FROM '
||'mhrorpun_bar'||'.FORM7 AS F7')
AS Data(cCode text,marked character(1),pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text)
WHERE
cCode ='272500130318050000' 
AND pin ='1' 
AND marked !='Y'



--form1FerFarDeatil
select * from (SELECT 
cCode,
pin,
assessment,
pin1,
net_Culti_Area_H,
total_Area_H,
na_Assessment,
potkharaba_A_H,
potkharaba_B_H,   
CONCAT(    
case when pin1 <> '' then   	pin1 end,   
case when pin2 <> '' then '/' || pin2 end,    
case when pin3 <> '' then '/' || pin3 end,    
case when pin4 <> '' then '/' || pin4 end,    
case when pin5 <> '' then '/' || pin5 end,    
case when pin6 <> '' then '/' || pin6 end,    
case when pin7 <> '' then '/' || pin7 end,    
case when pin8 <> '' then '/' || pin8 end     
)as hissaNo,
tenure_Name,
KHAND_NO    
FROM  
public.dblink(
'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'select 
F7.cCode,F7.KHAND_NO,F7.marked,F7.pin,F7.pin1,F7.pin2,F7.pin3,F7.pin4,F7.pin5,F7.pin6,F7.pin7,F7.pin8,
f7a.assessment,f7a.net_Culti_Area_H,f7a.total_Area_H,f7a.na_Assessment,f7a.potkharaba_A_H,f7a.potkharaba_B_H,   
t.tenure_Name
from '
|| 'mhrorpun_bar' || '.form7 f7,'
|| 'mhrorpun_bar' || '.form7_area f7a,'
|| 'mhrorpun_bar' || '.m_tenure t 
WHERE    
	F7.tenure_Code = T.tenure_Code    
AND F7.ccode=f7a.ccode    
AND F7.pin = f7a.pin 
AND F7.pin1 = f7a.pin1
AND F7.pin2 = f7a.pin2 
AND F7.pin3 = f7a.pin3 
AND F7.pin4 = f7a.pin4    
AND F7.pin5 = f7a.pin5    
AND F7.pin6 = f7a.pin6    
AND F7.pin7 = f7a.pin7    
AND F7.pin8 = f7a.pin8'    
)AS Data(
cCode text,
KHAND_NO text,
marked text,
pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,
assessment text,
net_Culti_Area_H text,
total_Area_H text,
na_Assessment text,
potkharaba_A_H text,
potkharaba_B_H text,   
tenure_Name text)
where 
	cCode = '272500130318050000'     
and pin = '1'    
and marked != 'Y')A where  hissaNo = '1'

--form1AllData(Table form)

select  
  tenure_Name,assessment,net_Culti_Area,total_Area_H,na_Assessment,  
  EF1.public_Rights_Of_Way_And_Easements,EF1.particulars_Of_Alteration,EF1.designation,EF1.remarks,EF1.order_no,EF1.order_date,  
  EF1.pin,EF1.id, CONCAT(  
  case when EF1.pin1 != ' ' then   	 EF1.pin1 end,  
  case when EF1.pin2 != ' ' then  '/'||EF1.pin2 end,  
  case when EF1.pin3 != ' ' then  '/'||EF1.pin3 end,  
  case when EF1.pin4 != ' ' then  '/'||EF1.pin4 end,  
  case when EF1.pin5 != ' ' then  '/'||EF1.pin5 end,  
  case when EF1.pin6 != ' ' then  '/'||EF1.pin6 end,  
  case when EF1.pin7 != ' ' then  '/'||EF1.pin7 end,  
  case when EF1.pin8 != ' ' then  '/'||EF1.pin8 end  
  )as hissaNo,potkharaba_a_h,potkharaba_b_h 
  from 
   (select * from public.dblink
   ('dbname='|| 'echpun' ||' host='|| 'localhost' ||' port=5432 user='||'postgres'|| ' password='|| 'root' ,
		 'select 
		  EF1.id, 
		  EF1.public_Rights_Of_Way_And_Easements,
		  EF1.particulars_Of_Alteration,
		  EF1.designation,
		  EF1.remarks,
		  EF1.order_no,
	      EF1.order_date, 
		  EF1.pin,
		  EF1.pin1,  
		  EF1.pin2,  
		  EF1.pin3,  
		  EF1.pin4,  
		  EF1.pin5,  
		  EF1.pin6,  
		  EF1.pin7,  
		  EF1.pin8,
		  EF1.ccode,
		  ef1.active_flag
		  from '||'echpun_bar'||'.echawdi_form1 ef1') 
		  AS 
		  EF1(
		  id bigint, 
		  public_Rights_Of_Way_And_Easements text,
		  particulars_Of_Alteration text,
		  designation text,
		  remarks text,
		  order_no text,
		  order_date text, 
		  pin text,
		  pin1 text,  
		  pin2 text,  
		  pin3 text,  
		  pin4 text,  
		  pin5 text,  
		  pin6 text,  
		  pin7 text,  
		  pin8 text,
		  ccode text,
		  active_flag character(1)) 
	  where  ccode='272500130318050000' and pin='1' and active_flag='Y') As EF1
		  ,
  public.dblink ( 
  'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user='||'postgres'|| ' password='|| 'root' ,
  'select  
  f7.ccode,tenure_Name, 
  F7A.assessment, 
  F7A.net_Culti_Area_H, 
  F7A.total_Area_H, 
  F7A.na_Assessment, 
  F7A.potkharaba_a_h, 
  F7A.potkharaba_b_h, 
  f7.pin,f7.pin1,f7.pin2,f7.pin3,f7.pin4, 
  f7.pin5,f7.pin6,f7.pin7,f7.pin8 
  from ' 
  || 'mhrorpun_bar' || '.form7 f7,' 
  || 'mhrorpun_bar' || '.form7_area f7a,' 
  || 'mhrorpun_bar' || '.m_tenure t 
   where  
   f7a.ccode=f7.ccode  
   and t.tenure_code=f7.tenure_code				    
   and f7.pin=f7a.pin and f7.pin=f7a.pin  
   and f7.pin1=f7a.pin1 and f7.pin2=f7a.pin2  
   and f7.pin3=f7a.pin3 and f7.pin4=f7a.pin4  
   and f7.pin5=f7a.pin5 and f7.pin6=f7a.pin6  
   and f7.pin7=f7a.pin7 and f7.pin8=f7a.pin8' 
  )                     
  AS F7                 
  (                     
   ccode text,          
   tenure_name text,    
   assessment text,     
   net_culti_area text, 
   total_Area_H text,   
   na_Assessment text,  
   potkharaba_a_h text, 
   potkharaba_b_h text, 
   pin text,
   pin1 text,  
   pin2 text, 
   pin3 text, 
   pin4 text, 
   pin5 text, 
   pin6 text, 
   pin7 text, 
   pin8 text  
  ) 
  where  
  ef1.ccode=f7.ccode   
  and ef1.pin=f7.pin   
  and trim(ef1.pin1)=trim(f7.pin1)   
  and trim(ef1.pin2)=trim(f7.pin2)   
  and trim(ef1.pin3)=trim(f7.pin3)   
  and trim(ef1.pin4)=trim(f7.pin4)   
  and trim(ef1.pin5)=trim(f7.pin5)   
  and trim(ef1.pin6)=trim(f7.pin6)   
  and trim(ef1.pin7)=trim(f7.pin7)   
  and trim(ef1.pin8)=trim(f7.pin8)   



--form1Report
SELECT   
fPIN,   
TENURE_NAME,   
ASSESSMENT,   
NET_CULTI_AREA_H,   
TOTAL_AREA_H,   
NA_ASSESSMENT,   
POTKHARABA_A_H,   
POTKHARABA_B_H,   
PUBLIC_RIGHTS_OF_WAY_AND_EASEMENTS,   
PARTICULARS_OF_ALTERATION,   
DESIGNATION,   
REMARKS,   
ORDER_NO,   
ORDER_DATE,   
hissaNof   
from   
(  
SELECT   
*    
FROM   
(   
select   
PIN as fpin,   
CCODE,  
CONCAT(CONCAT(CASE WHEN PIN1 != '' THEN PIN1 END),   
CONCAT(CASE WHEN PIN2 != '' THEN '/' || PIN2 END),   
CONCAT(CASE WHEN PIN3 != '' THEN '/' || PIN3 END),   
CONCAT(CASE WHEN PIN4 != '' THEN '/' || PIN4 END),   
CONCAT(CASE WHEN PIN5 != '' THEN '/' || PIN5 END),   
CONCAT(CASE WHEN PIN6 != '' THEN '/' || PIN6 END),   
CONCAT(CASE WHEN PIN7 != '' THEN '/' || PIN7 END),   
CONCAT(CASE WHEN PIN8 != '' THEN PIN8  END)) as hissaNof,    
TENURE_NAME,ASSESSMENT,NET_CULTI_AREA_H,total_Area_H,   
NA_ASSESSMENT,POTKHARABA_A_H,POTKHARABA_B_H  
from    
public.dblink( 
'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user='||'postgres'|| ' password='|| 'root' , 
'SELECT   
F7.PIN AS FPIN,   
F7.CCODE,  
f7.pin1,f7.pin2,f7.pin3,f7.pin4,f7.pin5,f7.pin6,f7.pin7,f7.pin7,  
T.TENURE_NAME, F7A.ASSESSMENT, F7A.NET_CULTI_AREA_H, f7a.total_Area_H,   
F7A.NA_ASSESSMENT, F7A.POTKHARABA_A_H, F7A.POTKHARABA_B_H,f7.marked    
FROM   
'||'mhrorpun_bar'||'.form7_AREA F7A, '||'mhrorpun_bar'||'.form7 F7, '||'mhrorpun_bar'||'.m_tenure T    
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
AND T.TENURE_CODE=F7.TENURE_CODE  
'    
)  
As ferfar  
(  
PIN text,   
CCODE text,  
PIN1 text,   
PIN2 text,   
PIN3 text,   
PIN4 text,   
PIN5 text,   
PIN6 text,   
PIN7 text,   
PIN8 text,   
TENURE_NAME text,  
ASSESSMENT text,  
NET_CULTI_AREA_H text,  
total_Area_H text,   
NA_ASSESSMENT text,  
POTKHARABA_A_H text,  
POTKHARABA_B_H text,  
marked text  
)    
where   
MARKED <> 'Y'    
and ccode='272500130318050000'  
AND   
(  
CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/',   
PIN5, '/', PIN6, '/', PIN7, '/', PIN8) NOT IN    
( SELECT   
CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)    
FROM   
public.dblink( 
'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user='||'postgres'|| ' password='|| 'root' , 
'select pin,pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,ccode,khata_no from '||'mhrorpun_bar'||'.form7_KHATA FK')  
as f7k(pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,ccode text,khata_no text)    
WHERE 	   
CCODE ='272500130318050000' AND KHATA_NO = '500001' )  
)  
)AS T1   
left join 
(select * from 
(select   
public_Rights_Of_Way_And_Easements,
particulars_Of_Alteration,
designation,
remarks,
order_no,
order_date,   
pin,
id,
ccode,
CONCAT(   
case when pin1 != ' ' then   	  pin1 end,   
case when pin2 != ' ' then  '/'|| pin2 end,   
case when pin3 != ' ' then  '/'|| pin3 end,   
case when pin4 != ' ' then  '/'|| pin4 end,   
case when pin5 != ' ' then  '/'|| pin5 end,   
case when pin6 != ' ' then  '/'|| pin6 end,   
case when pin7 != ' ' then  '/'|| pin7 end,   
case when pin8 != ' ' then  '/'|| pin8 end   
)as hissaNo from 
public.dblink('dbname='|| 'echpun' ||' host='|| 'localhost' ||' port=5432 user='||'postgres'|| ' password='|| 'root' ,
'select 
ef1.public_Rights_Of_Way_And_Easements,
ef1.particulars_Of_Alteration,
ef1.designation,
ef1.remarks,
ef1.order_no,
ef1.order_date,   
EF1.id,
ef1.ccode,ef1.pin,
ef1.pin1,ef1.pin2,
ef1.pin3,ef1.pin4,
ef1.pin5,ef1.pin6,
ef1.pin7,ef1.pin8,
ef1.active_flag 
from '
||'echpun_bar'||'.echawdi_form1 ef1') 
AS 
EF1(
	public_Rights_Of_Way_And_Easements text,
	particulars_Of_Alteration text,
	designation text,
	remarks text,
	order_no text,
	order_date text,   
	id bigint,
	ccode text,
	pin text,pin1 text,
	pin2 text,pin3 text,
	pin4 text,pin5 text,
	pin6 text,pin7 text,
	pin8 text,active_flag character(1)
   )
where ccode='272500130318050000' and active_flag='Y') 
as t2 ) 
as t3  
on  
(t1.ccode=t3.ccode and t1.fpin=t3.pin and t1.hissanof=t3.hissano))A 



--form 1a getFreferArea

select 
cCode,
pin,
pin1,
pin2,
pin3,
pin4,
pin5,
pin6,
pin7,
pin8,
total_area_h,
CONCAT(     
case when pin1 <> '' then       pin1 end,     
case when pin2 <> '' then '/'|| pin2 end,      
case when pin3 <> '' then '/'|| pin3 end,     
case when pin4 <> '' then '/'|| pin4 end,     
case when pin5 <> '' then '/'|| pin5 end,     
case when pin6 <> '' then '/'|| pin6 end,     
case when pin7 <> '' then '/'|| pin7 end,     
case when pin8 <> '' then '/'|| pin8 end     
)as hissaNo,
total_area_r,
total_area_sqm
from  
public.dblink
(
'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user='||'postgres'|| ' password='|| 'root' ,
'SELECT   
F7.cCode,
F7.pin,
F7.pin1,F7.pin2,F7.pin3,F7.pin4,
F7.pin5,F7.pin6,F7.pin7,F7.pin8,
f7a.total_area_h,f7a.total_area_r,f7a.total_area_sqm,
F7.marked
from '
||'mhrorpun_bar'||'.FORM7 F7,'
||'mhrorpun_bar'||'.FORM7_area F7a 
WHERE     
F7.ccode=f7a.ccode
AND F7.pin = f7a.pin 
AND F7.pin1 = f7a.pin1 
AND F7.pin2 = f7a.pin2 
AND F7.pin3 = f7a.pin3 
AND F7.pin4 = f7a.pin4     
AND F7.pin5 = f7a.pin5     
AND F7.pin6 = f7a.pin6     
AND F7.pin7 = f7a.pin7     
AND F7.pin8 = f7a.pin8'
)
AS 
Ferfar
(
cCode text,
pin text,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text,
total_area_h text,
total_area_r text,
total_area_sqm text,
marked character(1)
) where 
cCode = '272500130318050000'   
AND pin = '1' 
AND marked != 'Y'
AND CONCAT(     
case when pin1 <> '' then       pin1 end,     
case when pin2 <> '' then '/'|| pin2 end,      
case when pin3 <> '' then '/'|| pin3 end,     
case when pin4 <> '' then '/'|| pin4 end,     
case when pin5 <> '' then '/'|| pin5 end,     
case when pin6 <> '' then '/'|| pin6 end,     
case when pin7 <> '' then '/'|| pin7 end,     
case when pin8 <> '' then '/'|| pin8 end     
)='3'




--form1B assessment and area from ferfar 
SELECT 
cCode,
CONCAT( 
case when pin1 <> '' then   	pin1 end, 
case when pin2 <> '' then '/'|| pin2 end, 
case when pin3 <> '' then '/'|| pin3 end, 
case when pin4 <> '' then '/'|| pin4 end, 
case when pin5 <> '' then '/'|| pin5 end, 
case when pin6 <> '' then '/'|| pin6 end, 
case when pin7 <> '' then '/'|| pin7 end, 
case when pin8 <> '' then '/'|| pin8 end 
)
as hissaNo,
total_area_h, 
assessment,
total_area_r,
total_area_sqm
FROM 
public.dblink 
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select 
F7.cCode,
F7.pin,
F7.pin1,
F7.pin2,
F7.pin3,
F7.pin4,
F7.pin5,
F7.pin6,
F7.pin7,
F7.pin8,
F7a.total_area_h, 
F7a.assessment,
F7a.total_area_r,
F7a.total_area_sqm,
F7.marked
from '
||'mhrorpun_bar'||
'.FORM7 F7,'
||'mhrorpun_bar'||
'.FORM7_area  F7a 
where
	F7.ccode=f7a.ccode 
AND F7.pin  = f7a.pin 
AND F7.pin1 = f7a.pin1 
AND F7.pin2 = f7a.pin2 
AND F7.pin3 = f7a.pin3 
AND F7.pin4 = f7a.pin4 
AND F7.pin5 = f7a.pin5 
AND F7.pin6 = f7a.pin6 
AND F7.pin7 = f7a.pin7 
AND F7.pin8 = f7a.pin8'
) 
AS
Ferfar
(
ccode text,
pin  text,
pin1  text,
pin2  text,
pin3  text,
pin4  text,
pin5  text,
pin6  text,
pin7  text,
pin8  text,
total_area_h text,
assessment text,
total_area_r text,
total_area_sqm text,
marked character(1)
)
where 
ccode='272500130318050000' and pin='434' 
and  CONCAT( 
case when pin1 <> '' then   	pin1 end, 
case when pin2 <> '' then '/'|| pin2 end, 
case when pin3 <> '' then '/'|| pin3 end, 
case when pin4 <> '' then '/'|| pin4 end, 
case when pin5 <> '' then '/'|| pin5 end, 
case when pin6 <> '' then '/'|| pin6 end, 
case when pin7 <> '' then '/'|| pin7 end, 
case when pin8 <> '' then '/'|| pin8 end 
)=''
AND marked <>'Y'


---form1B getAll chawadi ferfar combine data 

select 
t1.ccode, 
t1.pin, 
CONCAT(
case when t1.pin1 != ' ' then 		t1.pin1 end, 
case when t1.pin2 != ' ' then '/'|| t1.pin2 end, 
case when t1.pin3 != ' ' then '/'|| t1.pin3 end,  
case when t1.pin4 != ' ' then '/'|| t1.pin4 end, 
case when t1.pin5 != ' ' then '/'|| t1.pin5 end, 
case when t1.pin6 != ' ' then '/'|| t1.pin6 end, 
case when t1.pin7 != ' ' then '/'|| t1.pin7 end, 
case when t1.pin8 != ' ' then '/'|| t1.pin8 end
)as HissaNo, 
total_area_h,
assessment,
id,
unassessed_area,
public_rights_of_way_and_easements,
remarks 
from 
(
select 
*
from 
public.dblink('dbname=echpun host=localhost user=postgres password=root',
'select 
ccode, 
pin, 
pin1, 
pin2, 
pin3,  
pin4, 
pin5, 
pin6, 
pin7, 
pin8,
id,
unassessed_area,
public_rights_of_way_and_easements,
remarks,
active_flag
from echpun_bar.echawdi_form1B ef1b')
AS Chawadi
(
ccode text, 
pin  text, 
pin1 text, 
pin2 text, 
pin3 text,  
pin4 text, 
pin5 text, 
pin6 text, 
pin7 text, 
pin8 text,
id text,
unassessed_area text,
public_rights_of_way_and_easements text,
remarks  text,
active_flag character(1)
)
) as t1,
(
select 
* 
from 
public.dblink('dbname=mhrorpun host=localhost user=postgres password=root',
'select
f7.ccode, 
f7.pin, 
f7.pin1, 
f7.pin2, 
f7.pin3,  
f7.pin4, 
f7.pin5, 
f7.pin6, 
f7.pin7, 
f7.pin8,
f7a.total_area_h,
f7a.assessment
from 
mhrorpun_bar.FORM7_area f7a,
mhrorpun_bar.FORM7 f7 
where  
f7.ccode=f7a.ccode  
and f7.pin=f7a.pin 
and f7.pin1=f7a.pin1 
and f7.pin2=f7a.pin2 
and f7.pin3=f7a.pin3 
and f7.pin4=f7a.pin4  
and f7.pin5=f7a.pin5 
and f7.pin6=f7a.pin6 
and f7.pin6=f7a.pin6 
and f7.pin7=f7a.pin7 
and f7.pin8=f7a.pin8'
)
AS 
ferfar
(
ccode text, 
pin  text, 
pin1 text, 
pin2 text, 
pin3 text,  
pin4 text, 
pin5 text, 
pin6 text, 
pin7 text, 
pin8 text,
total_area_h text,
assessment  text
)
)as t2

where 
 trim(t1.pin1)=trim(t2.pin1) 
and trim(t1.pin2)=trim(t2.pin2) 
and trim(t1.pin3)=trim(t2.pin3)  
and trim(t1.pin4)=trim(t2.pin4) 
and trim(t1.pin5)=trim(t2.pin5) 
and trim(t1.pin6)=trim(t2.pin6) 
and trim(t1.pin7)=trim(t2.pin7) 
and trim(t1.pin8)=trim(t2.pin8) 
and t1.ccode=t2.ccode 
and t1.pin=t2.pin 
and t1.ccode='272500130318050000'
and t1.pin='111' 
and t1.active_flag='Y'


---get KhataOwner name one time entry and form17 khatedar name ferfar

select 
distinct on(khata_no)
STRING_AGG(CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES 
from 
public.dblink(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'SELECT 
fname,mname,lname,khata_no,ccode
FROM 
'||'mhrorpun_bar'||'.FORM7_KHATA F7K'
)AS Fom7Khata
(
fname text,
mname text,
lname text,
khata_no text,
ccode text
)
WHERE 
cCode = '272500130318050000' 
and khata_no='2' 
GROUP BY 
KHATA_NO 



--getForm1D ferfar data

select 
cCode, 
pin, 
CONCAT
( 
case when pin1 <> '' then       pin1 end, 
case when pin2 <> '' then '/'|| pin2 end, 
case when pin3 <> '' then '/'|| pin3 end, 
case when pin4 <> '' then '/'|| pin4 end, 
case when pin5 <> '' then '/'|| pin5 end, 
case when pin6 <> '' then '/'|| pin6 end, 
case when pin7 <> '' then '/'|| pin7 end, 
case when pin8 <> '' then '/'|| pin8 end 
)
as 
hissaNo, 
total_area_h, 
assessment, 
bagayat_area_h,  
jirayat_area_h 
from
public.dblink(
'dbname='||'mhrorpun'||' host= '||'localhost'||' user='||' postgres'||' password='||'root',
'SELECT 
F7.cCode, 
F7.pin, 
F7.pin1,
F7.pin2, 
F7.pin3, 
F7.pin4, 
F7.pin5, 
F7.pin6, 
F7.pin7, 
F7.pin8, 
f7a.total_area_h, 
f7a.assessment, 
f7a.bagayat_area_h,  
f7a.jirayat_area_h,
f7.marked
FROM '
||'mhrorpun_bar'||'.FORM7  F7,'
||'mhrorpun_bar'||'.FORM7_area  F7a  
WHERE     
F7.ccode=f7a.ccode     
AND F7.pin = f7a.pin 
AND F7.pin1 = f7a.pin1 
AND F7.pin2 = f7a.pin2 
AND F7.pin3 = f7a.pin3 
AND F7.pin4 = f7a.pin4     
AND F7.pin5 = f7a.pin5     
AND F7.pin6 = f7a.pin6     
AND F7.pin7 = f7a.pin7     
AND F7.pin8 = f7a.pin8'
) 
AS
Ferfar
(
cCode text, 
pin  text, 
pin1 text,
pin2 text, 
pin3 text, 
pin4 text, 
pin5 text, 
pin6 text, 
pin7 text, 
pin8 text, 
total_area_h text, 
assessment text, 
bagayat_area_h text,  
jirayat_area_h text,
marked character(1)
)
where 
cCode = '272500130318050000'   
AND pin = '1'
AND marked != 'Y'
AND CONCAT
( 
case when pin1 <> '' then       pin1 end, 
case when pin2 <> '' then '/'|| pin2 end, 
case when pin3 <> '' then '/'|| pin3 end, 
case when pin4 <> '' then '/'|| pin4 end, 
case when pin5 <> '' then '/'|| pin5 end, 
case when pin6 <> '' then '/'|| pin6 end, 
case when pin7 <> '' then '/'|| pin7 end, 
case when pin8 <> '' then '/'|| pin8 end 
) = '1'



---getAllForm1D 

select 
*
from 
(
select 
ccode,
pin, 
pin1 ,
pin2 , 
pin3 , 
pin4 , 
pin5 , 
pin6 , 
pin7 ,
pin8 ,
active_flag,
CONCAT(
case when pin1 != ' ' then  	   pin1 end, 
case when pin2 != ' ' then  '/' || pin2 end, 
case when pin3 != ' ' then  '/' || pin3 end, 
case when pin4 != ' ' then  '/' || pin4 end, 
case when pin5 != ' ' then  '/' || pin5 end, 
case when pin6 != ' ' then  '/' || pin6 end, 
case when pin7 != ' ' then  '/' || pin7 end, 
case when pin8 != ' ' then  '/' || pin8 end
) 
as 
HissaNo
from public.dblink
(
'dbname='||'echpun'||' host= '||'localhost'||' user='||' postgres'||' password='||'root',
'select 
ccode, 
pin, 
pin1 ,
pin2 , 
pin3 , 
pin4 , 
pin5 , 
pin6 , 
pin7 ,
pin8 ,
id,
remarks,
active_flag
from '
||'echpun_bar'||'.echawdi_form1D EF1D'
)
AS 
Chaewadi
(
ccode text, 
pin text, 
pin1  text,
pin2  text, 
pin3  text, 
pin4  text, 
pin5  text, 
pin6  text, 
pin7  text,
pin8  text,
id bigint,
remarks text,
active_flag character(1)
)
) 
as 
t1,
(
select 
total_area_h,
assessment,
bagayat_area_h,
jirayat_area_h,
ccode,
pin, 
pin1 ,
pin2 , 
pin3 , 
pin4 , 
pin5 , 
pin6 , 
pin7 ,
pin8 
from public.dblink
(
'dbname='||'mhrorpun'||' host= '||'localhost'||' user='||' postgres'||' password='||'root',
'select 
f7a.total_area_h,
f7a.assessment,
f7a.bagayat_area_h,
f7a.jirayat_area_h,
f7.ccode,
f7.pin, 
f7.pin1 ,
f7.pin2 , 
f7.pin3 , 
f7.pin4 , 
f7.pin5 , 
f7.pin6 , 
f7.pin7 ,
f7.pin8 
from '
||'mhrorpun_bar'||'.FORM7_area f7a,'
||'mhrorpun_bar'||'.FORM7 f7 
where 
f7a.ccode=f7.ccode  
and f7.pin=f7a.pin 
and f7.pin=f7a.pin 
and f7.pin1=f7a.pin1 
and f7.pin2=f7a.pin2 
and f7.pin3=f7a.pin3 
and f7.pin4=f7a.pin4  
and f7.pin5=f7a.pin5 
and f7.pin6=f7a.pin6 
and f7.pin7=f7a.pin7 
and f7.pin8=f7a.pin8'
)
AS
Ferfar
(
total_area_h text,
assessment text,
bagayat_area_h text,
jirayat_area_h text,
ccode text,
pin text, 
pin1  text,
pin2  text, 
pin3  text, 
pin4  text, 
pin5  text, 
pin6  text, 
pin7  text,
pin8  text
)
)
as 
t2
where  
t1.ccode=t2.ccode 
and t1.pin=t2.pin 
and trim(t1.pin1)=trim(t2.pin1) 
and trim(t1.pin2)=trim(t2.pin2) 
and trim(t1.pin3)=trim(t2.pin3)  
and trim(t1.pin4)=trim(t2.pin4) 
and trim(t1.pin5)=trim(t2.pin5) 
and trim(t1.pin6)=trim(t2.pin6) 
and trim(t1.pin7)=trim(t2.pin7) 
and trim(t1.pin8)=trim(t2.pin8) 
and t1.ccode='272500130318050000' 
and t1.pin='1'
and t1.active_flag='Y'

--ote khatano list

SELECT 
KHATA_NO 
FROM 
public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select 
khata_no,
ccode,
revenue_year,
active_flag
from '||'echpun_bar'||'.LR_PENDING_DUES'
)
as 
ote
(
khata_no text,
ccode text,
revenue_year text,
active_flag character(1)
) 
WHERE 
CCODE='272500130318050000' 
AND REVENUE_YEAR='2021-22' 
AND ACTIVE_FLAG='Y'




-----ote genrate revenue 



select * from 
public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select * from '||'echpun_bar'||'.lr_pending_dues o'
) 
AS
OTE
(
	id bigint,
    version integer,
    created_user_id bigint,
    create_dt_tm timestamp without time zone,
    update_userid bigint,
    update_dt_tm timestamp without time zone,
    district_code integer,
    taluka_code integer,
    ccode text,
    khata_no character varying(255),
    miscellaneous_amount numeric(19,2),
    net_pending numeric(19,2),
    pre_year_pending_addl_educational_cess numeric(19,2),
    pre_year_pending_addl_land_revenue numeric(19,2),
    pre_year_pending_educational_cess numeric(19,2),
    pre_year_pending_na_cess numeric(19,2),
    pre_year_pending_employee_guarantee_scheme numeric(19,2),
    pre_year_pending_gp numeric(19,2),
    pre_year_pending_jm numeric(19,2),
    pre_year_pending_zp numeric(19,2),
    pre_year_pending_sankirn_jm_with numeric(19,2),
    pre_year_pending_sankirn_jm_without numeric(19,2),
    pre_year_pending_notice_fee numeric(19,2),
    revenue_year character varying(255),
    active_flag character(1),
    pre_year_notice_fee numeric(19,2),
    pre_year_sankirn_jm_with numeric(19,2),
    pre_year_sankirn_jm_without numeric(19,2)
)
where 
ccode ='272500130318050000' 
and active_flag='Y' 
and revenue_year='2021-22'
and khata_no not in
(
select khata_no from public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select khata_no,ccode,revenue_year from ' 
||'echpun_bar'||'.land_revenue_demand'
)
AS LRD(khata_no text,ccode text,revenue_year text)	
where ccode ='272500130318050000' and revenue_year='2021-22')
order by cast(khata_no as bigint) 




--getAll form17 records

select * from public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select * 
from '
||'echpun_bar'||'.echawdi_form17')
AS
F17
(
id bigint ,
version integer ,
create_dt_tm timestamp without time zone,
created_user_id bigint,
update_userid bigint,
update_dt_tm timestamp without time zone,
revenue_year text ,
district_code integer,
taluka_code integer,
ccode character varying(255) ,
khata_no character varying(255) ,
person_liable character varying(255) ,
survey_hissa character varying(255) ,
total_area numeric(18,2),
uom_of_total_area character varying(255) ,
case_no character varying(255) ,
area_affected numeric(18,2),
uom_of_area_affected character varying(255) ,
nature_of_case character varying(255) ,
period_if_more_than_year character varying(255) ,
period_from_date date,
period_to_date date,
local_cess character varying(255) ,
other_local_cess character varying(255) ,
other_local_cess_desc character varying(255) ,
local_cess_amount numeric(19,2),
other_local_cess_amount numeric(19,2),
amount_of_jm numeric(18,2),
amount_of_zp numeric(18,2),
amount_of_gp numeric(18,2),
assessment numeric(18,2),
remarks character varying(255) ,
active_flag character(1) ,
is_amount_paid character(1) ,
is_demand_generated character(1) ,
khata_type character varying(255) ,
makhta_khata_no character varying(255) 
)
where 
(khata_no='12' or split_part(makhta_khata_no,'/',3) LIKE '12') 
and active_flag='Y'
and cCode='272500130318050000' 
and revenue_year='2021-22'



--getform17 for generate revenue

select * from 
public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select * from '||'echpun_bar'||'.echawdi_form17'
) 
AS
F17
(
id bigint ,
version integer ,
create_dt_tm timestamp without time zone,
created_user_id bigint,
update_userid bigint,
update_dt_tm timestamp without time zone,
revenue_year text ,
district_code integer,
taluka_code integer,
ccode character varying(255) ,
khata_no character varying(255) ,
person_liable character varying(255) ,
survey_hissa character varying(255) ,
total_area numeric(18,2),
uom_of_total_area character varying(255) ,
case_no character varying(255) ,
area_affected numeric(18,2),
uom_of_area_affected character varying(255) ,
nature_of_case character varying(255) ,
period_if_more_than_year character varying(255) ,
period_from_date date,
period_to_date date,
local_cess character varying(255) ,
other_local_cess character varying(255) ,
other_local_cess_desc character varying(255) ,
local_cess_amount numeric(19,2),
other_local_cess_amount numeric(19,2),
amount_of_jm numeric(18,2),
amount_of_zp numeric(18,2),
amount_of_gp numeric(18,2),
assessment numeric(18,2),
remarks character varying(255) ,
active_flag character(1) ,
is_amount_paid character(1) ,
is_demand_generated character(1) ,
khata_type character varying(255) ,
makhta_khata_no character varying(255) 
)
where 
ccode ='272500130318050000' 
and active_flag='Y' 
and revenue_year='2021-22'
and khata_no not in
(
select khata_no from public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select khata_no,ccode,revenue_year from ' 
||'echpun_bar'||'.land_revenue_demand'
)
AS LRD(khata_no text,ccode text,revenue_year text)	
where ccode ='272500130318050000' and revenue_year='2021-22')
order by cast(khata_no as bigint) 



---get form17 khatano list

SELECT 
KHATA_NO 
FROM 
public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select 
khata_no,
ccode,
revenue_year,
active_flag
from '||'echpun_bar'||'.echawdi_form17'
)
as 
ote
(
khata_no text,
ccode text,
revenue_year text,
active_flag character(1)
) 
WHERE 
CCODE='272500130318050000' 
AND REVENUE_YEAR='2021-22' 
AND ACTIVE_FLAG='Y'



-----get makhta khataNo 

select 
case when makhta_khata_no is null then '' else split_part(makhta_khata_no,'/',3) end 
from 
public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select * from '||'echpun_bar'||'.echawdi_form17'
)AS
F17_1
(
id bigint ,
version integer ,
create_dt_tm timestamp without time zone,
created_user_id bigint,
update_userid bigint,
update_dt_tm timestamp without time zone,
revenue_year text ,
district_code integer,
taluka_code integer,
ccode character varying(255) ,
khata_no character varying(255) ,
person_liable character varying(255) ,
survey_hissa character varying(255) ,
total_area numeric(18,2),
uom_of_total_area character varying(255) ,
case_no character varying(255) ,
area_affected numeric(18,2),
uom_of_area_affected character varying(255) ,
nature_of_case character varying(255) ,
period_if_more_than_year character varying(255) ,
period_from_date date,
period_to_date date,
local_cess character varying(255) ,
other_local_cess character varying(255) ,
other_local_cess_desc character varying(255) ,
local_cess_amount numeric(19,2),
other_local_cess_amount numeric(19,2),
amount_of_jm numeric(18,2),
amount_of_zp numeric(18,2),
amount_of_gp numeric(18,2),
assessment numeric(18,2),
remarks character varying(255) ,
active_flag character(1) ,
is_amount_paid character(1) ,
is_demand_generated character(1) ,
khata_type character varying(255) ,
makhta_khata_no character varying(255) 
)
where 
id 
in(
select 
max(id)
from 
public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'SELECT * FROM '||'echpun_bar'||'.ECHAWDI_FORM17'
)
AS 
F17_2
(
id bigint ,
version integer ,
create_dt_tm timestamp without time zone,
created_user_id bigint,
update_userid bigint,
update_dt_tm timestamp without time zone,
revenue_year text ,
district_code integer,
taluka_code integer,
ccode character varying(255) ,
khata_no character varying(255) ,
person_liable character varying(255) ,
survey_hissa character varying(255) ,
total_area numeric(18,2),
uom_of_total_area character varying(255) ,
case_no character varying(255) ,
area_affected numeric(18,2),
uom_of_area_affected character varying(255) ,
nature_of_case character varying(255) ,
period_if_more_than_year character varying(255) ,
period_from_date date,
period_to_date date,
local_cess character varying(255) ,
other_local_cess character varying(255) ,
other_local_cess_desc character varying(255) ,
local_cess_amount numeric(19,2),
other_local_cess_amount numeric(19,2),
amount_of_jm numeric(18,2),
amount_of_zp numeric(18,2),
amount_of_gp numeric(18,2),
assessment numeric(18,2),
remarks character varying(255) ,
active_flag character(1) ,
is_amount_paid character(1) ,
is_demand_generated character(1) ,
khata_type character varying(255) ,
makhta_khata_no character varying(255) 
)
WHERE cCode='272500130318050000' and revenue_year='2021-22' and khata_type='M'
)



--get Form17 khataedar information

SELECT 
DISTINCT ON (T1.SURVEY_HISSA)  
T1.KHATA_NO,
T2.KHATEDAR_NAMES, 
T1.TOTAL_AREA_H, 
T1.SURVEY_HISSA, 
T1.UNIT  
FROM 
( 
SELECT DISTINCT 
KHATA_NO, 
CONCAT
(
PIN,  
CASE WHEN PIN1 <> '' THEN '-' || PIN1 END,  
CASE WHEN PIN2 <> '' THEN '/' || PIN2 END,  
CASE WHEN PIN3 <> '' THEN '/' || PIN3 END,  
CASE WHEN PIN4 <> '' THEN '/' || PIN4 END,  
CASE WHEN PIN5 <> '' THEN '/' || PIN5 END,  
CASE WHEN PIN6 <> '' THEN '/' || PIN6 END,  
CASE WHEN PIN7 <> '' THEN '/' || PIN7 END,  
CASE WHEN PIN8 <> '' THEN '/' || PIN8 END 
) 
AS 
SURVEY_HISSA, 
TOTAL_AREA_H,  
CASE 
WHEN 
KHAND_NO = '2'  
THEN 
'R.Sq.M.'  
ELSE 
'H-R.Sq.M.'  
END 
AS UNIT   
from public.dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'SELECT 
FK.KHATA_NO, 
FK.PIN,  
FK.PIN1,  
FK.PIN2,  
FK.PIN3,  
FK.PIN4,  
FK.PIN5,  
FK.PIN6,  
FK.PIN7,  
FK.PIN8, 
FK.TOTAL_AREA_H,
f7.KHAND_NO,
f7.ccode
FROM ' ||'mhrorpun_bar'||'.FORM7_KHATA FK,' ||'mhrorpun_bar'||'.FORM7 F7 
where 
	f7.pin =fk.pin
AND f7.pin1=fk.pin1
AND f7.pin2=fk.pin2
AND f7.pin3=fk.pin3
AND f7.pin4=fk.pin4
AND f7.pin5=fk.pin5
AND f7.pin6=fk.pin6
AND f7.pin7=fk.pin7
AND f7.pin8=fk.pin8'
) AS fer1
(
KHATA_NO text, 
PIN  text,  
PIN1 text,  
PIN2 text,  
PIN3 text,  
PIN4 text,  
PIN5 text,  
PIN6 text,  
PIN7 text,  
PIN8 text,
TOTAL_AREA_H text,
KHAND_NO text,
ccode text
)
where 
CCODE='272500130318050000'
AND KHATA_NO<>'500001'
) 
T1  
INNER JOIN 
(
select 
DISTINCT KHATA_NO,
STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME,' ', LNAME),'|') AS KHATEDAR_NAMES 
from public.dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'SELECT KHATA_NO,FNAME,MNAME,LNAME,ccode 
FROM '||'mhrorpun_bar'||'.FORM7_KHATA F7K'
)
AS fer2(khata_no text,fname text,mname text,lname text,ccode text)
WHERE 
CCODE = '272500130318050000' GROUP BY KHATA_NO 
) 
T2  
ON (T1.KHATA_NO = T2.KHATA_NO)
where t1.KHATA_NO = '2'


---bindumala
SELECT   
T1.KHATA_NO,   
T2.KHATEDAR_NAMES,   
T1.AREA,   
T1.BINDUMALA_ASSESSMENT,   
T2.SURVEY_HISSA,   
T1.UNIT    
FROM   
(
SELECT 
KHATA_NO,
SUM(TOTAL_AREA_H) AS AREA,
SUM(ASSESSMENT) AS BINDUMALA_ASSESSMENT,
CASE WHEN KHAND_NO = '2' THEN 'R.Sq.M.' ELSE 'H-R.Sq.M.' END AS UNIT 
FROM   
public.dblink(   
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'SELECT DISTINCT   
F7K.KHATA_NO,   
F7K.TOTAL_AREA_H,   
F7K.ASSESSMENT,
F7.KHAND_NO,
MVC.IMAGINARY_MUT_NO_CHECK,
F7K.MUT_NO,
F7K.ccode,f7k.pin,f7k.pin1,f7k.pin2,f7k.pin3,f7k.pin4,f7k.pin5,f7k.pin6,f7k.pin7,f7k.pin8,
F7K.MARKED,    
F7.TENURE_CODE,    
F7.TENURE_SUB_CODE 
FROM    
'||'mhrorpun_bar'||'.FORM7_KHATA F7K,   
'||'mhrorpun_bar'||'.FORM7 F7,   
'||'mhrorpun_bar'||'.M_VILLAGE_CENSUS_CHECK MVC    
WHERE    
F7K.CCODE = F7.CCODE    
AND F7K.CCODE = MVC.CCODE    
AND F7.PIN = F7K.PIN    
AND F7.PIN1 = F7K.PIN1    
AND F7.PIN2 = F7K.PIN2    
AND F7.PIN3 = F7K.PIN3    
AND F7.PIN4 = F7K.PIN4    
AND F7.PIN5 = F7K.PIN5    
AND F7.PIN6 = F7K.PIN6    
AND F7.PIN7 = F7K.PIN7    
AND F7.PIN8 = F7K.PIN8'    
)
AS 	
Fer1
(
KHATA_NO text,   
TOTAL_AREA_H numeric,   
ASSESSMENT numeric,
KHAND_NO  text,
IMAGINARY_MUT_NO_CHECK character(1),
MUT_NO  text,
ccode text,
pin  text,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text,
MARKED text,
TENURE_CODE text,
TENURE_SUB_CODE text
)
where    
CCODE = '272500130318050000'   
AND KHATA_NO NOT IN
(
SELECT KHATA_NO FROM public.dblink
(
'dbname='|| 'echpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'select KHATA_NO,ccode,revenue_year from '||'echpun_bar'||'.LAND_REVENUE_DEMAND LRD '
)
AS 
LRDD(khata_no text,ccode text,revenue_year text) 
WHERE 
CCODE= '272500130318050000' and revenue_year = '2021-22' and khata_no <> ''
) 
AND MARKED != 'Y'    
AND KHAND_NO != '2'    
AND TENURE_CODE != '2'    
AND TENURE_SUB_CODE != '7'
AND KHATA_NO <> '500001'
GROUP BY   
KHATA_NO ,UNIT   
)   
T1    
INNER JOIN   
(   
SELECT DISTINCT   
KHATA_NO,
STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES,   
STRING_AGG(DISTINCT CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8), ',') AS SURVEY_HISSA    
FROM
public.dblink
(   
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'SELECT 
KHATA_NO,CCODE,
F7K.FNAME,F7K.MNAME,F7K.LNAME,
F7K.PIN,F7K.PIN1,F7K.PIN2,F7K.PIN3,F7K.PIN4,F7K.PIN5,F7K.PIN6,F7K.PIN7,F7K.PIN8     
FROM '||'mhrorpun_bar'||'.FORM7_KHATA F7K'    
)
AS 	
Fer1
(
KHATA_NO text,   
ccode text,
FNAME text,
MNAME text,
LNAME text,
pin  text,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text
)
WHERE CCODE = '272500130318050000'  
GROUP BY KHATA_NO   
)   
T2    
ON (T1.KHATA_NO = T2.KHATA_NO) 
order by t1.khata_no::bigint


insert into mhrorpun_bar.m_village_census_check values(25,	13	,'272500130318050000',	1,	'T'	,true	,true)
	
	
--form2

select  
t1.pin, 
t2.na_area_h, 
t1.nature_and_terms_of_na_grant, 
t1.part, 
t1.purpose_of_use, 
t1.remarks, 
t1.id, 
t1.period_from, 
t1.period_to, 
t1.ccode, 
CONCAT
(	
case when t1.pin1 != ' ' then        t1.pin1 end,
case when t1.pin2 != ' ' then '/' || t1.pin2 end, 
case when t1.pin3 != ' ' then '/' || t1.pin3 end,
case when t1.pin4 != ' ' then '/' || t1.pin4 end, 
case when t1.pin5 != ' ' then '/' || t1.pin5 end,
case when t1.pin6 != ' ' then '/' || t1.pin6 end, 
case when t1.pin7 != ' ' then '/' || t1.pin7 end,
case when t1.pin8 != ' ' then '/' || t1.pin8 end 
)
as HissaNo, 
t1.authority, 
t1.occupany_price, 
t1.name_of_first_occupant, 
t1.entry_number_in_taluka_form_2  
from  
(select * from 
public.dblink(' dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select 
ccode,
pin,
pin1,
pin2,
pin3,
pin4,
pin5,
pin6,
pin7,
pin8,
nature_and_terms_of_na_grant, 
part, 
purpose_of_use, 
remarks, 
id, 
period_from, 
period_to,  
authority, 
occupany_price, 
name_of_first_occupant, 
entry_number_in_taluka_form_2,
active_flag
from ' ||'echpun_bar'||'.echawdi_form2 ef2')
AS Echawdi
(
ccode text,
pin text,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text,
nature_and_terms_of_na_grant text, 
part text, 
purpose_of_use text, 
remarks text, 
id bigint, 
period_from text, 
period_to text, 
authority text, 
occupany_price text, 
name_of_first_occupant text, 
entry_number_in_taluka_form_2 text,
active_flag character(1)
)
) as t1,
(select * from public.dblink
(
' dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select ccode,pin,pin2,pin3,pin4,pin5,pin6,pin7,pin8,na_area_h 
from '||'mhrorpun_bar'||'.FORM7_area f7a'
)AS
(
ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,na_area_h text
)
) as t2
where  
t1.ccode=t2.ccode 
and t1.pin=t2.pin  
and trim(t1.pin1)=trim(t2.pin1)  
and trim(t1.pin2)=trim(t2.pin2)  
and trim(t1.pin3)=trim(t2.pin3)  
and trim(t1.pin4)=trim(t2.pin4)  
and trim(t1.pin5)=trim(t2.pin5)  
and trim(t1.pin6)=trim(t2.pin6)  
and trim(t1.pin7)=trim(t2.pin7)  
and trim(t1.pin8)=trim(t2.pin8)  
and t1.pin='1'  
and t1.ccode='272500130318050000'  
and t1.active_flag='Y'


public.dblink bindumala

SELECT 
T1.KHATA_NO, 
T2.KHATEDAR_NAMES, 
T1.AREA, 
T1.BINDUMALA_ASSESSMENT, 
T2.SURVEY_HISSA, 
T1.UNIT  
FROM 
( 
SELECT 
A.KHATA_NO, 
SUM(A.TOTAL_AREA_H) AS AREA, 
SUM(A.ASSESSMENT) AS BINDUMALA_ASSESSMENT, 
A.UNIT  
FROM
(
SELECT 
DISTINCT 
KHATA_NO, 
TOTAL_AREA_H, 
ASSESSMENT, 
CASE 
WHEN 
KHAND_NO = '2'  
THEN 
'R.Sq.M.'  
ELSE 
'H-R.Sq.M.'  
END 
AS UNIT  
FROM 
public.dblink
( 
'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'SELECT 
F7K.KHATA_NO, 
F7K.TOTAL_AREA_H, 
F7K.ASSESSMENT, 
F7.KHAND_NO,
mvc.IMAGINARY_MUT_NO_CHECK,
f7k.mut_no,
f7.ccode,
f7.MARKED,
f7.PIN,
f7.PIN1,
f7.PIN2,
f7.PIN3,
f7.PIN4,
f7.PIN5,
f7.PIN6,
f7.PIN7,
f7.PIN8
FROM '
||'mhrorpun_bar'||'.FORM7_KHATA F7K,' 
||'mhrorpun_bar'||'.FORM7 F7,' 
||'mhrorpun_bar'||'.M_VILLAGE_CENSUS_CHECK MVC  
WHERE 
F7K.CCODE = F7.CCODE  
AND F7K.CCODE = MVC.CCODE  
AND F7.PIN = F7K.PIN  
AND F7.PIN1 = F7K.PIN1  
AND F7.PIN2 = F7K.PIN2  
AND F7.PIN3 = F7K.PIN3  
AND F7.PIN4 = F7K.PIN4  
AND F7.PIN5 = F7K.PIN5  
AND F7.PIN6 = F7K.PIN6  
AND F7.PIN7 = F7K.PIN7  
AND F7.PIN8 = F7K.PIN8'
)AS fer1
(
KHATA_NO text, 
TOTAL_AREA_H numeric, 
ASSESSMENT numeric, 
KHAND_NO text,
IMAGINARY_MUT_NO_CHECK text,
mut_no text,
CCODE text,
MARKED text,
PIN text,PIN1 text,PIN2 text,PIN3 text,PIN4 text,PIN5 text,PIN6 text,PIN7 text, PIN8 text
)
where
CASE WHEN TEXT(IMAGINARY_MUT_NO_CHECK) = 't' THEN MUT_NO < '100000' ELSE MUT_NO < '500000' END  
AND CCODE = '272500130318050000' 
AND KHATA_NO 
NOT IN
(
SELECT KHATA_NO FROM public.dblink
(
'dbname='|| 'echpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'select KHATA_NO,ccode,revenue_year from '||'echpun_bar'||'.LAND_REVENUE_DEMAND LRD '
)
AS 
LRDD(khata_no text,ccode text,revenue_year text) 
WHERE 
CCODE= '272500130318050000' and revenue_year = '2021-22' and khata_no <> ''
)
AND MARKED != 'Y'  
AND KHAND_NO != '2'  
AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) NOT IN  
( 
SELECT 
CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)  
FROM 
public.dblink
(
'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',	
'select PIN,PIN1,PIN2,PIN3,PIN4,PIN5,PIN6,PIN7, PIN8,ccode,khata_no from '||'mhrorpun_bar'||'.FORM7_KHATA FK'
) 
AS ferfarkhata(PIN text,PIN1 text,PIN2 text,PIN3 text,PIN4 text,PIN5 text,PIN6 text,PIN7 text, PIN8 text,ccode text,khata_no text) 
WHERE 
CCODE = '272500130318050000' 
AND KHATA_NO = '500001'
) 
)
AS A  
GROUP BY 
A.KHATA_NO ,A.UNIT 
) 
T1  
INNER JOIN 
(
select 
DISTINCT 
KHATA_NO, 
STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES, 
STRING_AGG(DISTINCT CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8), ',') AS SURVEY_HISSA  
from public.dblink
(
'dbname='|| 'mhrorpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'SELECT 
f7k.KHATA_NO,
F7K.FNAME,
F7K.MNAME,
F7K.LNAME, 
F7K.PIN,
F7K.PIN1,
F7K.PIN2,
F7K.PIN3,
F7K.PIN4,
F7K.PIN5,
F7K.PIN6,
F7K.PIN7,
F7K.PIN8,
f7k.ccode
FROM '
||'mhrorpun_bar'||'.FORM7_KHATA F7K')
AS
fer2
(
KHATA_NO text,
FNAME text,
MNAME text,
LNAME text, 
PIN  text, 
PIN1 text,
PIN2 text,
PIN3 text,
PIN4 text,
PIN5 text,
PIN6 text,
PIN7 text,
PIN8 text,
CCODE text
)
WHERE 
CCODE = '272500130318050000' 
AND KHATA_NO 
NOT IN
(
SELECT KHATA_NO FROM public.dblink
(
'dbname='|| 'echpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'select KHATA_NO,ccode,revenue_year from '||'echpun_bar'||'.LAND_REVENUE_DEMAND LRD '
)
AS 
LRDD(khata_no text,ccode text,revenue_year text) 
WHERE 
CCODE= '272500130318050000' and revenue_year = '2021-22' and khata_no <> ''
)
GROUP BY 
KHATA_NO
) 
T2  
ON (T1.KHATA_NO = T2.KHATA_NO)

normal bindumala


SELECT 
T1.KHATA_NO, 
T2.KHATEDAR_NAMES, 
T1.AREA, 
T1.BINDUMALA_ASSESSMENT, 
T2.SURVEY_HISSA, 
T1.UNIT  
FROM 
( 
SELECT 
A.KHATA_NO, 
SUM(A.TOTAL_AREA_H) AS AREA, 
SUM(A.ASSESSMENT) AS BINDUMALA_ASSESSMENT, 
A.UNIT  
FROM 
( 
SELECT DISTINCT 
F7K.KHATA_NO, 
F7K.TOTAL_AREA_H, 
F7K.ASSESSMENT, 
CASE 
WHEN 
F7.KHAND_NO = '2'  
THEN 
'R.Sq.M.'  
ELSE 
'H-R.Sq.M.'  
END 
AS UNIT  
FROM 
mhror_eferfar.FORM7_KHATA F7K, 
mhror_eferfar.FORM7 F7, 
mhror_master.M_VILLAGE_CENSUS_CHECK MVC  
WHERE 
CASE 
WHEN 
TEXT(MVC.IMAGINARY_MUT_NO_CHECK) = 't'  
THEN 
F7K.MUT_NO < '100000'  
ELSE 
F7K.MUT_NO < '500000'  
END 
AND F7K.CCODE = F7.CCODE  
AND F7K.CCODE = MVC.CCODE  
AND F7.PIN = F7K.PIN  
AND F7.PIN1 = F7K.PIN1  
AND F7.PIN2 = F7K.PIN2  
AND F7.PIN3 = F7K.PIN3  
AND F7.PIN4 = F7K.PIN4  
AND F7.PIN5 = F7K.PIN5  
AND F7.PIN6 = F7K.PIN6  
AND F7.PIN7 = F7K.PIN7  
AND F7.PIN8 = F7K.PIN8  
AND F7K.CCODE = '272500130318050000' 
AND F7K.KHATA_NO NOT IN(SELECT KHATA_NO FROM LAND_REVENUE_DEMAND LRD WHERE CCODE= '272500130318050000' and revenue_year = '2021-22' and khata_no <> '') 
AND F7K.MARKED != 'Y'  
AND F7.KHAND_NO != '2'  
AND F7.TENURE_CODE != '2'  
AND F7.TENURE_SUB_CODE != '7'  
AND CONCAT(F7.PIN, '/', F7.PIN1, '/', F7.PIN2, '/', F7.PIN3, '/', F7.PIN4, '/', F7.PIN5, '/', F7.PIN6, '/', F7.PIN7, '/', F7.PIN8) NOT IN  
( 
SELECT 
CONCAT(FK.PIN, '/', FK.PIN1, '/', FK.PIN2, '/', FK.PIN3, '/', FK.PIN4, '/', FK.PIN5, '/', FK.PIN6, '/', FK.PIN7, '/', FK.PIN8)  
FROM 
mhror_eferfar.FORM7_KHATA FK  
WHERE 
FK.CCODE = '272500130318050000' 
AND FK.KHATA_NO = '500001'  
) 
) 
AS A  
GROUP BY 
A.KHATA_NO ,A.UNIT 
) 
T1  
INNER JOIN 
( 
SELECT DISTINCT 
KHATA_NO, 
STRING_AGG(DISTINCT CONCAT(F7K.FNAME, ' ', F7K.MNAME, ' ', F7K.LNAME), ' | ') AS KHATEDAR_NAMES, 
STRING_AGG(DISTINCT CONCAT(F7K.PIN, '/', F7K.PIN1, '/', F7K.PIN2, '/', F7K.PIN3, '/', F7K.PIN4, '/', F7K.PIN5, '/', F7K.PIN6, '/', F7K.PIN7, '/', F7K.PIN8), ',') AS SURVEY_HISSA  
FROM 
mhror_eferfar.FORM7_KHATA F7K  
WHERE 
F7K.CCODE = '272500130318050000' 
AND F7K.KHATA_NO NOT IN(SELECT KHATA_NO FROM LAND_REVENUE_DEMAND LRD WHERE CCODE= '272500130318050000' and revenue_year = '2021-22' and khata_no <> '') 
GROUP BY 
F7K.KHATA_NO 
) 
T2  
ON (T1.KHATA_NO = T2.KHATA_NO) 



---getForm2areaassessmet

SELECT  
cCode,  
pin , 
na_area_h, 
CONCAT( 
case when pin1 <> '' then       pin1 end , 
case when pin2 <> '' then '/'|| pin2 end ,  
case when pin3 <> '' then '/'|| pin3 end ,  
case when pin4 <> '' then '/'|| pin4 end ,  
case when pin5 <> '' then '/'|| pin5 end ,  
case when pin6 <> '' then '/'|| pin6 end ,  
case when pin7 <> '' then '/'|| pin7 end ,   
case when pin8 <> '' then '/'|| pin8 end  
)as hissaNo , 
na_assessment  
FROM 
public.dblink(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select f7.ccode,f7.marked,F7.pin,F7.pin1,F7.pin2,F7.pin3,F7.pin4,F7.pin5,F7.pin6,F7.pin7,F7.pin8,f7a.na_area_h,
f7a.na_assessment from '||'mhrorpun_bar'||'.FORM7 F7,'||'mhrorpun_bar'||'.FORM7_area F7a 
where 
F7.ccode=F7a.ccode  
and F7a.pin=F7.pin  
and F7a.pin1=F7.pin1
and F7a.pin2=F7.pin2 
and F7a.pin3=F7.pin3 
and F7a.pin4=F7.pin4 
and F7a.pin5=F7.pin5 
and F7a.pin6=F7.pin6 
and F7a.pin7=F7.pin7 
and F7a.pin8=F7.pin8'
)
AS 
F7(ccode text,marked text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,na_area_h numeric,na_assessment text)
WHERE   
cCode ='272500130318050000'   
AND pin ='1'   
AND NA_AREA_H <> '0'    
AND MARKED != 'Y'
AND 
CONCAT
( 
case when pin1 <> '' then       pin1 end , 
case when pin2 <> '' then '/'|| pin2 end ,  
case when pin3 <> '' then '/'|| pin3 end ,  
case when pin4 <> '' then '/'|| pin4 end ,  
case when pin5 <> '' then '/'|| pin5 end ,  
case when pin6 <> '' then '/'|| pin6 end ,  
case when pin7 <> '' then '/'|| pin7 end ,   
case when pin8 <> '' then '/'|| pin8 end  
)='1'
AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) NOT IN    
(   
SELECT    
CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)    
FROM
public.dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select FK.CCODE,FK.KHATA_NO,FK.PIN,FK.PIN1,FK.PIN2,FK.PIN3,FK.PIN4,FK.PIN5,FK.PIN6,FK.PIN7,FK.PIN8 from ' 
||'mhrorpun_bar'||'.FORM7_KHATA FK') 
AS F7K(ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,khata_no text)
WHERE    
CCODE='272500130318050000'    
AND KHATA_NO = '500001'   
) 


form3 getSerial No

SELECT COUNT(running_no_each_class) FROM 
public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select running_no_each_class,ccode from '||'echpun_bar'||'.ECHAWDI_FORM3')
AS(running_no_each_class bigint,ccode text)
 WHERE cCode= '272500130318050000'
 
 
 ---getForm3AreaAssessment
 
 
 SELECT 
cCode,
pin,
na_area_h, 
CONCAT
( 
case when pin1 <> '' then       pin1 end, 
case when pin2 <> '' then '/'|| pin2 end, 
case when pin3 <> '' then '/'|| pin3 end, 
case when pin4 <> '' then '/'|| pin4 end, 
case when pin5 <> '' then '/'|| pin5 end, 
case when pin6 <> '' then '/'|| pin6 end, 
case when pin7 <> '' then '/'|| pin7 end, 
case when pin8 <> '' then '/'|| pin8 end 
)as Hissa_No,
na_assessment,
net_culti_area_h,
assessment 
FROM 
public.dblink(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select 
f7.ccode,f7.marked,
F7.pin,F7.pin1,F7.pin2,F7.pin3,F7.pin4,F7.pin5,F7.pin6,F7.pin7,F7.pin8,f7a.na_area_h,
f7a.na_assessment,f7a.net_culti_area_h,f7a.assessment,
f7.tenure_code,
f7.tenure_sub_code 
from '||'mhrorpun_bar'||'.FORM7 F7,'||'mhrorpun_bar'||'.FORM7_area F7a 
where 
F7.ccode=F7a.ccode  
and F7a.pin=F7.pin  
and F7a.pin1=F7.pin1
and F7a.pin2=F7.pin2 
and F7a.pin3=F7.pin3 
and F7a.pin4=F7.pin4 
and F7a.pin5=F7.pin5 
and F7a.pin6=F7.pin6 
and F7a.pin7=F7.pin7 
and F7a.pin8=F7.pin8'
)
AS 
F7
(
ccode text,marked text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,
pin8 text,na_area_h numeric,na_assessment text,net_culti_area_h text,assessment text,tenure_code text,
tenure_sub_code text
)
WHERE   
cCode ='272500130318050000'   
AND pin ='1'   
AND TENURE_CODE = '2' 
AND TENURE_SUB_CODE IN ('7','2') 
AND MARKED != 'Y'
AND CONCAT
( 
case when pin1 <> '' then       pin1 end , 
case when pin2 <> '' then '/'|| pin2 end ,  
case when pin3 <> '' then '/'|| pin3 end ,  
case when pin4 <> '' then '/'|| pin4 end ,  
case when pin5 <> '' then '/'|| pin5 end ,  
case when pin6 <> '' then '/'|| pin6 end ,  
case when pin7 <> '' then '/'|| pin7 end ,   
case when pin8 <> '' then '/'|| pin8 end  
)='1'
AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) NOT IN    
(   
SELECT    
CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)    
FROM
public.dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select FK.CCODE,FK.KHATA_NO,FK.PIN,FK.PIN1,FK.PIN2,FK.PIN3,FK.PIN4,FK.PIN5,FK.PIN6,FK.PIN7,FK.PIN8 from ' 
||'mhrorpun_bar'||'.FORM7_KHATA FK') 
AS F7K(ccode text,pin text,pin1 text,pin2 text,pin3 text,pin4 text,pin5 text,pin6 text,pin7 text,pin8 text,khata_no text)
WHERE    
CCODE='272500130318050000'    
AND KHATA_NO = '500001'   
)


---getAllForm3 & report

select 
ef3.tenure, 
ef3.kind_and_how_long_continuable, 
ef3.running_no_each_class, 
ef3.sanad_no, 
ef3.pin, 
f7a.net_culti_area_h, 
F7a.na_assessment, 
ef3.ccode, 
CONCAT
(
case when ef3.pin1 != ' ' then 		  ef3.pin1 end,
case when ef3.pin2 != ' ' then '/' || ef3.pin2 end, 
case when ef3.pin3 != ' ' then '/' || ef3.pin3 end,
case when ef3.pin4 != ' ' then '/' || ef3.pin4 end, 
case when ef3.pin5 != ' ' then '/' || ef3.pin5 end,
case when ef3.pin6 != ' ' then '/' || ef3.pin6 end, 
case when ef3.pin7 != ' ' then '/' || ef3.pin7 end,
case when ef3.pin8 != ' ' then '/' || ef3.pin8 end
)
as HissaNo, 
ef3.id,
ef3.remarks,
ef3.no_in_register_of_lands 
from 

(
select 
* 
from
public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select 
ef3.tenure, 
ef3.kind_and_how_long_continuable, 
ef3.running_no_each_class, 
ef3.sanad_no, 
ef3.ccode, 
ef3.pin,
ef3.pin1,
ef3.pin2, 
ef3.pin3,
ef3.pin4, 
ef3.pin5,
ef3.pin6, 
ef3.pin7,
ef3.pin8,
ef3.active_flag,
ef3.id,
ef3.remarks,
ef3.no_in_register_of_lands 
from '
||'echpun_bar'||'.echawdi_form3 ef3'
)
as ech
(
tenure text, 
kind_and_how_long_continuable text, 
running_no_each_class text, 
sanad_no text, 
ccode text, 
pin text,
pin1 text,
pin2 text, 
pin3 text,
pin4 text, 
pin5 text,
pin6 text, 
pin7 text,
pin8 text,
active_flag character(1),
id bigint,
remarks text,
no_in_register_of_lands text
)
) 
as ef3,
(
select * from 
public.dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select 
f7a.net_culti_area_h, 
f7a.na_assessment,
f7a.ccode,f7a.pin ,f7a.pin1,f7a.pin2,f7a.pin3,f7a.pin4,f7a.pin5,f7a.pin6,f7a.pin7,f7a.pin8
from '||'mhrorpun_bar'||'.FORM7_area f7a'
)
as fer
(
net_culti_area_h text,
na_assessment text,
ccode text,
pin   text,
pin1  text,
pin2  text,
pin3  text,
pin4  text,
pin5  text,
pin6  text,
pin7  text,
pin8  text
)
) as f7a

where  
		 ef3.ccode=		f7a.ccode 
and      ef3.pin  =     f7a.pin 
and trim(ef3.pin1)=trim(f7a.pin1) 
and trim(ef3.pin2)=trim(f7a.pin2) 
and trim(ef3.pin3)=trim(f7a.pin3) 
and trim(ef3.pin4)=trim(f7a.pin4) 
and trim(ef3.pin5)=trim(f7a.pin5) 
and trim(ef3.pin6)=trim(f7a.pin6) 
and trim(ef3.pin7)=trim(f7a.pin7) 
and trim(ef3.pin8)=trim(f7a.pin8) 
and ef3.ccode='272500130318050000' 
and ef3.pin='1' 
and ef3.active_flag='Y' 




---get ccode of nominated villages alter
select mhr_schema_name,ccode from m_village_mapper where  mhr_schema_name like '%mhrorrai_tal%'
union
select mhr_schema_name,ccode from m_village_mapper where  mhr_schema_name like '%mhrornan_mud%'
union
select mhr_schema_name,ccode from m_village_mapper where  mhr_schema_name like '%mhrorpun_bar%'
union
select mhr_schema_name,ccode from m_village_mapper where  mhr_schema_name like '%mhrornas_bag%'
union
select mhr_schema_name,ccode from m_village_mapper where  mhr_schema_name like '%mhrorako_ako%'
union
select mhr_schema_name,ccode from m_village_mapper where  mhr_schema_name like '%mhrornag_rul%'





--7A 

SELECT      
t1.last_year_serial_no,     
t1.name_of_tenant,     
STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS LANDLORD_NAME,       
t1.PIN,   
SUM(t2.TOTAL_AREA_H) AS AREA,       
SUM(t2.ASSESSMENT) AS ASSESSMENT,     
t1.RENT,     
t1.serial_no_in_mutation_reg,    
t1.REMARKS, 
CONCAT(       
case when t1.pin1 <> '' then   	  t1.pin1 end,       
case when t1.pin2 <> '' then '/'||t1.pin2 end,       
case when t1.pin3 <> '' then '/'||t1.pin3 end,       
case when t1.pin4 <> '' then '/'||t1.pin4 end,       
case when t1.pin5 <> '' then '/'||t1.pin5 end,       
case when t1.pin6 <> '' then '/'||t1.pin6 end,       
case when t1.pin7 <> '' then '/'||t1.pin7 end,       
case when t1.pin8 <> '' then '/'||t1.pin8 end       
)as Hissa_No 
FROM       
(
select * from 
public.dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'
select 
last_year_serial_no,     
name_of_tenant,     
PIN,   
RENT,     
serial_no_in_mutation_reg,    
REMARKS, 
pin1,     
pin2,       
pin3,       
pin4,       
pin5,       
pin6,       
pin7,       
pin8,
ccode              
from '||'echpun_bar'||'.ECHAWDI_FORM7A EF7A'     
)
AS
(
last_year_serial_no text,     
name_of_tenant text,     
PIN  text,   
RENT text,     
serial_no_in_mutation_reg text,    
REMARKS text, 
pin1 text,     
pin2 text,       
pin3 text,       
pin4 text,       
pin5 text,       
pin6 text,       
pin7 text,       
pin8 text,
ccode text
)
)
as t1,
(
select * from 
public.dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select 
F7A.TOTAL_AREA_H,F7A.ASSESSMENT,F7.ccode,f7k.khata_no,F7.PIN,F7.PIN1,F7.PIN2,F7.PIN3,F7.PIN4,F7.PIN5,F7.PIN6,F7.PIN7,F7.PIN8,F7K.FNAME,F7K.MNAME,F7K.LNAME,f7.marked 
from 
'||'mhrorpun_bar'||'.FORM7_KHATA F7K,       
'||'mhrorpun_bar'||'.FORM7 F7,       
'||'mhrorpun_bar'||'.FORM7_AREA F7A       
WHERE       
F7.CCODE = F7A.CCODE       
AND F7.CCODE = F7K.CCODE       
AND F7.PIN = F7A.PIN       
AND F7.PIN1 = F7A.PIN1       
AND F7.PIN2 = F7A.PIN2       
AND F7.PIN3 = F7A.PIN3       
AND F7.PIN4 = F7A.PIN4       
AND F7.PIN5 = F7A.PIN5       
AND F7.PIN6 = F7A.PIN6       
AND F7.PIN7 = F7A.PIN7       
AND F7.PIN8 = F7A.PIN8          
AND F7.PIN = F7K.PIN       
AND F7.PIN1 = F7K.PIN1       
AND F7.PIN2 = F7K.PIN2       
AND F7.PIN3 = F7K.PIN3       
AND F7.PIN4 = F7K.PIN4       
AND F7.PIN5 = F7K.PIN5       
AND F7.PIN6 = F7K.PIN6       
AND F7.PIN7 = F7K.PIN7       
AND F7.PIN8 = F7K.PIN8'       
)
AS
(
TOTAL_AREA_H numeric,
ASSESSMENT numeric,
ccode text,
khata_no text,
PIN text,
PIN1 text,
PIN2 text,
PIN3 text,
PIN4 text,
PIN5 text,
PIN6 text,
PIN7 text,
PIN8 text,
FNAME text,
MNAME text,
LNAME text,
marked character(1)
)
)as t2
where
t2.CCODE = t1.CCODE
AND t2.PIN =  TRIM(t1.PIN)     
AND t2.PIN1 = TRIM(t1.PIN1)      
AND t2.PIN2 = TRIM(t1.PIN2)      
AND t2.PIN3 = TRIM(t1.PIN3)      
AND t2.PIN4 = TRIM(t1.PIN4)      
AND t2.PIN5 = TRIM(t1.PIN5)      
AND t2.PIN6 = TRIM(t1.PIN6)      
AND t2.PIN7 = TRIM(t1.PIN7)      
AND t2.PIN8 = TRIM(t1.PIN8)   
AND CONCAT(t1.PIN, '/', t1.PIN1, '/', t1.PIN2, '/', t1.PIN3, '/', t1.PIN4, '/', t1.PIN5, '/', t1.PIN6, '/', t1.PIN7, '/', t1.PIN8) NOT IN       
(       
SELECT       
CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8)       
FROM 
public.dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select ccode,khata_no,FK.PIN,FK.PIN1,FK.PIN2,FK.PIN3,FK.PIN4,FK.PIN5,FK.PIN6,FK.PIN7,FK.PIN8 from '
||'mhrorpun_bar'||'.FORM7_KHATA FK'
)
AS(ccode text,khata_no text,PIN text,PIN1 text,PIN2 text,PIN3 text,PIN4 text,PIN5 text,PIN6 text,PIN7 text,PIN8 text)
	
WHERE       
CCODE = '272500130318050000' AND KHATA_NO = '500001'       
) 
and t2.MARKED != 'Y'
AND t2.CCODE = '272500130318050000'
GROUP BY 
t1.last_year_serial_no,     
t1.name_of_tenant,               
t2.TOTAL_AREA_H,       
t2.ASSESSMENT,     
t1.RENT,     
t1.serial_no_in_mutation_reg,    
t1.REMARKS, 
t1.ccode,
t1.pin,
t1.pin1,
t1.pin2,
t1.pin3,
t1.pin4,
t1.pin5,
t1.pin6,
t1.pin7,
t1.pin8





-----8d report & form6a Report

6 a report once confirm


SELECT 
MUT_NO, 
PIN, 
OBJECTION_DATE, 
FNAME, 
MNAME, 
LNAME, 
OBJECTION_REMARK,  
CONCAT( 
case when pin1 != '' then        pin1 end,case when pin2 != '' then '/' || pin2 end, 
case when pin3 != '' then '/' || pin3 end,case when pin4 != '' then '/' || pin4 end, 
case when pin5 != '' then '/' || pin5 end,case when pin6 != '' then '/' || pin6 end, 
case when pin7 != '' then '/' || pin7 end,case when pin8 != '' then '/' || pin8 end 
)as HissaNo 
FROM  
public.dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select 
MR.MUT_NO, 
PIN, 
OBJECTION_DATE, 
FNAME, 
MNAME, 
LNAME, 
OBJECTION_REMARK,  
pin1 ,
pin2 , 
pin3 ,
pin4 , 
pin5 ,
pin6 , 
pin7 ,
pin8 ,
MR.ccode 
from 
'||'mhrorpun_bar'||'.OBJECTION_DETAILS OD, 
'||'mhrorpun_bar'||'.MUTREGISTER MR  
WHERE 
OD.MUT_NO = MR.MUT_NO  
AND OD.CCODE = MR.CCODE' 
)
AS
(
MUT_NO int, 
PIN text, 
OBJECTION_DATE text, 
FNAME text, 
MNAME text, 
LNAME text, 
OBJECTION_REMARK text,  
pin1  text,
pin2  text, 
pin3  text,
pin4  text, 
pin5  text,
pin6  text, 
pin7  text,
pin8  text,
ccode text 
)
where ccode='272500130318050000' 



select  
receipt_date, 
receipt_no, 
net_amount as receiptAmount, 
challan_date, 
challan_no, 
total_amount as chalanAmount  
from  
( 
(
select 
*
from public.dblink 
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select 
challan_id,
receipt_date,
receipt_no,
net_amount,
ccode,
revenue_year
from 
'||'echpun_bar'||'.land_revenue_demand'
)
AS
(
challan_id bigint,
receipt_date text,
receipt_no text,
net_amount numeric,
ccode text,
revenue_year text
)
)  
as t1  
left join  
( 
select * from public.dblink
(
'dbnname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select id,
challan_date,
challan_no,
total_amount 
from '||'echpun_bar'||'challan_dtl'  
)
AS
(
id bigint,
challan_date text,
challan_no  text,
total_amount numeric
)
) as t2  
on(t1.challan_id=t2.id) 
) 
A where 
CCODE = '' 
and revenue_year='2021-22' 
and receipt_no is not null 
order by receipt_date



---edu cess

SELECT
KHATA_NO,
KHATEDAR_NAMES ,
CONST_CROP_CODE,
CROP_NAME,
CONST_IRR_SUM 
FROM
(
SELECT DISTINCT
t1.KHATA_NO,
KHATEDAR_NAMES,
CONST_CROP_CODE,
CROP_NAME,
CONST_IRR_SUM 
FROM
(
select 
f7kk.KHATA_NO,
f7kk.CONST_CROP_CODE,
CROP_NAME,
CONST_IRR_AREA_H AS CONST_IRR_SUM from 
(select * from public.dblink
('dbname='||'mhrorpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select 
f7k.KHATA_NO,mc.CROP_NAME,mc.CONST_CROP_CODE,f7k.MARKED,
f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,
f7k.ccode from
'||'mhrorpun_bar'||'.FORM7_KHATA F7K,
'||'mhrorpun_bar'||'.M_CROP_NAME MC
WHERE
F7K.KHATA_NO = TEXT(F12.NUMERIC_PIN) 
AND F7K.CCODE = F12.CCODE 
AND F12.CONST_CROP_CODE = MC.CROP_CODE '
)
AS(
KHATA_NO TEXT,
CROP_NAME TEXT,
CONST_CROP_CODE TEXT,
MARKED TEXT,
PIN TEXT,
PIN1 TEXT,
PIN2 TEXT,
PIN3 TEXT,
PIN4 TEXT,
PIN5 TEXT,
PIN6 TEXT,
PIN7 TEXT,
PIN8 TEXT,
ccode text
)
)as f7kk,
(
select * from public.dblink
('dbname='||'mhrorpuncrop' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select f12.CONST_CROP_CODE,f12.CONST_IRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE 
 from '||'mhrorpuncrop_bar'||'.FORM12 F12')
AS
(
CONST_CROP_CODE TEXT,
CONST_IRR_AREA_H numeric(18,2),
YEAR_CULTI text,
NUMERIC_PIN numeric(18,2),
ccode text
)
)as f12cr
WHERE 
f12cr.YEAR_CULTI ='2021-22' 
AND f12cr.CONST_IRR_AREA_H <> '0' 
AND CAST(f12cr.CONST_CROP_CODE AS VARCHAR) LIKE '27%' 
AND f7kk.CCODE = '272500130318050000' 
AND f7kk.MARKED != 'Y' 
AND CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) NOT IN 
(
SELECT
CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8) 
FROM
public.dblink
(
'dbname='||'mhrorpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select PIN,PIN1,PIN2,PIN3,PIN4,PIN5,PIN6,PIN7,PIN8 FROM 
'||'mhrorpun_bar'||'.FORM7_KHATA  FK '
)
AS 
(
PIN TEXT,
PIN1 TEXT,
PIN2 TEXT,
PIN3 TEXT,
PIN4 TEXT,
PIN5 TEXT,
PIN6 TEXT,
PIN7 TEXT,
PIN8 TEXT
)
WHERE
f7kk.CCODE = '272500130318050000' 
AND KHATA_NO = '500001' 
)
AND f7kk.ccode=f12cr.ccode and f7kk.CONST_CROP_CODE=f12cr.CONST_CROP_CODE
-- AND KHATA_NO NOT IN(SELECT KHATA_NO FROM LAND_REVENUE_DEMAND LRD WHERE CCODE='272500130318050000' and revenue_year = '2021-22' and khata_no <> '')
)AS T1 
INNER JOIN
(
SELECT DISTINCT
KHATA_NO,
STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES 
FROM
public.dblink
('dbname='||'mhrorpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select KHATA_NO,FNAME,MNAME,LNAME,ccode FROM 
'||'mhrorpun_bar'||'.FORM7_KHATA F7K')
AS(KHATA_NO TEXT,FNAME TEXT,MNAME TEXT,LNAME TEXT,ccode text) 
WHERE
CCODE = '272500130318050000' 
--AND KHATA_NO NOT IN(SELECT KHATA_NO FROM LAND_REVENUE_DEMAND LRD WHERE CCODE= '272500130318050000'  and revenue_year = '2021-22' and khata_no <> '')
GROUP BY
KHATA_NO 
)
T2 
ON (T1.KHATA_NO = T2.KHATA_NO)
)
A
































































---are in test

bindumala ----->
SELECT   
T1.KHATA_NO,   
T2.KHATEDAR_NAMES,   
T1.AREA,   
T1.BINDUMALA_ASSESSMENT,   
T2.SURVEY_HISSA,   
T1.UNIT    
FROM   
(
SELECT 
KHATA_NO,
SUM(TOTAL_AREA_H) AS AREA,
SUM(ASSESSMENT) AS BINDUMALA_ASSESSMENT,
CASE WHEN KHAND_NO = '2' THEN 'R.Sq.M.' ELSE 'H-R.Sq.M.' END AS UNIT 
FROM   
public.dblink(   
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'SELECT DISTINCT   
F7K.KHATA_NO,   
F7K.TOTAL_AREA_H,   
F7K.ASSESSMENT,
F7.KHAND_NO,
MVC.IMAGINARY_MUT_NO_CHECK,
F7K.MUT_NO,
F7K.ccode,f7k.pin,f7k.pin1,f7k.pin2,f7k.pin3,f7k.pin4,f7k.pin5,f7k.pin6,f7k.pin7,f7k.pin8,
F7K.MARKED,    
F7.TENURE_CODE,    
F7.TENURE_SUB_CODE 
FROM    
'||'mhrorpun_bar'||'.FORM7_KHATA F7K,   
'||'mhrorpun_bar'||'.FORM7 F7,   
'||'mhrorpun_bar'||'.M_VILLAGE_CENSUS_CHECK MVC    
WHERE    
F7K.CCODE = F7.CCODE    
AND F7K.CCODE = MVC.CCODE    
AND F7.PIN = F7K.PIN    
AND F7.PIN1 = F7K.PIN1    
AND F7.PIN2 = F7K.PIN2    
AND F7.PIN3 = F7K.PIN3    
AND F7.PIN4 = F7K.PIN4    
AND F7.PIN5 = F7K.PIN5    
AND F7.PIN6 = F7K.PIN6    
AND F7.PIN7 = F7K.PIN7    
AND F7.PIN8 = F7K.PIN8'    
)
AS 	
Fer1
(
KHATA_NO text,   
TOTAL_AREA_H numeric,   
ASSESSMENT numeric,
KHAND_NO  text,
IMAGINARY_MUT_NO_CHECK character(1),
MUT_NO  text,
ccode text,
pin  text,
pin1 text,
pin2 text,
pin3 text,pkk
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text,
MARKED text,
TENURE_CODE text,
TENURE_SUB_CODE text
)
where    
CCODE = '272500130318050000'   
AND KHATA_NO NOT IN
(
SELECT KHATA_NO FROM public.dblink
(
'dbname='|| 'echpun' ||' host='|| 'localhost' ||' port=5432 user=postgres password=root',
'select KHATA_NO,ccode,revenue_year from '||'echpun_bar'||'.LAND_REVENUE_DEMAND LRD '
)
AS 
LRDD(khata_no text,ccode text,revenue_year text) 
WHERE 
CCODE= '272500130318050000' and revenue_year = '2021-22' and khata_no <> ''
) 
AND MARKED != 'Y'    
AND KHAND_NO != '2'    
AND TENURE_CODE != '2'    
AND TENURE_SUB_CODE != '7'
AND KHATA_NO <> '500001'
GROUP BY   
KHATA_NO ,UNIT   
)   
T1    
INNER JOIN   
(   
SELECT DISTINCT   
KHATA_NO,
STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES,   
STRING_AGG(DISTINCT CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8), ',') AS SURVEY_HISSA    
FROM
public.dblink
(   
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'SELECT 
KHATA_NO,CCODE,
F7K.FNAME,F7K.MNAME,F7K.LNAME,
F7K.PIN,F7K.PIN1,F7K.PIN2,F7K.PIN3,F7K.PIN4,F7K.PIN5,F7K.PIN6,F7K.PIN7,F7K.PIN8     
FROM '||'mhrorpun_bar'||'.FORM7_KHATA F7K'    
)
AS 	
Fer1
(
KHATA_NO text,   
ccode text,
FNAME text,
MNAME text,
LNAME text,
pin  text,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text
)
WHERE CCODE = '272500130318050000'  
GROUP BY KHATA_NO   
)   
T2    
ON (T1.KHATA_NO = T2.KHATA_NO) 
order by t1.khata_no::bigint


dumala ----->

SELECT
T1.KHATA_NO,
T2.KHATEDAR_NAMES,
T1.AREA,
T1.DUMALA_ASSESSMENT,
T2.SURVEY_HISSA,
T1.UNIT 
FROM
(
SELECT
A.KHATA_NO,
SUM(A.TOTAL_AREA_H) AS AREA,
SUM(A.ASSESSMENT) AS DUMALA_ASSESSMENT,
CASE WHEN KHAND_NO = '2' THEN 'R.Sq.M.' ELSE 'H-R.Sq.M.' END As UNIT
FROM
(select * from public.dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'SELECT DISTINCT 
F7K.KHATA_NO,
F7K.TOTAL_AREA_H,
F7K.ASSESSMENT,
F7.KHAND_NO,
F7K.CCODE,
MVC.IMAGINARY_MUT_NO_CHECK,
F7K.MUT_NO, 
F7K.MARKED, 
F7A.NA_AREA_H,
F7.TENURE_CODE, 
F7.TENURE_SUB_CODE 
FROM 
'||'mhrorpun_bar'||'.FORM7_KHATA F7K, 
'||'mhrorpun_bar'||'.FORM7_AREA F7A, 
'||'mhrorpun_bar'||'.FORM7 F7, 
'||'mhrorpun_bar'||'.M_VILLAGE_CENSUS_CHECK MVC 
WHERE
F7K.CCODE = F7.CCODE 
AND F7A.CCODE = F7.CCODE 
AND F7K.CCODE = MVC.CCODE 
AND F7.PIN = F7K.PIN 
AND F7.PIN1 = F7K.PIN1 
AND F7.PIN2 = F7K.PIN2 
AND F7.PIN3 = F7K.PIN3 
AND F7.PIN4 = F7K.PIN4 
AND F7.PIN5 = F7K.PIN5 
AND F7.PIN6 = F7K.PIN6 
AND F7.PIN7 = F7K.PIN7 
AND F7.PIN8 = F7K.PIN8 
AND F7.PIN = F7A.PIN 
AND F7.PIN1 = F7A.PIN1 
AND F7.PIN2 = F7A.PIN2 
AND F7.PIN3 = F7A.PIN3 
AND F7.PIN4 = F7A.PIN4 
AND F7.PIN5 = F7A.PIN5 
AND F7.PIN6 = F7A.PIN6 
AND F7.PIN7 = F7A.PIN7 
AND F7.PIN8 = F7A.PIN8'
)
AS Fer1
(
KHATA_NO text,
TOTAL_AREA_H numeric,
ASSESSMENT numeric,
KHAND_NO text,
CCODE text,
IMAGINARY_MUT_NO_CHECK boolean,
MUT_NO text,
MARKED character(1),
NA_AREA_H numeric, 
TENURE_CODE text, 
TENURE_SUB_CODE text
)
where 
CASE WHEN TEXT(IMAGINARY_MUT_NO_CHECK) = 't' THEN MUT_NO < '100000' ELSE MUT_NO < '500000' END
AND CCODE = '272500130318050000' 
AND khata_no IN  
( 
SELECT khata_no     
FROM public.dblink( 
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'|| ' password='||'root', 
'select khata_no,revenue_year,ccode,active_flag from '||'echpun_bar'||'.land_revenue_demand' 
)AS 
( 
khata_no text,revenue_year text,ccode text,active_flag text 
)  
WHERE revenue_year ='2021-22' 
AND ccode = '272500130318050000'    
AND active_flag = 'Y' 
)
AND MARKED != 'Y' 
AND NA_AREA_H = 0 
AND TENURE_CODE = '2' 
AND TENURE_SUB_CODE = '7' 
AND KHATA_NO <> '500001' 
) AS A 
GROUP BY A.KHATA_NO,A.khand_no 
) T1 
INNER JOIN   
(   
SELECT DISTINCT   
KHATA_NO,
STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES,   
STRING_AGG(DISTINCT CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, '/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8), ',') AS SURVEY_HISSA    
FROM
public.dblink
(   
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'SELECT 
KHATA_NO,CCODE,
F7K.FNAME,F7K.MNAME,F7K.LNAME,
F7K.PIN,F7K.PIN1,F7K.PIN2,F7K.PIN3,F7K.PIN4,F7K.PIN5,F7K.PIN6,F7K.PIN7,F7K.PIN8     
FROM '||'mhrorpun_bar'||'.FORM7_KHATA F7K'    
)
AS 	
Fer2
(
KHATA_NO text,   
ccode text,
FNAME text,
MNAME text,
LNAME text,
pin  text,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text
)
WHERE CCODE = '272500130318050000'  
GROUP BY KHATA_NO   
)   
T2    
ON (T1.KHATA_NO = T2.KHATA_NO) 
order by t1.khata_no::bigint

akrushak---->

SELECT DISTINCT 
KHATA_NO, 
STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES, 
SUM(TOTAL_AREA_H +  POTKHARABA) AS AREA, 
SUM(ASSESSMENT) AS AKRUSHIK_ASSESSMENT, 
STRING_AGG(DISTINCT CONCAT(PIN, '/', PIN1, '/', PIN2, '/', PIN3, '/', PIN4, 
'/', PIN5, '/', PIN6, '/', PIN7, '/', PIN8), ',') AS SURVEY_HISSA, 
'RSQM' AS UNIT  
FROM 
public.dblink('dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root',
'select  
f7k.KHATA_NO, 
f7k.FNAME,
f7k.MNAME,
f7k.LNAME, 
f7a.TOTAL_AREA_H,
f7k.POTKHARABA, 
f7a.ASSESSMENT, 
f7k.PIN,
f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,
MVC.IMAGINARY_MUT_NO_CHECK,
f7.MUT_NO ,
NA_AREA_H ,
f7k.CCODE ,  
f7.TENURE_CODE,  
f7.TENURE_SUB_CODE,  
f7k.MARKED
from 
'||'mhrorpun_bar'||'.FORM7_KHATA F7K, 
'||'mhrorpun_bar'||'.FORM7_AREA F7A, 
'||'mhrorpun_bar'||'.FORM7 F7, 
'||'mhrorpun_bar'||'.M_VILLAGE_CENSUS_CHECK MVC 
WHERE 
 F7K.CCODE = MVC.CCODE  
AND F7.CCODE = F7A.CCODE  
AND F7.CCODE = F7K.CCODE  
AND F7.PIN = F7K.PIN  
AND F7.PIN1 = F7K.PIN1  
AND F7.PIN2 = F7K.PIN2  
AND F7.PIN3 = F7K.PIN3  
AND F7.PIN4 = F7K.PIN4  
AND F7.PIN5 = F7K.PIN5  
AND F7.PIN6 = F7K.PIN6  
AND F7.PIN7 = F7K.PIN7  
AND F7.PIN8 = F7K.PIN8  
AND F7.PIN = F7A.PIN  
AND F7.PIN1 = F7A.PIN1  
AND F7.PIN2 = F7A.PIN2  
AND F7.PIN3 = F7A.PIN3  
AND F7.PIN4 = F7A.PIN4  
AND F7.PIN5 = F7A.PIN5  
AND F7.PIN6 = F7A.PIN6  
AND F7.PIN7 = F7A.PIN7  
AND F7.PIN8 = F7A.PIN8'
)AS ferfar
(
KHATA_NO text, 
FNAME text,
MNAME text,
LNAME text, 
TOTAL_AREA_H numeric,
POTKHARABA  numeric,
ASSESSMENT  numeric, 
PIN text,
PIN1 text,
PIN2 text,
PIN3 text,
PIN4 text,
PIN5 text,
PIN6 text,
PIN7 text,
PIN8 text,
IMAGINARY_MUT_NO_CHECK boolean, 
MUT_NO text,
NA_AREA_H numeric,
CCODE text,  
TENURE_CODE text,  
TENURE_SUB_CODE  text,  
MARKED character(1)
)
where 
CASE 
WHEN 
TEXT(IMAGINARY_MUT_NO_CHECK) = 't'  
THEN 
MUT_NO < '100000'  
ELSE 
MUT_NO < '500000'  
END 
AND NA_AREA_H != 0  
AND CCODE = '272500130318050000'  
AND TENURE_CODE != '2'  
AND TENURE_SUB_CODE != '7'  
AND MARKED != 'Y'    
AND khata_no IN  
( 
SELECT khata_no     
FROM public.dblink( 
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'|| ' password='||'root', 
'select khata_no,revenue_year,ccode,active_flag from '||'echpun_bar'||'.land_revenue_demand' 
)AS 
( 
khata_no text,revenue_year text,ccode text,active_flag text 
)  
WHERE revenue_year ='2021-22' 
AND ccode = '272500130318050000'    
AND active_flag = 'Y' 
)
AND KHATA_NO <> '500001'  
GROUP BY 
KHATA_NO 





----egs---->

SELECT
T1.KHATA_NO,
T2.KHATEDAR_NAMES,
TOTAL_IRR_SUM ,
PURE_CROP_CODE,
CROP_NAME
FROM
(
SELECT
KHATA_NO,
T1.PURE_CROP_CODE,
CROP_NAME,
SUM(PURE_IRR_SUM) AS TOTAL_IRR_SUM
FROM
(
SELECT DISTINCT
F7K.KHATA_NO,
F12.PURE_CROP_CODE,
MC.CROP_NAME,
F12.PURE_IRR_AREA_H AS PURE_IRR_SUM
FROM
(
select
f7kk.KHATA_NO,
mcc.PURE_CROP_CODE,
CROP_NAME,
PURE_IRR_AREA_H AS PURE_IRR_SUM from
(select * from public.dblink
('dbname='||'mhrorpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select f7k.KHATA_NO,f7k.MARKED,f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,f7k.ccode from
'||'mhrorpun_bar'||'.FORM7_KHATA F7K'
)
AS(
KHATA_NO TEXT,
MARKED TEXT,
PIN TEXT,
PIN1 TEXT,
PIN2 TEXT,
PIN3 TEXT,
PIN4 TEXT,
PIN5 TEXT,
PIN6 TEXT,
PIN7 TEXT,
PIN8 TEXT,
ccode text
)
where CCODE = '272500130318050000'
AND MARKED <> 'Y'
AND KHATA_NO <> '500001'  
)as f7kk,
(
select * from public.dblink

(
'dbname='||'mhrorpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select mc.CROP_NAME,mc.CROP_CODE from '||'mhrorpun_bar'||'.M_CROP_NAME MC'
)
AS(
CROP_NAME TEXT,
PURE_CROP_CODE TEXT
)
)
as mcc,
(
select * from public.dblink
('dbname='||'mhrorpuncrop' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select f12.PURE_CROP_CODE,f12.PURE_IRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE
 from '||'mhrorpuncrop_bar'||'.FORM12 F12')
AS
(
PURE_CROP_CODE TEXT,
PURE_IRR_AREA_H numeric(18,2),
NUMERIC_PIN numeric(18),
YEAR_CULTI int,      
ccode text
)where CCODE = '272500130318050000'
 
)as f12cr
WHERE
F7KK.KHATA_NO = TEXT(F12cr.NUMERIC_PIN)
AND F7KK.CCODE = F12cr.CCODE
AND f12cr.YEAR_CULTI =2021
AND f12cr.PURE_IRR_AREA_H <> '0'
AND CAST(f12cr.PURE_CROP_CODE AS VARCHAR) LIKE '27%'

AND KHATA_NO NOT IN
(
SELECT KHATA_NO FROM
public.dblink
('dbname='||'echpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||'echpun_bar'||'.LAND_REVENUE_DEMAND LRD'
)
AS
(
 KHATA_NO text,CCODE text, revenue_year text
)
 WHERE CCODE= '272500130318050000'  and revenue_year = '2021-22' and khata_no <> '')
)
UNION ALL
SELECT DISTINCT
F7K.KHATA_NO,
F12.CONST_CROP_CODE,
MC.CROP_NAME,
F12.CONST_IRR_AREA_H AS CONST_IRR_SUM
FROM(
select
f7kk.KHATA_NO,
mcc.CROP_CODE,
CROP_NAME,
CONST_IRR_AREA_H AS CONST_IRR_SUM from
(select * from public.dblink
('dbname='||'mhrorpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select f7k.KHATA_NO,f7k.MARKED,f7k.PIN,f7k.PIN1,f7k.PIN2,f7k.PIN3,f7k.PIN4,f7k.PIN5,f7k.PIN6,f7k.PIN7,f7k.PIN8,f7k.ccode from
'||'mhrorpun_bar'||'.FORM7_KHATA F7K'
)
AS(
KHATA_NO TEXT,
MARKED TEXT,
PIN TEXT,
PIN1 TEXT,
PIN2 TEXT,
PIN3 TEXT,
PIN4 TEXT,
PIN5 TEXT,
PIN6 TEXT,
PIN7 TEXT,
PIN8 TEXT,
ccode text
)
where CCODE = '272500130318050000'
AND MARKED <> 'Y'
AND KHATA_NO <> '500001'  
)as f7kk,
(
select * from public.dblink
(
'dbname='||'mhrorpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select mc.CROP_NAME,mc.CROP_CODE from '||'mhrorpun_bar'||'.M_CROP_NAME MC'
)
AS(
CROP_NAME TEXT,
CROP_CODE TEXT
)
)
as mcc,
(
select * from public.dblink
('dbname='||'mhrorpuncrop' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select f12.CONST_CROP_CODE,f12.CONST_IRR_AREA_H,f12.NUMERIC_PIN,f12.YEAR_CULTI,F12.CCODE
 from '||'mhrorpuncrop_bar'||'.FORM12 F12')
AS
(
CONST_CROP_CODE TEXT,
CONST_IRR_AREA_H numeric(18,2),
NUMERIC_PIN numeric(18),
YEAR_CULTI int,
ccode text
)where CCODE = '272500130318050000'

)as f12cr
WHERE
F7KK.KHATA_NO = TEXT(F12cr.NUMERIC_PIN)
AND F7KK.CCODE = F12cr.CCODE
AND f12cr.YEAR_CULTI =2021
AND f12cr.CONST_IRR_AREA_H <> '0'
AND CAST(f12cr.CONST_CROP_CODE AS VARCHAR) LIKE '27%'
AND F12cr.CONST_CROP_CODE = MCC.CROP_CODE
AND KHATA_NO NOT IN(
SELECT KHATA_NO FROM
public.dblink
('dbname='||'echpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||'echpun_bar'||'.LAND_REVENUE_DEMAND LRD'
 )AS(
 KHATA_NO text,CCODE text, revenue_year text
 )
 WHERE CCODE= '272500130318050000'  and revenue_year = '2021-22' and khata_no <> '')
)
)
T1
GROUP BY
KHATA_NO,
T1.PURE_CROP_CODE,
CROP_NAME
)
T1
INNER JOIN
(
SELECT DISTINCT
KHATA_NO,
STRING_AGG(DISTINCT CONCAT(FNAME, ' ', MNAME, ' ', LNAME), ' | ') AS KHATEDAR_NAMES
FROM
public.dblink
('dbname='||'mhrorpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
'select KHATA_NO,FNAME,MNAME,LNAME,ccode FROM
'||'mhrorpun_bar'||'.FORM7_KHATA F7K')
AS(KHATA_NO TEXT,FNAME TEXT,MNAME TEXT,LNAME TEXT,ccode text)
WHERE
CCODE = '272500130318050000'
AND KHATA_NO NOT IN
(
SELECT KHATA_NO FROM
public.dblink
('dbname='||'echpun' ||' host='|| ' localhost' ||' user='||'postgres'|| ' password='||'root' ,
 'SELECT LRD.KHATA_NO,LRD.CCODE,LRD.revenue_year FROM '||'echpun_bar'||'.LAND_REVENUE_DEMAND LRD'
 )AS(
 KHATA_NO text,CCODE text, revenue_year text
 )
 WHERE CCODE= '272500130318050000'  and revenue_year = '2021-22' and khata_no <> '')
GROUP BY
KHATA_NO
)
T2
ON (T1.KHATA_NO = T2.KHATA_NO)




---identiy user --loadByUserName Native query

SELECT SERVARTHID,
FULLNAME,
EMAIL_ID,
DISTRICT_CODE,
TALUKA_CODE,
(
SELECT 
TALUKA_NAME
FROM DBLINK 
(
'dbname=' || 'linkgedb_emuroll' || ' host=' || ' localhost' || ' port=5432 user=' || ' postgres' || ' password=' || ' root ',
'select district_code,taluka_code,taluka_name from ' || 'rcis_uni' || '.m_taluka_census t'
) 
AS F7 
(DISTRICT_CODE text,TALUKA_CODE text,TALUKA_NAME text)
WHERE TRIM(TALUKA_CODE) = TRIM(A.TALUKA_CODE)
AND TRIM(DISTRICT_CODE) = TRIM(A.DISTRICT_CODE) )TALUKA_NAME,
MARATHINAME,
DESG,
PASSWORD
FROM
(SELECT *
FROM DBLINK 
(
'dbname=' || 'linkgedb_emuroll' || ' host=' || ' localhost' || ' port=5432 user=' || ' postgres' || ' password=' || ' root ',
'select servarthid,fullname,email_id,district_code,taluka_code,marathiname,desg,password from ' || 'rcis_uni' || '.fpusers1 f'
) 
AS F7 
(
SERVARTHID text,FULLNAME text,EMAIL_ID text,DISTRICT_CODE text,TALUKA_CODE text,MARATHINAME text,DESG text,PASSWORD text
)
WHERE TRIM(SERVARTHID) = 'REVAAJM8301' 
)A








---form1 & odc differnce

select 
form7ccode, 
form7pin, 
form7TenureCode, 
form1TenureCode, 
form7TotalArea, 
form1TotalArea, 
form7PotKharaba, 
form1PotKharaba, 
form7NetCultiArea, 
form1NetCultiArea, 
form7Assessment,  
form1Assessment, 
case when form7potkharab_A > 0 then 'अ'  when form7potkharab_B > 0 then 'ब' end as form7PotType , 
case when form1potkharab_A > 0 then 'अ'  when form1potkharab_B > 0 then 'ब' end as form1PotType
from (
select 
ccode form7ccode,  
pin form7pin,  
string_agg(DISTINCT cast(f7a.tenure_code as text),',') form7TenureCode,
sum
(
f7a.potkharaba_a_h+f7a.potkharaba_b_h+
f7a.bagayat_area_h+f7a.jirayat_area_h+f7a.tari_area_h+f7a.varkas_area_h+
f7a.itar_area_h
)
as form7TotalArea,  
sum(f7a.potkharaba_a_h+f7a.potkharaba_b_h) as form7PotKharaba,  
sum(f7a.net_culti_area_h) as form7NetCultiArea,  
sum(f7a.assessment) as form7Assessment, 
sum(f7a.potkharaba_a_h) as form7potkharab_A, 
sum(f7a.potkharaba_b_h) as form7potkharab_B
from dblink
( 
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select  
f7.ccode,  
f7.pin,  
f7.tenure_code,
f7a.potkharaba_a_h,
f7a.potkharaba_b_h,
f7a.bagayat_area_h,
f7a.jirayat_area_h,
f7a.tari_area_h   ,
f7a.varkas_area_h ,
f7a.itar_area_h	  ,
f7a.net_culti_area_h,  
f7a.assessment, 
F7.PIN1,
F7.PIN2,
F7.PIN3,
F7.PIN4,
F7.PIN5,
F7.PIN6,
F7.PIN7,
F7.PIN8,
F7.MARKED 
from 
'||'mhrorpun_shi'||'.form7 f7,
'||'mhrorpun_shi'||'.form7_area f7a,
'||'mhrorpun_shi'||'.m_tenure t  
where  
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
AND T.TENURE_CODE=F7.TENURE_CODE '
)
AS f7a
(
ccode text,
pin text,  
tenure_code text,
potkharaba_a_h numeric,
potkharaba_b_h numeric,
bagayat_area_h numeric,
jirayat_area_h numeric,
tari_area_h numeric   ,
varkas_area_h numeric ,
itar_area_h numeric ,  
net_culti_area_h   numeric,
assessment  numeric,
PIN1 text,
PIN2 text,
PIN3 text,
PIN4 text,
PIN5 text,
PIN6 text,
PIN7 text,
PIN8 text,
MARKED text
)  
where F7a.MARKED <> 'Y'
AND CONCAT
(
F7a.PIN , '/', 
F7a.PIN1, '/', 
F7a.PIN2, '/', 
F7a.PIN3, '/', 
F7a.PIN4, '/', 
F7a.PIN5, '/', 
F7a.PIN6, '/', 
F7a.PIN7, '/', 
F7a.PIN8
) 
NOT IN  
(  
SELECT  
CONCAT
(FK.PIN , '/', 
 FK.PIN1, '/', 
 FK.PIN2, '/', 
 FK.PIN3, '/', 
 FK.PIN4, '/', 
 FK.PIN5, '/', 
 FK.PIN6, '/', 
 FK.PIN7, '/', 
 FK.PIN8
)  
FROM  
dblink 
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select 
FK.PIN ,
FK.PIN1,
FK.PIN2,
FK.PIN3,
FK.PIN4,
FK.PIN5,
FK.PIN6,
FK.PIN7,
FK.PIN8,
ccode,khata_no
from '||'mhrorpun_shi'||'.form7_KHATA FK '
)
AS FK
(
PIN  text,
PIN1 text,
PIN2 text,
PIN3 text,
PIN4 text,
PIN5 text,
PIN6 text,
PIN7 text,
PIN8 text,
ccode text,
khata_no text
)
WHERE FK.CCODE ='' AND FK.KHATA_NO = '500001'	
)
group by f7a.ccode,f7a.pin
)x inner join
(  
select 
f1.ccode form1ccode,  
f1.pin form1pin,  
string_agg(DISTINCT cast(f1.tenure_code as text),',') form1TenureCode, 
sum(f1.net_cultivable_h+f1.uncultivable_h+f1.uncultivable_r) as form1TotalArea,  
sum(f1.uncultivable_h+f1.uncultivable_r) as form1PotKharaba,  
sum(f1.net_cultivable_h) as form1NetCultiArea,  
sum(f1.assessment) as form1Assessment, 
sum(f1.uncultivable_h) as form1potkharab_A, 
sum(f1.uncultivable_r) as form1potkharab_B  
from dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select  
f1.ccode,  
f1.pin,  
f1.tenure_code, 
f1.net_cultivable_h,
f1.uncultivable_h,
f1.uncultivable_r,  
f1.assessment 
from '||'mhrorpun_shi'||'.form1 f1,'||'mhrorpun_shi'||'.m_tenure t  
where T.TENURE_CODE=F1.TENURE_CODE  '
)
AS F1
(
ccode text,
pin text,  
tenure_code text,
net_cultivable_h numeric,
uncultivable_h numeric,
uncultivable_r  numeric,
assessment numeric
)

group by f1.ccode,f1.pin )y

on(x.form7pin=y.form1pin and x.form7ccode=y.form1ccode and form7ccode ='' )  




---form1 ODC Report




select
a.ccode,
a.pin,
CONCAT  
(  
case when a.pin1 <> '' then   	   a.pin1 end,  
case when a.pin2 <> '' then  '/'|| a.pin2 end,  
case when a.pin3 <> '' then  '/'|| a.pin3 end,  
case when a.pin4 <> '' then  '/'|| a.pin4 end,  
case when a.pin5 <> '' then  '/'|| a.pin5 end,  
case when a.pin6 <> '' then  '/'|| a.pin6 end,  
case when a.pin7 <> '' then  '/'|| a.pin7 end,  
case when a.pin8 <> '' then  '/'|| a.pin8 end  
)as hissaNo,
a.net_cultivable_h+a.uncultivable_h+a.uncultivable_r as total_area_h, 
a.net_cultivable_h, 
a.assessment, 
a.uncultivable_h, 
a.uncultivable_r,  
a.tenure_name 
from  
dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select   
F1.ccode,
F1.pin,  
F1.pin1, 
F1.pin2, 
F1.pin3, 
F1.pin4, 
F1.pin5, 
F1.pin6, 
F1.pin7, 
F1.pin8,
F1.net_cultivable_h,
F1.uncultivable_h,
F1.uncultivable_r,  
F1.assessment,
t.tenure_name 
from 
'||'mhrorpun_shi'||'.form1 f1,
'||'mhrorpun_shi'||'.m_tenure t  
where t.tenure_code=f1.tenure_code'
)
AS 
A
(
ccode text,
pin  text,  
pin1 text, 
pin2 text, 
pin3 text, 
pin4 text, 
pin5 text, 
pin6 text, 
pin7 text, 
pin8 text,
net_cultivable_h numeric,
uncultivable_h numeric,
uncultivable_r numeric, 
assessment numeric,
tenure_name text
)
where a.ccode='' order by a.pin





--form1c getAll getBypinandclauseandccode

select * from ( 
select * from ( 
select  
a.hissano,  
khatedar_name,  
a.total_area_h,  
a.assessment,  
case when a.aadeshnodate <> '' or a.aadeshnodate is not null then a.aadeshnodate else b.aadesh_on_date end as aadeshondateog,  
id,  
a.ccode,  
Authority_competent_to_permit_transfer_of_land,  
Details_of_Unclaimed_Amount,  
breach_of_condition,  
inspecting_Officer,  
inspection_date,  
name_of_village_panchayat,  
terms_of_grant,remarks,pin,clause,purpose_of_land_intended,code_no
from  
( 
select 
ccode,
pin, 
concat( 
case when pin1 <> '' then 	   pin1 end, 
case when pin2 <> '' then '/' || pin2 end, 
case when pin3 <> '' then '/' || pin3 end, 
case when pin4 <> '' then '/' || pin4 end, 
case when pin5 <> '' then '/' || pin5 end, 
case when pin6 <> '' then '/' || pin6 end, 
case when pin7 <> '' then '/' || pin7 end, 
case when pin8 <> '' then '/' || pin8 end  
) as hissaNo, 
FNAME||' '||MNAME||' '||LNAME AS KHATEDAR_NAME, 
TOTAL_AREA_H,
assessment, 
aadeshnodate, 
CLAUSE,
CODE_NO
from dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select   
distinct on(f7a.pin,f7a.pin1,f7a.pin2,f7a.pin3,f7a.pin4,f7a.pin5,f7a.pin6,f7a.pin7,f7a.pin8)  
f7a.ccode       ,
f7a.pin         , 
f7a.pin1        , 
f7a.pin2        , 
f7a.pin3        , 
f7a.pin4        , 
f7a.pin5        , 
f7a.pin6        , 
f7a.pin7        , 
f7a.pin8        ,  
F7K.FNAME       ,
F7K.MNAME       ,
F7K.LNAME       , 
F7A.TOTAL_AREA_H,
F7A.assessment  , 
F1C.aadeshnodate, 
MTR.CLAUSE      ,
MTR.CODE_NO 
from  
'||'mhrorpun_shi'||'.form1c f1c, 
'||'mhrorpun_shi'||'.form7_khata f7k, 
'||'mhrorpun_shi'||'.form7_area f7a, 
'||'mhrorpun_shi'||'.form7 f7, 
'||'mhrorpun_shi'||'.m_tenure_restriction MTR 
where  
MTR.CODE_NO=F1C.form1c_code 
AND f7a.ccode=f7.ccode  
and  
f7k.ccode=f7a.ccode  
and f1c.ccode=f7a.ccode  
and f7a.pin=f7.pin  
and f7a.pin1=f7.pin1  
and f7a.pin2=f7.pin2  
and f7a.pin3=f7.pin3  
and f7a.pin4=f7.pin4  
and f7a.pin5=f7.pin5  
and f7a.pin6=f7.pin6  
and f7a.pin7=f7.pin7  
and f7a.pin8=f7.pin8  
and f7a.pin=f7k.pin  
and f7a.pin1=f7k.pin1  
and f7a.pin2=f7k.pin2  
and f7a.pin3=f7k.pin3  
and f7a.pin4=f7k.pin4  
and f7a.pin5=f7k.pin5  
and f7a.pin6=f7k.pin6  
and f7a.pin7=f7k.pin7  
and f7a.pin8=f7k.pin8  
and f1c.pin=f7a.pin  
and f1c.pin1=f7a.pin1  
and f1c.pin2=f7a.pin2  
and f1c.pin3=f7a.pin3  
and f1c.pin4=f7a.pin4  
and f1c.pin5=f7a.pin5  
and f1c.pin6=f7a.pin6  
and f1c.pin7=f7a.pin7  
and f1c.pin8=f7a.pin8  
group by f7a.ccode,f7a.pin,f7a.pin1,f7a.pin2,f7a.pin3,f7a.pin4,f7a.pin5,f7a.pin6,f7a.pin7,f7a.pin8, 
F7K.FNAME,F7K.MNAME,F7K.LNAME,F7A.TOTAL_AREA_H,F7A.assessment, 
F1C.aadeshnodate, 
MTR.CLAUSE,MTR.CODE_NO'	
)AS A
(
ccode text,
pin  text, 
pin1 text, 
pin2 text, 
pin3 text, 
pin4 text, 
pin5 text, 
pin6 text, 
pin7 text, 
pin8 text,  
FNAME text,
MNAME text,
LNAME text, 
TOTAL_AREA_H numeric,
assessment numeric, 
aadeshnodate text, 
CLAUSE text,
CODE_NO integer 
)  
where  ccode='' 
)a 
LEFT JOIN 
(  
select 
id,
ccode,  
concat( 
case when pin1<> ' ' then        pin1 end,  
case when pin2<> ' ' then '/' || pin2 end,  
case when pin3<> ' ' then '/' || pin3 end,  
case when pin4<> ' ' then '/' || pin4 end,  
case when pin5<> ' ' then '/' || pin5 end,  
case when pin6<> ' ' then '/' || pin6 end,  
case when pin7<> ' ' then '/' || pin7 end,  
case when pin8<> ' ' then '/' || pin8 end  
)as hissaNo, 
Authority_competent_to_permit_transfer_of_land,  
Details_of_Unclaimed_Amount,
breach_of_condition,
Inspecting_Officer,
inspection_date,
name_of_village_panchayat,
terms_of_grant,remarks ,
active_flag,
aadesh_on_date,
purpose_of_land_intended,
pin as epin   
from dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select   
id,
ccode,  
pin1 ,  
pin2 ,  
pin3 ,  
pin4 ,  
pin5 ,  
pin6 ,  
pin7 ,  
pin8 ,  
Authority_competent_to_permit_transfer_of_land,  
Details_of_Unclaimed_Amount,
breach_of_condition,
Inspecting_Officer,
inspection_date,
name_of_village_panchayat,
terms_of_grant,
remarks ,
active_flag,
aadesh_on_date,
purpose_of_land_intended,
pin  
from '||'echpun_shi'||'.echawdi_form1c ef1c'
) 
AS B
(
id bigint,
ccode text,  
pin1  text,  
pin2  text,  
pin3  text,  
pin4  text,  
pin5  text,  
pin6  text,  
pin7  text,  
pin8  text,  
Authority_competent_to_permit_transfer_of_land text,  
Details_of_Unclaimed_Amount  text,
breach_of_condition text,
Inspecting_Officer text,
inspection_date date,
name_of_village_panchayat text,
terms_of_grant text,
remarks  text,
active_flag text,
aadesh_on_date text,
purpose_of_land_intended text,
pin  text
) 
where active_flag='Y' 
)
B  
ON
(A.ccode=B.ccode and A.pin=B.epin and A.hissaNo=B.hissaNo) 
where  
a.ccode='' 
and code_no=1  
)X 
)Y where pin='1'


--form1c clause 


select 
code_no,clause||'-'||discription 
from dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select code_no,discription,tenure_code,clause from '||'mhrorpun_shi'||'.m_tenure_restriction'	
)
AS clauses
(
code_no int,
discription text,
tenure_code text,
clause text
)
where tenure_code='2'





--form1b getAll And Report separate pin out for report query

select 
* 
from 
(
select f7.ccode, f7.pin, CONCAT 
( 
case when f7.pin1 != '' then      f7.pin1 end,case when f7.pin2 != '' then '/'||f7.pin2 end, 
case when f7.pin3 != '' then '/'||f7.pin3 end,case when f7.pin4 != '' then '/'||f7.pin4 end, 
case when f7.pin5 != '' then '/'||f7.pin5 end,case when f7.pin6 != '' then '/'||f7.pin6 end, 
case when f7.pin7 != '' then '/'||f7.pin7 end,case when f7.pin8 != '' then '/'||f7.pin8 end 
) 
as HissaNo, 
f7.total_area_h,f7.assessment,EF1B.id,EF1B.unassessed_area,EF1B.public_rights_of_way_and_easements,EF1B.remarks 
from  
( 
select  
total_area_h, 
assessment, 
ccode, 
pin, 
pin1, 
pin2, 
pin3, 
pin4, 
pin5, 
pin6, 
pin7, 
pin8, 
tenure_code, 
CONCAT 
( 
case when pin1 != '' then        pin1 end,case when pin2 != '' then '/' || pin2 end, 
case when pin3 != '' then '/' || pin3 end,case when pin4 != '' then '/' || pin4 end, 
case when pin5 != '' then '/' || pin5 end,case when pin6 != '' then '/' || pin6 end, 
case when pin7 != '' then '/' || pin7 end,case when pin8 != '' then '/' || pin8 end 
) 
as HissaNo 
from dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select 
f7a.total_area_h, 
f7a.assessment, 
f7.ccode, 
f7.pin, 
f7.pin1, 
f7.pin2, 
f7.pin3, 
f7.pin4, 
f7.pin5, 
f7.pin6, 
f7.pin7, 
f7.pin8, 
F7.tenure_code 
from 
'||'mhrorpun_shi'||'.FORM7_area f7a,
'||'mhrorpun_shi'||'.FORM7 f7 
where f7a.ccode=f7.ccode  
and f7.pin=f7a.pin  
and f7.pin=f7a.pin  
and f7.pin1=f7a.pin1  
and f7.pin2=f7a.pin2  
and f7.pin3=f7a.pin3  
and f7.pin4=f7a.pin4  
and f7.pin5=f7a.pin5  
and f7.pin6=f7a.pin6  
and f7.pin6=f7a.pin6  
and f7.pin7=f7a.pin7  
and f7.pin8=f7a.pin8'  
)
AS f7
(
total_area_h numeric, 
assessment numeric, 
ccode text, 
pin text, 
pin1 text, 
pin2 text, 
pin3 text, 
pin4 text, 
pin5 text, 
pin6 text, 
pin7 text, 
pin8 text, 
tenure_code text
)
where ccode='' AND tenure_code='3'
) 
as f7 
left join  
(
select  
ccode, 
CONCAT
(
case when pin1 != ' ' then       pin1 end,case when pin2 != ' ' then '/'|| pin2 end, 
case when pin3 != ' ' then '/'|| pin3 end,case when pin4 != ' ' then '/'|| pin4 end, 
case when pin5 != ' ' then '/'|| pin5 end,case when pin6 != ' ' then '/'|| pin6 end, 
case when pin7 != ' ' then '/'|| pin7 end,case when pin8 != ' ' then '/'|| pin8 end
)
as HissaNo, 
id,
unassessed_area,
public_rights_of_way_and_easements,
remarks,
pin, 
pin1, 
pin2, 
pin3, 
pin4, 
pin5, 
pin6, 
pin7, 
pin8, 
active_flag 
from dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select 
EF1B.ccode, 
EF1B.id,
EF1B.unassessed_area,
EF1B.public_rights_of_way_and_easements,
EF1B.remarks,
ef1b.pin, 
ef1b.pin1, 
ef1b.pin2, 
ef1b.pin3, 
ef1b.pin4, 
ef1b.pin5, 
ef1b.pin6, 
ef1b.pin7, 
ef1b.pin8, 
ef1b.active_flag 
from 
'||'echpun_shi'||'.echawdi_form1B ef1b '
)
AS ef1b
(
ccode text, 
id text,
unassessed_area text,
public_rights_of_way_and_easements text,
remarks text,
pin text, 
pin1 text, 
pin2 text, 
pin3 text, 
pin4 text, 
pin5 text, 
pin6 text, 
pin7 text, 
pin8 text, 
active_flag character(1)
)
where ef1b.active_flag='Y'
) as ef1b 
ON 
( 
ef1b.ccode=f7.ccode  
and ef1b.pin=f7.pin  
and trim(ef1b.pin1)=trim(f7.pin1)  
and trim(ef1b.pin2)=trim(f7.pin2)  
and trim(ef1b.pin3)=trim(f7.pin3)  
and trim(ef1b.pin4)=trim(f7.pin4) 
and trim(ef1b.pin5)=trim(f7.pin5)  
and trim(ef1b.pin6)=trim(f7.pin6)  
and trim(ef1b.pin7)=trim(f7.pin7)  
and trim(ef1b.pin8)=trim(f7.pin8) 
) 
order by  cast(f7.pin  as integer) asc)a 
where pin='1' 






--form 2 getAll and report query



select  
f7a.pin, 
f7a.na_area_h, 
ef2.nature_and_terms_of_na_grant, 
ef2.part, 
ef2.purpose_of_use, 
ef2.remarks, 
ef2.id, 
ef2.period_from, 
ef2.period_to, 
f7a.ccode, 
CONCAT 
( 
case when f7a.pin1 != '' then        f7a.pin1 end,case when f7a.pin2 != '' then '/' || f7a.pin2 end, 
case when f7a.pin3 != '' then '/' || f7a.pin3 end,case when f7a.pin4 != '' then '/' || f7a.pin4 end, 
case when f7a.pin5 != '' then '/' || f7a.pin5 end,case when f7a.pin6 != '' then '/' || f7a.pin6 end, 
case when f7a.pin7 != '' then '/' || f7a.pin7 end,case when f7a.pin8 != '' then '/' || f7a.pin8 end 
)as HissaNo, 
ef2.authority, 
ef2.occupany_price, 
ef2.name_of_first_occupant, 
ef2.entry_number_in_taluka_form_2,f7a.na_assessment  
from  
(
select * from dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select 
f7a.pin,
f7a.pin1,
f7a.pin2,
f7a.pin3,
f7a.pin4,
f7a.pin5,
f7a.pin6,
f7a.pin7,
f7a.pin8, 
f7a.na_area_h,
f7a.ccode ,
f7a.na_assessment
from 
'||'mhrorpun_shi'||'.FORM7_area f7a'
)
AS f7a
(
pin text,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text, 
na_area_h numeric,
ccode text,
na_assessment numeric
) 
where ccode='' and pin='1'
) f7a  

left join  
(
select * from dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select 
nature_and_terms_of_na_grant, 
part, 
purpose_of_use, 
remarks, 
id, 
period_from, 
period_to,
pin ,
pin1,
pin2,
pin3,
pin4,
pin5,
pin6,
pin7,
pin8,
ccode,
active_flag,
authority, 
occupany_price, 
name_of_first_occupant, 
entry_number_in_taluka_form_2
from 
'||'echpun_shi'||'echawdi_form2 ef2')
AS
ef2
(
nature_and_terms_of_na_grant text, 
part text, 
purpose_of_use text, 
remarks text, 
id bigint , 
period_from date, 
period_to date ,
pin text,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text,
ccode text,
active_flag character(1),
authority text, 
occupany_price text, 
name_of_first_occupant text, 
entry_number_in_taluka_form_2 text
)
where active_flag='Y'
) ef2 on 
( 
ef2.ccode=f7a.ccode 
and ef2.pin=f7a.pin  
and trim(ef2.pin1)=trim(f7a.pin1)  
and trim(ef2.pin2)=trim(f7a.pin2)  
and trim(ef2.pin3)=trim(f7a.pin3)  
and trim(ef2.pin4)=trim(f7a.pin4)  
and trim(ef2.pin5)=trim(f7a.pin5)  
and trim(ef2.pin6)=trim(f7a.pin6)  
and trim(ef2.pin7)=trim(f7a.pin7)  
and trim(ef2.pin8)=trim(f7a.pin8)   
)




--form3 getAll & report query 


select  
b.classes, 
b.kind_and_how_long_continuable, 
b.running_no_each_class, 
a.pin, 
a.hissa_no, 
a.net_culti_area_h, 
a.assessment, 
a.na_area_h, 
a.na_assessment, 
b.sanad_no, 
b.no_in_register_of_lands, 
b.juni_salami, 
b.rate, 
b.ech_assessment, 
b.total, 
b.net_pending, 
b.remarks,
b.id, 
a.ccode, 
a.potkharab, 
a.total_area_h  from ( 
SELECT 
F7.cCode,
F7.pin,
f7.na_area_h, 
CONCAT( 
case when pin1 <> '' then   	    pin1 end, 
case when pin2 <> '' then '/' || pin2 end, 
case when pin3 <> '' then '/' || pin3 end, 
case when pin4 <> '' then '/' || pin4 end, 
case when pin5 <> '' then '/' || pin5 end, 
case when pin6 <> '' then '/' || pin6 end, 
case when pin7 <> '' then '/' || pin7 end, 
case when pin8 <> '' then '/' || pin8 end 
)as Hissa_No,
na_assessment,
net_culti_area_h,
assessment,
pin1,
pin2,
pin3,
pin4,
pin5,
pin6,
pin7,
pin8,
(potkharaba_a_h+potkharaba_b_h) as potkharab,
total_area_h 
FROM dblink
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select 
F7.cCode,
F7.pin,
F7A.na_area_h, 
F7a.na_assessment,
F7a.net_culti_area_h,
F7a.assessment,
f7.pin1,
f7.pin2,
f7.pin3,
f7.pin4,
f7.pin5,
f7.pin6,
f7.pin7,
f7.pin8,
f7a.potkharaba_a_h,
f7a.potkharaba_b_h,
f7a.total_area_h,
f7.TENURE_CODE,
f7.TENURE_SUB_CODE,
f7.MARKED 
from 
'||'mhrorpun_shi'||'.FORM7 F7, 
'||'mhrorpun_shi'||'.FORM7_area F7a 
WHERE 
F7.CCODE = F7A.CCODE 
AND F7.PIN = F7A.PIN 
AND F7.PIN1 = F7A.PIN1 
AND F7.PIN2 = F7A.PIN2 
AND F7.PIN3 = F7A.PIN3 
AND F7.PIN4 = F7A.PIN4 
AND F7.PIN5 = F7A.PIN5 
AND F7.PIN6 = F7A.PIN6 
AND F7.PIN7 = F7A.PIN7 
AND F7.PIN8 = F7A.PIN8'
)AS f7
(
cCode text,
pin text,
na_area_h numeric, 
na_assessment numeric,
net_culti_area_h numeric,
assessment numeric,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text,
potkharaba_a_h numeric,
potkharaba_b_h numeric,
total_area_h  numeric,
TENURE_CODE text,
TENURE_SUB_CODE text,
MARKED text
)
where 
	TENURE_CODE = '2' 
AND TENURE_SUB_CODE IN ('7','2') 
AND MARKED != 'Y' 
AND cCode = ''
AND pin='1' 
AND CONCAT
(
PIN , '/', 
PIN1, '/', 
PIN2, '/', 
PIN3, '/', 
PIN4, '/', 
PIN5, '/', 
PIN6, '/', 
PIN7, '/', 
PIN8
) 
NOT IN  
(  
SELECT  
CONCAT
(FK.PIN , '/', 
 FK.PIN1, '/', 
 FK.PIN2, '/', 
 FK.PIN3, '/', 
 FK.PIN4, '/', 
 FK.PIN5, '/', 
 FK.PIN6, '/', 
 FK.PIN7, '/', 
 FK.PIN8
)  
FROM  
dblink 
(
'dbname='||'mhrorpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select 
FK.PIN ,
FK.PIN1,
FK.PIN2,
FK.PIN3,
FK.PIN4,
FK.PIN5,
FK.PIN6,
FK.PIN7,
FK.PIN8,
ccode,khata_no
from '||'mhrorpun_shi'||'.form7_KHATA FK '
)
AS FK
(
PIN  text,
PIN1 text,
PIN2 text,
PIN3 text,
PIN4 text,
PIN5 text,
PIN6 text,
PIN7 text,
PIN8 text,
ccode text,
khata_no text
)
WHERE FK.CCODE ='' AND FK.KHATA_NO = '500001'	
)
)
a left join 
(
select * from dblink
(
'dbname='||'echpun'||' host='||'localhost'||' user='||'postgres'||' password='||'root', 
'select 
ccode,
pin ,
pin1,
pin2,
pin3,
pin4,
pin5,
pin6,
pin7,
pin8,
active_flag,
classes, 
kind_and_how_long_continuable, 
running_no_each_class, 
sanad_no, 
no_in_register_of_lands, 
juni_salami, 
rate, 
ech_assessment, 
total, 
net_pending, 
remarks,
id 
from '||'echpun_shi'||'.echawdi_form3' 
)	
AS
(
ccode text,
pin  text,
pin1 text,
pin2 text,
pin3 text,
pin4 text,
pin5 text,
pin6 text,
pin7 text,
pin8 text,
active_flag character(1),
classes text, 
kind_and_how_long_continuable text, 
running_no_each_class text, 
sanad_no text, 
no_in_register_of_lands text, 
juni_salami text, 
rate text, 
ech_assessment text, 
total text, 
net_pending text, 
remarks text,
id bigint
)
where active_flag ='Y'  
)b
on 
( 
a.ccode=b.ccode 
and a.pin=b.pin 
and a.pin1=trim(b.pin1) 
and a.pin2=trim(b.pin2) 
and a.pin3=trim(b.pin3) 
and a.pin4=trim(b.pin4) 
and a.pin5=trim(b.pin5) 
and a.pin6=trim(b.pin6) 
and a.pin7=trim(b.pin7) 
and a.pin8=trim(b.pin8) 
)