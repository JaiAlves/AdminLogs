CREATE TABLE IF NOT EXISTS config (
	id                      int8            NOT NULL,
    name_space              varchar(30)     NULL,
CONSTRAINT config_pk PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS config_log (
	id                      int8            NOT NULL,
	group_name              varchar(50)     NULL,
	start_pod_name          varchar(100)    NOT NULL,
	path_log                varchar(100)    NOT NULL,
	except_start_pod_name   varchar(100)    NULL,
	log_name                varchar(100)    NOT NULL,
	name_space              varchar(50)     NULL,
	enum_status_id          int8            NOT NULL default(1),
CONSTRAINT config_log_pk PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS load (
	id                  int8            NOT NULL,
	name                varchar(100)    NOT NULL,
	start_date          timestamp    NOT NULL,
	end_date            timestamp    NULL,
	CONSTRAINT load_pk PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS file (
	id                  int8            NOT NULL,
	load_id             int8            NOT NULL,
	name                varchar(100)    NOT NULL,
	start_date          timestamp    NOT NULL,
	end_date            timestamp    NULL,
	CONSTRAINT file_pk PRIMARY KEY ( id ),
	CONSTRAINT fk_file_load
          FOREIGN KEY(load_id)
    	  REFERENCES load(id)
);

CREATE TABLE IF NOT EXISTS file_content (
	id                  int8            NOT NULL,
	file_id             int8            NOT NULL,
	line                int8            NOT NULL,
	type                int8            NULL,
	text                text            NULL,
	date_time           timestamp       NULL,
	CONSTRAINT file_content_pk PRIMARY KEY ( id ),
	CONSTRAINT fk_file_content_file
          FOREIGN KEY(file_id)
    	  REFERENCES file(id)
);

CREATE TABLE IF NOT EXISTS load_step (
	id                  int8            NOT NULL,
	load_id             int8            NOT NULL,
    step                int8            NOT NULL,
    status              int8            NULL,
	start_date          timestamp       NOT NULL,
	end_date            timestamp       NULL,
	CONSTRAINT load_step_pk PRIMARY KEY ( id ),
	CONSTRAINT fk_load_step_load
          FOREIGN KEY(load_id)
    	  REFERENCES load(id)
);

