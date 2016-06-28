/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/6/27 17:28:00                           */
/*==============================================================*/


alter table xlem_buyer
   drop constraint FK_BR_REFERENCE_U;

alter table xlem_buyer
   drop constraint FK_BR_REFERENCE_UL;

alter table xlem_item
   drop constraint FK_I_REFERENCE_SL;

alter table xlem_itemsku
   drop constraint FK_ISKU_REFERENCE_I;

alter table xlem_itemsku
   drop constraint FK_ISKU_REFERENCE_SKU;

alter table xlem_seller
   drop constraint FK_SL_REFERENCE_U;

alter table xlem_seller
   drop constraint FK_SL_REFERENCE_UL;

alter table xlem_sku
   drop constraint FK_SKU_REFERENCE_AUNIT;

alter table xlem_sku
   drop constraint FK_SKU_REFERENCE_SPU;

alter table xlem_spu
   drop constraint FK_SPU_REFERENCE_SPUC;

alter table xlem_stock
   drop constraint FK_STK_REFERENCE_SKU;

alter table xlem_stockhistory
   drop constraint FK_STKH_REFERENCE_SKU;

alter table xlem_unit
   drop constraint FK_UNIT_REFERENCE_AUNIT;

alter table xlem_userlevel
   drop constraint FK_UL_REFERENCE_UL;

alter table xlfin_accountcondition
   drop constraint FK_AC_REFERENCE_KD;

alter table xlfin_balance
   drop constraint FK_B_REFERENCE_A;

alter table xlfin_balance
   drop constraint FK_B_REFERENCE_C;

alter table xlfin_balance
   drop constraint FK_B_REFERENCE_KD;

alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_A;

alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_C;

alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_KD;

alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_U;

alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_VD;

alter table xlfin_bankstmtbalance
   drop constraint FK_BSB_REFERENCE_A;

alter table xlfin_bankstmtbalance
   drop constraint FK_BSB_REFERENCE_C;

alter table xlfin_bankstmtbalance
   drop constraint FK_BSB_REFERENCE_KD;

alter table xlfin_bstldt
   drop constraint FK_BSTLDT_REFERENCE_BSTL;

alter table xlfin_exchangerate
   drop constraint FK_ER_REFERENCE_C;

alter table xlfin_karaccdt
   drop constraint FK_KARADT_REFERENCE_KAR;

alter table xlfin_kardt
   drop constraint FK_KARDT_REFERENCE_KAR;

alter table xlfin_kardt
   drop constraint FK_KARDT_REFERENCE_VDTECA;

alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_C1;

alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_C2;

alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_KDAR1;

alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_KDAR2;

alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_KDAR3;

alter table xlfin_reportdata
   drop constraint FK_RD_REFERENCE_RDEPT;

alter table xlfin_reportdata
   drop constraint FK_RD_REFERENCE_RF;

alter table xlfin_reportdatadetail
   drop constraint FK_RDD_REFERENCE_RD;

alter table xlfin_reportformcol
   drop constraint FK_RFC_REFERENCE_RF;

alter table xlfin_reportformformula
   drop constraint FK_RFF_REFERENCE_RF;

alter table xlfin_reportformrow
   drop constraint FK_RFR_REFERENCE_RF;

alter table xlfin_vdtextra
   drop constraint FK_VDTE_REFERENCE_VDT;

alter table xlfin_vdtextra
   drop constraint FK_VDTE_REFERENCE_VEC;

alter table xlfin_vdtextracolattr
   drop constraint FK_VDTECA_REFERENCE_VDTEC;

alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_F;

alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_ID;

alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_KD;

alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_U1;

alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_U2;

alter table xlfin_voucherdetail
   drop constraint FK_VD_REFERENCE_A;

alter table xlfin_voucherdetail
   drop constraint FK_VD_REFERENCE_BS;

alter table xlfin_voucherdetail
   drop constraint FK_VD_REFERENCE_C;

alter table xlfin_voucherdetail
   drop constraint FK_VDT_REFERENCE_KD;

alter table xlfin_voucherdetail
   drop constraint FK_VDT_REFERENCE_U;

alter table xlfin_voucherdetail
   drop constraint FK_VOD_REFERENCE_VO;

alter table xlfin_vtempletdt
   drop constraint FK_VTLDT_REFERENCE_VTL;

alter table xlfin_vtldtcol
   drop constraint FK_VTLDTC_REFERENCE_VTLDT;

alter table xlsys_authorisedright
   drop constraint FK_AR_REFERENCE_AD;

alter table xlsys_authorisedright
   drop constraint FK_AR_REFERENCE_R;

alter table xlsys_authorize
   drop constraint FK_A_REFERENCE_F;

alter table xlsys_authorize
   drop constraint FK_A_REFERENCE_I1;

alter table xlsys_authorize
   drop constraint FK_A_REFERENCE_I2;

alter table xlsys_authorize
   drop constraint FK_A_REFERENCE_U;

alter table xlsys_authorizedetail
   drop constraint FK_AD_REFERENCE_A;

alter table xlsys_basebusi
   drop constraint FK_BB_REFERENCE_F;

alter table xlsys_basebusi
   drop constraint FK_BB_REFERENCE_I;

alter table xlsys_basebusi
   drop constraint FK_BB_REFERENCE_U;

alter table xlsys_dictdetail
   drop constraint FK_DD_REFERENCE_D;

alter table xlsys_envdetail
   drop constraint FK_ED_REFERENCE_D;

alter table xlsys_envdetail
   drop constraint FK_ED_REFERENCE_E;

alter table xlsys_exttableinfodetail
   drop constraint FK_ETI_REFERENCE_ETID;

alter table xlsys_flow
   drop constraint FK_F_REFERENCE_V1;

alter table xlsys_flow
   drop constraint FK_F_REFERENCE_V2;

alter table xlsys_flow
   drop constraint FK_FL_REFERENCE_P1;

alter table xlsys_flow
   drop constraint FK_FL_REFERENCE_P2;

alter table xlsys_flowcodealloc
   drop constraint FK_FCA_REFERENCE_CA;

alter table xlsys_flowcodealloc
   drop constraint FK_FCA_REFERENCE_F;

alter table xlsys_flowcondition
   drop constraint FK_FC_REFERENCE_F;

alter table xlsys_flowjava
   drop constraint FK_FJAVA_REFERENCE_F;

alter table xlsys_flowjava
   drop constraint FK_FJAVA_REFERENCE_V;

alter table xlsys_flowjs
   drop constraint FK_FJS_REFERENCE_F;

alter table xlsys_flowjs
   drop constraint FK_FJS_REFERENCE_V;

alter table xlsys_flowlogic
   drop constraint FK_FL_REFERENCE_F;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_F;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_P1;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_P2;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_R;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_V1;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_V2;

alter table xlsys_flowright
   drop constraint FK_FR_REFERENCE_R;

alter table xlsys_flowright
   drop constraint FK_FR_REFERENCE_FC;

alter table xlsys_flowsubtable
   drop constraint FK_FST_REFERENCE_F;

alter table xlsys_identity
   drop constraint FK_I_REFERENCE_M;

alter table xlsys_identity
   drop constraint FKV2_I_REFERENCE_F;

alter table xlsys_idrelation
   drop constraint FK_IR_REFERENCE_R;

alter table xlsys_idrelation
   drop constraint FK_IR_REFERENCE_I;

alter table xlsys_menuright
   drop constraint FK_MR_REFERENCE_M;

alter table xlsys_oacategoryright
   drop constraint FK_OACR_REFERENCE_OAC;

alter table xlsys_oacmbelong
   drop constraint FK_OACMB_REFERENCE_OAM;

alter table xlsys_oacmbelong
   drop constraint FK_OACMBL_REFERENCE_OAC;

alter table xlsys_oacmrelation
   drop constraint FK_OACMR_REFERENCE_ID;

alter table xlsys_oacmrelation
   drop constraint FK_OACMR_REFERENCE_OACMBL;

alter table xlsys_oamoduleextra
   drop constraint FK_OAME_REFERENCE_OAM;

alter table xlsys_oamoduleextra
   drop constraint FK_OAME_REFERENCE_V;

alter table xlsys_oamoduleright
   drop constraint FK_OAMR_REFERENCE_OAM;

alter table xlsys_partdetail
   drop constraint FK_PD_REFERENCE_P;

alter table xlsys_partdetail
   drop constraint FK_PD_REFERENCE_V1;

alter table xlsys_partdetail
   drop constraint FK_PD_REFERENCE_V2;

alter table xlsys_queryparamsave
   drop constraint FK_QPS_REFERENCE_ID;

alter table xlsys_queryparamsave
   drop constraint FK_QPS_REFERENCE_V;

alter table xlsys_ratify
   drop constraint FK_R_REFERENCE_FC_F;

alter table xlsys_ratify
   drop constraint FK_R_REFERENCE_FC_T;

alter table xlsys_ratify
   drop constraint FK_R_REFERENCE_U;

alter table xlsys_ratifydetail
   drop constraint FK_RD_REFERENCE_R;

alter table xlsys_ratifydetail
   drop constraint FK_RD_REFERENCE_U1;

alter table xlsys_ratifydetail
   drop constraint FK_RD_REFERENCE_U2;

alter table xlsys_transportdetail
   drop constraint FK_TSDT_REFERENCE_TS;

alter table xlsys_transportdtcolmap
   drop constraint FK_TSDTCM_REFERENCE_TSDT;

alter table xlsys_transportkeysynonym
   drop constraint FK_TSKS_REFERENCE_TSK;

alter table xlsys_transportmap
   drop constraint FK_TSK_REFERENCE_TSM;

alter table xlsys_transportrun
   drop constraint FK_TSR_REFERENCE_TS;

alter table xlsys_useremail
   drop constraint FK_UE_REFERENCE_U;

alter table xlsys_viewdetail
   drop constraint FK_VD_REFERENCE_V;

alter table xlsys_viewdetailparam
   drop constraint FK_VDP_REFERENCE_VD;

alter table xlsys_viewqueryparam
   drop constraint FK_VQP_REFERENCE_V;

alter table xlsys_wdtcolumn
   drop constraint FK_WDC_REFERENCE_WD;

alter table xlsys_wizarddetail
   drop constraint FK_WD_REFERENCE_V;

alter table xlsys_wizarddetail
   drop constraint FK_WD_REFERENCE_W1;

alter table xlv2_authorisedright
   drop constraint FKV2_AR_REFERENCE_AD;

alter table xlv2_authorisedright
   drop constraint FKV2_AR_REFERENCE_R;

alter table xlv2_authorize
   drop constraint FKV2_A_REFERENCE_F;

alter table xlv2_authorize
   drop constraint FKV2_A_REFERENCE_I1;

alter table xlv2_authorize
   drop constraint FKV2_A_REFERENCE_I2;

alter table xlv2_authorize
   drop constraint FKV2_A_REFERENCE_U;

alter table xlv2_authorizedetail
   drop constraint FKV2_AD_REFERENCE_A;

alter table xlv2_authorizedetail
   drop constraint FKV2_AD_REFERENCE_F;

alter table xlv2_authorizedetail
   drop constraint FKV2_AD_REFERENCE_I;

alter table xlv2_flowcodealloc
   drop constraint FKV2_FCA_REFERENCE_CA;

alter table xlv2_flowcodealloc
   drop constraint FKV2_FCA_REFERENCE_F;

alter table xlv2_flowcondition
   drop constraint FKV2_FC_REFERENCE_F;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_F1;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_F2;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_FLOW;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_R;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_V1;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_V2;

alter table xlv2_flowlogic
   drop constraint FKV2_FL_REFERENCE_F;

alter table xlv2_flowright
   drop constraint FKV2_FR_REFERENCE_FC;

alter table xlv2_flowright
   drop constraint FKV2_FR_REFERENCE_R1;

alter table xlv2_flowright
   drop constraint FKV2_FR_REFERENCE_R2;

alter table xlv2_flowsubtable
   drop constraint FKV2_FST_REFERENCE_F;

alter table xlv2_flowviewlistener
   drop constraint FKV2_FVL_REFERENCE_F;

alter table xlv2_flowviewlistener
   drop constraint FKV2_FVL_REFERENCE_V;

alter table xlv2_framedetail
   drop constraint FKV2_FD_REFERENCE_F;

alter table xlv2_framedetail
   drop constraint FKV2_FD_REFERENCE_UIM;

alter table xlv2_framedetail
   drop constraint FKV2_FD_REFERENCE_V;

alter table xlv2_framedetailparam
   drop constraint FKV2_FDP_REFERENCE_FD;

alter table xlv2_frameparam
   drop constraint FKV2_FP_REFERENCE_F;

alter table xlv2_menu
   drop constraint FKV2_M_REFERENCE_H;

alter table xlv2_menuhandlerparam
   drop constraint FKV2_MHP_REFERENCE_M;

alter table xlv2_menuright
   drop constraint FKV2_MR_REFERENCE_M;

alter table xlv2_menuright
   drop constraint FKV2_MR_REFERENCE_R;

alter table xlv2_ratify
   drop constraint FKV2_R_REFERENCE_FC1;

alter table xlv2_ratify
   drop constraint FKV2_R_REFERENCE_FC2;

alter table xlv2_ratify
   drop constraint FKV2_R_REFERENCE_U1;

alter table xlv2_ratify
   drop constraint FKV2_R_REFERENCE_U2;

alter table xlv2_ratifydetail
   drop constraint FKV2_RD_REFERENCE_R;

alter table xlv2_ratifydetail
   drop constraint FKV2_RD_REFERENCE_U;

alter table xlv2_testbusi
   drop constraint FKV2_TB_REFERENCE_F;

alter table xlv2_testbusisub
   drop constraint FKV2_TBS_REFERENCE_TB;

alter table xlv2_tool
   drop constraint FKV2_T_REFERENCE_H;

alter table xlv2_toolhandlerparam
   drop constraint FKV2_THP_REFERENCE_T;

alter table xlv2_toolright
   drop constraint FKV2_TR_REFERENCE_R;

alter table xlv2_toolright
   drop constraint FKV2_TR_REFERENCE_T;

alter table xlv2_view
   drop constraint FKV2_V_REFERENCE_UIM;

alter table xlv2_viewcolumn
   drop constraint FK_XLV2_VIE_REFERENCE_XLV2_VIE;

alter table xlv2_viewcolumn
   drop constraint FKV2_VC_REFERENCE_UIM;

alter table xlv2_viewcolumnparam
   drop constraint FKV2_VCP_REFERENCE_VC;

alter table xlv2_viewparam
   drop constraint FKV2_VP_REFERENCE_V;

alter table xlv2_viewqueryparam
   drop constraint FKV2_VQP_REFERENCE_UIM;

alter table xlv2_viewqueryparam
   drop constraint FKV2_VQP_REFERENCE_V;

alter table xlv2_viewqueryparamparam
   drop constraint FKV2_VQPP_REFERENCE_VQP;

drop table xlem_atomicunit cascade constraints;

drop table xlem_buyer cascade constraints;

drop table xlem_item cascade constraints;

drop table xlem_itemsku cascade constraints;

drop table xlem_prepsynonym cascade constraints;

drop table xlem_searchkeyword cascade constraints;

drop table xlem_searchtext cascade constraints;

drop index UN_SELLER_U;

drop table xlem_seller cascade constraints;

drop table xlem_sku cascade constraints;

drop table xlem_spu cascade constraints;

drop table xlem_spucategory cascade constraints;

drop table xlem_spuindex cascade constraints;

drop table xlem_stock cascade constraints;

drop table xlem_stockhistory cascade constraints;

drop table xlem_synonym cascade constraints;

drop table xlem_unit cascade constraints;

drop table xlem_user cascade constraints;

drop table xlem_userlevel cascade constraints;

drop table xlfin_account cascade constraints;

drop table xlfin_accountcondition cascade constraints;

drop table xlfin_accountingitem cascade constraints;

drop table xlfin_balance cascade constraints;

drop table xlfin_balanceitem cascade constraints;

drop table xlfin_bankstmt cascade constraints;

drop table xlfin_bankstmtbalance cascade constraints;

drop table xlfin_bankstmttemplet cascade constraints;

