/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/4/27 10:27:55                           */
/*==============================================================*/


drop table if exists XLSYSSH.GOLF_ACCOMMODATION;

drop table if exists XLSYSSH.GOLF_BRANCH;

drop table if exists XLSYSSH.GOLF_COMPETITIONSCHEDULE;

drop table if exists XLSYSSH.GOLF_COUSTERM;

drop table if exists XLSYSSH.GOLF_FEATURED;

drop table if exists XLSYSSH.GOLF_INFORMATION;

drop table if exists XLSYSSH.GOLF_MEMBER;

drop table if exists XLSYSSH.GOLF_MEMBERINFO;

drop table if exists XLSYSSH.GOLF_MESSAGE;

drop table if exists XLSYSSH.GOLF_NEWS;

drop table if exists XLSYSSH.GOLF_OVERVIEW;

drop table if exists XLSYSSH.GOLF_PHOTOS;

drop table if exists XLSYSSH.GOLF_PROGRAM;

drop table if exists XLSYSSH.GOLF_RELATEMEDIA;

drop table if exists XLSYSSH.GOLF_RESULT;

drop table if exists XLSYSSH.GOLF_SET;

drop table if exists XLSYSSH.GOLF_SLIDER;

drop table if exists XLSYSSH.GOLF_TAIWAN;

drop table if exists XLSYSSH.GOLF_TEAM;

drop table if exists XLSYSSH.GOLF_TEAMER;

drop table if exists XLSYSSH.GOLF_TOURNAMENT;

drop table if exists XLSYSSH.GOLF_TRANSPORT;

drop table if exists XLSYSSH.GOLF_VIDEOS;

drop table if exists XLSYSSH.SCORE;

drop table if exists XLSYSSH.TEST;

drop table if exists XLSYSSH.TESTSCORE;

drop table if exists XLSYSSH.XLEM_ATOMICUNIT;

drop table if exists XLSYSSH.XLEM_BUYER;

drop table if exists XLSYSSH.XLEM_ITEM;

drop table if exists XLSYSSH.XLEM_ITEMSKU;

drop table if exists XLSYSSH.XLEM_PREPSYNONYM;

drop table if exists XLSYSSH.XLEM_SEARCHKEYWORD;

drop table if exists XLSYSSH.XLEM_SEARCHTEXT;

drop table if exists XLSYSSH.XLEM_SELLER;

drop table if exists XLSYSSH.XLEM_SKU;

drop table if exists XLSYSSH.XLEM_SPU;

drop table if exists XLSYSSH.XLEM_SPUCATEGORY;

drop table if exists XLSYSSH.XLEM_SPUINDEX;

drop table if exists XLSYSSH.XLEM_STOCK;

drop table if exists XLSYSSH.XLEM_STOCKHISTORY;

drop table if exists XLSYSSH.XLEM_SYNONYM;

drop table if exists XLSYSSH.XLEM_UNIT;

drop table if exists XLSYSSH.XLEM_USER;

drop table if exists XLSYSSH.XLEM_USERLEVEL;

drop table if exists XLSYSSH.XLFIN_ACCOUNT;

drop table if exists XLSYSSH.XLFIN_ACCOUNTCONDITION;

drop table if exists XLSYSSH.XLFIN_ACCOUNTINGITEM;

drop table if exists XLSYSSH.XLFIN_BALANCE;

drop table if exists XLSYSSH.XLFIN_BALANCEITEM;

drop table if exists XLSYSSH.XLFIN_BANKSTMT;

drop table if exists XLSYSSH.XLFIN_BANKSTMTBALANCE;

drop table if exists XLSYSSH.XLFIN_BANKSTMTTEMPLET;

drop table if exists XLSYSSH.XLFIN_BSTLDT;

drop table if exists XLSYSSH.XLFIN_CURRENCY;

drop table if exists XLSYSSH.XLFIN_EXCHANGERATE;

drop table if exists XLSYSSH.XLFIN_KARACCDT;

drop table if exists XLSYSSH.XLFIN_KARDT;

drop table if exists XLSYSSH.XLFIN_KDEPTACCREALTION;

drop table if exists XLSYSSH.XLFIN_KEEPDEPT;

drop index UN_RRBE on XLSYSSH.XLFIN_REPORTDATA;

drop table if exists XLSYSSH.XLFIN_REPORTDATA;

drop table if exists XLSYSSH.XLFIN_REPORTDATADETAIL;

drop table if exists XLSYSSH.XLFIN_REPORTDEPT;

drop table if exists XLSYSSH.XLFIN_REPORTFORM;

drop table if exists XLSYSSH.XLFIN_REPORTFORMCOL;

drop table if exists XLSYSSH.XLFIN_REPORTFORMFORMULA;

drop table if exists XLSYSSH.XLFIN_REPORTFORMROW;

drop table if exists XLSYSSH.XLFIN_VDTEXTRA;

drop table if exists XLSYSSH.XLFIN_VDTEXTRACOL;

drop table if exists XLSYSSH.XLFIN_VDTEXTRACOLATTR;

drop table if exists XLSYSSH.XLFIN_VOUCHER;

drop table if exists XLSYSSH.XLFIN_VOUCHERDETAIL;

drop table if exists XLSYSSH.XLFIN_VOUCHERTEMPLET;

drop table if exists XLSYSSH.XLFIN_VTEMPLETDT;

drop table if exists XLSYSSH.XLFIN_VTLDTCOL;

drop table if exists XLSYSSH.XLSYS_ATTACHMENT;

drop table if exists XLSYSSH.XLSYS_AUTHORISEDRIGHT;

drop table if exists XLSYSSH.XLSYS_AUTHORIZE;

drop index UN_AD_FCI on XLSYSSH.XLSYS_AUTHORIZEDETAIL;

drop table if exists XLSYSSH.XLSYS_AUTHORIZEDETAIL;

drop table if exists XLSYSSH.XLSYS_BASEBUSI;

drop table if exists XLSYSSH.XLSYS_CODEALLOC;

drop table if exists XLSYSSH.XLSYS_DEPARTMENT;

drop table if exists XLSYSSH.XLSYS_DICT;

drop table if exists XLSYSSH.XLSYS_DICTDETAIL;

drop table if exists XLSYSSH.XLSYS_EMAILTEMPLATE;

drop table if exists XLSYSSH.XLSYS_EXTRACMD;

drop table if exists XLSYSSH.XLSYS_EXTTABLEINFO;

drop table if exists XLSYSSH.XLSYS_EXTTABLEINFODETAIL;

drop table if exists XLSYSSH.XLSYS_FLOW;

drop table if exists XLSYSSH.XLSYS_FLOWCODEALLOC;

drop table if exists XLSYSSH.XLSYS_FLOWCONDITION;

drop table if exists XLSYSSH.XLSYS_FLOWJAVA;

drop table if exists XLSYSSH.XLSYS_FLOWJS;

drop table if exists XLSYSSH.XLSYS_FLOWLOGIC;

drop table if exists XLSYSSH.XLSYS_FLOWPART;

drop table if exists XLSYSSH.XLSYS_FLOWRIGHT;

drop table if exists XLSYSSH.XLSYS_FLOWSUBTABLE;

drop table if exists XLSYSSH.XLSYS_IDENTITY;

drop index XLSYS_IDRELATION_UQ on XLSYSSH.XLSYS_IDRELATION;

drop index XLSYS_IDRELATION_TCV on XLSYSSH.XLSYS_IDRELATION;

drop table if exists XLSYSSH.XLSYS_IDRELATION;

drop table if exists XLSYSSH.XLSYS_IMAGE;

drop table if exists XLSYSSH.XLSYS_IPRESOURCE;

drop table if exists XLSYSSH.XLSYS_JAVACLASS;

drop table if exists XLSYSSH.XLSYS_MENU;

drop table if exists XLSYSSH.XLSYS_MENURIGHT;

drop table if exists XLSYSSH.XLSYS_OACATEGORY;

drop table if exists XLSYSSH.XLSYS_OACATEGORYRIGHT;

drop table if exists XLSYSSH.XLSYS_OACMBELONG;

drop table if exists XLSYSSH.XLSYS_OACMRELATION;

drop table if exists XLSYSSH.XLSYS_OAMODULE;

drop table if exists XLSYSSH.XLSYS_OAMODULEEXTRA;

drop table if exists XLSYSSH.XLSYS_OAMODULERIGHT;

drop table if exists XLSYSSH.XLSYS_PART;

drop table if exists XLSYSSH.XLSYS_PARTDETAIL;

drop table if exists XLSYSSH.XLSYS_POSITION;

drop table if exists XLSYSSH.XLSYS_PRINT;

drop table if exists XLSYSSH.XLSYS_QUERYPARAMSAVE;

drop table if exists XLSYSSH.XLSYS_RATIFY;

drop table if exists XLSYSSH.XLSYS_RATIFYDETAIL;

drop index UN_RIGHT_SK on XLSYSSH.XLSYS_RIGHT;

drop index UN_RIGHT_RC on XLSYSSH.XLSYS_RIGHT;

drop table if exists XLSYSSH.XLSYS_RIGHT;

drop table if exists XLSYSSH.XLSYS_TRANSLATOR;

drop table if exists XLSYSSH.XLSYS_TRANSPORT;

drop table if exists XLSYSSH.XLSYS_TRANSPORTDETAIL;

drop table if exists XLSYSSH.XLSYS_TRANSPORTDTCOLMAP;

drop table if exists XLSYSSH.XLSYS_TRANSPORTKEY;

drop table if exists XLSYSSH.XLSYS_TRANSPORTKEYSYNONYM;

drop table if exists XLSYSSH.XLSYS_TRANSPORTMAP;

drop table if exists XLSYSSH.XLSYS_TRANSPORTRUN;

drop table if exists XLSYSSH.XLSYS_USER;

drop table if exists XLSYSSH.XLSYS_USEREMAIL;

drop table if exists XLSYSSH.XLSYS_VIEW;

drop index UN_VD on XLSYSSH.XLSYS_VIEWDETAIL;

drop table if exists XLSYSSH.XLSYS_VIEWDETAIL;

drop table if exists XLSYSSH.XLSYS_VIEWDETAILPARAM;

drop table if exists XLSYSSH.XLSYS_VIEWQUERYPARAM;

drop table if exists XLSYSSH.XLSYS_WDTCOLUMN;

drop table if exists XLSYSSH.XLSYS_WIZARD;

drop table if exists XLSYSSH.XLSYS_WIZARDDETAIL;

/*==============================================================*/
/* User: XLSYSSH                                                */
/*==============================================================*/
create user XLSYSSH;

/*==============================================================*/
/* Table: GOLF_ACCOMMODATION                                    */
/*==============================================================*/
create table XLSYSSH.GOLF_ACCOMMODATION
(
   AC_ID                varchar(32) not null,
   MEMBERNAME           varchar(64),
   HOTLENO              varchar(6),
   AC_NO                varchar(6),
   GUESTTYPE            varchar(16),
   ROOMSHARE            varchar(16),
   BED1                 varchar(32),
   BED2                 varchar(32),
   BED3                 varchar(32),
   primary key (AC_ID)
);

/*==============================================================*/
/* Table: GOLF_BRANCH                                           */
/*==============================================================*/
create table XLSYSSH.GOLF_BRANCH
(
   BRA_ID               varchar(32) not null,
   COUNTRYNAME          varchar(16),
   ORGNAME              varchar(32),
   EMAIL                varchar(64),
   BRA_ORDER            numeric(5,0),
   ADDRESS              varchar(256),
   PHONENO              varchar(16),
   MOBILENO             varchar(16),
   STATUS               varchar(8),
   primary key (BRA_ID)
);

/*==============================================================*/
/* Table: GOLF_COMPETITIONSCHEDULE                              */
/*==============================================================*/
create table XLSYSSH.GOLF_COMPETITIONSCHEDULE
(
   COUNTRY              varchar(64) not null,
   EVENT                varchar(256),
   COURSE               varchar(256),
   FROM_DATES           datetime,
   TO_DATES             datetime,
   ORGANIZER            varchar(256),
   primary key (COUNTRY)
);

alter table XLSYSSH.GOLF_COMPETITIONSCHEDULE comment '2016 APJGA Competition';

/*==============================================================*/
/* Table: GOLF_COUSTERM                                         */
/*==============================================================*/
create table XLSYSSH.GOLF_COUSTERM
(
   COU_ID               varchar(32) not null,
   COUSTERMNAME         varchar(64),
   EVENT                varchar(512),
   VENUE                varchar(128),
   COUNTRY              varchar(16),
   LOGO                 longblob,
   STATUS               varchar(8),
   CATEGORY             varchar(256),
   STARTDATE            datetime,
   ENDDATE              datetime,
   COUSTERMTYPE         varchar(32),
   CURRENCYTYPE         varchar(16),
   CATEGORYAMOUNT       varchar(16),
   CONTACTNAME          varchar(32),
   CONTACTPHONE         varchar(16),
   CONTACTEMAIL         varchar(64),
   HOMEPAGE             varchar(32),
   CONTACTADRESS        varchar(256),
   IMAGEURL             varchar(200),
   primary key (COU_ID)
);

/*==============================================================*/
/* Table: GOLF_FEATURED                                         */
/*==============================================================*/
create table XLSYSSH.GOLF_FEATURED
(
   NAME                 varchar(256) not null,
   IMAGE                longblob,
   primary key (NAME)
);

/*==============================================================*/
/* Table: GOLF_INFORMATION                                      */
/*==============================================================*/
create table XLSYSSH.GOLF_INFORMATION
(
   INFOR_ID             varchar(32) not null,
   TITLE                varchar(128),
   MENU                 varchar(64),
   SM_URL               varchar(128),
   DESCRIPTION          longblob,
   STATUS               varchar(8),
   primary key (INFOR_ID)
);

