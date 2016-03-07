/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/3/7 16:13:44                            */
/*==============================================================*/


alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_F;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_P1;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_P2;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_R;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_V1;

alter table xlsys_flowpart
   drop constraint FK_FP_REFERENCE_V2;

drop table xlsys_flowpart cascade constraints;

/*==============================================================*/
/* Table: xlsys_flowpart                                      */
/*==============================================================*/
create table xlsys_flowpart 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   clienttype         VARCHAR2(32),
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   listpartid         VARCHAR2(256),
   mvidoflpart        NUMBER(8,0),
   mainpartid         VARCHAR2(256),
   mvidofmpart        NUMBER(8,0),
   constraint PK_XLSYS_FLOWPART primary key (flowid, idx)
);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_F foreign key (flowid)
      references xlsys_flow (flowid);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_P1 foreign key (listpartid)
      references xlsys_part (partid);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_P2 foreign key (mainpartid)
      references xlsys_part (partid);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_V1 foreign key (mvidoflpart)
      references xlsys_view (viewid);

alter table xlsys_flowpart
   add constraint FK_FP_REFERENCE_V2 foreign key (mvidofmpart)
      references xlsys_view (viewid);