drop table xlfin_bstldt cascade constraints;

drop table xlfin_currency cascade constraints;

drop table xlfin_exchangerate cascade constraints;

drop table xlfin_karaccdt cascade constraints;

drop table xlfin_kardt cascade constraints;

drop table xlfin_kdeptaccrealtion cascade constraints;

drop table xlfin_keepdept cascade constraints;

drop index un_rrbe;

drop table xlfin_reportdata cascade constraints;

drop table xlfin_reportdatadetail cascade constraints;

drop table xlfin_reportdept cascade constraints;

drop table xlfin_reportform cascade constraints;

drop table xlfin_reportformcol cascade constraints;

drop table xlfin_reportformformula cascade constraints;

drop table xlfin_reportformrow cascade constraints;

drop table xlfin_vdtextra cascade constraints;

drop table xlfin_vdtextracol cascade constraints;

drop table xlfin_vdtextracolattr cascade constraints;

drop table xlfin_voucher cascade constraints;

drop table xlfin_voucherdetail cascade constraints;

drop table xlfin_vouchertemplet cascade constraints;

drop table xlfin_vtempletdt cascade constraints;

drop table xlfin_vtldtcol cascade constraints;

drop table xlsys_attachment cascade constraints;

drop table xlsys_authorisedright cascade constraints;

drop table xlsys_authorize cascade constraints;

drop index un_ad_fci;

drop table xlsys_authorizedetail cascade constraints;

drop table xlsys_basebusi cascade constraints;

drop table xlsys_codealloc cascade constraints;

drop table xlsys_db cascade constraints;

drop table xlsys_department cascade constraints;

drop table xlsys_dict cascade constraints;

drop table xlsys_dictdetail cascade constraints;

drop table xlsys_emailtemplate cascade constraints;

drop table xlsys_env cascade constraints;

drop table xlsys_envdetail cascade constraints;

drop table xlsys_extracmd cascade constraints;

drop table xlsys_exttableinfo cascade constraints;

drop table xlsys_exttableinfodetail cascade constraints;

drop table xlsys_flow cascade constraints;

drop table xlsys_flowcodealloc cascade constraints;

drop table xlsys_flowcondition cascade constraints;

drop table xlsys_flowjava cascade constraints;

drop table xlsys_flowjs cascade constraints;

drop table xlsys_flowlogic cascade constraints;

drop table xlsys_flowpart cascade constraints;

drop table xlsys_flowright cascade constraints;

drop table xlsys_flowsubtable cascade constraints;

drop table xlsys_identity cascade constraints;

drop index xlsys_idrelation_tcv;

drop index xlsys_idrelation_uq;

drop table xlsys_idrelation cascade constraints;

drop table xlsys_image cascade constraints;

drop table xlsys_ipresource cascade constraints;

drop table xlsys_javaclass cascade constraints;

drop table xlsys_menu cascade constraints;

drop table xlsys_menuright cascade constraints;

drop table xlsys_oacategory cascade constraints;

drop table xlsys_oacategoryright cascade constraints;

drop table xlsys_oacmbelong cascade constraints;

drop table xlsys_oacmrelation cascade constraints;

drop table xlsys_oamodule cascade constraints;

drop table xlsys_oamoduleextra cascade constraints;

drop table xlsys_oamoduleright cascade constraints;

drop table xlsys_part cascade constraints;

drop table xlsys_partdetail cascade constraints;

drop table xlsys_position cascade constraints;

drop table xlsys_print cascade constraints;

drop table xlsys_queryparamsave cascade constraints;

drop table xlsys_ratify cascade constraints;

drop table xlsys_ratifydetail cascade constraints;

drop index UN_RIGHT_SK;

drop index UN_RIGHT_RC;

drop table xlsys_right cascade constraints;

drop table xlsys_test cascade constraints;

drop table xlsys_translator cascade constraints;

drop table xlsys_transport cascade constraints;

drop table xlsys_transportdetail cascade constraints;

drop table xlsys_transportdtcolmap cascade constraints;

drop table xlsys_transportkey cascade constraints;

drop table xlsys_transportkeysynonym cascade constraints;

drop table xlsys_transportmap cascade constraints;

drop table xlsys_transportrun cascade constraints;

drop table xlsys_user cascade constraints;

drop table xlsys_useremail cascade constraints;

drop table xlsys_view cascade constraints;

drop index un_vd;

drop table xlsys_viewdetail cascade constraints;

drop table xlsys_viewdetailparam cascade constraints;

drop table xlsys_viewqueryparam cascade constraints;

drop table xlsys_wdtcolumn cascade constraints;

drop table xlsys_wizard cascade constraints;

drop table xlsys_wizarddetail cascade constraints;

drop table xlv2_authorisedright cascade constraints;

drop table xlv2_authorize cascade constraints;

drop index un_ad_fci2;

drop table xlv2_authorizedetail cascade constraints;

drop table xlv2_codealloc cascade constraints;

drop table xlv2_control cascade constraints;

drop table xlv2_flow cascade constraints;

drop table xlv2_flowcodealloc cascade constraints;

drop table xlv2_flowcondition cascade constraints;

drop table xlv2_flowframe cascade constraints;

drop table xlv2_flowlogic cascade constraints;

drop table xlv2_flowright cascade constraints;

drop table xlv2_flowsubtable cascade constraints;

drop table xlv2_flowviewlistener cascade constraints;

drop table xlv2_frame cascade constraints;

drop table xlv2_framedetail cascade constraints;

drop table xlv2_framedetailparam cascade constraints;

drop table xlv2_frameparam cascade constraints;

drop table xlv2_handler cascade constraints;

drop table xlv2_menu cascade constraints;

drop table xlv2_menuhandlerparam cascade constraints;

drop table xlv2_menuright cascade constraints;

drop table xlv2_ratify cascade constraints;

drop table xlv2_ratifydetail cascade constraints;

drop table xlv2_testbusi cascade constraints;

drop table xlv2_testbusisub cascade constraints;

drop table xlv2_tool cascade constraints;

drop table xlv2_toolhandlerparam cascade constraints;

drop table xlv2_toolright cascade constraints;

drop table xlv2_uimodule cascade constraints;

drop table xlv2_view cascade constraints;

drop table xlv2_viewcolumn cascade constraints;

drop table xlv2_viewcolumnparam cascade constraints;

drop table xlv2_viewparam cascade constraints;

drop table xlv2_viewqueryparam cascade constraints;

drop table xlv2_viewqueryparamparam cascade constraints;

/*==============================================================*/
/* Table: xlem_atomicunit                                     */
/*==============================================================*/
create table xlem_atomicunit 
(
   aunitid            VARCHAR2(32)         not null,
   name               VARCHAR2(256),
   constraint PK_XLEM_ATOMICUNIT primary key (aunitid)
);

comment on table xlem_atomicunit is
'不可拆分的单位定义表';

/*==============================================================*/
/* Table: xlem_buyer                                          */
/*==============================================================*/
create table xlem_buyer 
(
   buyerid            VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   userid             VARCHAR2(256),
   levelid            VARCHAR2(256),
   experience         NUMBER(18,6),
   constraint PK_XLEM_BUYER primary key (buyerid)
);

comment on table xlem_buyer is
'电子商务买家表, 主要记录买家相关信息';

/*==============================================================*/
/* Table: xlem_item                                           */
/*==============================================================*/
create table xlem_item 
(
   itemid             VARCHAR2(32)         not null,
   sellerid           VARCHAR2(256),
   name               VARCHAR2(256),
   description        VARCHAR2(4000),
   constraint PK_XLEM_ITEM primary key (itemid)
);

comment on table xlem_item is
'电子商务商品表.
一个商品对应多个SKU';

/*==============================================================*/
/* Table: xlem_itemsku                                        */
/*==============================================================*/
create table xlem_itemsku 
(
   itemid             VARCHAR2(32)         not null,
   sku                VARCHAR2(32)         not null,
   constraint PK_XLEM_ITEMSKU primary key (itemid, sku)
);

comment on table xlem_itemsku is
'商品对应的SKU';

/*==============================================================*/
/* Table: xlem_prepsynonym                                    */
/*==============================================================*/
create table xlem_prepsynonym 
(
   mergehash          NUMBER(8,0)          not null,
   srcword            VARCHAR2(32)         not null,
   synword            VARCHAR2(32)         not null,
   heat               NUMBER(8,0),
   constraint PK_XLEM_PREPSYNONYM primary key (mergehash)
);

comment on table xlem_prepsynonym is
'预备同义词库. 该库中的同义词热度累计到达一定数量后, 则会转移到同义词库中';

/*==============================================================*/
/* Table: xlem_searchkeyword                                  */
/*==============================================================*/
create table xlem_searchkeyword 
(
   keyword            VARCHAR2(64)         not null,
   heat               NUMBER(8,0)          not null,
   constraint PK_XLEM_SEARCHKEYWORD primary key (keyword)
);

comment on table xlem_searchkeyword is
'查询关键字热度表, 此表中存的都是独立的不需要进行拆分的查询关键字信息';

/*==============================================================*/
/* Table: xlem_searchtext                                     */
/*==============================================================*/
create table xlem_searchtext 
(
   searchtext         VARCHAR2(1000)       not null,
   heat               NUMBER(8,0)          not null,
   constraint PK_XLEM_SEARCHTEXT primary key (searchtext)
);

comment on table xlem_searchtext is
'查询热度表. 此表保存了所有查询字符串以及对应的热度';

/*==============================================================*/
/* Table: xlem_seller                                         */
/*==============================================================*/
create table xlem_seller 
(
   sellerid           VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   userid             VARCHAR2(256),
   levelid            VARCHAR2(256),
   experience         NUMBER(18,6),
   constraint PK_XLEM_SELLER primary key (sellerid)
);

comment on table xlem_seller is
'电子商务卖家表, 主要记录卖家相关信息';

/*==============================================================*/
/* Index: UN_SELLER_U                                           */
/*==============================================================*/
create unique index UN_SELLER_U on xlem_seller (
   userid ASC
);

/*==============================================================*/
/* Table: xlem_sku                                            */
/*==============================================================*/
create table xlem_sku 
(
   sku                VARCHAR2(32)         not null,
   spu                VARCHAR2(32)         not null,
   aunitid            VARCHAR2(32)         not null,
   name               VARCHAR2(256),
   description        VARCHAR2(4000),
   constraint PK_XLEM_SKU primary key (sku)
);

comment on table xlem_sku is
'电子商务SKU.
库存量单位, 最小单位, 不可分割
一个SKU对应一个SPU.
一个SPU对应多个SKU.';

/*==============================================================*/
/* Table: xlem_spu                                            */
/*==============================================================*/
create table xlem_spu 
(
   spu                VARCHAR2(32)         not null,
   categoryid         VARCHAR2(256),
   name               VARCHAR2(256),
   description        VARCHAR2(4000),
   constraint PK_XLEM_SPU primary key (spu)
);

comment on table xlem_spu is
'电子商务SPU表.一个SPU对应多个SKU, 一个SKU只对应一个SPU';

/*==============================================================*/
/* Table: xlem_spucategory                                    */
/*==============================================================*/
create table xlem_spucategory 
(
   categoryid         VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   constraint PK_XLEM_SPUCATEGORY primary key (categoryid)
);

comment on table xlem_spucategory is
'SPU分类';

/*==============================================================*/
/* Table: xlem_spuindex                                       */
/*==============================================================*/
create table xlem_spuindex 
(
   filename           VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   content            BLOB,
   constraint PK_XLEM_SPUINDEX primary key (filename, idx)
);

comment on table xlem_spuindex is
'电子商务SPU索引';

/*==============================================================*/
/* Table: xlem_stock                                          */
/*==============================================================*/
create table xlem_stock 
(
   stkid              NUMBER(16,0)         not null,
   sku                VARCHAR2(32)         not null,
   quantity           NUMBER(18,6)         not null,
   direction          NUMBER(2,0)          not null,
   aunitprice         NUMBER(18,6)         not null,
   constraint PK_XLEM_STOCK primary key (stkid)
);

comment on table xlem_stock is
'电子商务库存表';

/*==============================================================*/
/* Table: xlem_stockhistory                                   */
/*==============================================================*/
create table xlem_stockhistory 
(
   stkid              NUMBER(16,0)         not null,
   sku                VARCHAR2(32),
   quantity           NUMBER(18,6),
   direction          NUMBER(2,0),
   aunitprice         NUMBER(18,6),
   constraint PK_XLEM_STOCKHISTORY primary key (stkid)
);

comment on table xlem_stockhistory is
'库存历史记录表';

/*==============================================================*/
/* Table: xlem_synonym                                        */
/*==============================================================*/
create table xlem_synonym 
(
   mergehash          NUMBER(8,0)          not null,
   srcword            VARCHAR2(32)         not null,
   synword            VARCHAR2(32)         not null,
   heat               NUMBER(8,0),
   constraint PK_XLEM_SYNONYM primary key (mergehash)
);

comment on table xlem_synonym is
'同义词表';

comment on column xlem_synonym.mergehash is
'原词和同义词经过排序连接后的hash值, 防止重复添加同义词';

comment on column xlem_synonym.heat is
'该同义词的使用热度';

/*==============================================================*/
/* Table: xlem_unit                                           */
/*==============================================================*/
create table xlem_unit 
(
   unitid             VARCHAR2(32)         not null,
   aunitid            VARCHAR2(32)         not null,
   name               VARCHAR2(256),
   exchangerate       NUMBER(18,6)         not null,
   constraint PK_XLEM_UNIT primary key (unitid)
);

comment on table xlem_unit is
'单位表';

comment on column xlem_unit.exchangerate is
'单位对原子单位的兑换率';

/*==============================================================*/
/* Table: xlem_user                                           */
/*==============================================================*/
create table xlem_user 
(
   userid             VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   constraint PK_XLEM_USER primary key (userid)
);

comment on table xlem_user is
'电子商城用户表, 主要记录用户基本资料';

/*==============================================================*/
/* Table: xlem_userlevel                                      */
/*==============================================================*/
create table xlem_userlevel 
(
   levelid            VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   nextlevel          VARCHAR2(256),
   exprequire         NUMBER(18,6),
   constraint PK_XLEM_USERLEVEL primary key (levelid)
);

comment on table xlem_userlevel is
'等级定义表';

/*==============================================================*/
/* Table: xlfin_account                                       */
/*==============================================================*/
create table xlfin_account 
(
   accid              VARCHAR2(256)        not null,
   name               VARCHAR2(1024),
   adc                NUMBER(2,0),
   vdc                NUMBER(2,0),
   acctype            NUMBER(2,0),
   forbankstmt        NUMBER(2,0),
   constraint PK_XLFIN_ACCOUNT primary key (accid)
);

comment on table xlfin_account is
'科目表';

/*==============================================================*/
/* Table: xlfin_accountcondition                              */
/*==============================================================*/
create table xlfin_accountcondition 
(
   kdeptid            VARCHAR2(256)        not null,
   year               NUMBER(8,0)          not null,
   month              NUMBER(2,0)          not null,
   condition          VARCHAR2(256),
   constraint PK_XLFIN_ACCOUNTCONDITION primary key (kdeptid, year, month)
);

comment on table xlfin_accountcondition is
'记账状态记录表';

comment on column xlfin_accountcondition.condition is
'400:已记账;800:已结账';

/*==============================================================*/
/* Table: xlfin_accountingitem                                */
/*==============================================================*/
create table xlfin_accountingitem 
(
   vdcol              VARCHAR2(256)        not null,
   kdeptids           VARCHAR2(4000),
   accids             VARCHAR2(4000),
   nasm               NUMBER(2,0),
   dbcol              VARCHAR2(64),
   cbcol              VARCHAR2(64),
   bcol               VARCHAR2(64),
   dvkdeptids         VARCHAR2(4000),
   dvaccids           VARCHAR2(4000),
   constraint PK_XLFIN_ACCOUNTINGITEM primary key (vdcol)
);

comment on table xlfin_accountingitem is
'核算项设置';

comment on column xlfin_accountingitem.vdcol is
'凭证明细字段';