/*==============================================================*/
/* Table: GOLF_MEMBER                                           */
/*==============================================================*/
create table XLSYSSH.GOLF_MEMBER
(
   MEMBER_ID            varchar(32) not null,
   FIRSTNAME            varchar(32),
   LASTNAME             varchar(32),
   COUNTRY              varchar(16),
   DOB                  datetime,
   MEM_PHOTO            longblob,
   MEMBERTYPE           varchar(32),
   GENDER               varchar(8),
   COACH_NO             varchar(64),
   CATEGORY             varchar(16),
   HANDICAP             varchar(8),
   PER_LANGUAGE         varchar(16),
   HC_NAME              varchar(256),
   HC_COUNTRY           varchar(16),
   HC_CITY              varchar(128),
   COACHNAME            varchar(64),
   COACH_PHNO           varchar(12),
   ACADEMYNAME          varchar(256),
   MYGB_BRAND           varchar(256),
   MYGB_MODEL           varchar(256),
   RA_STREET            varchar(256),
   RA_CITY              varchar(256),
   RA_STATE             varchar(256),
   RA_POSTALCODE        varchar(32),
   RA_COUNTRY           varchar(16),
   MA_STREET            varchar(256),
   MA_CITY              varchar(256),
   MA_STATE             varchar(256),
   MA_POSTALCODE        varchar(32),
   MA_COUNTRY           varchar(16),
   MA_EMAIL             varchar(256),
   SM_JTH               varchar(512),
   SM_PTH               varchar(512),
   SM_JFP               varchar(256),
   SM_PFP               varchar(256),
   PASSWORD             varchar(16),
   RE_PASSWORD          varchar(16),
   PW_QUESTION          varchar(256),
   ANSWER               varchar(128),
   primary key (MEMBER_ID)
);

/*==============================================================*/
/* Table: GOLF_MEMBERINFO                                       */
/*==============================================================*/
create table XLSYSSH.GOLF_MEMBERINFO
(
   INFO_ID              varchar(32) not null,
   RE_TITLE             varchar(128),
   RE_MESSAGE           longblob,
   CREATDATE            datetime,
   MODIFILEDDATE        datetime,
   POLICY               longblob,
   TERM                 longblob,
   primary key (INFO_ID)
);

/*==============================================================*/
/* Table: GOLF_MESSAGE                                          */
/*==============================================================*/
create table XLSYSSH.GOLF_MESSAGE
(
   MSG_ID               varchar(32) not null,
   MSGFROM              varchar(32),
   MSGTO                varchar(32),
   SUBJECT              varchar(256),
   MESSAGE              longblob,
   MSGDATE              datetime,
   STATUS               varchar(8),
   primary key (MSG_ID)
);

/*==============================================================*/
/* Table: GOLF_NEWS                                             */
/*==============================================================*/
create table XLSYSSH.GOLF_NEWS
(
   NEWS_ID              varchar(32) not null,
   TITLE                varchar(256),
   CATEGORY             varchar(32),
   CREATEDATE           datetime,
   STATUS               varchar(16),
   UPLODER              varchar(32),
   PHOTO                longblob,
   ARTICLE              longblob,
   VIDEO                longblob,
   primary key (NEWS_ID)
);

/*==============================================================*/
/* Table: GOLF_OVERVIEW                                         */
/*==============================================================*/
create table XLSYSSH.GOLF_OVERVIEW
(
   ID                   varchar(8) not null,
   NAME                 varchar(256),
   IMAGE                longblob,
   STAUS                varchar(32),
   DATES                datetime,
   ARTICLE              varchar(256),
   OPLODER              varchar(32),
   CATCATEGROY          varchar(32),
   primary key (ID)
);

/*==============================================================*/
/* Table: GOLF_PHOTOS                                           */
/*==============================================================*/
create table XLSYSSH.GOLF_PHOTOS
(
   ID                   varchar(8) not null,
   NAME                 varchar(32),
   IMAGE                longblob,
   LAST_LABEL           varchar(32),
   primary key (ID)
);

/*==============================================================*/
/* Table: GOLF_PROGRAM                                          */
/*==============================================================*/
create table XLSYSSH.GOLF_PROGRAM
(
   PROGRAM_ID           varchar(32) not null,
   TITLE                varchar(128),
   CONTENT              longblob,
   STATUS               varchar(8),
   MENU                 varchar(8),
   CREATEDATE           datetime,
   MODIFILEDDATE        datetime,
   PRO_INDEX            varchar(6),
   primary key (PROGRAM_ID)
);

/*==============================================================*/
/* Table: GOLF_RELATEMEDIA                                      */
/*==============================================================*/
create table XLSYSSH.GOLF_RELATEMEDIA
(
   MEDIA_ID             varchar(32) not null,
   LOGO                 longblob,
   MEDIA_URL            varchar(64),
   MEDIANAME            varchar(32),
   IMAGEURL             varchar(200),
   primary key (MEDIA_ID)
);

/*==============================================================*/
/* Table: GOLF_RESULT                                           */
/*==============================================================*/
create table XLSYSSH.GOLF_RESULT
(
   POS                  numeric(8,0) not null,
   PLAYER               varchar(256),
   SCORES               varchar(32),
   TOTAL                numeric(8,0),
   TOPAR                numeric(8,0),
   PRIZE_MONEY          numeric(8,0),
   SYMBOLS              varchar(64),
   primary key (POS)
);

/*==============================================================*/
/* Table: GOLF_SET                                              */
/*==============================================================*/
create table XLSYSSH.GOLF_SET
(
   SET_ID               varchar(32) not null,
   USRNAME              varchar(16),
   EMAIL                varchar(32),
   PASSWORD             varchar(16),
   WEBMASTEREMAIL       varchar(32),
   primary key (SET_ID)
);

/*==============================================================*/
/* Table: GOLF_SLIDER                                           */
/*==============================================================*/
create table XLSYSSH.GOLF_SLIDER
(
   SL_ID                varchar(32) not null,
   TITLE                varchar(256),
   IMAGE                longblob,
   LOGO                 longblob,
   SLNO                 numeric(6,0),
   SL_DATE              datetime,
   STATUS               varchar(8),
   IMAGEURL             varchar(200),
   primary key (SL_ID)
);

/*==============================================================*/
/* Table: GOLF_TAIWAN                                           */
/*==============================================================*/
create table XLSYSSH.GOLF_TAIWAN
(
   NAME                 varchar(256) not null,
   IMAGE                longblob,
   STAUS                varchar(32),
   primary key (NAME)
);

alter table XLSYSSH.GOLF_TAIWAN comment '2015 APJGA Hills Series Taiwan Junior Open';

/*==============================================================*/
/* Table: GOLF_TEAM                                             */
/*==============================================================*/
create table XLSYSSH.GOLF_TEAM
(
   TEAM_ID              varchar(32) not null,
   TEAMNO               numeric(6,0),
   TITLE                varchar(128),
   T_DESCRIPTION        longblob,
   LOGO                 longblob,
   LOGO_DESCRIPTION     longblob,
   POLICY               longblob,
   OURTEAM              longblob,
   primary key (TEAM_ID)
);

/*==============================================================*/
/* Table: GOLF_TEAMER                                           */
/*==============================================================*/
create table XLSYSSH.GOLF_TEAMER
(
   TEAM_ID              varchar(32) not null,
   MEMBERNAME           varchar(64),
   EMAIL                varchar(512),
   COUNTRY              varchar(64),
   PHOTO                longblob,
   MEM_TYPE             varchar(256),
   T_DATE               datetime,
   STATUS               varchar(8),
   primary key (TEAM_ID)
);

/*==============================================================*/
/* Table: GOLF_TOURNAMENT                                       */
/*==============================================================*/
create table XLSYSSH.GOLF_TOURNAMENT
(
   NAME                 varchar(16) not null,
   PICTURE              longblob,
   primary key (NAME)
);

/*==============================================================*/
/* Table: GOLF_TRANSPORT                                        */
/*==============================================================*/
create table XLSYSSH.GOLF_TRANSPORT
(
   TRANSPORT_ID         varchar(32) not null,
   COUNTRY              varchar(16),
   VENUE                varchar(128),
   EVENT                varchar(256),
   PK_LOCATION          varchar(256),
   PK_DATE              datetime,
   PK_TIME              varchar(64),
   AR_LOCATION          varchar(256),
   TR_NO                varchar(32),
   DE_LOCATION          varchar(256),
   DE_DATE              datetime,
   DE_TIME              varchar(64),
   DS_LOCATION          varchar(256),
   DS_DATE              datetime,
   DS_TIME              varchar(64),
   CO_NO                varchar(32),
   AR_DATE              datetime,
   AR_TIME              varchar(64),
   primary key (TRANSPORT_ID)
);

/*==============================================================*/
/* Table: GOLF_VIDEOS                                           */
/*==============================================================*/
create table XLSYSSH.GOLF_VIDEOS
(
   ID                   numeric(8,0) not null,
   NAME                 varchar(32),
   "LABEL"              varchar(32),
   VIEDO                longblob,
   LAST_LABEL           varchar(32),
   primary key (ID)
);

/*==============================================================*/
/* Table: SCORE                                                 */
/*==============================================================*/
create table XLSYSSH.SCORE
(
   ID                   varchar(32) not null,
   NAME                 varchar(256),
   GENDER               varchar(16),
   SCORE                numeric(8,0),
   primary key (ID)
);

/*==============================================================*/
/* Table: TEST                                                  */
/*==============================================================*/
create table XLSYSSH.TEST
(
   ID                   varchar(32) not null,
   NAME                 varchar(256),
   GENDER               varchar(8),
   SCORE                numeric(8,0),
   primary key (ID)
);

/*==============================================================*/
/* Table: TESTSCORE                                             */
/*==============================================================*/
create table XLSYSSH.TESTSCORE
(
   ID                   varchar(36),
   NAME                 varchar(258)
);

alter table XLSYSSH.TESTSCORE comment '成绩表';

/*==============================================================*/
/* Table: XLEM_ATOMICUNIT                                       */
/*==============================================================*/
create table XLSYSSH.XLEM_ATOMICUNIT
(
   AUNITID              varchar(32) not null,
   NAME                 varchar(256),
   primary key (AUNITID)
);

alter table XLSYSSH.XLEM_ATOMICUNIT comment '不可拆分的单位定义表';

/*==============================================================*/
/* Table: XLEM_BUYER                                            */
/*==============================================================*/
create table XLSYSSH.XLEM_BUYER
(
   BUYERID              varchar(256) not null,
   NAME                 varchar(256),
   USERID               varchar(256),
   LEVELID              varchar(256),
   EXPERIENCE           numeric(18,6),
   primary key (BUYERID)
);

alter table XLSYSSH.XLEM_BUYER comment '电子商务买家表, 主要记录买家相关信息';

/*==============================================================*/
/* Table: XLEM_ITEM                                             */
/*==============================================================*/
create table XLSYSSH.XLEM_ITEM
(
   ITEMID               varchar(32) not null,
   SELLERID             varchar(256),
   NAME                 varchar(256),
   DESCRIPTION          varchar(4000),
   primary key (ITEMID)
);

alter table XLSYSSH.XLEM_ITEM comment '电子商务商品表.
一个商品对应多个SKU';

/*==============================================================*/
/* Table: XLEM_ITEMSKU                                          */
/*==============================================================*/
create table XLSYSSH.XLEM_ITEMSKU
(
   ITEMID               varchar(32) not null,
   SKU                  varchar(32) not null,
   primary key (ITEMID, SKU)
);

alter table XLSYSSH.XLEM_ITEMSKU comment '商品对应的SKU';

/*==============================================================*/
/* Table: XLEM_PREPSYNONYM                                      */
/*==============================================================*/
create table XLSYSSH.XLEM_PREPSYNONYM
(
   MERGEHASH            numeric(8,0) not null,
   SRCWORD              varchar(32) not null,
   SYNWORD              varchar(32) not null,
   HEAT                 numeric(8,0),
   primary key (MERGEHASH)
);

alter table XLSYSSH.XLEM_PREPSYNONYM comment '预备同义词库. 该库中的同义词热度累计到达一定数量后, 则会转移到同义词库中';

/*==============================================================*/
/* Table: XLEM_SEARCHKEYWORD                                    */
/*==============================================================*/
create table XLSYSSH.XLEM_SEARCHKEYWORD
(
   KEYWORD              varchar(64) not null,
   HEAT                 numeric(8,0) not null,
   primary key (KEYWORD)
);

alter table XLSYSSH.XLEM_SEARCHKEYWORD comment '查询关键字热度表, 此表中存的都是独立的不需要进行拆分的查询关键字信息';

/*==============================================================*/
/* Table: XLEM_SEARCHTEXT                                       */
/*==============================================================*/
create table XLSYSSH.XLEM_SEARCHTEXT
(
   SEARCHTEXT           varchar(1000) not null,
   HEAT                 numeric(8,0) not null,
   primary key (SEARCHTEXT)
);

alter table XLSYSSH.XLEM_SEARCHTEXT comment '查询热度表. 此表保存了所有查询字符串以及对应的热度';

/*==============================================================*/
/* Table: XLEM_SELLER                                           */
/*==============================================================*/
create table XLSYSSH.XLEM_SELLER
(
   SELLERID             varchar(256) not null,
   NAME                 varchar(256),
   USERID               varchar(256),
   LEVELID              varchar(256),
   EXPERIENCE           numeric(18,6),
   primary key (SELLERID)
);

alter table XLSYSSH.XLEM_SELLER comment '电子商务卖家表, 主要记录卖家相关信息';

/*==============================================================*/
/* Table: XLEM_SKU                                              */
/*==============================================================*/
create table XLSYSSH.XLEM_SKU
(
   SKU                  varchar(32) not null,
   SPU                  varchar(32) not null,
   AUNITID              varchar(32) not null,
   NAME                 varchar(256),
   DESCRIPTION          varchar(4000),
   primary key (SKU)
);

alter table XLSYSSH.XLEM_SKU comment '电子商务SKU.
库存量单位, 最小单位, 不可分割
一个SKU对应一个SPU.
一';

