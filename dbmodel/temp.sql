/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/5/10 17:26:40                           */
/*==============================================================*/


drop table xlv2_dialog cascade constraints;

/*==============================================================*/
/* Table: xlv2_dialog                                         */
/*==============================================================*/
create table xlv2_dialog 
(
   dialogid           NUMBER(8,0)          not null,
   name               VARCHAR2(256),
   impl               VARCHAR2(512),
   constraint PK_XLV2_DIALOG primary key (dialogid)
);

comment on table xlv2_dialog is
'雪狼系统2.x对话框配置';