comment on column xlfin_accountingitem.nasm is
'Non accounting statistics method
非核算项的统计方式
1:sum;2:max;3:min;4:avg';

comment on column xlfin_accountingitem.dbcol is
'当vdc=1时统计放入balance表的字段';

comment on column xlfin_accountingitem.cbcol is
'当vdc=-1时统计放入balance的字段';

comment on column xlfin_accountingitem.bcol is
'忽略vdc时统计放入balance表的字段';

comment on column xlfin_accountingitem.dvkdeptids is
'使用该字段进行往来核销的记账部门';

comment on column xlfin_accountingitem.dvaccids is
'使用该字段进行往来核销的科目';

/*==============================================================*/
/* Table: xlfin_balance                                       */
/*==============================================================*/
create table xlfin_balance 
(
   balanceid          VARCHAR2(256)        not null,
   year               NUMBER(8,0),
   month              NUMBER(2,0),
   kdeptid            VARCHAR2(256),
   fcrcid             VARCHAR2(256),
   accid              VARCHAR2(256),
   dfca               NUMBER(18,6),
   cfca               NUMBER(18,6),
   fcb                NUMBER(18,6),
   dsca               NUMBER(18,6),
   csca               NUMBER(18,6),
   scb                NUMBER(18,6),
   drca               NUMBER(18,6),
   crca               NUMBER(18,6),
   rcb                NUMBER(18,6),
   dusda              NUMBER(18,6),
   cusda              NUMBER(18,6),
   usdb               NUMBER(18,6),
   dquantitya         NUMBER(18,6),
   cquantitya         NUMBER(18,6),
   quantityb          NUMBER(18,6),
   constraint PK_XLFIN_BALANCE primary key (balanceid)
);

comment on table xlfin_balance is
'余额表';

/*==============================================================*/
/* Table: xlfin_balanceitem                                   */
/*==============================================================*/
create table xlfin_balanceitem 
(
   bcol               VARCHAR2(64)         not null,
   operatormode       NUMBER(2,0),
   constraint PK_XLFIN_BALANCEITEM primary key (bcol)
);

comment on table xlfin_balanceitem is
'余额表项的配置';

comment on column xlfin_balanceitem.operatormode is
'balance column operator mode
余额表列的运算方式
1:add;2:max;3:min;4:cover';

/*==============================================================*/
/* Table: xlfin_bankstmt                                      */
/*==============================================================*/
create table xlfin_bankstmt 
(
   bsid               VARCHAR2(256)        not null,
   year               NUMBER(8,0),
   month              NUMBER(2,0),
   tradedate          DATE,
   kdeptid            VARCHAR2(256),
   userid             VARCHAR2(256),
   accid              VARCHAR2(256),
   bdc                NUMBER(2,0),
   fcrcid             VARCHAR2(256),
   fcrcamount         NUMBER(18,6),
   remark             VARCHAR2(256),
   vid                VARCHAR2(256),
   vidx               NUMBER(8,0),
   bcdate             DATE,
   bctype             NUMBER(2,0),
   constraint PK_XLFIN_BANKSTMT primary key (bsid)
);

comment on table xlfin_bankstmt is
'银行对账单表';

comment on column xlfin_bankstmt.bcdate is
'对账日期';

comment on column xlfin_bankstmt.bctype is
'对账类型
0:手动;1:自动';

/*==============================================================*/
/* Table: xlfin_bankstmtbalance                               */
/*==============================================================*/
create table xlfin_bankstmtbalance 
(
   bsbid              VARCHAR2(256)        not null,
   kdeptid            VARCHAR2(256),
   fcrcid             VARCHAR2(256),
   accid              VARCHAR2(256),
   year               NUMBER(8,0),
   month              NUMBER(2,0),
   fcb                NUMBER(18,6),
   constraint PK_XLFIN_BANKSTMTBALANCE primary key (bsbid)
);

comment on table xlfin_bankstmtbalance is
'银行对账单余额表';

/*==============================================================*/
/* Table: xlfin_bankstmttemplet                               */
/*==============================================================*/
create table xlfin_bankstmttemplet 
(
   bstlid             VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   javalistener       VARCHAR2(4000),
   jslistener         BLOB,
   constraint PK_XLFIN_BANKSTMTTEMPLET primary key (bstlid)
);

comment on table xlfin_bankstmttemplet is
'银行对账单模板主表';

/*==============================================================*/
/* Table: xlfin_bstldt                                        */
/*==============================================================*/
create table xlfin_bstldt 
(
   bstlid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   templetcol         VARCHAR2(256),
   bscol              VARCHAR2(256),
   constraint PK_XLFIN_BSTLDT primary key (bstlid, idx)
);

comment on table xlfin_bstldt is
'银行对账单模板明细';

/*==============================================================*/
/* Table: xlfin_currency                                      */
/*==============================================================*/
create table xlfin_currency 
(
   crcid              VARCHAR2(256)        not null,
   name               VARCHAR2(128),
   crccode            VARCHAR2(8),
   constraint PK_XLFIN_CURRENCY primary key (crcid)
);

comment on table xlfin_currency is
'币种表';

/*==============================================================*/
/* Table: xlfin_exchangerate                                  */
/*==============================================================*/
create table xlfin_exchangerate 
(
   erid               VARCHAR2(256)        not null,
   crcid              VARCHAR2(256),
   buyingrate         NUMBER(18,6),
   cashbuyingrate     NUMBER(18,6),
   sellingrate        NUMBER(18,6),
   cashsellingrate    NUMBER(18,6),
   middlerate         NUMBER(18,6),
   pubtime            DATE,
   constraint PK_XLFIN_EXCHANGERATE primary key (erid)
);

comment on table xlfin_exchangerate is
'汇率表
http://www.boc.cn/sourcedb/whpj/enindex.html';

/*==============================================================*/
/* Table: xlfin_karaccdt                                      */
/*==============================================================*/
create table xlfin_karaccdt 
(
   karid              VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   accids             VARCHAR2(4000),
   showcolumns        VARCHAR2(4000),
   constraint PK_XLFIN_KARACCDT primary key (karid, idx)
);

comment on table xlfin_karaccdt is
'记账部门科目关系明细配置';

/*==============================================================*/
/* Table: xlfin_kardt                                         */
/*==============================================================*/
create table xlfin_kardt 
(
   karid              VARCHAR2(256)        not null,
   vdtecaid           VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   constraint PK_XLFIN_KARDT primary key (karid, vdtecaid)
);

comment on table xlfin_kardt is
'记账部门对应科目所拥有的字段关系表';

/*==============================================================*/
/* Table: xlfin_kdeptaccrealtion                              */
/*==============================================================*/
create table xlfin_kdeptaccrealtion 
(
   karid              VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   accids             VARCHAR2(4000),
   constraint PK_XLFIN_KDEPTACCREALTION primary key (karid)
);

comment on table xlfin_kdeptaccrealtion is
'科目关系定义主表';

/*==============================================================*/
/* Table: xlfin_keepdept                                      */
/*==============================================================*/
create table xlfin_keepdept 
(
   kdeptid            VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   standardcrcid      VARCHAR2(256),
   reportcrcid        VARCHAR2(256),
   usedkarid          VARCHAR2(256),
   nocarryoverkarid   VARCHAR2(256),
   cockarid           VARCHAR2(256),
   needdcequal        NUMBER(2,0),
   vdatemode          NUMBER(2,0),
   transfermode       NUMBER(2,0),
   beginvdate         DATE,
   kdepttype          NUMBER(2,0),
   beginbsdate        DATE,
   constraint PK_XLFIN_KEEPDEPT primary key (kdeptid)
);

comment on table xlfin_keepdept is
'记账部门';

comment on column xlfin_keepdept.vdatemode is
'凭证日期模式
0:与当前日期同月时使用当前日期
1:与当前日期同月时使用上一次凭证日期';

comment on column xlfin_keepdept.transfermode is
'转账模式
0:转入时合并
1:转入时不合并';

comment on column xlfin_keepdept.beginvdate is
'期初日期，该记账部门的凭证日期只能大于等于该日期';

comment on column xlfin_keepdept.kdepttype is
'记账部门类型, 0:记账部门下属;1:记账部门节点
只有为1时该条记录才是系统使用的真正的记账部门';

/*==============================================================*/
/* Table: xlfin_reportdata                                    */
/*==============================================================*/
create table xlfin_reportdata 
(
   rdid               VARCHAR2(256)        not null,
   rfid               VARCHAR2(256)        not null,
   rdeptid            VARCHAR2(256)        not null,
   timeperiod         NUMBER(2,0),
   begindate          DATE                 not null,
   enddate            DATE                 not null,
   constraint PK_XLFIN_REPORTDATA primary key (rdid)
);

comment on table xlfin_reportdata is
'报表数据';

/*==============================================================*/
/* Index: un_rrbe                                             */
/*==============================================================*/
create unique index un_rrbe on xlfin_reportdata (
   rfid ASC,
   rdeptid ASC,
   begindate ASC,
   enddate ASC
);

/*==============================================================*/
/* Table: xlfin_reportdatadetail                              */
/*==============================================================*/
create table xlfin_reportdatadetail 
(
   rdid               VARCHAR2(256)        not null,
   idx                NUMBER(2,0)          not null,
   rfrowid            VARCHAR2(256)        not null,
   rfcolid            VARCHAR2(256)        not null,
   value              VARCHAR2(4000),
   constraint PK_XLFIN_REPORTDATADETAIL primary key (rdid, idx)
);

comment on table xlfin_reportdatadetail is
'报表数据明细';

/*==============================================================*/
/* Table: xlfin_reportdept                                    */
/*==============================================================*/
create table xlfin_reportdept 
(
   rdeptid            VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   kdeptids           VARCHAR2(4000),
   gatherrdeptids     VARCHAR2(4000),
   constraint PK_XLFIN_REPORTDEPT primary key (rdeptid)
);

comment on table xlfin_reportdept is
'上报单位定义表';

/*==============================================================*/
/* Table: xlfin_reportform                                    */
/*==============================================================*/
create table xlfin_reportform 
(
   rfid               VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   javalistener       VARCHAR2(4000),
   jslistener         BLOB,
   constraint PK_XLFIN_REPORTFORM primary key (rfid)
);

comment on table xlfin_reportform is
'报表定义';

/*==============================================================*/
/* Table: xlfin_reportformcol                                 */
/*==============================================================*/
create table xlfin_reportformcol 
(
   rfid               VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   name               VARCHAR2(64),
   rfcolid            VARCHAR2(256)        not null,
   datatype           NUMBER(2,0)          not null,
   param              VARCHAR2(4000),
   constraint PK_XLFIN_REPORTFORMCOL primary key (rfid, idx)
);

comment on table xlfin_reportformcol is
'报表列定义';

/*==============================================================*/
/* Table: xlfin_reportformformula                             */
/*==============================================================*/
create table xlfin_reportformformula 
(
   rfid               VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   rfrowid            VARCHAR2(256)        not null,
   rfcolid            VARCHAR2(256)        not null,
   formula            BLOB,
   constraint PK_XLFIN_REPORTFORMFORMULA primary key (rfid, idx)
);

comment on table xlfin_reportformformula is
'报表公式定义';

/*==============================================================*/
/* Table: xlfin_reportformrow                                 */
/*==============================================================*/
create table xlfin_reportformrow 
(
   rfid               VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   name               VARCHAR2(64),
   rfrowid            VARCHAR2(256)        not null,
   constraint PK_XLFIN_REPORTFORMROW primary key (rfid, idx)
);

comment on table xlfin_reportformrow is
'报表行定义';

/*==============================================================*/
/* Table: xlfin_vdtextra                                      */
/*==============================================================*/
create table xlfin_vdtextra 
(
   voucherid          VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   extracol           VARCHAR2(256)        not null,
   colvalue           VARCHAR2(4000),
   constraint PK_XLFIN_VDTEXTRA primary key (voucherid, idx, extracol)
);

comment on table xlfin_vdtextra is
'凭证明细附加表';

/*==============================================================*/
/* Table: xlfin_vdtextracol                                   */
/*==============================================================*/
create table xlfin_vdtextracol 
(
   extracol           VARCHAR2(256)        not null,
   datatype           NUMBER(2,0)          not null,
   name               VARCHAR2(64),
   constraint PK_XLFIN_VDTEXTRACOL primary key (extracol)
);

comment on table xlfin_vdtextracol is
'凭证明细可附加字段定义';

/*==============================================================*/
/* Table: xlfin_vdtextracolattr                               */
/*==============================================================*/
create table xlfin_vdtextracolattr 
(
   vdtecaid           VARCHAR2(256)        not null,
   extracol           VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   type               NUMBER(2,0),
   param              VARCHAR2(4000),
   supportvalue       VARCHAR2(4000),
   constraint PK_XLFIN_VDTEXTRACOLATTR primary key (vdtecaid)
);

comment on table xlfin_vdtextracolattr is
'凭证明细附加字段属性定义';

/*==============================================================*/
/* Table: xlfin_voucher                                       */
/*==============================================================*/
create table xlfin_voucher 
(
   voucherid          VARCHAR2(256)        not null,
   flowid             VARCHAR2(256),
   creater            VARCHAR2(256),
   creationdate       DATE,
   modifydate         DATE,
   condition          VARCHAR2(256),
   kdeptid            VARCHAR2(256),
   id                 VARCHAR2(256),
   vno                NUMBER(8,0),
   year               NUMBER(8,0),
   month              NUMBER(2,0),
   vdate              DATE,
   attachno           NUMBER(2,0),
   accounter          VARCHAR2(256),
   creationmode       NUMBER(2,0)          not null,
   constraint PK_XLFIN_VOUCHER primary key (voucherid)
);

comment on table xlfin_voucher is
'凭证表';

comment on column xlfin_voucher.creationmode is
'凭证生成模式
0:手动生成;90:年初结转的未达账数据;91:年初结转的往来未核销数据;1~89:业务各接口数据;90~99:财务自动生成数据';

/*==============================================================*/
/* Table: xlfin_voucherdetail                                 */
/*==============================================================*/
create table xlfin_voucherdetail 
(
   voucherid          VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   year               NUMBER(8,0),
   month              NUMBER(2,0),
   vno                NUMBER(8,0),
   vdate              DATE,
   kdeptid            VARCHAR2(256),
   vdc                NUMBER(2,0),
   fcrcid             VARCHAR2(256),
   accid              VARCHAR2(256),
   userid             VARCHAR2(256),
   fcrcamount         NUMBER(18,6),
   ftosrate           NUMBER(18,6),
   ftorrate           NUMBER(18,6),
   ftousdrate         NUMBER(18,6),
   quantity           NUMBER(18,6),
   remark             VARCHAR2(256),
   bsid               VARCHAR2(256),
   bcdate             DATE,
   bctype             NUMBER(2,0),
   constraint PK_XLFIN_VOUCHERDETAIL primary key (voucherid, idx)
);

comment on table xlfin_voucherdetail is
'凭证明细';

/*==============================================================*/
/* Table: xlfin_vouchertemplet                                */
/*==============================================================*/
create table xlfin_vouchertemplet 
(
   vtlid              VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   javalistener       VARCHAR2(4000),
   jslistener         BLOB,
   querysql           VARCHAR2(4000),
   creationmode       NUMBER(2,0)          not null,
   constraint PK_XLFIN_VOUCHERTEMPLET primary key (vtlid)
);

comment on table xlfin_vouchertemplet is
'凭证模板定义主表';

/*==============================================================*/
/* Table: xlfin_vtempletdt                                    */
/*==============================================================*/
create table xlfin_vtempletdt 
(
   vtlid              VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   name               VARCHAR2(64),
   vrowid             VARCHAR2(256),
   remark             VARCHAR2(256),
   vdc                NUMBER(2,0),
   accid              VARCHAR2(256),
   querysql           VARCHAR2(4000),
   formula            BLOB,
   constraint PK_XLFIN_VTEMPLETDT primary key (idx, vtlid)
);

comment on table xlfin_vtempletdt is
'凭证模板明细配置';

