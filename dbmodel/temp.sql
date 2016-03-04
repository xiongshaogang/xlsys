/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/3/4 18:10:33                            */
/*==============================================================*/


alter table xlsys_viewdetailparam
   drop constraint FK_VDP_REFERENCE_VD;

drop table xlsys_viewdetailparam cascade constraints;

/*==============================================================*/
/* Table: xlsys_viewdetailparam                               */
/*==============================================================*/
create table xlsys_viewdetailparam 
(
   viewid             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   attrname           VARCHAR2(4000)       not null,
   attrvalue          VARCHAR2(4000),
   constraint PK_XLSYS_VIEWDETAILPARAM primary key (viewid, idx, attrname)
);

alter table xlsys_viewdetailparam
   add constraint FK_VDP_REFERENCE_VD foreign key (viewid, idx)
      references xlsys_viewdetail (viewid, idx);

