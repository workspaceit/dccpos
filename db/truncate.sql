/*Remove Personal Information*/
SET FOREIGN_KEY_CHECKS =0;
TRUNCATE TABLE  `reset_password_tokens`;

DELETE from `acess_role`  where id>1;
ALTER TABLE acess_role AUTO_INCREMENT = 2;

DELETE from `auth_credential` where id>1;
ALTER TABLE `auth_credential` AUTO_INCREMENT = 2;

DELETE from `employee` where id>1;
ALTER TABLE `employee` AUTO_INCREMENT = 2;

DELETE from `company_role` where id>1;
ALTER TABLE `company_role` AUTO_INCREMENT = 2;
DELETE from `acc_ledgers` where `personal_info_id` >1 OR `company_id` IS NOT NULL;
UPDATE `acc_ledgers` SET `current_balance`=0 WHERE 1;

DELETE from  `personal_information` where id>1;
ALTER TABLE `personal_information` AUTO_INCREMENT = 2;

DELETE from  `address` where id>1;
ALTER TABLE `address` AUTO_INCREMENT = 2;

TRUNCATE TABLE `wholesaler`;
TRUNCATE TABLE `supplier`;
TRUNCATE TABLE `company`;

SET FOREIGN_KEY_CHECKS =1;

/*Remove Personal Information*/
SET FOREIGN_KEY_CHECKS =0;
TRUNCATE TABLE  `product`;
SET FOREIGN_KEY_CHECKS =1;

/* Purchase */
SET FOREIGN_KEY_CHECKS =0;
TRUNCATE TABLE `shipment_cost`;
TRUNCATE TABLE `shipment`;
TRUNCATE TABLE `inventory`;
TRUNCATE TABLE `inventory_details`;
TRUNCATE TABLE `acc_entries`;
TRUNCATE TABLE `acc_entry_items`;


SET FOREIGN_KEY_CHECKS =1;