/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2016/6/14 16:30:22                           */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_buyer') and o.name = 'FK_BR_REFERENCE_U')
alter table xlem_buyer
   drop constraint FK_BR_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_buyer') and o.name = 'FK_BR_REFERENCE_UL')
alter table xlem_buyer
   drop constraint FK_BR_REFERENCE_UL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_item') and o.name = 'FK_I_REFERENCE_SL')
alter table xlem_item
   drop constraint FK_I_REFERENCE_SL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_itemsku') and o.name = 'FK_ISKU_REFERENCE_I')
alter table xlem_itemsku
   drop constraint FK_ISKU_REFERENCE_I
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_itemsku') and o.name = 'FK_ISKU_REFERENCE_SKU')
alter table xlem_itemsku
   drop constraint FK_ISKU_REFERENCE_SKU
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_seller') and o.name = 'FK_SL_REFERENCE_U')
alter table xlem_seller
   drop constraint FK_SL_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_seller') and o.name = 'FK_SL_REFERENCE_UL')
alter table xlem_seller
   drop constraint FK_SL_REFERENCE_UL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_sku') and o.name = 'FK_SKU_REFERENCE_AUNIT')
alter table xlem_sku
   drop constraint FK_SKU_REFERENCE_AUNIT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_sku') and o.name = 'FK_SKU_REFERENCE_SPU')
alter table xlem_sku
   drop constraint FK_SKU_REFERENCE_SPU
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_spu') and o.name = 'FK_SPU_REFERENCE_SPUC')
alter table xlem_spu
   drop constraint FK_SPU_REFERENCE_SPUC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_stock') and o.name = 'FK_STK_REFERENCE_SKU')
alter table xlem_stock
   drop constraint FK_STK_REFERENCE_SKU
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_stockhistory') and o.name = 'FK_STKH_REFERENCE_SKU')
alter table xlem_stockhistory
   drop constraint FK_STKH_REFERENCE_SKU
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_unit') and o.name = 'FK_UNIT_REFERENCE_AUNIT')
alter table xlem_unit
   drop constraint FK_UNIT_REFERENCE_AUNIT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlem_userlevel') and o.name = 'FK_UL_REFERENCE_UL')
alter table xlem_userlevel
   drop constraint FK_UL_REFERENCE_UL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_accountcondition') and o.name = 'FK_AC_REFERENCE_KD')
alter table xlfin_accountcondition
   drop constraint FK_AC_REFERENCE_KD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_balance') and o.name = 'FK_B_REFERENCE_A')
alter table xlfin_balance
   drop constraint FK_B_REFERENCE_A
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_balance') and o.name = 'FK_B_REFERENCE_C')
alter table xlfin_balance
   drop constraint FK_B_REFERENCE_C
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_balance') and o.name = 'FK_B_REFERENCE_KD')
alter table xlfin_balance
   drop constraint FK_B_REFERENCE_KD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_bankstmt') and o.name = 'FK_BS_REFERENCE_A')
alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_A
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_bankstmt') and o.name = 'FK_BS_REFERENCE_C')
alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_C
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_bankstmt') and o.name = 'FK_BS_REFERENCE_KD')
alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_KD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_bankstmt') and o.name = 'FK_BS_REFERENCE_U')
alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_bankstmt') and o.name = 'FK_BS_REFERENCE_VD')
alter table xlfin_bankstmt
   drop constraint FK_BS_REFERENCE_VD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_bankstmtbalance') and o.name = 'FK_BSB_REFERENCE_A')
alter table xlfin_bankstmtbalance
   drop constraint FK_BSB_REFERENCE_A
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_bankstmtbalance') and o.name = 'FK_BSB_REFERENCE_C')
alter table xlfin_bankstmtbalance
   drop constraint FK_BSB_REFERENCE_C
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_bankstmtbalance') and o.name = 'FK_BSB_REFERENCE_KD')
alter table xlfin_bankstmtbalance
   drop constraint FK_BSB_REFERENCE_KD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_bstldt') and o.name = 'FK_BSTLDT_REFERENCE_BSTL')
alter table xlfin_bstldt
   drop constraint FK_BSTLDT_REFERENCE_BSTL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_exchangerate') and o.name = 'FK_ER_REFERENCE_C')
alter table xlfin_exchangerate
   drop constraint FK_ER_REFERENCE_C
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_karaccdt') and o.name = 'FK_KARADT_REFERENCE_KAR')
alter table xlfin_karaccdt
   drop constraint FK_KARADT_REFERENCE_KAR
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_kardt') and o.name = 'FK_KARDT_REFERENCE_KAR')
alter table xlfin_kardt
   drop constraint FK_KARDT_REFERENCE_KAR
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_kardt') and o.name = 'FK_KARDT_REFERENCE_VDTECA')
alter table xlfin_kardt
   drop constraint FK_KARDT_REFERENCE_VDTECA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_keepdept') and o.name = 'FK_KD_REFERENCE_C1')
alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_C1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_keepdept') and o.name = 'FK_KD_REFERENCE_C2')
alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_C2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_keepdept') and o.name = 'FK_KD_REFERENCE_KDAR1')
alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_KDAR1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_keepdept') and o.name = 'FK_KD_REFERENCE_KDAR2')
alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_KDAR2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_keepdept') and o.name = 'FK_KD_REFERENCE_KDAR3')
alter table xlfin_keepdept
   drop constraint FK_KD_REFERENCE_KDAR3
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_reportdata') and o.name = 'FK_RD_REFERENCE_RDEPT')
alter table xlfin_reportdata
   drop constraint FK_RD_REFERENCE_RDEPT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_reportdata') and o.name = 'FK_RD_REFERENCE_RF')
alter table xlfin_reportdata
   drop constraint FK_RD_REFERENCE_RF
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_reportdatadetail') and o.name = 'FK_RDD_REFERENCE_RD')
alter table xlfin_reportdatadetail
   drop constraint FK_RDD_REFERENCE_RD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_reportformcol') and o.name = 'FK_RFC_REFERENCE_RF')
alter table xlfin_reportformcol
   drop constraint FK_RFC_REFERENCE_RF
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_reportformformula') and o.name = 'FK_RFF_REFERENCE_RF')
alter table xlfin_reportformformula
   drop constraint FK_RFF_REFERENCE_RF
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_reportformrow') and o.name = 'FK_RFR_REFERENCE_RF')
alter table xlfin_reportformrow
   drop constraint FK_RFR_REFERENCE_RF
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_vdtextra') and o.name = 'FK_VDTE_REFERENCE_VDT')
alter table xlfin_vdtextra
   drop constraint FK_VDTE_REFERENCE_VDT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_vdtextra') and o.name = 'FK_VDTE_REFERENCE_VEC')
alter table xlfin_vdtextra
   drop constraint FK_VDTE_REFERENCE_VEC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_vdtextracolattr') and o.name = 'FK_VDTECA_REFERENCE_VDTEC')
alter table xlfin_vdtextracolattr
   drop constraint FK_VDTECA_REFERENCE_VDTEC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucher') and o.name = 'FK_V_REFERENCE_F')
alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucher') and o.name = 'FK_V_REFERENCE_ID')
alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_ID
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucher') and o.name = 'FK_V_REFERENCE_KD')
alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_KD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucher') and o.name = 'FK_V_REFERENCE_U1')
alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_U1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucher') and o.name = 'FK_V_REFERENCE_U2')
alter table xlfin_voucher
   drop constraint FK_V_REFERENCE_U2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucherdetail') and o.name = 'FK_VD_REFERENCE_A')
alter table xlfin_voucherdetail
   drop constraint FK_VD_REFERENCE_A
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucherdetail') and o.name = 'FK_VD_REFERENCE_BS')
alter table xlfin_voucherdetail
   drop constraint FK_VD_REFERENCE_BS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucherdetail') and o.name = 'FK_VD_REFERENCE_C')
alter table xlfin_voucherdetail
   drop constraint FK_VD_REFERENCE_C
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucherdetail') and o.name = 'FK_VDT_REFERENCE_KD')
alter table xlfin_voucherdetail
   drop constraint FK_VDT_REFERENCE_KD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucherdetail') and o.name = 'FK_VDT_REFERENCE_U')
alter table xlfin_voucherdetail
   drop constraint FK_VDT_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_voucherdetail') and o.name = 'FK_VOD_REFERENCE_VO')
alter table xlfin_voucherdetail
   drop constraint FK_VOD_REFERENCE_VO
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_vtempletdt') and o.name = 'FK_VTLDT_REFERENCE_VTL')
alter table xlfin_vtempletdt
   drop constraint FK_VTLDT_REFERENCE_VTL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlfin_vtldtcol') and o.name = 'FK_VTLDTC_REFERENCE_VTLDT')
alter table xlfin_vtldtcol
   drop constraint FK_VTLDTC_REFERENCE_VTLDT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_authorisedright') and o.name = 'FK_AR_REFERENCE_AD')
alter table xlsys_authorisedright
   drop constraint FK_AR_REFERENCE_AD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_authorisedright') and o.name = 'FK_AR_REFERENCE_R')
alter table xlsys_authorisedright
   drop constraint FK_AR_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_authorize') and o.name = 'FK_A_REFERENCE_F')
alter table xlsys_authorize
   drop constraint FK_A_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_authorize') and o.name = 'FK_A_REFERENCE_I1')
alter table xlsys_authorize
   drop constraint FK_A_REFERENCE_I1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_authorize') and o.name = 'FK_A_REFERENCE_I2')
alter table xlsys_authorize
   drop constraint FK_A_REFERENCE_I2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_authorize') and o.name = 'FK_A_REFERENCE_U')
alter table xlsys_authorize
   drop constraint FK_A_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_authorizedetail') and o.name = 'FK_AD_REFERENCE_A')
alter table xlsys_authorizedetail
   drop constraint FK_AD_REFERENCE_A
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_basebusi') and o.name = 'FK_BB_REFERENCE_F')
alter table xlsys_basebusi
   drop constraint FK_BB_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_basebusi') and o.name = 'FK_BB_REFERENCE_I')
alter table xlsys_basebusi
   drop constraint FK_BB_REFERENCE_I
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_basebusi') and o.name = 'FK_BB_REFERENCE_U')
alter table xlsys_basebusi
   drop constraint FK_BB_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_dictdetail') and o.name = 'FK_DD_REFERENCE_D')
alter table xlsys_dictdetail
   drop constraint FK_DD_REFERENCE_D
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_envdetail') and o.name = 'FK_ED_REFERENCE_D')
alter table xlsys_envdetail
   drop constraint FK_ED_REFERENCE_D
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_envdetail') and o.name = 'FK_ED_REFERENCE_E')
alter table xlsys_envdetail
   drop constraint FK_ED_REFERENCE_E
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_exttableinfodetail') and o.name = 'FK_ETI_REFERENCE_ETID')
alter table xlsys_exttableinfodetail
   drop constraint FK_ETI_REFERENCE_ETID
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flow') and o.name = 'FK_F_REFERENCE_V1')
alter table xlsys_flow
   drop constraint FK_F_REFERENCE_V1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flow') and o.name = 'FK_F_REFERENCE_V2')
alter table xlsys_flow
   drop constraint FK_F_REFERENCE_V2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flow') and o.name = 'FK_FL_REFERENCE_P1')
alter table xlsys_flow
   drop constraint FK_FL_REFERENCE_P1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flow') and o.name = 'FK_FL_REFERENCE_P2')
alter table xlsys_flow
   drop constraint FK_FL_REFERENCE_P2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowcodealloc') and o.name = 'FK_FCA_REFERENCE_CA')
alter table xlsys_flowcodealloc
   drop constraint FK_FCA_REFERENCE_CA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowcodealloc') and o.name = 'FK_FCA_REFERENCE_F')
alter table xlsys_flowcodealloc
   drop constraint FK_FCA_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowcondition') and o.name = 'FK_FC_REFERENCE_F')
alter table xlsys_flowcondition
   drop constraint FK_FC_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowjava') and o.name = 'FK_FJAVA_REFERENCE_F')
alter table xlsys_flowjava
   drop constraint FK_FJAVA_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowjava') and o.name = 'FK_FJAVA_REFERENCE_V')
alter table xlsys_flowjava
   drop constraint FK_FJAVA_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowjs') and o.name = 'FK_FJS_REFERENCE_F')
alter table xlsys_flowjs
   drop constraint FK_FJS_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowjs') and o.name = 'FK_FJS_REFERENCE_V')
alter table xlsys_flowjs
   drop constraint FK_FJS_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowlogic') and o.name = 'FK_FL_REFERENCE_F')
alter table xlsys_flowlogic
   drop constraint FK_FL_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowpart') and o.name = 'FK_FP_REFERENCE_F')
alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowpart') and o.name = 'FK_FP_REFERENCE_P1')
alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_P1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowpart') and o.name = 'FK_FP_REFERENCE_P2')
alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_P2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowpart') and o.name = 'FK_FP_REFERENCE_R')
alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowpart') and o.name = 'FK_FP_REFERENCE_V1')
alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_V1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowpart') and o.name = 'FK_FP_REFERENCE_V2')
alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_V2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowright') and o.name = 'FK_FR_REFERENCE_R')
alter table xlsys_flowright
   drop constraint FK_FR_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowright') and o.name = 'FK_FR_REFERENCE_FC')
alter table xlsys_flowright
   drop constraint FK_FR_REFERENCE_FC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_flowsubtable') and o.name = 'FK_FST_REFERENCE_F')
alter table xlsys_flowsubtable
   drop constraint FK_FST_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_identity') and o.name = 'FK_I_REFERENCE_M')
alter table xlsys_identity
   drop constraint FK_I_REFERENCE_M
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_identity') and o.name = 'FKV2_I_REFERENCE_F')
alter table xlsys_identity
   drop constraint FKV2_I_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_idrelation') and o.name = 'FK_IR_REFERENCE_R')
alter table xlsys_idrelation
   drop constraint FK_IR_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_idrelation') and o.name = 'FK_IR_REFERENCE_I')
alter table xlsys_idrelation
   drop constraint FK_IR_REFERENCE_I
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_menuright') and o.name = 'FK_MR_REFERENCE_M')
alter table xlsys_menuright
   drop constraint FK_MR_REFERENCE_M
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_oacategoryright') and o.name = 'FK_OACR_REFERENCE_OAC')
alter table xlsys_oacategoryright
   drop constraint FK_OACR_REFERENCE_OAC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_oacmbelong') and o.name = 'FK_OACMB_REFERENCE_OAM')
alter table xlsys_oacmbelong
   drop constraint FK_OACMB_REFERENCE_OAM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_oacmbelong') and o.name = 'FK_OACMBL_REFERENCE_OAC')
alter table xlsys_oacmbelong
   drop constraint FK_OACMBL_REFERENCE_OAC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_oacmrelation') and o.name = 'FK_OACMR_REFERENCE_ID')
alter table xlsys_oacmrelation
   drop constraint FK_OACMR_REFERENCE_ID
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_oacmrelation') and o.name = 'FK_OACMR_REFERENCE_OACMBL')
alter table xlsys_oacmrelation
   drop constraint FK_OACMR_REFERENCE_OACMBL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_oamoduleextra') and o.name = 'FK_OAME_REFERENCE_OAM')
alter table xlsys_oamoduleextra
   drop constraint FK_OAME_REFERENCE_OAM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_oamoduleextra') and o.name = 'FK_OAME_REFERENCE_V')
alter table xlsys_oamoduleextra
   drop constraint FK_OAME_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_oamoduleright') and o.name = 'FK_OAMR_REFERENCE_OAM')
alter table xlsys_oamoduleright
   drop constraint FK_OAMR_REFERENCE_OAM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_partdetail') and o.name = 'FK_PD_REFERENCE_P')
alter table xlsys_partdetail
   drop constraint FK_PD_REFERENCE_P
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_partdetail') and o.name = 'FK_PD_REFERENCE_V1')
alter table xlsys_partdetail
   drop constraint FK_PD_REFERENCE_V1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_partdetail') and o.name = 'FK_PD_REFERENCE_V2')
alter table xlsys_partdetail
   drop constraint FK_PD_REFERENCE_V2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_queryparamsave') and o.name = 'FK_QPS_REFERENCE_ID')
alter table xlsys_queryparamsave
   drop constraint FK_QPS_REFERENCE_ID
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_queryparamsave') and o.name = 'FK_QPS_REFERENCE_V')
alter table xlsys_queryparamsave
   drop constraint FK_QPS_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_ratify') and o.name = 'FK_R_REFERENCE_FC_F')
alter table xlsys_ratify
   drop constraint FK_R_REFERENCE_FC_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_ratify') and o.name = 'FK_R_REFERENCE_FC_T')
alter table xlsys_ratify
   drop constraint FK_R_REFERENCE_FC_T
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_ratify') and o.name = 'FK_R_REFERENCE_U')
alter table xlsys_ratify
   drop constraint FK_R_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_ratifydetail') and o.name = 'FK_RD_REFERENCE_R')
alter table xlsys_ratifydetail
   drop constraint FK_RD_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_ratifydetail') and o.name = 'FK_RD_REFERENCE_U1')
alter table xlsys_ratifydetail
   drop constraint FK_RD_REFERENCE_U1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_ratifydetail') and o.name = 'FK_RD_REFERENCE_U2')
alter table xlsys_ratifydetail
   drop constraint FK_RD_REFERENCE_U2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_transportdetail') and o.name = 'FK_TSDT_REFERENCE_TS')
alter table xlsys_transportdetail
   drop constraint FK_TSDT_REFERENCE_TS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_transportdtcolmap') and o.name = 'FK_TSDTCM_REFERENCE_TSDT')
alter table xlsys_transportdtcolmap
   drop constraint FK_TSDTCM_REFERENCE_TSDT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_transportkeysynonym') and o.name = 'FK_TSKS_REFERENCE_TSK')
alter table xlsys_transportkeysynonym
   drop constraint FK_TSKS_REFERENCE_TSK
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_transportmap') and o.name = 'FK_TSK_REFERENCE_TSM')
alter table xlsys_transportmap
   drop constraint FK_TSK_REFERENCE_TSM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_transportrun') and o.name = 'FK_TSR_REFERENCE_TS')
alter table xlsys_transportrun
   drop constraint FK_TSR_REFERENCE_TS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_useremail') and o.name = 'FK_UE_REFERENCE_U')
alter table xlsys_useremail
   drop constraint FK_UE_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_viewdetail') and o.name = 'FK_VD_REFERENCE_V')
alter table xlsys_viewdetail
   drop constraint FK_VD_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_viewdetailparam') and o.name = 'FK_VDP_REFERENCE_VD')
alter table xlsys_viewdetailparam
   drop constraint FK_VDP_REFERENCE_VD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_viewqueryparam') and o.name = 'FK_VQP_REFERENCE_V')
alter table xlsys_viewqueryparam
   drop constraint FK_VQP_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_wdtcolumn') and o.name = 'FK_WDC_REFERENCE_WD')
alter table xlsys_wdtcolumn
   drop constraint FK_WDC_REFERENCE_WD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_wizarddetail') and o.name = 'FK_WD_REFERENCE_V')
alter table xlsys_wizarddetail
   drop constraint FK_WD_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_wizarddetail') and o.name = 'FK_WD_REFERENCE_W1')
alter table xlsys_wizarddetail
   drop constraint FK_WD_REFERENCE_W1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_authorisedright') and o.name = 'FKV2_AR_REFERENCE_AD')
alter table xlv2_authorisedright
   drop constraint FKV2_AR_REFERENCE_AD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_authorisedright') and o.name = 'FKV2_AR_REFERENCE_R')
alter table xlv2_authorisedright
   drop constraint FKV2_AR_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_authorize') and o.name = 'FKV2_A_REFERENCE_F')
