/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2016/6/1 16:19:15                            */
/*==============================================================*/


alter table xlv2_flowcondition
   drop constraint FKV2_FC_REFERENCE_F;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_F1;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_F2;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_FLOW;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_R;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_V1;

alter table xlv2_flowframe
   drop constraint FKV2_FF_REFERENCE_V2;

alter table xlv2_flowlogic
   drop constraint FKV2_FL_REFERENCE_F;

alter table xlv2_flowright
   drop constraint FKV2_FR_REFERENCE_FC;

alter table xlv2_flowsubtable
   drop constraint FKV2_FST_REFERENCE_F;

alter table xlv2_flowviewlistener
   drop constraint FKV2_FVL_REFERENCE_F;

alter table xlv2_flowviewlistener
   drop constraint FKV2_FVL_REFERENCE_V;

drop table xlv2_flow cascade constraints;

drop table xlv2_flowcondition cascade constraints;

drop table xlv2_flowframe cascade constraints;

drop table xlv2_flowlogic cascade constraints;

drop table xlv2_flowright cascade constraints;

drop table xlv2_flowsubtable cascade constraints;

drop table xlv2_flowviewlistener cascade constraints;

/*==============================================================*/
/* Table: xlv2_flow                                           */
/*==============================================================*/
create table xlv2_flow 
(
   flowid             VARCHAR2(256)        not null,
   name               VARCHAR2(64),
   maintable          VARCHAR2(64),
   innercodecol       VARCHAR2(64),
   outtercodecol      VARCHAR2(64),
   innerlisteners     VARCHAR2(4000),
   scriptlistener     BLOB,
   versionupdate      NUMBER(2,0),
   cancopy            NUMBER(2,0),
   constraint PK_XLV2_FLOW primary key (flowid)
);

comment on table xlv2_flow is
'雪狼系统2.x流程定义主表';

comment on column xlv2_flow.versionupdate is
'是否允许版本更新';

/*==============================================================*/
/* Table: xlv2_flowcondition                                  */
/*==============================================================*/
create table xlv2_flowcondition 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   condition          VARCHAR2(64)         not null,
   name               VARCHAR2(256),
   audittype          NUMBER(2,0),
   voterate           NUMBER(18,6),
   constraint PK_XLV2_FLOWCONDITION primary key (flowid, idx)
);

comment on table xlv2_flowcondition is
'The condition of flow';

comment on column xlv2_flowcondition.audittype is
'审批类型, 0:单审;1:会审;2:组单审;3:组会审;4:投票审
[单审] : 任意一个人通过即可通过(提交时允许选择审批人)
[会审] : 所有人通过才可通过(提交时不允许选择审批人)
[组单审] : 任意一组人通过即可通过, 同一组里的人必须全部通过才算通过(提交时允许选择审批组，不允许选择审批人)
[组会审] : 所有组的人都通过才可通过, 同一组里的人只有要任意一个人通过就算通过(提交时允许选择审批人，但是每个组都必须选择至少一个审批人)
[投票审] : 按照一定比例票数通过后即可通过(提交时不允许选择)';

comment on column xlv2_flowcondition.voterate is
'投票率，当audittype为4:投票审时，此参数有效';

/*==============================================================*/
/* Table: xlv2_flowframe                                      */
/*==============================================================*/
create table xlv2_flowframe 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   platform           NUMBER(2,0),
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   listframeid        NUMBER(8,0),
   mainviewidinlistframe NUMBER(8,0),
   mainframeid        NUMBER(8,0),
   mainviewidinmainframe NUMBER(8,0),
   constraint PK_XLV2_FLOWFRAME primary key (flowid, idx)
);

/*==============================================================*/
/* Table: xlv2_flowlogic                                      */
/*==============================================================*/
create table xlv2_flowlogic 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   fromcondition      VARCHAR2(64),
   tocondition        VARCHAR2(64),
   passtype           NUMBER(2,0),
   rejecttype         NUMBER(2,0),
   canrejectto        VARCHAR2(64),
   clientautotriggersubmit NUMBER(2,0),
   constraint PK_XLV2_FLOWLOGIC primary key (flowid, idx)
);

