/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/3/11 17:44:57                           */
/*==============================================================*/


drop table if exists xlem_atomicunit;

drop table if exists xlem_buyer;

drop table if exists xlem_item;

drop table if exists xlem_itemsku;

drop table if exists xlem_prepsynonym;

drop table if exists xlem_searchkeyword;

drop table if exists xlem_searchtext;

drop index UN_SELLER_U on xlem_seller;

drop table if exists xlem_seller;

drop table if exists xlem_sku;

drop table if exists xlem_spu;

drop table if exists xlem_spucategory;

drop table if exists xlem_spuindex;

drop table if exists xlem_stock;

drop table if exists xlem_stockhistory;

drop table if exists xlem_synonym;

drop table if exists xlem_unit;

drop table if exists xlem_user;

drop table if exists xlem_userlevel;

drop table if exists xlfin_account;

drop table if exists xlfin_accountcondition;

drop table if exists xlfin_accountingitem;

drop table if exists xlfin_balance;

drop table if exists xlfin_balanceitem;

drop table if exists xlfin_bankstmt;

drop table if exists xlfin_bankstmtbalance;

drop table if exists xlfin_bankstmttemplet;

drop table if exists xlfin_bstldt;

drop table if exists xlfin_currency;

drop table if exists xlfin_exchangerate;

drop table if exists xlfin_karaccdt;

drop table if exists xlfin_kardt;

drop table if exists xlfin_kdeptaccrealtion;

drop table if exists xlfin_keepdept;

drop index un_rrbe on xlfin_reportdata;

drop table if exists xlfin_reportdata;

drop table if exists xlfin_reportdatadetail;

drop table if exists xlfin_reportdept;

drop table if exists xlfin_reportform;

drop table if exists xlfin_reportformcol;

drop table if exists xlfin_reportformformula;

drop table if exists xlfin_reportformrow;

drop table if exists xlfin_vdtextra;

drop table if exists xlfin_vdtextracol;

drop table if exists xlfin_vdtextracolattr;

drop table if exists xlfin_voucher;

drop table if exists xlfin_voucherdetail;

drop table if exists xlfin_vouchertemplet;

drop table if exists xlfin_vtempletdt;

drop table if exists xlfin_vtldtcol;

drop table if exists xlsys_attachment;

drop table if exists xlsys_authorisedright;

drop table if exists xlsys_authorize;

drop index un_ad_fci on xlsys_authorizedetail;

drop table if exists xlsys_authorizedetail;

drop table if exists xlsys_basebusi;

drop table if exists xlsys_codealloc;

drop table if exists xlsys_department;

drop table if exists xlsys_dict;

drop table if exists xlsys_dictdetail;

drop table if exists xlsys_emailtemplate;

drop table if exists xlsys_extracmd;

drop table if exists xlsys_exttableinfo;

drop table if exists xlsys_exttableinfodetail;

drop table if exists xlsys_flow;

drop table if exists xlsys_flowcodealloc;

drop table if exists xlsys_flowcondition;

drop table if exists xlsys_flowjava;

drop table if exists xlsys_flowjs;

drop table if exists xlsys_flowlogic;

drop table if exists xlsys_flowpart;

drop table if exists xlsys_flowright;

drop table if exists xlsys_flowsubtable;

drop table if exists xlsys_identity;

drop index xlsys_idrelation_tcv on xlsys_idrelation;

drop index xlsys_idrelation_uq on xlsys_idrelation;

drop table if exists xlsys_idrelation;

drop table if exists xlsys_image;

drop table if exists xlsys_ipresource;

drop table if exists xlsys_javaclass;

drop table if exists xlsys_menu;

drop table if exists xlsys_menuright;

drop table if exists xlsys_oacategory;

drop table if exists xlsys_oacategoryright;

drop table if exists xlsys_oacmbelong;

drop table if exists xlsys_oacmrelation;

drop table if exists xlsys_oamodule;

drop table if exists xlsys_oamoduleextra;

drop table if exists xlsys_oamoduleright;

drop table if exists xlsys_part;

drop table if exists xlsys_partdetail;

drop table if exists xlsys_position;

drop table if exists xlsys_print;

drop table if exists xlsys_queryparamsave;

drop table if exists xlsys_ratify;

drop table if exists xlsys_ratifydetail;

drop index UN_RIGHT_SK on xlsys_right;

drop index UN_RIGHT_RC on xlsys_right;

drop table if exists xlsys_right;

drop table if exists xlsys_translator;

drop table if exists xlsys_transport;

drop table if exists xlsys_transportdetail;

drop table if exists xlsys_transportdtcolmap;

drop table if exists xlsys_transportkey;

drop table if exists xlsys_transportkeysynonym;

drop table if exists xlsys_transportmap;

drop table if exists xlsys_transportrun;

drop table if exists xlsys_user;

drop table if exists xlsys_useremail;

drop table if exists xlsys_view;

drop index un_vd on xlsys_viewdetail;

drop table if exists xlsys_viewdetail;

drop table if exists xlsys_viewdetailparam;

drop table if exists xlsys_viewqueryparam;

drop table if exists xlsys_wdtcolumn;

drop table if exists xlsys_wizard;

drop table if exists xlsys_wizarddetail;

/*==============================================================*/
/* Table: xlem_atomicunit                                       */
/*==============================================================*/
create table xlem_atomicunit
(
   aunitid              varchar(32) not null,
   name                 varchar(256),
   primary key (aunitid)
);

alter table xlem_atomicunit comment '不可拆分的单位定义表';

/*==============================================================*/
/* Table: xlem_buyer                                            */
/*==============================================================*/
create table xlem_buyer
(
   buyerid              varchar(256) not null,
   name                 varchar(256),
   userid               varchar(256),
   levelid              varchar(256),
   experience           numeric(18,6),
   primary key (buyerid)
);

alter table xlem_buyer comment '电子商务买家表, 主要记录买家相关信息';

/*==============================================================*/
/* Table: xlem_item                                             */
/*==============================================================*/
create table xlem_item
(
   itemid               varchar(32) not null,
   sellerid             varchar(256),
   name                 varchar(256),
   description          varchar(4000),
   primary key (itemid)
);

alter table xlem_item comment '电子商务商品表.
一个商品对应多个SKU';

/*==============================================================*/
/* Table: xlem_itemsku                                          */
/*==============================================================*/
create table xlem_itemsku
(
   itemid               varchar(32) not null,
   sku                  varchar(32) not null,
   primary key (itemid, sku)
);

alter table xlem_itemsku comment '商品对应的SKU';

/*==============================================================*/
/* Table: xlem_prepsynonym                                      */
/*==============================================================*/
create table xlem_prepsynonym
(
   mergehash            numeric(8,0) not null,
   srcword              varchar(32) not null,
   synword              varchar(32) not null,
   heat                 numeric(8,0),
   primary key (mergehash)
);

alter table xlem_prepsynonym comment '预备同义词库. 该库中的同义词热度累计到达一定数量后, 则会转移到同义词库中';

/*==============================================================*/
/* Table: xlem_searchkeyword                                    */
/*==============================================================*/
create table xlem_searchkeyword
(
   keyword              varchar(64) not null,
   heat                 numeric(8,0) not null,
   primary key (keyword)
);

alter table xlem_searchkeyword comment '查询关键字热度表, 此表中存的都是独立的不需要进行拆分的查询关键字信息';

/*==============================================================*/
/* Table: xlem_searchtext                                       */
/*==============================================================*/
create table xlem_searchtext
(
   searchtext           varchar(1000) not null,
   heat                 numeric(8,0) not null,
   primary key (searchtext)
);

alter table xlem_searchtext comment '查询热度表. 此表保存了所有查询字符串以及对应的热度';

/*==============================================================*/
/* Table: xlem_seller                                           */
/*==============================================================*/
create table xlem_seller
(
   sellerid             varchar(256) not null,
   name                 varchar(256),
   userid               varchar(256),
   levelid              varchar(256),
   experience           numeric(18,6),
   primary key (sellerid)
);

alter table xlem_seller comment '电子商务卖家表, 主要记录卖家相关信息';

/*==============================================================*/
/* Index: UN_SELLER_U                                           */
/*==============================================================*/
create unique index UN_SELLER_U on xlem_seller
(
   userid
);

/*==============================================================*/
/* Table: xlem_sku                                              */
/*==============================================================*/
create table xlem_sku
(
   sku                  varchar(32) not null,
   spu                  varchar(32) not null,
   aunitid              varchar(32) not null,
   name                 varchar(256),
   description          varchar(4000),
   primary key (sku)
);

alter table xlem_sku comment '电子商务SKU.
库存量单位, 最小单位, 不可分割
一个SKU对应一个SPU.
一';

/*==============================================================*/
/* Table: xlem_spu                                              */
/*==============================================================*/
create table xlem_spu
(
   spu                  varchar(32) not null,
   categoryid           varchar(256),
   name                 varchar(256),
   description          varchar(4000),
   primary key (spu)
);

alter table xlem_spu comment '电子商务SPU表.一个SPU对应多个SKU, 一个SKU只对应一个SPU';

/*==============================================================*/
/* Table: xlem_spucategory                                      */
/*==============================================================*/
create table xlem_spucategory
(
   categoryid           varchar(256) not null,
   name                 varchar(256),
   primary key (categoryid)
);

alter table xlem_spucategory comment 'SPU分类';

