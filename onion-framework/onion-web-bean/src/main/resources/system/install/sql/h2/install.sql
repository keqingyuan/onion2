CREATE TABLE "S_USER_PROFILE" (
  "U_ID"    VARCHAR2(32)  NOT NULL,
  "CONTENT" CLOB          NOT NULL,
  "TYPE"    VARCHAR2(512) NULL,
  "USER_ID" VARCHAR2(32)  NULL
);
COMMENT ON TABLE "S_USER_PROFILE" IS '用户配置表';
COMMENT ON COLUMN "S_USER_PROFILE"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_USER_PROFILE"."CONTENT" IS '配置内容';
COMMENT ON COLUMN "S_USER_PROFILE"."TYPE" IS '类型';
COMMENT ON COLUMN "S_USER_PROFILE"."USER_ID" IS '用户ID';


CREATE TABLE "S_CONFIG" (
  "U_ID"          VARCHAR2(32)  NOT NULL,
  "CONTENT"       CLOB          NOT NULL,
  "REMARK"        VARCHAR2(512) NULL,
  "CREATE_DATE"   DATETIME      NOT NULL,
  "CLASSIFIED_ID" VARCHAR2(32)  NULL,
  "UPDATE_DATE"   DATETIME      NULL
);
COMMENT ON TABLE "S_CONFIG" IS '系统配置文件表';
COMMENT ON COLUMN "S_CONFIG"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_CONFIG"."CONTENT" IS '配置内容';
COMMENT ON COLUMN "S_CONFIG"."REMARK" IS '备注';
COMMENT ON COLUMN "S_CONFIG"."CLASSIFIED_ID" IS '分类ID';
COMMENT ON COLUMN "S_CONFIG"."CREATE_DATE" IS '创建日期';
COMMENT ON COLUMN "S_CONFIG"."UPDATE_DATE" IS '修改日期';
CREATE TABLE "S_FORM" (
  "U_ID"          VARCHAR2(32)  NOT NULL,
  "CLASSIFIED_ID" VARCHAR2(32),
  "NAME"          VARCHAR2(256) NOT NULL,
  "HTML"          CLOB          NULL,
  "META"          CLOB          NULL,
  "CONFIG"        CLOB          NULL,
  "VERSION"       NUMBER(32)    NULL,
  "REVISION"      NUMBER(32)    NULL,
  "RELEASE"       NUMBER(32)    NULL,
  "USING"         NUMBER(4)     NULL,
  "CREATE_DATE"   DATETIME      NOT NULL,
  "UPDATE_DATE"   DATETIME      NULL,
  "REMARK"        VARCHAR2(200) NULL
);
COMMENT ON COLUMN "S_FORM"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_FORM"."CLASSIFIED_ID" IS '分类ID';
COMMENT ON COLUMN "S_FORM"."NAME" IS '名称';
COMMENT ON COLUMN "S_FORM"."HTML" IS 'HTML内容';
COMMENT ON COLUMN "S_FORM"."META" IS '结构定义';
COMMENT ON COLUMN "S_FORM"."CONFIG" IS '配置';
COMMENT ON COLUMN "S_FORM"."VERSION" IS '版本';
COMMENT ON COLUMN "S_FORM"."REVISION" IS '修订版';
COMMENT ON COLUMN "S_FORM"."RELEASE" IS '当前发布版本';
COMMENT ON COLUMN "S_FORM"."USING" IS '是否使用中';
COMMENT ON COLUMN "S_FORM"."CREATE_DATE" IS '创建日期';
COMMENT ON COLUMN "S_FORM"."UPDATE_DATE" IS '修改日期';
CREATE TABLE "S_TEMPLATE" (
  "U_ID"          VARCHAR2(32)  NOT NULL,
  "NAME"          VARCHAR2(256) NOT NULL,
  "TEMPLATE"      CLOB          NULL,
  "CLASSIFIED_ID" VARCHAR2(32)  NULL,
  "TYPE"          VARCHAR2(64)  NULL,
  "SCRIPT"        CLOB          NULL,
  "CSS"           CLOB          NULL,
  "CSS_LINKS"     CLOB          NULL,
  "SCRIPT_LINKS"  CLOB          NULL,
  "VERSION"       NUMBER(32)    NULL,
  "REVISION"      NUMBER(32)    NULL,
  "RELEASE"       NUMBER(32)    NULL,
  "USING"         NUMBER(4)     NULL,
  "REMARK"        VARCHAR2(200) NULL
);
COMMENT ON COLUMN "S_TEMPLATE"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_TEMPLATE"."NAME" IS '名称';
COMMENT ON COLUMN "S_TEMPLATE"."TEMPLATE" IS '模板';
COMMENT ON COLUMN "S_TEMPLATE"."CLASSIFIED_ID" IS '分类';
COMMENT ON COLUMN "S_TEMPLATE"."TYPE" IS '类型';
COMMENT ON COLUMN "S_TEMPLATE"."SCRIPT" IS '脚本';
COMMENT ON COLUMN "S_TEMPLATE"."CSS" IS '样式';
COMMENT ON COLUMN "S_TEMPLATE"."CSS_LINKS" IS '样式链接';
COMMENT ON COLUMN "S_TEMPLATE"."SCRIPT_LINKS" IS '脚本链接';
COMMENT ON COLUMN "S_TEMPLATE"."VERSION" IS '版本';
COMMENT ON COLUMN "S_TEMPLATE"."REVISION" IS '修订版';
COMMENT ON COLUMN "S_TEMPLATE"."RELEASE" IS '当前发布版本';
COMMENT ON COLUMN "S_TEMPLATE"."USING" IS '是否使用中';
CREATE TABLE "S_MODULES" (
  "U_ID"       VARCHAR2(32)   NOT NULL,
  "NAME"       VARCHAR2(256)  NOT NULL,
  "URI"        VARCHAR2(1024) NULL,
  "ICON"       VARCHAR2(256)  NULL,
  "PARENT_ID"  VARCHAR2(256)  NOT NULL,
  "REMARK"     VARCHAR2(512)  NULL,
  "STATUS"     NUMBER(4)      NULL,
  "OPTIONAL"   CLOB           NOT NULL,
  "SORT_INDEX" NUMBER(32)     NOT NULL
);
COMMENT ON TABLE "S_MODULES" IS '系统模块';
COMMENT ON COLUMN "S_MODULES"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_MODULES"."NAME" IS '模块名称';
COMMENT ON COLUMN "S_MODULES"."URI" IS 'URI';
COMMENT ON COLUMN "S_MODULES"."ICON" IS '图标';
COMMENT ON COLUMN "S_MODULES"."PARENT_ID" IS '上级菜单';
COMMENT ON COLUMN "S_MODULES"."REMARK" IS '备注';
COMMENT ON COLUMN "S_MODULES"."STATUS" IS '状态';
COMMENT ON COLUMN "S_MODULES"."OPTIONAL" IS '可选权限';
COMMENT ON COLUMN "S_MODULES"."SORT_INDEX" IS '排序';
CREATE TABLE "S_MODULE_META" (
  "U_ID"      VARCHAR2(32)   NOT NULL,
  "KEY"       VARCHAR2(256)  NOT NULL,
  "MODULE_ID" VARCHAR2(32)   NOT NULL,
  "ROLE_ID"   VARCHAR2(32)   NULL,
  "REMARK"    VARCHAR2(1024) NULL,
  "META"      CLOB           NULL,
  "STATUS"    NUMBER(4)      NULL
);
COMMENT ON TABLE "S_MODULE_META" IS '系统模块配置';
COMMENT ON COLUMN "S_MODULE_META"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_MODULE_META"."KEY" IS '名称';
COMMENT ON COLUMN "S_MODULE_META"."MODULE_ID" IS '模块ID';
COMMENT ON COLUMN "S_MODULE_META"."ROLE_ID" IS '角色ID';
COMMENT ON COLUMN "S_MODULE_META"."META" IS '内容';
COMMENT ON COLUMN "S_MODULE_META"."REMARK" IS '备注';
COMMENT ON COLUMN "S_MODULE_META"."STATUS" IS '状态';
CREATE TABLE "S_RESOURCES" (
  "U_ID"        VARCHAR2(32)   NOT NULL,
  "NAME"        VARCHAR2(256)  NOT NULL,
  "PATH"        VARCHAR2(1024) NOT NULL,
  "TYPE"        VARCHAR2(256)  NOT NULL,
  "MD5"         VARCHAR2(256)  NOT NULL,
  "SIZE"        NUMBER(32)     NULL,
  "STATUS"      NUMBER(4)      NULL,
  "CLASSIFIED"  VARCHAR2(32)   NULL,
  "CREATE_DATE" DATETIME       NOT NULL,
  "CREATOR_ID"  VARCHAR2(256)  NOT NULL
);
COMMENT ON TABLE "S_RESOURCES" IS '资源表';
COMMENT ON COLUMN "S_RESOURCES"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_RESOURCES"."NAME" IS '资源名称';
COMMENT ON COLUMN "S_RESOURCES"."PATH" IS '路径';
COMMENT ON COLUMN "S_RESOURCES"."TYPE" IS '类型';
COMMENT ON COLUMN "S_RESOURCES"."MD5" IS 'MD5校验值';
COMMENT ON COLUMN "S_RESOURCES"."STATUS" IS '状态';
COMMENT ON COLUMN "S_RESOURCES"."SIZE" IS '资源大小';
COMMENT ON COLUMN "S_RESOURCES"."CLASSIFIED" IS '分类';
COMMENT ON COLUMN "S_RESOURCES"."CREATE_DATE" IS '创建时间';
COMMENT ON COLUMN "S_RESOURCES"."CREATOR_ID" IS '创建人';
CREATE TABLE "S_CLASSIFIED" (
  "U_ID"       VARCHAR2(32)   NOT NULL,
  "NAME"       VARCHAR2(256)  NOT NULL,
  "REMARK"     VARCHAR2(1024) NULL,
  "TYPE"       VARCHAR2(256)  NULL,
  "PARENT_ID"  VARCHAR2(32)   NOT NULL,
  "ICON"       VARCHAR2(256)  NULL,
  "CONFIG"     CLOB           NULL,
  "SORT_INDEX" NUMBER(32)     NULL
);
COMMENT ON TABLE "S_CLASSIFIED" IS '数据分类表';
COMMENT ON COLUMN "S_CLASSIFIED"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_CLASSIFIED"."NAME" IS '分类名称';
COMMENT ON COLUMN "S_CLASSIFIED"."REMARK" IS '备注';
COMMENT ON COLUMN "S_CLASSIFIED"."TYPE" IS '类型';
COMMENT ON COLUMN "S_CLASSIFIED"."PARENT_ID" IS '父级分类';
COMMENT ON COLUMN "S_CLASSIFIED"."ICON" IS '图标';
COMMENT ON COLUMN "S_CLASSIFIED"."CONFIG" IS '分类配置';
COMMENT ON COLUMN "S_CLASSIFIED"."SORT_INDEX" IS '排序';
CREATE TABLE "S_ROLE" (
  "U_ID"   VARCHAR2(32)  NOT NULL,
  "NAME"   VARCHAR2(256) NOT NULL,
  "TYPE"   VARCHAR2(50)  NULL,
  "REMARK" VARCHAR2(512) NULL
);
COMMENT ON TABLE "S_ROLE" IS '角色表';
COMMENT ON COLUMN "S_ROLE"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_ROLE"."NAME" IS '角色名称';
COMMENT ON COLUMN "S_ROLE"."TYPE" IS '类型';
COMMENT ON COLUMN "S_ROLE"."REMARK" IS '备注';
CREATE TABLE "S_ROLE_MODULES" (
  "U_ID"      VARCHAR2(32)  NOT NULL,
  "MODULE_ID" VARCHAR2(256) NOT NULL,
  "ROLE_ID"   VARCHAR2(256) NOT NULL,
  "ACTIONS"   CLOB          NULL
);
COMMENT ON TABLE "S_ROLE_MODULES" IS '角色模块绑定表';
COMMENT ON COLUMN "S_ROLE_MODULES"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_ROLE_MODULES"."MODULE_ID" IS '模块ID';
COMMENT ON COLUMN "S_ROLE_MODULES"."ROLE_ID" IS '角色ID';
COMMENT ON COLUMN "S_ROLE_MODULES"."ACTIONS" IS '可操作权限';
CREATE TABLE "S_SCRIPT" (
  "U_ID"          VARCHAR2(32)   NOT NULL,
  "NAME"          VARCHAR2(256)  NOT NULL,
  "CLASSIFIED_ID" VARCHAR2(1024) NOT NULL,
  "TYPE"          VARCHAR2(256)  NOT NULL,
  "CONTENT"       CLOB           NOT NULL,
  "REMARK"        VARCHAR2(512)  NULL,
  "STATUS"        NUMBER(4)      NULL
);
COMMENT ON TABLE "S_SCRIPT" IS '脚本';
COMMENT ON COLUMN "S_SCRIPT"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_SCRIPT"."NAME" IS '脚本名称';
COMMENT ON COLUMN "S_SCRIPT"."CLASSIFIED_ID" IS '路径';
COMMENT ON COLUMN "S_SCRIPT"."TYPE" IS '类型';
COMMENT ON COLUMN "S_SCRIPT"."CONTENT" IS '内容';
COMMENT ON COLUMN "S_SCRIPT"."REMARK" IS '备注';
COMMENT ON COLUMN "S_SCRIPT"."STATUS" IS '状态';
CREATE TABLE "S_USER" (
  "U_ID"        VARCHAR2(64)  NOT NULL,
  "USERNAME"    VARCHAR2(64)  NOT NULL,
  "PASSWORD"    VARCHAR2(64)  NOT NULL,
  "NAME"        VARCHAR2(64)  NULL,
  "EMAIL"       VARCHAR2(512) NULL,
  "PHONE"       VARCHAR2(64)  NULL,
  "STATUS"      NUMBER(4)     NULL,
  "CREATE_DATE" DATETIME      NOT NULL,
  "UPDATE_DATE" DATETIME      NULL
);
COMMENT ON TABLE "S_USER" IS '用户表';
COMMENT ON COLUMN "S_USER"."U_ID" IS 'ID';
COMMENT ON COLUMN "S_USER"."USERNAME" IS '用户名';
COMMENT ON COLUMN "S_USER"."PASSWORD" IS '密码';
COMMENT ON COLUMN "S_USER"."NAME" IS '姓名';
COMMENT ON COLUMN "S_USER"."EMAIL" IS '邮箱';
COMMENT ON COLUMN "S_USER"."PHONE" IS '联系电话';
COMMENT ON COLUMN "S_USER"."STATUS" IS '状态';
COMMENT ON COLUMN "S_USER"."CREATE_DATE" IS '创建日期';
COMMENT ON COLUMN "S_USER"."UPDATE_DATE" IS '修改日期';
CREATE TABLE "S_USER_ROLE" (
  "U_ID"    VARCHAR2(32)  NOT NULL,
  "USER_ID" VARCHAR2(256) NOT NULL,
  "ROLE_ID" VARCHAR2(256) NOT NULL
);
COMMENT ON TABLE "S_USER_ROLE" IS '用户角色关联表';
COMMENT ON COLUMN "S_USER_ROLE"."U_ID" IS 'UID';
COMMENT ON COLUMN "S_USER_ROLE"."USER_ID" IS '用户ID';
COMMENT ON COLUMN "S_USER_ROLE"."ROLE_ID" IS '角色ID';
CREATE TABLE "S_HISTORY"
(
  "U_ID"              VARCHAR2(32) NOT NULL,
  "TYPE"              VARCHAR2(64) NOT NULL,
  "DESCRIBE"          VARCHAR2(512),
  "PRIMARY_KEY_NAME"  VARCHAR2(32),
  "PRIMARY_KEY_VALUE" VARCHAR2(64),
  "CHANGE_BEFORE"     CLOB,
  "CHANGE_AFTER"      CLOB,
  "CREATE_DATE"       DATETIME     NOT NULL,
  "CREATOR_ID"        VARCHAR2(32)
);