alter table xlv2_authorize
   drop constraint FKV2_A_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_authorize') and o.name = 'FKV2_A_REFERENCE_I1')
alter table xlv2_authorize
   drop constraint FKV2_A_REFERENCE_I1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_authorize') and o.name = 'FKV2_A_REFERENCE_I2')
alter table xlv2_authorize
   drop constraint FKV2_A_REFERENCE_I2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_authorize') and o.name = 'FKV2_A_REFERENCE_U')
alter table xlv2_authorize
   drop constraint FKV2_A_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_authorizedetail') and o.name = 'FKV2_AD_REFERENCE_A')
alter table xlv2_authorizedetail
   drop constraint FKV2_AD_REFERENCE_A
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_authorizedetail') and o.name = 'FKV2_AD_REFERENCE_F')
alter table xlv2_authorizedetail
   drop constraint FKV2_AD_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_authorizedetail') and o.name = 'FKV2_AD_REFERENCE_I')
alter table xlv2_authorizedetail
   drop constraint FKV2_AD_REFERENCE_I
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowcodealloc') and o.name = 'FKV2_FCA_REFERENCE_CA')
alter table xlv2_flowcodealloc
   drop constraint FKV2_FCA_REFERENCE_CA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowcodealloc') and o.name = 'FKV2_FCA_REFERENCE_F')
alter table xlv2_flowcodealloc
   drop constraint FKV2_FCA_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowcondition') and o.name = 'FKV2_FC_REFERENCE_F')
alter table xlv2_flowcondition
   drop constraint FKV2_FC_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowframe') and o.name = 'FKV2_FF_REFERENCE_F1')
alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_F1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowframe') and o.name = 'FKV2_FF_REFERENCE_F2')
alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_F2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowframe') and o.name = 'FKV2_FF_REFERENCE_FLOW')
alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_FLOW
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowframe') and o.name = 'FKV2_FF_REFERENCE_R')
alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowframe') and o.name = 'FKV2_FF_REFERENCE_V1')
alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_V1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowframe') and o.name = 'FKV2_FF_REFERENCE_V2')
alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_V2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowlogic') and o.name = 'FKV2_FL_REFERENCE_F')
alter table xlv2_flowlogic
   drop constraint FKV2_FL_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowright') and o.name = 'FKV2_FR_REFERENCE_FC')
alter table xlv2_flowright
   drop constraint FKV2_FR_REFERENCE_FC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowright') and o.name = 'FKV2_FR_REFERENCE_R1')
alter table xlv2_flowright
   drop constraint FKV2_FR_REFERENCE_R1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowright') and o.name = 'FKV2_FR_REFERENCE_R2')
alter table xlv2_flowright
   drop constraint FKV2_FR_REFERENCE_R2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowsubtable') and o.name = 'FKV2_FST_REFERENCE_F')
alter table xlv2_flowsubtable
   drop constraint FKV2_FST_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowviewlistener') and o.name = 'FKV2_FVL_REFERENCE_F')
alter table xlv2_flowviewlistener
   drop constraint FKV2_FVL_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_flowviewlistener') and o.name = 'FKV2_FVL_REFERENCE_V')
alter table xlv2_flowviewlistener
   drop constraint FKV2_FVL_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_framedetail') and o.name = 'FKV2_FD_REFERENCE_F')
alter table xlv2_framedetail
   drop constraint FKV2_FD_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_framedetail') and o.name = 'FKV2_FD_REFERENCE_UIM')
alter table xlv2_framedetail
   drop constraint FKV2_FD_REFERENCE_UIM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_framedetail') and o.name = 'FKV2_FD_REFERENCE_V')
alter table xlv2_framedetail
   drop constraint FKV2_FD_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_framedetailparam') and o.name = 'FKV2_FDP_REFERENCE_FD')
alter table xlv2_framedetailparam
   drop constraint FKV2_FDP_REFERENCE_FD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_frameparam') and o.name = 'FKV2_FP_REFERENCE_F')
alter table xlv2_frameparam
   drop constraint FKV2_FP_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_menu') and o.name = 'FKV2_M_REFERENCE_H')
alter table xlv2_menu
   drop constraint FKV2_M_REFERENCE_H
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_menuhandlerparam') and o.name = 'FKV2_MHP_REFERENCE_M')
alter table xlv2_menuhandlerparam
   drop constraint FKV2_MHP_REFERENCE_M
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_menuright') and o.name = 'FKV2_MR_REFERENCE_M')
alter table xlv2_menuright
   drop constraint FKV2_MR_REFERENCE_M
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_menuright') and o.name = 'FKV2_MR_REFERENCE_R')
alter table xlv2_menuright
   drop constraint FKV2_MR_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_ratify') and o.name = 'FKV2_R_REFERENCE_FC1')
alter table xlv2_ratify
   drop constraint FKV2_R_REFERENCE_FC1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_ratify') and o.name = 'FKV2_R_REFERENCE_FC2')
alter table xlv2_ratify
   drop constraint FKV2_R_REFERENCE_FC2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_ratify') and o.name = 'FKV2_R_REFERENCE_U1')
alter table xlv2_ratify
   drop constraint FKV2_R_REFERENCE_U1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_ratify') and o.name = 'FKV2_R_REFERENCE_U2')
alter table xlv2_ratify
   drop constraint FKV2_R_REFERENCE_U2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_ratifydetail') and o.name = 'FKV2_RD_REFERENCE_R')
alter table xlv2_ratifydetail
   drop constraint FKV2_RD_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_ratifydetail') and o.name = 'FKV2_RD_REFERENCE_U')
alter table xlv2_ratifydetail
   drop constraint FKV2_RD_REFERENCE_U
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_testbusi') and o.name = 'FKV2_TB_REFERENCE_F')
alter table xlv2_testbusi
   drop constraint FKV2_TB_REFERENCE_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_testbusisub') and o.name = 'FKV2_TBS_REFERENCE_TB')
alter table xlv2_testbusisub
   drop constraint FKV2_TBS_REFERENCE_TB
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_tool') and o.name = 'FKV2_T_REFERENCE_H')
alter table xlv2_tool
   drop constraint FKV2_T_REFERENCE_H
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_toolhandlerparam') and o.name = 'FKV2_THP_REFERENCE_T')
alter table xlv2_toolhandlerparam
   drop constraint FKV2_THP_REFERENCE_T
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_toolright') and o.name = 'FKV2_TR_REFERENCE_R')
alter table xlv2_toolright
   drop constraint FKV2_TR_REFERENCE_R
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_toolright') and o.name = 'FKV2_TR_REFERENCE_T')
alter table xlv2_toolright
   drop constraint FKV2_TR_REFERENCE_T
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_view') and o.name = 'FKV2_V_REFERENCE_UIM')
alter table xlv2_view
   drop constraint FKV2_V_REFERENCE_UIM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_viewcolumn') and o.name = 'FK_XLV2_VIE_REFERENCE_XLV2_VIE')
alter table xlv2_viewcolumn
   drop constraint FK_XLV2_VIE_REFERENCE_XLV2_VIE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_viewcolumn') and o.name = 'FKV2_VC_REFERENCE_UIM')
alter table xlv2_viewcolumn
   drop constraint FKV2_VC_REFERENCE_UIM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_viewcolumnparam') and o.name = 'FKV2_VCP_REFERENCE_VC')
alter table xlv2_viewcolumnparam
   drop constraint FKV2_VCP_REFERENCE_VC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_viewparam') and o.name = 'FKV2_VP_REFERENCE_V')
alter table xlv2_viewparam
   drop constraint FKV2_VP_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_viewqueryparam') and o.name = 'FKV2_VQP_REFERENCE_UIM')
alter table xlv2_viewqueryparam
   drop constraint FKV2_VQP_REFERENCE_UIM
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_viewqueryparam') and o.name = 'FKV2_VQP_REFERENCE_V')
alter table xlv2_viewqueryparam
   drop constraint FKV2_VQP_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlv2_viewqueryparamparam') and o.name = 'FKV2_VQPP_REFERENCE_VQP')
alter table xlv2_viewqueryparamparam
   drop constraint FKV2_VQPP_REFERENCE_VQP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_atomicunit')
            and   type = 'U')
   drop table xlem_atomicunit
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_buyer')
            and   type = 'U')
   drop table xlem_buyer
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_item')
            and   type = 'U')
   drop table xlem_item
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_itemsku')
            and   type = 'U')
   drop table xlem_itemsku
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_prepsynonym')
            and   type = 'U')
   drop table xlem_prepsynonym
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_searchkeyword')
            and   type = 'U')
   drop table xlem_searchkeyword
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_searchtext')
            and   type = 'U')
   drop table xlem_searchtext
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('xlem_seller')
            and   name  = 'UN_SELLER_U'
            and   indid > 0
            and   indid < 255)
   drop index xlem_seller.UN_SELLER_U
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_seller')
            and   type = 'U')
   drop table xlem_seller
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_sku')
            and   type = 'U')
   drop table xlem_sku
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_spu')
            and   type = 'U')
   drop table xlem_spu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_spucategory')
            and   type = 'U')
   drop table xlem_spucategory
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_spuindex')
            and   type = 'U')
   drop table xlem_spuindex
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_stock')
            and   type = 'U')
   drop table xlem_stock
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_stockhistory')
            and   type = 'U')
   drop table xlem_stockhistory
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_synonym')
            and   type = 'U')
   drop table xlem_synonym
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_unit')
            and   type = 'U')
   drop table xlem_unit
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_user')
            and   type = 'U')
   drop table xlem_user
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlem_userlevel')
            and   type = 'U')
   drop table xlem_userlevel
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_account')
            and   type = 'U')
   drop table xlfin_account
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_accountcondition')
            and   type = 'U')
   drop table xlfin_accountcondition
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_accountingitem')
            and   type = 'U')
   drop table xlfin_accountingitem
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_balance')
            and   type = 'U')
   drop table xlfin_balance
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_balanceitem')
            and   type = 'U')
   drop table xlfin_balanceitem
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_bankstmt')
            and   type = 'U')
   drop table xlfin_bankstmt
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_bankstmtbalance')
            and   type = 'U')
   drop table xlfin_bankstmtbalance
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_bankstmttemplet')
            and   type = 'U')
   drop table xlfin_bankstmttemplet
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_bstldt')
            and   type = 'U')
   drop table xlfin_bstldt
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_currency')
            and   type = 'U')
   drop table xlfin_currency
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_exchangerate')
            and   type = 'U')
   drop table xlfin_exchangerate
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_karaccdt')
            and   type = 'U')
   drop table xlfin_karaccdt
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_kardt')
            and   type = 'U')
   drop table xlfin_kardt
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_kdeptaccrealtion')
            and   type = 'U')
   drop table xlfin_kdeptaccrealtion
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_keepdept')
            and   type = 'U')
   drop table xlfin_keepdept
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('xlfin_reportdata')
            and   name  = 'un_rrbe'
            and   indid > 0
            and   indid < 255)
   drop index xlfin_reportdata.un_rrbe
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_reportdata')
            and   type = 'U')
   drop table xlfin_reportdata
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_reportdatadetail')
            and   type = 'U')
   drop table xlfin_reportdatadetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_reportdept')
            and   type = 'U')
   drop table xlfin_reportdept
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_reportform')
            and   type = 'U')
   drop table xlfin_reportform
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_reportformcol')
            and   type = 'U')
   drop table xlfin_reportformcol
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_reportformformula')
            and   type = 'U')
   drop table xlfin_reportformformula
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_reportformrow')
            and   type = 'U')
   drop table xlfin_reportformrow
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_vdtextra')
            and   type = 'U')
   drop table xlfin_vdtextra
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_vdtextracol')
            and   type = 'U')
   drop table xlfin_vdtextracol
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_vdtextracolattr')
            and   type = 'U')
   drop table xlfin_vdtextracolattr
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_voucher')
            and   type = 'U')
   drop table xlfin_voucher
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_voucherdetail')
            and   type = 'U')
   drop table xlfin_voucherdetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_vouchertemplet')
            and   type = 'U')
   drop table xlfin_vouchertemplet
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_vtempletdt')
            and   type = 'U')
   drop table xlfin_vtempletdt
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlfin_vtldtcol')
            and   type = 'U')
   drop table xlfin_vtldtcol
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_attachment')
            and   type = 'U')
   drop table xlsys_attachment
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_authorisedright')
            and   type = 'U')
   drop table xlsys_authorisedright
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_authorize')
            and   type = 'U')
   drop table xlsys_authorize
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('xlsys_authorizedetail')
            and   name  = 'un_ad_fci'
            and   indid > 0
            and   indid < 255)
   drop index xlsys_authorizedetail.un_ad_fci
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_authorizedetail')
            and   type = 'U')
   drop table xlsys_authorizedetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_basebusi')
            and   type = 'U')
   drop table xlsys_basebusi
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_codealloc')
            and   type = 'U')
   drop table xlsys_codealloc
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_db')
            and   type = 'U')
   drop table xlsys_db
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_department')
            and   type = 'U')
   drop table xlsys_department
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_dict')
            and   type = 'U')
   drop table xlsys_dict
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_dictdetail')
            and   type = 'U')
   drop table xlsys_dictdetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_emailtemplate')
            and   type = 'U')
   drop table xlsys_emailtemplate
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_env')
            and   type = 'U')
   drop table xlsys_env
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_envdetail')
            and   type = 'U')
   drop table xlsys_envdetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_extracmd')
            and   type = 'U')
   drop table xlsys_extracmd
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_exttableinfo')
            and   type = 'U')
   drop table xlsys_exttableinfo
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_exttableinfodetail')
            and   type = 'U')
   drop table xlsys_exttableinfodetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_flow')
            and   type = 'U')
   drop table xlsys_flow
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_flowcodealloc')
            and   type = 'U')
   drop table xlsys_flowcodealloc
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_flowcondition')
            and   type = 'U')
   drop table xlsys_flowcondition
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_flowjava')
            and   type = 'U')
   drop table xlsys_flowjava
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_flowjs')
            and   type = 'U')
   drop table xlsys_flowjs
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_flowlogic')
            and   type = 'U')
   drop table xlsys_flowlogic
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_flowpart')
            and   type = 'U')
   drop table xlsys_flowpart
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_flowright')
            and   type = 'U')
   drop table xlsys_flowright
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_flowsubtable')
            and   type = 'U')
   drop table xlsys_flowsubtable
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_identity')
            and   type = 'U')
   drop table xlsys_identity
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('xlsys_idrelation')
            and   name  = 'xlsys_idrelation_tcv'
            and   indid > 0
            and   indid < 255)
   drop index xlsys_idrelation.xlsys_idrelation_tcv
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('xlsys_idrelation')
            and   name  = 'xlsys_idrelation_uq'
            and   indid > 0
            and   indid < 255)
   drop index xlsys_idrelation.xlsys_idrelation_uq
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_idrelation')
            and   type = 'U')
   drop table xlsys_idrelation
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_image')
            and   type = 'U')
   drop table xlsys_image
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_ipresource')
            and   type = 'U')
   drop table xlsys_ipresource
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_javaclass')
            and   type = 'U')
   drop table xlsys_javaclass
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_menu')
            and   type = 'U')
   drop table xlsys_menu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_menuright')
            and   type = 'U')
   drop table xlsys_menuright
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_oacategory')
            and   type = 'U')
   drop table xlsys_oacategory
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_oacategoryright')
            and   type = 'U')
   drop table xlsys_oacategoryright
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_oacmbelong')
            and   type = 'U')
   drop table xlsys_oacmbelong
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_oacmrelation')
            and   type = 'U')
   drop table xlsys_oacmrelation
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_oamodule')
            and   type = 'U')
   drop table xlsys_oamodule
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_oamoduleextra')
            and   type = 'U')
   drop table xlsys_oamoduleextra
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_oamoduleright')
            and   type = 'U')
   drop table xlsys_oamoduleright
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_part')
            and   type = 'U')
   drop table xlsys_part
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_partdetail')
            and   type = 'U')
   drop table xlsys_partdetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_position')
            and   type = 'U')
   drop table xlsys_position
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_print')
            and   type = 'U')
   drop table xlsys_print
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_queryparamsave')
            and   type = 'U')
   drop table xlsys_queryparamsave
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_ratify')
            and   type = 'U')
   drop table xlsys_ratify
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_ratifydetail')
            and   type = 'U')
   drop table xlsys_ratifydetail
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('xlsys_right')
            and   name  = 'UN_RIGHT_SK'
            and   indid > 0
            and   indid < 255)
   drop index xlsys_right.UN_RIGHT_SK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('xlsys_right')
            and   name  = 'UN_RIGHT_RC'
            and   indid > 0
            and   indid < 255)
   drop index xlsys_right.UN_RIGHT_RC
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_right')
            and   type = 'U')
   drop table xlsys_right
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_test')
            and   type = 'U')
   drop table xlsys_test
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_translator')
            and   type = 'U')
   drop table xlsys_translator
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_transport')
            and   type = 'U')
   drop table xlsys_transport
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_transportdetail')
            and   type = 'U')
   drop table xlsys_transportdetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_transportdtcolmap')
            and   type = 'U')
   drop table xlsys_transportdtcolmap
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_transportkey')
            and   type = 'U')
   drop table xlsys_transportkey
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_transportkeysynonym')
            and   type = 'U')
   drop table xlsys_transportkeysynonym
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_transportmap')
            and   type = 'U')
   drop table xlsys_transportmap
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_transportrun')
            and   type = 'U')
   drop table xlsys_transportrun
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_user')
            and   type = 'U')
   drop table xlsys_user
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_useremail')
            and   type = 'U')
   drop table xlsys_useremail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_view')
            and   type = 'U')
   drop table xlsys_view
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('xlsys_viewdetail')
            and   name  = 'un_vd'
            and   indid > 0
            and   indid < 255)
   drop index xlsys_viewdetail.un_vd
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_viewdetail')
            and   type = 'U')
   drop table xlsys_viewdetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_viewdetailparam')
            and   type = 'U')
   drop table xlsys_viewdetailparam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_viewqueryparam')
            and   type = 'U')
   drop table xlsys_viewqueryparam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_wdtcolumn')
            and   type = 'U')
   drop table xlsys_wdtcolumn
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_wizard')
            and   type = 'U')
   drop table xlsys_wizard
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_wizarddetail')
            and   type = 'U')
   drop table xlsys_wizarddetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_authorisedright')
            and   type = 'U')
   drop table xlv2_authorisedright
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_authorize')
            and   type = 'U')
   drop table xlv2_authorize
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('xlv2_authorizedetail')
            and   name  = 'un_ad_fci2'
            and   indid > 0
            and   indid < 255)
   drop index xlv2_authorizedetail.un_ad_fci2
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_authorizedetail')
            and   type = 'U')
   drop table xlv2_authorizedetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_codealloc')
            and   type = 'U')
   drop table xlv2_codealloc
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_control')
            and   type = 'U')
   drop table xlv2_control
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_flow')
            and   type = 'U')
   drop table xlv2_flow
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_flowcodealloc')
            and   type = 'U')
   drop table xlv2_flowcodealloc
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_flowcondition')
            and   type = 'U')
   drop table xlv2_flowcondition
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_flowframe')
            and   type = 'U')
   drop table xlv2_flowframe
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_flowlogic')
            and   type = 'U')
   drop table xlv2_flowlogic
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_flowright')
            and   type = 'U')
   drop table xlv2_flowright
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_flowsubtable')
            and   type = 'U')
   drop table xlv2_flowsubtable
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_flowviewlistener')
            and   type = 'U')
   drop table xlv2_flowviewlistener
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_frame')
            and   type = 'U')
   drop table xlv2_frame
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_framedetail')
            and   type = 'U')
   drop table xlv2_framedetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_framedetailparam')
            and   type = 'U')
   drop table xlv2_framedetailparam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_frameparam')
            and   type = 'U')
   drop table xlv2_frameparam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_handler')
            and   type = 'U')
   drop table xlv2_handler
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_menu')
            and   type = 'U')
   drop table xlv2_menu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_menuhandlerparam')
            and   type = 'U')
   drop table xlv2_menuhandlerparam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_menuright')
            and   type = 'U')
   drop table xlv2_menuright
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_ratify')
            and   type = 'U')
   drop table xlv2_ratify
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_ratifydetail')
            and   type = 'U')
   drop table xlv2_ratifydetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_testbusi')
            and   type = 'U')
   drop table xlv2_testbusi
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_testbusisub')
            and   type = 'U')
   drop table xlv2_testbusisub
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_tool')
            and   type = 'U')
   drop table xlv2_tool
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_toolhandlerparam')
            and   type = 'U')
   drop table xlv2_toolhandlerparam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_toolright')
            and   type = 'U')
   drop table xlv2_toolright
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_uimodule')
            and   type = 'U')
   drop table xlv2_uimodule
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_view')
            and   type = 'U')
   drop table xlv2_view
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_viewcolumn')
            and   type = 'U')
   drop table xlv2_viewcolumn
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_viewcolumnparam')
            and   type = 'U')
   drop table xlv2_viewcolumnparam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_viewparam')
            and   type = 'U')
   drop table xlv2_viewparam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_viewqueryparam')
            and   type = 'U')
   drop table xlv2_viewqueryparam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlv2_viewqueryparamparam')
            and   type = 'U')
   drop table xlv2_viewqueryparamparam
