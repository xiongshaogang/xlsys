/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2015/7/24 16:27:14                           */
/*==============================================================*/


alter table "xlfin_accountcondition"
   drop constraint FK_AC_REFERENCE_KD;

alter table "xlfin_balance"
   drop constraint FK_B_REFERENCE_A;

alter table "xlfin_balance"
   drop constraint FK_B_REFERENCE_C;

alter table "xlfin_balance"
   drop constraint FK_B_REFERENCE_KD;

alter table "xlfin_bankstmt"
   drop constraint FK_BS_REFERENCE_A;

alter table "xlfin_bankstmt"
   drop constraint FK_BS_REFERENCE_C;

alter table "xlfin_bankstmt"
   drop constraint FK_BS_REFERENCE_KD;

alter table "xlfin_bankstmt"
   drop constraint FK_BS_REFERENCE_U;

alter table "xlfin_bankstmt"
   drop constraint FK_BS_REFERENCE_VD;

alter table "xlfin_bankstmtbalance"
   drop constraint FK_BSB_REFERENCE_A;

alter table "xlfin_bankstmtbalance"
   drop constraint FK_BSB_REFERENCE_C;

alter table "xlfin_bankstmtbalance"
   drop constraint FK_BSB_REFERENCE_KD;

alter table "xlfin_bstldt"
   drop constraint FK_BSTLDT_REFERENCE_BSTL;

alter table "xlfin_exchangerate"
   drop constraint FK_ER_REFERENCE_C;

alter table "xlfin_karaccdt"
   drop constraint FK_KARADT_REFERENCE_KAR;

alter table "xlfin_kardt"
   drop constraint FK_KARDT_REFERENCE_KAR;

alter table "xlfin_kardt"
   drop constraint FK_KARDT_REFERENCE_VDTECA;

alter table "xlfin_keepdept"
   drop constraint FK_KD_REFERENCE_C1;

alter table "xlfin_keepdept"
   drop constraint FK_KD_REFERENCE_C2;

alter table "xlfin_keepdept"
   drop constraint FK_KD_REFERENCE_KDAR1;

alter table "xlfin_keepdept"
   drop constraint FK_KD_REFERENCE_KDAR2;

alter table "xlfin_keepdept"
   drop constraint FK_KD_REFERENCE_KDAR3;

alter table "xlfin_reportdata"
   drop constraint FK_RD_REFERENCE_RDEPT;

alter table "xlfin_reportdata"
   drop constraint FK_RD_REFERENCE_RF;

alter table "xlfin_reportdatadetail"
   drop constraint FK_XLFIN_RE_REFERENCE_XLFIN_RE;

alter table "xlfin_reportformcol"
   drop constraint FK_RFC_REFERENCE_RF;

alter table "xlfin_reportformformula"
   drop constraint FK_RFF_REFERENCE_RF;

alter table "xlfin_reportformrow"
   drop constraint FK_RFR_REFERENCE_RF;

alter table "xlfin_vdtextra"
   drop constraint FK_VDTE_REFERENCE_VDT;

alter table "xlfin_vdtextra"
   drop constraint FK_VDTE_REFERENCE_VEC;

alter table "xlfin_vdtextracolattr"
   drop constraint FK_VDTECA_REFERENCE_VDTEC;

alter table "xlfin_voucher"
   drop constraint FK_V_REFERENCE_F;

alter table "xlfin_voucher"
   drop constraint FK_V_REFERENCE_ID;

alter table "xlfin_voucher"
   drop constraint FK_V_REFERENCE_KD;

alter table "xlfin_voucher"
   drop constraint FK_V_REFERENCE_U1;

alter table "xlfin_voucher"
   drop constraint FK_V_REFERENCE_U2;

alter table "xlfin_voucherdetail"
   drop constraint FK_VD_REFERENCE_A;

alter table "xlfin_voucherdetail"
   drop constraint FK_VD_REFERENCE_BS;

alter table "xlfin_voucherdetail"
   drop constraint FK_VD_REFERENCE_C;

alter table "xlfin_voucherdetail"
   drop constraint FK_VDT_REFERENCE_KD;

alter table "xlfin_voucherdetail"
   drop constraint FK_VDT_REFERENCE_U;

alter table "xlfin_voucherdetail"
   drop constraint FK_VOD_REFERENCE_VO;

alter table "xlfin_vtempletdt"
   drop constraint FK_VTLDT_REFERENCE_VTL;

alter table "xlfin_vtldtcol"
   drop constraint FK_VTLDTC_REFERENCE_VTLDT;

alter table "xlsys_basebusi"
   drop constraint FK_BB_REFERENCE_F;

alter table "xlsys_basebusi"
   drop constraint FK_BB_REFERENCE_I;

alter table "xlsys_basebusi"
   drop constraint FK_BB_REFERENCE_U;

alter table "xlsys_dictdetail"
   drop constraint FK_DD_REFERENCE_D;

alter table "xlsys_exttableinfodetail"
   drop constraint FK_ETI_REFERENCE_ETID;

alter table "xlsys_flow"
   drop constraint FK_F_REFERENCE_V1;

alter table "xlsys_flow"
   drop constraint FK_F_REFERENCE_V2;

alter table "xlsys_flow"
   drop constraint FK_FL_REFERENCE_P1;

alter table "xlsys_flow"
   drop constraint FK_FL_REFERENCE_P2;

alter table "xlsys_flowcodealloc"
   drop constraint FK_FCA_REFERENCE_CA;

alter table "xlsys_flowcodealloc"
   drop constraint FK_FCA_REFERENCE_F;

alter table "xlsys_flowcondition"
   drop constraint FK_FC_REFERENCE_F;

alter table "xlsys_flowjava"
   drop constraint FK_FJAVA_REFERENCE_F;

alter table "xlsys_flowjava"
   drop constraint FK_FJAVA_REFERENCE_V;

alter table "xlsys_flowjs"
   drop constraint FK_FJS_REFERENCE_F;

alter table "xlsys_flowjs"
   drop constraint FK_FJS_REFERENCE_V;

alter table "xlsys_flowlogic"
   drop constraint FK_FL_REFERENCE_F;

alter table "xlsys_flowright"
   drop constraint FK_FR_REFERENCE_R;

alter table "xlsys_flowright"
   drop constraint FK_FR_REFERENCE_FC;

alter table "xlsys_idrelation"
   drop constraint FK_IR_REFERENCE_R;

alter table "xlsys_idrelation"
   drop constraint FK_IR_REFERENCE_I;

alter table "xlsys_menuright"
   drop constraint FK_MR_REFERENCE_M;

alter table "xlsys_partdetail"
   drop constraint FK_PD_REFERENCE_P;

alter table "xlsys_partdetail"
   drop constraint FK_PD_REFERENCE_V1;

alter table "xlsys_partdetail"
   drop constraint FK_PD_REFERENCE_V2;

alter table "xlsys_queryparamsave"
   drop constraint FK_QPS_REFERENCE_ID;

alter table "xlsys_queryparamsave"
   drop constraint FK_QPS_REFERENCE_V;

alter table "xlsys_ratify"
   drop constraint FK_R_REFERENCE_FC_F;

alter table "xlsys_ratify"
   drop constraint FK_R_REFERENCE_FC_T;

alter table "xlsys_ratify"
   drop constraint FK_R_REFERENCE_U;

alter table "xlsys_ratifydetail"
   drop constraint FK_RD_REFERENCE_R;

alter table "xlsys_ratifydetail"
   drop constraint FK_RD_REFERENCE_U1;

alter table "xlsys_ratifydetail"
   drop constraint FK_RD_REFERENCE_U2;

alter table "xlsys_transportdetail"
   drop constraint FK_TSDT_REFERENCE_TS;

alter table "xlsys_transportdtcolmap"
   drop constraint FK_TSDTCM_REFERENCE_TSDT;

alter table "xlsys_transportkeysynonym"
   drop constraint FK_TSKS_REFERENCE_TSK;

alter table "xlsys_transportmap"
   drop constraint FK_TSK_REFERENCE_TSM;

alter table "xlsys_transportrun"
   drop constraint FK_TSR_REFERENCE_TS;

alter table "xlsys_viewdetail"
   drop constraint FK_VD_REFERENCE_V;

alter table "xlsys_viewqueryparam"
   drop constraint FK_VQP_REFERENCE_V;

drop table "xlfin_account" cascade constraints;

drop table "xlfin_accountcondition" cascade constraints;

drop table "xlfin_accountingitem" cascade constraints;

drop table "xlfin_balance" cascade constraints;

drop table "xlfin_balanceitem" cascade constraints;

drop table "xlfin_bankstmt" cascade constraints;

drop table "xlfin_bankstmtbalance" cascade constraints;

drop table "xlfin_bankstmttemplet" cascade constraints;

drop table "xlfin_bstldt" cascade constraints;

drop table "xlfin_currency" cascade constraints;

drop table "xlfin_exchangerate" cascade constraints;

drop table "xlfin_karaccdt" cascade constraints;

drop table "xlfin_kardt" cascade constraints;

drop table "xlfin_kdeptaccrealtion" cascade constraints;

drop table "xlfin_keepdept" cascade constraints;

drop index "un_rrbe";

drop table "xlfin_reportdata" cascade constraints;

drop table "xlfin_reportdatadetail" cascade constraints;

drop table "xlfin_reportdept" cascade constraints;

drop table "xlfin_reportform" cascade constraints;