CREATE TABLE "S_QUERY_PLAN" (
  "U_ID"        VARCHAR2(32)  NOT NULL,
  "NAME"        VARCHAR2(256) NOT NULL,
  "TYPE"        VARCHAR2(256) NOT NULL,
  "CONFIG"      CLOB,
  "SHARING"     NUMBER(4),
  "CREATOR_ID"  VARCHAR2(32)  NOT NULL,
  "CREATE_DATE" DATETIME      NOT NULL
);
COMMENT ON TABLE "S_QUERY_PLAN" IS '查询方案表';
COMMENT ON COLUMN "S_QUERY_PLAN"."U_ID" IS '主键';
COMMENT ON COLUMN "S_QUERY_PLAN"."NAME" IS '方案名称';
COMMENT ON COLUMN "S_QUERY_PLAN"."TYPE" IS '方案分类';
COMMENT ON COLUMN "S_QUERY_PLAN"."CONFIG" IS '方案配置';
COMMENT ON COLUMN "S_QUERY_PLAN"."SHARING" IS '是否共享方案';
COMMENT ON COLUMN "S_QUERY_PLAN"."CREATOR_ID" IS '创建人ID';
COMMENT ON COLUMN "S_QUERY_PLAN"."CREATE_DATE" IS '创建日期';

