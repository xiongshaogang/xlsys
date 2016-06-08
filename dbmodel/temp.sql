/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/6/3 17:38:49                            */
/*==============================================================*/


alter table xlv2_flowcodealloc
   drop constraint FKV2_FCA_REFERENCE_CA;

alter table xlv2_flowcodealloc
   drop constraint FKV2_FCA_REFERENCE_F;

drop table xlv2_codealloc cascade constraints;

drop table xlv2_flowcodealloc cascade constraints;

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

alter table xlv2_flowcodealloc
   add constraint FKV2_FCA_REFERENCE_CA foreign key (caid)
      references xlv2_codealloc (caid);

alter table xlv2_flowcodealloc
   add constraint FKV2_FCA_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