drop table "xlfin_reportformcol" cascade constraints;

drop table "xlfin_reportformformula" cascade constraints;

drop table "xlfin_reportformrow" cascade constraints;

drop table "xlfin_vdtextra" cascade constraints;

drop table "xlfin_vdtextracol" cascade constraints;

drop table "xlfin_vdtextracolattr" cascade constraints;

drop table "xlfin_voucher" cascade constraints;

drop table "xlfin_voucherdetail" cascade constraints;

drop table "xlfin_vouchertemplet" cascade constraints;

drop table "xlfin_vtempletdt" cascade constraints;

drop table "xlfin_vtldtcol" cascade constraints;

drop table "xlsys_attachment" cascade constraints;

drop table "xlsys_basebusi" cascade constraints;

drop table "xlsys_codealloc" cascade constraints;

drop table "xlsys_department" cascade constraints;

drop table "xlsys_dict" cascade constraints;

drop table "xlsys_dictdetail" cascade constraints;

drop table "xlsys_extracmd" cascade constraints;

drop table "xlsys_exttableinfo" cascade constraints;

drop table "xlsys_exttableinfodetail" cascade constraints;

drop table "xlsys_flow" cascade constraints;

drop table "xlsys_flowcodealloc" cascade constraints;

drop table "xlsys_flowcondition" cascade constraints;

drop table "xlsys_flowjava" cascade constraints;

drop table "xlsys_flowjs" cascade constraints;

drop table "xlsys_flowlogic" cascade constraints;

drop table "xlsys_flowright" cascade constraints;

drop table "xlsys_identity" cascade constraints;

drop index "xlsys_idrelation_tcv";

drop index "xlsys_idrelation_uq";

drop table "xlsys_idrelation" cascade constraints;

drop table "xlsys_image" cascade constraints;

drop table "xlsys_javaclass" cascade constraints;

drop table "xlsys_menu" cascade constraints;

drop table "xlsys_menuright" cascade constraints;

drop table "xlsys_part" cascade constraints;

drop table "xlsys_partdetail" cascade constraints;

drop table "xlsys_position" cascade constraints;

drop table "xlsys_print" cascade constraints;

drop table "xlsys_queryparamsave" cascade constraints;

drop table "xlsys_ratify" cascade constraints;

drop table "xlsys_ratifydetail" cascade constraints;

drop index UN_RIGHT_SK;

drop index UN_RIGHT_RC;

drop table "xlsys_right" cascade constraints;

drop table "xlsys_translator" cascade constraints;

drop table "xlsys_transport" cascade constraints;

drop table "xlsys_transportdetail" cascade constraints;

drop table "xlsys_transportdtcolmap" cascade constraints;

drop table "xlsys_transportkey" cascade constraints;

drop table "xlsys_transportkeysynonym" cascade constraints;

drop table "xlsys_transportmap" cascade constraints;

drop table "xlsys_transportrun" cascade constraints;

drop table "xlsys_user" cascade constraints;

drop table "xlsys_view" cascade constraints;

drop table "xlsys_viewdetail" cascade constraints;

drop table "xlsys_viewqueryparam" cascade constraints;

/*==============================================================*/
/* Table: "xlfin_account"                                       */
/*==============================================================*/
create table "xlfin_account" 
(
   "accid"              VARCHAR2(256)        not null,
   "name"               VARCHAR2(1024),
   "adc"                NUMBER(2,0),
   "vdc"                NUMBER(2,0),
   "acctype"            NUMBER(2,0),
   "forbankstmt"        NUMBER(2,0),
   constraint PK_XLFIN_ACCOUNT primary key ("accid")
);

comment on table "xlfin_account" is
'科目表';

/*==============================================================*/
/* Table: "xlfin_accountcondition"                              */
/*==============================================================*/
create table "xlfin_accountcondition" 
(
   "kdeptid"            VARCHAR2(256)        not null,
   "year"               NUMBER(8,0)          not null,
   "month"              NUMBER(2,0)          not null,
   "condition"          VARCHAR2(256),
   constraint PK_XLFIN_ACCOUNTCONDITION primary key ("kdeptid", "year", "month")
);

comment on table "xlfin_accountcondition" is
'记账状态记录表';

comment on column "xlfin_accountcondition"."condition" is
'400:已记账;800:已结账';

/*==============================================================*/
/* Table: "xlfin_accountingitem"                                */
/*==============================================================*/
create table "xlfin_accountingitem" 
(
   "vdcol"              VARCHAR2(256)        not null,
   "kdeptids"           VARCHAR2(4000),
   "accids"             VARCHAR2(4000),
   "nasm"               NUMBER(2,0),
   "dbcol"              VARCHAR2(64),
   "cbcol"              VARCHAR2(64),
   "bcol"               VARCHAR2(64),
   "dvkdeptids"         VARCHAR2(4000),
   "dvaccids"           VARCHAR2(4000),
   constraint PK_XLFIN_ACCOUNTINGITEM primary key ("vdcol")
);

comment on table "xlfin_accountingitem" is
'核算项设置';

comment on column "xlfin_accountingitem"."vdcol" is
'凭证明细字段';

comment on column "xlfin_accountingitem"."nasm" is
'Non accounting statistics method
非核算项的统计方式
1:sum;2:max;3:min;4:avg';

comment on column "xlfin_accountingitem"."dbcol" is
'当vdc=1时统计放入balance表的字段';

comment on column "xlfin_accountingitem"."cbcol" is
'当vdc=-1时统计放入balance的字段';

comment on column "xlfin_accountingitem"."bcol" is
'忽略vdc时统计放入balance表的字段';

comment on column "xlfin_accountingitem"."dvkdeptids" is
'使用该字段进行往来核销的记账部门';

comment on column "xlfin_accountingitem"."dvaccids" is
'使用该字段进行往来核销的科目';

/*==============================================================*/
/* Table: "xlfin_balance"                                       */
/*==============================================================*/
create table "xlfin_balance" 
(
   "balanceid"          VARCHAR2(256)        not null,
   "year"               NUMBER(8,0),
   "month"              NUMBER(2,0),
   "kdeptid"            VARCHAR2(256),
   "fcrcid"             VARCHAR2(256),
   "accid"              VARCHAR2(256),
   "dfca"               NUMBER(18,6),
   "cfca"               NUMBER(18,6),
   "fcb"                NUMBER(18,6),
   "dsca"               NUMBER(18,6),
   "csca"               NUMBER(18,6),
   "scb"                NUMBER(18,6),
   "drca"               NUMBER(18,6),
   "crca"               NUMBER(18,6),
   "rcb"                NUMBER(18,6),
   "dusda"              NUMBER(18,6),
   "cusda"              NUMBER(18,6),
   "usdb"               NUMBER(18,6),
   "dquantitya"         NUMBER(18,6),
   "cquantitya"         NUMBER(18,6),
   "quantityb"          NUMBER(18,6),
   constraint PK_XLFIN_BALANCE primary key ("balanceid")
);

comment on table "xlfin_balance" is
'余额表';

/*==============================================================*/
/* Table: "xlfin_balanceitem"                                   */
/*==============================================================*/
create table "xlfin_balanceitem" 
(
   "bcol"               VARCHAR2(64)         not null,
   "operatormode"       NUMBER(2,0),
   constraint PK_XLFIN_BALANCEITEM primary key ("bcol")
);

comment on table "xlfin_balanceitem" is
'余额表项的配置';

comment on column "xlfin_balanceitem"."operatormode" is
'balance column operator mode
余额表列的运算方式
1:add;2:max;3:min;4:cover';

/*==============================================================*/
/* Table: "xlfin_bankstmt"                                      */
/*==============================================================*/
create table "xlfin_bankstmt" 
(
   "bsid"               VARCHAR2(256)        not null,
   "year"               NUMBER(8,0),
   "month"              NUMBER(2,0),
   "tradedate"          DATE,
   "kdeptid"            VARCHAR2(256),
   "userid"             VARCHAR2(256),
   "accid"              VARCHAR2(256),
   "bdc"                NUMBER(2,0),
   "fcrcid"             VARCHAR2(256),
   "fcrcamount"         NUMBER(18,6),
   "remark"             VARCHAR2(256),
   "vid"                VARCHAR2(256),
   "vidx"               NUMBER(8,0),
   "bcdate"             DATE,
   "bctype"             NUMBER(2,0),
   constraint PK_XLFIN_BANKSTMT primary key ("bsid")
);

comment on table "xlfin_bankstmt" is
'银行对账单表';

comment on column "xlfin_bankstmt"."bcdate" is
'对账日期';

comment on column "xlfin_bankstmt"."bctype" is
'对账类型
0:手动;1:自动';

/*==============================================================*/
/* Table: "xlfin_bankstmtbalance"                               */
/*==============================================================*/
create table "xlfin_bankstmtbalance" 
(
   "bsbid"              VARCHAR2(256)        not null,
   "kdeptid"            VARCHAR2(256),
   "fcrcid"             VARCHAR2(256),
   "accid"              VARCHAR2(256),
   "year"               NUMBER(8,0),
   "month"              NUMBER(2,0),
   "fcb"                NUMBER(18,6),
   constraint PK_XLFIN_BANKSTMTBALANCE primary key ("bsbid")
);

comment on table "xlfin_bankstmtbalance" is
'银行对账单余额表';