/*==============================================================*/
/* Table: xlfin_vtldtcol                                      */
/*==============================================================*/
create table xlfin_vtldtcol 
(
   vtlid              VARCHAR2(256)        not null,
   vtldtidx           NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   mdscolname         VARCHAR2(256),
   dtdscolname        VARCHAR2(256),
   vdtcolname         VARCHAR2(256),
   constraint PK_XLFIN_VTLDTCOL primary key (vtlid, vtldtidx, idx)
);

comment on table xlfin_vtldtcol is
'凭证模板明细字段配置';

/*==============================================================*/
/* Table: xlsys_attachment                                    */
/*==============================================================*/
create table xlsys_attachment 
(
   attachid           NUMBER(8,0)          not null,
   name               VARCHAR2(256),
   attachdata         BLOB,
   constraint PK_XLSYS_ATTACHMENT primary key (attachid)
);

/*==============================================================*/
/* Table: xlsys_authorisedright                               */
/*==============================================================*/
create table xlsys_authorisedright 
(
   arid               VARCHAR2(256)        not null,
   ardtidx            NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   constraint PK_XLSYS_AUTHORISEDRIGHT primary key (arid, ardtidx, idx)
);

comment on table xlsys_authorisedright is
'被授权权限';

/*==============================================================*/
/* Table: xlsys_authorize                                     */
/*==============================================================*/
create table xlsys_authorize 
(
   arid               VARCHAR2(256)        not null,
   arno               VARCHAR2(256),
   flowid             VARCHAR2(256),
   id                 VARCHAR2(256),
   creater            VARCHAR2(256),
   creationdate       DATE,
   modifydate         DATE,
   condition          VARCHAR2(256),
   authorisedid       VARCHAR2(256),
   begindate          DATE,
   enddate            DATE,
   remark             VARCHAR2(4000),
   constraint PK_XLSYS_AUTHORIZE primary key (arid)
);

comment on table xlsys_authorize is
'授权主表';

comment on column xlsys_authorize.authorisedid is
'授权身份';

/*==============================================================*/
/* Table: xlsys_authorizedetail                               */
/*==============================================================*/
create table xlsys_authorizedetail 
(
   arid               VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   arflowid           VARCHAR2(256),
   arcondition        VARCHAR2(256),
   beauthorizedid     VARCHAR2(256),
   constraint PK_XLSYS_AUTHORIZEDETAIL primary key (arid, idx)
);

comment on table xlsys_authorizedetail is
'授权明细表';

comment on column xlsys_authorizedetail.arflowid is
'授权流程';

comment on column xlsys_authorizedetail.arcondition is
'授权状态';

comment on column xlsys_authorizedetail.beauthorizedid is
'被授权身份';

/*==============================================================*/
/* Index: un_ad_fci                                           */
/*==============================================================*/
create unique index un_ad_fci on xlsys_authorizedetail (
   arflowid ASC,
   arcondition ASC,
   beauthorizedid ASC
);

/*==============================================================*/
/* Table: xlsys_basebusi                                      */
/*==============================================================*/
create table xlsys_basebusi 
(
   busiid             VARCHAR2(256)        not null,
   busino             VARCHAR2(256),
   name               VARCHAR2(64),
   flowid             VARCHAR2(256)        not null,
   id                 VARCHAR2(256),
   creater            VARCHAR2(256),
   creationdate       DATE                 not null,
   modifydate         DATE                 not null,
   condition          VARCHAR2(256)        not null,
   constraint PK_XLSYS_BASEBUSI primary key (busiid)
);

/*==============================================================*/
/* Table: xlsys_codealloc                                     */
/*==============================================================*/
create table xlsys_codealloc 
(
   caid               VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   clientjavascript   BLOB,
   clientjavamethod   VARCHAR2(4000),
   serverjavascript   BLOB,
   serverjavamethod   VARCHAR2(4000),
   constraint PK_XLSYS_CODEALLOC primary key (caid)
);

comment on table xlsys_codealloc is
'define alloc code method
select ''insert into xlsys_codealloc(caid,name,javamethod) values(''''''||caid||'''''',''''''||name||'''''',''''''||javamethod||'''''');'' from xlsys_codealloc;';

/*==============================================================*/
/* Table: xlsys_db                                            */
/*==============================================================*/
create table xlsys_db 
(
   dbid               NUMBER(8,0)          not null,
   name               VARCHAR2(256)        not null,
   datasource         VARCHAR2(512),
   username           VARCHAR2(256),
   password           VARCHAR2(256),
   corepoolsize       NUMBER(8,0),
   maximumpoolsize    NUMBER(8,0),
   keepalivetime      NUMBER(8,0),
   queuecapacity      NUMBER(8,0),
   constraint PK_XLSYS_DB primary key (dbid)
);

comment on table xlsys_db is
'数据库连接表. 该表记录了可访问的数据库连接信息';

/*==============================================================*/
/* Table: xlsys_department                                    */
/*==============================================================*/
create table xlsys_department 
(
   deptid             VARCHAR2(256)        not null,
   name               VARCHAR2(64)         not null,
   constraint PK_XLSYS_DEPARTMENT primary key (deptid)
);

comment on table xlsys_department is
'Department table';

/*==============================================================*/
/* Table: xlsys_dict                                          */
/*==============================================================*/
create table xlsys_dict 
(
   dictid             VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   constraint PK_XLSYS_DICT primary key (dictid)
);

comment on table xlsys_dict is
'The dictionary of xlsys';

/*==============================================================*/
/* Table: xlsys_dictdetail                                    */
/*==============================================================*/
create table xlsys_dictdetail 
(
   dictid             VARCHAR2(256)        not null,
   code               VARCHAR2(64)         not null,
   name               VARCHAR2(256),
   constraint PK_XLSYS_DICTDETAIL primary key (dictid, code)
);

/*==============================================================*/
/* Table: xlsys_emailtemplate                                 */
/*==============================================================*/
create table xlsys_emailtemplate 
(
   etid               VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   template           BLOB,
   javalistener       VARCHAR2(4000),
   jslistener         BLOB,
   constraint PK_XLSYS_EMAILTEMPLATE primary key (etid)
);

comment on table xlsys_emailtemplate is
'Email模板定义';

/*==============================================================*/
/* Table: xlsys_env                                           */
/*==============================================================*/
create table xlsys_env 
(
   envid              VARCHAR2(256)        not null,
   name               VARCHAR2(256)        not null,
   constraint PK_XLSYS_ENV primary key (envid)
);

comment on table xlsys_env is
'系统环境表. 该表记录了系统可登陆的环境';

/*==============================================================*/
/* Table: xlsys_envdetail                                     */
/*==============================================================*/
create table xlsys_envdetail 
(
   envid              VARCHAR2(256)        not null,
   tableregex         VARCHAR2(256)        not null,
   dbid               NUMBER(8,0)          not null,
   name               VARCHAR2(256),
   constraint PK_XLSYS_ENVDETAIL primary key (envid, dbid, tableregex)
);

comment on table xlsys_envdetail is
'系统环境明细表. 该表主要记录每个环境下哪些表使用哪些数据库去访问';

comment on column xlsys_envdetail.tableregex is
'表名匹配表达式, 只能写一个, 可使用正则表达式';

/*==============================================================*/
/* Table: xlsys_extracmd                                      */
/*==============================================================*/
create table xlsys_extracmd 
(
   extracmd           VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   dispatchpath       VARCHAR2(256),
   javalistener       VARCHAR2(4000),
   jslistener         BLOB,
   constraint PK_XLSYS_EXTRACMD primary key (extracmd)
);

/*==============================================================*/
/* Table: xlsys_exttableinfo                                  */
/*==============================================================*/
create table xlsys_exttableinfo 
(
   tableid            NUMBER(8,0)          not null,
   name               VARCHAR2(64),
   tablename          VARCHAR2(256),
   constraint PK_XLSYS_EXTTABLEINFO primary key (tableid)
);

comment on table xlsys_exttableinfo is
'Exter table information';

/*==============================================================*/
/* Table: xlsys_exttableinfodetail                            */
/*==============================================================*/
create table xlsys_exttableinfodetail 
(
   tableid            NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   colname            VARCHAR2(64),
   name               VARCHAR2(64),
   primarykey         NUMBER(2,0),
   nullable           NUMBER(2,0),
   constraint PK_XLSYS_EXTTABLEINFODETAIL primary key (tableid, idx)
);

comment on table xlsys_exttableinfodetail is
'The detail of exter table information';

/*==============================================================*/
/* Table: xlsys_flow                                          */
/*==============================================================*/
create table xlsys_flow 
(
   flowid             VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   listpartid         VARCHAR2(256),
   mvidoflpart        NUMBER(8,0),
   mainpartid         VARCHAR2(256),
   mvidofmpart        NUMBER(8,0),
   maintable          VARCHAR2(64),
   innercodecol       VARCHAR2(64),
   outtercodecol      VARCHAR2(64),
   jslistener         BLOB,
   javalistener       VARCHAR2(4000),
   versionupdate      NUMBER(2,0),
   cancopy            NUMBER(2,0),
   constraint PK_XLSYS_FLOW primary key (flowid)
);

comment on table xlsys_flow is
'Flow Define
select ''insert into XLSYS_FLOW(FLOWID,NAME,LISTPARTID,MAINPARTID,MAINTABLE,INNERCODECOL,OUTTERCODECOL,JAVALISTENER) values(''''''||FLOWID||'''''',''''''||name||'''''',''''''||LISTPARTID||'''''',''''''||MAINPARTID||'''''',''''''||MAINTABLE||'''''',''''''||INNERCODECOL||'''''',''''''||OUTTERCODECOL||'''''',''''''||JAVALISTENER||'''''');'' from XLSYS_FLOW;';

comment on column xlsys_flow.versionupdate is
'是否允许版本更新';

/*==============================================================*/
/* Table: xlsys_flowcodealloc                                 */
/*==============================================================*/
create table xlsys_flowcodealloc 
(
   fcaid              VARCHAR2(256)        not null,
   flowid             VARCHAR2(256),
   tablename          VARCHAR2(64)         not null,
   colname            VARCHAR2(64)         not null,
   caid               VARCHAR2(256),
   constraint PK_XLSYS_FLOWCODEALLOC primary key (fcaid)
);

comment on table xlsys_flowcodealloc is
'define code creation for flow';

/*==============================================================*/
/* Table: xlsys_flowcondition                                 */
/*==============================================================*/
create table xlsys_flowcondition 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   condition          VARCHAR2(64)         not null,
   name               VARCHAR2(256),
   audittype          NUMBER(2,0),
   voterate           NUMBER(18,6),
   constraint PK_XLSYS_FLOWCONDITION primary key (flowid, idx)
);

comment on table xlsys_flowcondition is
'The condition of flow';

comment on column xlsys_flowcondition.audittype is
'审批类型, 0:单审;1:会审;2:组单审;3:组会审;4:投票审
[单审] : 任意一个人通过即可通过(提交时允许选择审批人)
[会审] : 所有人通过才可通过(提交时不允许选择审批人)
[组单审] : 任意一组人通过即可通过, 同一组里的人必须全部通过才算通过(提交时允许选择审批组，不允许选择审批人)
[组会审] : 所有组的人都通过才可通过, 同一组里的人只有要任意一个人通过就算通过(提交时允许选择审批人，但是每个组都必须选择至少一个审批人)
[投票审] : 按照一定比例票数通过后即可通过(提交时不允许选择)';

comment on column xlsys_flowcondition.voterate is
'投票率，当audittype为4:投票审时，此参数有效';

/*==============================================================*/
/* Table: xlsys_flowjava                                      */
/*==============================================================*/
create table xlsys_flowjava 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   viewid             NUMBER(8,0),
   javalistener       VARCHAR2(4000),
   constraint PK_XLSYS_FLOWJAVA primary key (flowid, idx)
);

comment on table xlsys_flowjava is
'JavaListener for viewers of flow';

/*==============================================================*/
/* Table: xlsys_flowjs                                        */
/*==============================================================*/
create table xlsys_flowjs 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   viewid             NUMBER(8,0),
   jslistener         BLOB,
   constraint PK_XLSYS_FLOWJS primary key (flowid, idx)
);

comment on table xlsys_flowjs is
'JsListener for viewers of flow ';

/*==============================================================*/
/* Table: xlsys_flowlogic                                     */
/*==============================================================*/
create table xlsys_flowlogic 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   fromcondition      VARCHAR2(64),
   tocondition        VARCHAR2(64),
   passtype           NUMBER(2,0),
   rejecttype         NUMBER(2,0),
   canrejectto        VARCHAR2(64),
   clientautotriggersubmit NUMBER(2,0),
   constraint PK_XLSYS_FLOWLOGIC primary key (flowid, idx)
);

comment on table xlsys_flowlogic is
'The logic of each flow';

comment on column xlsys_flowlogic.passtype is
'0:手动;1:自动提交;2:自动审批;3:自动审批并自动提交';

comment on column xlsys_flowlogic.rejecttype is
'驳回类型,0:不允许驳回;1:可驳回到上一级;2:可驳回到任意上级;3:只允许驳回到自定义上级';

comment on column xlsys_flowlogic.clientautotriggersubmit is
'是否允许客户端自动触发提交按钮';

/*==============================================================*/
/* Table: xlsys_flowpart                                      */
/*==============================================================*/
create table xlsys_flowpart 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   clienttype         VARCHAR2(32),
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   listpartid         VARCHAR2(256),
   mvidoflpart        NUMBER(8,0),
   mainpartid         VARCHAR2(256),
   mvidofmpart        NUMBER(8,0),
   constraint PK_XLSYS_FLOWPART primary key (flowid, idx)
);

/*==============================================================*/
/* Table: xlsys_flowright                                     */
/*==============================================================*/
create table xlsys_flowright 
(
   flowid             VARCHAR2(256)        not null,
   cdtidx             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   belongrighttype    NUMBER(2,0),
   belongrightvalue   VARCHAR2(256),
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   groupnm            VARCHAR2(256),
   conditiongrp       VARCHAR2(32),
   constraint PK_XLSYS_FLOWRIGHT primary key (flowid, cdtidx, idx)
);

comment on table xlsys_flowright is
'The right of each flow condition';

comment on column xlsys_flowright.belongrighttype is
'0:identity;1:user;2:department;3:position';

comment on column xlsys_flowright.righttype is
'0:identity;1:user;2:department;3:position';

comment on column xlsys_flowright.groupnm is
'分组名称,当audittype选用 2:组单审和 3:组会审 时有效，用来标识当前权限为哪个组别所有，分组名称相同的视为同一组';

comment on column xlsys_flowright.conditiongrp is
'条件组, 组名一样的权限配置将使用and来连接, 否则使用or连接';

/*==============================================================*/
/* Table: xlsys_flowsubtable                                  */
/*==============================================================*/
create table xlsys_flowsubtable 
(
   flowid             VARCHAR2(256)        not null,
   tablename          VARCHAR2(64)         not null,
   relationinnercol   VARCHAR2(64),
   constraint PK_XLSYS_FLOWSUBTABLE primary key (flowid, tablename)
);

comment on table xlsys_flowsubtable is
'流程子表配置';

/*==============================================================*/
/* Table: xlsys_identity                                      */
/*==============================================================*/
create table xlsys_identity 
(
   id                 VARCHAR2(256)        not null,
   name               VARCHAR2(64)         not null,
   welcomemenuid      VARCHAR2(256),
   frameid            NUMBER(8,0),
   closedisable       NUMBER(2,0),
   constraint PK_XLSYS_IDENTITY primary key (id)
);

comment on table xlsys_identity is
'Identity for Xue Lang System, it is unique for Xlsys';

/*==============================================================*/
/* Table: xlsys_idrelation                                    */
/*==============================================================*/
create table xlsys_idrelation 
(
   id                 VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   name               VARCHAR2(64),
   righttype          NUMBER(2,0)          not null,
   relationvalue      VARCHAR2(32),
   constraint PK_XLSYS_IDRELATION primary key (idx, id)
);

comment on table xlsys_idrelation is
'The relationship of Identity.';

/*==============================================================*/
/* Index: xlsys_idrelation_uq                                 */
/*==============================================================*/
create unique index xlsys_idrelation_uq on xlsys_idrelation (
   id ASC,
   righttype ASC,
   relationvalue ASC
);