/*==============================================================*/
/* Table: xlem_spuindex                                         */
/*==============================================================*/
create table xlem_spuindex
(
   filename             varchar(256) not null,
   idx                  numeric(8,0) not null,
   content              longblob,
   primary key (filename, idx)
);

alter table xlem_spuindex comment '电子商务SPU索引';

/*==============================================================*/
/* Table: xlem_stock                                            */
/*==============================================================*/
create table xlem_stock
(
   stkid                numeric(16,0) not null,
   sku                  varchar(32) not null,
   quantity             numeric(18,6) not null,
   direction            numeric(2,0) not null,
   aunitprice           numeric(18,6) not null,
   primary key (stkid)
);

alter table xlem_stock comment '电子商务库存表';

/*==============================================================*/
/* Table: xlem_stockhistory                                     */
/*==============================================================*/
create table xlem_stockhistory
(
   stkid                numeric(16,0) not null,
   sku                  varchar(32),
   quantity             numeric(18,6),
   direction            numeric(2,0),
   aunitprice           numeric(18,6),
   primary key (stkid)
);

alter table xlem_stockhistory comment '库存历史记录表';

/*==============================================================*/
/* Table: xlem_synonym                                          */
/*==============================================================*/
create table xlem_synonym
(
   mergehash            numeric(8,0) not null comment '原词和同义词经过排序连接后的hash值, 防止重复添加同义词',
   srcword              varchar(32) not null,
   synword              varchar(32) not null,
   heat                 numeric(8,0) comment '该同义词的使用热度',
   primary key (mergehash)
);

alter table xlem_synonym comment '同义词表';

/*==============================================================*/
/* Table: xlem_unit                                             */
/*==============================================================*/
create table xlem_unit
(
   unitid               varchar(32) not null,
   aunitid              varchar(32) not null,
   name                 varchar(256),
   exchangerate         numeric(18,6) not null comment '单位对原子单位的兑换率',
   primary key (unitid)
);

alter table xlem_unit comment '单位表';

/*==============================================================*/
/* Table: xlem_user                                             */
/*==============================================================*/
create table xlem_user
(
   userid               varchar(256) not null,
   name                 varchar(256),
   primary key (userid)
);

alter table xlem_user comment '电子商城用户表, 主要记录用户基本资料';

/*==============================================================*/
/* Table: xlem_userlevel                                        */
/*==============================================================*/
create table xlem_userlevel
(
   levelid              varchar(256) not null,
   name                 varchar(256),
   nextlevel            varchar(256),
   exprequire           numeric(18,6),
   primary key (levelid)
);

alter table xlem_userlevel comment '等级定义表';

/*==============================================================*/
/* Table: xlfin_account                                         */
/*==============================================================*/
create table xlfin_account
(
   accid                varchar(256) not null,
   name                 varchar(1024),
   adc                  numeric(2,0),
   vdc                  numeric(2,0),
   acctype              numeric(2,0),
   forbankstmt          numeric(2,0),
   primary key (accid)
);

alter table xlfin_account comment '科目表';

/*==============================================================*/
/* Table: xlfin_accountcondition                                */
/*==============================================================*/
create table xlfin_accountcondition
(
   kdeptid              varchar(256) not null,
   year                 numeric(8,0) not null,
   month                numeric(2,0) not null,
   "condition"          varchar(256) comment '400:已记账;800:已结账',
   primary key (kdeptid, year, month)
);

alter table xlfin_accountcondition comment '记账状态记录表';

/*==============================================================*/
/* Table: xlfin_accountingitem                                  */
/*==============================================================*/
create table xlfin_accountingitem
(
   vdcol                varchar(256) not null comment '凭证明细字段',
   kdeptids             varchar(4000),
   accids               varchar(4000),
   nasm                 numeric(2,0) comment 'Non accounting statistics method
            非核算项的统计方式
            1:sum;2:max;3:min;4:avg',
   dbcol                varchar(64) comment '当vdc=1时统计放入balance表的字段',
   cbcol                varchar(64) comment '当vdc=-1时统计放入balance的字段',
   bcol                 varchar(64) comment '忽略vdc时统计放入balance表的字段',
   dvkdeptids           varchar(4000) comment '使用该字段进行往来核销的记账部门',
   dvaccids             varchar(4000) comment '使用该字段进行往来核销的科目',
   primary key (vdcol)
);

alter table xlfin_accountingitem comment '核算项设置';

/*==============================================================*/
/* Table: xlfin_balance                                         */
/*==============================================================*/
create table xlfin_balance
(
   balanceid            varchar(256) not null,
   year                 numeric(8,0),
   month                numeric(2,0),
   kdeptid              varchar(256),
   fcrcid               varchar(256),
   accid                varchar(256),
   dfca                 numeric(18,6),
   cfca                 numeric(18,6),
   fcb                  numeric(18,6),
   dsca                 numeric(18,6),
   csca                 numeric(18,6),
   scb                  numeric(18,6),
   drca                 numeric(18,6),
   crca                 numeric(18,6),
   rcb                  numeric(18,6),
   dusda                numeric(18,6),
   cusda                numeric(18,6),
   usdb                 numeric(18,6),
   dquantitya           numeric(18,6),
   cquantitya           numeric(18,6),
   quantityb            numeric(18,6),
   primary key (balanceid)
);

alter table xlfin_balance comment '余额表';

/*==============================================================*/
/* Table: xlfin_balanceitem                                     */
/*==============================================================*/
create table xlfin_balanceitem
(
   bcol                 varchar(64) not null,
   operatormode         numeric(2,0) comment 'balance column operator mode
            余额表列的运算方式
            1:add;2:max;3:min;4:cover',
   primary key (bcol)
);

alter table xlfin_balanceitem comment '余额表项的配置';

/*==============================================================*/
/* Table: xlfin_bankstmt                                        */
/*==============================================================*/
create table xlfin_bankstmt
(
   bsid                 varchar(256) not null,
   year                 numeric(8,0),
   month                numeric(2,0),
   tradedate            datetime,
   kdeptid              varchar(256),
   userid               varchar(256),
   accid                varchar(256),
   bdc                  numeric(2,0),
   fcrcid               varchar(256),
   fcrcamount           numeric(18,6),
   remark               varchar(256),
   vid                  varchar(256),
   vidx                 numeric(8,0),
   bcdate               datetime comment '对账日期',
   bctype               numeric(2,0) comment '对账类型
            0:手动;1:自动',
   primary key (bsid)
);

alter table xlfin_bankstmt comment '银行对账单表';

/*==============================================================*/
/* Table: xlfin_bankstmtbalance                                 */
/*==============================================================*/
create table xlfin_bankstmtbalance
(
   bsbid                varchar(256) not null,
   kdeptid              varchar(256),
   fcrcid               varchar(256),
   accid                varchar(256),
   year                 numeric(8,0),
   month                numeric(2,0),
   fcb                  numeric(18,6),
   primary key (bsbid)
);

alter table xlfin_bankstmtbalance comment '银行对账单余额表';

/*==============================================================*/
/* Table: xlfin_bankstmttemplet                                 */
/*==============================================================*/
create table xlfin_bankstmttemplet
(
   bstlid               varchar(256) not null,
   name                 varchar(64),
   javalistener         varchar(4000),
   jslistener           longblob,
   primary key (bstlid)
);

alter table xlfin_bankstmttemplet comment '银行对账单模板主表';

/*==============================================================*/
/* Table: xlfin_bstldt                                          */
/*==============================================================*/
create table xlfin_bstldt
(
   bstlid               varchar(256) not null,
   idx                  numeric(8,0) not null,
   templetcol           varchar(256),
   bscol                varchar(256),
   primary key (bstlid, idx)
);

alter table xlfin_bstldt comment '银行对账单模板明细';

/*==============================================================*/
/* Table: xlfin_currency                                        */
/*==============================================================*/
create table xlfin_currency
(
   crcid                varchar(256) not null,
   name                 varchar(128),
   crccode              varchar(8),
   primary key (crcid)
);

alter table xlfin_currency comment '币种表';

/*==============================================================*/
/* Table: xlfin_exchangerate                                    */
/*==============================================================*/
create table xlfin_exchangerate
(
   erid                 varchar(256) not null,
   crcid                varchar(256),
   buyingrate           numeric(18,6),
   cashbuyingrate       numeric(18,6),
   sellingrate          numeric(18,6),
   cashsellingrate      numeric(18,6),
   middlerate           numeric(18,6),
   pubtime              datetime,
   primary key (erid)
);

alter table xlfin_exchangerate comment '汇率表
http://www.boc.cn/sourcedb/whpj/enindex.html';

/*==============================================================*/
/* Table: xlfin_karaccdt                                        */
/*==============================================================*/
create table xlfin_karaccdt
(
   karid                varchar(256) not null,
   idx                  numeric(8,0) not null,
   accids               varchar(4000),
   showcolumns          varchar(4000),
   primary key (karid, idx)
);

alter table xlfin_karaccdt comment '记账部门科目关系明细配置';

/*==============================================================*/
/* Table: xlfin_kardt                                           */
/*==============================================================*/
create table xlfin_kardt
(
   karid                varchar(256) not null,
   vdtecaid             varchar(256) not null,
   name                 varchar(64),
   primary key (karid, vdtecaid)
);

alter table xlfin_kardt comment '记账部门对应科目所拥有的字段关系表';