/*==============================================================*/
/* Table: "xlfin_bankstmttemplet"                               */
/*==============================================================*/
create table "xlfin_bankstmttemplet" 
(
   "bstlid"             VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "javalistener"       VARCHAR2(4000),
   "jslistener"         BLOB,
   constraint PK_XLFIN_BANKSTMTTEMPLET primary key ("bstlid")
);

comment on table "xlfin_bankstmttemplet" is
'银行对账单模板主表';

/*==============================================================*/
/* Table: "xlfin_bstldt"                                        */
/*==============================================================*/
create table "xlfin_bstldt" 
(
   "bstlid"             VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "templetcol"         VARCHAR2(256),
   "bscol"              VARCHAR2(256),
   constraint PK_XLFIN_BSTLDT primary key ("bstlid", "idx")
);

comment on table "xlfin_bstldt" is
'银行对账单模板明细';

/*==============================================================*/
/* Table: "xlfin_currency"                                      */
/*==============================================================*/
create table "xlfin_currency" 
(
   "crcid"              VARCHAR2(256)        not null,
   "name"               VARCHAR2(128),
   "crccode"            VARCHAR2(8),
   constraint PK_XLFIN_CURRENCY primary key ("crcid")
);

comment on table "xlfin_currency" is
'币种表';

/*==============================================================*/
/* Table: "xlfin_exchangerate"                                  */
/*==============================================================*/
create table "xlfin_exchangerate" 
(
   "erid"               VARCHAR2(256)        not null,
   "crcid"              VARCHAR2(256),
   "buyingrate"         NUMBER(18,6),
   "cashbuyingrate"     NUMBER(18,6),
   "sellingrate"        NUMBER(18,6),
   "cashsellingrate"    NUMBER(18,6),
   "middlerate"         NUMBER(18,6),
   "pubtime"            DATE,
   constraint PK_XLFIN_EXCHANGERATE primary key ("erid")
);

comment on table "xlfin_exchangerate" is
'汇率表
http://www.boc.cn/sourcedb/whpj/enindex.html';

/*==============================================================*/
/* Table: "xlfin_karaccdt"                                      */
/*==============================================================*/
create table "xlfin_karaccdt" 
(
   "karid"              VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "accids"             VARCHAR2(4000),
   "showcolumns"        VARCHAR2(4000),
   constraint PK_XLFIN_KARACCDT primary key ("karid", "idx")
);

comment on table "xlfin_karaccdt" is
'记账部门科目关系明细配置';

/*==============================================================*/
/* Table: "xlfin_kardt"                                         */
/*==============================================================*/
create table "xlfin_kardt" 
(
   "karid"              VARCHAR2(256)        not null,
   "vdtecaid"           VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   constraint PK_XLFIN_KARDT primary key ("karid", "vdtecaid")
);

comment on table "xlfin_kardt" is
'记账部门对应科目所拥有的字段关系表';

/*==============================================================*/
/* Table: "xlfin_kdeptaccrealtion"                              */
/*==============================================================*/
create table "xlfin_kdeptaccrealtion" 
(
   "karid"              VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "accids"             VARCHAR2(4000),
   constraint PK_XLFIN_KDEPTACCREALTION primary key ("karid")
);

comment on table "xlfin_kdeptaccrealtion" is
'科目关系定义主表';

/*==============================================================*/
/* Table: "xlfin_keepdept"                                      */
/*==============================================================*/
create table "xlfin_keepdept" 
(
   "kdeptid"            VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "standardcrcid"      VARCHAR2(256),
   "reportcrcid"        VARCHAR2(256),
   "usedkarid"          VARCHAR2(256),
   "nocarryoverkarid"   VARCHAR2(256),
   "cockarid"           VARCHAR2(256),
   "needdcequal"        NUMBER(2,0),
   "vdatemode"          NUMBER(2,0),
   "transfermode"       NUMBER(2,0),
   "beginvdate"         DATE,
   "kdepttype"          NUMBER(2,0),
   "beginbsdate"        DATE,
   constraint PK_XLFIN_KEEPDEPT primary key ("kdeptid")
);

comment on table "xlfin_keepdept" is
'记账部门';

comment on column "xlfin_keepdept"."vdatemode" is
'凭证日期模式
0:与当前日期同月时使用当前日期
1:与当前日期同月时使用上一次凭证日期';

comment on column "xlfin_keepdept"."transfermode" is
'转账模式
0:转入时合并
1:转入时不合并';

comment on column "xlfin_keepdept"."beginvdate" is
'期初日期，该记账部门的凭证日期只能大于等于该日期';

comment on column "xlfin_keepdept"."kdepttype" is
'记账部门类型, 0:记账部门下属;1:记账部门节点
只有为1时该条记录才是系统使用的真正的记账部门';

/*==============================================================*/
/* Table: "xlfin_reportdata"                                    */
/*==============================================================*/
create table "xlfin_reportdata" 
(
   "rdid"               VARCHAR2(256)        not null,
   "rfid"               VARCHAR2(256)        not null,
   "rdeptid"            VARCHAR2(256)        not null,
   "timeperiod"         NUMBER(2,0),
   "begindate"          DATE                 not null,
   "enddate"            DATE                 not null,
   constraint PK_XLFIN_REPORTDATA primary key ("rdid")
);

comment on table "xlfin_reportdata" is
'报表数据';

/*==============================================================*/
/* Index: "un_rrbe"                                             */
/*==============================================================*/
create unique index "un_rrbe" on "xlfin_reportdata" (
   "rfid" ASC,
   "rdeptid" ASC,
   "begindate" ASC,
   "enddate" ASC
);

/*==============================================================*/
/* Table: "xlfin_reportdatadetail"                              */
/*==============================================================*/
create table "xlfin_reportdatadetail" 
(
   "rdid"               VARCHAR2(256)        not null,
   "idx"                NUMBER(2,0)          not null,
   "rfrowid"            VARCHAR2(256)        not null,
   "rfcolid"            VARCHAR2(256)        not null,
   "value"              VARCHAR2(4000),
   constraint PK_XLFIN_REPORTDATADETAIL primary key ("rdid", "idx")
);

comment on table "xlfin_reportdatadetail" is
'报表数据明细';

/*==============================================================*/
/* Table: "xlfin_reportdept"                                    */
/*==============================================================*/
create table "xlfin_reportdept" 
(
   "rdeptid"            VARCHAR2(256)        not null,
   "name"               VARCHAR2(256),
   "kdeptids"           VARCHAR2(4000),
   "gatherrdeptids"     VARCHAR2(4000),
   constraint PK_XLFIN_REPORTDEPT primary key ("rdeptid")
);

comment on table "xlfin_reportdept" is
'上报单位定义表';

/*==============================================================*/
/* Table: "xlfin_reportform"                                    */
/*==============================================================*/
create table "xlfin_reportform" 
(
   "rfid"               VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "javalistener"       VARCHAR2(4000),
   "jslistener"         BLOB,
   constraint PK_XLFIN_REPORTFORM primary key ("rfid")
);

comment on table "xlfin_reportform" is
'报表定义';

/*==============================================================*/
/* Table: "xlfin_reportformcol"                                 */
/*==============================================================*/
create table "xlfin_reportformcol" 
(
   "rfid"               VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "name"               VARCHAR2(64),
   "rfcolid"            VARCHAR2(256)        not null,
   "datatype"           NUMBER(2,0)          not null,
   "param"              VARCHAR2(4000),
   constraint PK_XLFIN_REPORTFORMCOL primary key ("rfid", "idx")
);

comment on table "xlfin_reportformcol" is
'报表列定义';

/*==============================================================*/
/* Table: "xlfin_reportformformula"                             */
/*==============================================================*/
create table "xlfin_reportformformula" 
(
   "rfid"               VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "rfrowid"            VARCHAR2(256)        not null,
   "rfcolid"            VARCHAR2(256)        not null,
   "formula"            BLOB,
   constraint PK_XLFIN_REPORTFORMFORMULA primary key ("rfid", "idx")
);

comment on table "xlfin_reportformformula" is
'报表公式定义';

/*==============================================================*/
/* Table: "xlfin_reportformrow"                                 */
/*==============================================================*/
create table "xlfin_reportformrow" 
(
   "rfid"               VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "name"               VARCHAR2(64),
   "rfrowid"            VARCHAR2(256)        not null,
   constraint PK_XLFIN_REPORTFORMROW primary key ("rfid", "idx")
);

comment on table "xlfin_reportformrow" is
'报表行定义';

/*==============================================================*/
/* Table: "xlfin_vdtextra"                                      */
/*==============================================================*/
create table "xlfin_vdtextra" 
(
   "voucherid"          VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "extracol"           VARCHAR2(256)        not null,
   "colvalue"           VARCHAR2(4000),
   constraint PK_XLFIN_VDTEXTRA primary key ("voucherid", "idx", "extracol")
);

comment on table "xlfin_vdtextra" is
'凭证明细附加表';

/*==============================================================*/
/* Table: "xlfin_vdtextracol"                                   */
/*==============================================================*/
create table "xlfin_vdtextracol" 
(
   "extracol"           VARCHAR2(256)        not null,
   "datatype"           NUMBER(2,0)          not null,
   "name"               VARCHAR2(64),
   constraint PK_XLFIN_VDTEXTRACOL primary key ("extracol")
);

comment on table "xlfin_vdtextracol" is
'凭证明细可附加字段定义';

