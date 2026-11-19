package com.probity.echawdi.utils;

import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;;

@Component
public class DataSourceKeeper {

	public void setDataSourceEchwadi(String echDbName,String echSchemaName) {
		
		switch (echDbName) {
		/** PUNE dEVISION*/
		case "echpun":
			switch (echSchemaName) {
			case "echpun_bar":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_bar);
				break;
			case "echpun_shi":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_shi);
				break;
			case "echpun_amb":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_amb);
				break;
			case "echpun_ind":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_ind);
				break;
			case "echpun_khe":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_khe);
				break;
			case "echpun_jun":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_jun);
				break;
			case "echpun_dau":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_dau);
				break;
			case "echpun_pun":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_pun);
				break;
			case "echpun_pur":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_pur);
				break;
			case "echpun_bho":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_bho);
				break;
			case "echpun_mav":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_mav);
				break;
			case "echpun_mul":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_mul);
				break;
			case "echpun_vel":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_vel);
				break;
			case "echpun_hav":
				DatabaseContextHolder.setCtx(DataSourceType.echpun_hav);
				break;

			default:
				break;
			}
		case "echkol":
			switch (echSchemaName) {
			case "echkol_aja":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_aja);
				break;
			case "echkol_kar":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_kar);
				break;
			case "echkol_kag":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_kag);
				break;
			case "echkol_gag":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_gag);
				break;
			case "echkol_gad":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_gad);
				break;
			case "echkol_cha":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_cha);
				break;
			case "echkol_pan":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_pan);
				break;
			case "echkol_bhu":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_bhu);
				break;
			case "echkol_rad":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_rad);
				break;
			case "echkol_sha":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_sha);
				break;
			case "echkol_shi":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_shi);
				break;
			case "echkol_hat":
				DatabaseContextHolder.setCtx(DataSourceType.echkol_hat);
				break;
			default:
				break;
			}
		case "echsan":
			switch (echSchemaName) {
			case "echsan_atp":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_atp);
				break;
			case "echsan_kad":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_kad);
				break;
			case "echsan_kav":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_kav);
				break;
			case "echsan_kha":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_kha);
				break;
			case "echsan_jat":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_jat);
				break;
			case "echsan_tas":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_tas);
				break;
			case "echsan_pal":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_pal);
				break;
			case "echsan_mir":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_mir);
				break;
			case "echsan_val":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_val);
				break;
			case "echsan_shi":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_shi);
				break;
			case "echsan_wal":
				DatabaseContextHolder.setCtx(DataSourceType.echsan_wal);
				break;
			default:
				break;
			}
		case "echsat":
			switch (echSchemaName) {
			case "echsat_kar":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_kar);
				break;
			case "echsat_kor":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_kor);
				break;
			case "echsat_khn":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_khn);
				break;
			case "echsat_kht":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_kht);
				break;
			case "echsat_jav":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_jav);
				break;
			case "echsat_pat":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_pat);
				break;
			case "echsat_pha":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_pha);
				break;
			case "echsat_mah":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_mah);
				break;
			case "echsat_man":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_man);
				break;
			case "echsat_wai":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_wai);
				break;
			case "echsat_sat":
				DatabaseContextHolder.setCtx(DataSourceType.echsat_sat);
				break;
			default:
				break;
			}
		case "echsol":
			switch (echSchemaName) {
			case "echsol_akk":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_akk);
				break;
			case "echsol_nor":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_nor);
				break;
			case "echsol_kar":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_kar);
				break;
			case "echsol_sou":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_sou);
				break;
			case "echsol_pan":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_pan);
				break;
			case "echsol_bar":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_bar);
				break;
			case "echsol_man":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_man);
				break;
			case "echsol_mad":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_mad);
				break;
			case "echsol_mal":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_mal);
				break;
			case "echsol_moh":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_moh);
				break;
			case "echsol_san":
				DatabaseContextHolder.setCtx(DataSourceType.echsol_san);
				break;
			default:
				break;
			}

			///////////////////NASHIK DEVISION////////////		

		case "echahm":
			switch (echSchemaName) {
			case "echahm_nag":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_nag);
				break;
			case "echahm_ako":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_ako);
				break;
			case "echahm_kar":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_kar);
				break;
			case "echahm_kop":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_kop);
				break;
			case "echahm_jam":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_jam);
				break;
			case "echahm_nev":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_nev);
				break;
			case "echahm_pat":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_pat);
				break;
			case "echahm_par":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_par);
				break;
			case "echahm_rat":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_rat);
				break;
			case "echahm_rah":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_rah);
				break;
			case "echahm_she":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_she);
				break;
			case "echahm_shg":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_shg);
				break;
			case "echahm_shr":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_shr);
				break;
			case "echahm_san":
				DatabaseContextHolder.setCtx(DataSourceType.echahm_san);
				break;
			default:
				break;
			}
		case "echjag":
			switch (echSchemaName) {
			case "echjag_jal":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_jal);
				break;
			case "echjag_cha":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_cha);
				break;
			case "echjag_bha":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_bha);
				break;
			case "echjag_pac":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_pac);
				break;
			case "echjag_jam":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_jam);
				break;
			case "echjag_par":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_par);
				break;
			case "echjag_era":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_era);
				break;
			case "echjag_dha":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_dha);
				break;
			case "echjag_bhu":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_bhu);
				break;
			case "echjag_muk":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_muk);
				break;
			case "echjag_ama":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_ama);
				break;
			case "echjag_cho":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_cho);
				break;
			case "echjag_yav":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_yav);
				break;
			case "echjag_rav":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_rav);
				break;
			case "echjag_bod":
				DatabaseContextHolder.setCtx(DataSourceType.echjag_bod);
				break;
			default:
				break;
			}
		case "echnad":
			switch (echSchemaName) {
			case "echnad_nad":
				DatabaseContextHolder.setCtx(DataSourceType.echnad_nad);
				break;
			case "echnad_akl":
				DatabaseContextHolder.setCtx(DataSourceType.echnad_akl);
				break;
			case "echnad_akr":
				DatabaseContextHolder.setCtx(DataSourceType.echnad_akr);
				break;
			case "echnad_tal":
				DatabaseContextHolder.setCtx(DataSourceType.echnad_tal);
				break;
			case "echnad_nav":
				DatabaseContextHolder.setCtx(DataSourceType.echnad_nav);
				break;
			case "echnad_shd":
				DatabaseContextHolder.setCtx(DataSourceType.echnad_shd);
				break;
			default:
				break;
			}
		case "echdhu":
			switch (echSchemaName) {
			case "echdhu_dhu":
				DatabaseContextHolder.setCtx(DataSourceType.echdhu_dhu);
				break;
			case "echdhu_shi":
				DatabaseContextHolder.setCtx(DataSourceType.echdhu_shi);
				break;
			case "echdhu_sak":
				DatabaseContextHolder.setCtx(DataSourceType.echdhu_sak);
				break;
			case "echdhu_shk":
				DatabaseContextHolder.setCtx(DataSourceType.echdhu_shk);
				break;
			default:
				break;
			}
		case "echnas":
			switch (echSchemaName) {
			case "echnas_bag":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_bag);
				break;
			case "echnas_nas":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_nas);
				break;
			case "echnas_sur":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_sur);
				break;
			case "echnas_mal":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_mal);
				break;
			case "echnas_dev":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_dev);
				break;
			case "echnas_pet":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_pet);
				break;
			case "echnas_din":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_din);
				break;
			case "echnas_cha":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_cha);
				break;
			case "echnas_nan":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_nan);
				break;
			case "echnas_nip":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_nip);
				break;
			case "echnas_yev":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_yev);
				break;
			case "echnas_iga":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_iga);
				break;
			case "echnas_sin":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_sin);
				break;
			case "echnas_kal":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_kal);
				break;
			case "echnas_tri":
				DatabaseContextHolder.setCtx(DataSourceType.echnas_tri);
				break;
			default:
				break;
			}

			/////////////NAGPUR DIVISION//////////////////////////			
		case "echnag":
			switch (echSchemaName) {
			case "echnag_rul":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_rul);
				break;
			case "echnag_urb":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_urb);
				break;
			case "echnag_sav":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_sav);
				break;
			case "echnag_kal":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_kal);
				break;
			case "echnag_nar":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_nar);
				break;
			case "echnag_kat":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_kat);
				break;
			case "echnag_par":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_par);
				break;
			case "echnag_ram":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_ram);
				break;
			case "echnag_hin":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_hin);
				break;
			case "echnag_mau":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_mau);
				break;
			case "echnag_kam":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_kam);
				break;
			case "echnag_umr":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_umr);
				break;
			case "echnag_bhi":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_bhi);
				break;
			case "echnag_kuh":
				DatabaseContextHolder.setCtx(DataSourceType.echnag_kuh);
				break;
			default:
				break;
			}
		case "echcha":
			switch (echSchemaName) {
			case "echcha_cha":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_cha);
				break;
			case "echcha_War":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_War);
				break;
			case "echcha_bha":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_bha);
				break;
			case "echcha_chi":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_chi);
				break;
			case "echcha_nag":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_nag);
				break;
			case "echcha_bra":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_bra);
				break;
			case "echcha_sin":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_sin);
				break;
			case "echcha_mul":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_mul);
				break;
			case "echcha_gon":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_gon);
				break;
			case "echcha_pom":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_pom);
				break;
			case "echcha_sao":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_sao);
				break;
			case "echcha_raj":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_raj);
				break;
			case "echcha_kor":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_kor);
				break;
			case "echcha_jiv":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_jiv);
				break;
			case "echcha_bal":
				DatabaseContextHolder.setCtx(DataSourceType.echcha_bal);
				break;
			default:
				break;
			}
		case "echgad":
			switch (echSchemaName) {
			case "echgad_gad":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_gad);
				break;
			case "echgad_cha":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_cha);
				break;
			case "echgad_ahe":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_ahe);
				break;
			case "echgad_arm":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_arm);
				break;
			case "echgad_sir":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_sir);
				break;
			case "echgad_eta":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_eta);
				break;
			case "echgad_kor":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_kor);
				break;
			case "echgad_kur":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_kur);
				break;
			case "echgad_dha":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_dha);
				break;
			case "echgad_wad":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_wad);
				break;
			case "echgad_bha":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_bha);
				break;
			case "echgad_mul":
				DatabaseContextHolder.setCtx(DataSourceType.echgad_mul);
				break;
			default:
				break;
			}
		case "echwar":
			switch (echSchemaName) {
			case "echwar_war":
				DatabaseContextHolder.setCtx(DataSourceType.echwar_war);
				break;
			case "echwar_arv":
				DatabaseContextHolder.setCtx(DataSourceType.echwar_arv);
				break;
			case "echwar_ash":
				DatabaseContextHolder.setCtx(DataSourceType.echwar_ash);
				break;
			case "echwar_sel":
				DatabaseContextHolder.setCtx(DataSourceType.echwar_sel);
				break;
			case "echwar_sam":
				DatabaseContextHolder.setCtx(DataSourceType.echwar_sam);
				break;
			case "echwar_kar":
				DatabaseContextHolder.setCtx(DataSourceType.echwar_kar);
				break;
			case "echwar_deo":
				DatabaseContextHolder.setCtx(DataSourceType.echwar_deo);
				break;
			case "echwar_hin":
				DatabaseContextHolder.setCtx(DataSourceType.echwar_hin);
				break;
			default:
				break;
			}
		case "echgon":
			switch (echSchemaName) {
			case "echgon_gon":
				DatabaseContextHolder.setCtx(DataSourceType.echgon_gon);
				break;
			case "echgon_mor":
				DatabaseContextHolder.setCtx(DataSourceType.echgon_mor);
				break;
			case "echgon_amg":
				DatabaseContextHolder.setCtx(DataSourceType.echgon_amg);
				break;
			case "echgon_deo":
				DatabaseContextHolder.setCtx(DataSourceType.echgon_deo);
				break;
			case "echgon_sad":
				DatabaseContextHolder.setCtx(DataSourceType.echgon_sad);
				break;
			case "echgon_tir":
				DatabaseContextHolder.setCtx(DataSourceType.echgon_tir);
				break;
			case "echgon_sal":
				DatabaseContextHolder.setCtx(DataSourceType.echgon_sal);
				break;
			case "echgon_gor":
				DatabaseContextHolder.setCtx(DataSourceType.echgon_gor);
				break;
			default:
				break;
			}
		case "echbha":
			switch (echSchemaName) {
			case "echbha_bha":
				DatabaseContextHolder.setCtx(DataSourceType.echbha_bha);
				break;
			case "echbha_sako":
				DatabaseContextHolder.setCtx(DataSourceType.echbha_sako);
				break;
			case "echbha_tum":
				DatabaseContextHolder.setCtx(DataSourceType.echbha_tum);
				break;
			case "echbha_pau":
				DatabaseContextHolder.setCtx(DataSourceType.echbha_pau);
				break;
			case "echbha_moh":
				DatabaseContextHolder.setCtx(DataSourceType.echbha_moh);
				break;
			case "echbha_lakn":
				DatabaseContextHolder.setCtx(DataSourceType.echbha_lakn);
				break;
			case "echbha_lakh":
				DatabaseContextHolder.setCtx(DataSourceType.echbha_lakh);
				break;
			default:
				break;
			}
		case "echtha":
			switch (echSchemaName) {
			case "echtha_tha":
				DatabaseContextHolder.setCtx(DataSourceType.echtha_tha);
				break;
			case "echtha_kal":
				DatabaseContextHolder.setCtx(DataSourceType.echtha_kal);
				break;
			case "echtha_amb":
				DatabaseContextHolder.setCtx(DataSourceType.echtha_amb);
				break;
			case "echtha_bhi":
				DatabaseContextHolder.setCtx(DataSourceType.echtha_bhi);
				break;
			case "echtha_sha":
				DatabaseContextHolder.setCtx(DataSourceType.echtha_sha);
				break;
			case "echtha_ulh":
				DatabaseContextHolder.setCtx(DataSourceType.echtha_ulh);
				break;
			case "echtha_mur":
				DatabaseContextHolder.setCtx(DataSourceType.echtha_mur);
				break;
			default:
				break;
			}


		case "echmsd":
			switch (echSchemaName) {
			case "echmsd_and":
				DatabaseContextHolder.setCtx(DataSourceType.echmsd_and);
				break;
			case "echmsd_bor":
				DatabaseContextHolder.setCtx(DataSourceType.echmsd_bor);
				break;
			case "echmsd_kur":
				DatabaseContextHolder.setCtx(DataSourceType.echmsd_kur);
				break;
			default:
				break;
			}

		case "echrai":
			switch (echSchemaName) {
			case "echrai_tal":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_tal);
				break;
			case "echrai_ali":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_ali);
				break;
			case "echrai_pan":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_pan);
				break;
			case "echrai_pen":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_pen);
				break;
			case "echrai_kar":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_kar);
				break;
			case "echrai_kha":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_kha);
				break;
			case "echrai_ura":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_ura);
				break;
			case "echrai_sud":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_sud);
				break;
			case "echrai_man":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_man);
				break;
			case "echrai_roh":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_roh);
				break;
			case "echrai_mur":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_mur);
				break;
			case "echrai_shr":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_shr);
				break;
			case "echrai_mha":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_mha);
				break;
			case "echrai_mah":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_mah);
				break;
			case "echrai_pol":
				DatabaseContextHolder.setCtx(DataSourceType.echrai_pol);
				break;
			default:
				break;
			}


		case "echrat":
			switch (echSchemaName) {
			case "echrat_rat":
				DatabaseContextHolder.setCtx(DataSourceType.echrat_rat);
				break;
			case "echrat_man":
				DatabaseContextHolder.setCtx(DataSourceType.echrat_man);
				break;
			case "echrat_dap":
				DatabaseContextHolder.setCtx(DataSourceType.echrat_dap);
				break;
			case "echrat_ked":
				DatabaseContextHolder.setCtx(DataSourceType.echrat_ked);
				break;
			case "echrat_chp":
				DatabaseContextHolder.setCtx(DataSourceType.echrat_chp);
				break;
			case "echrat_guh":
				DatabaseContextHolder.setCtx(DataSourceType.echrat_guh);
				break;
			case "echrat_sag":
				DatabaseContextHolder.setCtx(DataSourceType.echrat_sag);
				break;
			case "echrat_lan":
				DatabaseContextHolder.setCtx(DataSourceType.echrat_lan);
				break;
			case "echrat_raj":
				DatabaseContextHolder.setCtx(DataSourceType.echrat_raj);
				break;
			default:
				break;
			}



		case "echsin":
			switch (echSchemaName) {
			case "echsin_saw":
				DatabaseContextHolder.setCtx(DataSourceType.echsin_saw);
				break;
			case "echsin_kan":
				DatabaseContextHolder.setCtx(DataSourceType.echsin_kan);
				break;
			case "echsin_kud":
				DatabaseContextHolder.setCtx(DataSourceType.echsin_kud);
				break;
			case "echsin_dev":
				DatabaseContextHolder.setCtx(DataSourceType.echsin_dev);
				break;
			case "echsin_dod":
				DatabaseContextHolder.setCtx(DataSourceType.echsin_dod);
				break;
			case "echsin_mal":
				DatabaseContextHolder.setCtx(DataSourceType.echsin_mal);
				break;
			case "echsin_ven":
				DatabaseContextHolder.setCtx(DataSourceType.echsin_ven);
				break;
			case "echsin_vai":
				DatabaseContextHolder.setCtx(DataSourceType.echsin_vai);
				break;
			default:
				break;
			}


		case "echpal":
			switch (echSchemaName) {
			case "echpal_pal":
				DatabaseContextHolder.setCtx(DataSourceType.echpal_pal);
				break;
			case "echpal_vas":
				DatabaseContextHolder.setCtx(DataSourceType.echpal_vas);
				break;
			case "echpal_wad":
				DatabaseContextHolder.setCtx(DataSourceType.echpal_wad);
				break;
			case "echpal_jaw":
				DatabaseContextHolder.setCtx(DataSourceType.echpal_jaw);
				break;
			case "echpal_mok":
				DatabaseContextHolder.setCtx(DataSourceType.echpal_mok);
				break;
			case "echpal_dah":
				DatabaseContextHolder.setCtx(DataSourceType.echpal_dah);
				break;
			case "echpal_tal":
				DatabaseContextHolder.setCtx(DataSourceType.echpal_tal);
				break;
			case "echpal_vik":
				DatabaseContextHolder.setCtx(DataSourceType.echpal_vik);
				break;
			default:
				break;
			}



		case "echbee":
			switch (echSchemaName) {
			case "echbee_bee":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_bee);
				break;
			case "echbee_dha":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_dha);
				break;
			case "echbee_amb":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_amb);
				break;
			case "echbee_par":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_par);
				break;
			case "echbee_kej":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_kej);
				break;
			case "echbee_ash":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_ash);
				break;
			case "echbee_gev":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_gev);
				break;
			case "echpal_vik":
				DatabaseContextHolder.setCtx(DataSourceType.echpal_vik);
				break;
			case "echbee_maj":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_maj);
				break;
			case "echbee_pat":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_pat);
				break;
			case "echbee_shi":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_shi);
				break;
			case "echbee_vad":
				DatabaseContextHolder.setCtx(DataSourceType.echbee_vad);
				break;
			default:
				break;
			}



		case "echlat":
			switch (echSchemaName) {
			case "echlat_lat":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_lat);
				break;
			case "echlat_udg":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_udg);
				break;
			case "echlat_ahm":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_ahm);
				break;
			case "echlat_deo":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_deo);
				break;
			case "echlat_shi":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_shi);
				break;
			case "echlat_aus":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_aus);
				break;
			case "echlat_nil":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_nil);
				break;
			case "echlat_ren":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_ren);
				break;
			case "echlat_cha":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_cha);
				break;
			case "echlat_jal":
				DatabaseContextHolder.setCtx(DataSourceType.echlat_jal);
				break;
			default:
				break;
			}



		case "echpar":
			switch (echSchemaName) {
			case "echpar_par":
				DatabaseContextHolder.setCtx(DataSourceType.echpar_par);
				break;
			case "echpar_gan":
				DatabaseContextHolder.setCtx(DataSourceType.echpar_gan);
				break;
			case "echpar_son":
				DatabaseContextHolder.setCtx(DataSourceType.echpar_son);
				break;
			case "echpar_pat":
				DatabaseContextHolder.setCtx(DataSourceType.echpar_pat);
				break;
			case "echpar_man":
				DatabaseContextHolder.setCtx(DataSourceType.echpar_man);
				break;
			case "echpar_sel":
				DatabaseContextHolder.setCtx(DataSourceType.echpar_sel);
				break;
			case "echpar_pur":
				DatabaseContextHolder.setCtx(DataSourceType.echpar_pur);
				break;
			case "echpar_pal":
				DatabaseContextHolder.setCtx(DataSourceType.echpar_pal);
				break;
			case "echpar_jin":
				DatabaseContextHolder.setCtx(DataSourceType.echpar_jin);
				break;
			default:
				break;
			}



		case "echaur":
			switch (echSchemaName) {
			case "echaur_aur":
				DatabaseContextHolder.setCtx(DataSourceType.echaur_aur);
				break;
			case "echaur_khu":
				DatabaseContextHolder.setCtx(DataSourceType.echaur_khu);
				break;
			case "echaur_soe":
				DatabaseContextHolder.setCtx(DataSourceType.echaur_soe);
				break;
			case "echaur_sil":
				DatabaseContextHolder.setCtx(DataSourceType.echaur_sil);
				break;
			case "echaur_gan":
				DatabaseContextHolder.setCtx(DataSourceType.echaur_gan);
				break;
			case "echaur_kan":
				DatabaseContextHolder.setCtx(DataSourceType.echaur_kan);
				break;
			case "echaur_phu":
				DatabaseContextHolder.setCtx(DataSourceType.echaur_phu);
				break;
			case "echaur_pai":
				DatabaseContextHolder.setCtx(DataSourceType.echaur_pai);
				break;
			case "echaur_vai":
				DatabaseContextHolder.setCtx(DataSourceType.echaur_vai);
				break;
			default:
				break;
			}



		case "echosm":
			switch (echSchemaName) {
			case "echosm_osm":
				DatabaseContextHolder.setCtx(DataSourceType.echosm_osm);
				break;
			case "echosm_tul":
				DatabaseContextHolder.setCtx(DataSourceType.echosm_tul);
				break;
			case "echosm_ome":
				DatabaseContextHolder.setCtx(DataSourceType.echosm_ome);
				break;
			case "echosm_loh":
				DatabaseContextHolder.setCtx(DataSourceType.echosm_loh);
				break;
			case "echosm_kal":
				DatabaseContextHolder.setCtx(DataSourceType.echosm_kal);
				break;
			case "echosm_bho":
				DatabaseContextHolder.setCtx(DataSourceType.echosm_bho);
				break;
			case "echosm_was":
				DatabaseContextHolder.setCtx(DataSourceType.echosm_was);
				break;
			case "echosm_par":
				DatabaseContextHolder.setCtx(DataSourceType.echosm_par);
				break;
			default:
				break;
			}


		case "echjal":
			switch (echSchemaName) {
			case "echjal_jal":
				DatabaseContextHolder.setCtx(DataSourceType.echjal_jal);
				break;
			case "echjal_amb":
				DatabaseContextHolder.setCtx(DataSourceType.echjal_amb);
				break;
			case "echjal_bho":
				DatabaseContextHolder.setCtx(DataSourceType.echjal_bho);
				break;
			case "echjal_bad":
				DatabaseContextHolder.setCtx(DataSourceType.echjal_bad);
				break;
			case "echjal_gha":
				DatabaseContextHolder.setCtx(DataSourceType.echjal_gha);
				break;
			case "echjal_par":
				DatabaseContextHolder.setCtx(DataSourceType.echjal_par);
				break;
			case "echjal_man":
				DatabaseContextHolder.setCtx(DataSourceType.echjal_man);
				break;
			case "echjal_jaf":
				DatabaseContextHolder.setCtx(DataSourceType.echjal_jaf);
				break;
			default:
				break;
			}


		case "echnan":
			switch (echSchemaName) {
			case "echnan_mud":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_mud);
				break;
			case "echnan_nan":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_nan);
				break;
			case "echnan_bho":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_bho);
				break;
			case "echnan_ard":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_ard);
				break;
			case "echnan_bil":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_bil);
				break;
			case "echnan_deg":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_deg);
				break;
			case "echnan_dha":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_dha);
				break;
			case "echnan_had":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_had);
				break;
			case "echnan_hmy":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_hmy);
				break;
			case "echnan_kan":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_kan);
				break;
			case "echnan_kin":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_kin);
				break;
			case "echnan_loh":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_loh);
				break;
			case "echnan_mah":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_mah);
				break;
			case "echnan_muk":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_muk);
				break;
			case "echnan_nai":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_nai);
				break;
			case "echnan_umr":
				DatabaseContextHolder.setCtx(DataSourceType.echnan_umr);
				break;
			default:
				break;
			}


		case "echhin":
			switch (echSchemaName) {
			case "echhin_hin":
				DatabaseContextHolder.setCtx(DataSourceType.echhin_hin);
				break;
			case "echhin_aun":
				DatabaseContextHolder.setCtx(DataSourceType.echhin_aun);
				break;
			case "echhin_sen":
				DatabaseContextHolder.setCtx(DataSourceType.echhin_sen);
				break;
			case "echhin_kal":
				DatabaseContextHolder.setCtx(DataSourceType.echhin_kal);
				break;
			case "echhin_bas":
				DatabaseContextHolder.setCtx(DataSourceType.echhin_bas);
				break;
			default:
				break;
			}



		case "echamr":
			switch (echSchemaName) {
			case "echamr_amr":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_amr);
				break;
			case "echamr_dha":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_dha);
				break;
			case "echamr_chi":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_chi);
				break;
			case "echamr_ach":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_ach);
				break;
			case "echamr_chb":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_chb);
				break;
			case "echamr_mor":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_mor);
				break;
			case "echamr_war":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_war);
				break;
			case "echamr_anj":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_anj);
				break;
			case "echamr_bha":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_bha);
				break;
			case "echamr_tio":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_tio);
				break;
			case "echamr_dar":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_dar);
				break;
			case "echamr_nan":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_nan);
				break;
			case "echamr_chr":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_chr);
				break;
			case "echamr_dhm":
				DatabaseContextHolder.setCtx(DataSourceType.echamr_dhm);
				break;
			default:
				break;
			}



		case "echyav":
			switch (echSchemaName) {
			case "echyav_yav":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_yav);
				break;
			case "echyav_bab":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_bab);
				break;
			case "echyav_ner":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_ner);
				break;
			case "echyav_dar":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_dar);
				break;
			case "echyav_dig":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_dig);
				break;
			case "echyav_pus":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_pus);
				break;
			case "echyav_kal":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_kal);
				break;
			case "echyav_ral":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_ral);
				break;
			case "echyav_gha":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_gha);
				break;
			case "echyav_kel":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_kel);
				break;
			case "echyav_mar":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_mar);
				break;
			case "echyav_wan":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_wan);
				break;
			case "echyav_mah":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_mah);
				break;
			case "echyav_arn":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_arn);
				break;
			case "echyav_zar":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_zar);
				break;
			case "echyav_uma":
				DatabaseContextHolder.setCtx(DataSourceType.echyav_uma);
				break;
			default:
				break;
			}



		case "echbul":
			switch (echSchemaName) {
			case "echbul_kha":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_kha);
				break;
			case "echbul_chi":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_chi);
				break;
			case "echbul_san":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_san);
				break;
			case "echbul_sin":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_sin);
				break;
			case "echbul_deu":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_deu);
				break;
			case "echbul_bul":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_bul);
				break;
			case "echbul_meh":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_meh);
				break;
			case "echbul_mot":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_mot);
				break;
			case "echbul_mal":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_mal);
				break;
			case "echbul_lon":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_lon);
				break;
			case "echbul_jal":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_jal);
				break;
			case "echbul_she":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_she);
				break;
			case "echbul_nan":
				DatabaseContextHolder.setCtx(DataSourceType.echbul_nan);
				break;
			default:
				break;
			}


		case "echako":
			switch (echSchemaName) {
			case "echako_ako":
				DatabaseContextHolder.setCtx(DataSourceType.echako_ako);
				break;
			case "echako_akt":
				DatabaseContextHolder.setCtx(DataSourceType.echako_akt);
				break;
			case "echako_tel":
				DatabaseContextHolder.setCtx(DataSourceType.echako_tel);
				break;
			case "echako_pat":
				DatabaseContextHolder.setCtx(DataSourceType.echako_pat);
				break;
			case "echako_bar":
				DatabaseContextHolder.setCtx(DataSourceType.echako_bar);
				break;
			case "echako_bal":
				DatabaseContextHolder.setCtx(DataSourceType.echako_bal);
				break;
			case "echako_mur":
				DatabaseContextHolder.setCtx(DataSourceType.echako_mur);
				break;
			default:
				break;
			}

		case "echwas":
			switch (echSchemaName) {
			case "echwas_was":
				DatabaseContextHolder.setCtx(DataSourceType.echwas_was);
				break;
			case "echwas_kar":
				DatabaseContextHolder.setCtx(DataSourceType.echwas_kar);
				break;
			case "echwas_mng":
				DatabaseContextHolder.setCtx(DataSourceType.echwas_mng);
				break;
			case "echwas_mal":
				DatabaseContextHolder.setCtx(DataSourceType.echwas_mal);
				break;
			case "echwas_ris":
				DatabaseContextHolder.setCtx(DataSourceType.echwas_ris);
				break;
			case "echwas_man":
				DatabaseContextHolder.setCtx(DataSourceType.echwas_man);
			}
		default:
			break;
		}
	}
}