/*==============================================================*/
/* Table: xlfin_kdeptaccrealtion                                */
/*==============================================================*/
create table xlfin_kdeptaccrealtion
(
   karid                varchar(256) not null,
   name                 varchar(64),
   accids               varchar(4000),
   primary key (karid)
);

alter table xlfin_kdeptaccrealtion comment '科目关系定义主表';

/*==============================================================*/
/* Table: xlfin_keepdept                                        */
/*==============================================================*/
create table xlfin_keepdept
(
   kdeptid              varchar(256) not null,
   name                 varchar(64),
   standardcrcid        varchar(256),
   reportcrcid          varchar(256),
   usedkarid            varchar(256),
   nocarryoverkarid     varchar(256),
   cockarid             varchar(256),
   needdcequal          numeric(2,0),
   vdatemode            numeric(2,0) comment '凭证日期模式
            0:与当前日期同月时使用当前日期
            1:与当前日期同月时使用上一次凭证日期',
   transfermode         numeric(2,0) comment '转账模式
            0:转入时合并
            1:转入时不合并',
   beginvdate           datetime comment '期初日期，该记账部门的凭证日期只能大于等于该日期',
   kdepttype            numeric(2,0) comment '记账部门类型, 0:记账部门下属;1:记账部门节点
            只有为1时该条记录才是系统使用的真正的记账部门',
   beginbsdate          datetime,
   primary key (kdeptid)
);

alter table xlfin_keepdept comment '记账部门';

/*==============================================================*/
/* Table: xlfin_reportdata                                      */
/*==============================================================*/
create table xlfin_reportdata
(
   rdid                 varchar(256) not null,
   rfid                 varchar(256) not null,
   rdeptid              varchar(256) not null,
   timeperiod           numeric(2,0),
   begindate            datetime not null,
   enddate              datetime not null,
   primary key (rdid)
);

alter table xlfin_reportdata comment '报表数据';

/*==============================================================*/
/* Index: un_rrbe                                               */
/*==============================================================*/
create unique index un_rrbe on xlfin_reportdata
(
   rfid,
   rdeptid,
   begindate,
   enddate
);

/*==============================================================*/
/* Table: xlfin_reportdatadetail                                */
/*==============================================================*/
create table xlfin_reportdatadetail
(
   rdid                 varchar(256) not null,
   idx                  numeric(2,0) not null,
   rfrowid              varchar(256) not null,
   rfcolid              varchar(256) not null,
   value                varchar(4000),
   primary key (rdid, idx)
);

alter table xlfin_reportdatadetail comment '报表数据明细';

/*==============================================================*/
/* Table: xlfin_reportdept                                      */
/*==============================================================*/
create table xlfin_reportdept
(
   rdeptid              varchar(256) not null,
   name                 varchar(256),
   kdeptids             varchar(4000),
   gatherrdeptids       varchar(4000),
   primary key (rdeptid)
);

alter table xlfin_reportdept comment '上报单位定义表';

/*==============================================================*/
/* Table: xlfin_reportform                                      */
/*==============================================================*/
create table xlfin_reportform
(
   rfid                 varchar(256) not null,
   name                 varchar(64),
   javalistener         varchar(4000),
   jslistener           longblob,
   primary key (rfid)
);

alter table xlfin_reportform comment '报表定义';

/*==============================================================*/
/* Table: xlfin_reportformcol                                   */
/*==============================================================*/
create table xlfin_reportformcol
(
   rfid                 varchar(256) not null,
   idx                  numeric(8,0) not null,
   name                 varchar(64),
   rfcolid              varchar(256) not null,
   datatype             numeric(2,0) not null,
   param                varchar(4000),
   primary key (rfid, idx)
);

alter table xlfin_reportformcol comment '报表列定义';

/*==============================================================*/
/* Table: xlfin_reportformformula                               */
/*==============================================================*/
create table xlfin_reportformformula
(
   rfid                 varchar(256) not null,
   idx                  numeric(8,0) not null,
   rfrowid              varchar(256) not null,
   rfcolid              varchar(256) not null,
   formula              longblob,
   primary key (rfid, idx)
);

alter table xlfin_reportformformula comment '报表公式定义';

/*==============================================================*/
/* Table: xlfin_reportformrow                                   */
/*==============================================================*/
create table xlfin_reportformrow
(
   rfid                 varchar(256) not null,
   idx                  numeric(8,0) not null,
   name                 varchar(64),
   rfrowid              varchar(256) not null,
   primary key (rfid, idx)
);

alter table xlfin_reportformrow comment '报表行定义';

/*==============================================================*/
/* Table: xlfin_vdtextra                                        */
/*==============================================================*/
create table xlfin_vdtextra
(
   voucherid            varchar(256) not null,
   idx                  numeric(8,0) not null,
   extracol             varchar(256) not null,
   colvalue             varchar(4000),
   primary key (voucherid, idx, extracol)
);

alter table xlfin_vdtextra comment '凭证明细附加表';

/*==============================================================*/
/* Table: xlfin_vdtextracol                                     */
/*==============================================================*/
create table xlfin_vdtextracol
(
   extracol             varchar(256) not null,
   datatype             numeric(2,0) not null,
   name                 varchar(64),
   primary key (extracol)
);

alter table xlfin_vdtextracol comment '凭证明细可附加字段定义';

/*==============================================================*/
/* Table: xlfin_vdtextracolattr                                 */
/*==============================================================*/
create table xlfin_vdtextracolattr
(
   vdtecaid             varchar(256) not null,
   extracol             varchar(256) not null,
   name                 varchar(64),
   type                 numeric(2,0),
   param                varchar(4000),
   supportvalue         varchar(4000),
   primary key (vdtecaid)
);

alter table xlfin_vdtextracolattr comment '凭证明细附加字段属性定义';

/*==============================================================*/
/* Table: xlfin_voucher                                         */
/*==============================================================*/
create table xlfin_voucher
(
   voucherid            varchar(256) not null,
   flowid               varchar(256),
   creater              varchar(256),
   creationdate         datetime,
   modifydate           datetime,
   "condition"          varchar(256),
   kdeptid              varchar(256),
   id                   varchar(256),
   vno                  numeric(8,0),
   year                 numeric(8,0),
   month                numeric(2,0),
   vdate                datetime,
   attachno             numeric(2,0),
   accounter            varchar(256),
   creationmode         numeric(2,0) not null comment '凭证生成模式
            0:手动生成;90:年初结转的未达账数据;91:年初结转的往来未核销数据;1~89:业务各接口数据;90~99:财务自动生成数据',
   primary key (voucherid)
);

alter table xlfin_voucher comment '凭证表';

/*==============================================================*/
/* Table: xlfin_voucherdetail                                   */
/*==============================================================*/
create table xlfin_voucherdetail
(
   voucherid            varchar(256) not null,
   idx                  numeric(8,0) not null,
   year                 numeric(8,0),
   month                numeric(2,0),
   vno                  numeric(8,0),
   vdate                datetime,
   kdeptid              varchar(256),
   vdc                  numeric(2,0),
   fcrcid               varchar(256),
   accid                varchar(256),
   userid               varchar(256),
   fcrcamount           numeric(18,6),
   ftosrate             numeric(18,6),
   ftorrate             numeric(18,6),
   ftousdrate           numeric(18,6),
   quantity             numeric(18,6),
   remark               varchar(256),
   bsid                 varchar(256),
   bcdate               datetime,
   bctype               numeric(2,0),
   primary key (voucherid, idx)
);

alter table xlfin_voucherdetail comment '凭证明细';

/*==============================================================*/
/* Table: xlfin_vouchertemplet                                  */
/*==============================================================*/
create table xlfin_vouchertemplet
(
   vtlid                varchar(256) not null,
   name                 varchar(64),
   javalistener         varchar(4000),
   jslistener           longblob,
   querysql             varchar(4000),
   creationmode         numeric(2,0) not null,
   primary key (vtlid)
);

alter table xlfin_vouchertemplet comment '凭证模板定义主表';

/*==============================================================*/
/* Table: xlfin_vtempletdt                                      */
/*==============================================================*/
create table xlfin_vtempletdt
(
   vtlid                varchar(256) not null,
   idx                  numeric(8,0) not null,
   name                 varchar(64),
   vrowid               varchar(256),
   remark               varchar(256),
   vdc                  numeric(2,0),
   accid                varchar(256),
   querysql             varchar(4000),
   formula              longblob,
   primary key (idx, vtlid)
);

alter table xlfin_vtempletdt comment '凭证模板明细配置';

/*==============================================================*/
/* Table: xlfin_vtldtcol                                        */
/*==============================================================*/
create table xlfin_vtldtcol
(
   vtlid                varchar(256) not null,
   vtldtidx             numeric(8,0) not null,
   idx                  numeric(8,0) not null,
   mdscolname           varchar(256),
   dtdscolname          varchar(256),
   vdtcolname           varchar(256),
   primary key (vtlid, vtldtidx, idx)
);

alter table xlfin_vtldtcol comment '凭证模板明细字段配置';

/*==============================================================*/
/* Table: xlsys_attachment                                      */
/*==============================================================*/
create table xlsys_attachment
(
   attachid             numeric(8,0) not null,
   name                 varchar(256),
   attachdata           longblob,
   primary key (attachid)
);