CREATE TABLE S_DATA_SOURCE
(
  U_ID        VARCHAR(32) PRIMARY KEY NOT NULL,
  NAME        VARCHAR(64)             NOT NULL,
  DRIVER      VARCHAR(128)            NOT NULL,
  URL         VARCHAR(512)            NOT NULL,
  USERNAME    VARCHAR(128)            NOT NULL,
  PASSWORD    VARCHAR(128)            NOT NULL,
  ENABLED     DECIMAL(4)              NOT NULL,
  CREATE_DATE DATE                    NOT NULL,
  PROPERTIES  CLOB,
  COMMENT     VARCHAR(512),
  TEST_SQL    VARCHAR(512)
);
COMMENT ON COLUMN S_DATA_SOURCE.U_ID IS 'ID';
COMMENT ON COLUMN S_DATA_SOURCE.NAME IS '数据源名称';
COMMENT ON COLUMN S_DATA_SOURCE.DRIVER IS '驱动';
COMMENT ON COLUMN S_DATA_SOURCE.URL IS 'URL';
COMMENT ON COLUMN S_DATA_SOURCE.USERNAME IS '用户名';
COMMENT ON COLUMN S_DATA_SOURCE.PASSWORD IS '密码';
COMMENT ON COLUMN S_DATA_SOURCE.ENABLED IS '是否启用';
COMMENT ON COLUMN S_DATA_SOURCE.CREATE_DATE IS '创建日期';
COMMENT ON COLUMN S_DATA_SOURCE.PROPERTIES IS '其他配置';
COMMENT ON COLUMN S_DATA_SOURCE.COMMENT IS '备注';
COMMENT ON COLUMN S_DATA_SOURCE.TEST_SQL IS '测试链接时使用的sql';