//package com.probity.echawdi.utils;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Component;
//
//import com.probity.echawdi.config.DatabaseContextHolder;;
//
//@Component
//public class DataSourceKeeper {
//
//	public void setDataSourceEchwadi(HttpServletRequest request) {
//
//		switch (request.getHeader("echDbName")) {
//		/** PUNE dEVISION*/
//		case "echpun":
//			switch (request.getHeader("echSchemaName")) {
//			case "echpun_bar":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_bar);
//				break;
//			case "echpun_shi":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_shi);
//				break;
//			case "echpun_amb":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_amb);
//				break;
//			case "echpun_ind":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_ind);
//				break;
//			case "echpun_khe":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_khe);
//				break;
//			case "echpun_jun":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_jun);
//				break;
//			case "echpun_dau":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_dau);
//				break;
//			case "echpun_pun":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_pun);
//				break;
//			case "echpun_pur":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_pur);
//				break;
//			case "echpun_bho":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_bho);
//				break;
//			case "echpun_mav":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_mav);
//				break;
//			case "echpun_mul":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_mul);
//				break;
//			case "echpun_vel":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_vel);
//				break;
//			case "echpun_hav":
//				DatabaseContextHolder.setCtx(DataSourceType.echpun_hav);
//				break;
//
//			default:
//				break;
//			}
//		case "echkol":
//			switch (request.getHeader("echSchemaName")) {
//			case "echkol_aaj":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_aaj);
//				break;
//			case "echkol_kar":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_kar);
//				break;
//			case "echkol_kal":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_kal);
//				break;
//			case "echkol_gan":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_gan);
//				break;
//			case "echkol_gad":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_gad);
//				break;
//			case "echkol_cha":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_cha);
//				break;
//			case "echkol_pan":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_pan);
//				break;
//			case "echkol_bhu":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_bhu);
//				break;
//			case "echkol_rad":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_rad);
//				break;
//			case "echkol_sha":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_sha);
//				break;
//			case "echkol_shi":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_shi);
//				break;
//			case "echkol_hat":
//				DatabaseContextHolder.setCtx(DataSourceType.echkol_hat);
//				break;
//			default:
//				break;
//			}
//		case "echsan":
//			switch (request.getHeader("echSchemaName")) {
//			case "echsan_aat":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_aat);
//				break;
//			case "echsan_kad":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_kad);
//				break;
//			case "echsan_kav":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_kav);
//				break;
//			case "echsan_kha":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_kha);
//				break;
//			case "echsan_jat":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_jat);
//				break;
//			case "echsan_tas":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_tas);
//				break;
//			case "echsan_pal":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_pal);
//				break;
//			case "echsan_mir":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_mir);
//				break;
//			case "echsan_val":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_val);
//				break;
//			case "echsan_shi":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_shi);
//				break;
//			case "echsan_wal":
//				DatabaseContextHolder.setCtx(DataSourceType.echsan_wal);
//				break;
//			default:
//				break;
//			}
//		case "echsat":
//			switch (request.getHeader("echSchemaName")) {
//			case "echsat_kar":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_kar);
//				break;
//			case "echsat_kor":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_kor);
//				break;
//			case "echsat_khn":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_khn);
//				break;
//			case "echsat_kha":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_kha);
//				break;
//			case "echsat_jav":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_jav);
//				break;
//			case "echsat_pat":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_pat);
//				break;
//			case "echsat_fal":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_fal);
//				break;
//			case "echsat_mah":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_mah);
//				break;
//			case "echsat_man":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_man);
//				break;
//			case "echsat_vai":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_vai);
//				break;
//			case "echsat_sat":
//				DatabaseContextHolder.setCtx(DataSourceType.echsat_sat);
//				break;
//			default:
//				break;
//			}
//		case "echsol":
//			switch (request.getHeader("echSchemaName")) {
//			case "echsol_akk":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_akk);
//				break;
//			case "echsol_uat":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_uat);
//				break;
//			case "echsol_kar":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_kar);
//				break;
//			case "echsol_dak":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_dak);
//				break;
//			case "echsol_pan":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_pan);
//				break;
//			case "echsol_bar":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_bar);
//				break;
//			case "echsol_man":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_man);
//				break;
//			case "echsol_mad":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_mad);
//				break;
//			case "echsol_mal":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_mal);
//				break;
//			case "echsol_moh":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_moh);
//				break;
//			case "echsol_san":
//				DatabaseContextHolder.setCtx(DataSourceType.echsol_san);
//				break;
//			default:
//				break;
//			}
//
//			///////////////////NASHIK DEVISION////////////		
//
//		case "echahm":
//			switch (request.getHeader("echSchemaName")) {
//			case "echahm_nag":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_nag);
//				break;
//			case "echahm_ako":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_ako);
//				break;
//			case "echahm_kar":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_kar);
//				break;
//			case "echahm_kop":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_kop);
//				break;
//			case "echahm_jam":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_jam);
//				break;
//			case "echahm_nev":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_nev);
//				break;
//			case "echahm_pat":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_pat);
//				break;
//			case "echahm_par":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_par);
//				break;
//			case "echahm_rat":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_rat);
//				break;
//			case "echahm_rah":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_rah);
//				break;
//			case "echahm_she":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_she);
//				break;
//			case "echahm_shg":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_shg);
//				break;
//			case "echahm_shr":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_shr);
//				break;
//			case "echahm_san":
//				DatabaseContextHolder.setCtx(DataSourceType.echahm_san);
//				break;
//			default:
//				break;
//			}
//		case "echjag":
//			switch (request.getHeader("echSchemaName")) {
//			case "echjag_jal":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_jal);
//				break;
//			case "echjag_cha":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_cha);
//				break;
//			case "echjag_bha":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_bha);
//				break;
//			case "echjag_pac":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_pac);
//				break;
//			case "echjag_jam":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_jam);
//				break;
//			case "echjag_par":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_par);
//				break;
//			case "echjag_era":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_era);
//				break;
//			case "echjag_dha":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_dha);
//				break;
//			case "echjag_bhu":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_bhu);
//				break;
//			case "echjag_muk":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_muk);
//				break;
//			case "echjag_ama":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_ama);
//				break;
//			case "echjag_cho":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_cho);
//				break;
//			case "echjag_yav":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_yav);
//				break;
//			case "echjag_rav":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_rav);
//				break;
//			case "echjag_bod":
//				DatabaseContextHolder.setCtx(DataSourceType.echjag_bod);
//				break;
//			default:
//				break;
//			}
//		case "echnad":
//			switch (request.getHeader("echSchemaName")) {
//			case "echnad_nad":
//				DatabaseContextHolder.setCtx(DataSourceType.echnad_nad);
//				break;
//			case "echnad_akl":
//				DatabaseContextHolder.setCtx(DataSourceType.echnad_akl);
//				break;
//			case "echnad_akr":
//				DatabaseContextHolder.setCtx(DataSourceType.echnad_akr);
//				break;
//			case "echnad_tal":
//				DatabaseContextHolder.setCtx(DataSourceType.echnad_tal);
//				break;
//			case "echnad_nav":
//				DatabaseContextHolder.setCtx(DataSourceType.echnad_nav);
//				break;
//			case "echnad_shd":
//				DatabaseContextHolder.setCtx(DataSourceType.echnad_shd);
//				break;
//			default:
//				break;
//			}
//		case "echdhu":
//			switch (request.getHeader("echSchemaName")) {
//			case "echdhu_dhu":
//				DatabaseContextHolder.setCtx(DataSourceType.echdhu_dhu);
//				break;
//			case "echdhu_shi":
//				DatabaseContextHolder.setCtx(DataSourceType.echdhu_shi);
//				break;
//			case "echdhu_sak":
//				DatabaseContextHolder.setCtx(DataSourceType.echdhu_sak);
//				break;
//			case "echdhu_shk":
//				DatabaseContextHolder.setCtx(DataSourceType.echdhu_shk);
//				break;
//			default:
//				break;
//			}
//		case "echnas":
//			switch (request.getHeader("echSchemaName")) {
//			case "echnas_bag":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_bag);
//				break;
//			case "echnas_nas":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_nas);
//				break;
//			case "echnas_sur":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_sur);
//				break;
//			case "echnas_mal":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_mal);
//				break;
//			case "echnas_dev":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_dev);
//				break;
//			case "echnas_pet":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_pet);
//				break;
//			case "echnas_din":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_din);
//				break;
//			case "echnas_cha":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_cha);
//				break;
//			case "echnas_nan":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_nan);
//				break;
//			case "echnas_nip":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_nip);
//				break;
//			case "echnas_yev":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_yev);
//				break;
//			case "echnas_iga":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_iga);
//				break;
//			case "echnas_sin":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_sin);
//				break;
//			case "echnas_kal":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_kal);
//				break;
//			case "echnas_tri":
//				DatabaseContextHolder.setCtx(DataSourceType.echnas_tri);
//				break;
//			default:
//				break;
//			}
//
//			/////////////NAGPUR DIVISION//////////////////////////			
//		case "echnag":
//			switch (request.getHeader("echSchemaName")) {
//			case "echnag_rul":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_rul);
//				break;
//			case "echnag_urb":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_urb);
//				break;
//			case "echnag_sav":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_sav);
//				break;
//			case "echnag_kal":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_kal);
//				break;
//			case "echnag_nar":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_nar);
//				break;
//			case "echnag_kat":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_kat);
//				break;
//			case "echnag_par":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_par);
//				break;
//			case "echnag_ram":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_ram);
//				break;
//			case "echnag_hin":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_hin);
//				break;
//			case "echnag_mau":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_mau);
//				break;
//			case "echnag_kam":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_kam);
//				break;
//			case "echnag_umr":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_umr);
//				break;
//			case "echnag_bhi":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_bhi);
//				break;
//			case "echnag_kuh":
//				DatabaseContextHolder.setCtx(DataSourceType.echnag_kuh);
//				break;
//			default:
//				break;
//			}
//		case "echcha":
//			switch (request.getHeader("echSchemaName")) {
//			case "echcha_cha":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_cha);
//				break;
//			case "echcha_War":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_War);
//				break;
//			case "echcha_bha":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_bha);
//				break;
//			case "echcha_chi":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_chi);
//				break;
//			case "echcha_nag":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_nag);
//				break;
//			case "echcha_bra":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_bra);
//				break;
//			case "echcha_sin":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_sin);
//				break;
//			case "echcha_mul":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_mul);
//				break;
//			case "echcha_gon":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_gon);
//				break;
//			case "echcha_pom":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_pom);
//				break;
//			case "echcha_sao":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_pom);
//				break;
//			case "echcha_raj":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_raj);
//				break;
//			case "echcha_kor":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_kor);
//				break;
//			case "echcha_jiv":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_jiv);
//				break;
//			case "echcha_bal":
//				DatabaseContextHolder.setCtx(DataSourceType.echcha_bal);
//				break;
//			default:
//				break;
//			}
//		case "echgad":
//			switch (request.getHeader("echSchemaName")) {
//			case "echgad_gad":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_gad);
//				break;
//			case "echgad_cha":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_cha);
//				break;
//			case "echgad_ahe":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_ahe);
//				break;
//			case "echgad_arm":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_arm);
//				break;
//			case "echgad_sir":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_sir);
//				break;
//			case "echgad_eta":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_eta);
//				break;
//			case "echgad_kor":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_kor);
//				break;
//			case "echgad_kur":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_kur);
//				break;
//			case "echgad_dha":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_dha);
//				break;
//			case "echgad_wad":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_wad);
//				break;
//			case "echgad_bha":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_bha);
//				break;
//			case "echgad_mul":
//				DatabaseContextHolder.setCtx(DataSourceType.echgad_mul);
//				break;
//			default:
//				break;
//			}
//		case "echwar":
//			switch (request.getHeader("echSchemaName")) {
//			case "echwar_war":
//				DatabaseContextHolder.setCtx(DataSourceType.echwar_war);
//				break;
//			case "echwar_arv":
//				DatabaseContextHolder.setCtx(DataSourceType.echwar_arv);
//				break;
//			case "echwar_ash":
//				DatabaseContextHolder.setCtx(DataSourceType.echwar_ash);
//				break;
//			case "echwar_sel":
//				DatabaseContextHolder.setCtx(DataSourceType.echwar_sel);
//				break;
//			case "echwar_sam":
//				DatabaseContextHolder.setCtx(DataSourceType.echwar_sam);
//				break;
//			case "echwar_kar":
//				DatabaseContextHolder.setCtx(DataSourceType.echwar_kar);
//				break;
//			case "echwar_deo":
//				DatabaseContextHolder.setCtx(DataSourceType.echwar_deo);
//				break;
//			case "echwar_hin":
//				DatabaseContextHolder.setCtx(DataSourceType.echwar_hin);
//				break;
//			default:
//				break;
//			}
//		case "echgon":
//			switch (request.getHeader("echSchemaName")) {
//			case "echgon_gon":
//				DatabaseContextHolder.setCtx(DataSourceType.echgon_gon);
//				break;
//			case "echgon_mor":
//				DatabaseContextHolder.setCtx(DataSourceType.echgon_mor);
//				break;
//			case "echgon_amg":
//				DatabaseContextHolder.setCtx(DataSourceType.echgon_amg);
//				break;
//			case "echgon_deo":
//				DatabaseContextHolder.setCtx(DataSourceType.echgon_deo);
//				break;
//			case "echgon_sad":
//				DatabaseContextHolder.setCtx(DataSourceType.echgon_sad);
//				break;
//			case "echgon_tir":
//				DatabaseContextHolder.setCtx(DataSourceType.echgon_tir);
//				break;
//			case "echgon_sal":
//				DatabaseContextHolder.setCtx(DataSourceType.echgon_sal);
//				break;
//			case "echgon_gor":
//				DatabaseContextHolder.setCtx(DataSourceType.echgon_gor);
//				break;
//			default:
//				break;
//			}
//		case "echbha":
//			switch (request.getHeader("echSchemaName")) {
//			case "echbha_bha":
//				DatabaseContextHolder.setCtx(DataSourceType.echbha_bha);
//				break;
//			case "echbha_sako":
//				DatabaseContextHolder.setCtx(DataSourceType.echbha_sako);
//				break;
//			case "echbha_tum":
//				DatabaseContextHolder.setCtx(DataSourceType.echbha_tum);
//				break;
//			case "echbha_pau":
//				DatabaseContextHolder.setCtx(DataSourceType.echbha_pau);
//				break;
//			case "echbha_moh":
//				DatabaseContextHolder.setCtx(DataSourceType.echbha_moh);
//				break;
//			case "echbha_lakn":
//				DatabaseContextHolder.setCtx(DataSourceType.echbha_lakn);
//				break;
//			case "echbha_lakh":
//				DatabaseContextHolder.setCtx(DataSourceType.echbha_lakh);
//				break;
//			default:
//				break;
//			}
//		case "echtha":
//			switch (request.getHeader("echSchemaName")) {
//			case "echtha_tha":
//				DatabaseContextHolder.setCtx(DataSourceType.echtha_tha);
//				break;
//			case "echtha_kal":
//				DatabaseContextHolder.setCtx(DataSourceType.echtha_kal);
//				break;
//			case "echtha_amb":
//				DatabaseContextHolder.setCtx(DataSourceType.echtha_amb);
//				break;
//			case "echtha_bhi":
//				DatabaseContextHolder.setCtx(DataSourceType.echtha_bhi);
//				break;
//			case "echtha_sha":
//				DatabaseContextHolder.setCtx(DataSourceType.echtha_sha);
//				break;
//			case "echtha_ulh":
//				DatabaseContextHolder.setCtx(DataSourceType.echtha_ulh);
//				break;
//			case "echtha_mur":
//				DatabaseContextHolder.setCtx(DataSourceType.echtha_mur);
//				break;
//			default:
//				break;
//			}
//
//
//		case "echmsd":
//			switch (request.getHeader("echSchemaName")) {
//			case "echmsd_and":
//				DatabaseContextHolder.setCtx(DataSourceType.echmsd_and);
//				break;
//			case "echmsd_bor":
//				DatabaseContextHolder.setCtx(DataSourceType.echmsd_bor);
//				break;
//			case "echmsd_kur":
//				DatabaseContextHolder.setCtx(DataSourceType.echmsd_kur);
//				break;
//			default:
//				break;
//			}
//
//		case "echrai":
//			switch (request.getHeader("echSchemaName")) {
//			case "echrai_tal":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_tal);
//				break;
//			case "echrai_ali":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_ali);
//				break;
//			case "echrai_pan":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_pan);
//				break;
//			case "echrai_pen":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_pen);
//				break;
//			case "echrai_kar":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_kar);
//				break;
//			case "echrai_kha":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_kha);
//				break;
//			case "echrai_ura":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_ura);
//				break;
//			case "echrai_sud":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_sud);
//				break;
//			case "echrai_man":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_man);
//				break;
//			case "echrai_roh":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_roh);
//				break;
//			case "echrai_mur":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_mur);
//				break;
//			case "echrai_shr":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_shr);
//				break;
//			case "echrai_mha":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_mha);
//				break;
//			case "echrai_mah":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_mah);
//				break;
//			case "echrai_pol":
//				DatabaseContextHolder.setCtx(DataSourceType.echrai_pol);
//				break;
//			default:
//				break;
//			}
//
//
//		case "echrat":
//			switch (request.getHeader("echSchemaName")) {
//			case "echrat_rat":
//				DatabaseContextHolder.setCtx(DataSourceType.echrat_rat);
//				break;
//			case "echrat_man":
//				DatabaseContextHolder.setCtx(DataSourceType.echrat_man);
//				break;
//			case "echrat_dap":
//				DatabaseContextHolder.setCtx(DataSourceType.echrat_dap);
//				break;
//			case "echrat_ked":
//				DatabaseContextHolder.setCtx(DataSourceType.echrat_ked);
//				break;
//			case "echrat_chp":
//				DatabaseContextHolder.setCtx(DataSourceType.echrat_chp);
//				break;
//			case "echrat_guh":
//				DatabaseContextHolder.setCtx(DataSourceType.echrat_guh);
//				break;
//			case "echrat_sag":
//				DatabaseContextHolder.setCtx(DataSourceType.echrat_sag);
//				break;
//			case "echrat_lan":
//				DatabaseContextHolder.setCtx(DataSourceType.echrat_lan);
//				break;
//			default:
//				break;
//			}
//
//
//
//		case "echsin":
//			switch (request.getHeader("echSchemaName")) {
//			case "echsin_saw":
//				DatabaseContextHolder.setCtx(DataSourceType.echsin_saw);
//				break;
//			case "echsin_kan":
//				DatabaseContextHolder.setCtx(DataSourceType.echsin_kan);
//				break;
//			case "echsin_kud":
//				DatabaseContextHolder.setCtx(DataSourceType.echsin_kud);
//				break;
//			case "echsin_dev":
//				DatabaseContextHolder.setCtx(DataSourceType.echsin_dev);
//				break;
//			case "echsin_dod":
//				DatabaseContextHolder.setCtx(DataSourceType.echsin_dod);
//				break;
//			case "echsin_mal":
//				DatabaseContextHolder.setCtx(DataSourceType.echsin_mal);
//				break;
//			case "echsin_ven":
//				DatabaseContextHolder.setCtx(DataSourceType.echsin_ven);
//				break;
//			case "echsin_vai":
//				DatabaseContextHolder.setCtx(DataSourceType.echsin_vai);
//				break;
//			default:
//				break;
//			}
//
//
//		case "echpal":
//			switch (request.getHeader("echSchemaName")) {
//			case "echpal_pal":
//				DatabaseContextHolder.setCtx(DataSourceType.echpal_pal);
//				break;
//			case "echpal_vas":
//				DatabaseContextHolder.setCtx(DataSourceType.echpal_vas);
//				break;
//			case "echpal_wad":
//				DatabaseContextHolder.setCtx(DataSourceType.echpal_wad);
//				break;
//			case "echpal_jaw":
//				DatabaseContextHolder.setCtx(DataSourceType.echpal_jaw);
//				break;
//			case "echpal_mok":
//				DatabaseContextHolder.setCtx(DataSourceType.echpal_mok);
//				break;
//			case "echpal_dah":
//				DatabaseContextHolder.setCtx(DataSourceType.echpal_dah);
//				break;
//			case "echpal_tal":
//				DatabaseContextHolder.setCtx(DataSourceType.echpal_tal);
//				break;
//			case "echpal_vik":
//				DatabaseContextHolder.setCtx(DataSourceType.echpal_vik);
//				break;
//			default:
//				break;
//			}
//
//
//
//		case "echbee":
//			switch (request.getHeader("echSchemaName")) {
//			case "echbee_bee":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_bee);
//				break;
//			case "echbee_dha":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_dha);
//				break;
//			case "echbee_amb":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_amb);
//				break;
//			case "echbee_par":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_par);
//				break;
//			case "echbee_kej":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_kej);
//				break;
//			case "echbee_aas":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_aas);
//				break;
//			case "echbee_gev":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_gev);
//				break;
//			case "echpal_vik":
//				DatabaseContextHolder.setCtx(DataSourceType.echpal_vik);
//				break;
//			case "echbee_maj":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_maj);
//				break;
//			case "echbee_pat":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_pat);
//				break;
//			case "echbee_shi":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_shi);
//				break;
//			case "echbee_vad":
//				DatabaseContextHolder.setCtx(DataSourceType.echbee_vad);
//				break;
//			default:
//				break;
//			}
//
//
//
//		case "echlat":
//			switch (request.getHeader("echSchemaName")) {
//			case "echlat_lat":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_lat);
//				break;
//			case "echlat_udg":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_udg);
//				break;
//			case "echlat_ahm":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_ahm);
//				break;
//			case "echlat_dev":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_dev);
//				break;
//			case "echlat_shi":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_shi);
//				break;
//			case "echlat_aus":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_aus);
//				break;
//			case "echlat_nil":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_nil);
//				break;
//			case "echlat_ren":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_ren);
//				break;
//			case "echlat_cha":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_cha);
//				break;
//			case "echlat_jal":
//				DatabaseContextHolder.setCtx(DataSourceType.echlat_jal);
//				break;
//			default:
//				break;
//			}
//
//
//
//		case "echpar":
//			switch (request.getHeader("echSchemaName")) {
//			case "echpar_par":
//				DatabaseContextHolder.setCtx(DataSourceType.echpar_par);
//				break;
//			case "echpar_gan":
//				DatabaseContextHolder.setCtx(DataSourceType.echpar_gan);
//				break;
//			case "echpar_son":
//				DatabaseContextHolder.setCtx(DataSourceType.echpar_son);
//				break;
//			case "echpar_pat":
//				DatabaseContextHolder.setCtx(DataSourceType.echpar_pat);
//				break;
//			case "echpar_man":
//				DatabaseContextHolder.setCtx(DataSourceType.echpar_man);
//				break;
//			case "echpar_sel":
//				DatabaseContextHolder.setCtx(DataSourceType.echpar_sel);
//				break;
//			case "echpar_pur":
//				DatabaseContextHolder.setCtx(DataSourceType.echpar_pur);
//				break;
//			case "echpar_pal":
//				DatabaseContextHolder.setCtx(DataSourceType.echpar_pal);
//				break;
//			case "echpar_jin":
//				DatabaseContextHolder.setCtx(DataSourceType.echpar_jin);
//				break;
//			default:
//				break;
//			}
//
//
//
//		case "echaur":
//			switch (request.getHeader("echSchemaName")) {
//			case "echaur_aur":
//				DatabaseContextHolder.setCtx(DataSourceType.echaur_aur);
//				break;
//			case "echaur_khu":
//				DatabaseContextHolder.setCtx(DataSourceType.echaur_khu);
//				break;
//			case "echaur_soy":
//				DatabaseContextHolder.setCtx(DataSourceType.echaur_soy);
//				break;
//			case "echaur_sil":
//				DatabaseContextHolder.setCtx(DataSourceType.echaur_sil);
//				break;
//			case "echaur_gan":
//				DatabaseContextHolder.setCtx(DataSourceType.echaur_gan);
//				break;
//			case "echaur_kan":
//				DatabaseContextHolder.setCtx(DataSourceType.echaur_kan);
//				break;
//			case "echaur_phu":
//				DatabaseContextHolder.setCtx(DataSourceType.echaur_phu);
//				break;
//			case "echaur_pai":
//				DatabaseContextHolder.setCtx(DataSourceType.echaur_pai);
//				break;
//			case "echaur_vai":
//				DatabaseContextHolder.setCtx(DataSourceType.echaur_vai);
//				break;
//			default:
//				break;
//			}
//
//
//
//		case "echosm":
//			switch (request.getHeader("echSchemaName")) {
//			case "echosm_osm":
//				DatabaseContextHolder.setCtx(DataSourceType.echosm_osm);
//				break;
//			case "echosm_tul":
//				DatabaseContextHolder.setCtx(DataSourceType.echosm_tul);
//				break;
//			case "echosm_ome":
//				DatabaseContextHolder.setCtx(DataSourceType.echosm_ome);
//				break;
//			case "echosm_loh":
//				DatabaseContextHolder.setCtx(DataSourceType.echosm_loh);
//				break;
//			case "echosm_kal":
//				DatabaseContextHolder.setCtx(DataSourceType.echosm_kal);
//				break;
//			case "echosm_bho":
//				DatabaseContextHolder.setCtx(DataSourceType.echosm_bho);
//				break;
//			case "echosm_was":
//				DatabaseContextHolder.setCtx(DataSourceType.echosm_was);
//				break;
//			case "echosm_par":
//				DatabaseContextHolder.setCtx(DataSourceType.echosm_par);
//				break;
//			default:
//				break;
//			}
//
//
//		case "echjal":
//			switch (request.getHeader("echSchemaName")) {
//			case "echjal_jal":
//				DatabaseContextHolder.setCtx(DataSourceType.echjal_jal);
//				break;
//			case "echjal_amb":
//				DatabaseContextHolder.setCtx(DataSourceType.echjal_amb);
//				break;
//			case "echjal_bho":
//				DatabaseContextHolder.setCtx(DataSourceType.echjal_bho);
//				break;
//			case "echjal_bad":
//				DatabaseContextHolder.setCtx(DataSourceType.echjal_bad);
//				break;
//			case "echjal_gha":
//				DatabaseContextHolder.setCtx(DataSourceType.echjal_gha);
//				break;
//			case "echjal_par":
//				DatabaseContextHolder.setCtx(DataSourceType.echjal_par);
//				break;
//			case "echjal_man":
//				DatabaseContextHolder.setCtx(DataSourceType.echjal_man);
//				break;
//			case "echjal_jaf":
//				DatabaseContextHolder.setCtx(DataSourceType.echjal_jaf);
//				break;
//			default:
//				break;
//			}
//
//
//		case "echnan":
//			switch (request.getHeader("echSchemaName")) {
//			case "echnan_mud":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_mud);
//				break;
//			case "echnan_nan":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_nan);
//				break;
//			case "echnan_bho":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_bho);
//				break;
//			case "echnan_ard":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_ard);
//				break;
//			case "echnan_bil":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_bil);
//				break;
//			case "echnan_deg":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_deg);
//				break;
//			case "echnan_dha":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_dha);
//				break;
//			case "echnan_had":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_had);
//				break;
//			case "echnan_hmy":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_hmy);
//				break;
//			case "echnan_kan":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_kan);
//				break;
//			case "echnan_kin":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_kin);
//				break;
//			case "echnan_loh":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_loh);
//				break;
//			case "echnan_mah":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_mah);
//				break;
//			case "echnan_muk":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_muk);
//				break;
//			case "echnan_nai":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_nai);
//				break;
//			case "echnan_umr":
//				DatabaseContextHolder.setCtx(DataSourceType.echnan_umr);
//				break;
//			default:
//				break;
//			}
//
//
//		case "echhin":
//			switch (request.getHeader("echSchemaName")) {
//			case "echhin_hin":
//				DatabaseContextHolder.setCtx(DataSourceType.echhin_hin);
//				break;
//			case "echhin_aun":
//				DatabaseContextHolder.setCtx(DataSourceType.echhin_aun);
//				break;
//			case "echhin_sen":
//				DatabaseContextHolder.setCtx(DataSourceType.echhin_sen);
//				break;
//			case "echhin_kal":
//				DatabaseContextHolder.setCtx(DataSourceType.echhin_kal);
//				break;
//			case "echhin_bas":
//				DatabaseContextHolder.setCtx(DataSourceType.echhin_bas);
//				break;
//			default:
//				break;
//			}
//
//
//
//		case "echamr":
//			switch (request.getHeader("echSchemaName")) {
//			case "echamr_amr":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_amr);
//				break;
//			case "echamr_dha":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_dha);
//				break;
//			case "echamr_chi":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_chi);
//				break;
//			case "echamr_ach":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_ach);
//				break;
//			case "echamr_chb":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_chb);
//				break;
//			case "echamr_mor":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_mor);
//				break;
//			case "echamr_war":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_war);
//				break;
//			case "echamr_anj":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_anj);
//				break;
//			case "echamr_bha":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_bha);
//				break;
//			case "echamr_tio":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_tio);
//				break;
//			case "echamr_dar":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_dar);
//				break;
//			case "echamr_nan":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_nan);
//				break;
//			case "echamr_chr":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_chr);
//				break;
//			case "echamr_dhm":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_dhm);
//				break;
//			default:
//				break;
//			}
//
//
//
//		case "echyav":
//			switch (request.getHeader("echSchemaName")) {
//			case "echyav_yav":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_yav);
//				break;
//			case "echyav_bab":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_bab);
//				break;
//			case "echyav_ner":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_ner);
//				break;
//			case "echyav_dar":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_dar);
//				break;
//			case "echyav_dig":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_dig);
//				break;
//			case "echyav_pus":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_pus);
//				break;
//			case "echyav_kal":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_kal);
//				break;
//			case "echyav_ral":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_ral);
//				break;
//			case "echyav_gha":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_gha);
//				break;
//			case "echyav_kel":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_kel);
//				break;
//			case "echyav_mar":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_mar);
//				break;
//			case "echyav_wan":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_wan);
//				break;
//			case "echyav_mah":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_mah);
//				break;
//			case "echamr_dhm":
//				DatabaseContextHolder.setCtx(DataSourceType.echamr_dhm);
//				break;
//			case "echyav_arn":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_arn);
//				break;
//			case "echyav_zar":
//				DatabaseContextHolder.setCtx(DataSourceType.echyav_zar);
//				break;
//			default:
//				break;
//			}
//
//
//
//		case "echbul":
//			switch (request.getHeader("echSchemaName")) {
//			case "echbul_kha":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_kha);
//				break;
//			case "echbul_chi":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_chi);
//				break;
//			case "echbul_san":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_san);
//				break;
//			case "echbul_sin":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_sin);
//				break;
//			case "echbul_deu":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_deu);
//				break;
//			case "echbul_bul":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_bul);
//				break;
//			case "echbul_meh":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_meh);
//				break;
//			case "echbul_mot":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_mot);
//				break;
//			case "echbul_mal":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_mal);
//				break;
//			case "echbul_lon":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_lon);
//				break;
//			case "echbul_jal":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_jal);
//				break;
//			case "echbul_she":
//				DatabaseContextHolder.setCtx(DataSourceType.echbul_she);
//				break;
//			default:
//				break;
//			}
//
//
//		case "echako":
//			switch (request.getHeader("echSchemaName")) {
//			case "echako_ako":
//				DatabaseContextHolder.setCtx(DataSourceType.echako_ako);
//				break;
//			case "echako_akt":
//				DatabaseContextHolder.setCtx(DataSourceType.echako_akt);
//				break;
//			case "echako_tel":
//				DatabaseContextHolder.setCtx(DataSourceType.echako_tel);
//				break;
//			case "echako_pat":
//				DatabaseContextHolder.setCtx(DataSourceType.echako_pat);
//				break;
//			case "echako_bar":
//				DatabaseContextHolder.setCtx(DataSourceType.echako_bar);
//				break;
//			case "echako_bal":
//				DatabaseContextHolder.setCtx(DataSourceType.echako_bal);
//				break;
//			case "echako_mur":
//				DatabaseContextHolder.setCtx(DataSourceType.echako_mur);
//				break;
//			default:
//				break;
//			}
//
//		case "echwas":
//			switch (request.getHeader("echSchemaName")) {
//			case "echwas_was":
//				DatabaseContextHolder.setCtx(DataSourceType.echwas_was);
//				break;
//			case "echwas_kar":
//				DatabaseContextHolder.setCtx(DataSourceType.echwas_kar);
//				break;
//			case "echwas_mng":
//				DatabaseContextHolder.setCtx(DataSourceType.echwas_mng);
//				break;
//			case "echwas_mal":
//				DatabaseContextHolder.setCtx(DataSourceType.echwas_mal);
//				break;
//			case "echwas_ris":
//				DatabaseContextHolder.setCtx(DataSourceType.echwas_ris);
//				break;
//			case "echwas_man":
//				DatabaseContextHolder.setCtx(DataSourceType.echwas_man);
//			}
//		default:
//			break;
//		}
//	}
//}