/*==============================================================*/
/* Table: xlsys_authorisedright                                 */
/*==============================================================*/
create table xlsys_authorisedright
(
   arid                 varchar(256) not null,
   ardtidx              numeric(8,0) not null,
   idx                  numeric(8,0) not null,
   righttype            numeric(2,0),
   rightvalue           varchar(256),
   primary key (arid, ardtidx, idx)
);

alter table xlsys_authorisedright comment '被授权权限';

/*==============================================================*/
/* Table: xlsys_authorize                                       */
/*==============================================================*/
create table xlsys_authorize
(
   arid                 varchar(256) not null,
   arno                 varchar(256),
   flowid               varchar(256),
   id                   varchar(256),
   creater              varchar(256),
   creationdate         datetime,
   modifydate           datetime,
   "condition"          varchar(256),
   authorisedid         varchar(256) comment '授权身份',
   begindate            datetime,
   enddate              datetime,
   remark               varchar(4000),
   primary key (arid)
);

alter table xlsys_authorize comment '授权主表';

/*==============================================================*/
/* Table: xlsys_authorizedetail                                 */
/*==============================================================*/
create table xlsys_authorizedetail
(
   arid                 varchar(256) not null,
   idx                  numeric(8,0) not null,
   arflowid             varchar(256) comment '授权流程',
   arcondition          varchar(256) comment '授权状态',
   beauthorizedid       varchar(256) comment '被授权身份',
   primary key (arid, idx)
);

alter table xlsys_authorizedetail comment '授权明细表';

/*==============================================================*/
/* Index: un_ad_fci                                             */
/*==============================================================*/
create unique index un_ad_fci on xlsys_authorizedetail
(
   arflowid,
   arcondition,
   beauthorizedid
);

/*==============================================================*/
/* Table: xlsys_basebusi                                        */
/*==============================================================*/
create table xlsys_basebusi
(
   busiid               varchar(256) not null,
   busino               varchar(256),
   name                 varchar(64),
   flowid               varchar(256) not null,
   id                   varchar(256),
   creater              varchar(256),
   creationdate         datetime not null,
   modifydate           datetime not null,
   "condition"          varchar(256) not null,
   primary key (busiid)
);

/*==============================================================*/
/* Table: xlsys_codealloc                                       */
/*==============================================================*/
create table xlsys_codealloc
(
   caid                 varchar(256) not null,
   name                 varchar(256),
   clientjavascript     longblob,
   clientjavamethod     varchar(4000),
   serverjavascript     longblob,
   serverjavamethod     varchar(4000),
   primary key (caid)
);

alter table xlsys_codealloc comment 'define alloc code method
select ''insert into xlsys_co';

/*==============================================================*/
/* Table: xlsys_department                                      */
/*==============================================================*/
create table xlsys_department
(
   deptid               varchar(256) not null,
   name                 varchar(64) not null,
   primary key (deptid)
);

alter table xlsys_department comment 'Department table';

/*==============================================================*/
/* Table: xlsys_dict                                            */
/*==============================================================*/
create table xlsys_dict
(
   dictid               varchar(256) not null,
   name                 varchar(256),
   primary key (dictid)
);

alter table xlsys_dict comment 'The dictionary of xlsys';

/*==============================================================*/
/* Table: xlsys_dictdetail                                      */
/*==============================================================*/
create table xlsys_dictdetail
(
   dictid               varchar(256) not null,
   code                 varchar(64) not null,
   name                 varchar(256),
   primary key (dictid, code)
);

/*==============================================================*/
/* Table: xlsys_emailtemplate                                   */
/*==============================================================*/
create table xlsys_emailtemplate
(
   etid                 varchar(256) not null,
   name                 varchar(256),
   template             longblob,
   javalistener         varchar(4000),
   jslistener           longblob,
   primary key (etid)
);

alter table xlsys_emailtemplate comment 'Email模板定义';

/*==============================================================*/
/* Table: xlsys_extracmd                                        */
/*==============================================================*/
create table xlsys_extracmd
(
   extracmd             varchar(256) not null,
   name                 varchar(64),
   dispatchpath         varchar(256),
   javalistener         varchar(4000),
   jslistener           longblob,
   primary key (extracmd)
);

/*==============================================================*/
/* Table: xlsys_exttableinfo                                    */
/*==============================================================*/
create table xlsys_exttableinfo
(
   tableid              numeric(8,0) not null,
   name                 varchar(64),
   tablename            varchar(256),
   primary key (tableid)
);

alter table xlsys_exttableinfo comment 'Exter table information';

/*==============================================================*/
/* Table: xlsys_exttableinfodetail                              */
/*==============================================================*/
create table xlsys_exttableinfodetail
(
   tableid              numeric(8,0) not null,
   idx                  numeric(8,0) not null,
   colname              varchar(64),
   name                 varchar(64),
   primarykey           numeric(2,0),
   nullable             numeric(2,0),
   primary key (tableid, idx)
);

alter table xlsys_exttableinfodetail comment 'The detail of exter table information';

/*==============================================================*/
/* Table: xlsys_flow                                            */
/*==============================================================*/
create table xlsys_flow
(
   flowid               varchar(256) not null,
   name                 varchar(64),
   listpartid           varchar(256),
   mvidoflpart          numeric(8,0),
   mainpartid           varchar(256),
   mvidofmpart          numeric(8,0),
   maintable            varchar(64),
   innercodecol         varchar(64),
   outtercodecol        varchar(64),
   jslistener           longblob,
   javalistener         varchar(4000),
   versionupdate        numeric(2,0) comment '是否允许版本更新',
   cancopy              numeric(2,0),
   primary key (flowid)
);