comment on table xlv2_flowlogic is
'The logic of each flow';

comment on column xlv2_flowlogic.passtype is
'0:手动;1:自动提交;2:自动审批;3:自动审批并自动提交';

comment on column xlv2_flowlogic.rejecttype is
'驳回类型,0:不允许驳回;1:可驳回到上一级;2:可驳回到任意上级;3:只允许驳回到自定义上级';

comment on column xlv2_flowlogic.clientautotriggersubmit is
'是否允许客户端自动触发提交按钮';

/*==============================================================*/
/* Table: xlv2_flowright                                      */
/*==============================================================*/
create table xlv2_flowright 
(
   flowid             VARCHAR2(256)        not null,
   cdtidx             NUMBER(8,0)          not null,
   idx                NUMBER(8,0)          not null,
   belongrighttype    NUMBER(2,0),
   belongrightvalue   VARCHAR2(256),
   righttype          NUMBER(2,0),
   rightvalue         VARCHAR2(256),
   groupnm            VARCHAR2(256),
   conditiongrp       VARCHAR2(32),
   constraint PK_XLV2_FLOWRIGHT primary key (flowid, cdtidx, idx)
);

comment on table xlv2_flowright is
'The right of each flow condition';

comment on column xlv2_flowright.belongrighttype is
'0:identity;1:user;2:department;3:position';

comment on column xlv2_flowright.righttype is
'0:identity;1:user;2:department;3:position';

comment on column xlv2_flowright.groupnm is
'分组名称,当audittype选用 2:组单审和 3:组会审 时有效，用来标识当前权限为哪个组别所有，分组名称相同的视为同一组';

comment on column xlv2_flowright.conditiongrp is
'条件组, 组名一样的权限配置将使用and来连接, 否则使用or连接';

/*==============================================================*/
/* Table: xlv2_flowsubtable                                   */
/*==============================================================*/
create table xlv2_flowsubtable 
(
   flowid             VARCHAR2(256)        not null,
   tablename          VARCHAR2(64)         not null,
   relationinnercol   VARCHAR2(64),
   constraint PK_XLV2_FLOWSUBTABLE primary key (flowid, tablename)
);

comment on table xlv2_flowsubtable is
'流程子表配置';

/*==============================================================*/
/* Table: xlv2_flowviewlistener                               */
/*==============================================================*/
create table xlv2_flowviewlistener 
(
   flowid             VARCHAR2(256)        not null,
   idx                NUMBER(8,0)          not null,
   viewid             NUMBER(8,0),
   innerlisteners     VARCHAR2(4000),
   scriptlistener     BLOB,
   constraint PK_XLV2_FLOWVIEWLISTENER primary key (flowid, idx)
);

comment on table xlv2_flowviewlistener is
'Listener for view of flow';

alter table xlv2_flowcondition
   add constraint FKV2_FC_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_F1 foreign key (listframeid)
      references xlv2_frame (frameid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_F2 foreign key (mainframeid)
      references xlv2_frame (frameid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_FLOW foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_R foreign key (righttype)
      references xlsys_right (righttype);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_V1 foreign key (mainviewidinlistframe)
      references xlv2_view (viewid);

alter table xlv2_flowframe
   add constraint FKV2_FF_REFERENCE_V2 foreign key (mainviewidinmainframe)
      references xlv2_view (viewid);

alter table xlv2_flowlogic
   add constraint FKV2_FL_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowright
   add constraint FKV2_FR_REFERENCE_FC foreign key (flowid, cdtidx)
      references xlv2_flowcondition (flowid, idx);

alter table xlv2_flowsubtable
   add constraint FKV2_FST_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowviewlistener
   add constraint FKV2_FVL_REFERENCE_F foreign key (flowid)
      references xlv2_flow (flowid);

alter table xlv2_flowviewlistener
   add constraint FKV2_FVL_REFERENCE_V foreign key (viewid)
      references xlv2_view (viewid);