ALTER TABLE "S_QUERY_PLAN"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_USER_PROFILE"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_CLASSIFIED"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_CONFIG"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_MODULES"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_MODULE_META"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_RESOURCES"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_ROLE"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_ROLE_MODULES"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_SCRIPT"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_USER"
  ADD PRIMARY KEY ("U_ID");
ALTER TABLE "S_USER_ROLE"
  ADD PRIMARY KEY ("U_ID");

--定时任务
CREATE TABLE S_QUARTZ_JOB_HIS (
  u_id       VARCHAR(32) NOT NULL  PRIMARY KEY,
  job_id     VARCHAR(32) NOT NULL,
  start_time DATETIME    NOT NULL,
  end_time   DATETIME,
  result     CLOB,
  status     NUMBER(4)
);
COMMENT ON COLUMN S_QUARTZ_JOB_HIS.u_id IS '主键';
COMMENT ON COLUMN S_QUARTZ_JOB_HIS.job_id IS '任务ID';
COMMENT ON COLUMN S_QUARTZ_JOB_HIS.start_time IS '开始时间';
COMMENT ON COLUMN S_QUARTZ_JOB_HIS.end_time IS '结束时间';
COMMENT ON COLUMN S_QUARTZ_JOB_HIS.result IS '执行结果';
COMMENT ON COLUMN S_QUARTZ_JOB_HIS.status IS '状态';