alter table xlsys_flow comment 'Flow Define
select ''insert into XLSYS_FLOW(FLOWID,NAM';

/*==============================================================*/
/* Table: xlsys_flowcodealloc                                   */
/*==============================================================*/
create table xlsys_flowcodealloc
(
   fcaid                varchar(256) not null,
   flowid               varchar(256),
   tablename            varchar(64) not null,
   colname              varchar(64) not null,
   caid                 varchar(256),
   primary key (fcaid)
);

alter table xlsys_flowcodealloc comment 'define code creation for flow';

/*==============================================================*/
/* Table: xlsys_flowcondition                                   */
/*==============================================================*/
create table xlsys_flowcondition
(
   flowid               varchar(256) not null,
   idx                  numeric(8,0) not null,
   "condition"          varchar(64) not null,
   name                 varchar(256),
   audittype            numeric(2,0) comment '审批类型, 0:单审;1:会审;2:组单审;3:组会审;4:投票审
            [单审] : 任意一个人通过即可通过(提交时允许选择审批人)
            [会审] : 所有人通过才可通过(提交时不允许选择审批人)
            [组单审] : 任意一组人通过即可通过, 同一组里的人必须全部通过才算通过(提交时允许选择审批组，不允许选择审批人)
            [组会审] : 所有组的人都通过才可通过, 同一组里的人只有要任意一个人通过就算通过(提交时允许选择审批人，但是每个组都必须选择至少一个审批人)
            [投票审] : 按照一定比例票数通过后即可通过(提交时不允许选择)',
   voterate             numeric(18,6) comment '投票率，当audittype为4:投票审时，此参数有效',
   primary key (flowid, idx)
);

alter table xlsys_flowcondition comment 'The condition of flow';

/*==============================================================*/
/* Table: xlsys_flowjava                                        */
/*==============================================================*/
create table xlsys_flowjava
(
   flowid               varchar(256) not null,
   idx                  numeric(8,0) not null,
   viewid               numeric(8,0),
   javalistener         varchar(4000),
   primary key (flowid, idx)
);

alter table xlsys_flowjava comment 'JavaListener for viewers of flow';

/*==============================================================*/
/* Table: xlsys_flowjs                                          */
/*==============================================================*/
create table xlsys_flowjs
(
   flowid               varchar(256) not null,
   idx                  numeric(8,0) not null,
   viewid               numeric(8,0),
   jslistener           longblob,
   primary key (flowid, idx)
);

alter table xlsys_flowjs comment 'JsListener for viewers of flow ';

/*==============================================================*/
/* Table: xlsys_flowlogic                                       */
/*==============================================================*/
create table xlsys_flowlogic
(
   flowid               varchar(256) not null,
   idx                  numeric(8,0) not null,
   fromcondition        varchar(64),
   tocondition          varchar(64),
   passtype             numeric(2,0) comment '0:手动;1:自动提交;2:自动审批;3:自动审批并自动提交',
   rejecttype           numeric(2,0) comment '驳回类型,0:不允许驳回;1:可驳回到上一级;2:可驳回到任意上级;3:只允许驳回到自定义上级',
   canrejectto          varchar(64),
   clientautotriggersubmit numeric(2,0) comment '是否允许客户端自动触发提交按钮',
   primary key (flowid, idx)
);

alter table xlsys_flowlogic comment 'The logic of each flow';

/*==============================================================*/
/* Table: xlsys_flowpart                                        */
/*==============================================================*/
create table xlsys_flowpart
(
   flowid               varchar(256) not null,
   idx                  numeric(8,0) not null,
   clienttype           varchar(32),
   righttype            numeric(2,0),
   rightvalue           varchar(256),
   listpartid           varchar(256),
   mvidoflpart          numeric(8,0),
   mainpartid           varchar(256),
   mvidofmpart          numeric(8,0),
   primary key (flowid, idx)
);

/*==============================================================*/
/* Table: xlsys_flowright                                       */
/*==============================================================*/
create table xlsys_flowright
(
   flowid               varchar(256) not null,
   cdtidx               numeric(8,0) not null,
   idx                  numeric(8,0) not null,
   belongrighttype      numeric(2,0) comment '0:identity;1:user;2:department;3:position',
   belongrightvalue     varchar(256),
   righttype            numeric(2,0) comment '0:identity;1:user;2:department;3:position',
   rightvalue           varchar(256),
   groupnm              varchar(256) comment '分组名称,当audittype选用 2:组单审和 3:组会审 时有效，用来标识当前权限为哪个组别所有，分组名称相同的视为同一组',
   primary key (flowid, cdtidx, idx)
);

alter table xlsys_flowright comment 'The right of each flow condition';

/*==============================================================*/
/* Table: xlsys_flowsubtable                                    */
/*==============================================================*/
create table xlsys_flowsubtable
(
   flowid               varchar(256) not null,
   tablename            varchar(64) not null,
   relationinnercol     varchar(64),
   primary key (flowid, tablename)
);

alter table xlsys_flowsubtable comment '流程子表配置';

/*==============================================================*/
/* Table: xlsys_identity                                        */
/*==============================================================*/
create table xlsys_identity
(
   id                   varchar(256) not null,
   name                 varchar(64) not null,
   welcomemenuid        varchar(256),
   closedisable         numeric(2,0),
   primary key (id)
);

alter table xlsys_identity comment 'Identity for Xue Lang System, it is unique for Xlsys';

/*==============================================================*/
/* Table: xlsys_idrelation                                      */
/*==============================================================*/
create table xlsys_idrelation
(
   id                   varchar(256) not null,
   idx                  numeric(8,0) not null,
   name                 varchar(64),
   righttype            numeric(2,0) not null,
   relationvalue        varchar(32),
   primary key (idx, id)
);

alter table xlsys_idrelation comment 'The relationship of Identity.';

/*==============================================================*/
/* Index: xlsys_idrelation_uq                                   */
/*==============================================================*/
create unique index xlsys_idrelation_uq on xlsys_idrelation
(
   id,
   righttype,
   relationvalue
);

/*==============================================================*/
/* Index: xlsys_idrelation_tcv                                  */
/*==============================================================*/
create index xlsys_idrelation_tcv on xlsys_idrelation
(
   righttype,
   relationvalue
);

/*==============================================================*/
/* Table: xlsys_image                                           */
/*==============================================================*/
create table xlsys_image
(
   imageid              numeric(8,0) not null,
   name                 varchar(256),
   imagedata            longblob,
   primary key (imageid)
);

alter table xlsys_image comment '图片库';

/*==============================================================*/
/* Table: xlsys_ipresource                                      */
/*==============================================================*/
create table xlsys_ipresource
(
   ipaddress            varchar(64) not null,
   ipresource           longblob,
   primary key (ipaddress)
);

alter table xlsys_ipresource comment 'IP相关资源信息';

/*==============================================================*/
/* Table: xlsys_javaclass                                       */
/*==============================================================*/
create table xlsys_javaclass
(
   classid              varchar(512) not null,
   name                 varchar(64),
   javasource           longblob,
   javabinary           longblob,
   primary key (classid)
);

alter table xlsys_javaclass comment 'The Additional Java Class';

/*==============================================================*/
/* Table: xlsys_menu                                            */
/*==============================================================*/
create table xlsys_menu
(
   menuid               varchar(256) not null,
   name                 varchar(64),
   type                 numeric(2,0),
   cmd                  varchar(256),
   param                varchar(4000),
   showninphone         numeric(1,0),
   primary key (menuid)
);

alter table xlsys_menu comment 'Define Menu';

/*==============================================================*/
/* Table: xlsys_menuright                                       */
/*==============================================================*/
create table xlsys_menuright
(
   menuid               varchar(256) not null,
   idx                  numeric(8,0) not null,
   righttype            numeric(2,0) comment '0:identity;1:user;2:department;3:position',
   rightvalue           varchar(256),
   primary key (menuid, idx)
);

/*==============================================================*/
/* Table: xlsys_oacategory                                      */
/*==============================================================*/
create table xlsys_oacategory
(
   oacid                varchar(256) not null,
   name                 varchar(256) not null,
   icon                 longblob,
   primary key (oacid)
);

alter table xlsys_oacategory comment 'OA类别定义表';

/*==============================================================*/
/* Table: xlsys_oacategoryright                                 */
/*==============================================================*/
create table xlsys_oacategoryright
(
   oacid                varchar(256) not null,
   idx                  numeric(8,0) not null,
   righttype            numeric(2,0),
   rightvalue           varchar(256),
   primary key (oacid, idx)
);

alter table xlsys_oacategoryright comment 'OA分类权限定义表';

/*==============================================================*/
/* Table: xlsys_oacmbelong                                      */
/*==============================================================*/
create table xlsys_oacmbelong
(
   oacid                varchar(256) not null,
   oamid                varchar(256) not null,
   name                 varchar(256),
   primary key (oacid, oamid)
);

alter table xlsys_oacmbelong comment 'OA分类和OA模块的所属关系表';

/*==============================================================*/
/* Table: xlsys_oacmrelation                                    */
/*==============================================================*/
create table xlsys_oacmrelation
(
   id                   varchar(256) not null,
   idx                  numeric(2,0) not null,
   oacid                varchar(256),
   oamid                varchar(256),
   name                 varchar(256),
   sheetname            varchar(256) comment '分页名称',
   hpercent             numeric(4,2) comment '横向占百分比',
   vpixel               numeric(8,0) comment '纵向占像素',
   primary key (idx, id)
);

alter table xlsys_oacmrelation comment 'OA分类和OA模块的关系定义表';

/*==============================================================*/
/* Table: xlsys_oamodule                                        */
/*==============================================================*/
create table xlsys_oamodule
(
   oamid                varchar(256) not null,
   name                 varchar(256),
   cmd                  varchar(256),
   param                varchar(4000),
   primary key (oamid)
);

alter table xlsys_oamodule comment 'OA模块定义表';

/*==============================================================*/
/* Table: xlsys_oamoduleextra                                   */
/*==============================================================*/
create table xlsys_oamoduleextra
(
   oamid                varchar(256) not null,
   viewid               numeric(8,0) not null,
   extcmd               varchar(256),
   extparam             varchar(4000),
   primary key (oamid, viewid)
);

alter table xlsys_oamoduleextra comment 'OA模块附加窗口配置';

/*==============================================================*/
/* Table: xlsys_oamoduleright                                   */
/*==============================================================*/
create table xlsys_oamoduleright
(
   oamid                varchar(256) not null,
   idx                  numeric(8,0) not null,
   righttype            numeric(2,0),
   rightvalue           varchar(256),
   primary key (oamid, idx)
);

alter table xlsys_oamoduleright comment 'OA模块权限定义表';

/*==============================================================*/
/* Table: xlsys_part                                            */
/*==============================================================*/
create table xlsys_part
(
   partid               varchar(256) not null,
   name                 varchar(64) not null,
   parttype             numeric(2,0) not null comment '0:Node;1:Part',
   primary key (partid)
);

alter table xlsys_part comment 'The Main Table Of Defining Part';

/*==============================================================*/
/* Table: xlsys_partdetail                                      */
/*==============================================================*/
create table xlsys_partdetail
(
   partid               varchar(256) not null,
   detailid             varchar(256) not null,
   name                 varchar(64),
   type                 numeric(2,0) comment '0:SashForm;1:TabFolder;2:ExpandBar',
   param                varchar(4000),
   viewid               numeric(8,0),
   relationtype         numeric(2,0),
   mainviewid           numeric(8,0),
   soporder             numeric(8,0),
   primary key (partid, detailid)
);

alter table xlsys_partdetail comment 'The detail of part';

/*==============================================================*/
/* Table: xlsys_position                                        */
/*==============================================================*/
create table xlsys_position
(
   pstid                varchar(256) not null,
   name                 varchar(64) not null,
   primary key (pstid)
);

alter table xlsys_position comment 'Position';

/*==============================================================*/
/* Table: xlsys_print                                           */
/*==============================================================*/
create table xlsys_print
(
   printid              varchar(256) not null,
   name                 varchar(256),
   printtype            numeric(2,0),
   template             longblob,
   primary key (printid)
);

alter table xlsys_print comment '打印模板定义表';

/*==============================================================*/
/* Table: xlsys_queryparamsave                                  */
/*==============================================================*/
create table xlsys_queryparamsave
(
   viewid               numeric(8,0) not null,
   id                   varchar(256) not null,
   name                 varchar(64) not null,
   paramtype            numeric(2,0) not null comment '参数类型
            0:界面查询参数
            1:分组参数',
   paramsave            longblob,
   primary key (viewid, id, name, paramtype)
);

alter table xlsys_queryparamsave comment '查询参数保存表';

/*==============================================================*/
/* Table: xlsys_ratify                                          */
/*==============================================================*/
create table xlsys_ratify
(
   rtfid                varchar(256) not null,
   name                 varchar(256),
   fromuserid           varchar(256),
   fromflowid           varchar(256),
   fromcdtidx           numeric(8,0),
   toflowid             varchar(256),
   tocdtidx             numeric(8,0),
   innercode            varchar(256),
   version              numeric(8,0),
   rtfret               numeric(2,0) comment '0:已提交;1:已通过;2:已驳回',
   rtfdate              datetime,
   primary key (rtfid)
);

alter table xlsys_ratify comment 'The situation of ratifing business flow';

/*==============================================================*/
/* Table: xlsys_ratifydetail                                    */
/*==============================================================*/
create table xlsys_ratifydetail
(
   rtfid                varchar(256) not null,
   touserid             varchar(256) not null,
   replaceuserid        varchar(256),
   rtfret               numeric(2,0) comment '0:已提交;1:已通过;2:已驳回',
   rtfdesc              varchar(4000),
   rtfdate              datetime,
   groupnm              varchar(256),
   primary key (rtfid, touserid)
);

alter table xlsys_ratifydetail comment 'The detail of ratify condition';

/*==============================================================*/
/* Table: xlsys_right                                           */
/*==============================================================*/
create table xlsys_right
(
   righttype            numeric(2,0) not null,
   name                 varchar(64),
   sessionkey           varchar(256) not null,
   relationtable        varchar(256) not null,
   relationcolumn       varchar(256) not null,
   primary key (righttype)
);

alter table xlsys_right comment '系统权限定义表';

/*==============================================================*/
/* Index: UN_RIGHT_RC                                           */
/*==============================================================*/
create unique index UN_RIGHT_RC on xlsys_right
(
   relationcolumn
);

/*==============================================================*/
/* Index: UN_RIGHT_SK                                           */
/*==============================================================*/
create unique index UN_RIGHT_SK on xlsys_right
(
   sessionkey
);

/*==============================================================*/
/* Table: xlsys_translator                                      */
/*==============================================================*/
create table xlsys_translator
(
   tablename            varchar(32) not null,
   defaultname          varchar(256) not null,
   language             varchar(32) not null,
   transname            varchar(256),
   primary key (tablename, defaultname, language)
);

alter table xlsys_translator comment 'Language support';

/*==============================================================*/
/* Table: xlsys_transport                                       */
/*==============================================================*/
create table xlsys_transport
(
   tsid                 varchar(256) not null,
   name                 varchar(256),
   primary key (tsid)
);

alter table xlsys_transport comment '系统传输表, 用来做跨数据库的数据传输';

/*==============================================================*/
/* Table: xlsys_transportdetail                                 */
/*==============================================================*/
create table xlsys_transportdetail
(
   tsid                 varchar(256) not null,
   idx                  numeric(8,0) not null,
   fromtable            varchar(64),
   totable              varchar(64),
   fromsql              varchar(4000),
   javalistener         varchar(4000),
   jslistener           longblob,
   batchcount           numeric(8,0) comment '批量更新数量',
   cpsmcol              numeric(2,0) comment '是否拷贝同名字段',
   active               numeric(2,0),
   primary key (tsid, idx)
);

alter table xlsys_transportdetail comment '数据传输明细定义';

/*==============================================================*/
/* Table: xlsys_transportdtcolmap                               */
/*==============================================================*/
create table xlsys_transportdtcolmap
(
   tsid                 varchar(256) not null,
   tsdtidx              numeric(8,0) not null,
   idx                  numeric(8,0) not null,
   fromcolumn           varchar(64),
   tocolumn             varchar(64),
   primary key (tsid, tsdtidx, idx)
);

alter table xlsys_transportdtcolmap comment '数据传输明细字段对照';

/*==============================================================*/
/* Table: xlsys_transportkey                                    */
/*==============================================================*/
create table xlsys_transportkey
(
   tskeyid              varchar(32) not null,
   name                 varchar(256),
   primary key (tskeyid)
);

alter table xlsys_transportkey comment '传输数据的关键码定义';

/*==============================================================*/
/* Table: xlsys_transportkeysynonym                             */
/*==============================================================*/
create table xlsys_transportkeysynonym
(
   tskeyid              varchar(32) not null,
   tablename            varchar(64) not null,
   columnname           varchar(64) not null,
   primary key (tskeyid, tablename, columnname)
);

alter table xlsys_transportkeysynonym comment '传输关键码同义词定义表，主要用来定义哪些表的哪些字段使用该关键码来进行映射';

/*==============================================================*/
/* Table: xlsys_transportmap                                    */
/*==============================================================*/
create table xlsys_transportmap
(
   tsmapid              varchar(256) not null,
   tskeyid              varchar(32),
   fromdsid             numeric(8,0),
   todsid               numeric(8,0),
   fromtable            varchar(64),
   totable              varchar(64),
   fromcolumn           varchar(64),
   tocolumn             varchar(64),
   fromvalue            varchar(4000),
   tovalue              varchar(4000),
   syndate              datetime,
   batchno              varchar(32),
   remark               varchar(4000),
   otheruse1            varchar(256),
   otheruse2            varchar(256),
   otheruse3            varchar(256),
   primary key (tsmapid)
);

alter table xlsys_transportmap comment '数据传输映射表, 可看做数据传输日志, 以及数据在两个系统中的对照表';

/*==============================================================*/
/* Table: xlsys_transportrun                                    */
/*==============================================================*/
create table xlsys_transportrun
(
   tsrunid              varchar(256) not null,
   tsid                 varchar(256),
   fromdsid             numeric(8,0),
   todsid               numeric(8,0),
   totalthreadcount     numeric(8,0),
   threadcount          numeric(8,0),
   dataperthread        numeric(8,0),
   primary key (tsrunid)
);

alter table xlsys_transportrun comment '数据传输运行表';

/*==============================================================*/
/* Table: xlsys_user                                            */
/*==============================================================*/
create table xlsys_user
(
   userid               varchar(256) not null,
   name                 varchar(64) not null,
   password             varchar(255),
   primary key (userid)
);

alter table xlsys_user comment 'user table';

/*==============================================================*/
/* Table: xlsys_useremail                                       */
/*==============================================================*/
create table xlsys_useremail
(
   userid               varchar(256) not null,
   idx                  numeric(8,0) not null,
   email                varchar(64),
   pop                  varchar(64),
   smtp                 varchar(64),
   emailuser            varchar(64),
   emailpwd             varchar(64),
   remark               varchar(4000),
   header               longblob comment 'Email的抬头',
   footer               longblob comment 'Email的签名',
   primary key (userid, idx)
);

alter table xlsys_useremail comment '用户email配置';

/*==============================================================*/
/* Table: xlsys_view                                            */
/*==============================================================*/
create table xlsys_view
(
   viewid               numeric(8,0) not null,
   name                 varchar(64),
   viewtype             numeric(2,0),
   param                varchar(4000),
   relationtype         numeric(2,0),
   mainviewid           numeric(8,0),
   jslistener           longblob,
   javalistener         varchar(1000),
   selectbody           varchar(1000),
   frombody             varchar(1000),
   wherebody            varchar(1000),
   groupbybody          varchar(1000),
   orderbybody          varchar(1000),
   wholesql             varchar(1000),
   treecolname          varchar(64),
   primary key (viewid)
);

alter table xlsys_view comment 'Define the composite
select ''insert into XLSYS_VIEW (';

/*==============================================================*/
/* Table: xlsys_viewdetail                                      */
/*==============================================================*/
create table xlsys_viewdetail
(
   viewid               numeric(8,0) not null,
   idx                  numeric(8,0) not null,
   colname              varchar(64),
   name                 varchar(64),
   colgroup             varchar(64),
   colgroupname         varchar(64),
   datatype             numeric(2,0),
   type                 numeric(2,0),
   defaultvalue         varchar(256),
   supportvalue         varchar(4000),
   aggregation          numeric(2,0),
   relationcolname      varchar(64),
   sqlexp               varchar(4000),
   showninphoneoverview numeric(1,0),
   showninphonedetail   numeric(1,0),
   primary key (viewid, idx)
);

alter table xlsys_viewdetail comment 'Detail of view';

/*==============================================================*/
/* Index: un_vd                                                 */
/*==============================================================*/
create unique index un_vd on xlsys_viewdetail
(
   viewid,
   colname
);

/*==============================================================*/
/* Table: xlsys_viewdetailparam                                 */
/*==============================================================*/
create table xlsys_viewdetailparam
(
   viewid               numeric(8,0) not null,
   idx                  numeric(8,0) not null,
   attrname             varchar(1000) not null,
   attrvalue            varchar(4000),
   primary key (viewid, idx, attrname)
);

/*==============================================================*/
/* Table: xlsys_viewqueryparam                                  */
/*==============================================================*/
create table xlsys_viewqueryparam
(
   viewid               numeric(8,0) not null,
   idx                  numeric(8,0) not null,
   colname              varchar(64),
   name                 varchar(64),
   datatype             numeric(2,0),
   type                 numeric(2,0),
   param                varchar(4000),
   defaultvalue         varchar(256),
   supportvalue         varchar(4000),
   showninphone         numeric(1,0),
   primary key (viewid, idx)
);

alter table xlsys_viewqueryparam comment 'Query parameter for view';

/*==============================================================*/
/* Table: xlsys_wdtcolumn                                       */
/*==============================================================*/
create table xlsys_wdtcolumn
(
   wizardid             varchar(256) not null,
   dtidx                numeric(8,0) not null,
   idx                  numeric(8,0) not null,
   colname              varchar(64),
   name                 varchar(64),
   forceinput           numeric(2,0),
   tooltip              varchar(256),
   primary key (wizardid, dtidx, idx)
);

alter table xlsys_wdtcolumn comment '向导页对应字段';

/*==============================================================*/
/* Table: xlsys_wizard                                          */
/*==============================================================*/
create table xlsys_wizard
(
   wizardid             varchar(256) not null,
   name                 varchar(64),
   javalistener         varchar(4000),
   jslistener           longblob,
   primary key (wizardid)
);

alter table xlsys_wizard comment '雪狼系统向导主表';

/*==============================================================*/
/* Table: xlsys_wizarddetail                                    */
/*==============================================================*/
create table xlsys_wizarddetail
(
   wizardid             varchar(256) not null,
   idx                  numeric(8,0) not null,
   viewid               numeric(8,0) comment '对应视图ID',
   title                varchar(256),
   message              varchar(4000),
   needsave             numeric(2,0),
   nextidx              numeric(8,0) comment '下一步序号',
   primary key (wizardid, idx)
);

alter table xlsys_wizarddetail comment '雪狼系统向导明细表';

alter table xlem_buyer add constraint FK_BR_REFERENCE_U foreign key (userid)
      references xlem_user (userid) on delete restrict on update restrict;

alter table xlem_buyer add constraint FK_BR_REFERENCE_UL foreign key (levelid)
      references xlem_userlevel (levelid) on delete restrict on update restrict;

alter table xlem_item add constraint FK_I_REFERENCE_SL foreign key (sellerid)
      references xlem_seller (sellerid) on delete restrict on update restrict;

alter table xlem_itemsku add constraint FK_ISKU_REFERENCE_I foreign key (itemid)
      references xlem_item (itemid) on delete restrict on update restrict;

alter table xlem_itemsku add constraint FK_ISKU_REFERENCE_SKU foreign key (sku)
      references xlem_sku (sku) on delete restrict on update restrict;

alter table xlem_seller add constraint FK_SL_REFERENCE_U foreign key (userid)
      references xlem_user (userid) on delete restrict on update restrict;

alter table xlem_seller add constraint FK_SL_REFERENCE_UL foreign key (levelid)
      references xlem_userlevel (levelid) on delete restrict on update restrict;

alter table xlem_sku add constraint FK_SKU_REFERENCE_AUNIT foreign key (aunitid)
      references xlem_atomicunit (aunitid) on delete restrict on update restrict;

alter table xlem_sku add constraint FK_SKU_REFERENCE_SPU foreign key (spu)
      references xlem_spu (spu) on delete restrict on update restrict;

alter table xlem_spu add constraint FK_SPU_REFERENCE_SPUC foreign key (categoryid)
      references xlem_spucategory (categoryid) on delete restrict on update restrict;

alter table xlem_stock add constraint FK_STK_REFERENCE_SKU foreign key (sku)
      references xlem_sku (sku) on delete restrict on update restrict;

alter table xlem_stockhistory add constraint FK_STKH_REFERENCE_SKU foreign key (sku)
      references xlem_sku (sku) on delete restrict on update restrict;

alter table xlem_unit add constraint FK_UNIT_REFERENCE_AUNIT foreign key (aunitid)
      references xlem_atomicunit (aunitid) on delete restrict on update restrict;

alter table xlem_userlevel add constraint FK_UL_REFERENCE_UL foreign key (nextlevel)
      references xlem_userlevel (levelid) on delete restrict on update restrict;

alter table xlfin_accountcondition add constraint FK_AC_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid) on delete restrict on update restrict;

alter table xlfin_balance add constraint FK_B_REFERENCE_A foreign key (accid)
      references xlfin_account (accid) on delete restrict on update restrict;

alter table xlfin_balance add constraint FK_B_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid) on delete restrict on update restrict;

