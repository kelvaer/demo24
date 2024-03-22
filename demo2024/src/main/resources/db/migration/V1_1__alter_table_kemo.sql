DROP TABLE IF EXISTS kemo ;
CREATE TABLE `kemo` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `name` varchar(20) NOT NULL COMMENT '姓名',
                        `age` int(5) DEFAULT NULL COMMENT '年龄',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;