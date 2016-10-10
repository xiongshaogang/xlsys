/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/10/8 16:08:16                           */
/*==============================================================*/


drop table xlsys_template cascade constraints;

/*==============================================================*/
/* Table: xlsys_template                                      */
/*==============================================================*/
create table xlsys_template 
(
   templateid         VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   templatepath       VARCHAR2(1000),
   template           BLOB,
   dispatchpath       VARCHAR2(1000),
   javalistener       VARCHAR2(1000),
   jslistener         BLOB,
   constraint PK_XLSYS_TEMPLATE primary key (templateid)
);