CREATE TABLE S_QUARTZ_JOB (
  u_id       VARCHAR(32)  NOT NULL PRIMARY KEY,
  name       VARCHAR(128) NOT NULL,
  remark     VARCHAR(512),
  cron       VARCHAR(512) NOT NULL,
  script     CLOB         NOT NULL,
  language   VARCHAR(32)  NOT NULL,
  enabled    NUMBER(4),
  parameters CLOB,
  type       NUMBER(4)
);
COMMENT ON COLUMN S_QUARTZ_JOB.u_id IS '主键';
COMMENT ON COLUMN S_QUARTZ_JOB.name IS '任务名称';
COMMENT ON COLUMN S_QUARTZ_JOB.remark IS '备注';
COMMENT ON COLUMN S_QUARTZ_JOB.cron IS 'cron表达式';
COMMENT ON COLUMN S_QUARTZ_JOB.script IS '执行脚本';
COMMENT ON COLUMN S_QUARTZ_JOB.language IS '脚本语言';
COMMENT ON COLUMN S_QUARTZ_JOB.enabled IS '是否启用';
COMMENT ON COLUMN S_QUARTZ_JOB.parameters IS '启动参数';
COMMENT ON COLUMN S_QUARTZ_JOB.type IS '任务类型';


CREATE TABLE QRTZ_CALENDARS (
  SCHED_NAME    VARCHAR(120) NOT NULL,
  CALENDAR_NAME VARCHAR(200) NOT NULL,
  CALENDAR      IMAGE        NOT NULL
);