/*==============================================================*/
/* Table: "xlfin_vdtextracolattr"                               */
/*==============================================================*/
create table "xlfin_vdtextracolattr" 
(
   "vdtecaid"           VARCHAR2(256)        not null,
   "extracol"           VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "type"               NUMBER(2,0),
   "param"              VARCHAR2(4000),
   "supportvalue"       VARCHAR2(4000),
   constraint PK_XLFIN_VDTEXTRACOLATTR primary key ("vdtecaid")
);

comment on table "xlfin_vdtextracolattr" is
'凭证明细附加字段属性定义';

/*==============================================================*/
/* Table: "xlfin_voucher"                                       */
/*==============================================================*/
create table "xlfin_voucher" 
(
   "voucherid"          VARCHAR2(256)        not null,
   "flowid"             VARCHAR2(256),
   "creater"            VARCHAR2(256),
   "creationdate"       DATE,
   "modifydate"         DATE,
   "condition"          VARCHAR2(256),
   "kdeptid"            VARCHAR2(256),
   "id"                 VARCHAR2(256),
   "vno"                NUMBER(8,0),
   "year"               NUMBER(8,0),
   "month"              NUMBER(2,0),
   "vdate"              DATE,
   "attachno"           NUMBER(2,0),
   "accounter"          VARCHAR2(256),
   "creationmode"       NUMBER(2,0)          not null,
   constraint PK_XLFIN_VOUCHER primary key ("voucherid")
);

comment on table "xlfin_voucher" is
'凭证表';

comment on column "xlfin_voucher"."creationmode" is
'凭证生成模式
0:手动生成;90:年初结转的未达账数据;91:年初结转的往来未核销数据;1~89:业务各接口数据;90~99:财务自动生成数据';

/*==============================================================*/
/* Table: "xlfin_voucherdetail"                                 */
/*==============================================================*/
create table "xlfin_voucherdetail" 
(
   "voucherid"          VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "year"               NUMBER(8,0),
   "month"              NUMBER(2,0),
   "vno"                NUMBER(8,0),
   "vdate"              DATE,
   "kdeptid"            VARCHAR2(256),
   "vdc"                NUMBER(2,0),
   "fcrcid"             VARCHAR2(256),
   "accid"              VARCHAR2(256),
   "userid"             VARCHAR2(256),
   "fcrcamount"         NUMBER(18,6),
   "ftosrate"           NUMBER(18,6),
   "ftorrate"           NUMBER(18,6),
   "ftousdrate"         NUMBER(18,6),
   "quantity"           NUMBER(18,6),
   "remark"             VARCHAR2(256),
   "bsid"               VARCHAR2(256),
   "bcdate"             DATE,
   "bctype"             NUMBER(2,0),
   constraint PK_XLFIN_VOUCHERDETAIL primary key ("voucherid", "idx")
);

comment on table "xlfin_voucherdetail" is
'凭证明细';

/*==============================================================*/
/* Table: "xlfin_vouchertemplet"                                */
/*==============================================================*/
create table "xlfin_vouchertemplet" 
(
   "vtlid"              VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "javalistener"       VARCHAR2(4000),
   "jslistener"         BLOB,
   "querysql"           VARCHAR2(4000),
   "creationmode"       NUMBER(2,0)          not null,
   constraint PK_XLFIN_VOUCHERTEMPLET primary key ("vtlid")
);

comment on table "xlfin_vouchertemplet" is
'凭证模板定义主表';

/*==============================================================*/
/* Table: "xlfin_vtempletdt"                                    */
/*==============================================================*/
create table "xlfin_vtempletdt" 
(
   "vtlid"              VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "name"               VARCHAR2(64),
   "vrowid"             VARCHAR2(256),
   "remark"             VARCHAR2(256),
   "vdc"                NUMBER(2,0),
   "accid"              VARCHAR2(256),
   "querysql"           VARCHAR2(4000),
   "formula"            BLOB,
   constraint PK_XLFIN_VTEMPLETDT primary key ("idx", "vtlid")
);

comment on table "xlfin_vtempletdt" is
'凭证模板明细配置';

/*==============================================================*/
/* Table: "xlfin_vtldtcol"                                      */
/*==============================================================*/
create table "xlfin_vtldtcol" 
(
   "vtlid"              VARCHAR2(256)        not null,
   "vtldtidx"           NUMBER(8,0)          not null,
   "idx"                NUMBER(8,0)          not null,
   "mdscolname"         VARCHAR2(256),
   "dtdscolname"        VARCHAR2(256),
   "vdtcolname"         VARCHAR2(256),
   constraint PK_XLFIN_VTLDTCOL primary key ("vtlid", "vtldtidx", "idx")
);

comment on table "xlfin_vtldtcol" is
'凭证模板明细字段配置';

/*==============================================================*/
/* Table: "xlsys_attachment"                                    */
/*==============================================================*/
create table "xlsys_attachment" 
(
   "attachid"           NUMBER(8,0)          not null,
   "name"               VARCHAR2(256),
   "attachdata"         BLOB,
   constraint PK_XLSYS_ATTACHMENT primary key ("attachid")
);

/*==============================================================*/
/* Table: "xlsys_basebusi"                                      */
/*==============================================================*/
create table "xlsys_basebusi" 
(
   "busiid"             VARCHAR2(256)        not null,
   "busino"             VARCHAR2(256),
   "name"               VARCHAR2(64),
   "flowid"             VARCHAR2(256)        not null,
   "id"                 VARCHAR2(256),
   "creater"            VARCHAR2(256),
   "creationdate"       DATE                 not null,
   "modifydate"         DATE                 not null,
   "condition"          VARCHAR2(256)        not null,
   constraint PK_XLSYS_BASEBUSI primary key ("busiid")
);

/*==============================================================*/
/* Table: "xlsys_codealloc"                                     */
/*==============================================================*/
create table "xlsys_codealloc" 
(
   "caid"               VARCHAR2(256)        not null,
   "name"               VARCHAR2(256),
   "clientjavascript"   BLOB,
   "clientjavamethod"   VARCHAR2(4000),
   "serverjavascript"   BLOB,
   "serverjavamethod"   VARCHAR2(4000),
   constraint PK_XLSYS_CODEALLOC primary key ("caid")
);

comment on table "xlsys_codealloc" is
'define alloc code method
select ''insert into xlsys_codealloc(caid,name,javamethod) values(''''''||caid||'''''',''''''||name||'''''',''''''||javamethod||'''''');'' from xlsys_codealloc;';

/*==============================================================*/
/* Table: "xlsys_department"                                    */
/*==============================================================*/
create table "xlsys_department" 
(
   "deptid"             VARCHAR2(256)        not null,
   "name"               VARCHAR2(64)         not null,
   constraint PK_XLSYS_DEPARTMENT primary key ("deptid")
);

comment on table "xlsys_department" is
'Department table';

/*==============================================================*/
/* Table: "xlsys_dict"                                          */
/*==============================================================*/
create table "xlsys_dict" 
(
   "dictid"             VARCHAR2(256)        not null,
   "name"               VARCHAR2(256),
   constraint PK_XLSYS_DICT primary key ("dictid")
);

comment on table "xlsys_dict" is
'The dictionary of xlsys';

/*==============================================================*/
/* Table: "xlsys_dictdetail"                                    */
/*==============================================================*/
create table "xlsys_dictdetail" 
(
   "dictid"             VARCHAR2(256)        not null,
   "code"               VARCHAR2(64)         not null,
   "name"               VARCHAR2(256),
   constraint PK_XLSYS_DICTDETAIL primary key ("dictid", "code")
);

/*==============================================================*/
/* Table: "xlsys_extracmd"                                      */
/*==============================================================*/
create table "xlsys_extracmd" 
(
   "extracmd"           VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "dispatchpath"       VARCHAR2(256),
   "javalistener"       VARCHAR2(4000),
   "jslistener"         BLOB,
   constraint PK_XLSYS_EXTRACMD primary key ("extracmd")
);

/*==============================================================*/
/* Table: "xlsys_exttableinfo"                                  */
/*==============================================================*/
create table "xlsys_exttableinfo" 
(
   "tableid"            NUMBER(8,0)          not null,
   "name"               VARCHAR2(64),
   "tablename"          VARCHAR2(256),
   constraint PK_XLSYS_EXTTABLEINFO primary key ("tableid")
);

comment on table "xlsys_exttableinfo" is
'Exter table information';

/*==============================================================*/
/* Table: "xlsys_exttableinfodetail"                            */
/*==============================================================*/
create table "xlsys_exttableinfodetail" 
(
   "tableid"            NUMBER(8,0)          not null,
   "idx"                NUMBER(8,0)          not null,
   "colname"            VARCHAR2(64),
   "name"               VARCHAR2(64),
   "primarykey"         NUMBER(2,0),
   "nullable"           NUMBER(2,0),
   constraint PK_XLSYS_EXTTABLEINFODETAIL primary key ("tableid", "idx")
);

comment on table "xlsys_exttableinfodetail" is
'The detail of exter table information';

/*==============================================================*/
/* Table: "xlsys_flow"                                          */
/*==============================================================*/
create table "xlsys_flow" 
(
   "flowid"             VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "listpartid"         VARCHAR2(256),
   "mvidoflpart"        NUMBER(8,0),
   "mainpartid"         VARCHAR2(256),
   "mvidofmpart"        NUMBER(8,0),
   "maintable"          VARCHAR2(64),
   "innercodecol"       VARCHAR2(64),
   "outtercodecol"      VARCHAR2(64),
   "jslistener"         BLOB,
   "javalistener"       VARCHAR2(4000),
   constraint PK_XLSYS_FLOW primary key ("flowid")
);