/*==============================================================*/
/* Index: xlsys_idrelation_tcv                                */
/*==============================================================*/
create index xlsys_idrelation_tcv on xlsys_idrelation (
   righttype ASC,
   relationvalue ASC
);

/*==============================================================*/
/* Table: xlsys_image                                         */
/*==============================================================*/
create table xlsys_image 
(
   imageid            NUMBER(8,0)          not null,
   name               VARCHAR2(256),
   imagedata          BLOB,
   constraint PK_XLSYS_IMAGE primary key (imageid)
);

comment on table xlsys_image is
'图片库';

/*==============================================================*/
/* Table: xlsys_ipresource                                    */
/*==============================================================*/
create table xlsys_ipresource 
(
   ipaddress          VARCHAR2(64)         not null,
   ipresource         BLOB,
   constraint PK_XLSYS_IPRESOURCE primary key (ipaddress)
);

comment on table xlsys_ipresource is
'IP相关资源信息';

/*==============================================================*/
/* Table: xlsys_javaclass                                     */
/*==============================================================*/
create table xlsys_javaclass 
(
   classid            VARCHAR2(512)        not null,
   name               VARCHAR2(64),
   javasource         BLOB,
   javabinary         BLOB,
   constraint PK_XLSYS_JAVACLASS primary key (classid)
);

comment on table xlsys_javaclass is
'The Additional Java Class';

/*==============================================================*/
/* Table: xlsys_menu                                          */
/*==============================================================*/
create table xlsys_menu 
(
   menuid             VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   type               NUMBER(2,0),
   cmd                VARCHAR2(256),
   param              VARCHAR2(4000),
   icon               BLOB,
   shortcut           VARCHAR2(64),
   showninphone       NUMBER(1,0),
   constraint PK_XLSYS_MENU primary key (menuid)
);

comment on table xlsys_menu is
'Define Menu';

/*==============================================================*/
/* Table: xlsys_menuright                                     */
/*==============================================================*/
create table xlsys_menuright 
(
   menuid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   constraint PK_XLSYS_MENURIGHT primary key (menuid, idx)
);

comment on column xlsys_menuright.righttype is
'0:identity;1:user;2:department;3:position';

/*==============================================================*/
/* Table: xlsys_oacategory                                    */
/*==============================================================*/
create table xlsys_oacategory 
(
   oacid              VARCHAR2(256)        not null,
   name               VARCHAR2(256)        not null,
   icon               BLOB,
   constraint PK_XLSYS_OACATEGORY primary key (oacid)
);

comment on table xlsys_oacategory is
'OA类别定义表';

/*==============================================================*/
/* Table: xlsys_oacategoryright                               */
/*==============================================================*/
create table xlsys_oacategoryright 
(
   oacid              VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   constraint PK_XLSYS_OACATEGORYRIGHT primary key (oacid, idx)
);

comment on table xlsys_oacategoryright is
'OA分类权限定义表';

/*==============================================================*/
/* Table: xlsys_oacmbelong                                    */
/*==============================================================*/
create table xlsys_oacmbelong 
(
   oacid              VARCHAR2(256)        not null,
   oamid              VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   constraint PK_XLSYS_OACMBELONG primary key (oacid, oamid)
);

comment on table xlsys_oacmbelong is
'OA分类和OA模块的所属关系表';

/*==============================================================*/
/* Table: xlsys_oacmrelation                                  */
/*==============================================================*/
create table xlsys_oacmrelation 
(
   id                 VARCHAR2(256)        not null,
   idx                NUMBER(2,0)          not null,
   oacid              VARCHAR2(256),
   oamid              VARCHAR2(256),
   name               VARCHAR2(256),
   sheetname          VARCHAR2(256),
   hpercent           NUMBER(4,2),
   vpixel             NUMBER(8,0),
   constraint PK_XLSYS_OACMRELATION primary key (idx, id)
);

comment on table xlsys_oacmrelation is
'OA分类和OA模块的关系定义表';

comment on column xlsys_oacmrelation.sheetname is
'分页名称';

comment on column xlsys_oacmrelation.hpercent is
'横向占百分比';

comment on column xlsys_oacmrelation.vpixel is
'纵向占像素';

/*==============================================================*/
/* Table: xlsys_oamodule                                      */
/*==============================================================*/
create table xlsys_oamodule 
(
   oamid              VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   cmd                VARCHAR2(256),
   param              VARCHAR2(4000),
   constraint PK_XLSYS_OAMODULE primary key (oamid)
);

comment on table xlsys_oamodule is
'OA模块定义表';

/*==============================================================*/
/* Table: xlsys_oamoduleextra                                 */
/*==============================================================*/
create table xlsys_oamoduleextra 
(
   oamid              VARCHAR2(256)        not null,
   viewid             NUMBER(8,0)          not null,
   extcmd             VARCHAR2(256),
   extparam           VARCHAR2(4000),
   constraint PK_XLSYS_OAMODULEEXTRA primary key (oamid, viewid)
);

comment on table xlsys_oamoduleextra is
'OA模块附加窗口配置';

/*==============================================================*/
/* Table: xlsys_oamoduleright                                 */
/*==============================================================*/
create table xlsys_oamoduleright 
(
   oamid              VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   constraint PK_XLSYS_OAMODULERIGHT primary key (oamid, idx)
);

comment on table xlsys_oamoduleright is
'OA模块权限定义表';

/*==============================================================*/
/* Table: xlsys_part                                          */
/*==============================================================*/
create table xlsys_part 
(
   partid             VARCHAR2(256)        not null,
   name               VARCHAR2(64)         not null,
   parttype           NUMBER(2,0)          not null,
   constraint PK_XLSYS_PART primary key (partid)
);

comment on table xlsys_part is
'The Main Table Of Defining Part';

comment on column xlsys_part.parttype is
'0:Node;1:Part';

/*==============================================================*/
/* Table: xlsys_partdetail                                    */
/*==============================================================*/
create table xlsys_partdetail 
(
   partid             VARCHAR2(256)        not null,
   detailid           VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   type               NUMBER(2,0),
   param              VARCHAR2(4000),
   viewid             NUMBER(8,0),
   relationtype       NUMBER(2,0),
   mainviewid         NUMBER(8,0),
   soporder           NUMBER(8,0),
   diyimplement       VARCHAR2(1000),
   constraint PK_XLSYS_PARTDETAIL primary key (partid, detailid)
);

comment on table xlsys_partdetail is
'The detail of part';

comment on column xlsys_partdetail.type is
'0:SashForm;1:TabFolder;2:ExpandBar';

comment on column xlsys_partdetail.diyimplement is
'自定义实现';

/*==============================================================*/
/* Table: xlsys_position                                      */
/*==============================================================*/
create table xlsys_position 
(
   pstid              VARCHAR2(256)        not null,
   name               VARCHAR2(64)         not null,
   constraint PK_XLSYS_POSITION primary key (pstid)
);

comment on table xlsys_position is
'Position';

/*==============================================================*/
/* Table: xlsys_print                                         */
/*==============================================================*/
create table xlsys_print 
(
   printid            VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   printtype          NUMBER(2,0),
   template           BLOB,
   constraint PK_XLSYS_PRINT primary key (printid)
);

comment on table xlsys_print is
'打印模板定义表';

/*==============================================================*/
/* Table: xlsys_queryparamsave                                */
/*==============================================================*/
create table xlsys_queryparamsave 
(
   viewid             NUMBER(8,0)          not null,
   id                 VARCHAR2(256)        not null,
   name               VARCHAR2(64)         not null,
   paramtype          NUMBER(2,0)          not null,
   paramsave          BLOB,
   constraint PK_XLSYS_QUERYPARAMSAVE primary key (viewid, id, name, paramtype)
);

comment on table xlsys_queryparamsave is
'查询参数保存表';

comment on column xlsys_queryparamsave.paramtype is
'参数类型
0:界面查询参数
1:分组参数';

/*==============================================================*/
/* Table: xlsys_ratify                                        */
/*==============================================================*/
create table xlsys_ratify 
(
   rtfid              VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   fromuserid         VARCHAR2(256),
   fromflowid         VARCHAR2(256),
   fromcdtidx         NUMBER(8,0),
   toflowid           VARCHAR2(256),
   tocdtidx           NUMBER(8,0),
   innercode          VARCHAR2(256),
   version            NUMBER(8,0),
   rtfret             NUMBER(2,0),
   rtfdate            DATE,
   constraint PK_XLSYS_RATIFY primary key (rtfid)
);

comment on table xlsys_ratify is
'The situation of ratifing business flow';

comment on column xlsys_ratify.rtfret is
'0:已提交;1:已通过;2:已驳回';

/*==============================================================*/
/* Table: xlsys_ratifydetail                                  */
/*==============================================================*/
create table xlsys_ratifydetail 
(
   rtfid              VARCHAR2(256)        not null,
   touserid           VARCHAR2(256)        not null,
   replaceuserid      VARCHAR2(256),
   rtfret             NUMBER(2,0),
   rtfdesc            VARCHAR2(4000),
   rtfdate            DATE,
   groupnm            VARCHAR(256),
   constraint PK_XLSYS_RATIFYDETAIL primary key (rtfid, touserid)
);

comment on table xlsys_ratifydetail is
'The detail of ratify condition';

comment on column xlsys_ratifydetail.rtfret is
'0:已提交;1:已通过;2:已驳回';

/*==============================================================*/
/* Table: xlsys_right                                         */
/*==============================================================*/
create table xlsys_right 
(
   righttype          NUMBER(2,0)          not null,
   name               VARCHAR2(64),
   sessionkey         VARCHAR2(256)        not null,
   relationtable      VARCHAR2(256)        not null,
   relationcolumn     VARCHAR2(256)        not null,
   constraint PK_XLSYS_RIGHT primary key (righttype)
);

comment on table xlsys_right is
'系统权限定义表';

/*==============================================================*/
/* Index: UN_RIGHT_RC                                           */
/*==============================================================*/
create unique index UN_RIGHT_RC on xlsys_right (
   relationcolumn ASC
);

/*==============================================================*/
/* Index: UN_RIGHT_SK                                           */
/*==============================================================*/
create unique index UN_RIGHT_SK on xlsys_right (
   sessionkey ASC
);

/*==============================================================*/
/* Table: xlsys_test                                          */
/*==============================================================*/
create table xlsys_test 
(
   idx                NUMBER(8,0)          not null,
   name               VARCHAR2(256),
   value              NUMBER(18,6),
   constraint PK_XLSYS_TEST primary key (idx)
);

/*==============================================================*/
/* Table: xlsys_translator                                    */
/*==============================================================*/
create table xlsys_translator 
(
   tablename          VARCHAR2(32)         not null,
   defaultname        VARCHAR2(256)        not null,
   language           VARCHAR2(32)         not null,
   transname          VARCHAR2(256),
   constraint PK_XLSYS_TRANSLATOR primary key (tablename, defaultname, language)
);

comment on table xlsys_translator is
'Language support';

/*==============================================================*/
/* Table: xlsys_transport                                     */
/*==============================================================*/
create table xlsys_transport 
(
   tsid               VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   constraint PK_XLSYS_TRANSPORT primary key (tsid)
);

comment on table xlsys_transport is
'系统传输表, 用来做跨数据库的数据传输';

/*==============================================================*/
/* Table: xlsys_transportdetail                               */
/*==============================================================*/
create table xlsys_transportdetail 
(
   tsid               VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   fromtable          VARCHAR2(64),
   totable            VARCHAR2(64),
   fromsql            VARCHAR2(4000),
   javalistener       VARCHAR2(4000),
   jslistener         BLOB,
   batchcount         NUMBER(8,0),
   cpsmcol            NUMBER(2,0),
   active             NUMBER(2,0),
   constraint PK_XLSYS_TRANSPORTDETAIL primary key (tsid, idx)
);

comment on table xlsys_transportdetail is
'数据传输明细定义';

comment on column xlsys_transportdetail.batchcount is
'批量更新数量';

comment on column xlsys_transportdetail.cpsmcol is
'是否拷贝同名字段';

/*==============================================================*/
/* Table: xlsys_transportdtcolmap                             */
/*==============================================================*/
create table xlsys_transportdtcolmap 
(
   tsid               VARCHAR2(256)        not null,
   tsdtidx            NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   fromcolumn         VARCHAR2(64),
   tocolumn           VARCHAR2(64),
   constraint PK_XLSYS_TRANSPORTDTCOLMAP primary key (tsid, tsdtidx, idx)
);

comment on table xlsys_transportdtcolmap is
'数据传输明细字段对照';

/*==============================================================*/
/* Table: xlsys_transportkey                                  */
/*==============================================================*/
create table xlsys_transportkey 
(
   tskeyid            VARCHAR2(32)         not null,
   name               VARCHAR2(256),
   constraint PK_XLSYS_TRANSPORTKEY primary key (tskeyid)
);

comment on table xlsys_transportkey is
'传输数据的关键码定义';

/*==============================================================*/
/* Table: xlsys_transportkeysynonym                           */
/*==============================================================*/
create table xlsys_transportkeysynonym 
(
   tskeyid            VARCHAR2(32)         not null,
   tablename          VARCHAR2(64)         not null,
   columnname         VARCHAR2(64)         not null,
   constraint PK_XLSYS_TRANSPORTKEYSYNONYM primary key (tskeyid, tablename, columnname)
);

comment on table xlsys_transportkeysynonym is
'传输关键码同义词定义表，主要用来定义哪些表的哪些字段使用该关键码来进行映射';

/*==============================================================*/
/* Table: xlsys_transportmap                                  */
/*==============================================================*/
create table xlsys_transportmap 
(
   tsmapid            VARCHAR2(256)        not null,
   tskeyid            VARCHAR2(32),
   fromdsid           NUMBER(8,0),
   todsid             NUMBER(8,0),
   fromtable          VARCHAR2(64),
   totable            VARCHAR2(64),
   fromcolumn         VARCHAR2(64),
   tocolumn           VARCHAR2(64),
   fromvalue          VARCHAR2(4000),
   tovalue            VARCHAR2(4000),
   syndate            DATE,
   batchno            VARCHAR2(32),
   remark             VARCHAR2(4000),
   otheruse1          VARCHAR2(256),
   otheruse2          VARCHAR2(256),
   otheruse3          VARCHAR2(256),
   constraint PK_XLSYS_TRANSPORTMAP primary key (tsmapid)
);

comment on table xlsys_transportmap is
'数据传输映射表, 可看做数据传输日志, 以及数据在两个系统中的对照表';

/*==============================================================*/
/* Table: xlsys_transportrun                                  */
/*==============================================================*/
create table xlsys_transportrun 
(
   tsrunid            VARCHAR2(256)        not null,
   tsid               VARCHAR2(256),
   fromdsid           NUMBER(8,0),
   todsid             NUMBER(8,0),
   totalthreadcount   NUMBER(8,0),
   threadcount        NUMBER(8,0),
   dataperthread      NUMBER(8,0),
   constraint PK_XLSYS_TRANSPORTRUN primary key (tsrunid)
);

comment on table xlsys_transportrun is
'数据传输运行表';

/*==============================================================*/
/* Table: xlsys_user                                          */
/*==============================================================*/
create table xlsys_user 
(
   userid             VARCHAR2(256)        not null,
   name               VARCHAR2(64)         not null,
   password           VARCHAR2(255),
   constraint PK_XLSYS_USER primary key (userid)
);

comment on table xlsys_user is
'user table';

/*==============================================================*/
/* Table: xlsys_useremail                                     */
/*==============================================================*/
create table xlsys_useremail 
(
   userid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   email              VARCHAR2(64),
   pop                VARCHAR2(64),
   smtp               VARCHAR2(64),
   emailuser          VARCHAR2(64),
   emailpwd           VARCHAR2(64),
   remark             VARCHAR2(4000),
   header             BLOB,
   footer             BLOB,
   constraint PK_XLSYS_USEREMAIL primary key (userid, idx)
);

comment on table xlsys_useremail is
'用户email配置';

comment on column xlsys_useremail.header is
'Email的抬头';

comment on column xlsys_useremail.footer is
'Email的签名';