/*==============================================================*/
/* Table: XLEM_SPU                                              */
/*==============================================================*/
create table XLSYSSH.XLEM_SPU
(
   SPU                  varchar(32) not null,
   CATEGORYID           varchar(256),
   NAME                 varchar(256),
   DESCRIPTION          varchar(4000),
   primary key (SPU)
);

alter table XLSYSSH.XLEM_SPU comment '电子商务SPU表.一个SPU对应多个SKU, 一个SKU只对应一个SPU';

/*==============================================================*/
/* Table: XLEM_SPUCATEGORY                                      */
/*==============================================================*/
create table XLSYSSH.XLEM_SPUCATEGORY
(
   CATEGORYID           varchar(256) not null,
   NAME                 varchar(256),
   primary key (CATEGORYID)
);

alter table XLSYSSH.XLEM_SPUCATEGORY comment 'SPU分类';

/*==============================================================*/
/* Table: XLEM_SPUINDEX                                         */
/*==============================================================*/
create table XLSYSSH.XLEM_SPUINDEX
(
   FILENAME             varchar(256) not null,
   IDX                  numeric(8,0) not null,
   CONTENT              longblob,
   primary key (FILENAME, IDX)
);

alter table XLSYSSH.XLEM_SPUINDEX comment '电子商务SPU索引';

/*==============================================================*/
/* Table: XLEM_STOCK                                            */
/*==============================================================*/
create table XLSYSSH.XLEM_STOCK
(
   STKID                numeric(16,0) not null,
   SKU                  varchar(32) not null,
   QUANTITY             numeric(18,6) not null,
   DIRECTION            numeric(2,0) not null,
   AUNITPRICE           numeric(18,6) not null,
   primary key (STKID)
);

alter table XLSYSSH.XLEM_STOCK comment '电子商务库存表';

/*==============================================================*/
/* Table: XLEM_STOCKHISTORY                                     */
/*==============================================================*/
create table XLSYSSH.XLEM_STOCKHISTORY
(
   STKID                numeric(16,0) not null,
   SKU                  varchar(32),
   QUANTITY             numeric(18,6),
   DIRECTION            numeric(2,0),
   AUNITPRICE           numeric(18,6),
   primary key (STKID)
);

alter table XLSYSSH.XLEM_STOCKHISTORY comment '库存历史记录表';

/*==============================================================*/
/* Table: XLEM_SYNONYM                                          */
/*==============================================================*/
create table XLSYSSH.XLEM_SYNONYM
(
   MERGEHASH            numeric(8,0) not null comment '原词和同义词经过排序连接后的hash值, 防止重复添加同义词',
   SRCWORD              varchar(32) not null,
   SYNWORD              varchar(32) not null,
   HEAT                 numeric(8,0) comment '该同义词的使用热度',
   primary key (MERGEHASH)
);

alter table XLSYSSH.XLEM_SYNONYM comment '同义词表';

/*==============================================================*/
/* Table: XLEM_UNIT                                             */
/*==============================================================*/
create table XLSYSSH.XLEM_UNIT
(
   UNITID               varchar(32) not null,
   AUNITID              varchar(32) not null,
   NAME                 varchar(256),
   EXCHANGERATE         numeric(18,6) not null comment '单位对原子单位的兑换率',
   primary key (UNITID)
);

alter table XLSYSSH.XLEM_UNIT comment '单位表';

/*==============================================================*/
/* Table: XLEM_USER                                             */
/*==============================================================*/
create table XLSYSSH.XLEM_USER
(
   USERID               varchar(256) not null,
   NAME                 varchar(256),
   primary key (USERID)
);

alter table XLSYSSH.XLEM_USER comment '电子商城用户表, 主要记录用户基本资料';

/*==============================================================*/
/* Table: XLEM_USERLEVEL                                        */
/*==============================================================*/
create table XLSYSSH.XLEM_USERLEVEL
(
   LEVELID              varchar(256) not null,
   NAME                 varchar(256),
   NEXTLEVEL            varchar(256),
   EXPREQUIRE           numeric(18,6),
   primary key (LEVELID)
);

alter table XLSYSSH.XLEM_USERLEVEL comment '等级定义表';

/*==============================================================*/
/* Table: XLFIN_ACCOUNT                                         */
/*==============================================================*/
create table XLSYSSH.XLFIN_ACCOUNT
(
   ACCID                varchar(256) not null,
   NAME                 varchar(1024),
   ADC                  numeric(2,0),
   VDC                  numeric(2,0),
   ACCTYPE              numeric(2,0),
   FORBANKSTMT          numeric(2,0),
   primary key (ACCID)
);

alter table XLSYSSH.XLFIN_ACCOUNT comment '科目表';

/*==============================================================*/
/* Table: XLFIN_ACCOUNTCONDITION                                */
/*==============================================================*/
create table XLSYSSH.XLFIN_ACCOUNTCONDITION
(
   KDEPTID              varchar(256) not null,
   YEAR                 numeric(8,0) not null,
   MONTH                numeric(2,0) not null,
   "CONDITION"          varchar(256) comment '400:已记账;800:已结账',
   primary key (KDEPTID, YEAR, MONTH)
);

alter table XLSYSSH.XLFIN_ACCOUNTCONDITION comment '记账状态记录表';

/*==============================================================*/
/* Table: XLFIN_ACCOUNTINGITEM                                  */
/*==============================================================*/
create table XLSYSSH.XLFIN_ACCOUNTINGITEM
(
   VDCOL                varchar(256) not null comment '凭证明细字段',
   KDEPTIDS             varchar(4000),
   ACCIDS               varchar(4000),
   NASM                 numeric(2,0) comment 'Non accounting statistics method
            非核算项的统计方式
            1:sum;2:max;3:min;4:avg',
   DBCOL                varchar(64) comment '当vdc=1时统计放入balance表的字段',
   CBCOL                varchar(64) comment '当vdc=-1时统计放入balance的字段',
   BCOL                 varchar(64) comment '忽略vdc时统计放入balance表的字段',
   DVKDEPTIDS           varchar(4000) comment '使用该字段进行往来核销的记账部门',
   DVACCIDS             varchar(4000) comment '使用该字段进行往来核销的科目',
   primary key (VDCOL)
);

alter table XLSYSSH.XLFIN_ACCOUNTINGITEM comment '核算项设置';

/*==============================================================*/
/* Table: XLFIN_BALANCE                                         */
/*==============================================================*/
create table XLSYSSH.XLFIN_BALANCE
(
   BALANCEID            varchar(256) not null,
   YEAR                 numeric(8,0),
   MONTH                numeric(2,0),
   KDEPTID              varchar(256),
   FCRCID               varchar(256),
   ACCID                varchar(256),
   DFCA                 numeric(18,6),
   CFCA                 numeric(18,6),
   FCB                  numeric(18,6),
   DSCA                 numeric(18,6),
   CSCA                 numeric(18,6),
   SCB                  numeric(18,6),
   DRCA                 numeric(18,6),
   CRCA                 numeric(18,6),
   RCB                  numeric(18,6),
   DUSDA                numeric(18,6),
   CUSDA                numeric(18,6),
   USDB                 numeric(18,6),
   DQUANTITYA           numeric(18,6),
   CQUANTITYA           numeric(18,6),
   QUANTITYB            numeric(18,6),
   primary key (BALANCEID)
);

alter table XLSYSSH.XLFIN_BALANCE comment '余额表';

/*==============================================================*/
/* Table: XLFIN_BALANCEITEM                                     */
/*==============================================================*/
create table XLSYSSH.XLFIN_BALANCEITEM
(
   BCOL                 varchar(64) not null,
   OPERATORMODE         numeric(2,0) comment 'balance column operator mode
            余额表列的运算方式
            1:add;2:max;3:min;4:cover',
   primary key (BCOL)
);

alter table XLSYSSH.XLFIN_BALANCEITEM comment '余额表项的配置';

/*==============================================================*/
/* Table: XLFIN_BANKSTMT                                        */
/*==============================================================*/
create table XLSYSSH.XLFIN_BANKSTMT
(
   BSID                 varchar(256) not null,
   YEAR                 numeric(8,0),
   MONTH                numeric(2,0),
   TRADEDATE            datetime,
   KDEPTID              varchar(256),
   USERID               varchar(256),
   ACCID                varchar(256),
   BDC                  numeric(2,0),
   FCRCID               varchar(256),
   FCRCAMOUNT           numeric(18,6),
   REMARK               varchar(256),
   VID                  varchar(256),
   VIDX                 numeric(8,0),
   BCDATE               datetime comment '对账日期',
   BCTYPE               numeric(2,0) comment '对账类型
            0:手动;1:自动',
   primary key (BSID)
);

alter table XLSYSSH.XLFIN_BANKSTMT comment '银行对账单表';

/*==============================================================*/
/* Table: XLFIN_BANKSTMTBALANCE                                 */
/*==============================================================*/
create table XLSYSSH.XLFIN_BANKSTMTBALANCE
(
   BSBID                varchar(256) not null,
   KDEPTID              varchar(256),
   FCRCID               varchar(256),
   ACCID                varchar(256),
   YEAR                 numeric(8,0),
   MONTH                numeric(2,0),
   FCB                  numeric(18,6),
   primary key (BSBID)
);

alter table XLSYSSH.XLFIN_BANKSTMTBALANCE comment '银行对账单余额表';

/*==============================================================*/
/* Table: XLFIN_BANKSTMTTEMPLET                                 */
/*==============================================================*/
create table XLSYSSH.XLFIN_BANKSTMTTEMPLET
(
   BSTLID               varchar(256) not null,
   NAME                 varchar(64),
   JAVALISTENER         varchar(4000),
   JSLISTENER           longblob,
   primary key (BSTLID)
);

alter table XLSYSSH.XLFIN_BANKSTMTTEMPLET comment '银行对账单模板主表';

/*==============================================================*/
/* Table: XLFIN_BSTLDT                                          */
/*==============================================================*/
create table XLSYSSH.XLFIN_BSTLDT
(
   BSTLID               varchar(256) not null,
   IDX                  numeric(8,0) not null,
   TEMPLETCOL           varchar(256),
   BSCOL                varchar(256),
   primary key (BSTLID, IDX)
);

alter table XLSYSSH.XLFIN_BSTLDT comment '银行对账单模板明细';

/*==============================================================*/
/* Table: XLFIN_CURRENCY                                        */
/*==============================================================*/
create table XLSYSSH.XLFIN_CURRENCY
(
   CRCID                varchar(256) not null,
   NAME                 varchar(128),
   CRCCODE              varchar(8),
   primary key (CRCID)
);

alter table XLSYSSH.XLFIN_CURRENCY comment '币种表';

/*==============================================================*/
/* Table: XLFIN_EXCHANGERATE                                    */
/*==============================================================*/
create table XLSYSSH.XLFIN_EXCHANGERATE
(
   ERID                 varchar(256) not null,
   CRCID                varchar(256),
   BUYINGRATE           numeric(18,6),
   CASHBUYINGRATE       numeric(18,6),
   SELLINGRATE          numeric(18,6),
   CASHSELLINGRATE      numeric(18,6),
   MIDDLERATE           numeric(18,6),
   PUBTIME              datetime,
   primary key (ERID)
);

alter table XLSYSSH.XLFIN_EXCHANGERATE comment '汇率表
http://www.boc.cn/sourcedb/whpj/enindex.html';

/*==============================================================*/
/* Table: XLFIN_KARACCDT                                        */
/*==============================================================*/
create table XLSYSSH.XLFIN_KARACCDT
(
   KARID                varchar(256) not null,
   IDX                  numeric(8,0) not null,
   ACCIDS               varchar(4000),
   SHOWCOLUMNS          varchar(4000),
   primary key (KARID, IDX)
);

alter table XLSYSSH.XLFIN_KARACCDT comment '记账部门科目关系明细配置';

/*==============================================================*/
/* Table: XLFIN_KARDT                                           */
/*==============================================================*/
create table XLSYSSH.XLFIN_KARDT
(
   KARID                varchar(256) not null,
   VDTECAID             varchar(256) not null,
   NAME                 varchar(64),
   primary key (KARID, VDTECAID)
);

alter table XLSYSSH.XLFIN_KARDT comment '记账部门对应科目所拥有的字段关系表';

/*==============================================================*/
/* Table: XLFIN_KDEPTACCREALTION                                */
/*==============================================================*/
create table XLSYSSH.XLFIN_KDEPTACCREALTION
(
   KARID                varchar(256) not null,
   NAME                 varchar(64),
   ACCIDS               varchar(4000),
   primary key (KARID)
);

alter table XLSYSSH.XLFIN_KDEPTACCREALTION comment '科目关系定义主表';

/*==============================================================*/
/* Table: XLFIN_KEEPDEPT                                        */
/*==============================================================*/
create table XLSYSSH.XLFIN_KEEPDEPT
(
   KDEPTID              varchar(256) not null,
   NAME                 varchar(64),
   STANDARDCRCID        varchar(256),
   REPORTCRCID          varchar(256),
   USEDKARID            varchar(256),
   NOCARRYOVERKARID     varchar(256),
   COCKARID             varchar(256),
   NEEDDCEQUAL          numeric(2,0),
   VDATEMODE            numeric(2,0) comment '凭证日期模式
            0:与当前日期同月时使用当前日期
            1:与当前日期同月时使用上一次凭证日期',
   TRANSFERMODE         numeric(2,0) comment '转账模式
            0:转入时合并
            1:转入时不合并',
   BEGINVDATE           datetime comment '期初日期，该记账部门的凭证日期只能大于等于该日期',
   KDEPTTYPE            numeric(2,0) comment '记账部门类型, 0:记账部门下属;1:记账部门节点
            只有为1时该条记录才是系统使用的真正的记账部门',
   BEGINBSDATE          datetime,
   primary key (KDEPTID)
);

alter table XLSYSSH.XLFIN_KEEPDEPT comment '记账部门';

