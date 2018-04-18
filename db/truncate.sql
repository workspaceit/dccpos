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
TRUNCATE TABLE `company`;
DELETE from `acc_ledgers` where `personal_info_id` IS NOT NULL OR `company_id` IS NOT NULL;
SET FOREIGN_KEY_CHECKS =1;
w
/*Remove Personal Information*/
SET FOREIGN_KEY_CHECKS =0;
TRUNCATE TABLE  `product`;
SET FOREIGN_KEY_CHECKS =1;