CREATE TABLE QRTZ_CRON_TRIGGERS (
  SCHED_NAME      VARCHAR(120) NOT NULL,
  TRIGGER_NAME    VARCHAR(200) NOT NULL,
  TRIGGER_GROUP   VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID    VARCHAR(80)
);

CREATE TABLE QRTZ_FIRED_TRIGGERS (
  SCHED_NAME        VARCHAR(120) NOT NULL,
  ENTRY_ID          VARCHAR(95)  NOT NULL,
  TRIGGER_NAME      VARCHAR(200) NOT NULL,
  TRIGGER_GROUP     VARCHAR(200) NOT NULL,
  INSTANCE_NAME     VARCHAR(200) NOT NULL,
  FIRED_TIME        BIGINT       NOT NULL,
  SCHED_TIME        BIGINT       NOT NULL,
  PRIORITY          INTEGER      NOT NULL,
  STATE             VARCHAR(16)  NOT NULL,
  JOB_NAME          VARCHAR(200) NULL,
  JOB_GROUP         VARCHAR(200) NULL,
  IS_NONCONCURRENT  BOOLEAN      NULL,
  REQUESTS_RECOVERY BOOLEAN      NULL
);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
  SCHED_NAME    VARCHAR(120) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL
);

CREATE TABLE QRTZ_SCHEDULER_STATE (
  SCHED_NAME        VARCHAR(120) NOT NULL,
  INSTANCE_NAME     VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT       NOT NULL,
  CHECKIN_INTERVAL  BIGINT       NOT NULL
);