/*==============================================================*/
/* Table: xlsys_view                                          */
/*==============================================================*/
create table xlsys_view 
(
   viewid             NUMBER(8,0)          not null,
   name               VARCHAR2(64),
   viewtype           NUMBER(2,0),
   param              VARCHAR2(4000),
   relationtype       NUMBER(2,0),
   mainviewid         NUMBER(8,0),
   jslistener         BLOB,
   javalistener       VARCHAR2(1000),
   selectbody         VARCHAR2(1000),
   frombody           VARCHAR2(1000),
   wherebody          VARCHAR2(1000),
   groupbybody        VARCHAR2(1000),
   orderbybody        VARCHAR2(1000),
   wholesql           VARCHAR2(1000),
   treecolname        VARCHAR2(64),
   constraint PK_XLSYS_VIEW primary key (viewid)
);

comment on table xlsys_view is
'Define the composite
select ''insert into XLSYS_VIEW (VIEWID,NAME,VIEWTYPE,PARAM,RELATIONTYPE,MAINVIEWID,JAVALISTENER,SELECTBODY,FROMBODY,WHEREBODY,GROUPBYBODY,ORDERBYBODY,WHOLESQL,TREECOLNAME) values (''||VIEWID||'',''''''||NAME||'''''',''||VIEWTYPE||'',''''''||PARAM||'''''',''||RELATIONTYPE||'',''||MAINVIEWID||'',''''''||JAVALISTENER||'''''',''''''||SELECTBODY||'''''',''''''||FROMBODY||'''''',''''''||WHEREBODY||'''''',''''''||GROUPBYBODY||'''''',''''''||ORDERBYBODY||'''''',''''''||WHOLESQL||'''''',''''''||TREECOLNAME||'''''');'' from XLSYS_VIEW';

/*==============================================================*/
/* Table: xlsys_viewdetail                                    */
/*==============================================================*/
create table xlsys_viewdetail 
(
   viewid             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   colname            VARCHAR2(64),
   name               VARCHAR2(64),
   colgroup           VARCHAR2(64),
   colgroupname       VARCHAR2(64),
   datatype           NUMBER(2,0),
   type               NUMBER(2,0),
   defaultvalue       VARCHAR2(256),
   supportvalue       VARCHAR2(4000),
   aggregation        NUMBER(2,0),
   relationcolname    VARCHAR2(64),
   sqlexp             VARCHAR2(4000),
   showninphoneoverview NUMBER(1,0),
   showninphonedetail NUMBER(1,0),
   constraint PK_XLSYS_VIEWDETAIL primary key (viewid, idx)
);

comment on table xlsys_viewdetail is
'Detail of view';

/*==============================================================*/
/* Index: un_vd                                               */
/*==============================================================*/
create unique index un_vd on xlsys_viewdetail (
   viewid ASC,
   colname ASC
);

/*==============================================================*/
/* Table: xlsys_viewdetailparam                               */
/*==============================================================*/
create table xlsys_viewdetailparam 
(
   viewid             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   attrname           VARCHAR2(1000)       not null,
   attrvalue          VARCHAR2(4000),
   constraint PK_XLSYS_VIEWDETAILPARAM primary key (viewid, idx, attrname)
);

/*==============================================================*/
/* Table: xlsys_viewqueryparam                                */
/*==============================================================*/
create table xlsys_viewqueryparam 
(
   viewid             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   colname            VARCHAR2(64),
   name               VARCHAR2(64),
   datatype           NUMBER(2,0),
   type               NUMBER(2,0),
   param              VARCHAR2(4000),
   defaultvalue       VARCHAR2(256),
   supportvalue       VARCHAR2(4000),
   showninphone       NUMBER(1,0),
   constraint PK_XLSYS_VIEWQUERYPARAM primary key (viewid, idx)
);

comment on table xlsys_viewqueryparam is
'Query parameter for view';

/*==============================================================*/
/* Table: xlsys_wdtcolumn                                     */
/*==============================================================*/
create table xlsys_wdtcolumn 
(
   wizardid           VARCHAR2(256)        not null,
   dtidx              NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   colname            VARCHAR2(64),
   name               VARCHAR2(64),
   forceinput         NUMBER(2,0),
   tooltip            VARCHAR2(256),
   constraint PK_XLSYS_WDTCOLUMN primary key (wizardid, dtidx, idx)
);

comment on table xlsys_wdtcolumn is
'向导页对应字段';

/*==============================================================*/
/* Table: xlsys_wizard                                        */
/*==============================================================*/
create table xlsys_wizard 
(
   wizardid           VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   javalistener       VARCHAR2(4000),
   jslistener         BLOB,
   constraint PK_XLSYS_WIZARD primary key (wizardid)
);

comment on table xlsys_wizard is
'雪狼系统向导主表';

/*==============================================================*/
/* Table: xlsys_wizarddetail                                  */
/*==============================================================*/
create table xlsys_wizarddetail 
(
   wizardid           VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   viewid             NUMBER(8,0),
   title              VARCHAR2(256),
   message            VARCHAR2(4000),
   needsave           NUMBER(2,0),
   nextidx            NUMBER(8,0),
   constraint PK_XLSYS_WIZARDDETAIL primary key (wizardid, idx)
);

comment on table xlsys_wizarddetail is
'雪狼系统向导明细表';

comment on column xlsys_wizarddetail.viewid is
'对应视图ID';

comment on column xlsys_wizarddetail.nextidx is
'下一步序号';

/*==============================================================*/
/* Table: xlv2_authorisedright                                */
/*==============================================================*/
create table xlv2_authorisedright 
(
   arid               VARCHAR2(256)        not null,
   ardtidx            NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   constraint PK_XLV2_AUTHORISEDRIGHT primary key (arid, ardtidx, idx)
);

comment on table xlv2_authorisedright is
'被授权权限';

/*==============================================================*/
/* Table: xlv2_authorize                                      */
/*==============================================================*/
create table xlv2_authorize 
(
   arid               VARCHAR2(256)        not null,
   arno               VARCHAR2(256),
   flowid             VARCHAR2(256),
   id                 VARCHAR2(256),
   creater            VARCHAR2(256),
   creationdate       DATE,
   modifydate         DATE,
   condition          VARCHAR2(256),
   authorisedid       VARCHAR2(256),
   begindate          DATE,
   enddate            DATE,
   remark             VARCHAR2(4000),
   constraint PK_XLV2_AUTHORIZE primary key (arid)
);

comment on table xlv2_authorize is
'授权主表';

comment on column xlv2_authorize.authorisedid is
'授权身份';

/*==============================================================*/
/* Table: xlv2_authorizedetail                                */
/*==============================================================*/
create table xlv2_authorizedetail 
(
   arid               VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   arflowid           VARCHAR2(256),
   arcondition        VARCHAR2(256),
   beauthorizedid     VARCHAR2(256),
   constraint PK_XLV2_AUTHORIZEDETAIL primary key (arid, idx)
);

comment on table xlv2_authorizedetail is
'授权明细表';

comment on column xlv2_authorizedetail.arflowid is
'授权流程';

comment on column xlv2_authorizedetail.arcondition is
'授权状态';

comment on column xlv2_authorizedetail.beauthorizedid is
'被授权身份';

/*==============================================================*/
/* Index: un_ad_fci2                                          */
/*==============================================================*/
create unique index un_ad_fci2 on xlv2_authorizedetail (
   arflowid ASC,
   arcondition ASC,
   beauthorizedid ASC
);

/*==============================================================*/
/* Table: xlv2_codealloc                                      */
/*==============================================================*/
create table xlv2_codealloc 
(
   caid               VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   clientscript       BLOB,
   clientinnermethod  VARCHAR2(1000),
   serverscript       BLOB,
   serverinnermethod  VARCHAR2(1000),
   constraint PK_XLV2_CODEALLOC primary key (caid)
);

comment on table xlv2_codealloc is
'define alloc code method';

/*==============================================================*/
/* Table: xlv2_control                                        */
/*==============================================================*/
create table xlv2_control 
(
   controlid          NUMBER(8,0)          not null,
   name               VARCHAR2(32)         not null,
   implclass          VARCHAR2(256)        not null,
   constraint PK_XLV2_CONTROL primary key (controlid)
);

comment on table xlv2_control is
'雪狼2.x系统的控件定义表';

/*==============================================================*/
/* Table: xlv2_flow                                           */
/*==============================================================*/
create table xlv2_flow 
(
   flowid             VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   flowcode           VARCHAR2(256)        not null,
   maintable          VARCHAR2(64),
   innercodecol       VARCHAR2(64),
   outtercodecol      VARCHAR2(64),
   innerlisteners     VARCHAR2(4000),
   scriptlistener     BLOB,
   versionupdate      NUMBER(2,0),
   cancopy            NUMBER(2,0),
   constraint PK_XLV2_FLOW primary key (flowid)
);

comment on table xlv2_flow is
'雪狼系统2.x流程定义主表';

comment on column xlv2_flow.versionupdate is
'是否允许版本更新';

/*==============================================================*/
/* Table: xlv2_flowcodealloc                                  */
/*==============================================================*/
create table xlv2_flowcodealloc 
(
   fcaid              VARCHAR2(256)        not null,
   flowid             VARCHAR2(256),
   tablename          VARCHAR2(64)         not null,
   colname            VARCHAR2(64)         not null,
   caid               VARCHAR2(256),
   constraint PK_XLV2_FLOWCODEALLOC primary key (fcaid)
);

comment on table xlv2_flowcodealloc is
'define code creation for flow';

/*==============================================================*/
/* Table: xlv2_flowcondition                                  */
/*==============================================================*/
create table xlv2_flowcondition 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   condition          VARCHAR2(64)         not null,
   name               VARCHAR2(256),
   audittype          NUMBER(2,0),
   voterate           NUMBER(18,6),
   constraint PK_XLV2_FLOWCONDITION primary key (flowid, idx)
);

comment on table xlv2_flowcondition is
'The condition of flow';

comment on column xlv2_flowcondition.audittype is
'审批类型, 0:单审;1:会审;2:组单审;3:组会审;4:投票审
[单审] : 任意一个人通过即可通过(提交时允许选择审批人)
[会审] : 所有人通过才可通过(提交时不允许选择审批人)
[组单审] : 任意一组人通过即可通过, 同一组里的人必须全部通过才算通过(提交时允许选择审批组，不允许选择审批人)
[组会审] : 所有组的人都通过才可通过, 同一组里的人只有要任意一个人通过就算通过(提交时允许选择审批人，但是每个组都必须选择至少一个审批人)
[投票审] : 按照一定比例票数通过后即可通过(提交时不允许选择)';

comment on column xlv2_flowcondition.voterate is
'投票率，当audittype为4:投票审时，此参数有效';

/*==============================================================*/
/* Table: xlv2_flowframe                                      */
/*==============================================================*/
create table xlv2_flowframe 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   platform           NUMBER(2,0),
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   listframeid        NUMBER(8,0),
   mainviewidinlistframe NUMBER(8,0),
   mainframeid        NUMBER(8,0),
   mainviewidinmainframe NUMBER(8,0),
   constraint PK_XLV2_FLOWFRAME primary key (flowid, idx)
);

/*==============================================================*/
/* Table: xlv2_flowlogic                                      */
/*==============================================================*/
create table xlv2_flowlogic 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   fromcondition      VARCHAR2(64),
   tocondition        VARCHAR2(64),
   passtype           NUMBER(2,0),
   rejecttype         NUMBER(2,0),
   canrejectto        VARCHAR2(64),
   clientautotriggersubmit NUMBER(2,0),
   constraint PK_XLV2_FLOWLOGIC primary key (flowid, idx)
);

comment on table xlv2_flowlogic is
'The logic of each flow';

comment on column xlv2_flowlogic.passtype is
'0:手动;1:自动提交;2:自动审批;3:自动审批并自动提交';

comment on column xlv2_flowlogic.rejecttype is
'驳回类型,0:不允许驳回;1:可驳回到上一级;2:可驳回到任意上级;3:只允许驳回到自定义上级';

comment on column xlv2_flowlogic.clientautotriggersubmit is
'是否允许客户端自动触发提交按钮';

/*==============================================================*/
/* Table: xlv2_flowright                                      */
/*==============================================================*/
create table xlv2_flowright 
(
   flowid             VARCHAR2(256)        not null,
   cdtidx             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   belongrighttype    NUMBER(2,0),
   righttype          NUMBER(2,0),
   belongrightvalue   VARCHAR2(256),
   rightvalue         VARCHAR2(256),
   groupnm            VARCHAR2(256),
   conditiongrp       VARCHAR2(32),
   constraint PK_XLV2_FLOWRIGHT primary key (flowid, cdtidx, idx)
);

comment on table xlv2_flowright is
'The right of each flow condition';

comment on column xlv2_flowright.belongrighttype is
'0:identity;1:user;2:department;3:position';

comment on column xlv2_flowright.groupnm is
'分组名称,当audittype选用 2:组单审和 3:组会审 时有效，用来标识当前权限为哪个组别所有，分组名称相同的视为同一组';

comment on column xlv2_flowright.conditiongrp is
'条件组, 组名一样的权限配置将使用and来连接, 否则使用or连接';

/*==============================================================*/
/* Table: xlv2_flowsubtable                                   */
/*==============================================================*/
create table xlv2_flowsubtable 
(
   flowid             VARCHAR2(256)        not null,
   tablename          VARCHAR2(64)         not null,
   relationinnercol   VARCHAR2(64),
   constraint PK_XLV2_FLOWSUBTABLE primary key (flowid, tablename)
);

comment on table xlv2_flowsubtable is
'流程子表配置';

/*==============================================================*/
/* Table: xlv2_flowviewlistener                               */
/*==============================================================*/
create table xlv2_flowviewlistener 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   viewid             NUMBER(8,0),
   innerlisteners     VARCHAR2(4000),
   scriptlistener     BLOB,
   constraint PK_XLV2_FLOWVIEWLISTENER primary key (flowid, idx)
);

comment on table xlv2_flowviewlistener is
'Listener for view of flow';

/*==============================================================*/
/* Table: xlv2_frame                                          */
/*==============================================================*/
create table xlv2_frame 
(
   frameid            NUMBER(8,0)          not null,
   name               VARCHAR2(256),
   innerlisteners     VARCHAR2(1000),
   scriptlistener     BLOB,
   constraint PK_XLV2_FRAME primary key (frameid)
);

comment on table xlv2_frame is
'雪狼系统2.x框架界面配置主表';

/*==============================================================*/
/* Table: xlv2_framedetail                                    */
/*==============================================================*/
create table xlv2_framedetail 
(
   frameid            NUMBER(8,0)          not null,
   fdtid              VARCHAR2(256)        not null,
   levelid            VARCHAR2(256)        not null,
   name               VARCHAR2(256),
   uimid              NUMBER(8,0)          not null,
   diyimpl            VARCHAR2(512),
   registname         VARCHAR2(256),
   viewid             NUMBER(8,0),
   constraint PK_XLV2_FRAMEDETAIL primary key (frameid, fdtid)
);

comment on table xlv2_framedetail is
'雪狼2.x系统框架界面配置明细表';

/*==============================================================*/
/* Table: xlv2_framedetailparam                               */
/*==============================================================*/
create table xlv2_framedetailparam 
(
   frameid            NUMBER(8,0)          not null,
   fdtid              VARCHAR2(256)        not null,
   attrname           VARCHAR2(512)        not null,
   attrvalue          VARCHAR2(512),
   constraint PK_XLV2_FRAMEDETAILPARAM primary key (frameid, fdtid, attrname)
);

comment on table xlv2_framedetailparam is
'雪狼2.x系统框架明细参数';

/*==============================================================*/
/* Table: xlv2_frameparam                                     */
/*==============================================================*/
create table xlv2_frameparam 
(
   frameid            NUMBER(8,0)          not null,
   attrname           VARCHAR2(512)        not null,
   attrvalue          VARCHAR2(512),
   constraint PK_XLV2_FRAMEPARAM primary key (frameid, attrname)
);

comment on table xlv2_frameparam is
'雪狼系统2.x框架界面参数';

