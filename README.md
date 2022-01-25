# SimpleExcel
简单的导入

该项目旨在提供一个导入的模板，由excel_template表（导入模板表）确定导入的excel模板，由excel_rule（规则定义表）
确定数据校验规则。

技术：Springboot、poi、mybatis-plus

简单说明：
excel_rule表仅对数据合法性进行校验，校验规则使用正则表达式
ExcelBizService接口提供了两个接口，需要使用者去实现（***必须实现***）
接口1：bizCheckExcelData业务校验结果
接口2：saveBatchExcelData保存接口（入参的List的size默认为1000）


需要配合两张表进行使用：
1.导入模板表
```mysql
DROP TABLE IF EXISTS `excel_template`;
CREATE TABLE `excel_template`  (
  `id` bigint(20) NOT NULL,
  `excel_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导入excel模板的id',
  `excel_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导入的excel模板名字',
  `sheet_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '需要导入的sheet名字',
  `header_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表头名字',
  `item_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表头名字编码',
  `column_num` int(11) NOT NULL COMMENT '第几列',
  `need_check` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否需要校验（1：是，0：否）',
  `rule_id` bigint(20) NULL DEFAULT NULL COMMENT '校验规则id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '导入模板表' ROW_FORMAT = Dynamic;
```
2.规则定义表
```mysql
DROP TABLE IF EXISTS `excel_rule`;
CREATE TABLE `excel_rule`  (
  `id` bigint(20) NOT NULL,
  `rule` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '正则表达式',
  `err_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '校验失败时的错误信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '规则定义表' ROW_FORMAT = Dynamic;
```