CREATE TABLE QRTZ_LOCKS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME  VARCHAR(40)  NOT NULL
);

CREATE TABLE QRTZ_JOB_DETAILS (
  SCHED_NAME        VARCHAR(120) NOT NULL,
  JOB_NAME          VARCHAR(200) NOT NULL,
  JOB_GROUP         VARCHAR(200) NOT NULL,
  DESCRIPTION       VARCHAR(250) NULL,
  JOB_CLASS_NAME    VARCHAR(250) NOT NULL,
  IS_DURABLE        BOOLEAN      NOT NULL,
  IS_NONCONCURRENT  BOOLEAN      NOT NULL,
  IS_UPDATE_DATA    BOOLEAN      NOT NULL,
  REQUESTS_RECOVERY BOOLEAN      NOT NULL,
  JOB_DATA          IMAGE        NULL
);

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
  SCHED_NAME      VARCHAR(120) NOT NULL,
  TRIGGER_NAME    VARCHAR(200) NOT NULL,
  TRIGGER_GROUP   VARCHAR(200) NOT NULL,
  REPEAT_COUNT    BIGINT       NOT NULL,
  REPEAT_INTERVAL BIGINT       NOT NULL,
  TIMES_TRIGGERED BIGINT       NOT NULL
);

CREATE TABLE qrtz_simprop_triggers
(
  SCHED_NAME    VARCHAR(120)   NOT NULL,
  TRIGGER_NAME  VARCHAR(200)   NOT NULL,
  TRIGGER_GROUP VARCHAR(200)   NOT NULL,
  STR_PROP_1    VARCHAR(512)   NULL,
  STR_PROP_2    VARCHAR(512)   NULL,
  STR_PROP_3    VARCHAR(512)   NULL,
  INT_PROP_1    INTEGER        NULL,
  INT_PROP_2    INTEGER        NULL,
  LONG_PROP_1   BIGINT         NULL,
  LONG_PROP_2   BIGINT         NULL,
  DEC_PROP_1    NUMERIC(13, 4) NULL,
  DEC_PROP_2    NUMERIC(13, 4) NULL,
  BOOL_PROP_1   BOOLEAN        NULL,
  BOOL_PROP_2   BOOLEAN        NULL,
);

CREATE TABLE QRTZ_BLOB_TRIGGERS (
  SCHED_NAME    VARCHAR(120) NOT NULL,
  TRIGGER_NAME  VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA     IMAGE        NULL
);

CREATE TABLE QRTZ_TRIGGERS (
  SCHED_NAME     VARCHAR(120) NOT NULL,
  TRIGGER_NAME   VARCHAR(200) NOT NULL,
  TRIGGER_GROUP  VARCHAR(200) NOT NULL,
  JOB_NAME       VARCHAR(200) NOT NULL,
  JOB_GROUP      VARCHAR(200) NOT NULL,
  DESCRIPTION    VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT       NULL,
  PREV_FIRE_TIME BIGINT       NULL,
  PRIORITY       INTEGER      NULL,
  TRIGGER_STATE  VARCHAR(16)  NOT NULL,
  TRIGGER_TYPE   VARCHAR(8)   NOT NULL,
  START_TIME     BIGINT       NOT NULL,
  END_TIME       BIGINT       NULL,
  CALENDAR_NAME  VARCHAR(200) NULL,
  MISFIRE_INSTR  SMALLINT     NULL,
  JOB_DATA       IMAGE        NULL
);

ALTER TABLE QRTZ_CALENDARS
  ADD
  CONSTRAINT PK_QRTZ_CALENDARS PRIMARY KEY
    (
      SCHED_NAME,
      CALENDAR_NAME
    );