/*==============================================================*/
/* Table: xlv2_handler                                        */
/*==============================================================*/
create table xlv2_handler 
(
   handlerid          NUMBER(8,0)          not null,
   name               VARCHAR2(32),
   impl               VARCHAR2(512),
   constraint PK_XLV2_HANDLER primary key (handlerid)
);

comment on table xlv2_handler is
'雪狼系统2.x处理机';

/*==============================================================*/
/* Table: xlv2_menu                                           */
/*==============================================================*/
create table xlv2_menu 
(
   menuid             VARCHAR2(256)        not null,
   levelid            VARCHAR2(256)        not null,
   handlerid          NUMBER(8,0),
   name               VARCHAR2(64),
   type               NUMBER(2,0),
   icon               BLOB,
   shortcut           VARCHAR2(64),
   constraint PK_XLV2_MENU primary key (menuid)
);

comment on table xlv2_menu is
'雪狼系统2.x菜单配置';

/*==============================================================*/
/* Table: xlv2_menuhandlerparam                               */
/*==============================================================*/
create table xlv2_menuhandlerparam 
(
   menuid             VARCHAR2(256)        not null,
   attrname           VARCHAR2(512)        not null,
   attrvalue          VARCHAR2(512),
   constraint PK_XLV2_MENUHANDLERPARAM primary key (menuid, attrname)
);

comment on table xlv2_menuhandlerparam is
'雪狼系统2.x菜单处理器参数';

/*==============================================================*/
/* Table: xlv2_menuright                                      */
/*==============================================================*/
create table xlv2_menuright 
(
   menuid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   constraint PK_XLV2_MENURIGHT primary key (menuid, idx)
);

comment on table xlv2_menuright is
'雪狼系统2.x菜单权限';

/*==============================================================*/
/* Table: xlv2_ratify                                         */
/*==============================================================*/
create table xlv2_ratify 
(
   rtfid              VARCHAR2(256)        not null,
   userid             VARCHAR2(256),
   name               VARCHAR2(256),
   fromuserid         VARCHAR2(256),
   fromflowid         VARCHAR2(256),
   fromcdtidx         NUMBER(8,0),
   toflowid           VARCHAR2(256),
   tocdtidx           NUMBER(8,0),
   innercode          VARCHAR2(256),
   version            NUMBER(8,0),
   rtfret             NUMBER(2,0),
   rtfdate            DATE,
   constraint PK_XLV2_RATIFY primary key (rtfid)
);

comment on table xlv2_ratify is
'The situation of ratifing business flow';

comment on column xlv2_ratify.rtfret is
'0:已提交;1:已通过;2:已驳回';

/*==============================================================*/
/* Table: xlv2_ratifydetail                                   */
/*==============================================================*/
create table xlv2_ratifydetail 
(
   rtfid              VARCHAR2(256)        not null,
   touserid           VARCHAR2(256)        not null,
   replaceuserid      VARCHAR2(256),
   rtfret             NUMBER(2,0),
   rtfdesc            VARCHAR2(4000),
   rtfdate            DATE,
   groupnm            VARCHAR(256),
   constraint PK_XLV2_RATIFYDETAIL primary key (rtfid, touserid)
);

comment on table xlv2_ratifydetail is
'The detail of ratify condition';

comment on column xlv2_ratifydetail.rtfret is
'0:已提交;1:已通过;2:已驳回';

/*==============================================================*/
/* Table: xlv2_testbusi                                       */
/*==============================================================*/
create table xlv2_testbusi 
(
   busiid             VARCHAR2(256)        not null,
   busino             VARCHAR2(256),
   name               VARCHAR2(64),
   flowid             VARCHAR2(256)        not null,
   id                 VARCHAR2(256),
   creater            VARCHAR2(256),
   creationdate       DATE                 not null,
   modifydate         DATE                 not null,
   condition          VARCHAR2(256)        not null,
   constraint PK_XLV2_TESTBUSI primary key (busiid)
);

comment on table xlv2_testbusi is
'雪狼系统2.x测试流程单据';

/*==============================================================*/
/* Table: xlv2_testbusisub                                    */
/*==============================================================*/
create table xlv2_testbusisub 
(
   busiid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   name               VARCHAR2(256),
   constraint PK_XLV2_TESTBUSISUB primary key (busiid, idx)
);

/*==============================================================*/
/* Table: xlv2_tool                                           */
/*==============================================================*/
create table xlv2_tool 
(
   toolid             VARCHAR2(256)        not null,
   levelid            VARCHAR2(256)        not null,
   handlerid          NUMBER(8,0),
   name               VARCHAR2(64),
   type               NUMBER(2,0),
   icon               BLOB,
   shortcut           VARCHAR2(64),
   constraint PK_XLV2_TOOL primary key (toolid)
);

comment on table xlv2_tool is
'雪狼系统2.x工具栏配置';

/*==============================================================*/
/* Table: xlv2_toolhandlerparam                               */
/*==============================================================*/
create table xlv2_toolhandlerparam 
(
   toolid             VARCHAR2(256)        not null,
   attrname           VARCHAR2(512)        not null,
   attrvalue          VARCHAR2(512),
   constraint PK_XLV2_TOOLHANDLERPARAM primary key (toolid, attrname)
);

comment on table xlv2_toolhandlerparam is
'雪狼系统2.x工具控制器参数';

/*==============================================================*/
/* Table: xlv2_toolright                                      */
/*==============================================================*/
create table xlv2_toolright 
(
   toolid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   constraint PK_XLV2_TOOLRIGHT primary key (toolid, idx)
);

comment on table xlv2_toolright is
'雪狼系统2.x工具栏权限';

/*==============================================================*/
/* Table: xlv2_uimodule                                       */
/*==============================================================*/
create table xlv2_uimodule 
(
   uimid              NUMBER(8,0)          not null,
   name               VARCHAR2(256),
   defaultimpl        VARCHAR2(512),
   platform           NUMBER(2,0),
   type               NUMBER(2,0),
   constraint PK_XLV2_UIMODULE primary key (uimid)
);

comment on table xlv2_uimodule is
'雪狼2.x系统用户界面模块类型定义';

comment on column xlv2_uimodule.defaultimpl is
'界面模块默认实现类';

comment on column xlv2_uimodule.platform is
'UI对应平台
1:web;2:mobile';

comment on column xlv2_uimodule.type is
'0:widget;1:view;2:dialog;3:cellrenderer';

/*==============================================================*/
/* Table: xlv2_view                                           */
/*==============================================================*/
create table xlv2_view 
(
   viewid             NUMBER(8,0)          not null,
   name               VARCHAR2(256),
   uimid              NUMBER(8,0),
   diyimpl            VARCHAR2(512),
   relationtype       NUMBER(2,0),
   parentviewid       NUMBER(8,0),
   innerlisteners     VARCHAR2(1000),
   scriptlistener     BLOB,
   reflecttable       VARCHAR2(32),
   selectbody         VARCHAR2(1000),
   frombody           VARCHAR2(1000),
   wherebody          VARCHAR2(1000),
   groupbybody        VARCHAR2(1000),
   orderbybody        VARCHAR2(1000),
   wholesql           VARCHAR2(1000),
   treecolname        VARCHAR2(32),
   constraint PK_XLV2_VIEW primary key (viewid)
);

comment on table xlv2_view is
'雪狼系统2.x视图定义';

/*==============================================================*/
/* Table: xlv2_viewcolumn                                     */
/*==============================================================*/
create table xlv2_viewcolumn 
(
   viewid             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   name               VARCHAR2(32),
   colname            VARCHAR2(32),
   sqlexp             VARCHAR2(1000),
   datatype           NUMBER(2,0),
   uimid              NUMBER(8,0),
   diyimpl            VARCHAR2(512),
   defaultvalue       VARCHAR2(256),
   supportvalue       VARCHAR2(1000),
   relationcolname    VARCHAR2(32),
   constraint PK_XLV2_VIEWCOLUMN primary key (viewid, idx)
);

comment on table xlv2_viewcolumn is
'雪狼系统2.x视图列';

/*==============================================================*/
/* Table: xlv2_viewcolumnparam                                */
/*==============================================================*/
create table xlv2_viewcolumnparam 
(
   viewid             NUMBER(8,0)          not null,
   type               NUMBER(2,0)          not null,
   idx                NUMBER(8,0)          not null,
   attrname           VARCHAR2(512)        not null,
   attrvalue          VARCHAR2(512),
   constraint PK_XLV2_VIEWCOLUMNPARAM primary key (viewid, type, idx, attrname)
);

comment on table xlv2_viewcolumnparam is
'雪狼系统2.x视图列参数';

comment on column xlv2_viewcolumnparam.type is
'视图列的属性所属类型.
1:container;2:control';

/*==============================================================*/
/* Table: xlv2_viewparam                                      */
/*==============================================================*/
create table xlv2_viewparam 
(
   viewid             NUMBER(8,0)          not null,
   type               NUMBER(2,0)          not null,
   attrname           VARCHAR2(512)        not null,
   attrvalue          VARCHAR2(512),
   constraint PK_XLV2_VIEWPARAM primary key (viewid, type, attrname)
);

comment on table xlv2_viewparam is
'雪狼系统2.x视图参数';

comment on column xlv2_viewparam.type is
'视图参数类型. 1是作为视图本身的属性类型, 2是查询参数界面Container的属性类型
1:self;2:queryparam';

/*==============================================================*/
/* Table: xlv2_viewqueryparam                                 */
/*==============================================================*/
create table xlv2_viewqueryparam 
(
   viewid             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   name               VARCHAR2(32),
   colname            VARCHAR2(32),
   datatype           NUMBER(2,0),
   uimid              NUMBER(8,0),
   diyimpl            VARCHAR2(512),
   defaultvalue       VARCHAR2(256),
   supportvalue       VARCHAR2(1000),
   constraint PK_XLV2_VIEWQUERYPARAM primary key (viewid, idx)
);

comment on table xlv2_viewqueryparam is
'雪狼系统2.x视图查询参数';

/*==============================================================*/
/* Table: xlv2_viewqueryparamparam                            */
/*==============================================================*/
create table xlv2_viewqueryparamparam 
(
   viewid             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   type               NUMBER(2,0)          not null,
   attrname           VARCHAR2(512)        not null,
   attrvalue          VARCHAR2(512),
   constraint PK_XLV2_VIEWQUERYPARAMPARAM primary key (viewid, idx, type, attrname)
);

comment on table xlv2_viewqueryparamparam is
'雪狼系统2.x视图查询参数的参数';

comment on column xlv2_viewqueryparamparam.type is
'查询组件的参数类型
1:container;2:control';

alter table xlem_buyer
   add constraint FK_BR_REFERENCE_U foreign key (userid)
      references xlem_user (userid);

alter table xlem_buyer
   add constraint FK_BR_REFERENCE_UL foreign key (levelid)
      references xlem_userlevel (levelid);

alter table xlem_item
   add constraint FK_I_REFERENCE_SL foreign key (sellerid)
      references xlem_seller (sellerid);

alter table xlem_itemsku
   add constraint FK_ISKU_REFERENCE_I foreign key (itemid)
      references xlem_item (itemid);

alter table xlem_itemsku
   add constraint FK_ISKU_REFERENCE_SKU foreign key (sku)
      references xlem_sku (sku);

alter table xlem_seller
   add constraint FK_SL_REFERENCE_U foreign key (userid)
      references xlem_user (userid);

alter table xlem_seller
   add constraint FK_SL_REFERENCE_UL foreign key (levelid)
      references xlem_userlevel (levelid);

alter table xlem_sku
   add constraint FK_SKU_REFERENCE_AUNIT foreign key (aunitid)
      references xlem_atomicunit (aunitid);

alter table xlem_sku
   add constraint FK_SKU_REFERENCE_SPU foreign key (spu)
      references xlem_spu (spu);

alter table xlem_spu
   add constraint FK_SPU_REFERENCE_SPUC foreign key (categoryid)
      references xlem_spucategory (categoryid);

alter table xlem_stock
   add constraint FK_STK_REFERENCE_SKU foreign key (sku)
      references xlem_sku (sku);

alter table xlem_stockhistory
   add constraint FK_STKH_REFERENCE_SKU foreign key (sku)
      references xlem_sku (sku);

alter table xlem_unit
   add constraint FK_UNIT_REFERENCE_AUNIT foreign key (aunitid)
      references xlem_atomicunit (aunitid);

alter table xlem_userlevel
   add constraint FK_UL_REFERENCE_UL foreign key (nextlevel)
      references xlem_userlevel (levelid);

alter table xlfin_accountcondition
   add constraint FK_AC_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid);

alter table xlfin_balance
   add constraint FK_B_REFERENCE_A foreign key (accid)
      references xlfin_account (accid);

alter table xlfin_balance
   add constraint FK_B_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid);

alter table xlfin_balance
   add constraint FK_B_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid);

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_A foreign key (accid)
      references xlfin_account (accid);

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid);

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid);

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_U foreign key (userid)
      references xlsys_user (userid);

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_VD foreign key (vid, vidx)
      references xlfin_voucherdetail (voucherid, idx);

alter table xlfin_bankstmtbalance
   add constraint FK_BSB_REFERENCE_A foreign key (accid)
      references xlfin_account (accid);

alter table xlfin_bankstmtbalance
   add constraint FK_BSB_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid);

alter table xlfin_bankstmtbalance
   add constraint FK_BSB_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid);

alter table xlfin_bstldt
   add constraint FK_BSTLDT_REFERENCE_BSTL foreign key (bstlid)
      references xlfin_bankstmttemplet (bstlid);

alter table xlfin_exchangerate
   add constraint FK_ER_REFERENCE_C foreign key (crcid)
      references xlfin_currency (crcid);

alter table xlfin_karaccdt
   add constraint FK_KARADT_REFERENCE_KAR foreign key (karid)
      references xlfin_kdeptaccrealtion (karid);

alter table xlfin_kardt
   add constraint FK_KARDT_REFERENCE_KAR foreign key (karid)
      references xlfin_kdeptaccrealtion (karid);

alter table xlfin_kardt
   add constraint FK_KARDT_REFERENCE_VDTECA foreign key (vdtecaid)
      references xlfin_vdtextracolattr (vdtecaid);

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_C1 foreign key (standardcrcid)
      references xlfin_currency (crcid);

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_C2 foreign key (reportcrcid)
      references xlfin_currency (crcid);

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_KDAR1 foreign key (usedkarid)
      references xlfin_kdeptaccrealtion (karid);

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_KDAR2 foreign key (nocarryoverkarid)
      references xlfin_kdeptaccrealtion (karid);

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_KDAR3 foreign key (cockarid)
      references xlfin_kdeptaccrealtion (karid);

alter table xlfin_reportdata
   add constraint FK_RD_REFERENCE_RDEPT foreign key (rdeptid)
      references xlfin_reportdept (rdeptid);

alter table xlfin_reportdata
   add constraint FK_RD_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid);

alter table xlfin_reportdatadetail
   add constraint FK_RDD_REFERENCE_RD foreign key (rdid)
      references xlfin_reportdata (rdid);

alter table xlfin_reportformcol
   add constraint FK_RFC_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid);

alter table xlfin_reportformformula
   add constraint FK_RFF_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid);

alter table xlfin_reportformrow
   add constraint FK_RFR_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid);

alter table xlfin_vdtextra
   add constraint FK_VDTE_REFERENCE_VDT foreign key (voucherid, idx)
      references xlfin_voucherdetail (voucherid, idx);

alter table xlfin_vdtextra
   add constraint FK_VDTE_REFERENCE_VEC foreign key (extracol)
      references xlfin_vdtextracol (extracol);

alter table xlfin_vdtextracolattr
   add constraint FK_VDTECA_REFERENCE_VDTEC foreign key (extracol)
      references xlfin_vdtextracol (extracol);

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_ID foreign key (id)
      references xlsys_identity (id);

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid);

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_U1 foreign key (creater)
      references xlsys_user (userid);

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_U2 foreign key (accounter)
      references xlsys_user (userid);

alter table xlfin_voucherdetail
   add constraint FK_VD_REFERENCE_A foreign key (accid)
      references xlfin_account (accid);