comment on table "xlsys_flow" is
'Flow Define
select ''insert into XLSYS_FLOW(FLOWID,NAME,LISTPARTID,MAINPARTID,MAINTABLE,INNERCODECOL,OUTTERCODECOL,JAVALISTENER) values(''''''||FLOWID||'''''',''''''||name||'''''',''''''||LISTPARTID||'''''',''''''||MAINPARTID||'''''',''''''||MAINTABLE||'''''',''''''||INNERCODECOL||'''''',''''''||OUTTERCODECOL||'''''',''''''||JAVALISTENER||'''''');'' from XLSYS_FLOW;';

/*==============================================================*/
/* Table: "xlsys_flowcodealloc"                                 */
/*==============================================================*/
create table "xlsys_flowcodealloc" 
(
   "fcaid"              VARCHAR2(256)        not null,
   "flowid"             VARCHAR2(256),
   "tablename"          VARCHAR2(64)         not null,
   "colname"            VARCHAR2(64)         not null,
   "caid"               VARCHAR2(256),
   constraint PK_XLSYS_FLOWCODEALLOC primary key ("fcaid")
);

comment on table "xlsys_flowcodealloc" is
'define code creation for flow';

/*==============================================================*/
/* Table: "xlsys_flowcondition"                                 */
/*==============================================================*/
create table "xlsys_flowcondition" 
(
   "flowid"             VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "condition"          VARCHAR2(64)         not null,
   "name"               VARCHAR2(256),
   "audittype"          NUMBER(2,0),
   "voterate"           NUMBER(18,6),
   constraint PK_XLSYS_FLOWCONDITION primary key ("flowid", "idx")
);

comment on table "xlsys_flowcondition" is
'The condition of flow';

comment on column "xlsys_flowcondition"."audittype" is
'审批类型, 0:单审;1:会审;2:组单审;3:组会审;4:投票审
[单审] : 任意一个人通过即可通过(提交时允许选择审批人)
[会审] : 所有人通过才可通过(提交时不允许选择审批人)
[组单审] : 任意一组人通过即可通过, 同一组里的人必须全部通过才算通过(提交时允许选择审批组，不允许选择审批人)
[组会审] : 所有组的人都通过才可通过, 同一组里的人只有要任意一个人通过就算通过(提交时允许选择审批人，但是每个组都必须选择至少一个审批人)
[投票审] : 按照一定比例票数通过后即可通过(提交时不允许选择)';

comment on column "xlsys_flowcondition"."voterate" is
'投票率，当audittype为4:投票审时，此参数有效';

/*==============================================================*/
/* Table: "xlsys_flowjava"                                      */
/*==============================================================*/
create table "xlsys_flowjava" 
(
   "flowid"             VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "viewid"             NUMBER(8,0),
   "javalistener"       VARCHAR2(4000),
   constraint PK_XLSYS_FLOWJAVA primary key ("flowid", "idx")
);

comment on table "xlsys_flowjava" is
'JavaListener for viewers of flow';

/*==============================================================*/
/* Table: "xlsys_flowjs"                                        */
/*==============================================================*/
create table "xlsys_flowjs" 
(
   "flowid"             VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "viewid"             NUMBER(8,0),
   "jslistener"         BLOB,
   constraint PK_XLSYS_FLOWJS primary key ("flowid", "idx")
);

comment on table "xlsys_flowjs" is
'JsListener for viewers of flow ';

/*==============================================================*/
/* Table: "xlsys_flowlogic"                                     */
/*==============================================================*/
create table "xlsys_flowlogic" 
(
   "flowid"             VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "fromcondition"      VARCHAR2(64),
   "tocondition"        VARCHAR2(64),
   "passtype"           NUMBER(2,0),
   "rejecttype"         NUMBER(2,0),
   "canrejectto"        VARCHAR2(64),
   constraint PK_XLSYS_FLOWLOGIC primary key ("flowid", "idx")
);

comment on table "xlsys_flowlogic" is
'The logic of each flow';

comment on column "xlsys_flowlogic"."passtype" is
'0:手动;1:自动提交;2:自动审批;3:自动审批并自动提交';

comment on column "xlsys_flowlogic"."rejecttype" is
'驳回类型,0:不允许驳回;1:可驳回到上一级;2:可驳回到任意上级;3:只允许驳回到自定义上级';

/*==============================================================*/
/* Table: "xlsys_flowright"                                     */
/*==============================================================*/
create table "xlsys_flowright" 
(
   "flowid"             VARCHAR2(256)        not null,
   "cdtidx"             NUMBER(8,0)          not null,
   "idx"                NUMBER(8,0)          not null,
   "belongrighttype"    NUMBER(2,0),
   "belongrightvalue"   VARCHAR2(256),
   "righttype"          NUMBER(2,0),
   "rightvalue"         VARCHAR2(256),
   "groupnm"            VARCHAR2(256),
   constraint PK_XLSYS_FLOWRIGHT primary key ("flowid", "cdtidx", "idx")
);

comment on table "xlsys_flowright" is
'The right of each flow condition';

comment on column "xlsys_flowright"."belongrighttype" is
'0:identity;1:user;2:department;3:position';

comment on column "xlsys_flowright"."righttype" is
'0:identity;1:user;2:department;3:position';

comment on column "xlsys_flowright"."groupnm" is
'分组名称,当audittype选用 2:组单审和 3:组会审 时有效，用来标识当前权限为哪个组别所有，分组名称相同的视为同一组';

/*==============================================================*/
/* Table: "xlsys_identity"                                      */
/*==============================================================*/
create table "xlsys_identity" 
(
   "id"                 VARCHAR2(256)        not null,
   "name"               VARCHAR2(64)         not null,
   constraint PK_XLSYS_IDENTITY primary key ("id")
);

comment on table "xlsys_identity" is
'Identity for Xue Lang System, it is unique for Xlsys';

/*==============================================================*/
/* Table: "xlsys_idrelation"                                    */
/*==============================================================*/
create table "xlsys_idrelation" 
(
   "id"                 VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "name"               VARCHAR2(64),
   "righttype"          NUMBER(2,0)          not null,
   "relationvalue"      VARCHAR2(32),
   constraint PK_XLSYS_IDRELATION primary key ("idx", "id")
);

comment on table "xlsys_idrelation" is
'The relationship of Identity.';

/*==============================================================*/
/* Index: "xlsys_idrelation_uq"                                 */
/*==============================================================*/
create unique index "xlsys_idrelation_uq" on "xlsys_idrelation" (
   "id" ASC,
   "righttype" ASC,
   "relationvalue" ASC
);

/*==============================================================*/
/* Index: "xlsys_idrelation_tcv"                                */
/*==============================================================*/
create index "xlsys_idrelation_tcv" on "xlsys_idrelation" (
   "righttype" ASC,
   "relationvalue" ASC
);

/*==============================================================*/
/* Table: "xlsys_image"                                         */
/*==============================================================*/
create table "xlsys_image" 
(
   "imageid"            NUMBER(8,0)          not null,
   "name"               VARCHAR2(256),
   "imagedata"          BLOB,
   constraint PK_XLSYS_IMAGE primary key ("imageid")
);

comment on table "xlsys_image" is
'图片库';

/*==============================================================*/
/* Table: "xlsys_javaclass"                                     */
/*==============================================================*/
create table "xlsys_javaclass" 
(
   "classid"            VARCHAR2(512)        not null,
   "name"               VARCHAR2(64),
   "javasource"         BLOB,
   "javabinary"         BLOB,
   constraint PK_XLSYS_JAVACLASS primary key ("classid")
);

comment on table "xlsys_javaclass" is
'The Additional Java Class';

/*==============================================================*/
/* Table: "xlsys_menu"                                          */
/*==============================================================*/
create table "xlsys_menu" 
(
   "menuid"             VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "type"               NUMBER(2,0),
   "cmd"                VARCHAR2(256),
   "param"              VARCHAR2(4000),
   "showninphone"       NUMBER(1,0),
   constraint PK_XLSYS_MENU primary key ("menuid")
);

comment on table "xlsys_menu" is
'Define Menu';

/*==============================================================*/
/* Table: "xlsys_menuright"                                     */
/*==============================================================*/
create table "xlsys_menuright" 
(
   "menuid"             VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "righttype"          NUMBER(2,0),
   "rightvalue"         VARCHAR2(256),
   constraint PK_XLSYS_MENURIGHT primary key ("menuid", "idx")
);

comment on column "xlsys_menuright"."righttype" is
'0:identity;1:user;2:department;3:position';

/*==============================================================*/
/* Table: "xlsys_part"                                          */
/*==============================================================*/
create table "xlsys_part" 
(
   "partid"             VARCHAR2(256)        not null,
   "name"               VARCHAR2(64)         not null,
   "parttype"           NUMBER(2,0)          not null,
   constraint PK_XLSYS_PART primary key ("partid")
);

comment on table "xlsys_part" is
'The Main Table Of Defining Part';

comment on column "xlsys_part"."parttype" is
'0:Node;1:Part';