ALTER TABLE QRTZ_CRON_TRIGGERS
  ADD
  CONSTRAINT PK_QRTZ_CRON_TRIGGERS PRIMARY KEY
    (
      SCHED_NAME,
      TRIGGER_NAME,
      TRIGGER_GROUP
    );

ALTER TABLE QRTZ_FIRED_TRIGGERS
  ADD
  CONSTRAINT PK_QRTZ_FIRED_TRIGGERS PRIMARY KEY
    (
      SCHED_NAME,
      ENTRY_ID
    );

ALTER TABLE QRTZ_PAUSED_TRIGGER_GRPS
  ADD
  CONSTRAINT PK_QRTZ_PAUSED_TRIGGER_GRPS PRIMARY KEY
    (
      SCHED_NAME,
      TRIGGER_GROUP
    );

ALTER TABLE QRTZ_SCHEDULER_STATE
  ADD
  CONSTRAINT PK_QRTZ_SCHEDULER_STATE PRIMARY KEY
    (
      SCHED_NAME,
      INSTANCE_NAME
    );

ALTER TABLE QRTZ_LOCKS
  ADD
  CONSTRAINT PK_QRTZ_LOCKS PRIMARY KEY
    (
      SCHED_NAME,
      LOCK_NAME
    );

ALTER TABLE QRTZ_JOB_DETAILS
  ADD
  CONSTRAINT PK_QRTZ_JOB_DETAILS PRIMARY KEY
    (
      SCHED_NAME,
      JOB_NAME,
      JOB_GROUP
    );

ALTER TABLE QRTZ_SIMPLE_TRIGGERS
  ADD
  CONSTRAINT PK_QRTZ_SIMPLE_TRIGGERS PRIMARY KEY
    (
      SCHED_NAME,
      TRIGGER_NAME,
      TRIGGER_GROUP
    );

ALTER TABLE QRTZ_SIMPROP_TRIGGERS
  ADD
  CONSTRAINT PK_QRTZ_SIMPROP_TRIGGERS PRIMARY KEY
    (
      SCHED_NAME,
      TRIGGER_NAME,
      TRIGGER_GROUP
    );

ALTER TABLE QRTZ_TRIGGERS
  ADD
  CONSTRAINT PK_QRTZ_TRIGGERS PRIMARY KEY
    (
      SCHED_NAME,
      TRIGGER_NAME,
      TRIGGER_GROUP
    );

ALTER TABLE QRTZ_CRON_TRIGGERS
  ADD
  CONSTRAINT FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS FOREIGN KEY
    (
      SCHED_NAME,
      TRIGGER_NAME,
      TRIGGER_GROUP
    ) REFERENCES QRTZ_TRIGGERS (
    SCHED_NAME,
    TRIGGER_NAME,
    TRIGGER_GROUP
  ) ON DELETE CASCADE;


ALTER TABLE QRTZ_SIMPLE_TRIGGERS
  ADD
  CONSTRAINT FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS FOREIGN KEY
    (
      SCHED_NAME,
      TRIGGER_NAME,
      TRIGGER_GROUP
    ) REFERENCES QRTZ_TRIGGERS (
    SCHED_NAME,
    TRIGGER_NAME,
    TRIGGER_GROUP
  ) ON DELETE CASCADE;

ALTER TABLE QRTZ_SIMPROP_TRIGGERS
  ADD
  CONSTRAINT FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS FOREIGN KEY
    (
      SCHED_NAME,
      TRIGGER_NAME,
      TRIGGER_GROUP
    ) REFERENCES QRTZ_TRIGGERS (
    SCHED_NAME,
    TRIGGER_NAME,
    TRIGGER_GROUP
  ) ON DELETE CASCADE;


ALTER TABLE QRTZ_TRIGGERS
  ADD
  CONSTRAINT FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS FOREIGN KEY
    (
      SCHED_NAME,
      JOB_NAME,
      JOB_GROUP
    ) REFERENCES QRTZ_JOB_DETAILS (
    SCHED_NAME,
    JOB_NAME,
    JOB_GROUP
  );