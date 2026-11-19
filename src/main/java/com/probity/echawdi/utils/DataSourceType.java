package com.probity.echawdi.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataSourceType {

//    ferfar master db.	
//	linkgedb_emuroll("linkagedbemuroll"), //1
	
//    echawadi master db..
	
	echmst("echmst"),  //2
	
////	echawadi datasources to persist and get echawadi form1-21 records...
	
	echpun_shi("echpunshi"), //3
	echpun_bar("echpunbar"), //4
	echnas_bag("echnasbag"), //5
	echako_ako("echakoako"), //6
	echnan_mud("echnanmud"), //7
	echrai_tal("echraital"), //8 
	echnag_rul("echnagrul"), //9

	echahm_ako("echahmako"), //10
	echtha_tha("echthatha"), //11
	echtha_kal("echthakal"), //12
	echtha_amb("echthaamb"), //13
	echtha_bhi("echthabhi"), //14
	echtha_sha("echthasha"), //15
	echtha_ulh("echthaulh"), //16
	echtha_mur("echthamur"), //17 

	echmsd_and("echmsdand"), //18
	echmsd_bor("echmsdbor"), //19
	echmsd_kur("echmsdkur"), //20

	echrai_ali("echraiali"), //21
	echrai_pan("echraipan"), //22
	echrai_pen("echraipen"), //23 
	echrai_kar("echraikar"), //24
	echrai_kha("echraikha"), //25
	echrai_ura("echraiura"), //26
	echrai_sud("echraisud"), //27
	echrai_man("echraiman"), //28
	echrai_roh("echrairoh"), //29 
	echrai_mur("echraimur"), //30
	echrai_shr("echraishr"), //31
	echrai_mha("echraimha"), //32
	echrai_mah("echraimah"), //33
	echrai_pol("echraipol"), //34
	
	echrat_rat("echratrat"), //35
	echrat_man("echratman"), //36
	echrat_dap("echratdap"), //37
	echrat_ked("echratked"), //38
	echrat_chp("echratchp"), //39
	echrat_guh("echratguh"), //40
	echrat_sag("echratsag"), //41
	echrat_lan("echratlan"), //42
	echrat_raj("echratraj"), //43
	
	echsin_saw("echsinsaw"), //44
	echsin_kan("echsinkan"), //45
	echsin_kud("echsinkud"), //46
	echsin_dev("echsindev"), //47
	echsin_dod("echsindod"), //48
	echsin_mal("echsinmal"), //49
	echsin_ven("echsinven"), //50 
	echsin_vai("echsinvai"), //51
	
	echpal_pal("echpalpal"), //52
	echpal_vas("echpalvas"), //53
	echpal_wad("echpalwad"), //54
	echpal_jaw("echpaljaw"), //55 
	echpal_mok("echpalmok"), //56
	echpal_dah("echpaldah"), //57
	echpal_tal("echpaltal"), //58
	echpal_vik("echpalvik"), //59
	
	echpun_amb("echpunamb"), //60
	echpun_ind("echpunind"), //61
	echpun_khe("echpunkhe"), //62
	echpun_jun("echpunjun"), //63
	echpun_dau("echpundau"), //64
	echpun_pun("echpunpun"), //65
	echpun_pur("echpunpur"), //66
	echpun_bho("echpunbho"), //67
	echpun_mav("echpunmav"), //68
	echpun_mul("echpunmul"), //69
	echpun_vel("echpunvel"), //70
	echpun_hav("echpunhav"), //71

	echkol_aja("echkolaja"), //72
	echkol_kar("echkolkar"), //73
	echkol_kag("echkolkag"), //74
	echkol_gag("echkolgag"), //75
	echkol_gad("echkolgad"), //76
	echkol_cha("echkolcha"), //77
	echkol_pan("echkolpan"), //78
	echkol_bhu("echkolbhu"), //79
	echkol_rad("echkolrad"), //80
	echkol_sha("echkolsha"), //81
	echkol_shi("echkolshi"), //82
	echkol_hat("echkolhat"), //83

	echsan_atp("echsanatp"), //84
	echsan_kad("echsankad"), //85
	echsan_kav("echsankav"), //86
	echsan_kha("echsankha"), //87
	echsan_jat("echsanjat"), //88
	echsan_tas("echsantas"), //89
	echsan_pal("echsanpal"), //90
	echsan_mir("echsanmir"), //91
	echsan_val("echsanval"), //92
	echsan_shi("echsanshi"), //93
	echsan_wal("echsanwal"), //360
	
	echsat_kar("echsatkar"), //94
	echsat_kor("echsatkor"), //95
	echsat_khn("echsatkhn"), //96
	echsat_kht("echsatkht"), //97
	echsat_jav("echsatjav"), //98
	echsat_pat("echsatpat"), //99
	echsat_pha("echsatpha"), //100
	echsat_mah("echsatmah"), //101
	echsat_man("echsatman"), //102
	echsat_wai("echsatwai"), //103
	echsat_sat("echsatsat"), //104
	
	echsol_akk("echsolakk"), //105
	echsol_nor("echsolnor"), //106
	echsol_kar("echsolkar"), //107
	echsol_sou("echsolsou"), //108
	echsol_pan("echsolpan"), //109
	echsol_bar("echsolbar"), //110
	echsol_man("echsolman"), //111
	echsol_mad("echsolmad"), //112
	echsol_mal("echsolmal"), //113
	echsol_moh("echsolmoh"), //114
	echsol_san("echsolsan"), //115
	
	echahm_nag("echahmnag"), //116
	echahm_kar("echahmkar"), //117
	echahm_kop("echahmkop"), //118
	echahm_jam("echahmjam"), //119
	echahm_nev("echahmnev"), //120
	echahm_pat("echahmpat"), //121
	echahm_par("echahmpar"), //122
	echahm_rat("echahmrat"), //123
	echahm_rah("echahmrah"), //124
	echahm_she("echahmshe"), //125
	echahm_shg("echahmshg"), //126
	echahm_shr("echahmshr"), //127
	echahm_san("echahmsan"), //128
	
	echjag_jal("echjagjal"), //129
	echjag_cha("echjagcha"), //130
	echjag_bha("echjagbha"), //131
	echjag_pac("echjagpac"), //132
	echjag_jam("echjagjam"), //133
	echjag_par("echjagpar"), //134
	echjag_era("echjagera"), //135
	echjag_dha("echjagdha"), //136
	echjag_bhu("echjagbhu"), //137
	echjag_muk("echjagmuk"), //138
	echjag_ama("echjagama"), //139
	echjag_cho("echjagcho"), //140
	echjag_yav("echjagyav"), //141
	echjag_rav("echjagrav"), //142
	echjag_bod("echjagbod"), //143
	
	echnad_nad("echnadnad"), //144
	echnad_akl("echnadakl"), //145
	echnad_akr("echnadakr"), //146
	echnad_tal("echnadtal"), //147
	echnad_nav("echnadnav"), //148
	echnad_shd("echnadshd"), //149
	
	echdhu_dhu("echdhudhu"), //150
	echdhu_shi("echdhushi"), //151
	echdhu_sak("echdhusak"), //152
	echdhu_shk("echdhushk"), //153
	
	echnas_nas("echnasnas"), //154
	echnas_sur("echnassur"), //155
	echnas_mal("echnasmal"), //156
	echnas_dev("echnasdev"), //157
	echnas_pet("echnaspet"), //158
	echnas_din("echnasdin"), //159
	echnas_cha("echnascha"), //160
	echnas_nan("echnasnan"), //161
	echnas_nip("echnasnip"), //162
	echnas_yev("echnasyev"), //163
	echnas_iga("echnasiga"), //164
	echnas_sin("echnassin"), //165
	echnas_kal("echnaskal"), //166
	echnas_tri("echnastri"), //167

	echbee_bee("echbeebee"), //168
	echbee_dha("echbeedha"), //169
	echbee_amb("echbeeamb"), //170
	echbee_par("echbeepar"), //171
	echbee_kej("echbeekej"), //172
	echbee_ash("echbeeash"), //173
	echbee_gev("echbeegev"), //174
	echbee_maj("echbeemaj"), //175
	echbee_pat("echbeepat"), //176
	echbee_shi("echbeeshi"), //177
	echbee_vad("echbeevad"), //178
	
	echlat_lat("echlatlat"), //179
	echlat_udg("echlatudg"), //180
	echlat_ahm("echlatahm"), //181
	echlat_deo("echlatdeo"), //182
	echlat_shi("echlatshi"), //183
	echlat_aus("echlataus"), //184
	echlat_nil("echlatnil"), //185
	echlat_ren("echlatren"), //186
	echlat_cha("echlatcha"), //187
	echlat_jal("echlatjal"), //188
	
	echpar_par("echparpar"), //189
	echpar_gan("echpargan"), //190
	echpar_son("echparson"), //191
	echpar_pat("echparpat"), //192
	echpar_man("echparman"), //193
	echpar_sel("echparsel"), //194
	echpar_pur("echparpur"), //195
	echpar_pal("echparpal"), //196
	echpar_jin("echparjin"), //197
	
	echaur_aur("echauraur"), //198
	echaur_khu("echaurkhu"), //199
	echaur_soe("echaursoe"), //200
	echaur_sil("echaursil"), //201
	echaur_gan("echaurgan"), //202
	echaur_kan("echaurkan"), //203
	echaur_phu("echaurphu"), //204
	echaur_pai("echaurpai"), //205
	echaur_vai("echaurvai"), //206

	echosm_osm("echosmosm"), //207
	echosm_tul("echosmtul"), //208
	echosm_ome("echosmome"), //209
	echosm_loh("echosmloh"), //210
	echosm_kal("echosmkal"), //211
	echosm_bho("echosmbho"), //212
	echosm_was("echosmwas"), //213
	echosm_par("echosmpar"), //214
	
	echjal_jal("echjaljal"), //215
	echjal_amb("echjalamb"), //216
	echjal_bho("echjalbho"), //217
	echjal_bad("echjalbad"), //218
	echjal_gha("echjalgha"), //219
	echjal_par("echjalpar"), //220
	echjal_man("echjalman"), //221
	echjal_jaf("echjaljaf"), //222
	
	echnan_nan("echnannan"), //223
	echnan_bho("echnanbho"), //224
	echnan_ard("echnanard"), //225
	echnan_bil("echnanbil"), //226
	echnan_deg("echnandeg"), //227
	echnan_dha("echnandha"), //228
	echnan_had("echnanhad"), //229
	echnan_hmy("echnanhmy"), //230
	echnan_kan("echnankan"), //231
	echnan_kin("echnankin"), //232
	echnan_loh("echnanloh"), //233
	echnan_mah("echnanmah"), //234
	echnan_muk("echnanmuk"), //235
	echnan_nai("echnannai"), //236
	echnan_umr("echnanumr"), //237
	
	echhin_hin("echhinhin"), //238
	echhin_aun("echhinaun"), //239
	echhin_sen("echhinsen"), //240
	echhin_kal("echhinkal"), //241
	echhin_bas("echhinbas"), //242
	
	echnag_urb("echnagurb"), //243
	echnag_sav("echnagsav"), //244
	echnag_kal("echnagkal"), //245
	echnag_nar("echnagnar"), //246
	echnag_kat("echnagkat"), //247
	echnag_par("echnagpar"), //248
	echnag_ram("echnagram"), //249
	echnag_hin("echnaghin"), //250
	echnag_mau("echnagmau"), //251
	echnag_kam("echnagkam"), //252
	echnag_umr("echnagumr"), //253
	echnag_bhi("echnagbhi"), //254
	echnag_kuh("echnagkuh"), //255
	
	echcha_cha("echchacha"), //256
	echcha_War("echchaWar"), //257
	echcha_bha("echchabha"), //258
	echcha_chi("echchachi"), //259
	echcha_nag("echchanag"), //260
	echcha_bra("echchabra"), //261
	echcha_sin("echchasin"), //262
	echcha_mul("echchamul"), //263
	echcha_gon("echchagon"), //264
	echcha_pom("echchapom"), //265
	echcha_sao("echchasao"), //266
	echcha_raj("echcharaj"), //267
	echcha_kor("echchakor"), //268
	echcha_jiv("echchajiv"), //269
	echcha_bal("echchabal"), //270
	
	echgad_gad("echgadgad"), //271
	echgad_cha("echgadcha"), //272
	echgad_ahe("echgadahe"), //273
	echgad_arm("echgadarm"), //274
	echgad_sir("echgadsir"), //275
	echgad_eta("echgadeta"), //276
	echgad_kor("echgadkor"), //277
	echgad_kur("echgadkur"), //278
	echgad_dha("echgaddha"), //279
	echgad_wad("echgadwad"), //280
	echgad_bha("echgadbha"), //281
	echgad_mul("echgadmul"), //282
	                            
	echwar_war("echwarwar"), //283
	echwar_arv("echwararv"), //284
	echwar_ash("echwarash"), //285
	echwar_sel("echwarsel"), //286
	echwar_sam("echwarsam"), //287
	echwar_kar("echwarkar"), //288
	echwar_deo("echwardeo"), //289
	echwar_hin("echwarhin"), //290
	
	echgon_gon("echgongon"), //291
	echgon_mor("echgonmor"), //292
	echgon_amg("echgonamg"), //293
	echgon_deo("echgondeo"), //294
	echgon_sad("echgonsad"), //295
	echgon_tir("echgontir"), //296
	echgon_sal("echgonsal"), //297
	echgon_gor("echgongor"), //298
	
	echbha_bha("echbhabha"), //299
	echbha_sako("echbhasako"), //300
	echbha_tum("echbhatum"), //301
	echbha_pau("echbhapau"), //302
	echbha_moh("echbhamoh"), //303
	echbha_lakn("echbhalakn"), //304
	echbha_lakh("echbhalakh"), //305
	
	echamr_amr("echamramr"), //306
	echamr_dha("echamrdha"), //307
	echamr_chi("echamrchi"), //308
	echamr_ach("echamrach"), //309
	echamr_chb("echamrchb"), //310
	echamr_mor("echamrmor"), //311
	echamr_war("echamrwar"), //312
	echamr_anj("echamranj"), //313
	echamr_bha("echamrbha"), //314
	echamr_tio("echamrtio"), //315
	echamr_dar("echamrdar"), //316
	echamr_nan("echamrnan"), //317
	echamr_chr("echamrchr"), //318
	echamr_dhm("echamrdhm"), //319
	
	echyav_yav("echyavyav"), //320
	echyav_bab("echyavbab"), //321
	echyav_ner("echyavner"), //322
	echyav_dar("echyavdar"), //323
	echyav_dig("echyavdig"), //324
	echyav_pus("echyavpus"), //325
	echyav_kal("echyavkal"), //326
	echyav_ral("echyavral"), //327
	echyav_gha("echyavgha"), //328
	echyav_kel("echyavkel"), //329
	echyav_mar("echyavmar"), //330
	echyav_wan("echyavwan"), //331
	echyav_mah("echyavmah"), //332
	echyav_uma("echyavuma"), //333
	echyav_arn("echyavarn"), //334
	echyav_zar("echyavzar"), //335
	
	echbul_kha("echbulkha"), //336
	echbul_chi("echbulchi"), //337
	echbul_san("echbulsan"), //338
	echbul_sin("echbulsin"), //339
	echbul_deu("echbuldeu"), //340
	echbul_bul("echbulbul"), //341
	echbul_meh("echbulmeh"), //342
	echbul_mot("echbulmot"), //343
	echbul_mal("echbulmal"), //344
	echbul_lon("echbullon"), //345
	echbul_jal("echbuljal"), //346
	echbul_she("echbulshe"), //347
	echbul_nan("echbulnan"), //348
	
	echako_akt("echakoakt"), //349
	echako_tel("echakotel"), //350
	echako_pat("echakopat"), //351
	echako_bar("echakobar"), //352
	echako_bal("echakobal"), //353
	echako_mur("echakomur"), //354
	
	echwas_was("echwaswas"), //355
	echwas_kar("echwaskar"), //356
	echwas_mng("echwasmng"), //357
	echwas_mal("echwasmal"), //358
	echwas_ris("echwasris"), //359
	echwas_man("echwasman"),   //360
	; 
	private final String name;
}