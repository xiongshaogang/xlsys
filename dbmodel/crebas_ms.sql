/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2015/7/8 18:01:55                            */
/*==============================================================*/


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
   where r.fkeyid = object_id('xlfin_reportdatadetail') and o.name = 'FK_XLFIN_RE_REFERENCE_XLFIN_RE')
alter table xlfin_reportdatadetail
   drop constraint FK_XLFIN_RE_REFERENCE_XLFIN_RE
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
   where r.fkeyid = object_id('xlsys_partdetail') and o.name = 'FK_PD_REFERENCE_P')
alter table xlsys_partdetail
   drop constraint FK_PD_REFERENCE_P
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_partdetail') and o.name = 'FK_PD_REFERENCE_V')
alter table xlsys_partdetail
   drop constraint FK_PD_REFERENCE_V
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
   where r.fkeyid = object_id('xlsys_viewdetail') and o.name = 'FK_VD_REFERENCE_V')
alter table xlsys_viewdetail
   drop constraint FK_VD_REFERENCE_V
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('xlsys_viewqueryparam') and o.name = 'FK_VQP_REFERENCE_V')
alter table xlsys_viewqueryparam
   drop constraint FK_VQP_REFERENCE_V
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
           where  id = object_id('xlsys_flowright')
            and   type = 'U')
   drop table xlsys_flowright
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
           where  id = object_id('xlsys_translator')
            and   type = 'U')
   drop table xlsys_translator
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_user')
            and   type = 'U')
   drop table xlsys_user
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_view')
            and   type = 'U')
   drop table xlsys_view
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_viewdetail')
            and   type = 'U')
   drop table xlsys_viewdetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('xlsys_viewqueryparam')
            and   type = 'U')
   drop table xlsys_viewqueryparam
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

/*==============================================================*/
/* Table: xlsys_identity                                        */
/*==============================================================*/
create table xlsys_identity (
   id                   varchar(256)         not null,
   name                 varchar(64)          not null,
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
   javalistener         varchar(4000)        null,
   selectbody           varchar(4000)        null,
   frombody             varchar(4000)        null,
   wherebody            varchar(4000)        null,
   groupbybody          varchar(4000)        null,
   orderbybody          varchar(4000)        null,
   wholesql             varchar(4000)        null,
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
   datatype             numeric(2,0)         null,
   type                 numeric(2,0)         null,
   param                varchar(4000)        null,
   defaultvalue         varchar(256)         null,
   supportvalue         varchar(4000)        null,
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
   add constraint FK_XLFIN_RE_REFERENCE_XLFIN_RE foreign key (rdid)
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

alter table xlsys_flowright
   add constraint FK_FR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype)
go

alter table xlsys_flowright
   add constraint FK_FR_REFERENCE_FC foreign key (flowid, cdtidx)
      references xlsys_flowcondition (flowid, idx)
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

alter table xlsys_partdetail
   add constraint FK_PD_REFERENCE_P foreign key (partid)
      references xlsys_part (partid)
go

alter table xlsys_partdetail
   add constraint FK_PD_REFERENCE_V foreign key (viewid)
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

alter table xlsys_viewdetail
   add constraint FK_VD_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid)
go

alter table xlsys_viewqueryparam
   add constraint FK_VQP_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid)
go