/*==============================================================*/
/* Table: XLFIN_REPORTDATA                                      */
/*==============================================================*/
create table XLSYSSH.XLFIN_REPORTDATA
(
   RDID                 varchar(256) not null,
   RFID                 varchar(256) not null,
   RDEPTID              varchar(256) not null,
   TIMEPERIOD           numeric(2,0),
   BEGINDATE            datetime not null,
   ENDDATE              datetime not null,
   primary key (RDID)
);

alter table XLSYSSH.XLFIN_REPORTDATA comment '报表数据';

/*==============================================================*/
/* Index: UN_RRBE                                               */
/*==============================================================*/
create unique index UN_RRBE on XLSYSSH.XLFIN_REPORTDATA
(
   RFID,
   RDEPTID,
   BEGINDATE,
   ENDDATE
);

/*==============================================================*/
/* Table: XLFIN_REPORTDATADETAIL                                */
/*==============================================================*/
create table XLSYSSH.XLFIN_REPORTDATADETAIL
(
   RDID                 varchar(256) not null,
   IDX                  numeric(2,0) not null,
   RFROWID              varchar(256) not null,
   RFCOLID              varchar(256) not null,
   VALUE                varchar(4000),
   primary key (RDID, IDX)
);

alter table XLSYSSH.XLFIN_REPORTDATADETAIL comment '报表数据明细';

/*==============================================================*/
/* Table: XLFIN_REPORTDEPT                                      */
/*==============================================================*/
create table XLSYSSH.XLFIN_REPORTDEPT
(
   RDEPTID              varchar(256) not null,
   NAME                 varchar(256),
   KDEPTIDS             varchar(4000),
   GATHERRDEPTIDS       varchar(4000),
   primary key (RDEPTID)
);

alter table XLSYSSH.XLFIN_REPORTDEPT comment '上报单位定义表';

/*==============================================================*/
/* Table: XLFIN_REPORTFORM                                      */
/*==============================================================*/
create table XLSYSSH.XLFIN_REPORTFORM
(
   RFID                 varchar(256) not null,
   NAME                 varchar(64),
   JAVALISTENER         varchar(4000),
   JSLISTENER           longblob,
   primary key (RFID)
);

alter table XLSYSSH.XLFIN_REPORTFORM comment '报表定义';

/*==============================================================*/
/* Table: XLFIN_REPORTFORMCOL                                   */
/*==============================================================*/
create table XLSYSSH.XLFIN_REPORTFORMCOL
(
   RFID                 varchar(256) not null,
   IDX                  numeric(8,0) not null,
   NAME                 varchar(64),
   RFCOLID              varchar(256) not null,
   DATATYPE             numeric(2,0) not null,
   PARAM                varchar(4000),
   primary key (RFID, IDX)
);

alter table XLSYSSH.XLFIN_REPORTFORMCOL comment '报表列定义';

/*==============================================================*/
/* Table: XLFIN_REPORTFORMFORMULA                               */
/*==============================================================*/
create table XLSYSSH.XLFIN_REPORTFORMFORMULA
(
   RFID                 varchar(256) not null,
   IDX                  numeric(8,0) not null,
   RFROWID              varchar(256) not null,
   RFCOLID              varchar(256) not null,
   FORMULA              longblob,
   primary key (RFID, IDX)
);

alter table XLSYSSH.XLFIN_REPORTFORMFORMULA comment '报表公式定义';

/*==============================================================*/
/* Table: XLFIN_REPORTFORMROW                                   */
/*==============================================================*/
create table XLSYSSH.XLFIN_REPORTFORMROW
(
   RFID                 varchar(256) not null,
   IDX                  numeric(8,0) not null,
   NAME                 varchar(64),
   RFROWID              varchar(256) not null,
   primary key (RFID, IDX)
);

alter table XLSYSSH.XLFIN_REPORTFORMROW comment '报表行定义';

/*==============================================================*/
/* Table: XLFIN_VDTEXTRA                                        */
/*==============================================================*/
create table XLSYSSH.XLFIN_VDTEXTRA
(
   VOUCHERID            varchar(256) not null,
   IDX                  numeric(8,0) not null,
   EXTRACOL             varchar(256) not null,
   COLVALUE             varchar(4000),
   primary key (VOUCHERID, IDX, EXTRACOL)
);

alter table XLSYSSH.XLFIN_VDTEXTRA comment '凭证明细附加表';

/*==============================================================*/
/* Table: XLFIN_VDTEXTRACOL                                     */
/*==============================================================*/
create table XLSYSSH.XLFIN_VDTEXTRACOL
(
   EXTRACOL             varchar(256) not null,
   DATATYPE             numeric(2,0) not null,
   NAME                 varchar(64),
   primary key (EXTRACOL)
);

alter table XLSYSSH.XLFIN_VDTEXTRACOL comment '凭证明细可附加字段定义';

/*==============================================================*/
/* Table: XLFIN_VDTEXTRACOLATTR                                 */
/*==============================================================*/
create table XLSYSSH.XLFIN_VDTEXTRACOLATTR
(
   VDTECAID             varchar(256) not null,
   EXTRACOL             varchar(256) not null,
   NAME                 varchar(64),
   TYPE                 numeric(2,0),
   PARAM                varchar(4000),
   SUPPORTVALUE         varchar(4000),
   primary key (VDTECAID)
);

alter table XLSYSSH.XLFIN_VDTEXTRACOLATTR comment '凭证明细附加字段属性定义';

/*==============================================================*/
/* Table: XLFIN_VOUCHER                                         */
/*==============================================================*/
create table XLSYSSH.XLFIN_VOUCHER
(
   VOUCHERID            varchar(256) not null,
   FLOWID               varchar(256),
   CREATER              varchar(256),
   CREATIONDATE         datetime,
   MODIFYDATE           datetime,
   "CONDITION"          varchar(256),
   KDEPTID              varchar(256),
   ID                   varchar(256),
   VNO                  numeric(8,0),
   YEAR                 numeric(8,0),
   MONTH                numeric(2,0),
   VDATE                datetime,
   ATTACHNO             numeric(2,0),
   ACCOUNTER            varchar(256),
   CREATIONMODE         numeric(2,0) not null comment '凭证生成模式
            0:手动生成;90:年初结转的未达账数据;91:年初结转的往来未核销数据;1~89:业务各接口数据;90~99:财务自动生成数据',
   primary key (VOUCHERID)
);

alter table XLSYSSH.XLFIN_VOUCHER comment '凭证表';

/*==============================================================*/
/* Table: XLFIN_VOUCHERDETAIL                                   */
/*==============================================================*/
create table XLSYSSH.XLFIN_VOUCHERDETAIL
(
   VOUCHERID            varchar(256) not null,
   IDX                  numeric(8,0) not null,
   YEAR                 numeric(8,0),
   MONTH                numeric(2,0),
   VNO                  numeric(8,0),
   VDATE                datetime,
   KDEPTID              varchar(256),
   VDC                  numeric(2,0),
   FCRCID               varchar(256),
   ACCID                varchar(256),
   USERID               varchar(256),
   FCRCAMOUNT           numeric(18,6),
   FTOSRATE             numeric(18,6),
   FTORRATE             numeric(18,6),
   FTOUSDRATE           numeric(18,6),
   QUANTITY             numeric(18,6),
   REMARK               varchar(256),
   BSID                 varchar(256),
   BCDATE               datetime,
   BCTYPE               numeric(2,0),
   primary key (VOUCHERID, IDX)
);

alter table XLSYSSH.XLFIN_VOUCHERDETAIL comment '凭证明细';

/*==============================================================*/
/* Table: XLFIN_VOUCHERTEMPLET                                  */
/*==============================================================*/
create table XLSYSSH.XLFIN_VOUCHERTEMPLET
(
   VTLID                varchar(256) not null,
   NAME                 varchar(64),
   JAVALISTENER         varchar(4000),
   JSLISTENER           longblob,
   QUERYSQL             varchar(4000),
   CREATIONMODE         numeric(2,0) not null,
   primary key (VTLID)
);

alter table XLSYSSH.XLFIN_VOUCHERTEMPLET comment '凭证模板定义主表';

/*==============================================================*/
/* Table: XLFIN_VTEMPLETDT                                      */
/*==============================================================*/
create table XLSYSSH.XLFIN_VTEMPLETDT
(
   VTLID                varchar(256) not null,
   IDX                  numeric(8,0) not null,
   NAME                 varchar(64),
   VROWID               varchar(256),
   REMARK               varchar(256),
   VDC                  numeric(2,0),
   ACCID                varchar(256),
   QUERYSQL             varchar(4000),
   FORMULA              longblob,
   primary key (IDX, VTLID)
);

alter table XLSYSSH.XLFIN_VTEMPLETDT comment '凭证模板明细配置';

/*==============================================================*/
/* Table: XLFIN_VTLDTCOL                                        */
/*==============================================================*/
create table XLSYSSH.XLFIN_VTLDTCOL
(
   VTLID                varchar(256) not null,
   VTLDTIDX             numeric(8,0) not null,
   IDX                  numeric(8,0) not null,
   MDSCOLNAME           varchar(256),
   DTDSCOLNAME          varchar(256),
   VDTCOLNAME           varchar(256),
   primary key (VTLID, VTLDTIDX, IDX)
);

alter table XLSYSSH.XLFIN_VTLDTCOL comment '凭证模板明细字段配置';

/*==============================================================*/
/* Table: XLSYS_ATTACHMENT                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_ATTACHMENT
(
   ATTACHID             numeric(8,0) not null,
   NAME                 varchar(256),
   ATTACHDATA           longblob,
   primary key (ATTACHID)
);

/*==============================================================*/
/* Table: XLSYS_AUTHORISEDRIGHT                                 */
/*==============================================================*/
create table XLSYSSH.XLSYS_AUTHORISEDRIGHT
(
   ARID                 varchar(256) not null,
   ARDTIDX              numeric(8,0) not null,
   IDX                  numeric(8,0) not null,
   RIGHTTYPE            numeric(2,0),
   RIGHTVALUE           varchar(256),
   primary key (ARID, ARDTIDX, IDX)
);

alter table XLSYSSH.XLSYS_AUTHORISEDRIGHT comment '被授权权限';

/*==============================================================*/
/* Table: XLSYS_AUTHORIZE                                       */
/*==============================================================*/
create table XLSYSSH.XLSYS_AUTHORIZE
(
   ARID                 varchar(256) not null,
   ARNO                 varchar(256),
   FLOWID               varchar(256),
   ID                   varchar(256),
   CREATER              varchar(256),
   CREATIONDATE         datetime,
   MODIFYDATE           datetime,
   "CONDITION"          varchar(256),
   AUTHORISEDID         varchar(256) comment '授权身份',
   BEGINDATE            datetime,
   ENDDATE              datetime,
   REMARK               varchar(4000),
   primary key (ARID)
);

alter table XLSYSSH.XLSYS_AUTHORIZE comment '授权主表';

/*==============================================================*/
/* Table: XLSYS_AUTHORIZEDETAIL                                 */
/*==============================================================*/
create table XLSYSSH.XLSYS_AUTHORIZEDETAIL
(
   ARID                 varchar(256) not null,
   IDX                  numeric(8,0) not null,
   ARFLOWID             varchar(256) comment '授权流程',
   ARCONDITION          varchar(256) comment '授权状态',
   BEAUTHORIZEDID       varchar(256) comment '被授权身份',
   primary key (ARID, IDX)
);

alter table XLSYSSH.XLSYS_AUTHORIZEDETAIL comment '授权明细表';

/*==============================================================*/
/* Index: UN_AD_FCI                                             */
/*==============================================================*/
create unique index UN_AD_FCI on XLSYSSH.XLSYS_AUTHORIZEDETAIL
(
   ARFLOWID,
   ARCONDITION,
   BEAUTHORIZEDID
);

/*==============================================================*/
/* Table: XLSYS_BASEBUSI                                        */
/*==============================================================*/
create table XLSYSSH.XLSYS_BASEBUSI
(
   BUSIID               varchar(256) not null,
   BUSINO               varchar(256),
   NAME                 varchar(64),
   FLOWID               varchar(256) not null,
   ID                   varchar(256),
   CREATER              varchar(256),
   CREATIONDATE         datetime not null,
   MODIFYDATE           datetime not null,
   "CONDITION"          varchar(256) not null,
   primary key (BUSIID)
);

/*==============================================================*/
/* Table: XLSYS_CODEALLOC                                       */
/*==============================================================*/
create table XLSYSSH.XLSYS_CODEALLOC
(
   CAID                 varchar(256) not null,
   NAME                 varchar(256),
   CLIENTJAVASCRIPT     longblob,
   CLIENTJAVAMETHOD     varchar(4000),
   SERVERJAVASCRIPT     longblob,
   SERVERJAVAMETHOD     varchar(4000),
   primary key (CAID)
);

alter table XLSYSSH.XLSYS_CODEALLOC comment 'define alloc code method
select ''insert into xlsys_co';

/*==============================================================*/
/* Table: XLSYS_DEPARTMENT                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_DEPARTMENT
(
   DEPTID               varchar(256) not null,
   NAME                 varchar(64) not null,
   primary key (DEPTID)
);

alter table XLSYSSH.XLSYS_DEPARTMENT comment 'Department table';

/*==============================================================*/
/* Table: XLSYS_DICT                                            */
/*==============================================================*/
create table XLSYSSH.XLSYS_DICT
(
   DICTID               varchar(256) not null,
   NAME                 varchar(256),
   GOLF                 varchar(8),
   primary key (DICTID)
);

alter table XLSYSSH.XLSYS_DICT comment 'The dictionary of xlsys';