alter table xlfin_balance add constraint FK_B_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid) on delete restrict on update restrict;

alter table xlfin_bankstmt add constraint FK_BS_REFERENCE_A foreign key (accid)
      references xlfin_account (accid) on delete restrict on update restrict;

alter table xlfin_bankstmt add constraint FK_BS_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid) on delete restrict on update restrict;

alter table xlfin_bankstmt add constraint FK_BS_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid) on delete restrict on update restrict;

alter table xlfin_bankstmt add constraint FK_BS_REFERENCE_U foreign key (userid)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlfin_bankstmt add constraint FK_BS_REFERENCE_VD foreign key (vid, vidx)
      references xlfin_voucherdetail (voucherid, idx) on delete restrict on update restrict;

alter table xlfin_bankstmtbalance add constraint FK_BSB_REFERENCE_A foreign key (accid)
      references xlfin_account (accid) on delete restrict on update restrict;

alter table xlfin_bankstmtbalance add constraint FK_BSB_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid) on delete restrict on update restrict;

alter table xlfin_bankstmtbalance add constraint FK_BSB_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid) on delete restrict on update restrict;

alter table xlfin_bstldt add constraint FK_BSTLDT_REFERENCE_BSTL foreign key (bstlid)
      references xlfin_bankstmttemplet (bstlid) on delete restrict on update restrict;