alter table xlfin_voucherdetail
   add constraint FK_VD_REFERENCE_BS foreign key (bsid)
      references xlfin_bankstmt (bsid);

alter table xlfin_voucherdetail
   add constraint FK_VD_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid);

alter table xlfin_voucherdetail
   add constraint FK_VDT_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid);

alter table xlfin_voucherdetail
   add constraint FK_VDT_REFERENCE_U foreign key (userid)
      references xlsys_user (userid);

alter table xlfin_voucherdetail
   add constraint FK_VOD_REFERENCE_VO foreign key (voucherid)
      references xlfin_voucher (voucherid);

alter table xlfin_vtempletdt
   add constraint FK_VTLDT_REFERENCE_VTL foreign key (vtlid)
      references xlfin_vouchertemplet (vtlid);

alter table xlfin_vtldtcol
   add constraint FK_VTLDTC_REFERENCE_VTLDT foreign key (vtldtidx, vtlid)
      references xlfin_vtempletdt (idx, vtlid);

alter table xlsys_authorisedright
   add constraint FK_AR_REFERENCE_AD foreign key (arid, ardtidx)
      references xlsys_authorizedetail (arid, idx);

alter table xlsys_authorisedright
   add constraint FK_AR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlsys_authorize
   add constraint FK_A_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_authorize
   add constraint FK_A_REFERENCE_I1 foreign key (id)
      references xlsys_identity (id);

alter table xlsys_authorize
   add constraint FK_A_REFERENCE_I2 foreign key (authorisedid)
      references xlsys_identity (id);

alter table xlsys_authorize
   add constraint FK_A_REFERENCE_U foreign key (creater)
      references xlsys_user (userid);

alter table xlsys_authorizedetail
   add constraint FK_AD_REFERENCE_A foreign key (arid)
      references xlsys_authorize (arid);

alter table xlsys_basebusi
   add constraint FK_BB_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_basebusi
   add constraint FK_BB_REFERENCE_I foreign key (id)
      references xlsys_identity (id);

alter table xlsys_basebusi
   add constraint FK_BB_REFERENCE_U foreign key (creater)
      references xlsys_user (userid);

alter table xlsys_dictdetail
   add constraint FK_DD_REFERENCE_D foreign key (dictid)
      references xlsys_dict (dictid);

alter table xlsys_envdetail
   add constraint FK_ED_REFERENCE_D foreign key (dbid)
      references xlsys_db (dbid);

alter table xlsys_envdetail
   add constraint FK_ED_REFERENCE_E foreign key (envid)
      references xlsys_env (envid);

alter table xlsys_exttableinfodetail
   add constraint FK_ETI_REFERENCE_ETID foreign key (tableid)
      references xlsys_exttableinfo (tableid);

alter table xlsys_flow
   add constraint FK_F_REFERENCE_V1 foreign key (mvidoflpart)
      references xlsys_view (viewid);

alter table xlsys_flow
   add constraint FK_F_REFERENCE_V2 foreign key (mvidofmpart)
      references xlsys_view (viewid);

alter table xlsys_flow
   add constraint FK_FL_REFERENCE_P1 foreign key (mainpartid)
      references xlsys_part (partid);

alter table xlsys_flow
   add constraint FK_FL_REFERENCE_P2 foreign key (listpartid)
      references xlsys_part (partid);

alter table xlsys_flowcodealloc
   add constraint FK_FCA_REFERENCE_CA foreign key (caid)
      references xlsys_codealloc (caid);

alter table xlsys_flowcodealloc
   add constraint FK_FCA_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_flowcondition
   add constraint FK_FC_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_flowjava
   add constraint FK_FJAVA_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_flowjava
   add constraint FK_FJAVA_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid);

alter table xlsys_flowjs
   add constraint FK_FJS_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_flowjs
   add constraint FK_FJS_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid);

alter table xlsys_flowlogic
   add constraint FK_FL_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_P1 foreign key (listpartid)
      references xlsys_part (partid);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_P2 foreign key (mainpartid)
      references xlsys_part (partid);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_V1 foreign key (mvidoflpart)
      references xlsys_view (viewid);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_V2 foreign key (mvidofmpart)
      references xlsys_view (viewid);

alter table xlsys_flowright
   add constraint FK_FR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlsys_flowright
   add constraint FK_FR_REFERENCE_FC foreign key (flowid, cdtidx)
      references xlsys_flowcondition (flowid, idx);

alter table xlsys_flowsubtable
   add constraint FK_FST_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_identity
   add constraint FK_I_REFERENCE_M foreign key (welcomemenuid)
      references xlsys_menu (menuid);

alter table xlsys_identity
   add constraint FKV2_I_REFERENCE_F foreign key (frameid)
      references xlv2_frame (frameid);

alter table xlsys_idrelation
   add constraint FK_IR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlsys_idrelation
   add constraint FK_IR_REFERENCE_I foreign key (id)
      references xlsys_identity (id);

alter table xlsys_menuright
   add constraint FK_MR_REFERENCE_M foreign key (menuid)
      references xlsys_menu (menuid);

alter table xlsys_oacategoryright
   add constraint FK_OACR_REFERENCE_OAC foreign key (oacid)
      references xlsys_oacategory (oacid);

alter table xlsys_oacmbelong
   add constraint FK_OACMB_REFERENCE_OAM foreign key (oamid)
      references xlsys_oamodule (oamid);

alter table xlsys_oacmbelong
   add constraint FK_OACMBL_REFERENCE_OAC foreign key (oacid)
      references xlsys_oacategory (oacid);

alter table xlsys_oacmrelation
   add constraint FK_OACMR_REFERENCE_ID foreign key (id)
      references xlsys_identity (id);

alter table xlsys_oacmrelation
   add constraint FK_OACMR_REFERENCE_OACMBL foreign key (oacid, oamid)
      references xlsys_oacmbelong (oacid, oamid);

alter table xlsys_oamoduleextra
   add constraint FK_OAME_REFERENCE_OAM foreign key (oamid)
      references xlsys_oamodule (oamid);

alter table xlsys_oamoduleextra
   add constraint FK_OAME_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid);

alter table xlsys_oamoduleright
   add constraint FK_OAMR_REFERENCE_OAM foreign key (oamid)
      references xlsys_oamodule (oamid);

alter table xlsys_partdetail
   add constraint FK_PD_REFERENCE_P foreign key (partid)
      references xlsys_part (partid);

alter table xlsys_partdetail
   add constraint FK_PD_REFERENCE_V1 foreign key (viewid)
      references xlsys_view (viewid);

alter table xlsys_partdetail
   add constraint FK_PD_REFERENCE_V2 foreign key (mainviewid)
      references xlsys_view (viewid);

alter table xlsys_queryparamsave
   add constraint FK_QPS_REFERENCE_ID foreign key (id)
      references xlsys_identity (id);

alter table xlsys_queryparamsave
   add constraint FK_QPS_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid);

alter table xlsys_ratify
   add constraint FK_R_REFERENCE_FC_F foreign key (fromflowid, fromcdtidx)
      references xlsys_flowcondition (flowid, idx);

alter table xlsys_ratify
   add constraint FK_R_REFERENCE_FC_T foreign key (toflowid, tocdtidx)
      references xlsys_flowcondition (flowid, idx);

alter table xlsys_ratify
   add constraint FK_R_REFERENCE_U foreign key (fromuserid)
      references xlsys_user (userid);

alter table xlsys_ratifydetail
   add constraint FK_RD_REFERENCE_R foreign key (rtfid)
      references xlsys_ratify (rtfid);

alter table xlsys_ratifydetail
   add constraint FK_RD_REFERENCE_U1 foreign key (touserid)
      references xlsys_user (userid);

alter table xlsys_ratifydetail
   add constraint FK_RD_REFERENCE_U2 foreign key (replaceuserid)
      references xlsys_user (userid);

alter table xlsys_transportdetail
   add constraint FK_TSDT_REFERENCE_TS foreign key (tsid)
      references xlsys_transport (tsid);

alter table xlsys_transportdtcolmap
   add constraint FK_TSDTCM_REFERENCE_TSDT foreign key (tsid, tsdtidx)
      references xlsys_transportdetail (tsid, idx);

alter table xlsys_transportkeysynonym
   add constraint FK_TSKS_REFERENCE_TSK foreign key (tskeyid)
      references xlsys_transportkey (tskeyid);

alter table xlsys_transportmap
   add constraint FK_TSK_REFERENCE_TSM foreign key (tskeyid)
      references xlsys_transportkey (tskeyid);

alter table xlsys_transportrun
   add constraint FK_TSR_REFERENCE_TS foreign key (tsid)
      references xlsys_transport (tsid);

alter table xlsys_useremail
   add constraint FK_UE_REFERENCE_U foreign key (userid)
      references xlsys_user (userid);

alter table xlsys_viewdetail
   add constraint FK_VD_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid);

alter table xlsys_viewdetailparam
   add constraint FK_VDP_REFERENCE_VD foreign key (viewid, idx)
      references xlsys_viewdetail (viewid, idx);

alter table xlsys_viewqueryparam
   add constraint FK_VQP_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid);

alter table xlsys_wdtcolumn
   add constraint FK_WDC_REFERENCE_WD foreign key (wizardid, dtidx)
      references xlsys_wizarddetail (wizardid, idx);

alter table xlsys_wizarddetail
   add constraint FK_WD_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid);

alter table xlsys_wizarddetail
   add constraint FK_WD_REFERENCE_W1 foreign key (wizardid)
      references xlsys_wizard (wizardid);

alter table xlv2_authorisedright
   add constraint FKV2_AR_REFERENCE_AD foreign key (arid, ardtidx)
      references xlv2_authorizedetail (arid, idx);

alter table xlv2_authorisedright
   add constraint FKV2_AR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlv2_authorize
   add constraint FKV2_A_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_authorize
   add constraint FKV2_A_REFERENCE_I1 foreign key (id)
      references xlsys_identity (id);

alter table xlv2_authorize
   add constraint FKV2_A_REFERENCE_I2 foreign key (authorisedid)
      references xlsys_identity (id);

alter table xlv2_authorize
   add constraint FKV2_A_REFERENCE_U foreign key (creater)
      references xlsys_user (userid);

alter table xlv2_authorizedetail
   add constraint FKV2_AD_REFERENCE_A foreign key (arid)
      references xlv2_authorize (arid);

alter table xlv2_authorizedetail
   add constraint FKV2_AD_REFERENCE_F foreign key (arflowid)
      references xlv2_flow (flowid);

alter table xlv2_authorizedetail
   add constraint FKV2_AD_REFERENCE_I foreign key (beauthorizedid)
      references xlsys_identity (id);

alter table xlv2_flowcodealloc
   add constraint FKV2_FCA_REFERENCE_CA foreign key (caid)
      references xlv2_codealloc (caid);

alter table xlv2_flowcodealloc
   add constraint FKV2_FCA_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowcondition
   add constraint FKV2_FC_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_F1 foreign key (listframeid)
      references xlv2_frame (frameid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_F2 foreign key (mainframeid)
      references xlv2_frame (frameid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_FLOW foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_V1 foreign key (mainviewidinlistframe)
      references xlv2_view (viewid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_V2 foreign key (mainviewidinmainframe)
      references xlv2_view (viewid);

alter table xlv2_flowlogic
   add constraint FKV2_FL_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowright
   add constraint FKV2_FR_REFERENCE_FC foreign key (flowid, cdtidx)
      references xlv2_flowcondition (flowid, idx);

alter table xlv2_flowright
   add constraint FKV2_FR_REFERENCE_R1 foreign key (belongrighttype)
      references xlsys_right (righttype);

alter table xlv2_flowright
   add constraint FKV2_FR_REFERENCE_R2 foreign key (righttype)
      references xlsys_right (righttype);

alter table xlv2_flowsubtable
   add constraint FKV2_FST_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowviewlistener
   add constraint FKV2_FVL_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowviewlistener
   add constraint FKV2_FVL_REFERENCE_V foreign key (viewid)
      references xlv2_view (viewid);

alter table xlv2_framedetail
   add constraint FKV2_FD_REFERENCE_F foreign key (frameid)
      references xlv2_frame (frameid);

alter table xlv2_framedetail
   add constraint FKV2_FD_REFERENCE_UIM foreign key (uimid)
      references xlv2_uimodule (uimid);

alter table xlv2_framedetail
   add constraint FKV2_FD_REFERENCE_V foreign key (viewid)
      references xlv2_view (viewid);

alter table xlv2_framedetailparam
   add constraint FKV2_FDP_REFERENCE_FD foreign key (frameid, fdtid)
      references xlv2_framedetail (frameid, fdtid);

alter table xlv2_frameparam
   add constraint FKV2_FP_REFERENCE_F foreign key (frameid)
      references xlv2_frame (frameid);

alter table xlv2_menu
   add constraint FKV2_M_REFERENCE_H foreign key (handlerid)
      references xlv2_handler (handlerid);

alter table xlv2_menuhandlerparam
   add constraint FKV2_MHP_REFERENCE_M foreign key (menuid)
      references xlv2_menu (menuid);

alter table xlv2_menuright
   add constraint FKV2_MR_REFERENCE_M foreign key (menuid)
      references xlv2_menu (menuid);

alter table xlv2_menuright
   add constraint FKV2_MR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlv2_ratify
   add constraint FKV2_R_REFERENCE_FC1 foreign key (fromflowid, fromcdtidx)
      references xlv2_flowcondition (flowid, idx);

alter table xlv2_ratify
   add constraint FKV2_R_REFERENCE_FC2 foreign key (toflowid, tocdtidx)
      references xlv2_flowcondition (flowid, idx);

alter table xlv2_ratify
   add constraint FKV2_R_REFERENCE_U1 foreign key (userid)
      references xlsys_user (userid);

alter table xlv2_ratify
   add constraint FKV2_R_REFERENCE_U2 foreign key (fromuserid)
      references xlsys_user (userid);

alter table xlv2_ratifydetail
   add constraint FKV2_RD_REFERENCE_R foreign key (rtfid)
      references xlv2_ratify (rtfid);

alter table xlv2_ratifydetail
   add constraint FKV2_RD_REFERENCE_U foreign key (replaceuserid)
      references xlsys_user (userid);

alter table xlv2_testbusi
   add constraint FKV2_TB_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_testbusisub
   add constraint FKV2_TBS_REFERENCE_TB foreign key (busiid)
      references xlv2_testbusi (busiid);

alter table xlv2_tool
   add constraint FKV2_T_REFERENCE_H foreign key (handlerid)
      references xlv2_handler (handlerid);

alter table xlv2_toolhandlerparam
   add constraint FKV2_THP_REFERENCE_T foreign key (toolid)
      references xlv2_tool (toolid);

alter table xlv2_toolright
   add constraint FKV2_TR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlv2_toolright
   add constraint FKV2_TR_REFERENCE_T foreign key (toolid)
      references xlv2_tool (toolid);

alter table xlv2_view
   add constraint FKV2_V_REFERENCE_UIM foreign key (uimid)
      references xlv2_uimodule (uimid);

alter table xlv2_viewcolumn
   add constraint FK_XLV2_VIE_REFERENCE_XLV2_VIE foreign key (viewid)
      references xlv2_view (viewid);

alter table xlv2_viewcolumn
   add constraint FKV2_VC_REFERENCE_UIM foreign key (uimid)
      references xlv2_uimodule (uimid);

alter table xlv2_viewcolumnparam
   add constraint FKV2_VCP_REFERENCE_VC foreign key (viewid, idx)
      references xlv2_viewcolumn (viewid, idx);

alter table xlv2_viewparam
   add constraint FKV2_VP_REFERENCE_V foreign key (viewid)
      references xlv2_view (viewid);

alter table xlv2_viewqueryparam
   add constraint FKV2_VQP_REFERENCE_UIM foreign key (uimid)
      references xlv2_uimodule (uimid);

alter table xlv2_viewqueryparam
   add constraint FKV2_VQP_REFERENCE_V foreign key (viewid)
      references xlv2_view (viewid);

alter table xlv2_viewqueryparamparam
   add constraint FKV2_VQPP_REFERENCE_VQP foreign key (viewid, idx)
      references xlv2_viewqueryparam (viewid, idx);