/*==============================================================*/
/* Table: XLSYS_DICTDETAIL                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_DICTDETAIL
(
   DICTID               varchar(256) not null,
   CODE                 varchar(64) not null,
   NAME                 varchar(256),
   GOLF                 varchar(8),
   primary key (DICTID, CODE)
);

/*==============================================================*/
/* Table: XLSYS_EMAILTEMPLATE                                   */
/*==============================================================*/
create table XLSYSSH.XLSYS_EMAILTEMPLATE
(
   ETID                 varchar(256) not null,
   NAME                 varchar(256),
   TEMPLATE             longblob,
   JAVALISTENER         varchar(4000),
   JSLISTENER           longblob,
   primary key (ETID)
);

alter table XLSYSSH.XLSYS_EMAILTEMPLATE comment 'Email模板定义';

/*==============================================================*/
/* Table: XLSYS_EXTRACMD                                        */
/*==============================================================*/
create table XLSYSSH.XLSYS_EXTRACMD
(
   EXTRACMD             varchar(256) not null,
   NAME                 varchar(64),
   DISPATCHPATH         varchar(256),
   JAVALISTENER         varchar(4000),
   JSLISTENER           longblob,
   primary key (EXTRACMD)
);

/*==============================================================*/
/* Table: XLSYS_EXTTABLEINFO                                    */
/*==============================================================*/
create table XLSYSSH.XLSYS_EXTTABLEINFO
(
   TABLEID              numeric(8,0) not null,
   NAME                 varchar(64),
   TABLENAME            varchar(256),
   primary key (TABLEID)
);

alter table XLSYSSH.XLSYS_EXTTABLEINFO comment 'Exter table information';

/*==============================================================*/
/* Table: XLSYS_EXTTABLEINFODETAIL                              */
/*==============================================================*/
create table XLSYSSH.XLSYS_EXTTABLEINFODETAIL
(
   TABLEID              numeric(8,0) not null,
   IDX                  numeric(8,0) not null,
   COLNAME              varchar(64),
   NAME                 varchar(64),
   PRIMARYKEY           numeric(2,0),
   NULLABLE             numeric(2,0),
   primary key (TABLEID, IDX)
);

alter table XLSYSSH.XLSYS_EXTTABLEINFODETAIL comment 'The detail of exter table information';

/*==============================================================*/
/* Table: XLSYS_FLOW                                            */
/*==============================================================*/
create table XLSYSSH.XLSYS_FLOW
(
   FLOWID               varchar(256) not null,
   NAME                 varchar(64),
   LISTPARTID           varchar(256),
   MVIDOFLPART          numeric(8,0),
   MAINPARTID           varchar(256),
   MVIDOFMPART          numeric(8,0),
   MAINTABLE            varchar(64),
   INNERCODECOL         varchar(64),
   OUTTERCODECOL        varchar(64),
   JSLISTENER           longblob,
   JAVALISTENER         varchar(4000),
   VERSIONUPDATE        numeric(2,0) comment '是否允许版本更新',
   CANCOPY              numeric(2,0),
   primary key (FLOWID)
);

