/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/9/13 10:08:29                           */
/*==============================================================*/


drop table xlsys_bufferinfo cascade constraints;

/*==============================================================*/
/* Table: xlsys_bufferinfo                                    */
/*==============================================================*/
create table xlsys_bufferinfo 
(
   buffername         VARCHAR2(256)        not null,
   version            NUMBER(8,0)          not null,
   constraint PK_XLSYS_BUFFERINFO primary key (buffername)
);

comment on table xlsys_bufferinfo is
'雪狼系统缓存信息表';