go

/*==============================================================*/
/* Table: xlem_atomicunit                                       */
/*==============================================================*/
create table xlem_atomicunit (
   aunitid              varchar(32)          not null,
   name                 varchar(256)         null,
   constraint PK_XLEM_ATOMICUNIT primary key nonclustered (aunitid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '不可拆分的单位定义表',
   'user', @CurrentUser, 'table', 'xlem_atomicunit'
go

/*==============================================================*/
/* Table: xlem_buyer                                            */
/*==============================================================*/
create table xlem_buyer (
   buyerid              varchar(256)         not null,
   name                 varchar(256)         null,
   userid               varchar(256)         null,
   levelid              varchar(256)         null,
   experience           numeric(18,6)        null,
   constraint PK_XLEM_BUYER primary key nonclustered (buyerid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子商务买家表, 主要记录买家相关信息',
   'user', @CurrentUser, 'table', 'xlem_buyer'
go

/*==============================================================*/
/* Table: xlem_item                                             */
/*==============================================================*/
create table xlem_item (
   itemid               varchar(32)          not null,
   sellerid             varchar(256)         null,
   name                 varchar(256)         null,
   description          varchar(4000)        null,
   constraint PK_XLEM_ITEM primary key nonclustered (itemid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子商务商品表.
   一个商品对应多个SKU',
   'user', @CurrentUser, 'table', 'xlem_item'
go

/*==============================================================*/
/* Table: xlem_itemsku                                          */
/*==============================================================*/
create table xlem_itemsku (
   itemid               varchar(32)          not null,
   sku                  varchar(32)          not null,
   constraint PK_XLEM_ITEMSKU primary key nonclustered (itemid, sku)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商品对应的SKU',
   'user', @CurrentUser, 'table', 'xlem_itemsku'
go

/*==============================================================*/
/* Table: xlem_prepsynonym                                      */
/*==============================================================*/
create table xlem_prepsynonym (
   mergehash            numeric(8,0)         not null,
   srcword              varchar(32)          not null,
   synword              varchar(32)          not null,
   heat                 numeric(8,0)         null,
   constraint PK_XLEM_PREPSYNONYM primary key nonclustered (mergehash)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '预备同义词库. 该库中的同义词热度累计到达一定数量后, 则会转移到同义词库中',
   'user', @CurrentUser, 'table', 'xlem_prepsynonym'
go

/*==============================================================*/
/* Table: xlem_searchkeyword                                    */
/*==============================================================*/
create table xlem_searchkeyword (
   keyword              varchar(64)          not null,
   heat                 numeric(8,0)         not null,
   constraint PK_XLEM_SEARCHKEYWORD primary key nonclustered (keyword)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '查询关键字热度表, 此表中存的都是独立的不需要进行拆分的查询关键字信息',
   'user', @CurrentUser, 'table', 'xlem_searchkeyword'
go

/*==============================================================*/
/* Table: xlem_searchtext                                       */
/*==============================================================*/
create table xlem_searchtext (
   searchtext           varchar(1000)        not null,
   heat                 numeric(8,0)         not null,
   constraint PK_XLEM_SEARCHTEXT primary key nonclustered (searchtext)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '查询热度表. 此表保存了所有查询字符串以及对应的热度',
   'user', @CurrentUser, 'table', 'xlem_searchtext'
go

/*==============================================================*/
/* Table: xlem_seller                                           */
/*==============================================================*/
create table xlem_seller (
   sellerid             varchar(256)         not null,
   name                 varchar(256)         null,
   userid               varchar(256)         null,
   levelid              varchar(256)         null,
   experience           numeric(18,6)        null,
   constraint PK_XLEM_SELLER primary key nonclustered (sellerid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子商务卖家表, 主要记录卖家相关信息',
   'user', @CurrentUser, 'table', 'xlem_seller'
go

/*==============================================================*/
/* Index: UN_SELLER_U                                           */
/*==============================================================*/
create unique index UN_SELLER_U on xlem_seller (
userid ASC
)
go

/*==============================================================*/
/* Table: xlem_sku                                              */
/*==============================================================*/
create table xlem_sku (
   sku                  varchar(32)          not null,
   spu                  varchar(32)          not null,
   aunitid              varchar(32)          not null,
   name                 varchar(256)         null,
   description          varchar(4000)        null,
   constraint PK_XLEM_SKU primary key nonclustered (sku)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子商务SKU.
   库存量单位, 最小单位, 不可分割
   一个SKU对应一个SPU.
   一个SPU对应多个SKU.',
   'user', @CurrentUser, 'table', 'xlem_sku'
go

/*==============================================================*/
/* Table: xlem_spu                                              */
/*==============================================================*/
create table xlem_spu (
   spu                  varchar(32)          not null,
   categoryid           varchar(256)         null,
   name                 varchar(256)         null,
   description          varchar(4000)        null,
   constraint PK_XLEM_SPU primary key nonclustered (spu)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子商务SPU表.一个SPU对应多个SKU, 一个SKU只对应一个SPU',
   'user', @CurrentUser, 'table', 'xlem_spu'
go

/*==============================================================*/
/* Table: xlem_spucategory                                      */
/*==============================================================*/
create table xlem_spucategory (
   categoryid           varchar(256)         not null,
   name                 varchar(256)         null,
   constraint PK_XLEM_SPUCATEGORY primary key nonclustered (categoryid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'SPU分类',
   'user', @CurrentUser, 'table', 'xlem_spucategory'
go

/*==============================================================*/
/* Table: xlem_spuindex                                         */
/*==============================================================*/
create table xlem_spuindex (
   filename             varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   content              varbinary(max)                null,
   constraint PK_XLEM_SPUINDEX primary key nonclustered (filename, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子商务SPU索引',
   'user', @CurrentUser, 'table', 'xlem_spuindex'
go

/*==============================================================*/
/* Table: xlem_stock                                            */
/*==============================================================*/
create table xlem_stock (
   stkid                numeric(16,0)        not null,
   sku                  varchar(32)          not null,
   quantity             numeric(18,6)        not null,
   direction            numeric(2,0)         not null,
   aunitprice           numeric(18,6)        not null,
   constraint PK_XLEM_STOCK primary key nonclustered (stkid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子商务库存表',
   'user', @CurrentUser, 'table', 'xlem_stock'
go

/*==============================================================*/
/* Table: xlem_stockhistory                                     */
/*==============================================================*/
create table xlem_stockhistory (
   stkid                numeric(16,0)        not null,
   sku                  varchar(32)          null,
   quantity             numeric(18,6)        null,
   direction            numeric(2,0)         null,
   aunitprice           numeric(18,6)        null,
   constraint PK_XLEM_STOCKHISTORY primary key nonclustered (stkid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '库存历史记录表',
   'user', @CurrentUser, 'table', 'xlem_stockhistory'
go

/*==============================================================*/
/* Table: xlem_synonym                                          */
/*==============================================================*/
create table xlem_synonym (
   mergehash            numeric(8,0)         not null,
   srcword              varchar(32)          not null,
   synword              varchar(32)          not null,
   heat                 numeric(8,0)         null,
   constraint PK_XLEM_SYNONYM primary key nonclustered (mergehash)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '同义词表',
   'user', @CurrentUser, 'table', 'xlem_synonym'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '原词和同义词经过排序连接后的hash值, 防止重复添加同义词',
   'user', @CurrentUser, 'table', 'xlem_synonym', 'column', 'mergehash'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '该同义词的使用热度',
   'user', @CurrentUser, 'table', 'xlem_synonym', 'column', 'heat'
go

/*==============================================================*/
/* Table: xlem_unit                                             */
/*==============================================================*/
create table xlem_unit (
   unitid               varchar(32)          not null,
   aunitid              varchar(32)          not null,
   name                 varchar(256)         null,
   exchangerate         numeric(18,6)        not null,
   constraint PK_XLEM_UNIT primary key nonclustered (unitid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '单位表',
   'user', @CurrentUser, 'table', 'xlem_unit'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '单位对原子单位的兑换率',
   'user', @CurrentUser, 'table', 'xlem_unit', 'column', 'exchangerate'
go

/*==============================================================*/
/* Table: xlem_user                                             */
/*==============================================================*/
create table xlem_user (
   userid               varchar(256)         not null,
   name                 varchar(256)         null,
   constraint PK_XLEM_USER primary key nonclustered (userid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子商城用户表, 主要记录用户基本资料',
   'user', @CurrentUser, 'table', 'xlem_user'
go

/*==============================================================*/
/* Table: xlem_userlevel                                        */
/*==============================================================*/
create table xlem_userlevel (
   levelid              varchar(256)         not null,
   name                 varchar(256)         null,
   nextlevel            varchar(256)         null,
   exprequire           numeric(18,6)        null,
   constraint PK_XLEM_USERLEVEL primary key nonclustered (levelid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '等级定义表',
   'user', @CurrentUser, 'table', 'xlem_userlevel'
go

/*==============================================================*/
/* Table: xlfin_account                                         */
/*==============================================================*/
create table xlfin_account (
   accid                varchar(256)         not null,
   name                 varchar(1024)        null,
   adc                  numeric(2,0)         null,
   vdc                  numeric(2,0)         null,
   acctype              numeric(2,0)         null,
   forbankstmt          numeric(2,0)         null,
   constraint PK_XLFIN_ACCOUNT primary key nonclustered (accid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '科目表',
   'user', @CurrentUser, 'table', 'xlfin_account'
go

/*==============================================================*/
/* Table: xlfin_accountcondition                                */
/*==============================================================*/
create table xlfin_accountcondition (
   kdeptid              varchar(256)         not null,
   year                 numeric(8,0)         not null,
   month                numeric(2,0)         not null,
   condition            varchar(256)         null,
   constraint PK_XLFIN_ACCOUNTCONDITION primary key nonclustered (kdeptid, year, month)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '记账状态记录表',
   'user', @CurrentUser, 'table', 'xlfin_accountcondition'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '400:已记账;800:已结账',
   'user', @CurrentUser, 'table', 'xlfin_accountcondition', 'column', 'condition'
go

/*==============================================================*/
/* Table: xlfin_accountingitem                                  */
/*==============================================================*/
create table xlfin_accountingitem (
   vdcol                varchar(256)         not null,
   kdeptids             varchar(4000)        null,
   accids               varchar(4000)        null,
   nasm                 numeric(2,0)         null,
   dbcol                varchar(64)          null,
   cbcol                varchar(64)          null,
   bcol                 varchar(64)          null,
   dvkdeptids           varchar(4000)        null,
   dvaccids             varchar(4000)        null,
   constraint PK_XLFIN_ACCOUNTINGITEM primary key nonclustered (vdcol)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '核算项设置',
   'user', @CurrentUser, 'table', 'xlfin_accountingitem'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证明细字段',
   'user', @CurrentUser, 'table', 'xlfin_accountingitem', 'column', 'vdcol'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Non accounting statistics method
   非核算项的统计方式
   1:sum;2:max;3:min;4:avg',
   'user', @CurrentUser, 'table', 'xlfin_accountingitem', 'column', 'nasm'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当vdc=1时统计放入balance表的字段',
   'user', @CurrentUser, 'table', 'xlfin_accountingitem', 'column', 'dbcol'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当vdc=-1时统计放入balance的字段',
   'user', @CurrentUser, 'table', 'xlfin_accountingitem', 'column', 'cbcol'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '忽略vdc时统计放入balance表的字段',
   'user', @CurrentUser, 'table', 'xlfin_accountingitem', 'column', 'bcol'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '使用该字段进行往来核销的记账部门',
   'user', @CurrentUser, 'table', 'xlfin_accountingitem', 'column', 'dvkdeptids'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '使用该字段进行往来核销的科目',
   'user', @CurrentUser, 'table', 'xlfin_accountingitem', 'column', 'dvaccids'
go

/*==============================================================*/
/* Table: xlfin_balance                                         */
/*==============================================================*/
create table xlfin_balance (
   balanceid            varchar(256)         not null,
   year                 numeric(8,0)         null,
   month                numeric(2,0)         null,
   kdeptid              varchar(256)         null,
   fcrcid               varchar(256)         null,
   accid                varchar(256)         null,
   dfca                 numeric(18,6)        null,
   cfca                 numeric(18,6)        null,
   fcb                  numeric(18,6)        null,
   dsca                 numeric(18,6)        null,
   csca                 numeric(18,6)        null,
   scb                  numeric(18,6)        null,
   drca                 numeric(18,6)        null,
   crca                 numeric(18,6)        null,
   rcb                  numeric(18,6)        null,
   dusda                numeric(18,6)        null,
   cusda                numeric(18,6)        null,
   usdb                 numeric(18,6)        null,
   dquantitya           numeric(18,6)        null,
   cquantitya           numeric(18,6)        null,
   quantityb            numeric(18,6)        null,
   constraint PK_XLFIN_BALANCE primary key nonclustered (balanceid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '余额表',
   'user', @CurrentUser, 'table', 'xlfin_balance'
go

/*==============================================================*/
/* Table: xlfin_balanceitem                                     */
/*==============================================================*/
create table xlfin_balanceitem (
   bcol                 varchar(64)          not null,
   operatormode         numeric(2,0)         null,
   constraint PK_XLFIN_BALANCEITEM primary key nonclustered (bcol)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '余额表项的配置',
   'user', @CurrentUser, 'table', 'xlfin_balanceitem'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'balance column operator mode
   余额表列的运算方式
   1:add;2:max;3:min;4:cover',
   'user', @CurrentUser, 'table', 'xlfin_balanceitem', 'column', 'operatormode'
go

/*==============================================================*/
/* Table: xlfin_bankstmt                                        */
/*==============================================================*/
create table xlfin_bankstmt (
   bsid                 varchar(256)         not null,
   year                 numeric(8,0)         null,
   month                numeric(2,0)         null,
   tradedate            datetime             null,
   kdeptid              varchar(256)         null,
   userid               varchar(256)         null,
   accid                varchar(256)         null,
   bdc                  numeric(2,0)         null,
   fcrcid               varchar(256)         null,
   fcrcamount           numeric(18,6)        null,
   remark               varchar(256)         null,
   vid                  varchar(256)         null,
   vidx                 numeric(8,0)         null,
   bcdate               datetime             null,
   bctype               numeric(2,0)         null,
   constraint PK_XLFIN_BANKSTMT primary key nonclustered (bsid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '银行对账单表',
   'user', @CurrentUser, 'table', 'xlfin_bankstmt'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '对账日期',
   'user', @CurrentUser, 'table', 'xlfin_bankstmt', 'column', 'bcdate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '对账类型
   0:手动;1:自动',
   'user', @CurrentUser, 'table', 'xlfin_bankstmt', 'column', 'bctype'
go

/*==============================================================*/
/* Table: xlfin_bankstmtbalance                                 */
/*==============================================================*/
create table xlfin_bankstmtbalance (
   bsbid                varchar(256)         not null,
   kdeptid              varchar(256)         null,
   fcrcid               varchar(256)         null,
   accid                varchar(256)         null,
   year                 numeric(8,0)         null,
   month                numeric(2,0)         null,
   fcb                  numeric(18,6)        null,
   constraint PK_XLFIN_BANKSTMTBALANCE primary key nonclustered (bsbid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '银行对账单余额表',
   'user', @CurrentUser, 'table', 'xlfin_bankstmtbalance'
go

/*==============================================================*/
/* Table: xlfin_bankstmttemplet                                 */
/*==============================================================*/
create table xlfin_bankstmttemplet (
   bstlid               varchar(256)         not null,
   name                 varchar(64)          null,
   javalistener         varchar(4000)        null,
   jslistener           varbinary(max)                null,
   constraint PK_XLFIN_BANKSTMTTEMPLET primary key nonclustered (bstlid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '银行对账单模板主表',
   'user', @CurrentUser, 'table', 'xlfin_bankstmttemplet'
go

/*==============================================================*/
/* Table: xlfin_bstldt                                          */
/*==============================================================*/
create table xlfin_bstldt (
   bstlid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   templetcol           varchar(256)         null,
   bscol                varchar(256)         null,
   constraint PK_XLFIN_BSTLDT primary key nonclustered (bstlid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '银行对账单模板明细',
   'user', @CurrentUser, 'table', 'xlfin_bstldt'
go

/*==============================================================*/
/* Table: xlfin_currency                                        */
/*==============================================================*/
create table xlfin_currency (
   crcid                varchar(256)         not null,
   name                 varchar(128)         null,
   crccode              varchar(8)           null,
   constraint PK_XLFIN_CURRENCY primary key nonclustered (crcid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '币种表',
   'user', @CurrentUser, 'table', 'xlfin_currency'
go

/*==============================================================*/
/* Table: xlfin_exchangerate                                    */
/*==============================================================*/
create table xlfin_exchangerate (
   erid                 varchar(256)         not null,
   crcid                varchar(256)         null,
   buyingrate           numeric(18,6)        null,
   cashbuyingrate       numeric(18,6)        null,
   sellingrate          numeric(18,6)        null,
   cashsellingrate      numeric(18,6)        null,
   middlerate           numeric(18,6)        null,
   pubtime              datetime             null,
   constraint PK_XLFIN_EXCHANGERATE primary key nonclustered (erid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '汇率表
   http://www.boc.cn/sourcedb/whpj/enindex.html',
   'user', @CurrentUser, 'table', 'xlfin_exchangerate'
go

/*==============================================================*/
/* Table: xlfin_karaccdt                                        */
/*==============================================================*/
create table xlfin_karaccdt (
   karid                varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   accids               varchar(4000)        null,
   showcolumns          varchar(4000)        null,
   constraint PK_XLFIN_KARACCDT primary key nonclustered (karid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '记账部门科目关系明细配置',
   'user', @CurrentUser, 'table', 'xlfin_karaccdt'
go

/*==============================================================*/
/* Table: xlfin_kardt                                           */
/*==============================================================*/
create table xlfin_kardt (
   karid                varchar(256)         not null,
   vdtecaid             varchar(256)         not null,
   name                 varchar(64)          null,
   constraint PK_XLFIN_KARDT primary key nonclustered (karid, vdtecaid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '记账部门对应科目所拥有的字段关系表',
   'user', @CurrentUser, 'table', 'xlfin_kardt'
go

/*==============================================================*/
/* Table: xlfin_kdeptaccrealtion                                */
/*==============================================================*/
create table xlfin_kdeptaccrealtion (
   karid                varchar(256)         not null,
   name                 varchar(64)          null,
   accids               varchar(4000)        null,
   constraint PK_XLFIN_KDEPTACCREALTION primary key nonclustered (karid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '科目关系定义主表',
   'user', @CurrentUser, 'table', 'xlfin_kdeptaccrealtion'
go

/*==============================================================*/
/* Table: xlfin_keepdept                                        */
/*==============================================================*/
create table xlfin_keepdept (
   kdeptid              varchar(256)         not null,
   name                 varchar(64)          null,
   standardcrcid        varchar(256)         null,
   reportcrcid          varchar(256)         null,
   usedkarid            varchar(256)         null,
   nocarryoverkarid     varchar(256)         null,
   cockarid             varchar(256)         null,
   needdcequal          numeric(2,0)         null,
   vdatemode            numeric(2,0)         null,
   transfermode         numeric(2,0)         null,
   beginvdate           datetime             null,
   kdepttype            numeric(2,0)         null,
   beginbsdate          datetime             null,
   constraint PK_XLFIN_KEEPDEPT primary key nonclustered (kdeptid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '记账部门',
   'user', @CurrentUser, 'table', 'xlfin_keepdept'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证日期模式
   0:与当前日期同月时使用当前日期
   1:与当前日期同月时使用上一次凭证日期',
   'user', @CurrentUser, 'table', 'xlfin_keepdept', 'column', 'vdatemode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '转账模式
   0:转入时合并
   1:转入时不合并',
   'user', @CurrentUser, 'table', 'xlfin_keepdept', 'column', 'transfermode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '期初日期，该记账部门的凭证日期只能大于等于该日期',
   'user', @CurrentUser, 'table', 'xlfin_keepdept', 'column', 'beginvdate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '记账部门类型, 0:记账部门下属;1:记账部门节点
   只有为1时该条记录才是系统使用的真正的记账部门',
   'user', @CurrentUser, 'table', 'xlfin_keepdept', 'column', 'kdepttype'
go

/*==============================================================*/
/* Table: xlfin_reportdata                                      */
/*==============================================================*/
create table xlfin_reportdata (
   rdid                 varchar(256)         not null,
   rfid                 varchar(256)         not null,
   rdeptid              varchar(256)         not null,
   timeperiod           numeric(2,0)         null,
   begindate            datetime             not null,
   enddate              datetime             not null,
   constraint PK_XLFIN_REPORTDATA primary key nonclustered (rdid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表数据',
   'user', @CurrentUser, 'table', 'xlfin_reportdata'
go

/*==============================================================*/
/* Index: un_rrbe                                               */
/*==============================================================*/
create unique index un_rrbe on xlfin_reportdata (
rfid ASC,
rdeptid ASC,
begindate ASC,
enddate ASC
)
go

/*==============================================================*/
/* Table: xlfin_reportdatadetail                                */
/*==============================================================*/
create table xlfin_reportdatadetail (
   rdid                 varchar(256)         not null,
   idx                  numeric(2,0)         not null,
   rfrowid              varchar(256)         not null,
   rfcolid              varchar(256)         not null,
   value                varchar(4000)        null,
   constraint PK_XLFIN_REPORTDATADETAIL primary key nonclustered (rdid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表数据明细',
   'user', @CurrentUser, 'table', 'xlfin_reportdatadetail'
go

/*==============================================================*/
/* Table: xlfin_reportdept                                      */
/*==============================================================*/
create table xlfin_reportdept (
   rdeptid              varchar(256)         not null,
   name                 varchar(256)         null,
   kdeptids             varchar(4000)        null,
   gatherrdeptids       varchar(4000)        null,
   constraint PK_XLFIN_REPORTDEPT primary key nonclustered (rdeptid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上报单位定义表',
   'user', @CurrentUser, 'table', 'xlfin_reportdept'
go

/*==============================================================*/
/* Table: xlfin_reportform                                      */
/*==============================================================*/
create table xlfin_reportform (
   rfid                 varchar(256)         not null,
   name                 varchar(64)          null,
   javalistener         varchar(4000)        null,
   jslistener           varbinary(max)                null,
   constraint PK_XLFIN_REPORTFORM primary key nonclustered (rfid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表定义',
   'user', @CurrentUser, 'table', 'xlfin_reportform'
go

/*==============================================================*/
/* Table: xlfin_reportformcol                                   */
/*==============================================================*/
create table xlfin_reportformcol (
   rfid                 varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   name                 varchar(64)          null,
   rfcolid              varchar(256)         not null,
   datatype             numeric(2,0)         not null,
   param                varchar(4000)        null,
   constraint PK_XLFIN_REPORTFORMCOL primary key nonclustered (rfid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表列定义',
   'user', @CurrentUser, 'table', 'xlfin_reportformcol'
go

/*==============================================================*/
/* Table: xlfin_reportformformula                               */
/*==============================================================*/
create table xlfin_reportformformula (
   rfid                 varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   rfrowid              varchar(256)         not null,
   rfcolid              varchar(256)         not null,
   formula              varbinary(max)                null,
   constraint PK_XLFIN_REPORTFORMFORMULA primary key nonclustered (rfid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表公式定义',
   'user', @CurrentUser, 'table', 'xlfin_reportformformula'
go

/*==============================================================*/
/* Table: xlfin_reportformrow                                   */
/*==============================================================*/
create table xlfin_reportformrow (
   rfid                 varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   name                 varchar(64)          null,
   rfrowid              varchar(256)         not null,
   constraint PK_XLFIN_REPORTFORMROW primary key nonclustered (rfid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表行定义',
   'user', @CurrentUser, 'table', 'xlfin_reportformrow'
go

/*==============================================================*/
/* Table: xlfin_vdtextra                                        */
/*==============================================================*/
create table xlfin_vdtextra (
   voucherid            varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   extracol             varchar(256)         not null,
   colvalue             varchar(4000)        null,
   constraint PK_XLFIN_VDTEXTRA primary key nonclustered (voucherid, idx, extracol)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证明细附加表',
   'user', @CurrentUser, 'table', 'xlfin_vdtextra'
go

/*==============================================================*/
/* Table: xlfin_vdtextracol                                     */
/*==============================================================*/
create table xlfin_vdtextracol (
   extracol             varchar(256)         not null,
   datatype             numeric(2,0)         not null,
   name                 varchar(64)          null,
   constraint PK_XLFIN_VDTEXTRACOL primary key nonclustered (extracol)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证明细可附加字段定义',
   'user', @CurrentUser, 'table', 'xlfin_vdtextracol'
go

/*==============================================================*/
/* Table: xlfin_vdtextracolattr                                 */
/*==============================================================*/
create table xlfin_vdtextracolattr (
   vdtecaid             varchar(256)         not null,
   extracol             varchar(256)         not null,
   name                 varchar(64)          null,
   type                 numeric(2,0)         null,
   param                varchar(4000)        null,
   supportvalue         varchar(4000)        null,
   constraint PK_XLFIN_VDTEXTRACOLATTR primary key nonclustered (vdtecaid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证明细附加字段属性定义',
   'user', @CurrentUser, 'table', 'xlfin_vdtextracolattr'
go

/*==============================================================*/
/* Table: xlfin_voucher                                         */
/*==============================================================*/
create table xlfin_voucher (
   voucherid            varchar(256)         not null,
   flowid               varchar(256)         null,
   creater              varchar(256)         null,
   creationdate         datetime             null,
   modifydate           datetime             null,
   condition            varchar(256)         null,
   kdeptid              varchar(256)         null,
   id                   varchar(256)         null,
   vno                  numeric(8,0)         null,
   year                 numeric(8,0)         null,
   month                numeric(2,0)         null,
   vdate                datetime             null,
   attachno             numeric(2,0)         null,
   accounter            varchar(256)         null,
   creationmode         numeric(2,0)         not null,
   constraint PK_XLFIN_VOUCHER primary key nonclustered (voucherid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证表',
   'user', @CurrentUser, 'table', 'xlfin_voucher'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证生成模式
   0:手动生成;90:年初结转的未达账数据;91:年初结转的往来未核销数据;1~89:业务各接口数据;90~99:财务自动生成数据',
   'user', @CurrentUser, 'table', 'xlfin_voucher', 'column', 'creationmode'
go

/*==============================================================*/
/* Table: xlfin_voucherdetail                                   */
/*==============================================================*/
create table xlfin_voucherdetail (
   voucherid            varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   year                 numeric(8,0)         null,
   month                numeric(2,0)         null,
   vno                  numeric(8,0)         null,
   vdate                datetime             null,
   kdeptid              varchar(256)         null,
   vdc                  numeric(2,0)         null,
   fcrcid               varchar(256)         null,
   accid                varchar(256)         null,
   userid               varchar(256)         null,
   fcrcamount           numeric(18,6)        null,
   ftosrate             numeric(18,6)        null,
   ftorrate             numeric(18,6)        null,
   ftousdrate           numeric(18,6)        null,
   quantity             numeric(18,6)        null,
   remark               varchar(256)         null,
   bsid                 varchar(256)         null,
   bcdate               datetime             null,
   bctype               numeric(2,0)         null,
   constraint PK_XLFIN_VOUCHERDETAIL primary key nonclustered (voucherid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证明细',
   'user', @CurrentUser, 'table', 'xlfin_voucherdetail'
go

/*==============================================================*/
/* Table: xlfin_vouchertemplet                                  */
/*==============================================================*/
create table xlfin_vouchertemplet (
   vtlid                varchar(256)         not null,
   name                 varchar(64)          null,
   javalistener         varchar(4000)        null,
   jslistener           varbinary(max)                null,
   querysql             varchar(4000)        null,
   creationmode         numeric(2,0)         not null,
   constraint PK_XLFIN_VOUCHERTEMPLET primary key nonclustered (vtlid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证模板定义主表',
   'user', @CurrentUser, 'table', 'xlfin_vouchertemplet'
go

/*==============================================================*/
/* Table: xlfin_vtempletdt                                      */
/*==============================================================*/
create table xlfin_vtempletdt (
   vtlid                varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   name                 varchar(64)          null,
   vrowid               varchar(256)         null,
   remark               varchar(256)         null,
   vdc                  numeric(2,0)         null,
   accid                varchar(256)         null,
   querysql             varchar(4000)        null,
   formula              varbinary(max)                null,
   constraint PK_XLFIN_VTEMPLETDT primary key nonclustered (idx, vtlid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证模板明细配置',
   'user', @CurrentUser, 'table', 'xlfin_vtempletdt'
go

/*==============================================================*/
/* Table: xlfin_vtldtcol                                        */
/*==============================================================*/
create table xlfin_vtldtcol (
   vtlid                varchar(256)         not null,
   vtldtidx             numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   mdscolname           varchar(256)         null,
   dtdscolname          varchar(256)         null,
   vdtcolname           varchar(256)         null,
   constraint PK_XLFIN_VTLDTCOL primary key nonclustered (vtlid, vtldtidx, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '凭证模板明细字段配置',
   'user', @CurrentUser, 'table', 'xlfin_vtldtcol'
go

/*==============================================================*/
/* Table: xlsys_attachment                                      */
/*==============================================================*/
create table xlsys_attachment (
   attachid             numeric(8,0)         not null,
   name                 varchar(256)         null,
   attachdata           varbinary(max)                null,
   constraint PK_XLSYS_ATTACHMENT primary key nonclustered (attachid)
)
go

/*==============================================================*/
/* Table: xlsys_authorisedright                                 */
/*==============================================================*/
create table xlsys_authorisedright (
   arid                 varchar(256)         not null,
   ardtidx              numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   constraint PK_XLSYS_AUTHORISEDRIGHT primary key nonclustered (arid, ardtidx, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '被授权权限',
   'user', @CurrentUser, 'table', 'xlsys_authorisedright'
go

/*==============================================================*/
/* Table: xlsys_authorize                                       */
/*==============================================================*/
create table xlsys_authorize (
   arid                 varchar(256)         not null,
   arno                 varchar(256)         null,
   flowid               varchar(256)         null,
   id                   varchar(256)         null,
   creater              varchar(256)         null,
   creationdate         datetime             null,
   modifydate           datetime             null,
   condition            varchar(256)         null,
   authorisedid         varchar(256)         null,
   begindate            datetime             null,
   enddate              datetime             null,
   remark               varchar(4000)        null,
   constraint PK_XLSYS_AUTHORIZE primary key nonclustered (arid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权主表',
   'user', @CurrentUser, 'table', 'xlsys_authorize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权身份',
   'user', @CurrentUser, 'table', 'xlsys_authorize', 'column', 'authorisedid'
go

/*==============================================================*/
/* Table: xlsys_authorizedetail                                 */
/*==============================================================*/
create table xlsys_authorizedetail (
   arid                 varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   arflowid             varchar(256)         null,
   arcondition          varchar(256)         null,
   beauthorizedid       varchar(256)         null,
   constraint PK_XLSYS_AUTHORIZEDETAIL primary key nonclustered (arid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权明细表',
   'user', @CurrentUser, 'table', 'xlsys_authorizedetail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权流程',
   'user', @CurrentUser, 'table', 'xlsys_authorizedetail', 'column', 'arflowid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权状态',
   'user', @CurrentUser, 'table', 'xlsys_authorizedetail', 'column', 'arcondition'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '被授权身份',
   'user', @CurrentUser, 'table', 'xlsys_authorizedetail', 'column', 'beauthorizedid'
go

/*==============================================================*/
/* Index: un_ad_fci                                             */
/*==============================================================*/
create unique index un_ad_fci on xlsys_authorizedetail (
arflowid ASC,
arcondition ASC,
beauthorizedid ASC
)
go

/*==============================================================*/
/* Table: xlsys_basebusi                                        */
/*==============================================================*/
create table xlsys_basebusi (
   busiid               varchar(256)         not null,
   busino               varchar(256)         null,
   name                 varchar(64)          null,
   flowid               varchar(256)         not null,
   id                   varchar(256)         null,
   creater              varchar(256)         null,
   creationdate         datetime             not null,
   modifydate           datetime             not null,
   condition            varchar(256)         not null,
   constraint PK_XLSYS_BASEBUSI primary key nonclustered (busiid)
)
go

/*==============================================================*/
/* Table: xlsys_codealloc                                       */
/*==============================================================*/
create table xlsys_codealloc (
   caid                 varchar(256)         not null,
   name                 varchar(256)         null,
   clientjavascript     varbinary(max)                null,
   clientjavamethod     varchar(4000)        null,
   serverjavascript     varbinary(max)                null,
   serverjavamethod     varchar(4000)        null,
   constraint PK_XLSYS_CODEALLOC primary key nonclustered (caid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'define alloc code method
   select ''insert into xlsys_codealloc(caid,name,javamethod) values(''''''||caid||'''''',''''''||name||'''''',''''''||javamethod||'''''');'' from xlsys_codealloc;',
   'user', @CurrentUser, 'table', 'xlsys_codealloc'
go

/*==============================================================*/
/* Table: xlsys_db                                              */
/*==============================================================*/
create table xlsys_db (
   dbid                 numeric(8,0)         not null,
   name                 varchar(256)         not null,
   datasource           varchar(512)         null,
   username             varchar(256)         null,
   password             varchar(256)         null,
   corepoolsize         numeric(8,0)         null,
   maximumpoolsize      numeric(8,0)         null,
   keepalivetime        numeric(8,0)         null,
   queuecapacity        numeric(8,0)         null,
   constraint PK_XLSYS_DB primary key nonclustered (dbid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据库连接表. 该表记录了可访问的数据库连接信息',
   'user', @CurrentUser, 'table', 'xlsys_db'
go

/*==============================================================*/
/* Table: xlsys_department                                      */
/*==============================================================*/
create table xlsys_department (
   deptid               varchar(256)         not null,
   name                 varchar(64)          not null,
   constraint PK_XLSYS_DEPARTMENT primary key nonclustered (deptid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Department table',
   'user', @CurrentUser, 'table', 'xlsys_department'
go

/*==============================================================*/
/* Table: xlsys_dict                                            */
/*==============================================================*/
create table xlsys_dict (
   dictid               varchar(256)         not null,
   name                 varchar(256)         null,
   constraint PK_XLSYS_DICT primary key nonclustered (dictid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The dictionary of xlsys',
   'user', @CurrentUser, 'table', 'xlsys_dict'
go

/*==============================================================*/
/* Table: xlsys_dictdetail                                      */
/*==============================================================*/
create table xlsys_dictdetail (
   dictid               varchar(256)         not null,
   code                 varchar(64)          not null,
   name                 varchar(256)         null,
   constraint PK_XLSYS_DICTDETAIL primary key nonclustered (dictid, code)
)
go

/*==============================================================*/
/* Table: xlsys_emailtemplate                                   */
/*==============================================================*/
create table xlsys_emailtemplate (
   etid                 varchar(256)         not null,
   name                 varchar(256)         null,
   template             varbinary(max)                null,
   javalistener         varchar(4000)        null,
   jslistener           varbinary(max)                null,
   constraint PK_XLSYS_EMAILTEMPLATE primary key nonclustered (etid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Email模板定义',
   'user', @CurrentUser, 'table', 'xlsys_emailtemplate'
go

/*==============================================================*/
/* Table: xlsys_env                                             */
/*==============================================================*/
create table xlsys_env (
   envid                varchar(256)         not null,
   name                 varchar(256)         not null,
   constraint PK_XLSYS_ENV primary key nonclustered (envid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统环境表. 该表记录了系统可登陆的环境',
   'user', @CurrentUser, 'table', 'xlsys_env'
go

/*==============================================================*/
/* Table: xlsys_envdetail                                       */
/*==============================================================*/
create table xlsys_envdetail (
   envid                varchar(256)         not null,
   tableregex           varchar(256)         not null,
   dbid                 numeric(8,0)         not null,
   name                 varchar(256)         null,
   constraint PK_XLSYS_ENVDETAIL primary key nonclustered (envid, dbid, tableregex)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统环境明细表. 该表主要记录每个环境下哪些表使用哪些数据库去访问',
   'user', @CurrentUser, 'table', 'xlsys_envdetail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '表名匹配表达式, 只能写一个, 可使用正则表达式',
   'user', @CurrentUser, 'table', 'xlsys_envdetail', 'column', 'tableregex'
go

/*==============================================================*/
/* Table: xlsys_extracmd                                        */
/*==============================================================*/
create table xlsys_extracmd (
   extracmd             varchar(256)         not null,
   name                 varchar(64)          null,
   dispatchpath         varchar(256)         null,
   javalistener         varchar(4000)        null,
   jslistener           varbinary(max)                null,
   constraint PK_XLSYS_EXTRACMD primary key nonclustered (extracmd)
)
go

/*==============================================================*/
/* Table: xlsys_exttableinfo                                    */
/*==============================================================*/
create table xlsys_exttableinfo (
   tableid              numeric(8,0)         not null,
   name                 varchar(64)          null,
   tablename            varchar(256)         null,
   constraint PK_XLSYS_EXTTABLEINFO primary key nonclustered (tableid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Exter table information',
   'user', @CurrentUser, 'table', 'xlsys_exttableinfo'
go

/*==============================================================*/
/* Table: xlsys_exttableinfodetail                              */
/*==============================================================*/
create table xlsys_exttableinfodetail (
   tableid              numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   colname              varchar(64)          null,
   name                 varchar(64)          null,
   primarykey           numeric(2,0)         null,
   nullable             numeric(2,0)         null,
   constraint PK_XLSYS_EXTTABLEINFODETAIL primary key nonclustered (tableid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The detail of exter table information',
   'user', @CurrentUser, 'table', 'xlsys_exttableinfodetail'
go

/*==============================================================*/
/* Table: xlsys_flow                                            */
/*==============================================================*/
create table xlsys_flow (
   flowid               varchar(256)         not null,
   name                 varchar(64)          null,
   listpartid           varchar(256)         null,
   mvidoflpart          numeric(8,0)         null,
   mainpartid           varchar(256)         null,
   mvidofmpart          numeric(8,0)         null,
   maintable            varchar(64)          null,
   innercodecol         varchar(64)          null,
   outtercodecol        varchar(64)          null,
   jslistener           varbinary(max)                null,
   javalistener         varchar(4000)        null,
   versionupdate        numeric(2,0)         null,
   cancopy              numeric(2,0)         null,
   constraint PK_XLSYS_FLOW primary key nonclustered (flowid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Flow Define
   select ''insert into XLSYS_FLOW(FLOWID,NAME,LISTPARTID,MAINPARTID,MAINTABLE,INNERCODECOL,OUTTERCODECOL,JAVALISTENER) values(''''''||FLOWID||'''''',''''''||name||'''''',''''''||LISTPARTID||'''''',''''''||MAINPARTID||'''''',''''''||MAINTABLE||'''''',''''''||INNERCODECOL||'''''',''''''||OUTTERCODECOL||'''''',''''''||JAVALISTENER||'''''');'' from XLSYS_FLOW;',
   'user', @CurrentUser, 'table', 'xlsys_flow'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否允许版本更新',
   'user', @CurrentUser, 'table', 'xlsys_flow', 'column', 'versionupdate'
go

/*==============================================================*/
/* Table: xlsys_flowcodealloc                                   */
/*==============================================================*/
create table xlsys_flowcodealloc (
   fcaid                varchar(256)         not null,
   flowid               varchar(256)         null,
   tablename            varchar(64)          not null,
   colname              varchar(64)          not null,
   caid                 varchar(256)         null,
   constraint PK_XLSYS_FLOWCODEALLOC primary key nonclustered (fcaid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'define code creation for flow',
   'user', @CurrentUser, 'table', 'xlsys_flowcodealloc'
go

/*==============================================================*/
/* Table: xlsys_flowcondition                                   */
/*==============================================================*/
create table xlsys_flowcondition (
   flowid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   condition            varchar(64)          not null,
   name                 varchar(256)         null,
   audittype            numeric(2,0)         null,
   voterate             numeric(18,6)        null,
   constraint PK_XLSYS_FLOWCONDITION primary key nonclustered (flowid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The condition of flow',
   'user', @CurrentUser, 'table', 'xlsys_flowcondition'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '审批类型, 0:单审;1:会审;2:组单审;3:组会审;4:投票审
   [单审] : 任意一个人通过即可通过(提交时允许选择审批人)
   [会审] : 所有人通过才可通过(提交时不允许选择审批人)
   [组单审] : 任意一组人通过即可通过, 同一组里的人必须全部通过才算通过(提交时允许选择审批组，不允许选择审批人)
   [组会审] : 所有组的人都通过才可通过, 同一组里的人只有要任意一个人通过就算通过(提交时允许选择审批人，但是每个组都必须选择至少一个审批人)
   [投票审] : 按照一定比例票数通过后即可通过(提交时不允许选择)',
   'user', @CurrentUser, 'table', 'xlsys_flowcondition', 'column', 'audittype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投票率，当audittype为4:投票审时，此参数有效',
   'user', @CurrentUser, 'table', 'xlsys_flowcondition', 'column', 'voterate'
go

/*==============================================================*/
/* Table: xlsys_flowjava                                        */
/*==============================================================*/
create table xlsys_flowjava (
   flowid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   viewid               numeric(8,0)         null,
   javalistener         varchar(4000)        null,
   constraint PK_XLSYS_FLOWJAVA primary key nonclustered (flowid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'JavaListener for viewers of flow',
   'user', @CurrentUser, 'table', 'xlsys_flowjava'
go

/*==============================================================*/
/* Table: xlsys_flowjs                                          */
/*==============================================================*/
create table xlsys_flowjs (
   flowid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   viewid               numeric(8,0)         null,
   jslistener           varbinary(max)                null,
   constraint PK_XLSYS_FLOWJS primary key nonclustered (flowid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'JsListener for viewers of flow ',
   'user', @CurrentUser, 'table', 'xlsys_flowjs'
go

/*==============================================================*/
/* Table: xlsys_flowlogic                                       */
/*==============================================================*/
create table xlsys_flowlogic (
   flowid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   fromcondition        varchar(64)          null,
   tocondition          varchar(64)          null,
   passtype             numeric(2,0)         null,
   rejecttype           numeric(2,0)         null,
   canrejectto          varchar(64)          null,
   clientautotriggersubmit numeric(2,0)         null,
   constraint PK_XLSYS_FLOWLOGIC primary key nonclustered (flowid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The logic of each flow',
   'user', @CurrentUser, 'table', 'xlsys_flowlogic'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:手动;1:自动提交;2:自动审批;3:自动审批并自动提交',
   'user', @CurrentUser, 'table', 'xlsys_flowlogic', 'column', 'passtype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '驳回类型,0:不允许驳回;1:可驳回到上一级;2:可驳回到任意上级;3:只允许驳回到自定义上级',
   'user', @CurrentUser, 'table', 'xlsys_flowlogic', 'column', 'rejecttype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否允许客户端自动触发提交按钮',
   'user', @CurrentUser, 'table', 'xlsys_flowlogic', 'column', 'clientautotriggersubmit'
go

/*==============================================================*/
/* Table: xlsys_flowpart                                        */
/*==============================================================*/
create table xlsys_flowpart (
   flowid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   clienttype           varchar(32)          null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   listpartid           varchar(256)         null,
   mvidoflpart          numeric(8,0)         null,
   mainpartid           varchar(256)         null,
   mvidofmpart          numeric(8,0)         null,
   constraint PK_XLSYS_FLOWPART primary key nonclustered (flowid, idx)
)
go

/*==============================================================*/
/* Table: xlsys_flowright                                       */
/*==============================================================*/
create table xlsys_flowright (
   flowid               varchar(256)         not null,
   cdtidx               numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   belongrighttype      numeric(2,0)         null,
   belongrightvalue     varchar(256)         null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   groupnm              varchar(256)         null,
   conditiongrp         varchar(32)          null,
   constraint PK_XLSYS_FLOWRIGHT primary key nonclustered (flowid, cdtidx, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The right of each flow condition',
   'user', @CurrentUser, 'table', 'xlsys_flowright'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:identity;1:user;2:department;3:position',
   'user', @CurrentUser, 'table', 'xlsys_flowright', 'column', 'belongrighttype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:identity;1:user;2:department;3:position',
   'user', @CurrentUser, 'table', 'xlsys_flowright', 'column', 'righttype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分组名称,当audittype选用 2:组单审和 3:组会审 时有效，用来标识当前权限为哪个组别所有，分组名称相同的视为同一组',
   'user', @CurrentUser, 'table', 'xlsys_flowright', 'column', 'groupnm'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '条件组, 组名一样的权限配置将使用and来连接, 否则使用or连接',
   'user', @CurrentUser, 'table', 'xlsys_flowright', 'column', 'conditiongrp'
go

/*==============================================================*/
/* Table: xlsys_flowsubtable                                    */
/*==============================================================*/
create table xlsys_flowsubtable (
   flowid               varchar(256)         not null,
   tablename            varchar(64)          not null,
   relationinnercol     varchar(64)          null,
   constraint PK_XLSYS_FLOWSUBTABLE primary key nonclustered (flowid, tablename)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程子表配置',
   'user', @CurrentUser, 'table', 'xlsys_flowsubtable'
go

/*==============================================================*/
/* Table: xlsys_identity                                        */
/*==============================================================*/
create table xlsys_identity (
   id                   varchar(256)         not null,
   name                 varchar(64)          not null,
   welcomemenuid        varchar(256)         null,
   frameid              numeric(8,0)         null,
   closedisable         numeric(2,0)         null,
   constraint PK_XLSYS_IDENTITY primary key nonclustered (id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Identity for Xue Lang System, it is unique for Xlsys',
   'user', @CurrentUser, 'table', 'xlsys_identity'
go

/*==============================================================*/
/* Table: xlsys_idrelation                                      */
/*==============================================================*/
create table xlsys_idrelation (
   id                   varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   name                 varchar(64)          null,
   righttype            numeric(2,0)         not null,
   relationvalue        varchar(32)          null,
   constraint PK_XLSYS_IDRELATION primary key nonclustered (idx, id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The relationship of Identity.',
   'user', @CurrentUser, 'table', 'xlsys_idrelation'
go

/*==============================================================*/
/* Index: xlsys_idrelation_uq                                   */
/*==============================================================*/
create unique index xlsys_idrelation_uq on xlsys_idrelation (
id ASC,
righttype ASC,
relationvalue ASC
)
go

/*==============================================================*/
/* Index: xlsys_idrelation_tcv                                  */
/*==============================================================*/
create index xlsys_idrelation_tcv on xlsys_idrelation (
righttype ASC,
relationvalue ASC
)
go

/*==============================================================*/
/* Table: xlsys_image                                           */
/*==============================================================*/
create table xlsys_image (
   imageid              numeric(8,0)         not null,
   name                 varchar(256)         null,
   imagedata            varbinary(max)                null,
   constraint PK_XLSYS_IMAGE primary key nonclustered (imageid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '图片库',
   'user', @CurrentUser, 'table', 'xlsys_image'
go

/*==============================================================*/
/* Table: xlsys_ipresource                                      */
/*==============================================================*/
create table xlsys_ipresource (
   ipaddress            varchar(64)          not null,
   ipresource           varbinary(max)                null,
   constraint PK_XLSYS_IPRESOURCE primary key nonclustered (ipaddress)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'IP相关资源信息',
   'user', @CurrentUser, 'table', 'xlsys_ipresource'
go

/*==============================================================*/
/* Table: xlsys_javaclass                                       */
/*==============================================================*/
create table xlsys_javaclass (
   classid              varchar(512)         not null,
   name                 varchar(64)          null,
   javasource           varbinary(max)                null,
   javabinary           varbinary(max)                null,
   constraint PK_XLSYS_JAVACLASS primary key nonclustered (classid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The Additional Java Class',
   'user', @CurrentUser, 'table', 'xlsys_javaclass'
go

/*==============================================================*/
/* Table: xlsys_menu                                            */
/*==============================================================*/
create table xlsys_menu (
   menuid               varchar(256)         not null,
   name                 varchar(64)          null,
   type                 numeric(2,0)         null,
   cmd                  varchar(256)         null,
   param                varchar(4000)        null,
   icon                 varbinary(max)                null,
   shortcut             varchar(64)          null,
   showninphone         numeric(1,0)         null,
   constraint PK_XLSYS_MENU primary key nonclustered (menuid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Define Menu',
   'user', @CurrentUser, 'table', 'xlsys_menu'
go

/*==============================================================*/
/* Table: xlsys_menuright                                       */
/*==============================================================*/
create table xlsys_menuright (
   menuid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   constraint PK_XLSYS_MENURIGHT primary key nonclustered (menuid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:identity;1:user;2:department;3:position',
   'user', @CurrentUser, 'table', 'xlsys_menuright', 'column', 'righttype'
go

/*==============================================================*/
/* Table: xlsys_oacategory                                      */
/*==============================================================*/
create table xlsys_oacategory (
   oacid                varchar(256)         not null,
   name                 varchar(256)         not null,
   icon                 varbinary(max)                null,
   constraint PK_XLSYS_OACATEGORY primary key nonclustered (oacid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'OA类别定义表',
   'user', @CurrentUser, 'table', 'xlsys_oacategory'
go

/*==============================================================*/
/* Table: xlsys_oacategoryright                                 */
/*==============================================================*/
create table xlsys_oacategoryright (
   oacid                varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   constraint PK_XLSYS_OACATEGORYRIGHT primary key nonclustered (oacid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'OA分类权限定义表',
   'user', @CurrentUser, 'table', 'xlsys_oacategoryright'
go

/*==============================================================*/
/* Table: xlsys_oacmbelong                                      */
/*==============================================================*/
create table xlsys_oacmbelong (
   oacid                varchar(256)         not null,
   oamid                varchar(256)         not null,
   name                 varchar(256)         null,
   constraint PK_XLSYS_OACMBELONG primary key nonclustered (oacid, oamid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'OA分类和OA模块的所属关系表',
   'user', @CurrentUser, 'table', 'xlsys_oacmbelong'
go

/*==============================================================*/
/* Table: xlsys_oacmrelation                                    */
/*==============================================================*/
create table xlsys_oacmrelation (
   id                   varchar(256)         not null,
   idx                  numeric(2,0)         not null,
   oacid                varchar(256)         null,
   oamid                varchar(256)         null,
   name                 varchar(256)         null,
   sheetname            varchar(256)         null,
   hpercent             numeric(4,2)         null,
   vpixel               numeric(8,0)         null,
   constraint PK_XLSYS_OACMRELATION primary key nonclustered (idx, id)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'OA分类和OA模块的关系定义表',
   'user', @CurrentUser, 'table', 'xlsys_oacmrelation'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分页名称',
   'user', @CurrentUser, 'table', 'xlsys_oacmrelation', 'column', 'sheetname'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '横向占百分比',
   'user', @CurrentUser, 'table', 'xlsys_oacmrelation', 'column', 'hpercent'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '纵向占像素',
   'user', @CurrentUser, 'table', 'xlsys_oacmrelation', 'column', 'vpixel'
go

/*==============================================================*/
/* Table: xlsys_oamodule                                        */
/*==============================================================*/
create table xlsys_oamodule (
   oamid                varchar(256)         not null,
   name                 varchar(256)         null,
   cmd                  varchar(256)         null,
   param                varchar(4000)        null,
   constraint PK_XLSYS_OAMODULE primary key nonclustered (oamid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'OA模块定义表',
   'user', @CurrentUser, 'table', 'xlsys_oamodule'
go

/*==============================================================*/
/* Table: xlsys_oamoduleextra                                   */
/*==============================================================*/
create table xlsys_oamoduleextra (
   oamid                varchar(256)         not null,
   viewid               numeric(8,0)         not null,
   extcmd               varchar(256)         null,
   extparam             varchar(4000)        null,
   constraint PK_XLSYS_OAMODULEEXTRA primary key nonclustered (oamid, viewid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'OA模块附加窗口配置',
   'user', @CurrentUser, 'table', 'xlsys_oamoduleextra'
go

/*==============================================================*/
/* Table: xlsys_oamoduleright                                   */
/*==============================================================*/
create table xlsys_oamoduleright (
   oamid                varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   constraint PK_XLSYS_OAMODULERIGHT primary key nonclustered (oamid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'OA模块权限定义表',
   'user', @CurrentUser, 'table', 'xlsys_oamoduleright'
go

/*==============================================================*/
/* Table: xlsys_part                                            */
/*==============================================================*/
create table xlsys_part (
   partid               varchar(256)         not null,
   name                 varchar(64)          not null,
   parttype             numeric(2,0)         not null,
   constraint PK_XLSYS_PART primary key nonclustered (partid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The Main Table Of Defining Part',
   'user', @CurrentUser, 'table', 'xlsys_part'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:Node;1:Part',
   'user', @CurrentUser, 'table', 'xlsys_part', 'column', 'parttype'
go

/*==============================================================*/
/* Table: xlsys_partdetail                                      */
/*==============================================================*/
create table xlsys_partdetail (
   partid               varchar(256)         not null,
   detailid             varchar(256)         not null,
   name                 varchar(64)          null,
   type                 numeric(2,0)         null,
   param                varchar(4000)        null,
   viewid               numeric(8,0)         null,
   relationtype         numeric(2,0)         null,
   mainviewid           numeric(8,0)         null,
   soporder             numeric(8,0)         null,
   diyimplement         varchar(1000)        null,
   constraint PK_XLSYS_PARTDETAIL primary key nonclustered (partid, detailid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The detail of part',
   'user', @CurrentUser, 'table', 'xlsys_partdetail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:SashForm;1:TabFolder;2:ExpandBar',
   'user', @CurrentUser, 'table', 'xlsys_partdetail', 'column', 'type'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '自定义实现',
   'user', @CurrentUser, 'table', 'xlsys_partdetail', 'column', 'diyimplement'
go

/*==============================================================*/
/* Table: xlsys_position                                        */
/*==============================================================*/
create table xlsys_position (
   pstid                varchar(256)         not null,
   name                 varchar(64)          not null,
   constraint PK_XLSYS_POSITION primary key nonclustered (pstid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Position',
   'user', @CurrentUser, 'table', 'xlsys_position'
go

/*==============================================================*/
/* Table: xlsys_print                                           */
/*==============================================================*/
create table xlsys_print (
   printid              varchar(256)         not null,
   name                 varchar(256)         null,
   printtype            numeric(2,0)         null,
   template             varbinary(max)                null,
   constraint PK_XLSYS_PRINT primary key nonclustered (printid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '打印模板定义表',
   'user', @CurrentUser, 'table', 'xlsys_print'
go

/*==============================================================*/
/* Table: xlsys_queryparamsave                                  */
/*==============================================================*/
create table xlsys_queryparamsave (
   viewid               numeric(8,0)         not null,
   id                   varchar(256)         not null,
   name                 varchar(64)          not null,
   paramtype            numeric(2,0)         not null,
   paramsave            varbinary(max)                null,
   constraint PK_XLSYS_QUERYPARAMSAVE primary key nonclustered (viewid, id, name, paramtype)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '查询参数保存表',
   'user', @CurrentUser, 'table', 'xlsys_queryparamsave'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数类型
   0:界面查询参数
   1:分组参数',
   'user', @CurrentUser, 'table', 'xlsys_queryparamsave', 'column', 'paramtype'
go

/*==============================================================*/
/* Table: xlsys_ratify                                          */
/*==============================================================*/
create table xlsys_ratify (
   rtfid                varchar(256)         not null,
   name                 varchar(256)         null,
   fromuserid           varchar(256)         null,
   fromflowid           varchar(256)         null,
   fromcdtidx           numeric(8,0)         null,
   toflowid             varchar(256)         null,
   tocdtidx             numeric(8,0)         null,
   innercode            varchar(256)         null,
   version              numeric(8,0)         null,
   rtfret               numeric(2,0)         null,
   rtfdate              datetime             null,
   constraint PK_XLSYS_RATIFY primary key nonclustered (rtfid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The situation of ratifing business flow',
   'user', @CurrentUser, 'table', 'xlsys_ratify'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:已提交;1:已通过;2:已驳回',
   'user', @CurrentUser, 'table', 'xlsys_ratify', 'column', 'rtfret'
go

/*==============================================================*/
/* Table: xlsys_ratifydetail                                    */
/*==============================================================*/
create table xlsys_ratifydetail (
   rtfid                varchar(256)         not null,
   touserid             varchar(256)         not null,
   replaceuserid        varchar(256)         null,
   rtfret               numeric(2,0)         null,
   rtfdesc              varchar(4000)        null,
   rtfdate              datetime             null,
   groupnm              varchar(256)         null,
   constraint PK_XLSYS_RATIFYDETAIL primary key nonclustered (rtfid, touserid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The detail of ratify condition',
   'user', @CurrentUser, 'table', 'xlsys_ratifydetail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:已提交;1:已通过;2:已驳回',
   'user', @CurrentUser, 'table', 'xlsys_ratifydetail', 'column', 'rtfret'
go

/*==============================================================*/
/* Table: xlsys_right                                           */
/*==============================================================*/
create table xlsys_right (
   righttype            numeric(2,0)         not null,
   name                 varchar(64)          null,
   sessionkey           varchar(256)         not null,
   relationtable        varchar(256)         not null,
   relationcolumn       varchar(256)         not null,
   constraint PK_XLSYS_RIGHT primary key nonclustered (righttype)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统权限定义表',
   'user', @CurrentUser, 'table', 'xlsys_right'
go

/*==============================================================*/
/* Index: UN_RIGHT_RC                                           */
/*==============================================================*/
create unique index UN_RIGHT_RC on xlsys_right (
relationcolumn ASC
)
go

/*==============================================================*/
/* Index: UN_RIGHT_SK                                           */
/*==============================================================*/
create unique index UN_RIGHT_SK on xlsys_right (
sessionkey ASC
)
go

/*==============================================================*/
/* Table: xlsys_test                                            */
/*==============================================================*/
create table xlsys_test (
   idx                  numeric(8,0)         not null,
   name                 varchar(256)         null,
   value                numeric(18,6)        null,
   constraint PK_XLSYS_TEST primary key nonclustered (idx)
)
go

/*==============================================================*/
/* Table: xlsys_translator                                      */
/*==============================================================*/
create table xlsys_translator (
   tablename            varchar(32)          not null,
   defaultname          varchar(256)         not null,
   language             varchar(32)          not null,
   transname            varchar(256)         null,
   constraint PK_XLSYS_TRANSLATOR primary key nonclustered (tablename, defaultname, language)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Language support',
   'user', @CurrentUser, 'table', 'xlsys_translator'
go

/*==============================================================*/
/* Table: xlsys_transport                                       */
/*==============================================================*/
create table xlsys_transport (
   tsid                 varchar(256)         not null,
   name                 varchar(256)         null,
   constraint PK_XLSYS_TRANSPORT primary key nonclustered (tsid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统传输表, 用来做跨数据库的数据传输',
   'user', @CurrentUser, 'table', 'xlsys_transport'
go

/*==============================================================*/
/* Table: xlsys_transportdetail                                 */
/*==============================================================*/
create table xlsys_transportdetail (
   tsid                 varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   fromtable            varchar(64)          null,
   totable              varchar(64)          null,
   fromsql              varchar(4000)        null,
   javalistener         varchar(4000)        null,
   jslistener           varbinary(max)                null,
   batchcount           numeric(8,0)         null,
   cpsmcol              numeric(2,0)         null,
   active               numeric(2,0)         null,
   constraint PK_XLSYS_TRANSPORTDETAIL primary key nonclustered (tsid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据传输明细定义',
   'user', @CurrentUser, 'table', 'xlsys_transportdetail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '批量更新数量',
   'user', @CurrentUser, 'table', 'xlsys_transportdetail', 'column', 'batchcount'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否拷贝同名字段',
   'user', @CurrentUser, 'table', 'xlsys_transportdetail', 'column', 'cpsmcol'
go

/*==============================================================*/
/* Table: xlsys_transportdtcolmap                               */
/*==============================================================*/
create table xlsys_transportdtcolmap (
   tsid                 varchar(256)         not null,
   tsdtidx              numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   fromcolumn           varchar(64)          null,
   tocolumn             varchar(64)          null,
   constraint PK_XLSYS_TRANSPORTDTCOLMAP primary key nonclustered (tsid, tsdtidx, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据传输明细字段对照',
   'user', @CurrentUser, 'table', 'xlsys_transportdtcolmap'
go

/*==============================================================*/
/* Table: xlsys_transportkey                                    */
/*==============================================================*/
create table xlsys_transportkey (
   tskeyid              varchar(32)          not null,
   name                 varchar(256)         null,
   constraint PK_XLSYS_TRANSPORTKEY primary key nonclustered (tskeyid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '传输数据的关键码定义',
   'user', @CurrentUser, 'table', 'xlsys_transportkey'
go

/*==============================================================*/
/* Table: xlsys_transportkeysynonym                             */
/*==============================================================*/
create table xlsys_transportkeysynonym (
   tskeyid              varchar(32)          not null,
   tablename            varchar(64)          not null,
   columnname           varchar(64)          not null,
   constraint PK_XLSYS_TRANSPORTKEYSYNONYM primary key nonclustered (tskeyid, tablename, columnname)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '传输关键码同义词定义表，主要用来定义哪些表的哪些字段使用该关键码来进行映射',
   'user', @CurrentUser, 'table', 'xlsys_transportkeysynonym'
go

/*==============================================================*/
/* Table: xlsys_transportmap                                    */
/*==============================================================*/
create table xlsys_transportmap (
   tsmapid              varchar(256)         not null,
   tskeyid              varchar(32)          null,
   fromdsid             numeric(8,0)         null,
   todsid               numeric(8,0)         null,
   fromtable            varchar(64)          null,
   totable              varchar(64)          null,
   fromcolumn           varchar(64)          null,
   tocolumn             varchar(64)          null,
   fromvalue            varchar(4000)        null,
   tovalue              varchar(4000)        null,
   syndate              datetime             null,
   batchno              varchar(32)          null,
   remark               varchar(4000)        null,
   otheruse1            varchar(256)         null,
   otheruse2            varchar(256)         null,
   otheruse3            varchar(256)         null,
   constraint PK_XLSYS_TRANSPORTMAP primary key nonclustered (tsmapid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据传输映射表, 可看做数据传输日志, 以及数据在两个系统中的对照表',
   'user', @CurrentUser, 'table', 'xlsys_transportmap'
go

/*==============================================================*/
/* Table: xlsys_transportrun                                    */
/*==============================================================*/
create table xlsys_transportrun (
   tsrunid              varchar(256)         not null,
   tsid                 varchar(256)         null,
   fromdsid             numeric(8,0)         null,
   todsid               numeric(8,0)         null,
   totalthreadcount     numeric(8,0)         null,
   threadcount          numeric(8,0)         null,
   dataperthread        numeric(8,0)         null,
   constraint PK_XLSYS_TRANSPORTRUN primary key nonclustered (tsrunid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据传输运行表',
   'user', @CurrentUser, 'table', 'xlsys_transportrun'
go

/*==============================================================*/
/* Table: xlsys_user                                            */
/*==============================================================*/
create table xlsys_user (
   userid               varchar(256)         not null,
   name                 varchar(64)          not null,
   password             varchar(255)         null,
   constraint PK_XLSYS_USER primary key nonclustered (userid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'user table',
   'user', @CurrentUser, 'table', 'xlsys_user'
go

/*==============================================================*/
/* Table: xlsys_useremail                                       */
/*==============================================================*/
create table xlsys_useremail (
   userid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   email                varchar(64)          null,
   pop                  varchar(64)          null,
   smtp                 varchar(64)          null,
   emailuser            varchar(64)          null,
   emailpwd             varchar(64)          null,
   remark               varchar(4000)        null,
   header               varbinary(max)                null,
   footer               varbinary(max)                null,
   constraint PK_XLSYS_USEREMAIL primary key nonclustered (userid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户email配置',
   'user', @CurrentUser, 'table', 'xlsys_useremail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Email的抬头',
   'user', @CurrentUser, 'table', 'xlsys_useremail', 'column', 'header'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Email的签名',
   'user', @CurrentUser, 'table', 'xlsys_useremail', 'column', 'footer'
go

/*==============================================================*/
/* Table: xlsys_view                                            */
/*==============================================================*/
create table xlsys_view (
   viewid               numeric(8,0)         not null,
   name                 varchar(64)          null,
   viewtype             numeric(2,0)         null,
   param                varchar(4000)        null,
   relationtype         numeric(2,0)         null,
   mainviewid           numeric(8,0)         null,
   jslistener           varbinary(max)                null,
   javalistener         varchar(1000)        null,
   selectbody           varchar(1000)        null,
   frombody             varchar(1000)        null,
   wherebody            varchar(1000)        null,
   groupbybody          varchar(1000)        null,
   orderbybody          varchar(1000)        null,
   wholesql             varchar(1000)        null,
   treecolname          varchar(64)          null,
   constraint PK_XLSYS_VIEW primary key nonclustered (viewid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Define the composite
   select ''insert into XLSYS_VIEW (VIEWID,NAME,VIEWTYPE,PARAM,RELATIONTYPE,MAINVIEWID,JAVALISTENER,SELECTBODY,FROMBODY,WHEREBODY,GROUPBYBODY,ORDERBYBODY,WHOLESQL,TREECOLNAME) values (''||VIEWID||'',''''''||NAME||'''''',''||VIEWTYPE||'',''''''||PARAM||'''''',''||RELATIONTYPE||'',''||MAINVIEWID||'',''''''||JAVALISTENER||'''''',''''''||SELECTBODY||'''''',''''''||FROMBODY||'''''',''''''||WHEREBODY||'''''',''''''||GROUPBYBODY||'''''',''''''||ORDERBYBODY||'''''',''''''||WHOLESQL||'''''',''''''||TREECOLNAME||'''''');'' from XLSYS_VIEW',
   'user', @CurrentUser, 'table', 'xlsys_view'
go

/*==============================================================*/
/* Table: xlsys_viewdetail                                      */
/*==============================================================*/
create table xlsys_viewdetail (
   viewid               numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   colname              varchar(64)          null,
   name                 varchar(64)          null,
   colgroup             varchar(64)          null,
   colgroupname         varchar(64)          null,
   datatype             numeric(2,0)         null,
   type                 numeric(2,0)         null,
   defaultvalue         varchar(256)         null,
   supportvalue         varchar(4000)        null,
   aggregation          numeric(2,0)         null,
   relationcolname      varchar(64)          null,
   sqlexp               varchar(4000)        null,
   showninphoneoverview numeric(1,0)         null,
   showninphonedetail   numeric(1,0)         null,
   constraint PK_XLSYS_VIEWDETAIL primary key nonclustered (viewid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Detail of view',
   'user', @CurrentUser, 'table', 'xlsys_viewdetail'
go

/*==============================================================*/
/* Index: un_vd                                                 */
/*==============================================================*/
create unique index un_vd on xlsys_viewdetail (
viewid ASC,
colname ASC
)
go

/*==============================================================*/
/* Table: xlsys_viewdetailparam                                 */
/*==============================================================*/
create table xlsys_viewdetailparam (
   viewid               numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   attrname             varchar(1000)        not null,
   attrvalue            varchar(4000)        null,
   constraint PK_XLSYS_VIEWDETAILPARAM primary key nonclustered (viewid, idx, attrname)
)
go

/*==============================================================*/
/* Table: xlsys_viewqueryparam                                  */
/*==============================================================*/
create table xlsys_viewqueryparam (
   viewid               numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   colname              varchar(64)          null,
   name                 varchar(64)          null,
   datatype             numeric(2,0)         null,
   type                 numeric(2,0)         null,
   param                varchar(4000)        null,
   defaultvalue         varchar(256)         null,
   supportvalue         varchar(4000)        null,
   showninphone         numeric(1,0)         null,
   constraint PK_XLSYS_VIEWQUERYPARAM primary key nonclustered (viewid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Query parameter for view',
   'user', @CurrentUser, 'table', 'xlsys_viewqueryparam'
go

/*==============================================================*/
/* Table: xlsys_wdtcolumn                                       */
/*==============================================================*/
create table xlsys_wdtcolumn (
   wizardid             varchar(256)         not null,
   dtidx                numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   colname              varchar(64)          null,
   name                 varchar(64)          null,
   forceinput           numeric(2,0)         null,
   tooltip              varchar(256)         null,
   constraint PK_XLSYS_WDTCOLUMN primary key nonclustered (wizardid, dtidx, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '向导页对应字段',
   'user', @CurrentUser, 'table', 'xlsys_wdtcolumn'
go

/*==============================================================*/
/* Table: xlsys_wizard                                          */
/*==============================================================*/
create table xlsys_wizard (
   wizardid             varchar(256)         not null,
   name                 varchar(64)          null,
   javalistener         varchar(4000)        null,
   jslistener           varbinary(max)                null,
   constraint PK_XLSYS_WIZARD primary key nonclustered (wizardid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统向导主表',
   'user', @CurrentUser, 'table', 'xlsys_wizard'
go

/*==============================================================*/
/* Table: xlsys_wizarddetail                                    */
/*==============================================================*/
create table xlsys_wizarddetail (
   wizardid             varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   viewid               numeric(8,0)         null,
   title                varchar(256)         null,
   message              varchar(4000)        null,
   needsave             numeric(2,0)         null,
   nextidx              numeric(8,0)         null,
   constraint PK_XLSYS_WIZARDDETAIL primary key nonclustered (wizardid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统向导明细表',
   'user', @CurrentUser, 'table', 'xlsys_wizarddetail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '对应视图ID',
   'user', @CurrentUser, 'table', 'xlsys_wizarddetail', 'column', 'viewid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '下一步序号',
   'user', @CurrentUser, 'table', 'xlsys_wizarddetail', 'column', 'nextidx'
go

/*==============================================================*/
/* Table: xlv2_authorisedright                                  */
/*==============================================================*/
create table xlv2_authorisedright (
   arid                 varchar(256)         not null,
   ardtidx              numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   constraint PK_XLV2_AUTHORISEDRIGHT primary key nonclustered (arid, ardtidx, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '被授权权限',
   'user', @CurrentUser, 'table', 'xlv2_authorisedright'
go

/*==============================================================*/
/* Table: xlv2_authorize                                        */
/*==============================================================*/
create table xlv2_authorize (
   arid                 varchar(256)         not null,
   arno                 varchar(256)         null,
   flowid               varchar(256)         null,
   id                   varchar(256)         null,
   creater              varchar(256)         null,
   creationdate         datetime             null,
   modifydate           datetime             null,
   condition            varchar(256)         null,
   authorisedid         varchar(256)         null,
   begindate            datetime             null,
   enddate              datetime             null,
   remark               varchar(4000)        null,
   constraint PK_XLV2_AUTHORIZE primary key nonclustered (arid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权主表',
   'user', @CurrentUser, 'table', 'xlv2_authorize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权身份',
   'user', @CurrentUser, 'table', 'xlv2_authorize', 'column', 'authorisedid'
go

/*==============================================================*/
/* Table: xlv2_authorizedetail                                  */
/*==============================================================*/
create table xlv2_authorizedetail (
   arid                 varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   arflowid             varchar(256)         null,
   arcondition          varchar(256)         null,
   beauthorizedid       varchar(256)         null,
   constraint PK_XLV2_AUTHORIZEDETAIL primary key nonclustered (arid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权明细表',
   'user', @CurrentUser, 'table', 'xlv2_authorizedetail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权流程',
   'user', @CurrentUser, 'table', 'xlv2_authorizedetail', 'column', 'arflowid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权状态',
   'user', @CurrentUser, 'table', 'xlv2_authorizedetail', 'column', 'arcondition'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '被授权身份',
   'user', @CurrentUser, 'table', 'xlv2_authorizedetail', 'column', 'beauthorizedid'
go

/*==============================================================*/
/* Index: un_ad_fci2                                            */
/*==============================================================*/
create unique index un_ad_fci2 on xlv2_authorizedetail (
arflowid ASC,
arcondition ASC,
beauthorizedid ASC
)
go

/*==============================================================*/
/* Table: xlv2_codealloc                                        */
/*==============================================================*/
create table xlv2_codealloc (
   caid                 varchar(256)         not null,
   name                 varchar(256)         null,
   clientscript         varbinary(max)                null,
   clientinnermethod    varchar(1000)        null,
   serverscript         varbinary(max)                null,
   serverinnermethod    varchar(1000)        null,
   constraint PK_XLV2_CODEALLOC primary key nonclustered (caid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'define alloc code method',
   'user', @CurrentUser, 'table', 'xlv2_codealloc'
go

/*==============================================================*/
/* Table: xlv2_control                                          */
/*==============================================================*/
create table xlv2_control (
   controlid            numeric(8,0)         not null,
   name                 varchar(32)          not null,
   implclass            varchar(256)         not null,
   constraint PK_XLV2_CONTROL primary key nonclustered (controlid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼2.x系统的控件定义表',
   'user', @CurrentUser, 'table', 'xlv2_control'
go

/*==============================================================*/
/* Table: xlv2_flow                                             */
/*==============================================================*/
create table xlv2_flow (
   flowid               varchar(256)         not null,
   name                 varchar(64)          null,
   flowcode             varchar(256)         not null,
   maintable            varchar(64)          null,
   innercodecol         varchar(64)          null,
   outtercodecol        varchar(64)          null,
   innerlisteners       varchar(4000)        null,
   scriptlistener       varbinary(max)                null,
   versionupdate        numeric(2,0)         null,
   cancopy              numeric(2,0)         null,
   constraint PK_XLV2_FLOW primary key nonclustered (flowid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x流程定义主表',
   'user', @CurrentUser, 'table', 'xlv2_flow'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否允许版本更新',
   'user', @CurrentUser, 'table', 'xlv2_flow', 'column', 'versionupdate'
go

/*==============================================================*/
/* Table: xlv2_flowcodealloc                                    */
/*==============================================================*/
create table xlv2_flowcodealloc (
   fcaid                varchar(256)         not null,
   flowid               varchar(256)         null,
   tablename            varchar(64)          not null,
   colname              varchar(64)          not null,
   caid                 varchar(256)         null,
   constraint PK_XLV2_FLOWCODEALLOC primary key nonclustered (fcaid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'define code creation for flow',
   'user', @CurrentUser, 'table', 'xlv2_flowcodealloc'
go

/*==============================================================*/
/* Table: xlv2_flowcondition                                    */
/*==============================================================*/
create table xlv2_flowcondition (
   flowid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   condition            varchar(64)          not null,
   name                 varchar(256)         null,
   audittype            numeric(2,0)         null,
   voterate             numeric(18,6)        null,
   constraint PK_XLV2_FLOWCONDITION primary key nonclustered (flowid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The condition of flow',
   'user', @CurrentUser, 'table', 'xlv2_flowcondition'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '审批类型, 0:单审;1:会审;2:组单审;3:组会审;4:投票审
   [单审] : 任意一个人通过即可通过(提交时允许选择审批人)
   [会审] : 所有人通过才可通过(提交时不允许选择审批人)
   [组单审] : 任意一组人通过即可通过, 同一组里的人必须全部通过才算通过(提交时允许选择审批组，不允许选择审批人)
   [组会审] : 所有组的人都通过才可通过, 同一组里的人只有要任意一个人通过就算通过(提交时允许选择审批人，但是每个组都必须选择至少一个审批人)
   [投票审] : 按照一定比例票数通过后即可通过(提交时不允许选择)',
   'user', @CurrentUser, 'table', 'xlv2_flowcondition', 'column', 'audittype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投票率，当audittype为4:投票审时，此参数有效',
   'user', @CurrentUser, 'table', 'xlv2_flowcondition', 'column', 'voterate'
go

/*==============================================================*/
/* Table: xlv2_flowframe                                        */
/*==============================================================*/
create table xlv2_flowframe (
   flowid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   platform             numeric(2,0)         null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   listframeid          numeric(8,0)         null,
   mainviewidinlistframe numeric(8,0)         null,
   mainframeid          numeric(8,0)         null,
   mainviewidinmainframe numeric(8,0)         null,
   constraint PK_XLV2_FLOWFRAME primary key nonclustered (flowid, idx)
)
go

/*==============================================================*/
/* Table: xlv2_flowlogic                                        */
/*==============================================================*/
create table xlv2_flowlogic (
   flowid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   fromcondition        varchar(64)          null,
   tocondition          varchar(64)          null,
   passtype             numeric(2,0)         null,
   rejecttype           numeric(2,0)         null,
   canrejectto          varchar(64)          null,
   clientautotriggersubmit numeric(2,0)         null,
   constraint PK_XLV2_FLOWLOGIC primary key nonclustered (flowid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The logic of each flow',
   'user', @CurrentUser, 'table', 'xlv2_flowlogic'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:手动;1:自动提交;2:自动审批;3:自动审批并自动提交',
   'user', @CurrentUser, 'table', 'xlv2_flowlogic', 'column', 'passtype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '驳回类型,0:不允许驳回;1:可驳回到上一级;2:可驳回到任意上级;3:只允许驳回到自定义上级',
   'user', @CurrentUser, 'table', 'xlv2_flowlogic', 'column', 'rejecttype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否允许客户端自动触发提交按钮',
   'user', @CurrentUser, 'table', 'xlv2_flowlogic', 'column', 'clientautotriggersubmit'
go

/*==============================================================*/
/* Table: xlv2_flowright                                        */
/*==============================================================*/
create table xlv2_flowright (
   flowid               varchar(256)         not null,
   cdtidx               numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   belongrighttype      numeric(2,0)         null,
   righttype            numeric(2,0)         null,
   belongrightvalue     varchar(256)         null,
   rightvalue           varchar(256)         null,
   groupnm              varchar(256)         null,
   conditiongrp         varchar(32)          null,
   constraint PK_XLV2_FLOWRIGHT primary key nonclustered (flowid, cdtidx, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The right of each flow condition',
   'user', @CurrentUser, 'table', 'xlv2_flowright'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:identity;1:user;2:department;3:position',
   'user', @CurrentUser, 'table', 'xlv2_flowright', 'column', 'belongrighttype'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分组名称,当audittype选用 2:组单审和 3:组会审 时有效，用来标识当前权限为哪个组别所有，分组名称相同的视为同一组',
   'user', @CurrentUser, 'table', 'xlv2_flowright', 'column', 'groupnm'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '条件组, 组名一样的权限配置将使用and来连接, 否则使用or连接',
   'user', @CurrentUser, 'table', 'xlv2_flowright', 'column', 'conditiongrp'
go

/*==============================================================*/
/* Table: xlv2_flowsubtable                                     */
/*==============================================================*/
create table xlv2_flowsubtable (
   flowid               varchar(256)         not null,
   tablename            varchar(64)          not null,
   relationinnercol     varchar(64)          null,
   constraint PK_XLV2_FLOWSUBTABLE primary key nonclustered (flowid, tablename)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流程子表配置',
   'user', @CurrentUser, 'table', 'xlv2_flowsubtable'
go

/*==============================================================*/
/* Table: xlv2_flowviewlistener                                 */
/*==============================================================*/
create table xlv2_flowviewlistener (
   flowid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   viewid               numeric(8,0)         null,
   innerlisteners       varchar(4000)        null,
   scriptlistener       varbinary(max)                null,
   constraint PK_XLV2_FLOWVIEWLISTENER primary key nonclustered (flowid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Listener for view of flow',
   'user', @CurrentUser, 'table', 'xlv2_flowviewlistener'
go

/*==============================================================*/
/* Table: xlv2_frame                                            */
/*==============================================================*/
create table xlv2_frame (
   frameid              numeric(8,0)         not null,
   name                 varchar(256)         null,
   innerlisteners       varchar(1000)        null,
   scriptlistener       varbinary(max)                null,
   constraint PK_XLV2_FRAME primary key nonclustered (frameid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x框架界面配置主表',
   'user', @CurrentUser, 'table', 'xlv2_frame'
go

/*==============================================================*/
/* Table: xlv2_framedetail                                      */
/*==============================================================*/
create table xlv2_framedetail (
   frameid              numeric(8,0)         not null,
   fdtid                varchar(256)         not null,
   levelid              varchar(256)         not null,
   name                 varchar(256)         null,
   uimid                numeric(8,0)         not null,
   diyimpl              varchar(512)         null,
   registname           varchar(256)         null,
   viewid               numeric(8,0)         null,
   constraint PK_XLV2_FRAMEDETAIL primary key nonclustered (frameid, fdtid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼2.x系统框架界面配置明细表',
   'user', @CurrentUser, 'table', 'xlv2_framedetail'
go

/*==============================================================*/
/* Table: xlv2_framedetailparam                                 */
/*==============================================================*/
create table xlv2_framedetailparam (
   frameid              numeric(8,0)         not null,
   fdtid                varchar(256)         not null,
   attrname             varchar(512)         not null,
   attrvalue            varchar(512)         null,
   constraint PK_XLV2_FRAMEDETAILPARAM primary key nonclustered (frameid, fdtid, attrname)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼2.x系统框架明细参数',
   'user', @CurrentUser, 'table', 'xlv2_framedetailparam'
go

/*==============================================================*/
/* Table: xlv2_frameparam                                       */
/*==============================================================*/
create table xlv2_frameparam (
   frameid              numeric(8,0)         not null,
   attrname             varchar(512)         not null,
   attrvalue            varchar(512)         null,
   constraint PK_XLV2_FRAMEPARAM primary key nonclustered (frameid, attrname)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x框架界面参数',
   'user', @CurrentUser, 'table', 'xlv2_frameparam'
go

/*==============================================================*/
/* Table: xlv2_handler                                          */
/*==============================================================*/
create table xlv2_handler (
   handlerid            numeric(8,0)         not null,
   name                 varchar(32)          null,
   impl                 varchar(512)         null,
   constraint PK_XLV2_HANDLER primary key nonclustered (handlerid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x处理机',
   'user', @CurrentUser, 'table', 'xlv2_handler'
go

/*==============================================================*/
/* Table: xlv2_menu                                             */
/*==============================================================*/
create table xlv2_menu (
   menuid               varchar(256)         not null,
   levelid              varchar(256)         not null,
   handlerid            numeric(8,0)         null,
   name                 varchar(64)          null,
   type                 numeric(2,0)         null,
   icon                 varbinary(max)                null,
   shortcut             varchar(64)          null,
   constraint PK_XLV2_MENU primary key nonclustered (menuid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x菜单配置',
   'user', @CurrentUser, 'table', 'xlv2_menu'
go

/*==============================================================*/
/* Table: xlv2_menuhandlerparam                                 */
/*==============================================================*/
create table xlv2_menuhandlerparam (
   menuid               varchar(256)         not null,
   attrname             varchar(512)         not null,
   attrvalue            varchar(512)         null,
   constraint PK_XLV2_MENUHANDLERPARAM primary key nonclustered (menuid, attrname)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x菜单处理器参数',
   'user', @CurrentUser, 'table', 'xlv2_menuhandlerparam'
go

/*==============================================================*/
/* Table: xlv2_menuright                                        */
/*==============================================================*/
create table xlv2_menuright (
   menuid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   constraint PK_XLV2_MENURIGHT primary key nonclustered (menuid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x菜单权限',
   'user', @CurrentUser, 'table', 'xlv2_menuright'
go

/*==============================================================*/
/* Table: xlv2_ratify                                           */
/*==============================================================*/
create table xlv2_ratify (
   rtfid                varchar(256)         not null,
   userid               varchar(256)         null,
   name                 varchar(256)         null,
   fromuserid           varchar(256)         null,
   fromflowid           varchar(256)         null,
   fromcdtidx           numeric(8,0)         null,
   toflowid             varchar(256)         null,
   tocdtidx             numeric(8,0)         null,
   innercode            varchar(256)         null,
   version              numeric(8,0)         null,
   rtfret               numeric(2,0)         null,
   rtfdate              datetime             null,
   constraint PK_XLV2_RATIFY primary key nonclustered (rtfid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The situation of ratifing business flow',
   'user', @CurrentUser, 'table', 'xlv2_ratify'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:已提交;1:已通过;2:已驳回',
   'user', @CurrentUser, 'table', 'xlv2_ratify', 'column', 'rtfret'
go

/*==============================================================*/
/* Table: xlv2_ratifydetail                                     */
/*==============================================================*/
create table xlv2_ratifydetail (
   rtfid                varchar(256)         not null,
   touserid             varchar(256)         not null,
   replaceuserid        varchar(256)         null,
   rtfret               numeric(2,0)         null,
   rtfdesc              varchar(4000)        null,
   rtfdate              datetime             null,
   groupnm              varchar(256)         null,
   constraint PK_XLV2_RATIFYDETAIL primary key nonclustered (rtfid, touserid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'The detail of ratify condition',
   'user', @CurrentUser, 'table', 'xlv2_ratifydetail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:已提交;1:已通过;2:已驳回',
   'user', @CurrentUser, 'table', 'xlv2_ratifydetail', 'column', 'rtfret'
go

/*==============================================================*/
/* Table: xlv2_testbusi                                         */
/*==============================================================*/
create table xlv2_testbusi (
   busiid               varchar(256)         not null,
   busino               varchar(256)         null,
   name                 varchar(64)          null,
   flowid               varchar(256)         not null,
   id                   varchar(256)         null,
   creater              varchar(256)         null,
   creationdate         datetime             not null,
   modifydate           datetime             not null,
   condition            varchar(256)         not null,
   constraint PK_XLV2_TESTBUSI primary key nonclustered (busiid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x测试流程单据',
   'user', @CurrentUser, 'table', 'xlv2_testbusi'
go

/*==============================================================*/
/* Table: xlv2_testbusisub                                      */
/*==============================================================*/
create table xlv2_testbusisub (
   busiid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   name                 varchar(256)         null,
   constraint PK_XLV2_TESTBUSISUB primary key nonclustered (busiid, idx)
)
go

/*==============================================================*/
/* Table: xlv2_tool                                             */
/*==============================================================*/
create table xlv2_tool (
   toolid               varchar(256)         not null,
   levelid              varchar(256)         not null,
   handlerid            numeric(8,0)         null,
   name                 varchar(64)          null,
   type                 numeric(2,0)         null,
   icon                 varbinary(max)                null,
   shortcut             varchar(64)          null,
   constraint PK_XLV2_TOOL primary key nonclustered (toolid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x工具栏配置',
   'user', @CurrentUser, 'table', 'xlv2_tool'
go

/*==============================================================*/
/* Table: xlv2_toolhandlerparam                                 */
/*==============================================================*/
create table xlv2_toolhandlerparam (
   toolid               varchar(256)         not null,
   attrname             varchar(512)         not null,
   attrvalue            varchar(512)         null,
   constraint PK_XLV2_TOOLHANDLERPARAM primary key nonclustered (toolid, attrname)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x工具控制器参数',
   'user', @CurrentUser, 'table', 'xlv2_toolhandlerparam'
go

/*==============================================================*/
/* Table: xlv2_toolright                                        */
/*==============================================================*/
create table xlv2_toolright (
   toolid               varchar(256)         not null,
   idx                  numeric(8,0)         not null,
   righttype            numeric(2,0)         null,
   rightvalue           varchar(256)         null,
   constraint PK_XLV2_TOOLRIGHT primary key nonclustered (toolid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x工具栏权限',
   'user', @CurrentUser, 'table', 'xlv2_toolright'
go

/*==============================================================*/
/* Table: xlv2_uimodule                                         */
/*==============================================================*/
create table xlv2_uimodule (
   uimid                numeric(8,0)         not null,
   name                 varchar(256)         null,
   defaultimpl          varchar(512)         null,
   platform             numeric(2,0)         null,
   type                 numeric(2,0)         null,
   constraint PK_XLV2_UIMODULE primary key nonclustered (uimid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼2.x系统用户界面模块类型定义',
   'user', @CurrentUser, 'table', 'xlv2_uimodule'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '界面模块默认实现类',
   'user', @CurrentUser, 'table', 'xlv2_uimodule', 'column', 'defaultimpl'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'UI对应平台
   1:web;2:mobile',
   'user', @CurrentUser, 'table', 'xlv2_uimodule', 'column', 'platform'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '0:widget;1:view;2:dialog;3:cellrenderer',
   'user', @CurrentUser, 'table', 'xlv2_uimodule', 'column', 'type'
go

/*==============================================================*/
/* Table: xlv2_view                                             */
/*==============================================================*/
create table xlv2_view (
   viewid               numeric(8,0)         not null,
   name                 varchar(256)         null,
   uimid                numeric(8,0)         null,
   diyimpl              varchar(512)         null,
   relationtype         numeric(2,0)         null,
   parentviewid         numeric(8,0)         null,
   innerlisteners       varchar(1000)        null,
   scriptlistener       varbinary(max)                null,
   reflecttable         varchar(32)          null,
   selectbody           varchar(1000)        null,
   frombody             varchar(1000)        null,
   wherebody            varchar(1000)        null,
   groupbybody          varchar(1000)        null,
   orderbybody          varchar(1000)        null,
   wholesql             varchar(1000)        null,
   treecolname          varchar(32)          null,
   constraint PK_XLV2_VIEW primary key nonclustered (viewid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x视图定义',
   'user', @CurrentUser, 'table', 'xlv2_view'
go

/*==============================================================*/
/* Table: xlv2_viewcolumn                                       */
/*==============================================================*/
create table xlv2_viewcolumn (
   viewid               numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   name                 varchar(32)          null,
   colname              varchar(32)          null,
   sqlexp               varchar(1000)        null,
   datatype             numeric(2,0)         null,
   uimid                numeric(8,0)         null,
   diyimpl              varchar(512)         null,
   defaultvalue         varchar(256)         null,
   supportvalue         varchar(1000)        null,
   relationcolname      varchar(32)          null,
   constraint PK_XLV2_VIEWCOLUMN primary key nonclustered (viewid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x视图列',
   'user', @CurrentUser, 'table', 'xlv2_viewcolumn'
go

/*==============================================================*/
/* Table: xlv2_viewcolumnparam                                  */
/*==============================================================*/
create table xlv2_viewcolumnparam (
   viewid               numeric(8,0)         not null,
   type                 numeric(2,0)         not null,
   idx                  numeric(8,0)         not null,
   attrname             varchar(512)         not null,
   attrvalue            varchar(512)         null,
   constraint PK_XLV2_VIEWCOLUMNPARAM primary key nonclustered (viewid, type, idx, attrname)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x视图列参数',
   'user', @CurrentUser, 'table', 'xlv2_viewcolumnparam'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '视图列的属性所属类型.
   1:container;2:control',
   'user', @CurrentUser, 'table', 'xlv2_viewcolumnparam', 'column', 'type'
go

/*==============================================================*/
/* Table: xlv2_viewparam                                        */
/*==============================================================*/
create table xlv2_viewparam (
   viewid               numeric(8,0)         not null,
   type                 numeric(2,0)         not null,
   attrname             varchar(512)         not null,
   attrvalue            varchar(512)         null,
   constraint PK_XLV2_VIEWPARAM primary key nonclustered (viewid, type, attrname)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x视图参数',
   'user', @CurrentUser, 'table', 'xlv2_viewparam'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '视图参数类型. 1是作为视图本身的属性类型, 2是查询参数界面Container的属性类型
   1:self;2:queryparam',
   'user', @CurrentUser, 'table', 'xlv2_viewparam', 'column', 'type'
go

/*==============================================================*/
/* Table: xlv2_viewqueryparam                                   */
/*==============================================================*/
create table xlv2_viewqueryparam (
   viewid               numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   name                 varchar(32)          null,
   colname              varchar(32)          null,
   datatype             numeric(2,0)         null,
   uimid                numeric(8,0)         null,
   diyimpl              varchar(512)         null,
   defaultvalue         varchar(256)         null,
   supportvalue         varchar(1000)        null,
   constraint PK_XLV2_VIEWQUERYPARAM primary key nonclustered (viewid, idx)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x视图查询参数',
   'user', @CurrentUser, 'table', 'xlv2_viewqueryparam'
go

/*==============================================================*/
/* Table: xlv2_viewqueryparamparam                              */
/*==============================================================*/
create table xlv2_viewqueryparamparam (
   viewid               numeric(8,0)         not null,
   idx                  numeric(8,0)         not null,
   type                 numeric(2,0)         not null,
   attrname             varchar(512)         not null,
   attrvalue            varchar(512)         null,
   constraint PK_XLV2_VIEWQUERYPARAMPARAM primary key nonclustered (viewid, idx, type, attrname)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '雪狼系统2.x视图查询参数的参数',
   'user', @CurrentUser, 'table', 'xlv2_viewqueryparamparam'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '查询组件的参数类型
   1:container;2:control',
   'user', @CurrentUser, 'table', 'xlv2_viewqueryparamparam', 'column', 'type'
go

alter table xlem_buyer
   add constraint FK_BR_REFERENCE_U foreign key (userid)
      references xlem_user (userid)
go

alter table xlem_buyer
   add constraint FK_BR_REFERENCE_UL foreign key (levelid)
      references xlem_userlevel (levelid)
go

alter table xlem_item
   add constraint FK_I_REFERENCE_SL foreign key (sellerid)
      references xlem_seller (sellerid)
go

alter table xlem_itemsku
   add constraint FK_ISKU_REFERENCE_I foreign key (itemid)
      references xlem_item (itemid)
go

alter table xlem_itemsku
   add constraint FK_ISKU_REFERENCE_SKU foreign key (sku)
      references xlem_sku (sku)
go

alter table xlem_seller
   add constraint FK_SL_REFERENCE_U foreign key (userid)
      references xlem_user (userid)
go

alter table xlem_seller
   add constraint FK_SL_REFERENCE_UL foreign key (levelid)
      references xlem_userlevel (levelid)
go

alter table xlem_sku
   add constraint FK_SKU_REFERENCE_AUNIT foreign key (aunitid)
      references xlem_atomicunit (aunitid)
go

alter table xlem_sku
   add constraint FK_SKU_REFERENCE_SPU foreign key (spu)
      references xlem_spu (spu)
go

alter table xlem_spu
   add constraint FK_SPU_REFERENCE_SPUC foreign key (categoryid)
      references xlem_spucategory (categoryid)
go

alter table xlem_stock
   add constraint FK_STK_REFERENCE_SKU foreign key (sku)
      references xlem_sku (sku)
go

alter table xlem_stockhistory
   add constraint FK_STKH_REFERENCE_SKU foreign key (sku)
      references xlem_sku (sku)
go

alter table xlem_unit
   add constraint FK_UNIT_REFERENCE_AUNIT foreign key (aunitid)
      references xlem_atomicunit (aunitid)
go

alter table xlem_userlevel
   add constraint FK_UL_REFERENCE_UL foreign key (nextlevel)
      references xlem_userlevel (levelid)
go

alter table xlfin_accountcondition
   add constraint FK_AC_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid)
go

alter table xlfin_balance
   add constraint FK_B_REFERENCE_A foreign key (accid)
      references xlfin_account (accid)
go

alter table xlfin_balance
   add constraint FK_B_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid)
go

alter table xlfin_balance
   add constraint FK_B_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid)
go

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_A foreign key (accid)
      references xlfin_account (accid)
go

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid)
go

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid)
go

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_U foreign key (userid)
      references xlsys_user (userid)
go

alter table xlfin_bankstmt
   add constraint FK_BS_REFERENCE_VD foreign key (vid, vidx)
      references xlfin_voucherdetail (voucherid, idx)
go

alter table xlfin_bankstmtbalance
   add constraint FK_BSB_REFERENCE_A foreign key (accid)
      references xlfin_account (accid)
go

alter table xlfin_bankstmtbalance
   add constraint FK_BSB_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid)
go

alter table xlfin_bankstmtbalance
   add constraint FK_BSB_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid)
go

alter table xlfin_bstldt
   add constraint FK_BSTLDT_REFERENCE_BSTL foreign key (bstlid)
      references xlfin_bankstmttemplet (bstlid)
go

alter table xlfin_exchangerate
   add constraint FK_ER_REFERENCE_C foreign key (crcid)
      references xlfin_currency (crcid)
go

alter table xlfin_karaccdt
   add constraint FK_KARADT_REFERENCE_KAR foreign key (karid)
      references xlfin_kdeptaccrealtion (karid)
go

alter table xlfin_kardt
   add constraint FK_KARDT_REFERENCE_KAR foreign key (karid)
      references xlfin_kdeptaccrealtion (karid)
go

alter table xlfin_kardt
   add constraint FK_KARDT_REFERENCE_VDTECA foreign key (vdtecaid)
      references xlfin_vdtextracolattr (vdtecaid)
go

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_C1 foreign key (standardcrcid)
      references xlfin_currency (crcid)
go

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_C2 foreign key (reportcrcid)
      references xlfin_currency (crcid)
go

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_KDAR1 foreign key (usedkarid)
      references xlfin_kdeptaccrealtion (karid)
go

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_KDAR2 foreign key (nocarryoverkarid)
      references xlfin_kdeptaccrealtion (karid)
go

alter table xlfin_keepdept
   add constraint FK_KD_REFERENCE_KDAR3 foreign key (cockarid)
      references xlfin_kdeptaccrealtion (karid)
go

alter table xlfin_reportdata
   add constraint FK_RD_REFERENCE_RDEPT foreign key (rdeptid)
      references xlfin_reportdept (rdeptid)
go

alter table xlfin_reportdata
   add constraint FK_RD_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid)
go

alter table xlfin_reportdatadetail
   add constraint FK_RDD_REFERENCE_RD foreign key (rdid)
      references xlfin_reportdata (rdid)
go

alter table xlfin_reportformcol
   add constraint FK_RFC_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid)
go

alter table xlfin_reportformformula
   add constraint FK_RFF_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid)
go

alter table xlfin_reportformrow
   add constraint FK_RFR_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid)
go

alter table xlfin_vdtextra
   add constraint FK_VDTE_REFERENCE_VDT foreign key (voucherid, idx)
      references xlfin_voucherdetail (voucherid, idx)
go

alter table xlfin_vdtextra
   add constraint FK_VDTE_REFERENCE_VEC foreign key (extracol)
      references xlfin_vdtextracol (extracol)
go

alter table xlfin_vdtextracolattr
   add constraint FK_VDTECA_REFERENCE_VDTEC foreign key (extracol)
      references xlfin_vdtextracol (extracol)
go

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_ID foreign key (id)
      references xlsys_identity (id)
go

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid)
go

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_U1 foreign key (creater)
      references xlsys_user (userid)
go

alter table xlfin_voucher
   add constraint FK_V_REFERENCE_U2 foreign key (accounter)
      references xlsys_user (userid)
go

alter table xlfin_voucherdetail
   add constraint FK_VD_REFERENCE_A foreign key (accid)
      references xlfin_account (accid)
go

alter table xlfin_voucherdetail
   add constraint FK_VD_REFERENCE_BS foreign key (bsid)
      references xlfin_bankstmt (bsid)
go

alter table xlfin_voucherdetail
   add constraint FK_VD_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid)
go

alter table xlfin_voucherdetail
   add constraint FK_VDT_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid)
go

alter table xlfin_voucherdetail
   add constraint FK_VDT_REFERENCE_U foreign key (userid)
      references xlsys_user (userid)
go

alter table xlfin_voucherdetail
   add constraint FK_VOD_REFERENCE_VO foreign key (voucherid)
      references xlfin_voucher (voucherid)
go

alter table xlfin_vtempletdt
   add constraint FK_VTLDT_REFERENCE_VTL foreign key (vtlid)
      references xlfin_vouchertemplet (vtlid)
go

alter table xlfin_vtldtcol
   add constraint FK_VTLDTC_REFERENCE_VTLDT foreign key (vtldtidx, vtlid)
      references xlfin_vtempletdt (idx, vtlid)
go

alter table xlsys_authorisedright
   add constraint FK_AR_REFERENCE_AD foreign key (arid, ardtidx)
      references xlsys_authorizedetail (arid, idx)
go

alter table xlsys_authorisedright
   add constraint FK_AR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlsys_authorize
   add constraint FK_A_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlsys_authorize
   add constraint FK_A_REFERENCE_I1 foreign key (id)
      references xlsys_identity (id)
go

alter table xlsys_authorize
   add constraint FK_A_REFERENCE_I2 foreign key (authorisedid)
      references xlsys_identity (id)
go

alter table xlsys_authorize
   add constraint FK_A_REFERENCE_U foreign key (creater)
      references xlsys_user (userid)
go

alter table xlsys_authorizedetail
   add constraint FK_AD_REFERENCE_A foreign key (arid)
      references xlsys_authorize (arid)
go

alter table xlsys_basebusi
   add constraint FK_BB_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlsys_basebusi
   add constraint FK_BB_REFERENCE_I foreign key (id)
      references xlsys_identity (id)
go

alter table xlsys_basebusi
   add constraint FK_BB_REFERENCE_U foreign key (creater)
      references xlsys_user (userid)
go

alter table xlsys_dictdetail
   add constraint FK_DD_REFERENCE_D foreign key (dictid)
      references xlsys_dict (dictid)
go

alter table xlsys_envdetail
   add constraint FK_ED_REFERENCE_D foreign key (dbid)
      references xlsys_db (dbid)
go

alter table xlsys_envdetail
   add constraint FK_ED_REFERENCE_E foreign key (envid)
      references xlsys_env (envid)
go

alter table xlsys_exttableinfodetail
   add constraint FK_ETI_REFERENCE_ETID foreign key (tableid)
      references xlsys_exttableinfo (tableid)
go

alter table xlsys_flow
   add constraint FK_F_REFERENCE_V1 foreign key (mvidoflpart)
      references xlsys_view (viewid)
go

alter table xlsys_flow
   add constraint FK_F_REFERENCE_V2 foreign key (mvidofmpart)
      references xlsys_view (viewid)
go

alter table xlsys_flow
   add constraint FK_FL_REFERENCE_P1 foreign key (mainpartid)
      references xlsys_part (partid)
go

alter table xlsys_flow
   add constraint FK_FL_REFERENCE_P2 foreign key (listpartid)
      references xlsys_part (partid)
go

alter table xlsys_flowcodealloc
   add constraint FK_FCA_REFERENCE_CA foreign key (caid)
      references xlsys_codealloc (caid)
go

alter table xlsys_flowcodealloc
   add constraint FK_FCA_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlsys_flowcondition
   add constraint FK_FC_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlsys_flowjava
   add constraint FK_FJAVA_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlsys_flowjava
   add constraint FK_FJAVA_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid)
go

alter table xlsys_flowjs
   add constraint FK_FJS_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlsys_flowjs
   add constraint FK_FJS_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid)
go

alter table xlsys_flowlogic
   add constraint FK_FL_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_P1 foreign key (listpartid)
      references xlsys_part (partid)
go

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_P2 foreign key (mainpartid)
      references xlsys_part (partid)
go

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_V1 foreign key (mvidoflpart)
      references xlsys_view (viewid)
go

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_V2 foreign key (mvidofmpart)
      references xlsys_view (viewid)
go

alter table xlsys_flowright
   add constraint FK_FR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlsys_flowright
   add constraint FK_FR_REFERENCE_FC foreign key (flowid, cdtidx)
      references xlsys_flowcondition (flowid, idx)
go

alter table xlsys_flowsubtable
   add constraint FK_FST_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid)
go

alter table xlsys_identity
   add constraint FK_I_REFERENCE_M foreign key (welcomemenuid)
      references xlsys_menu (menuid)
go

alter table xlsys_identity
   add constraint FKV2_I_REFERENCE_F foreign key (frameid)
      references xlv2_frame (frameid)
go

alter table xlsys_idrelation
   add constraint FK_IR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlsys_idrelation
   add constraint FK_IR_REFERENCE_I foreign key (id)
      references xlsys_identity (id)
go

alter table xlsys_menuright
   add constraint FK_MR_REFERENCE_M foreign key (menuid)
      references xlsys_menu (menuid)
go

alter table xlsys_oacategoryright
   add constraint FK_OACR_REFERENCE_OAC foreign key (oacid)
      references xlsys_oacategory (oacid)
go

alter table xlsys_oacmbelong
   add constraint FK_OACMB_REFERENCE_OAM foreign key (oamid)
      references xlsys_oamodule (oamid)
go

alter table xlsys_oacmbelong
   add constraint FK_OACMBL_REFERENCE_OAC foreign key (oacid)
      references xlsys_oacategory (oacid)
go

alter table xlsys_oacmrelation
   add constraint FK_OACMR_REFERENCE_ID foreign key (id)
      references xlsys_identity (id)
go

alter table xlsys_oacmrelation
   add constraint FK_OACMR_REFERENCE_OACMBL foreign key (oacid, oamid)
      references xlsys_oacmbelong (oacid, oamid)
go

alter table xlsys_oamoduleextra
   add constraint FK_OAME_REFERENCE_OAM foreign key (oamid)
      references xlsys_oamodule (oamid)
go

alter table xlsys_oamoduleextra
   add constraint FK_OAME_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid)
go

alter table xlsys_oamoduleright
   add constraint FK_OAMR_REFERENCE_OAM foreign key (oamid)
      references xlsys_oamodule (oamid)
go

alter table xlsys_partdetail
   add constraint FK_PD_REFERENCE_P foreign key (partid)
      references xlsys_part (partid)
go

alter table xlsys_partdetail
   add constraint FK_PD_REFERENCE_V1 foreign key (viewid)
      references xlsys_view (viewid)
go

alter table xlsys_partdetail
   add constraint FK_PD_REFERENCE_V2 foreign key (mainviewid)
      references xlsys_view (viewid)
go

alter table xlsys_queryparamsave
   add constraint FK_QPS_REFERENCE_ID foreign key (id)
      references xlsys_identity (id)
go

alter table xlsys_queryparamsave
   add constraint FK_QPS_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid)
go

alter table xlsys_ratify
   add constraint FK_R_REFERENCE_FC_F foreign key (fromflowid, fromcdtidx)
      references xlsys_flowcondition (flowid, idx)
go

alter table xlsys_ratify
   add constraint FK_R_REFERENCE_FC_T foreign key (toflowid, tocdtidx)
      references xlsys_flowcondition (flowid, idx)
go

alter table xlsys_ratify
   add constraint FK_R_REFERENCE_U foreign key (fromuserid)
      references xlsys_user (userid)
go

alter table xlsys_ratifydetail
   add constraint FK_RD_REFERENCE_R foreign key (rtfid)
      references xlsys_ratify (rtfid)
go

alter table xlsys_ratifydetail
   add constraint FK_RD_REFERENCE_U1 foreign key (touserid)
      references xlsys_user (userid)
go

alter table xlsys_ratifydetail
   add constraint FK_RD_REFERENCE_U2 foreign key (replaceuserid)
      references xlsys_user (userid)
go

alter table xlsys_transportdetail
   add constraint FK_TSDT_REFERENCE_TS foreign key (tsid)
      references xlsys_transport (tsid)
go

alter table xlsys_transportdtcolmap
   add constraint FK_TSDTCM_REFERENCE_TSDT foreign key (tsid, tsdtidx)
      references xlsys_transportdetail (tsid, idx)
go

alter table xlsys_transportkeysynonym
   add constraint FK_TSKS_REFERENCE_TSK foreign key (tskeyid)
      references xlsys_transportkey (tskeyid)
go

alter table xlsys_transportmap
   add constraint FK_TSK_REFERENCE_TSM foreign key (tskeyid)
      references xlsys_transportkey (tskeyid)
go

alter table xlsys_transportrun
   add constraint FK_TSR_REFERENCE_TS foreign key (tsid)
      references xlsys_transport (tsid)
go

alter table xlsys_useremail
   add constraint FK_UE_REFERENCE_U foreign key (userid)
      references xlsys_user (userid)
go

alter table xlsys_viewdetail
   add constraint FK_VD_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid)
go

alter table xlsys_viewdetailparam
   add constraint FK_VDP_REFERENCE_VD foreign key (viewid, idx)
      references xlsys_viewdetail (viewid, idx)
go

alter table xlsys_viewqueryparam
   add constraint FK_VQP_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid)
go

alter table xlsys_wdtcolumn
   add constraint FK_WDC_REFERENCE_WD foreign key (wizardid, dtidx)
      references xlsys_wizarddetail (wizardid, idx)
go

alter table xlsys_wizarddetail
   add constraint FK_WD_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid)
go

alter table xlsys_wizarddetail
   add constraint FK_WD_REFERENCE_W1 foreign key (wizardid)
      references xlsys_wizard (wizardid)
go

alter table xlv2_authorisedright
   add constraint FKV2_AR_REFERENCE_AD foreign key (arid, ardtidx)
      references xlv2_authorizedetail (arid, idx)
go

alter table xlv2_authorisedright
   add constraint FKV2_AR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlv2_authorize
   add constraint FKV2_A_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid)
go

alter table xlv2_authorize
   add constraint FKV2_A_REFERENCE_I1 foreign key (id)
      references xlsys_identity (id)
go

alter table xlv2_authorize
   add constraint FKV2_A_REFERENCE_I2 foreign key (authorisedid)
      references xlsys_identity (id)
go

alter table xlv2_authorize
   add constraint FKV2_A_REFERENCE_U foreign key (creater)
      references xlsys_user (userid)
go

alter table xlv2_authorizedetail
   add constraint FKV2_AD_REFERENCE_A foreign key (arid)
      references xlv2_authorize (arid)
go

alter table xlv2_authorizedetail
   add constraint FKV2_AD_REFERENCE_F foreign key (arflowid)
      references xlv2_flow (flowid)
go

alter table xlv2_authorizedetail
   add constraint FKV2_AD_REFERENCE_I foreign key (beauthorizedid)
      references xlsys_identity (id)
go

alter table xlv2_flowcodealloc
   add constraint FKV2_FCA_REFERENCE_CA foreign key (caid)
      references xlv2_codealloc (caid)
go

alter table xlv2_flowcodealloc
   add constraint FKV2_FCA_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid)
go

alter table xlv2_flowcondition
   add constraint FKV2_FC_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid)
go

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_F1 foreign key (listframeid)
      references xlv2_frame (frameid)
go

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_F2 foreign key (mainframeid)
      references xlv2_frame (frameid)
go

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_FLOW foreign key (flowid)
      references xlv2_flow (flowid)
go

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_V1 foreign key (mainviewidinlistframe)
      references xlv2_view (viewid)
go

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_V2 foreign key (mainviewidinmainframe)
      references xlv2_view (viewid)
go

alter table xlv2_flowlogic
   add constraint FKV2_FL_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid)
go

alter table xlv2_flowright
   add constraint FKV2_FR_REFERENCE_FC foreign key (flowid, cdtidx)
      references xlv2_flowcondition (flowid, idx)
go

alter table xlv2_flowright
   add constraint FKV2_FR_REFERENCE_R1 foreign key (belongrighttype)
      references xlsys_right (righttype)
go

alter table xlv2_flowright
   add constraint FKV2_FR_REFERENCE_R2 foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlv2_flowsubtable
   add constraint FKV2_FST_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid)
go

alter table xlv2_flowviewlistener
   add constraint FKV2_FVL_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid)
go

alter table xlv2_flowviewlistener
   add constraint FKV2_FVL_REFERENCE_V foreign key (viewid)
      references xlv2_view (viewid)
go

alter table xlv2_framedetail
   add constraint FKV2_FD_REFERENCE_F foreign key (frameid)
      references xlv2_frame (frameid)
go

alter table xlv2_framedetail
   add constraint FKV2_FD_REFERENCE_UIM foreign key (uimid)
      references xlv2_uimodule (uimid)
go

alter table xlv2_framedetail
   add constraint FKV2_FD_REFERENCE_V foreign key (viewid)
      references xlv2_view (viewid)
go

alter table xlv2_framedetailparam
   add constraint FKV2_FDP_REFERENCE_FD foreign key (frameid, fdtid)
      references xlv2_framedetail (frameid, fdtid)
go

alter table xlv2_frameparam
   add constraint FKV2_FP_REFERENCE_F foreign key (frameid)
      references xlv2_frame (frameid)
go

alter table xlv2_menu
   add constraint FKV2_M_REFERENCE_H foreign key (handlerid)
      references xlv2_handler (handlerid)
go

alter table xlv2_menuhandlerparam
   add constraint FKV2_MHP_REFERENCE_M foreign key (menuid)
      references xlv2_menu (menuid)
go

alter table xlv2_menuright
   add constraint FKV2_MR_REFERENCE_M foreign key (menuid)
      references xlv2_menu (menuid)
go

alter table xlv2_menuright
   add constraint FKV2_MR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlv2_ratify
   add constraint FKV2_R_REFERENCE_FC1 foreign key (fromflowid, fromcdtidx)
      references xlv2_flowcondition (flowid, idx)
go

alter table xlv2_ratify
   add constraint FKV2_R_REFERENCE_FC2 foreign key (toflowid, tocdtidx)
      references xlv2_flowcondition (flowid, idx)
go

alter table xlv2_ratify
   add constraint FKV2_R_REFERENCE_U1 foreign key (userid)
      references xlsys_user (userid)
go

alter table xlv2_ratify
   add constraint FKV2_R_REFERENCE_U2 foreign key (fromuserid)
      references xlsys_user (userid)
go

alter table xlv2_ratifydetail
   add constraint FKV2_RD_REFERENCE_R foreign key (rtfid)
      references xlv2_ratify (rtfid)
go

alter table xlv2_ratifydetail
   add constraint FKV2_RD_REFERENCE_U foreign key (replaceuserid)
      references xlsys_user (userid)
go

alter table xlv2_testbusi
   add constraint FKV2_TB_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid)
go

alter table xlv2_testbusisub
   add constraint FKV2_TBS_REFERENCE_TB foreign key (busiid)
      references xlv2_testbusi (busiid)
go

alter table xlv2_tool
   add constraint FKV2_T_REFERENCE_H foreign key (handlerid)
      references xlv2_handler (handlerid)
go

alter table xlv2_toolhandlerparam
   add constraint FKV2_THP_REFERENCE_T foreign key (toolid)
      references xlv2_tool (toolid)
go

alter table xlv2_toolright
   add constraint FKV2_TR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlv2_toolright
   add constraint FKV2_TR_REFERENCE_T foreign key (toolid)
      references xlv2_tool (toolid)
go

alter table xlv2_view
   add constraint FKV2_V_REFERENCE_UIM foreign key (uimid)
      references xlv2_uimodule (uimid)
go

alter table xlv2_viewcolumn
   add constraint FK_XLV2_VIE_REFERENCE_XLV2_VIE foreign key (viewid)
      references xlv2_view (viewid)
go

alter table xlv2_viewcolumn
   add constraint FKV2_VC_REFERENCE_UIM foreign key (uimid)
      references xlv2_uimodule (uimid)
go

alter table xlv2_viewcolumnparam
   add constraint FKV2_VCP_REFERENCE_VC foreign key (viewid, idx)
      references xlv2_viewcolumn (viewid, idx)
go

alter table xlv2_viewparam
   add constraint FKV2_VP_REFERENCE_V foreign key (viewid)
      references xlv2_view (viewid)
go

alter table xlv2_viewqueryparam
   add constraint FKV2_VQP_REFERENCE_UIM foreign key (uimid)
      references xlv2_uimodule (uimid)
go

alter table xlv2_viewqueryparam
   add constraint FKV2_VQP_REFERENCE_V foreign key (viewid)
      references xlv2_view (viewid)
go

alter table xlv2_viewqueryparamparam
   add constraint FKV2_VQPP_REFERENCE_VQP foreign key (viewid, idx)
      references xlv2_viewqueryparam (viewid, idx)
go