alter table XLSYSSH.XLSYS_FLOW comment 'Flow Define
select ''insert into XLSYS_FLOW(FLOWID,NAM';

/*==============================================================*/
/* Table: XLSYS_FLOWCODEALLOC                                   */
/*==============================================================*/
create table XLSYSSH.XLSYS_FLOWCODEALLOC
(
   FCAID                varchar(256) not null,
   FLOWID               varchar(256),
   TABLENAME            varchar(64) not null,
   COLNAME              varchar(64) not null,
   CAID                 varchar(256),
   primary key (FCAID)
);

alter table XLSYSSH.XLSYS_FLOWCODEALLOC comment 'define code creation for flow';

/*==============================================================*/
/* Table: XLSYS_FLOWCONDITION                                   */
/*==============================================================*/
create table XLSYSSH.XLSYS_FLOWCONDITION
(
   FLOWID               varchar(256) not null,
   IDX                  numeric(8,0) not null,
   "CONDITION"          varchar(64) not null,
   NAME                 varchar(256),
   AUDITTYPE            numeric(2,0) comment '审批类型, 0:单审;1:会审;2:组单审;3:组会审;4:投票审
            [单审] : 任意一个人通过即可通过(提交时允许选择审批人)
            [会审] : 所有人通过才可通过(提交时不允许选择审批人)
            [组单审] : 任意一组人通过即可通过, 同一组里的人必须全部通过才算通过(提交时允许选择审批组，不允许选择审批人)
            [组会审] : 所有组的人都通过才可通过, 同一组里的人只有要任意一个人通过就算通过(提交时允许选择审批人，但是每个组都必须选择至少一个审批人)
            [投票审] : 按照一定比例票数通过后即可通过(提交时不允许选择)',
   VOTERATE             numeric(18,6) comment '投票率，当audittype为4:投票审时，此参数有效',
   primary key (FLOWID, IDX)
);

alter table XLSYSSH.XLSYS_FLOWCONDITION comment 'The condition of flow';

/*==============================================================*/
/* Table: XLSYS_FLOWJAVA                                        */
/*==============================================================*/
create table XLSYSSH.XLSYS_FLOWJAVA
(
   FLOWID               varchar(256) not null,
   IDX                  numeric(8,0) not null,
   VIEWID               numeric(8,0),
   JAVALISTENER         varchar(4000),
   primary key (FLOWID, IDX)
);

alter table XLSYSSH.XLSYS_FLOWJAVA comment 'JavaListener for viewers of flow';

/*==============================================================*/
/* Table: XLSYS_FLOWJS                                          */
/*==============================================================*/
create table XLSYSSH.XLSYS_FLOWJS
(
   FLOWID               varchar(256) not null,
   IDX                  numeric(8,0) not null,
   VIEWID               numeric(8,0),
   JSLISTENER           longblob,
   primary key (FLOWID, IDX)
);

alter table XLSYSSH.XLSYS_FLOWJS comment 'JsListener for viewers of flow';

/*==============================================================*/
/* Table: XLSYS_FLOWLOGIC                                       */
/*==============================================================*/
create table XLSYSSH.XLSYS_FLOWLOGIC
(
   FLOWID               varchar(256) not null,
   IDX                  numeric(8,0) not null,
   FROMCONDITION        varchar(64),
   TOCONDITION          varchar(64),
   PASSTYPE             numeric(2,0) comment '0:手动;1:自动提交;2:自动审批;3:自动审批并自动提交',
   REJECTTYPE           numeric(2,0) comment '驳回类型,0:不允许驳回;1:可驳回到上一级;2:可驳回到任意上级;3:只允许驳回到自定义上级',
   CANREJECTTO          varchar(64),
   CLIENTAUTOTRIGGERSUBMIT numeric(2,0) comment '是否允许客户端自动触发提交按钮',
   primary key (FLOWID, IDX)
);

alter table XLSYSSH.XLSYS_FLOWLOGIC comment 'The logic of each flow';

/*==============================================================*/
/* Table: XLSYS_FLOWPART                                        */
/*==============================================================*/
create table XLSYSSH.XLSYS_FLOWPART
(
   FLOWID               varchar(256) not null,
   IDX                  numeric(8,0) not null,
   CLIENTTYPE           varchar(32),
   RIGHTTYPE            numeric(2,0),
   RIGHTVALUE           varchar(256),
   LISTPARTID           varchar(256),
   MVIDOFLPART          numeric(8,0),
   MAINPARTID           varchar(256),
   MVIDOFMPART          numeric(8,0),
   primary key (FLOWID, IDX)
);

/*==============================================================*/
/* Table: XLSYS_FLOWRIGHT                                       */
/*==============================================================*/
create table XLSYSSH.XLSYS_FLOWRIGHT
(
   FLOWID               varchar(256) not null,
   CDTIDX               numeric(8,0) not null,
   IDX                  numeric(8,0) not null,
   BELONGRIGHTTYPE      numeric(2,0) comment '0:identity;1:user;2:department;3:position',
   BELONGRIGHTVALUE     varchar(256),
   RIGHTTYPE            numeric(2,0) comment '0:identity;1:user;2:department;3:position',
   RIGHTVALUE           varchar(256),
   GROUPNM              varchar(256) comment '分组名称,当audittype选用 2:组单审和 3:组会审 时有效，用来标识当前权限为哪个组别所有，分组名称相同的视为同一组',
   primary key (FLOWID, CDTIDX, IDX)
);

alter table XLSYSSH.XLSYS_FLOWRIGHT comment 'The right of each flow condition';

/*==============================================================*/
/* Table: XLSYS_FLOWSUBTABLE                                    */
/*==============================================================*/
create table XLSYSSH.XLSYS_FLOWSUBTABLE
(
   FLOWID               varchar(256) not null,
   TABLENAME            varchar(64) not null,
   RELATIONINNERCOL     varchar(64),
   primary key (FLOWID, TABLENAME)
);

alter table XLSYSSH.XLSYS_FLOWSUBTABLE comment '流程子表配置';

/*==============================================================*/
/* Table: XLSYS_IDENTITY                                        */
/*==============================================================*/
create table XLSYSSH.XLSYS_IDENTITY
(
   ID                   varchar(256) not null,
   NAME                 varchar(64) not null,
   WELCOMEMENUID        varchar(256),
   CLOSEDISABLE         numeric(2,0),
   primary key (ID)
);

alter table XLSYSSH.XLSYS_IDENTITY comment 'Identity for Xue Lang System, it is unique for Xlsys';

/*==============================================================*/
/* Table: XLSYS_IDRELATION                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_IDRELATION
(
   ID                   varchar(256) not null,
   IDX                  numeric(8,0) not null,
   NAME                 varchar(64),
   RIGHTTYPE            numeric(2,0) not null,
   RELATIONVALUE        varchar(32),
   primary key (IDX, ID)
);

alter table XLSYSSH.XLSYS_IDRELATION comment 'The relationship of Identity.';

/*==============================================================*/
/* Index: XLSYS_IDRELATION_TCV                                  */
/*==============================================================*/
create index XLSYS_IDRELATION_TCV on XLSYSSH.XLSYS_IDRELATION
(
   RIGHTTYPE,
   RELATIONVALUE
);

/*==============================================================*/
/* Index: XLSYS_IDRELATION_UQ                                   */
/*==============================================================*/
create unique index XLSYS_IDRELATION_UQ on XLSYSSH.XLSYS_IDRELATION
(
   ID,
   RIGHTTYPE,
   RELATIONVALUE
);

/*==============================================================*/
/* Table: XLSYS_IMAGE                                           */
/*==============================================================*/
create table XLSYSSH.XLSYS_IMAGE
(
   IMAGEID              numeric(8,0) not null,
   NAME                 varchar(256),
   IMAGEDATA            longblob,
   primary key (IMAGEID)
);

alter table XLSYSSH.XLSYS_IMAGE comment '图片库';

/*==============================================================*/
/* Table: XLSYS_IPRESOURCE                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_IPRESOURCE
(
   IPADDRESS            varchar(64) not null,
   IPRESOURCE           longblob,
   primary key (IPADDRESS)
);

alter table XLSYSSH.XLSYS_IPRESOURCE comment 'IP相关资源信息';

/*==============================================================*/
/* Table: XLSYS_JAVACLASS                                       */
/*==============================================================*/
create table XLSYSSH.XLSYS_JAVACLASS
(
   CLASSID              varchar(512) not null,
   NAME                 varchar(64),
   JAVASOURCE           longblob,
   JAVABINARY           longblob,
   primary key (CLASSID)
);

alter table XLSYSSH.XLSYS_JAVACLASS comment 'The Additional Java Class';

/*==============================================================*/
/* Table: XLSYS_MENU                                            */
/*==============================================================*/
create table XLSYSSH.XLSYS_MENU
(
   MENUID               varchar(256) not null,
   NAME                 varchar(64),
   TYPE                 numeric(2,0),
   CMD                  varchar(256),
   PARAM                varchar(4000),
   SHOWNINPHONE         numeric(1,0),
   primary key (MENUID)
);

alter table XLSYSSH.XLSYS_MENU comment 'Define Menu';

/*==============================================================*/
/* Table: XLSYS_MENURIGHT                                       */
/*==============================================================*/
create table XLSYSSH.XLSYS_MENURIGHT
(
   MENUID               varchar(256) not null,
   IDX                  numeric(8,0) not null,
   RIGHTTYPE            numeric(2,0) comment '0:identity;1:user;2:department;3:position',
   RIGHTVALUE           varchar(256),
   primary key (MENUID, IDX)
);

/*==============================================================*/
/* Table: XLSYS_OACATEGORY                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_OACATEGORY
(
   OACID                varchar(256) not null,
   NAME                 varchar(256) not null,
   ICON                 longblob,
   primary key (OACID)
);

alter table XLSYSSH.XLSYS_OACATEGORY comment 'OA类别定义表';

/*==============================================================*/
/* Table: XLSYS_OACATEGORYRIGHT                                 */
/*==============================================================*/
create table XLSYSSH.XLSYS_OACATEGORYRIGHT
(
   OACID                varchar(256) not null,
   IDX                  numeric(8,0) not null,
   RIGHTTYPE            numeric(2,0),
   RIGHTVALUE           varchar(256),
   primary key (OACID, IDX)
);

alter table XLSYSSH.XLSYS_OACATEGORYRIGHT comment 'OA分类权限定义表';

/*==============================================================*/
/* Table: XLSYS_OACMBELONG                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_OACMBELONG
(
   OACID                varchar(256) not null,
   OAMID                varchar(256) not null,
   NAME                 varchar(256),
   primary key (OACID, OAMID)
);

alter table XLSYSSH.XLSYS_OACMBELONG comment 'OA分类和OA模块的所属关系表';

/*==============================================================*/
/* Table: XLSYS_OACMRELATION                                    */
/*==============================================================*/
create table XLSYSSH.XLSYS_OACMRELATION
(
   ID                   varchar(256) not null,
   IDX                  numeric(2,0) not null,
   OACID                varchar(256),
   OAMID                varchar(256),
   NAME                 varchar(256),
   SHEETNAME            varchar(256) comment '分页名称',
   HPERCENT             numeric(4,2) comment '横向占百分比',
   VPIXEL               numeric(8,0) comment '纵向占像素',
   primary key (IDX, ID)
);

alter table XLSYSSH.XLSYS_OACMRELATION comment 'OA分类和OA模块的关系定义表';

/*==============================================================*/
/* Table: XLSYS_OAMODULE                                        */
/*==============================================================*/
create table XLSYSSH.XLSYS_OAMODULE
(
   OAMID                varchar(256) not null,
   NAME                 varchar(256),
   CMD                  varchar(256),
   PARAM                varchar(4000),
   primary key (OAMID)
);

alter table XLSYSSH.XLSYS_OAMODULE comment 'OA模块定义表';

/*==============================================================*/
/* Table: XLSYS_OAMODULEEXTRA                                   */
/*==============================================================*/
create table XLSYSSH.XLSYS_OAMODULEEXTRA
(
   OAMID                varchar(256) not null,
   VIEWID               numeric(8,0) not null,
   EXTCMD               varchar(256),
   EXTPARAM             varchar(4000),
   primary key (OAMID, VIEWID)
);

alter table XLSYSSH.XLSYS_OAMODULEEXTRA comment 'OA模块附加窗口配置';

/*==============================================================*/
/* Table: XLSYS_OAMODULERIGHT                                   */
/*==============================================================*/
create table XLSYSSH.XLSYS_OAMODULERIGHT
(
   OAMID                varchar(256) not null,
   IDX                  numeric(8,0) not null,
   RIGHTTYPE            numeric(2,0),
   RIGHTVALUE           varchar(256),
   primary key (OAMID, IDX)
);

alter table XLSYSSH.XLSYS_OAMODULERIGHT comment 'OA模块权限定义表';

/*==============================================================*/
/* Table: XLSYS_PART                                            */
/*==============================================================*/
create table XLSYSSH.XLSYS_PART
(
   PARTID               varchar(256) not null,
   NAME                 varchar(64) not null,
   PARTTYPE             numeric(2,0) not null comment '0:Node;1:Part',
   primary key (PARTID)
);

alter table XLSYSSH.XLSYS_PART comment 'The Main Table Of Defining Part';

/*==============================================================*/
/* Table: XLSYS_PARTDETAIL                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_PARTDETAIL
(
   PARTID               varchar(256) not null,
   DETAILID             varchar(256) not null,
   NAME                 varchar(64),
   TYPE                 numeric(2,0) comment '0:SashForm;1:TabFolder;2:ExpandBar',
   PARAM                varchar(4000),
   VIEWID               numeric(8,0),
   RELATIONTYPE         numeric(2,0),
   MAINVIEWID           numeric(8,0),
   SOPORDER             numeric(8,0),
   DIYIMPLEMENT         varchar(1000),
   primary key (PARTID, DETAILID)
);

alter table XLSYSSH.XLSYS_PARTDETAIL comment 'The detail of part';

/*==============================================================*/
/* Table: XLSYS_POSITION                                        */
/*==============================================================*/
create table XLSYSSH.XLSYS_POSITION
(
   PSTID                varchar(256) not null,
   NAME                 varchar(64) not null,
   primary key (PSTID)
);

alter table XLSYSSH.XLSYS_POSITION comment 'Position';

/*==============================================================*/
/* Table: XLSYS_PRINT                                           */
/*==============================================================*/
create table XLSYSSH.XLSYS_PRINT
(
   PRINTID              varchar(256) not null,
   NAME                 varchar(256),
   PRINTTYPE            numeric(2,0),
   TEMPLATE             longblob,
   primary key (PRINTID)
);

alter table XLSYSSH.XLSYS_PRINT comment '打印模板定义表';

/*==============================================================*/
/* Table: XLSYS_QUERYPARAMSAVE                                  */
/*==============================================================*/
create table XLSYSSH.XLSYS_QUERYPARAMSAVE
(
   VIEWID               numeric(8,0) not null,
   ID                   varchar(256) not null,
   NAME                 varchar(64) not null,
   PARAMTYPE            numeric(2,0) not null comment '参数类型
            0:界面查询参数
            1:分组参数',
   PARAMSAVE            longblob,
   primary key (VIEWID, ID, NAME, PARAMTYPE)
);

alter table XLSYSSH.XLSYS_QUERYPARAMSAVE comment '查询参数保存表';

/*==============================================================*/
/* Table: XLSYS_RATIFY                                          */
/*==============================================================*/
create table XLSYSSH.XLSYS_RATIFY
(
   RTFID                varchar(256) not null,
   NAME                 varchar(256),
   FROMUSERID           varchar(256),
   FROMFLOWID           varchar(256),
   FROMCDTIDX           numeric(8,0),
   TOFLOWID             varchar(256),
   TOCDTIDX             numeric(8,0),
   INNERCODE            varchar(256),
   VERSION              numeric(8,0),
   RTFRET               numeric(2,0) comment '0:已提交;1:已通过;2:已驳回',
   RTFDATE              datetime,
   primary key (RTFID)
);

alter table XLSYSSH.XLSYS_RATIFY comment 'The situation of ratifing business flow';

/*==============================================================*/
/* Table: XLSYS_RATIFYDETAIL                                    */
/*==============================================================*/
create table XLSYSSH.XLSYS_RATIFYDETAIL
(
   RTFID                varchar(256) not null,
   TOUSERID             varchar(256) not null,
   REPLACEUSERID        varchar(256),
   RTFRET               numeric(2,0) comment '0:已提交;1:已通过;2:已驳回',
   RTFDESC              varchar(4000),
   RTFDATE              datetime,
   GROUPNM              varchar(256),
   primary key (RTFID, TOUSERID)
);

alter table XLSYSSH.XLSYS_RATIFYDETAIL comment 'The detail of ratify condition';

/*==============================================================*/
/* Table: XLSYS_RIGHT                                           */
/*==============================================================*/
create table XLSYSSH.XLSYS_RIGHT
(
   RIGHTTYPE            numeric(2,0) not null,
   NAME                 varchar(64),
   SESSIONKEY           varchar(256) not null,
   RELATIONTABLE        varchar(256) not null,
   RELATIONCOLUMN       varchar(256) not null,
   primary key (RIGHTTYPE)
);

alter table XLSYSSH.XLSYS_RIGHT comment '系统权限定义表';

/*==============================================================*/
/* Index: UN_RIGHT_RC                                           */
/*==============================================================*/
create unique index UN_RIGHT_RC on XLSYSSH.XLSYS_RIGHT
(
   RELATIONCOLUMN
);

/*==============================================================*/
/* Index: UN_RIGHT_SK                                           */
/*==============================================================*/
create unique index UN_RIGHT_SK on XLSYSSH.XLSYS_RIGHT
(
   SESSIONKEY
);

/*==============================================================*/
/* Table: XLSYS_TRANSLATOR                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_TRANSLATOR
(
   TABLENAME            varchar(32) not null,
   DEFAULTNAME          varchar(256) not null,
   LANGUAGE             varchar(32) not null,
   TRANSNAME            varchar(256),
   primary key (TABLENAME, DEFAULTNAME, LANGUAGE)
);

alter table XLSYSSH.XLSYS_TRANSLATOR comment 'Language support';

/*==============================================================*/
/* Table: XLSYS_TRANSPORT                                       */
/*==============================================================*/
create table XLSYSSH.XLSYS_TRANSPORT
(
   TSID                 varchar(256) not null,
   NAME                 varchar(256),
   primary key (TSID)
);

alter table XLSYSSH.XLSYS_TRANSPORT comment '系统传输表, 用来做跨数据库的数据传输';

/*==============================================================*/
/* Table: XLSYS_TRANSPORTDETAIL                                 */
/*==============================================================*/
create table XLSYSSH.XLSYS_TRANSPORTDETAIL
(
   TSID                 varchar(256) not null,
   IDX                  numeric(8,0) not null,
   FROMTABLE            varchar(64),
   TOTABLE              varchar(64),
   FROMSQL              varchar(4000),
   JAVALISTENER         varchar(4000),
   JSLISTENER           longblob,
   BATCHCOUNT           numeric(8,0) comment '批量更新数量',
   CPSMCOL              numeric(2,0) comment '是否拷贝同名字段',
   ACTIVE               numeric(2,0),
   primary key (TSID, IDX)
);

alter table XLSYSSH.XLSYS_TRANSPORTDETAIL comment '数据传输明细定义';

/*==============================================================*/
/* Table: XLSYS_TRANSPORTDTCOLMAP                               */
/*==============================================================*/
create table XLSYSSH.XLSYS_TRANSPORTDTCOLMAP
(
   TSID                 varchar(256) not null,
   TSDTIDX              numeric(8,0) not null,
   IDX                  numeric(8,0) not null,
   FROMCOLUMN           varchar(64),
   TOCOLUMN             varchar(64),
   primary key (TSID, TSDTIDX, IDX)
);

alter table XLSYSSH.XLSYS_TRANSPORTDTCOLMAP comment '数据传输明细字段对照';

/*==============================================================*/
/* Table: XLSYS_TRANSPORTKEY                                    */
/*==============================================================*/
create table XLSYSSH.XLSYS_TRANSPORTKEY
(
   TSKEYID              varchar(32) not null,
   NAME                 varchar(256),
   primary key (TSKEYID)
);

alter table XLSYSSH.XLSYS_TRANSPORTKEY comment '传输数据的关键码定义';

/*==============================================================*/
/* Table: XLSYS_TRANSPORTKEYSYNONYM                             */
/*==============================================================*/
create table XLSYSSH.XLSYS_TRANSPORTKEYSYNONYM
(
   TSKEYID              varchar(32) not null,
   TABLENAME            varchar(64) not null,
   COLUMNNAME           varchar(64) not null,
   primary key (TSKEYID, TABLENAME, COLUMNNAME)
);

alter table XLSYSSH.XLSYS_TRANSPORTKEYSYNONYM comment '传输关键码同义词定义表，主要用来定义哪些表的哪些字段使用该关键码来进行映射';

/*==============================================================*/
/* Table: XLSYS_TRANSPORTMAP                                    */
/*==============================================================*/
create table XLSYSSH.XLSYS_TRANSPORTMAP
(
   TSMAPID              varchar(256) not null,
   TSKEYID              varchar(32),
   FROMDSID             numeric(8,0),
   TODSID               numeric(8,0),
   FROMTABLE            varchar(64),
   TOTABLE              varchar(64),
   FROMCOLUMN           varchar(64),
   TOCOLUMN             varchar(64),
   FROMVALUE            varchar(4000),
   TOVALUE              varchar(4000),
   SYNDATE              datetime,
   BATCHNO              varchar(32),
   REMARK               varchar(4000),
   OTHERUSE1            varchar(256),
   OTHERUSE2            varchar(256),
   OTHERUSE3            varchar(256),
   primary key (TSMAPID)
);

alter table XLSYSSH.XLSYS_TRANSPORTMAP comment '数据传输映射表, 可看做数据传输日志, 以及数据在两个系统中的对照表';

/*==============================================================*/
/* Table: XLSYS_TRANSPORTRUN                                    */
/*==============================================================*/
create table XLSYSSH.XLSYS_TRANSPORTRUN
(
   TSRUNID              varchar(256) not null,
   TSID                 varchar(256),
   FROMDSID             numeric(8,0),
   TODSID               numeric(8,0),
   TOTALTHREADCOUNT     numeric(8,0),
   THREADCOUNT          numeric(8,0),
   DATAPERTHREAD        numeric(8,0),
   primary key (TSRUNID)
);

alter table XLSYSSH.XLSYS_TRANSPORTRUN comment '数据传输运行表';

/*==============================================================*/
/* Table: XLSYS_USER                                            */
/*==============================================================*/
create table XLSYSSH.XLSYS_USER
(
   USERID               varchar(256) not null,
   NAME                 varchar(64) not null,
   PASSWORD             varchar(255),
   primary key (USERID)
);

alter table XLSYSSH.XLSYS_USER comment 'user table';

/*==============================================================*/
/* Table: XLSYS_USEREMAIL                                       */
/*==============================================================*/
create table XLSYSSH.XLSYS_USEREMAIL
(
   USERID               varchar(256) not null,
   IDX                  numeric(8,0) not null,
   EMAIL                varchar(64),
   POP                  varchar(64),
   SMTP                 varchar(64),
   EMAILUSER            varchar(64),
   EMAILPWD             varchar(64),
   REMARK               varchar(4000),
   HEADER               longblob comment 'Email的抬头',
   FOOTER               longblob comment 'Email的签名',
   primary key (USERID, IDX)
);

alter table XLSYSSH.XLSYS_USEREMAIL comment '用户email配置';

/*==============================================================*/
/* Table: XLSYS_VIEW                                            */
/*==============================================================*/
create table XLSYSSH.XLSYS_VIEW
(
   VIEWID               numeric(8,0) not null,
   NAME                 varchar(64),
   VIEWTYPE             numeric(2,0),
   PARAM                varchar(4000),
   RELATIONTYPE         numeric(2,0),
   MAINVIEWID           numeric(8,0),
   JSLISTENER           longblob,
   JAVALISTENER         varchar(1000),
   SELECTBODY           varchar(1000),
   FROMBODY             varchar(1000),
   WHEREBODY            varchar(1000),
   GROUPBYBODY          varchar(1000),
   ORDERBYBODY          varchar(1000),
   WHOLESQL             varchar(1000),
   TREECOLNAME          varchar(64),
   primary key (VIEWID)
);

alter table XLSYSSH.XLSYS_VIEW comment 'Define the composite
select ''insert into XLSYS_VIEW (';

/*==============================================================*/
/* Table: XLSYS_VIEWDETAIL                                      */
/*==============================================================*/
create table XLSYSSH.XLSYS_VIEWDETAIL
(
   VIEWID               numeric(8,0) not null,
   IDX                  numeric(8,0) not null,
   COLNAME              varchar(64),
   NAME                 varchar(64),
   COLGROUP             varchar(64),
   COLGROUPNAME         varchar(64),
   DATATYPE             numeric(2,0),
   TYPE                 numeric(2,0),
   DEFAULTVALUE         varchar(256),
   SUPPORTVALUE         varchar(4000),
   AGGREGATION          numeric(2,0),
   RELATIONCOLNAME      varchar(64),
   SQLEXP               varchar(4000),
   SHOWNINPHONEOVERVIEW numeric(1,0),
   SHOWNINPHONEDETAIL   numeric(1,0),
   primary key (VIEWID, IDX)
);

alter table XLSYSSH.XLSYS_VIEWDETAIL comment 'Detail of view';

/*==============================================================*/
/* Index: UN_VD                                                 */
/*==============================================================*/
create unique index UN_VD on XLSYSSH.XLSYS_VIEWDETAIL
(
   VIEWID,
   COLNAME
);

/*==============================================================*/
/* Table: XLSYS_VIEWDETAILPARAM                                 */
/*==============================================================*/
create table XLSYSSH.XLSYS_VIEWDETAILPARAM
(
   VIEWID               numeric(8,0) not null,
   IDX                  numeric(8,0) not null,
   ATTRNAME             varchar(1000) not null,
   ATTRVALUE            varchar(4000),
   primary key (VIEWID, IDX, ATTRNAME)
);

/*==============================================================*/
/* Table: XLSYS_VIEWQUERYPARAM                                  */
/*==============================================================*/
create table XLSYSSH.XLSYS_VIEWQUERYPARAM
(
   VIEWID               numeric(8,0) not null,
   IDX                  numeric(8,0) not null,
   COLNAME              varchar(64),
   NAME                 varchar(64),
   DATATYPE             numeric(2,0),
   TYPE                 numeric(2,0),
   PARAM                varchar(4000),
   DEFAULTVALUE         varchar(256),
   SUPPORTVALUE         varchar(4000),
   SHOWNINPHONE         numeric(1,0),
   primary key (VIEWID, IDX)
);

alter table XLSYSSH.XLSYS_VIEWQUERYPARAM comment 'Query parameter for view';

/*==============================================================*/
/* Table: XLSYS_WDTCOLUMN                                       */
/*==============================================================*/
create table XLSYSSH.XLSYS_WDTCOLUMN
(
   WIZARDID             varchar(256) not null,
   DTIDX                numeric(8,0) not null,
   IDX                  numeric(8,0) not null,
   COLNAME              varchar(64),
   NAME                 varchar(64),
   FORCEINPUT           numeric(2,0),
   TOOLTIP              varchar(256),
   primary key (WIZARDID, DTIDX, IDX)
);

alter table XLSYSSH.XLSYS_WDTCOLUMN comment '向导页对应字段';

/*==============================================================*/
/* Table: XLSYS_WIZARD                                          */
/*==============================================================*/
create table XLSYSSH.XLSYS_WIZARD
(
   WIZARDID             varchar(256) not null,
   NAME                 varchar(64),
   JAVALISTENER         varchar(4000),
   JSLISTENER           longblob,
   primary key (WIZARDID)
);

alter table XLSYSSH.XLSYS_WIZARD comment '雪狼系统向导主表';

/*==============================================================*/
/* Table: XLSYS_WIZARDDETAIL                                    */
/*==============================================================*/
create table XLSYSSH.XLSYS_WIZARDDETAIL
(
   WIZARDID             varchar(256) not null,
   IDX                  numeric(8,0) not null,
   VIEWID               numeric(8,0) comment '对应视图ID',
   TITLE                varchar(256),
   MESSAGE              varchar(4000),
   NEEDSAVE             numeric(2,0),
   NEXTIDX              numeric(8,0) comment '下一步序号',
   primary key (WIZARDID, IDX)
);

alter table XLSYSSH.XLSYS_WIZARDDETAIL comment '雪狼系统向导明细表';

alter table XLSYSSH.XLEM_BUYER add constraint FK_BR_REFERENCE_U foreign key (USERID)
      references XLSYSSH.XLEM_USER (USERID) on update restrict;

alter table XLSYSSH.XLEM_BUYER add constraint FK_BR_REFERENCE_UL foreign key (LEVELID)
      references XLSYSSH.XLEM_USERLEVEL (LEVELID) on update restrict;

alter table XLSYSSH.XLEM_ITEM add constraint FK_I_REFERENCE_SL foreign key (SELLERID)
      references XLSYSSH.XLEM_SELLER (SELLERID) on update restrict;

alter table XLSYSSH.XLEM_ITEMSKU add constraint FK_ISKU_REFERENCE_I foreign key (ITEMID)
      references XLSYSSH.XLEM_ITEM (ITEMID) on update restrict;

alter table XLSYSSH.XLEM_ITEMSKU add constraint FK_ISKU_REFERENCE_SKU foreign key (SKU)
      references XLSYSSH.XLEM_SKU (SKU) on update restrict;

alter table XLSYSSH.XLEM_SELLER add constraint FK_SL_REFERENCE_U foreign key (USERID)
      references XLSYSSH.XLEM_USER (USERID) on update restrict;

alter table XLSYSSH.XLEM_SELLER add constraint FK_SL_REFERENCE_UL foreign key (LEVELID)
      references XLSYSSH.XLEM_USERLEVEL (LEVELID) on update restrict;

alter table XLSYSSH.XLEM_SKU add constraint FK_SKU_REFERENCE_AUNIT foreign key (AUNITID)
      references XLSYSSH.XLEM_ATOMICUNIT (AUNITID) on update restrict;

alter table XLSYSSH.XLEM_SKU add constraint FK_SKU_REFERENCE_SPU foreign key (SPU)
      references XLSYSSH.XLEM_SPU (SPU) on update restrict;

alter table XLSYSSH.XLEM_SPU add constraint FK_SPU_REFERENCE_SPUC foreign key (CATEGORYID)
      references XLSYSSH.XLEM_SPUCATEGORY (CATEGORYID) on update restrict;

alter table XLSYSSH.XLEM_STOCK add constraint FK_STK_REFERENCE_SKU foreign key (SKU)
      references XLSYSSH.XLEM_SKU (SKU) on update restrict;

alter table XLSYSSH.XLEM_STOCKHISTORY add constraint FK_STKH_REFERENCE_SKU foreign key (SKU)
      references XLSYSSH.XLEM_SKU (SKU) on update restrict;

alter table XLSYSSH.XLEM_UNIT add constraint FK_UNIT_REFERENCE_AUNIT foreign key (AUNITID)
      references XLSYSSH.XLEM_ATOMICUNIT (AUNITID) on update restrict;

alter table XLSYSSH.XLEM_USERLEVEL add constraint FK_UL_REFERENCE_UL foreign key (NEXTLEVEL)
      references XLSYSSH.XLEM_USERLEVEL (LEVELID) on update restrict;

alter table XLSYSSH.XLFIN_ACCOUNTCONDITION add constraint FK_AC_REFERENCE_KD foreign key (KDEPTID)
      references XLSYSSH.XLFIN_KEEPDEPT (KDEPTID) on update restrict;

alter table XLSYSSH.XLFIN_BALANCE add constraint FK_B_REFERENCE_A foreign key (ACCID)
      references XLSYSSH.XLFIN_ACCOUNT (ACCID) on update restrict;

alter table XLSYSSH.XLFIN_BALANCE add constraint FK_B_REFERENCE_C foreign key (FCRCID)
      references XLSYSSH.XLFIN_CURRENCY (CRCID) on update restrict;

alter table XLSYSSH.XLFIN_BALANCE add constraint FK_B_REFERENCE_KD foreign key (KDEPTID)
      references XLSYSSH.XLFIN_KEEPDEPT (KDEPTID) on update restrict;

alter table XLSYSSH.XLFIN_BANKSTMT add constraint FK_BS_REFERENCE_A foreign key (ACCID)
      references XLSYSSH.XLFIN_ACCOUNT (ACCID) on update restrict;

alter table XLSYSSH.XLFIN_BANKSTMT add constraint FK_BS_REFERENCE_C foreign key (FCRCID)
      references XLSYSSH.XLFIN_CURRENCY (CRCID) on update restrict;

alter table XLSYSSH.XLFIN_BANKSTMT add constraint FK_BS_REFERENCE_KD foreign key (KDEPTID)
      references XLSYSSH.XLFIN_KEEPDEPT (KDEPTID) on update restrict;

alter table XLSYSSH.XLFIN_BANKSTMT add constraint FK_BS_REFERENCE_U foreign key (USERID)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLFIN_BANKSTMT add constraint FK_BS_REFERENCE_VD foreign key (VID, VIDX)
      references XLSYSSH.XLFIN_VOUCHERDETAIL (VOUCHERID, IDX) on update restrict;

alter table XLSYSSH.XLFIN_BANKSTMTBALANCE add constraint FK_BSB_REFERENCE_A foreign key (ACCID)
      references XLSYSSH.XLFIN_ACCOUNT (ACCID) on update restrict;

alter table XLSYSSH.XLFIN_BANKSTMTBALANCE add constraint FK_BSB_REFERENCE_C foreign key (FCRCID)
      references XLSYSSH.XLFIN_CURRENCY (CRCID) on update restrict;

alter table XLSYSSH.XLFIN_BANKSTMTBALANCE add constraint FK_BSB_REFERENCE_KD foreign key (KDEPTID)
      references XLSYSSH.XLFIN_KEEPDEPT (KDEPTID) on update restrict;

alter table XLSYSSH.XLFIN_BSTLDT add constraint FK_BSTLDT_REFERENCE_BSTL foreign key (BSTLID)
      references XLSYSSH.XLFIN_BANKSTMTTEMPLET (BSTLID) on update restrict;

alter table XLSYSSH.XLFIN_EXCHANGERATE add constraint FK_ER_REFERENCE_C foreign key (CRCID)
      references XLSYSSH.XLFIN_CURRENCY (CRCID) on update restrict;

alter table XLSYSSH.XLFIN_KARACCDT add constraint FK_KARADT_REFERENCE_KAR foreign key (KARID)
      references XLSYSSH.XLFIN_KDEPTACCREALTION (KARID) on update restrict;

alter table XLSYSSH.XLFIN_KARDT add constraint FK_KARDT_REFERENCE_KAR foreign key (KARID)
      references XLSYSSH.XLFIN_KDEPTACCREALTION (KARID) on update restrict;

alter table XLSYSSH.XLFIN_KARDT add constraint FK_KARDT_REFERENCE_VDTECA foreign key (VDTECAID)
      references XLSYSSH.XLFIN_VDTEXTRACOLATTR (VDTECAID) on update restrict;

alter table XLSYSSH.XLFIN_KEEPDEPT add constraint FK_KD_REFERENCE_C1 foreign key (STANDARDCRCID)
      references XLSYSSH.XLFIN_CURRENCY (CRCID) on update restrict;

alter table XLSYSSH.XLFIN_KEEPDEPT add constraint FK_KD_REFERENCE_C2 foreign key (REPORTCRCID)
      references XLSYSSH.XLFIN_CURRENCY (CRCID) on update restrict;

alter table XLSYSSH.XLFIN_KEEPDEPT add constraint FK_KD_REFERENCE_KDAR1 foreign key (USEDKARID)
      references XLSYSSH.XLFIN_KDEPTACCREALTION (KARID) on update restrict;

alter table XLSYSSH.XLFIN_KEEPDEPT add constraint FK_KD_REFERENCE_KDAR2 foreign key (NOCARRYOVERKARID)
      references XLSYSSH.XLFIN_KDEPTACCREALTION (KARID) on update restrict;

alter table XLSYSSH.XLFIN_KEEPDEPT add constraint FK_KD_REFERENCE_KDAR3 foreign key (COCKARID)
      references XLSYSSH.XLFIN_KDEPTACCREALTION (KARID) on update restrict;

alter table XLSYSSH.XLFIN_REPORTDATA add constraint FK_RD_REFERENCE_RDEPT foreign key (RDEPTID)
      references XLSYSSH.XLFIN_REPORTDEPT (RDEPTID) on update restrict;

alter table XLSYSSH.XLFIN_REPORTDATA add constraint FK_RD_REFERENCE_RF foreign key (RFID)
      references XLSYSSH.XLFIN_REPORTFORM (RFID) on update restrict;

alter table XLSYSSH.XLFIN_REPORTDATADETAIL add constraint FK_RDD_REFERENCE_RD foreign key (RDID)
      references XLSYSSH.XLFIN_REPORTDATA (RDID) on update restrict;

alter table XLSYSSH.XLFIN_REPORTFORMCOL add constraint FK_RFC_REFERENCE_RF foreign key (RFID)
      references XLSYSSH.XLFIN_REPORTFORM (RFID) on update restrict;

alter table XLSYSSH.XLFIN_REPORTFORMFORMULA add constraint FK_RFF_REFERENCE_RF foreign key (RFID)
      references XLSYSSH.XLFIN_REPORTFORM (RFID) on update restrict;

alter table XLSYSSH.XLFIN_REPORTFORMROW add constraint FK_RFR_REFERENCE_RF foreign key (RFID)
      references XLSYSSH.XLFIN_REPORTFORM (RFID) on update restrict;

alter table XLSYSSH.XLFIN_VDTEXTRA add constraint FK_VDTE_REFERENCE_VDT foreign key (VOUCHERID, IDX)
      references XLSYSSH.XLFIN_VOUCHERDETAIL (VOUCHERID, IDX) on update restrict;

alter table XLSYSSH.XLFIN_VDTEXTRA add constraint FK_VDTE_REFERENCE_VEC foreign key (EXTRACOL)
      references XLSYSSH.XLFIN_VDTEXTRACOL (EXTRACOL) on update restrict;

alter table XLSYSSH.XLFIN_VDTEXTRACOLATTR add constraint FK_VDTECA_REFERENCE_VDTEC foreign key (EXTRACOL)
      references XLSYSSH.XLFIN_VDTEXTRACOL (EXTRACOL) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHER add constraint FK_V_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHER add constraint FK_V_REFERENCE_ID foreign key (ID)
      references XLSYSSH.XLSYS_IDENTITY (ID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHER add constraint FK_V_REFERENCE_KD foreign key (KDEPTID)
      references XLSYSSH.XLFIN_KEEPDEPT (KDEPTID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHER add constraint FK_V_REFERENCE_U1 foreign key (CREATER)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHER add constraint FK_V_REFERENCE_U2 foreign key (ACCOUNTER)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHERDETAIL add constraint FK_VDT_REFERENCE_KD foreign key (KDEPTID)
      references XLSYSSH.XLFIN_KEEPDEPT (KDEPTID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHERDETAIL add constraint FK_VDT_REFERENCE_U foreign key (USERID)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHERDETAIL add constraint FK_VD_REFERENCE_A foreign key (ACCID)
      references XLSYSSH.XLFIN_ACCOUNT (ACCID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHERDETAIL add constraint FK_VD_REFERENCE_BS foreign key (BSID)
      references XLSYSSH.XLFIN_BANKSTMT (BSID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHERDETAIL add constraint FK_VD_REFERENCE_C foreign key (FCRCID)
      references XLSYSSH.XLFIN_CURRENCY (CRCID) on update restrict;

alter table XLSYSSH.XLFIN_VOUCHERDETAIL add constraint FK_VOD_REFERENCE_VO foreign key (VOUCHERID)
      references XLSYSSH.XLFIN_VOUCHER (VOUCHERID) on update restrict;

alter table XLSYSSH.XLFIN_VTEMPLETDT add constraint FK_VTLDT_REFERENCE_VTL foreign key (VTLID)
      references XLSYSSH.XLFIN_VOUCHERTEMPLET (VTLID) on update restrict;

alter table XLSYSSH.XLFIN_VTLDTCOL add constraint FK_VTLDTC_REFERENCE_VTLDT foreign key (VTLDTIDX, VTLID)
      references XLSYSSH.XLFIN_VTEMPLETDT (IDX, VTLID) on update restrict;

alter table XLSYSSH.XLSYS_AUTHORISEDRIGHT add constraint FK_AR_REFERENCE_AD foreign key (ARID, ARDTIDX)
      references XLSYSSH.XLSYS_AUTHORIZEDETAIL (ARID, IDX) on update restrict;

alter table XLSYSSH.XLSYS_AUTHORISEDRIGHT add constraint FK_AR_REFERENCE_R foreign key (RIGHTTYPE)
      references XLSYSSH.XLSYS_RIGHT (RIGHTTYPE) on update restrict;

alter table XLSYSSH.XLSYS_AUTHORIZE add constraint FK_A_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLSYS_AUTHORIZE add constraint FK_A_REFERENCE_I1 foreign key (ID)
      references XLSYSSH.XLSYS_IDENTITY (ID) on update restrict;

alter table XLSYSSH.XLSYS_AUTHORIZE add constraint FK_A_REFERENCE_I2 foreign key (AUTHORISEDID)
      references XLSYSSH.XLSYS_IDENTITY (ID) on update restrict;

alter table XLSYSSH.XLSYS_AUTHORIZE add constraint FK_A_REFERENCE_U foreign key (CREATER)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLSYS_AUTHORIZEDETAIL add constraint FK_AD_REFERENCE_A foreign key (ARID)
      references XLSYSSH.XLSYS_AUTHORIZE (ARID) on update restrict;

alter table XLSYSSH.XLSYS_BASEBUSI add constraint FK_BB_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLSYS_BASEBUSI add constraint FK_BB_REFERENCE_I foreign key (ID)
      references XLSYSSH.XLSYS_IDENTITY (ID) on update restrict;

alter table XLSYSSH.XLSYS_BASEBUSI add constraint FK_BB_REFERENCE_U foreign key (CREATER)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLSYS_DICTDETAIL add constraint FK_DD_REFERENCE_D foreign key (DICTID)
      references XLSYSSH.XLSYS_DICT (DICTID) on update restrict;

alter table XLSYSSH.XLSYS_EXTTABLEINFODETAIL add constraint FK_ETI_REFERENCE_ETID foreign key (TABLEID)
      references XLSYSSH.XLSYS_EXTTABLEINFO (TABLEID) on update restrict;

alter table XLSYSSH.XLSYS_FLOW add constraint FK_FL_REFERENCE_P1 foreign key (MAINPARTID)
      references XLSYSSH.XLSYS_PART (PARTID) on update restrict;

alter table XLSYSSH.XLSYS_FLOW add constraint FK_FL_REFERENCE_P2 foreign key (LISTPARTID)
      references XLSYSSH.XLSYS_PART (PARTID) on update restrict;

alter table XLSYSSH.XLSYS_FLOW add constraint FK_F_REFERENCE_V1 foreign key (MVIDOFLPART)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOW add constraint FK_F_REFERENCE_V2 foreign key (MVIDOFMPART)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWCODEALLOC add constraint FK_FCA_REFERENCE_CA foreign key (CAID)
      references XLSYSSH.XLSYS_CODEALLOC (CAID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWCODEALLOC add constraint FK_FCA_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWCONDITION add constraint FK_FC_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWJAVA add constraint FK_FJAVA_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWJAVA add constraint FK_FJAVA_REFERENCE_V foreign key (VIEWID)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWJS add constraint FK_FJS_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWJS add constraint FK_FJS_REFERENCE_V foreign key (VIEWID)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWLOGIC add constraint FK_FL_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWPART add constraint FK_FP_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWPART add constraint FK_FP_REFERENCE_P1 foreign key (LISTPARTID)
      references XLSYSSH.XLSYS_PART (PARTID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWPART add constraint FK_FP_REFERENCE_P2 foreign key (MAINPARTID)
      references XLSYSSH.XLSYS_PART (PARTID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWPART add constraint FK_FP_REFERENCE_R foreign key (RIGHTTYPE)
      references XLSYSSH.XLSYS_RIGHT (RIGHTTYPE) on update restrict;

alter table XLSYSSH.XLSYS_FLOWPART add constraint FK_FP_REFERENCE_V1 foreign key (MVIDOFLPART)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWPART add constraint FK_FP_REFERENCE_V2 foreign key (MVIDOFMPART)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_FLOWRIGHT add constraint FK_FR_REFERENCE_FC foreign key (FLOWID, CDTIDX)
      references XLSYSSH.XLSYS_FLOWCONDITION (FLOWID, IDX) on update restrict;

alter table XLSYSSH.XLSYS_FLOWRIGHT add constraint FK_FR_REFERENCE_R foreign key (RIGHTTYPE)
      references XLSYSSH.XLSYS_RIGHT (RIGHTTYPE) on update restrict;

alter table XLSYSSH.XLSYS_FLOWSUBTABLE add constraint FK_FST_REFERENCE_F foreign key (FLOWID)
      references XLSYSSH.XLSYS_FLOW (FLOWID) on update restrict;

alter table XLSYSSH.XLSYS_IDENTITY add constraint FK_I_REFERENCE_M foreign key (WELCOMEMENUID)
      references XLSYSSH.XLSYS_MENU (MENUID) on update restrict;

alter table XLSYSSH.XLSYS_IDRELATION add constraint FK_IR_REFERENCE_I foreign key (ID)
      references XLSYSSH.XLSYS_IDENTITY (ID) on update restrict;

alter table XLSYSSH.XLSYS_IDRELATION add constraint FK_IR_REFERENCE_R foreign key (RIGHTTYPE)
      references XLSYSSH.XLSYS_RIGHT (RIGHTTYPE) on update restrict;

alter table XLSYSSH.XLSYS_MENURIGHT add constraint FK_MR_REFERENCE_M foreign key (MENUID)
      references XLSYSSH.XLSYS_MENU (MENUID) on update restrict;

alter table XLSYSSH.XLSYS_OACATEGORYRIGHT add constraint FK_OACR_REFERENCE_OAC foreign key (OACID)
      references XLSYSSH.XLSYS_OACATEGORY (OACID) on update restrict;

alter table XLSYSSH.XLSYS_OACMBELONG add constraint FK_OACMBL_REFERENCE_OAC foreign key (OACID)
      references XLSYSSH.XLSYS_OACATEGORY (OACID) on update restrict;

alter table XLSYSSH.XLSYS_OACMBELONG add constraint FK_OACMB_REFERENCE_OAM foreign key (OAMID)
      references XLSYSSH.XLSYS_OAMODULE (OAMID) on update restrict;

alter table XLSYSSH.XLSYS_OACMRELATION add constraint FK_OACMR_REFERENCE_ID foreign key (ID)
      references XLSYSSH.XLSYS_IDENTITY (ID) on update restrict;

alter table XLSYSSH.XLSYS_OACMRELATION add constraint FK_OACMR_REFERENCE_OACMBL foreign key (OACID, OAMID)
      references XLSYSSH.XLSYS_OACMBELONG (OACID, OAMID) on update restrict;

alter table XLSYSSH.XLSYS_OAMODULEEXTRA add constraint FK_OAME_REFERENCE_OAM foreign key (OAMID)
      references XLSYSSH.XLSYS_OAMODULE (OAMID) on update restrict;

alter table XLSYSSH.XLSYS_OAMODULEEXTRA add constraint FK_OAME_REFERENCE_V foreign key (VIEWID)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_OAMODULERIGHT add constraint FK_OAMR_REFERENCE_OAM foreign key (OAMID)
      references XLSYSSH.XLSYS_OAMODULE (OAMID) on update restrict;

alter table XLSYSSH.XLSYS_PARTDETAIL add constraint FK_PD_REFERENCE_P foreign key (PARTID)
      references XLSYSSH.XLSYS_PART (PARTID) on update restrict;

alter table XLSYSSH.XLSYS_PARTDETAIL add constraint FK_PD_REFERENCE_V1 foreign key (VIEWID)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_PARTDETAIL add constraint FK_PD_REFERENCE_V2 foreign key (MAINVIEWID)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_QUERYPARAMSAVE add constraint FK_QPS_REFERENCE_ID foreign key (ID)
      references XLSYSSH.XLSYS_IDENTITY (ID) on update restrict;

alter table XLSYSSH.XLSYS_QUERYPARAMSAVE add constraint FK_QPS_REFERENCE_V foreign key (VIEWID)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_RATIFY add constraint FK_R_REFERENCE_FC_F foreign key (FROMFLOWID, FROMCDTIDX)
      references XLSYSSH.XLSYS_FLOWCONDITION (FLOWID, IDX) on update restrict;

alter table XLSYSSH.XLSYS_RATIFY add constraint FK_R_REFERENCE_FC_T foreign key (TOFLOWID, TOCDTIDX)
      references XLSYSSH.XLSYS_FLOWCONDITION (FLOWID, IDX) on update restrict;

alter table XLSYSSH.XLSYS_RATIFY add constraint FK_R_REFERENCE_U foreign key (FROMUSERID)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLSYS_RATIFYDETAIL add constraint FK_RD_REFERENCE_R foreign key (RTFID)
      references XLSYSSH.XLSYS_RATIFY (RTFID) on update restrict;

alter table XLSYSSH.XLSYS_RATIFYDETAIL add constraint FK_RD_REFERENCE_U1 foreign key (TOUSERID)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLSYS_RATIFYDETAIL add constraint FK_RD_REFERENCE_U2 foreign key (REPLACEUSERID)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLSYS_TRANSPORTDETAIL add constraint FK_TSDT_REFERENCE_TS foreign key (TSID)
      references XLSYSSH.XLSYS_TRANSPORT (TSID) on update restrict;

alter table XLSYSSH.XLSYS_TRANSPORTDTCOLMAP add constraint FK_TSDTCM_REFERENCE_TSDT foreign key (TSID, TSDTIDX)
      references XLSYSSH.XLSYS_TRANSPORTDETAIL (TSID, IDX) on update restrict;

alter table XLSYSSH.XLSYS_TRANSPORTKEYSYNONYM add constraint FK_TSKS_REFERENCE_TSK foreign key (TSKEYID)
      references XLSYSSH.XLSYS_TRANSPORTKEY (TSKEYID) on update restrict;

alter table XLSYSSH.XLSYS_TRANSPORTMAP add constraint FK_TSK_REFERENCE_TSM foreign key (TSKEYID)
      references XLSYSSH.XLSYS_TRANSPORTKEY (TSKEYID) on update restrict;

alter table XLSYSSH.XLSYS_TRANSPORTRUN add constraint FK_TSR_REFERENCE_TS foreign key (TSID)
      references XLSYSSH.XLSYS_TRANSPORT (TSID) on update restrict;

alter table XLSYSSH.XLSYS_USEREMAIL add constraint FK_UE_REFERENCE_U foreign key (USERID)
      references XLSYSSH.XLSYS_USER (USERID) on update restrict;

alter table XLSYSSH.XLSYS_VIEWDETAIL add constraint FK_VD_REFERENCE_V foreign key (VIEWID)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_VIEWDETAILPARAM add constraint FK_VDP_REFERENCE_VD foreign key (VIEWID, IDX)
      references XLSYSSH.XLSYS_VIEWDETAIL (VIEWID, IDX) on update restrict;

alter table XLSYSSH.XLSYS_VIEWQUERYPARAM add constraint FK_VQP_REFERENCE_V foreign key (VIEWID)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_WDTCOLUMN add constraint FK_WDC_REFERENCE_WD foreign key (WIZARDID, DTIDX)
      references XLSYSSH.XLSYS_WIZARDDETAIL (WIZARDID, IDX) on update restrict;

alter table XLSYSSH.XLSYS_WIZARDDETAIL add constraint FK_WD_REFERENCE_V foreign key (VIEWID)
      references XLSYSSH.XLSYS_VIEW (VIEWID) on update restrict;

alter table XLSYSSH.XLSYS_WIZARDDETAIL add constraint FK_WD_REFERENCE_W1 foreign key (WIZARDID)
      references XLSYSSH.XLSYS_WIZARD (WIZARDID) on update restrict;