/*==============================================================*/
/* Table: "xlsys_partdetail"                                    */
/*==============================================================*/
create table "xlsys_partdetail" 
(
   "partid"             VARCHAR2(256)        not null,
   "detailid"           VARCHAR2(256)        not null,
   "name"               VARCHAR2(64),
   "type"               NUMBER(2,0),
   "param"              VARCHAR2(4000),
   "viewid"             NUMBER(8,0),
   "relationtype"       NUMBER(2,0),
   "mainviewid"         NUMBER(8,0),
   "soporder"           NUMBER(8,0),
   constraint PK_XLSYS_PARTDETAIL primary key ("partid", "detailid")
);

comment on table "xlsys_partdetail" is
'The detail of part';

comment on column "xlsys_partdetail"."type" is
'0:SashForm;1:TabFolder;2:ExpandBar';

/*==============================================================*/
/* Table: "xlsys_position"                                      */
/*==============================================================*/
create table "xlsys_position" 
(
   "pstid"              VARCHAR2(256)        not null,
   "name"               VARCHAR2(64)         not null,
   constraint PK_XLSYS_POSITION primary key ("pstid")
);

comment on table "xlsys_position" is
'Position';

/*==============================================================*/
/* Table: "xlsys_print"                                         */
/*==============================================================*/
create table "xlsys_print" 
(
   "printid"            VARCHAR2(256)        not null,
   "name"               VARCHAR2(256),
   "printtype"          NUMBER(2,0),
   "template"           BLOB,
   constraint PK_XLSYS_PRINT primary key ("printid")
);

comment on table "xlsys_print" is
'打印模板定义表';

/*==============================================================*/
/* Table: "xlsys_queryparamsave"                                */
/*==============================================================*/
create table "xlsys_queryparamsave" 
(
   "viewid"             NUMBER(8,0)          not null,
   "id"                 VARCHAR2(256)        not null,
   "name"               VARCHAR2(64)         not null,
   "paramtype"          NUMBER(2,0)          not null,
   "paramsave"          BLOB,
   constraint PK_XLSYS_QUERYPARAMSAVE primary key ("viewid", "id", "name", "paramtype")
);

comment on table "xlsys_queryparamsave" is
'查询参数保存表';

comment on column "xlsys_queryparamsave"."paramtype" is
'参数类型
0:界面查询参数
1:分组参数';

/*==============================================================*/
/* Table: "xlsys_ratify"                                        */
/*==============================================================*/
create table "xlsys_ratify" 
(
   "rtfid"              VARCHAR2(256)        not null,
   "name"               VARCHAR2(256),
   "fromuserid"         VARCHAR2(256),
   "fromflowid"         VARCHAR2(256),
   "fromcdtidx"         NUMBER(8,0),
   "toflowid"           VARCHAR2(256),
   "tocdtidx"           NUMBER(8,0),
   "innercode"          VARCHAR2(256),
   "version"            NUMBER(8,0),
   "rtfret"             NUMBER(2,0),
   "rtfdate"            DATE,
   constraint PK_XLSYS_RATIFY primary key ("rtfid")
);

comment on table "xlsys_ratify" is
'The situation of ratifing business flow';

comment on column "xlsys_ratify"."rtfret" is
'0:已提交;1:已通过;2:已驳回';

/*==============================================================*/
/* Table: "xlsys_ratifydetail"                                  */
/*==============================================================*/
create table "xlsys_ratifydetail" 
(
   "rtfid"              VARCHAR2(256)        not null,
   "touserid"           VARCHAR2(256)        not null,
   "replaceuserid"      VARCHAR2(256),
   "rtfret"             NUMBER(2,0),
   "rtfdesc"            VARCHAR2(4000),
   "rtfdate"            DATE,
   "groupnm"            VARCHAR(256),
   constraint PK_XLSYS_RATIFYDETAIL primary key ("rtfid", "touserid")
);

comment on table "xlsys_ratifydetail" is
'The detail of ratify condition';

comment on column "xlsys_ratifydetail"."rtfret" is
'0:已提交;1:已通过;2:已驳回';

/*==============================================================*/
/* Table: "xlsys_right"                                         */
/*==============================================================*/
create table "xlsys_right" 
(
   "righttype"          NUMBER(2,0)          not null,
   "name"               VARCHAR2(64),
   "sessionkey"         VARCHAR2(256)        not null,
   "relationtable"      VARCHAR2(256)        not null,
   "relationcolumn"     VARCHAR2(256)        not null,
   constraint PK_XLSYS_RIGHT primary key ("righttype")
);

comment on table "xlsys_right" is
'系统权限定义表';

/*==============================================================*/
/* Index: UN_RIGHT_RC                                           */
/*==============================================================*/
create unique index UN_RIGHT_RC on "xlsys_right" (
   "relationcolumn" ASC
);

/*==============================================================*/
/* Index: UN_RIGHT_SK                                           */
/*==============================================================*/
create unique index UN_RIGHT_SK on "xlsys_right" (
   "sessionkey" ASC
);

/*==============================================================*/
/* Table: "xlsys_translator"                                    */
/*==============================================================*/
create table "xlsys_translator" 
(
   "tablename"          VARCHAR2(32)         not null,
   "defaultname"        VARCHAR2(256)        not null,
   "language"           VARCHAR2(32)         not null,
   "transname"          VARCHAR2(256),
   constraint PK_XLSYS_TRANSLATOR primary key ("tablename", "defaultname", "language")
);

comment on table "xlsys_translator" is
'Language support';

/*==============================================================*/
/* Table: "xlsys_transport"                                     */
/*==============================================================*/
create table "xlsys_transport" 
(
   "tsid"               VARCHAR2(256)        not null,
   "name"               VARCHAR2(256),
   constraint PK_XLSYS_TRANSPORT primary key ("tsid")
);

comment on table "xlsys_transport" is
'系统传输表, 用来做跨数据库的数据传输';

/*==============================================================*/
/* Table: "xlsys_transportdetail"                               */
/*==============================================================*/
create table "xlsys_transportdetail" 
(
   "tsid"               VARCHAR2(256)        not null,
   "idx"                NUMBER(8,0)          not null,
   "fromtable"          VARCHAR2(64),
   "totable"            VARCHAR2(64),
   "fromsql"            VARCHAR2(4000),
   "javalistener"       VARCHAR2(4000),
   "jslistener"         BLOB,
   "batchcount"         NUMBER(8,0),
   "cpsmcol"            NUMBER(2,0),
   "active"             NUMBER(2,0),
   constraint PK_XLSYS_TRANSPORTDETAIL primary key ("tsid", "idx")
);

comment on table "xlsys_transportdetail" is
'数据传输明细定义';

comment on column "xlsys_transportdetail"."batchcount" is
'批量更新数量';

comment on column "xlsys_transportdetail"."cpsmcol" is
'是否拷贝同名字段';

/*==============================================================*/
/* Table: "xlsys_transportdtcolmap"                             */
/*==============================================================*/
create table "xlsys_transportdtcolmap" 
(
   "tsid"               VARCHAR2(256)        not null,
   "tsdtidx"            NUMBER(8,0)          not null,
   "idx"                NUMBER(8,0)          not null,
   "fromcolumn"         VARCHAR2(64),
   "tocolumn"           VARCHAR2(64),
   constraint PK_XLSYS_TRANSPORTDTCOLMAP primary key ("tsid", "tsdtidx", "idx")
);

comment on table "xlsys_transportdtcolmap" is
'数据传输明细字段对照';

/*==============================================================*/
/* Table: "xlsys_transportkey"                                  */
/*==============================================================*/
create table "xlsys_transportkey" 
(
   "tskeyid"            VARCHAR2(32)         not null,
   "name"               VARCHAR2(256),
   constraint PK_XLSYS_TRANSPORTKEY primary key ("tskeyid")
);

comment on table "xlsys_transportkey" is
'传输数据的关键码定义';

/*==============================================================*/
/* Table: "xlsys_transportkeysynonym"                           */
/*==============================================================*/
create table "xlsys_transportkeysynonym" 
(
   "tskeyid"            VARCHAR2(32)         not null,
   "tablename"          VARCHAR2(64)         not null,
   "columnname"         VARCHAR2(64)         not null,
   constraint PK_XLSYS_TRANSPORTKEYSYNONYM primary key ("tskeyid", "tablename", "columnname")
);

comment on table "xlsys_transportkeysynonym" is
'传输关键码同义词定义表，主要用来定义哪些表的哪些字段使用该关键码来进行映射';

/*==============================================================*/
/* Table: "xlsys_transportmap"                                  */
/*==============================================================*/
create table "xlsys_transportmap" 
(
   "tsmapid"            VARCHAR2(256)        not null,
   "tskeyid"            VARCHAR2(32),
   "fromdsid"           NUMBER(8,0),
   "todsid"             NUMBER(8,0),
   "fromtable"          VARCHAR2(64),
   "totable"            VARCHAR2(64),
   "fromcolumn"         VARCHAR2(64),
   "tocolumn"           VARCHAR2(64),
   "fromvalue"          VARCHAR2(4000),
   "tovalue"            VARCHAR2(4000),
   "syndate"            DATE,
   "batchno"            VARCHAR2(32),
   "remark"             VARCHAR2(4000),
   "otheruse1"          VARCHAR2(256),
   "otheruse2"          VARCHAR2(256),
   "otheruse3"          VARCHAR2(256),
   constraint PK_XLSYS_TRANSPORTMAP primary key ("tsmapid")
);

comment on table "xlsys_transportmap" is
'数据传输映射表, 可看做数据传输日志, 以及数据在两个系统中的对照表';

