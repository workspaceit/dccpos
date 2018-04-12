/*Remove Personal Information*/
SET FOREIGN_KEY_CHECKS =0;
TRUNCATE TABLE  `reset_password_tokens`;
TRUNCATE TABLE  `acess_role`;
TRUNCATE TABLE `auth_credential`;
TRUNCATE TABLE `wholesaler`;
TRUNCATE TABLE `supplier`;
TRUNCATE TABLE `employee`;
TRUNCATE TABLE `company_role`;
TRUNCATE TABLE `address`;
TRUNCATE TABLE `personal_information`;
UPDATE `acc_ledgers` SET `personal_info_id`=NULL;
SET FOREIGN_KEY_CHECKS =1;