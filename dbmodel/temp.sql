/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/7/13 9:22:02                            */
/*==============================================================*/


alter table xlsys_iddetail
   drop constraint FK_XLSYS_ID_REFERENCE_XLSYS_ID;

alter table xlsys_iddetail
   drop constraint FKV2_ID_REFERENCE_F;

drop table xlsys_iddetail cascade constraints;

/*==============================================================*/
/* Table: xlsys_iddetail                                      */
/*==============================================================*/
create table xlsys_iddetail 
(
   id                 VARCHAR2(256)        not null,
   platform           NUMBER(2,0)          not null,
   frameid            NUMBER(8,0),
   constraint PK_XLSYS_IDDETAIL primary key (id, platform)
);

alter table xlsys_iddetail
   add constraint FK_XLSYS_ID_REFERENCE_XLSYS_ID foreign key (id)
      references xlsys_identity (id);

alter table xlsys_iddetail
   add constraint FKV2_ID_REFERENCE_F foreign key (frameid)
      references xlv2_frame (frameid);