/*==============================================================*/
/* Table: "xlsys_transportrun"                                  */
/*==============================================================*/
create table "xlsys_transportrun" 
(
   "tsrunid"            VARCHAR2(256)        not null,
   "tsid"               VARCHAR2(256),
   "fromdsid"           NUMBER(8,0),
   "todsid"             NUMBER(8,0),
   "totalthreadcount"   NUMBER(8,0),
   "threadcount"        NUMBER(8,0),
   "dataperthread"      NUMBER(8,0),
   constraint PK_XLSYS_TRANSPORTRUN primary key ("tsrunid")
);

comment on table "xlsys_transportrun" is
'数据传输运行表';

/*==============================================================*/
/* Table: "xlsys_user"                                          */
/*==============================================================*/
create table "xlsys_user" 
(
   "userid"             VARCHAR2(256)        not null,
   "name"               VARCHAR2(64)         not null,
   "password"           VARCHAR2(255),
   constraint PK_XLSYS_USER primary key ("userid")
);

comment on table "xlsys_user" is
'user table';

/*==============================================================*/
/* Table: "xlsys_view"                                          */
/*==============================================================*/
create table "xlsys_view" 
(
   "viewid"             NUMBER(8,0)          not null,
   "name"               VARCHAR2(64),
   "viewtype"           NUMBER(2,0),
   "param"              VARCHAR2(4000),
   "relationtype"       NUMBER(2,0),
   "mainviewid"         NUMBER(8,0),
   "jslistener"         BLOB,
   "javalistener"       VARCHAR2(4000),
   "selectbody"         VARCHAR2(4000),
   "frombody"           VARCHAR2(4000),
   "wherebody"          VARCHAR2(4000),
   "groupbybody"        VARCHAR2(4000),
   "orderbybody"        VARCHAR2(4000),
   "wholesql"           VARCHAR2(4000),
   "treecolname"        VARCHAR2(64),
   constraint PK_XLSYS_VIEW primary key ("viewid")
);

comment on table "xlsys_view" is
'Define the composite
select ''insert into XLSYS_VIEW (VIEWID,NAME,VIEWTYPE,PARAM,RELATIONTYPE,MAINVIEWID,JAVALISTENER,SELECTBODY,FROMBODY,WHEREBODY,GROUPBYBODY,ORDERBYBODY,WHOLESQL,TREECOLNAME) values (''||VIEWID||'',''''''||NAME||'''''',''||VIEWTYPE||'',''''''||PARAM||'''''',''||RELATIONTYPE||'',''||MAINVIEWID||'',''''''||JAVALISTENER||'''''',''''''||SELECTBODY||'''''',''''''||FROMBODY||'''''',''''''||WHEREBODY||'''''',''''''||GROUPBYBODY||'''''',''''''||ORDERBYBODY||'''''',''''''||WHOLESQL||'''''',''''''||TREECOLNAME||'''''');'' from XLSYS_VIEW';

/*==============================================================*/
/* Table: "xlsys_viewdetail"                                    */
/*==============================================================*/
create table "xlsys_viewdetail" 
(
   "viewid"             NUMBER(8,0)          not null,
   "idx"                NUMBER(8,0)          not null,
   "colname"            VARCHAR2(64),
   "name"               VARCHAR2(64),
   "colgroup"           VARCHAR2(64),
   "colgroupname"       VARCHAR2(64),
   "datatype"           NUMBER(2,0),
   "type"               NUMBER(2,0),
   "param"              VARCHAR2(4000),
   "defaultvalue"       VARCHAR2(256),
   "supportvalue"       VARCHAR2(4000),
   "aggregation"        NUMBER(2,0),
   "relationcolname"    VARCHAR2(64),
   "sqlexp"             VARCHAR2(4000),
   "showninphoneoverview" NUMBER(1,0),
   "showninphonedetail" NUMBER(1,0),
   constraint PK_XLSYS_VIEWDETAIL primary key ("viewid", "idx")
);

comment on table "xlsys_viewdetail" is
'Detail of view';

/*==============================================================*/
/* Table: "xlsys_viewqueryparam"                                */
/*==============================================================*/
create table "xlsys_viewqueryparam" 
(
   "viewid"             NUMBER(8,0)          not null,
   "idx"                NUMBER(8,0)          not null,
   "colname"            VARCHAR2(64),
   "name"               VARCHAR2(64),
   "datatype"           NUMBER(2,0),
   "type"               NUMBER(2,0),
   "param"              VARCHAR2(4000),
   "defaultvalue"       VARCHAR2(256),
   "supportvalue"       VARCHAR2(4000),
   "showninphone"       NUMBER(1,0),
   constraint PK_XLSYS_VIEWQUERYPARAM primary key ("viewid", "idx")
);

comment on table "xlsys_viewqueryparam" is
'Query parameter for view';

alter table "xlfin_accountcondition"
   add constraint FK_AC_REFERENCE_KD foreign key ("kdeptid")
      references "xlfin_keepdept" ("kdeptid");

alter table "xlfin_balance"
   add constraint FK_B_REFERENCE_A foreign key ("accid")
      references "xlfin_account" ("accid");

alter table "xlfin_balance"
   add constraint FK_B_REFERENCE_C foreign key ("fcrcid")
      references "xlfin_currency" ("crcid");

alter table "xlfin_balance"
   add constraint FK_B_REFERENCE_KD foreign key ("kdeptid")
      references "xlfin_keepdept" ("kdeptid");

alter table "xlfin_bankstmt"
   add constraint FK_BS_REFERENCE_A foreign key ("accid")
      references "xlfin_account" ("accid");

alter table "xlfin_bankstmt"
   add constraint FK_BS_REFERENCE_C foreign key ("fcrcid")
      references "xlfin_currency" ("crcid");

alter table "xlfin_bankstmt"
   add constraint FK_BS_REFERENCE_KD foreign key ("kdeptid")
      references "xlfin_keepdept" ("kdeptid");

alter table "xlfin_bankstmt"
   add constraint FK_BS_REFERENCE_U foreign key ("userid")
      references "xlsys_user" ("userid");

alter table "xlfin_bankstmt"
   add constraint FK_BS_REFERENCE_VD foreign key ("vid", "vidx")
      references "xlfin_voucherdetail" ("voucherid", "idx");

alter table "xlfin_bankstmtbalance"
   add constraint FK_BSB_REFERENCE_A foreign key ("accid")
      references "xlfin_account" ("accid");

alter table "xlfin_bankstmtbalance"
   add constraint FK_BSB_REFERENCE_C foreign key ("fcrcid")
      references "xlfin_currency" ("crcid");

alter table "xlfin_bankstmtbalance"
   add constraint FK_BSB_REFERENCE_KD foreign key ("kdeptid")
      references "xlfin_keepdept" ("kdeptid");

alter table "xlfin_bstldt"
   add constraint FK_BSTLDT_REFERENCE_BSTL foreign key ("bstlid")
      references "xlfin_bankstmttemplet" ("bstlid");

alter table "xlfin_exchangerate"
   add constraint FK_ER_REFERENCE_C foreign key ("crcid")
      references "xlfin_currency" ("crcid");

alter table "xlfin_karaccdt"
   add constraint FK_KARADT_REFERENCE_KAR foreign key ("karid")
      references "xlfin_kdeptaccrealtion" ("karid");

alter table "xlfin_kardt"
   add constraint FK_KARDT_REFERENCE_KAR foreign key ("karid")
      references "xlfin_kdeptaccrealtion" ("karid");

alter table "xlfin_kardt"
   add constraint FK_KARDT_REFERENCE_VDTECA foreign key ("vdtecaid")
      references "xlfin_vdtextracolattr" ("vdtecaid");

alter table "xlfin_keepdept"
   add constraint FK_KD_REFERENCE_C1 foreign key ("standardcrcid")
      references "xlfin_currency" ("crcid");

alter table "xlfin_keepdept"
   add constraint FK_KD_REFERENCE_C2 foreign key ("reportcrcid")
      references "xlfin_currency" ("crcid");

alter table "xlfin_keepdept"
   add constraint FK_KD_REFERENCE_KDAR1 foreign key ("usedkarid")
      references "xlfin_kdeptaccrealtion" ("karid");

alter table "xlfin_keepdept"
   add constraint FK_KD_REFERENCE_KDAR2 foreign key ("nocarryoverkarid")
      references "xlfin_kdeptaccrealtion" ("karid");

alter table "xlfin_keepdept"
   add constraint FK_KD_REFERENCE_KDAR3 foreign key ("cockarid")
      references "xlfin_kdeptaccrealtion" ("karid");

alter table "xlfin_reportdata"
   add constraint FK_RD_REFERENCE_RDEPT foreign key ("rdeptid")
      references "xlfin_reportdept" ("rdeptid");

alter table "xlfin_reportdata"
   add constraint FK_RD_REFERENCE_RF foreign key ("rfid")
      references "xlfin_reportform" ("rfid");

alter table "xlfin_reportdatadetail"
   add constraint FK_XLFIN_RE_REFERENCE_XLFIN_RE foreign key ("rdid")
      references "xlfin_reportdata" ("rdid");

alter table "xlfin_reportformcol"
   add constraint FK_RFC_REFERENCE_RF foreign key ("rfid")
      references "xlfin_reportform" ("rfid");

alter table "xlfin_reportformformula"
   add constraint FK_RFF_REFERENCE_RF foreign key ("rfid")
      references "xlfin_reportform" ("rfid");

alter table "xlfin_reportformrow"
   add constraint FK_RFR_REFERENCE_RF foreign key ("rfid")
      references "xlfin_reportform" ("rfid");