alter table xlfin_exchangerate add constraint FK_ER_REFERENCE_C foreign key (crcid)
      references xlfin_currency (crcid) on delete restrict on update restrict;

alter table xlfin_karaccdt add constraint FK_KARADT_REFERENCE_KAR foreign key (karid)
      references xlfin_kdeptaccrealtion (karid) on delete restrict on update restrict;

alter table xlfin_kardt add constraint FK_KARDT_REFERENCE_KAR foreign key (karid)
      references xlfin_kdeptaccrealtion (karid) on delete restrict on update restrict;

alter table xlfin_kardt add constraint FK_KARDT_REFERENCE_VDTECA foreign key (vdtecaid)
      references xlfin_vdtextracolattr (vdtecaid) on delete restrict on update restrict;

alter table xlfin_keepdept add constraint FK_KD_REFERENCE_C1 foreign key (standardcrcid)
      references xlfin_currency (crcid) on delete restrict on update restrict;

alter table xlfin_keepdept add constraint FK_KD_REFERENCE_C2 foreign key (reportcrcid)
      references xlfin_currency (crcid) on delete restrict on update restrict;

alter table xlfin_keepdept add constraint FK_KD_REFERENCE_KDAR1 foreign key (usedkarid)
      references xlfin_kdeptaccrealtion (karid) on delete restrict on update restrict;

alter table xlfin_keepdept add constraint FK_KD_REFERENCE_KDAR2 foreign key (nocarryoverkarid)
      references xlfin_kdeptaccrealtion (karid) on delete restrict on update restrict;

alter table xlfin_keepdept add constraint FK_KD_REFERENCE_KDAR3 foreign key (cockarid)
      references xlfin_kdeptaccrealtion (karid) on delete restrict on update restrict;

alter table xlfin_reportdata add constraint FK_RD_REFERENCE_RDEPT foreign key (rdeptid)
      references xlfin_reportdept (rdeptid) on delete restrict on update restrict;

alter table xlfin_reportdata add constraint FK_RD_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid) on delete restrict on update restrict;

alter table xlfin_reportdatadetail add constraint FK_RDD_REFERENCE_RD foreign key (rdid)
      references xlfin_reportdata (rdid) on delete restrict on update restrict;

alter table xlfin_reportformcol add constraint FK_RFC_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid) on delete restrict on update restrict;

alter table xlfin_reportformformula add constraint FK_RFF_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid) on delete restrict on update restrict;

alter table xlfin_reportformrow add constraint FK_RFR_REFERENCE_RF foreign key (rfid)
      references xlfin_reportform (rfid) on delete restrict on update restrict;

alter table xlfin_vdtextra add constraint FK_VDTE_REFERENCE_VDT foreign key (voucherid, idx)
      references xlfin_voucherdetail (voucherid, idx) on delete restrict on update restrict;

alter table xlfin_vdtextra add constraint FK_VDTE_REFERENCE_VEC foreign key (extracol)
      references xlfin_vdtextracol (extracol) on delete restrict on update restrict;

alter table xlfin_vdtextracolattr add constraint FK_VDTECA_REFERENCE_VDTEC foreign key (extracol)
      references xlfin_vdtextracol (extracol) on delete restrict on update restrict;

alter table xlfin_voucher add constraint FK_V_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlfin_voucher add constraint FK_V_REFERENCE_ID foreign key (id)
      references xlsys_identity (id) on delete restrict on update restrict;

alter table xlfin_voucher add constraint FK_V_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid) on delete restrict on update restrict;

alter table xlfin_voucher add constraint FK_V_REFERENCE_U1 foreign key (creater)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlfin_voucher add constraint FK_V_REFERENCE_U2 foreign key (accounter)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlfin_voucherdetail add constraint FK_VD_REFERENCE_A foreign key (accid)
      references xlfin_account (accid) on delete restrict on update restrict;

alter table xlfin_voucherdetail add constraint FK_VD_REFERENCE_BS foreign key (bsid)
      references xlfin_bankstmt (bsid) on delete restrict on update restrict;

alter table xlfin_voucherdetail add constraint FK_VD_REFERENCE_C foreign key (fcrcid)
      references xlfin_currency (crcid) on delete restrict on update restrict;

alter table xlfin_voucherdetail add constraint FK_VDT_REFERENCE_KD foreign key (kdeptid)
      references xlfin_keepdept (kdeptid) on delete restrict on update restrict;

alter table xlfin_voucherdetail add constraint FK_VDT_REFERENCE_U foreign key (userid)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlfin_voucherdetail add constraint FK_VOD_REFERENCE_VO foreign key (voucherid)
      references xlfin_voucher (voucherid) on delete restrict on update restrict;

