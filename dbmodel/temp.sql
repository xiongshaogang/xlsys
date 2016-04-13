/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/4/12 17:11:38                           */
/*==============================================================*/


alter table xlv2_framedetailparam
   drop constraint FKV2_FDP_REFERENCE_FD;

drop table xlv2_framedetailparam cascade constraints;

/*==============================================================*/
/* Table: xlv2_framedetailparam                               */
/*==============================================================*/
create table xlv2_framedetailparam 
(
   frameid            NUMBER(8,0)          not null,
   fdtid              VARCHAR2(256)        not null,
   attrname           VARCHAR2(512)        not null,
   attrvalue          VARCHAR2(512),
   constraint PK_XLV2_FRAMEDETAILPARAM primary key (frameid, fdtid)
);

comment on table xlv2_framedetailparam is
'雪狼2.x系统框架明细参数';

alter table xlv2_framedetailparam
   add constraint FKV2_FDP_REFERENCE_FD foreign key (frameid, fdtid)
      references xlv2_framedetail (frameid, fdtid);

