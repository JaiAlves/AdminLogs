insert into config (id, name_space)
select nextval('config_seq'), 'phxl';


insert into config_log(id , start_pod_name, path_log, log_name)
select nextval('config_log_seq'), 'mno-mg', '/logs/drum_mg/' , 'MG_62003143.log';


insert into config_log(id , start_pod_name, path_log, log_name)
select nextval('config_log_seq'), 'managed-notification-handler', '/logs/drum_galactus/' , 'queue-1811_managed_charge_notif_queue.log';


--flow SMS receiveMO
insert into config_log(id ,  group_name, start_pod_name, path_log, log_name, except_start_pod_name)
select nextval('config_log_seq'), 'RECEIVE_MO', 'workflow-engine', '/logs/drum_galactus_storm/' , 'channel-SMS_key-receiveMo_flowEntryId-9725.log', 'workflow-engine-banking';

--------------------------dcb google -----------------------------------------------------------------------------------------------------------------------------------------------
    --flow WEB chargeDOB
    insert into config_log(id , group_name, start_pod_name, path_log, log_name, except_start_pod_name)
    select nextval('config_log_seq'), 'DCB_GOOGLE', 'workflow-engine', '/logs/drum_galactus_storm/' , 'channel-WEB_key-chargeDOB_flowEntryId-9737.log', 'workflow-engine-banking';

    --dcb no-google
    insert into config_log(id , group_name, start_pod_name, path_log, log_name)
    select nextval('config_log_seq'), 'DCB_GOOGLE', 'partner-dcb-google', '/logs/tomcat/' , 'webapp-partnerhub-xl-idn-dcb-google.log';

    -- insert into config_log(id , group_name, start_pod_name, path_log, log_name)
    -- select nextval('config_log_seq'), 'DCB_GOOGLE', 'mno-charging', '/logs/drum_charging/' , 'charging-engine.log';
--------------------------dcb google -----------------------------------------------------------------------------------------------------------------------------------------------


--------------------------dcb non google -----------------------------------------------------------------------------------------------------------------------------------------------
    --flow WEB chargeDOB
    insert into config_log(id , group_name, start_pod_name, path_log, log_name, except_start_pod_name)
    select nextval('config_log_seq'), 'DCB_NON_GOOGLE', 'workflow-engine', '/logs/drum_galactus_storm/' , 'channel-WEB_key-chargeDOB_flowEntryId-9737.log', 'workflow-engine-banking';

    --dcb no-google
    insert into config_log(id , group_name, start_pod_name, path_log, log_name)
    select nextval('config_log_seq'), 'DCB_NON_GOOGLE', 'partner-dcb-nongoogle', '/logs/tomcat/' , 'webapp-partnerhub-xl-idn-dcb-nongoogle.log';


  --  insert into config_log(id , group_name, start_pod_name, path_log, log_name)
  --  select nextval('config_log_seq'), 'DCB_NON_GOOGLE', 'mno-charging', '/logs/drum_charging/' , 'charging-engine.log';

--------------------------dcb non google -----------------------------------------------------------------------------------------------------------------------------------------------


--update config_log cl set enum_status_id= 2 where id = 4