alter table xlfin_vtempletdt add constraint FK_VTLDT_REFERENCE_VTL foreign key (vtlid)
      references xlfin_vouchertemplet (vtlid) on delete restrict on update restrict;

alter table xlfin_vtldtcol add constraint FK_VTLDTC_REFERENCE_VTLDT foreign key (vtldtidx, vtlid)
      references xlfin_vtempletdt (idx, vtlid) on delete restrict on update restrict;

alter table xlsys_authorisedright add constraint FK_AR_REFERENCE_AD foreign key (arid, ardtidx)
      references xlsys_authorizedetail (arid, idx) on delete restrict on update restrict;

alter table xlsys_authorisedright add constraint FK_AR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype) on delete restrict on update restrict;

alter table xlsys_authorize add constraint FK_A_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlsys_authorize add constraint FK_A_REFERENCE_I1 foreign key (id)
      references xlsys_identity (id) on delete restrict on update restrict;

alter table xlsys_authorize add constraint FK_A_REFERENCE_I2 foreign key (authorisedid)
      references xlsys_identity (id) on delete restrict on update restrict;

alter table xlsys_authorize add constraint FK_A_REFERENCE_U foreign key (creater)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlsys_authorizedetail add constraint FK_AD_REFERENCE_A foreign key (arid)
      references xlsys_authorize (arid) on delete restrict on update restrict;

alter table xlsys_basebusi add constraint FK_BB_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlsys_basebusi add constraint FK_BB_REFERENCE_I foreign key (id)
      references xlsys_identity (id) on delete restrict on update restrict;

alter table xlsys_basebusi add constraint FK_BB_REFERENCE_U foreign key (creater)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlsys_dictdetail add constraint FK_DD_REFERENCE_D foreign key (dictid)
      references xlsys_dict (dictid) on delete restrict on update restrict;

alter table xlsys_exttableinfodetail add constraint FK_ETI_REFERENCE_ETID foreign key (tableid)
      references xlsys_exttableinfo (tableid) on delete restrict on update restrict;

alter table xlsys_flow add constraint FK_F_REFERENCE_V1 foreign key (mvidoflpart)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_flow add constraint FK_F_REFERENCE_V2 foreign key (mvidofmpart)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_flow add constraint FK_FL_REFERENCE_P1 foreign key (mainpartid)
      references xlsys_part (partid) on delete restrict on update restrict;

alter table xlsys_flow add constraint FK_FL_REFERENCE_P2 foreign key (listpartid)
      references xlsys_part (partid) on delete restrict on update restrict;

alter table xlsys_flowcodealloc add constraint FK_FCA_REFERENCE_CA foreign key (caid)
      references xlsys_codealloc (caid) on delete restrict on update restrict;

alter table xlsys_flowcodealloc add constraint FK_FCA_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlsys_flowcondition add constraint FK_FC_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlsys_flowjava add constraint FK_FJAVA_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlsys_flowjava add constraint FK_FJAVA_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_flowjs add constraint FK_FJS_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlsys_flowjs add constraint FK_FJS_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_flowlogic add constraint FK_FL_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlsys_flowpart add constraint FK_FP_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlsys_flowpart add constraint FK_FP_REFERENCE_P1 foreign key (listpartid)
      references xlsys_part (partid) on delete restrict on update restrict;

alter table xlsys_flowpart add constraint FK_FP_REFERENCE_P2 foreign key (mainpartid)
      references xlsys_part (partid) on delete restrict on update restrict;

alter table xlsys_flowpart add constraint FK_FP_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype) on delete restrict on update restrict;

alter table xlsys_flowpart add constraint FK_FP_REFERENCE_V1 foreign key (mvidoflpart)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_flowpart add constraint FK_FP_REFERENCE_V2 foreign key (mvidofmpart)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_flowright add constraint FK_FR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype) on delete restrict on update restrict;

alter table xlsys_flowright add constraint FK_FR_REFERENCE_FC foreign key (flowid, cdtidx)
      references xlsys_flowcondition (flowid, idx) on delete restrict on update restrict;

alter table xlsys_flowsubtable add constraint FK_FST_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid) on delete restrict on update restrict;

alter table xlsys_identity add constraint FK_I_REFERENCE_M foreign key (welcomemenuid)
      references xlsys_menu (menuid) on delete restrict on update restrict;

alter table xlsys_idrelation add constraint FK_IR_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype) on delete restrict on update restrict;

alter table xlsys_idrelation add constraint FK_IR_REFERENCE_I foreign key (id)
      references xlsys_identity (id) on delete restrict on update restrict;

alter table xlsys_menuright add constraint FK_MR_REFERENCE_M foreign key (menuid)
      references xlsys_menu (menuid) on delete restrict on update restrict;

alter table xlsys_oacategoryright add constraint FK_OACR_REFERENCE_OAC foreign key (oacid)
      references xlsys_oacategory (oacid) on delete restrict on update restrict;

alter table xlsys_oacmbelong add constraint FK_OACMB_REFERENCE_OAM foreign key (oamid)
      references xlsys_oamodule (oamid) on delete restrict on update restrict;

alter table xlsys_oacmbelong add constraint FK_OACMBL_REFERENCE_OAC foreign key (oacid)
      references xlsys_oacategory (oacid) on delete restrict on update restrict;

alter table xlsys_oacmrelation add constraint FK_OACMR_REFERENCE_ID foreign key (id)
      references xlsys_identity (id) on delete restrict on update restrict;

alter table xlsys_oacmrelation add constraint FK_OACMR_REFERENCE_OACMBL foreign key (oacid, oamid)
      references xlsys_oacmbelong (oacid, oamid) on delete restrict on update restrict;

alter table xlsys_oamoduleextra add constraint FK_OAME_REFERENCE_OAM foreign key (oamid)
      references xlsys_oamodule (oamid) on delete restrict on update restrict;

alter table xlsys_oamoduleextra add constraint FK_OAME_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_oamoduleright add constraint FK_OAMR_REFERENCE_OAM foreign key (oamid)
      references xlsys_oamodule (oamid) on delete restrict on update restrict;

alter table xlsys_partdetail add constraint FK_PD_REFERENCE_P foreign key (partid)
      references xlsys_part (partid) on delete restrict on update restrict;

alter table xlsys_partdetail add constraint FK_PD_REFERENCE_V1 foreign key (viewid)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_partdetail add constraint FK_PD_REFERENCE_V2 foreign key (mainviewid)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_queryparamsave add constraint FK_QPS_REFERENCE_ID foreign key (id)
      references xlsys_identity (id) on delete restrict on update restrict;

alter table xlsys_queryparamsave add constraint FK_QPS_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_ratify add constraint FK_R_REFERENCE_FC_F foreign key (fromflowid, fromcdtidx)
      references xlsys_flowcondition (flowid, idx) on delete restrict on update restrict;

alter table xlsys_ratify add constraint FK_R_REFERENCE_FC_T foreign key (toflowid, tocdtidx)
      references xlsys_flowcondition (flowid, idx) on delete restrict on update restrict;

alter table xlsys_ratify add constraint FK_R_REFERENCE_U foreign key (fromuserid)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlsys_ratifydetail add constraint FK_RD_REFERENCE_R foreign key (rtfid)
      references xlsys_ratify (rtfid) on delete restrict on update restrict;

alter table xlsys_ratifydetail add constraint FK_RD_REFERENCE_U1 foreign key (touserid)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlsys_ratifydetail add constraint FK_RD_REFERENCE_U2 foreign key (replaceuserid)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlsys_transportdetail add constraint FK_TSDT_REFERENCE_TS foreign key (tsid)
      references xlsys_transport (tsid) on delete restrict on update restrict;

alter table xlsys_transportdtcolmap add constraint FK_TSDTCM_REFERENCE_TSDT foreign key (tsid, tsdtidx)
      references xlsys_transportdetail (tsid, idx) on delete restrict on update restrict;

alter table xlsys_transportkeysynonym add constraint FK_TSKS_REFERENCE_TSK foreign key (tskeyid)
      references xlsys_transportkey (tskeyid) on delete restrict on update restrict;

alter table xlsys_transportmap add constraint FK_TSK_REFERENCE_TSM foreign key (tskeyid)
      references xlsys_transportkey (tskeyid) on delete restrict on update restrict;

alter table xlsys_transportrun add constraint FK_TSR_REFERENCE_TS foreign key (tsid)
      references xlsys_transport (tsid) on delete restrict on update restrict;

alter table xlsys_useremail add constraint FK_UE_REFERENCE_U foreign key (userid)
      references xlsys_user (userid) on delete restrict on update restrict;

alter table xlsys_viewdetail add constraint FK_VD_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_viewdetailparam add constraint FK_VDP_REFERENCE_VD foreign key (viewid, idx)
      references xlsys_viewdetail (viewid, idx) on delete restrict on update restrict;

alter table xlsys_viewqueryparam add constraint FK_VQP_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_wdtcolumn add constraint FK_WDC_REFERENCE_WD foreign key (wizardid, dtidx)
      references xlsys_wizarddetail (wizardid, idx) on delete restrict on update restrict;

alter table xlsys_wizarddetail add constraint FK_WD_REFERENCE_V foreign key (viewid)
      references xlsys_view (viewid) on delete restrict on update restrict;

alter table xlsys_wizarddetail add constraint FK_WD_REFERENCE_W1 foreign key (wizardid)
      references xlsys_wizard (wizardid) on delete restrict on update restrict;