alter table "xlfin_vdtextra"
   add constraint FK_VDTE_REFERENCE_VDT foreign key ("voucherid", "idx")
      references "xlfin_voucherdetail" ("voucherid", "idx");

alter table "xlfin_vdtextra"
   add constraint FK_VDTE_REFERENCE_VEC foreign key ("extracol")
      references "xlfin_vdtextracol" ("extracol");

alter table "xlfin_vdtextracolattr"
   add constraint FK_VDTECA_REFERENCE_VDTEC foreign key ("extracol")
      references "xlfin_vdtextracol" ("extracol");

alter table "xlfin_voucher"
   add constraint FK_V_REFERENCE_F foreign key ("flowid")
      references "xlsys_flow" ("flowid");

alter table "xlfin_voucher"
   add constraint FK_V_REFERENCE_ID foreign key ("id")
      references "xlsys_identity" ("id");

alter table "xlfin_voucher"
   add constraint FK_V_REFERENCE_KD foreign key ("kdeptid")
      references "xlfin_keepdept" ("kdeptid");

alter table "xlfin_voucher"
   add constraint FK_V_REFERENCE_U1 foreign key ("creater")
      references "xlsys_user" ("userid");

alter table "xlfin_voucher"
   add constraint FK_V_REFERENCE_U2 foreign key ("accounter")
      references "xlsys_user" ("userid");

alter table "xlfin_voucherdetail"
   add constraint FK_VD_REFERENCE_A foreign key ("accid")
      references "xlfin_account" ("accid");

alter table "xlfin_voucherdetail"
   add constraint FK_VD_REFERENCE_BS foreign key ("bsid")
      references "xlfin_bankstmt" ("bsid");

alter table "xlfin_voucherdetail"
   add constraint FK_VD_REFERENCE_C foreign key ("fcrcid")
      references "xlfin_currency" ("crcid");

alter table "xlfin_voucherdetail"
   add constraint FK_VDT_REFERENCE_KD foreign key ("kdeptid")
      references "xlfin_keepdept" ("kdeptid");

alter table "xlfin_voucherdetail"
   add constraint FK_VDT_REFERENCE_U foreign key ("userid")
      references "xlsys_user" ("userid");

alter table "xlfin_voucherdetail"
   add constraint FK_VOD_REFERENCE_VO foreign key ("voucherid")
      references "xlfin_voucher" ("voucherid");

alter table "xlfin_vtempletdt"
   add constraint FK_VTLDT_REFERENCE_VTL foreign key ("vtlid")
      references "xlfin_vouchertemplet" ("vtlid");

alter table "xlfin_vtldtcol"
   add constraint FK_VTLDTC_REFERENCE_VTLDT foreign key ("vtldtidx", "vtlid")
      references "xlfin_vtempletdt" ("idx", "vtlid");

alter table "xlsys_basebusi"
   add constraint FK_BB_REFERENCE_F foreign key ("flowid")
      references "xlsys_flow" ("flowid");

alter table "xlsys_basebusi"
   add constraint FK_BB_REFERENCE_I foreign key ("id")
      references "xlsys_identity" ("id");

alter table "xlsys_basebusi"
   add constraint FK_BB_REFERENCE_U foreign key ("creater")
      references "xlsys_user" ("userid");

alter table "xlsys_dictdetail"
   add constraint FK_DD_REFERENCE_D foreign key ("dictid")
      references "xlsys_dict" ("dictid");

alter table "xlsys_exttableinfodetail"
   add constraint FK_ETI_REFERENCE_ETID foreign key ("tableid")
      references "xlsys_exttableinfo" ("tableid");

alter table "xlsys_flow"
   add constraint FK_F_REFERENCE_V1 foreign key ("mvidoflpart")
      references "xlsys_view" ("viewid");

alter table "xlsys_flow"
   add constraint FK_F_REFERENCE_V2 foreign key ("mvidofmpart")
      references "xlsys_view" ("viewid");

alter table "xlsys_flow"
   add constraint FK_FL_REFERENCE_P1 foreign key ("mainpartid")
      references "xlsys_part" ("partid");

alter table "xlsys_flow"
   add constraint FK_FL_REFERENCE_P2 foreign key ("listpartid")
      references "xlsys_part" ("partid");

alter table "xlsys_flowcodealloc"
   add constraint FK_FCA_REFERENCE_CA foreign key ("caid")
      references "xlsys_codealloc" ("caid");

alter table "xlsys_flowcodealloc"
   add constraint FK_FCA_REFERENCE_F foreign key ("flowid")
      references "xlsys_flow" ("flowid");

alter table "xlsys_flowcondition"
   add constraint FK_FC_REFERENCE_F foreign key ("flowid")
      references "xlsys_flow" ("flowid");

alter table "xlsys_flowjava"
   add constraint FK_FJAVA_REFERENCE_F foreign key ("flowid")
      references "xlsys_flow" ("flowid");

alter table "xlsys_flowjava"
   add constraint FK_FJAVA_REFERENCE_V foreign key ("viewid")
      references "xlsys_view" ("viewid");

alter table "xlsys_flowjs"
   add constraint FK_FJS_REFERENCE_F foreign key ("flowid")
      references "xlsys_flow" ("flowid");

alter table "xlsys_flowjs"
   add constraint FK_FJS_REFERENCE_V foreign key ("viewid")
      references "xlsys_view" ("viewid");

alter table "xlsys_flowlogic"
   add constraint FK_FL_REFERENCE_F foreign key ("flowid")
      references "xlsys_flow" ("flowid");

alter table "xlsys_flowright"
   add constraint FK_FR_REFERENCE_R foreign key ("righttype")
      references "xlsys_right" ("righttype");

alter table "xlsys_flowright"
   add constraint FK_FR_REFERENCE_FC foreign key ("flowid", "cdtidx")
      references "xlsys_flowcondition" ("flowid", "idx");

alter table "xlsys_idrelation"
   add constraint FK_IR_REFERENCE_R foreign key ("righttype")
      references "xlsys_right" ("righttype");

alter table "xlsys_idrelation"
   add constraint FK_IR_REFERENCE_I foreign key ("id")
      references "xlsys_identity" ("id");

alter table "xlsys_menuright"
   add constraint FK_MR_REFERENCE_M foreign key ("menuid")
      references "xlsys_menu" ("menuid");

alter table "xlsys_partdetail"
   add constraint FK_PD_REFERENCE_P foreign key ("partid")
      references "xlsys_part" ("partid");

alter table "xlsys_partdetail"
   add constraint FK_PD_REFERENCE_V1 foreign key ("viewid")
      references "xlsys_view" ("viewid");

alter table "xlsys_partdetail"
   add constraint FK_PD_REFERENCE_V2 foreign key ("mainviewid")
      references "xlsys_view" ("viewid");

alter table "xlsys_queryparamsave"
   add constraint FK_QPS_REFERENCE_ID foreign key ("id")
      references "xlsys_identity" ("id");

alter table "xlsys_queryparamsave"
   add constraint FK_QPS_REFERENCE_V foreign key ("viewid")
      references "xlsys_view" ("viewid");

alter table "xlsys_ratify"
   add constraint FK_R_REFERENCE_FC_F foreign key ("fromflowid", "fromcdtidx")
      references "xlsys_flowcondition" ("flowid", "idx");

alter table "xlsys_ratify"
   add constraint FK_R_REFERENCE_FC_T foreign key ("toflowid", "tocdtidx")
      references "xlsys_flowcondition" ("flowid", "idx");

alter table "xlsys_ratify"
   add constraint FK_R_REFERENCE_U foreign key ("fromuserid")
      references "xlsys_user" ("userid");

alter table "xlsys_ratifydetail"
   add constraint FK_RD_REFERENCE_R foreign key ("rtfid")
      references "xlsys_ratify" ("rtfid");

alter table "xlsys_ratifydetail"
   add constraint FK_RD_REFERENCE_U1 foreign key ("touserid")
      references "xlsys_user" ("userid");

alter table "xlsys_ratifydetail"
   add constraint FK_RD_REFERENCE_U2 foreign key ("replaceuserid")
      references "xlsys_user" ("userid");

alter table "xlsys_transportdetail"
   add constraint FK_TSDT_REFERENCE_TS foreign key ("tsid")
      references "xlsys_transport" ("tsid");

alter table "xlsys_transportdtcolmap"
   add constraint FK_TSDTCM_REFERENCE_TSDT foreign key ("tsid", "tsdtidx")
      references "xlsys_transportdetail" ("tsid", "idx");

alter table "xlsys_transportkeysynonym"
   add constraint FK_TSKS_REFERENCE_TSK foreign key ("tskeyid")
      references "xlsys_transportkey" ("tskeyid");

alter table "xlsys_transportmap"
   add constraint FK_TSK_REFERENCE_TSM foreign key ("tskeyid")
      references "xlsys_transportkey" ("tskeyid");

alter table "xlsys_transportrun"
   add constraint FK_TSR_REFERENCE_TS foreign key ("tsid")
      references "xlsys_transport" ("tsid");

alter table "xlsys_viewdetail"
   add constraint FK_VD_REFERENCE_V foreign key ("viewid")
      references "xlsys_view" ("viewid");

alter table "xlsys_viewqueryparam"
   add constraint FK_VQP_REFERENCE_V foreign key ("viewid")
      references "xlsys_view" ("viewid");

