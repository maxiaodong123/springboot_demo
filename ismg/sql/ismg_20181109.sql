ALTER TABLE `type_handler_rel` ADD COLUMN `cost` DECIMAL(10,2) NULL DEFAULT 0 COMMENT '花费（单位：分）' AFTER `status`;
ALTER TABLE `type_temp_rel` ADD COLUMN `cost` DECIMAL(10,2) NULL DEFAULT 0 COMMENT '花费（单位：分）' AFTER `status`;

update type_handler_rel set cost = 4.5 where service_name='aliSmsService' and id>-1;

UPDATE type_temp_rel 
SET 
    cost = 4.5
WHERE
    business_type IN (SELECT 
            business_type
        FROM
            type_handler_rel
        WHERE
            service_name = 'aliSmsService')
        AND id > - 1;
        
update type_handler_rel thr set thr.cost = (select ttr.cost from type_temp_rel ttr where thr.business_type = ttr.business_type) where service_name='aliSmsService' and id>-1;

UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]，第${time}次放单财务审批已通过，请知悉。' WHERE `id`='14';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]，第${time}次放单财务审批未通过，请知悉。' WHERE `id`='13';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]，第${time}次放单财务主管审批已通过，请知悉。' WHERE `id`='12';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]，第${time}次放单财务主管审批未通过，请知悉。' WHERE `id`='11';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]，第${time}次放单处理${result}，请知悉。' WHERE `id`='10';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]，第${time}次放单正在处理中${hour}小时。' WHERE `id`='9';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]前期收单处理${result}。' WHERE `id`='8';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]前期收单正在处理中${hour}小时。' WHERE `id`='7';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}],前台财务人员确认收单。' WHERE `id`='6';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]，第[${period}]单，收单${money}，处理结果：${result}。' WHERE `id`='5';
UPDATE `type_handler_rel` SET `temp_desc`='申请单[${name}]，前台收单处理结果:${result}。' WHERE `id`='4';

UPDATE `type_temp_rel` SET `cost`='9.00' WHERE `id`='18';
UPDATE `type_temp_rel` SET `cost`='9.00' WHERE `id`='1';
UPDATE `type_temp_rel` SET `cost`='9.00' WHERE `id`='23';

-------------------  以上生产已经执行 ---------------------