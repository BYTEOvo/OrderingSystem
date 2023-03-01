/*
 Navicat Premium Data Transfer

 Source Server         : OrderSystem
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : ordering

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 01/09/2022 19:06:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bulletin
-- ----------------------------
DROP TABLE IF EXISTS `bulletin`;
CREATE TABLE `bulletin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bulletin
-- ----------------------------
INSERT INTO `bulletin` VALUES (1, 'staff', '明天放假！');
INSERT INTO `bulletin` VALUES (3, 'customer', '全场免费');
INSERT INTO `bulletin` VALUES (4, 'customer', '111');
INSERT INTO `bulletin` VALUES (5, 'customer', '222');
INSERT INTO `bulletin` VALUES (6, 'staff', '333');

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `imgurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_special` bit(1) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` double(11, 2) NOT NULL,
  `tid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKtgk6w5dtnq8ae34959mqg8aly`(`tid`) USING BTREE,
  CONSTRAINT `dish_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `dish_type` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish
-- ----------------------------
INSERT INTO `dish` VALUES (1, '味鲜清口带有茶香，有消除油腻﹑帮助消化的作用。在夏季或吃油腻食品后品尝，最为适宜。', 'http://123.60.210.166:8443/api/file/碧螺春清炒白虾仁.PNG', b'0', '碧螺春清炒白虾仁', 34.00, 2);
INSERT INTO `dish` VALUES (2, '老鸭煲最好就是在夏天的时候吃，夏天吃老鸭煲可以滋阴美容，缓解暑热，能让人的心情没有那么的烦躁。\r\n', 'http://123.60.210.166:8443/api/file/六朝一品老鸭煲.PNG', b'0', '六朝一品老鸭煲', 25.00, 2);
INSERT INTO `dish` VALUES (3, '再“鲜”不过六月黄。肉质尤其鲜嫩软滑，重要的是满腹像流沙的蟹黄，让人口流涎水。\r\n', 'http://123.60.210.166:8443/api/file/花雕熟醉六月黄.PNG', b'0', '花雕熟醉六月黄', 100.50, 2);
INSERT INTO `dish` VALUES (4, '煮好的红烧肉带着些许鲍鱼的鲜美，肉质软糯肥而不腻，而鲍鱼裹满了浓郁的肉汁，鲜嫩弹牙\r\n', 'http://123.60.210.166:8443/api/file/随园鲍鱼红烧肉.PNG', b'0', '随园鲍鱼红烧肉', 11.00, 2);
INSERT INTO `dish` VALUES (6, '必点之一 豆腐软嫩配虾仁海胆 满口的留香\r\n', 'http://123.60.210.166:8443/api/file/海胆虾仁豆腐.png', b'0', '海胆虾仁豆腐', 6.00, 2);
INSERT INTO `dish` VALUES (7, '鲜香微辣，其香味十足，口感丰富。\r\n', 'http://123.60.210.166:8443/api/file/沸腾鱼虾.PNG', b'0', '沸腾鱼虾', 32.00, 2);
INSERT INTO `dish` VALUES (9, '香辣微麻，鲜滑爽口，肉质更加鲜嫩。\r\n', 'http://123.60.210.166:8443/api/file/江南水乡一桶鲜.PNG', b'0', '江南水乡一桶鲜', 4.50, 3);
INSERT INTO `dish` VALUES (10, '酱香口味,用石锅装盘,热气腾腾,肉质鲜嫩,入味\r\n', 'http://123.60.210.166:8443/api/file/杭椒石锅牛蛙.PNG', b'0', '杭椒石锅牛蛙', 99.00, 3);
INSERT INTO `dish` VALUES (11, '自然成形，鱼肉鲜嫩，酸鲜麻香，口味纯正。\r\n', 'http://123.60.210.166:8443/api/file/脆皮西湖苏醋鱼.PNG', b'0', '脆皮西湖苏醋鱼', 10.00, 3);
INSERT INTO `dish` VALUES (12, '杏花鸡特点鲜明，采用白切之法烹制更易保留鸡肉的青嫩，外皮呈金黄色，肉质细腻、滑嫩，十分可口。\r\n', 'http://123.60.210.166:8443/api/file/古法焗杏花鸡.PNG', b'0', '古法焗杏花鸡', 100.00, 3);
INSERT INTO `dish` VALUES (13, '内里绵软，酥香无比，入口是清新爽朗，似水波撩过舌尖，荡得人心绪徜徉\r\n', 'http://123.60.210.166:8443/api/file/灵隐烧素鹅.PNG', b'0', '灵隐烧素鹅', 10.00, 3);
INSERT INTO `dish` VALUES (14, '肥而不腻，带有芹菜的清香\r\n', 'http://123.60.210.166:8443/api/file/翡翠狮子头.PNG', b'0', '翡翠狮子头', 10.00, 3);
INSERT INTO `dish` VALUES (15, '在脆饼卷上面洒一圈香味浓郁的沙拉酱，再放上已经切成小碎的水果放上，清新解腻，赏心悦目，颜值超高。\r\n', 'http://123.60.210.166:8443/api/file/江南什锦拼盘.PNG', b'0', '江南什锦拼盘', 10.00, 4);
INSERT INTO `dish` VALUES (16, '形状美观，皮黄肉白，肥嫩鲜美，滋味异常鲜美，十分可口。\r\n', 'http://123.60.210.166:8443/api/file/江南白切鸡.PNG', b'0', '江南白切鸡', 10.00, 4);
INSERT INTO `dish` VALUES (17, '霜余蔬甲淡中甜，春近录苗嫩不蔹\r\n', 'http://123.60.210.166:8443/api/file/杭式爽口萝卜.PNG', b'0', '杭式爽口脆萝卜', 10.00, 4);
INSERT INTO `dish` VALUES (18, '多味调合，清爽利口，面条有筋力，为夏季佳品。\r\n', 'http://123.60.210.166:8443/api/file/鸡丝凉面.PNG', b'0', '鸡丝凉面', 10.00, 4);
INSERT INTO `dish` VALUES (19, '肉的营养丰富、肉质细嫩、易消化\r\n', 'http://123.60.210.166:8443/api/file/卤鸡爪.PNG', b'0', '卤鸡爪', 10.00, 4);
INSERT INTO `dish` VALUES (20, '雪沫乳花浮午盏，蓼茸蒿笋试春盘。人间有味是清欢。\r\n', 'http://123.60.210.166:8443/api/file/白马御笋.PNG', b'0', '白马御笋', 10.00, 5);
INSERT INTO `dish` VALUES (21, '家常菜的秘密，只有在外工作的你知道。\r\n', 'http://123.60.210.166:8443/api/file/家常宽豆角.PNG', b'0', '家常宽豆角', 10.00, 5);
INSERT INTO `dish` VALUES (22, '新鲜爽口', 'http://123.60.210.166:8443/api/file/手撕包菜.PNG', b'0', '手撕包菜', 0.00, 5);
INSERT INTO `dish` VALUES (23, '芳香松脆', 'http://123.60.210.166:8443/api/file/西湖藕韵.PNG', b'0', '西湖藕韵', 1.00, 5);
INSERT INTO `dish` VALUES (24, '当奶油恋上芝士，香浓的爱意沉浸在芝士的丝滑里，却因同根生而被命运阻隔，于是这份爱恋少了些许甜腻，多了一丝酸楚\r\n', 'http://123.60.210.166:8443/api/file/奶香南瓜.PNG', b'0', '奶香南瓜', 1.00, 5);
INSERT INTO `dish` VALUES (25, '香浓细滑的南瓜和芝士，再加上酥脆的脆卷，口感丰富层次鲜明。\r\n', 'http://123.60.210.166:8443/api/file/浓情芝士南瓜.PNG', b'0', '浓情芝士南瓜', 10.00, 5);
INSERT INTO `dish` VALUES (26, '菜色泽棕红油亮，质地酥软，麻辣味浓。咸鲜醇香，略带回甜。\r\n', 'http://123.60.210.166:8443/api/file/辣子鸡.PNG', b'0', '辣子鸡', 10.00, 6);
INSERT INTO `dish` VALUES (27, '宋嫂鱼羹用的是鲜美的鲈鱼，鲜香的鱼肉用清鸡汤做汤底炖煮。入碗色泽黄亮，鲜嫩滑润，味似蟹羹，十分美味。\r\n', 'http://123.60.210.166:8443/api/file/宋嫂鱼羹.PNG', b'0', '宋嫂鱼羹', 1.00, 6);
INSERT INTO `dish` VALUES (28, '红得透亮，色如玛瑙，夹起一块尝尝，软而不烂，肥而不腻。\r\n', 'http://123.60.210.166:8443/api/file/东坡肉.PNG', b'0', '东坡肉', 1.00, 6);
INSERT INTO `dish` VALUES (29, '白条的肉够嫩,味道非常清淡。\r\n', 'http://123.60.210.166:8443/api/file/鸡汁大白条.PNG', b'0', '鸡汁大白条', 10.00, 6);
INSERT INTO `dish` VALUES (30, '清凉解暑，酸甜可口\r\n', 'http://123.60.210.166:8443/api/file/草莓杏仁豆腐.PNG', b'0', '草莓杏仁豆腐', 10.00, 7);
INSERT INTO `dish` VALUES (31, '香滑细腻，味道太赞了\r\n', 'http://123.60.210.166:8443/api/file/大白兔奶糖布丁.PNG', b'0', '大白兔奶糖布丁', 10.00, 7);
INSERT INTO `dish` VALUES (32, '冰凉香甜,嫩滑爽口,并且还有生津解暑,清凉降火的作用\r\n', 'http://123.60.210.166:8443/api/file/西湖冰粉.WEBP', b'0', '西湖冰粉', 10.00, 7);
INSERT INTO `dish` VALUES (33, '小饼如嚼月，中有酥和饴。\r\n', 'http://123.60.210.166:8443/api/file/杭州春卷.PNG', b'0', '杭州春卷', 10.00, 7);
INSERT INTO `dish` VALUES (34, '制品浓稠,但却又透明顺滑,不易澥开。\r\n', 'http://123.60.210.166:8443/api/file/酒酿赤豆元宵.PNG', b'0', '酒酿赤豆元宵', 10.00, 7);
INSERT INTO `dish` VALUES (35, '既清淡简朴，又营养丰富。\r\n', 'http://123.60.210.166:8443/api/file/民国美龄粥.JPG', b'0', '民国美龄粥', 10.00, 7);
INSERT INTO `dish` VALUES (36, '铜碗声声街里唤，一瓯冰水和梅汤\r\n', 'http://123.60.210.166:8443/api/file/老南京酸梅汤.PNG', b'0', '老南京酸梅汤', 0.00, 8);
INSERT INTO `dish` VALUES (37, '香糯甜美\r\n', 'http://123.60.210.166:8443/api/file/米酒.JPG', b'0', '米酒', 10.00, 8);
INSERT INTO `dish` VALUES (38, '清甜的香酒，清新的口感，迷人的色泽、馥郁的花香\r\n', 'http://123.60.210.166:8443/api/file/特酿桂花酒.JPG', b'0', '特酿桂花酒', 10.00, 8);
INSERT INTO `dish` VALUES (39, '桃胶清热止渴、抗皱嫩肤\r\n', 'http://123.60.210.166:8443/api/file/椰汁桃胶.JPG', b'0', '椰汁桃胶', 10.00, 8);
INSERT INTO `dish` VALUES (40, '营养丰富,口味香甜,十分受大众喜欢。\r\n', 'http://123.60.210.166:8443/api/file/杨枝甘露.JPG', b'0', '杨枝甘露', 10.00, 8);
INSERT INTO `dish` VALUES (41, '清清爽爽超解腻\r\n', 'http://123.60.210.166:8443/api/file/茉香金桔汁.JPG', b'0', '茉香金桔汁', 10.00, 8);

-- ----------------------------
-- Table structure for dish_collect
-- ----------------------------
DROP TABLE IF EXISTS `dish_collect`;
CREATE TABLE `dish_collect`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `did` int(11) NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKtmwov81cpd7te9sk04qo94ljl`(`did`) USING BTREE,
  INDEX `FKwiwyqfs39lg0q8452shf7s1p`(`uid`) USING BTREE,
  CONSTRAINT `dish_collect_ibfk_1` FOREIGN KEY (`did`) REFERENCES `dish` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `dish_collect_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish_collect
-- ----------------------------
INSERT INTO `dish_collect` VALUES (2, 1, 3);
INSERT INTO `dish_collect` VALUES (9, 4, 4);
INSERT INTO `dish_collect` VALUES (29, 3, 2);
INSERT INTO `dish_collect` VALUES (32, 1, 2);
INSERT INTO `dish_collect` VALUES (33, 7, 2);
INSERT INTO `dish_collect` VALUES (34, 32, 2);
INSERT INTO `dish_collect` VALUES (36, 10, 5);
INSERT INTO `dish_collect` VALUES (37, 12, 5);
INSERT INTO `dish_collect` VALUES (40, 6, 5);
INSERT INTO `dish_collect` VALUES (42, 30, 2);

-- ----------------------------
-- Table structure for dish_type
-- ----------------------------
DROP TABLE IF EXISTS `dish_type`;
CREATE TABLE `dish_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish_type
-- ----------------------------
INSERT INTO `dish_type` VALUES (2, '码铃招牌');
INSERT INTO `dish_type` VALUES (3, '苏杭名菜');
INSERT INTO `dish_type` VALUES (4, '江南冷菜');
INSERT INTO `dish_type` VALUES (5, '精选时蔬');
INSERT INTO `dish_type` VALUES (6, '传世风味');
INSERT INTO `dish_type` VALUES (7, '江南名点');
INSERT INTO `dish_type` VALUES (8, '曲水流觞');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `did` int(11) NULL DEFAULT NULL,
  `oid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKqsx45s6t3792dyqbk7ooimetk`(`did`) USING BTREE,
  INDEX `FKm6an0aai7muf3tij80e3e6jr5`(`oid`) USING BTREE,
  CONSTRAINT `FKm6an0aai7muf3tij80e3e6jr5` FOREIGN KEY (`oid`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKqsx45s6t3792dyqbk7ooimetk` FOREIGN KEY (`did`) REFERENCES `dish` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1, 1, '已上菜', 2, 3);
INSERT INTO `order_detail` VALUES (2, 1, '已上菜', 4, 3);
INSERT INTO `order_detail` VALUES (3, 1, '制作完成', 2, 4);
INSERT INTO `order_detail` VALUES (4, 2, '制作完成', 4, 4);
INSERT INTO `order_detail` VALUES (5, 1, '制作完成', 3, 5);
INSERT INTO `order_detail` VALUES (6, 1, '制作完成', 7, 5);
INSERT INTO `order_detail` VALUES (7, 1, '已上菜', 1, 6);
INSERT INTO `order_detail` VALUES (8, 1, '已上菜', 2, 6);
INSERT INTO `order_detail` VALUES (9, 1, '已上菜', 3, 6);
INSERT INTO `order_detail` VALUES (10, 1, '已上菜', 4, 6);
INSERT INTO `order_detail` VALUES (11, 1, '已上菜', 6, 6);
INSERT INTO `order_detail` VALUES (12, 1, '已上菜', 7, 6);
INSERT INTO `order_detail` VALUES (13, 1, '已上菜', 9, 6);
INSERT INTO `order_detail` VALUES (14, 1, '已上菜', 10, 6);
INSERT INTO `order_detail` VALUES (15, 1, '已上菜', 13, 6);
INSERT INTO `order_detail` VALUES (16, 1, '已上菜', 14, 6);
INSERT INTO `order_detail` VALUES (17, 1, '已上菜', 30, 6);
INSERT INTO `order_detail` VALUES (18, 1, '已上菜', 31, 6);
INSERT INTO `order_detail` VALUES (19, 1, '已上菜', 32, 6);
INSERT INTO `order_detail` VALUES (20, 1, '已上菜', 33, 6);
INSERT INTO `order_detail` VALUES (21, 1, '已上菜', 34, 6);
INSERT INTO `order_detail` VALUES (22, 1, '已上菜', 35, 6);
INSERT INTO `order_detail` VALUES (23, 5, '已上菜', 41, 6);
INSERT INTO `order_detail` VALUES (24, 2, '已上菜', 1, 7);
INSERT INTO `order_detail` VALUES (25, 1, '已上菜', 10, 7);
INSERT INTO `order_detail` VALUES (26, 2, '已上菜', 15, 7);
INSERT INTO `order_detail` VALUES (27, 2, '已上菜', 26, 7);
INSERT INTO `order_detail` VALUES (28, 8, '已上菜', 31, 7);
INSERT INTO `order_detail` VALUES (29, 3, '制作完成', 3, 8);
INSERT INTO `order_detail` VALUES (30, 1, '制作完成', 4, 8);
INSERT INTO `order_detail` VALUES (31, 5, '制作完成', 10, 8);
INSERT INTO `order_detail` VALUES (32, 5, '制作完成', 20, 8);
INSERT INTO `order_detail` VALUES (33, 6, '制作完成', 21, 8);
INSERT INTO `order_detail` VALUES (34, 14, '制作完成', 22, 8);
INSERT INTO `order_detail` VALUES (35, 13, '制作完成', 24, 8);
INSERT INTO `order_detail` VALUES (36, 7, '制作完成', 25, 8);
INSERT INTO `order_detail` VALUES (37, 6, '制作完成', 26, 8);
INSERT INTO `order_detail` VALUES (38, 1, '制作完成', 2, 9);
INSERT INTO `order_detail` VALUES (39, 1, '制作完成', 3, 9);
INSERT INTO `order_detail` VALUES (40, 1, '制作完成', 6, 9);
INSERT INTO `order_detail` VALUES (41, 14, '制作完成', 21, 9);
INSERT INTO `order_detail` VALUES (42, 20, '制作完成', 22, 9);
INSERT INTO `order_detail` VALUES (43, 2, '制作完成', 25, 9);
INSERT INTO `order_detail` VALUES (44, 1, '制作完成', 1, 10);
INSERT INTO `order_detail` VALUES (45, 1, '制作完成', 6, 10);
INSERT INTO `order_detail` VALUES (46, 2, '制作完成', 26, 10);
INSERT INTO `order_detail` VALUES (47, 2, '制作完成', 27, 10);
INSERT INTO `order_detail` VALUES (48, 3, '制作完成', 28, 10);
INSERT INTO `order_detail` VALUES (49, 1, '制作完成', 36, 10);
INSERT INTO `order_detail` VALUES (50, 2, '制作完成', 37, 10);
INSERT INTO `order_detail` VALUES (51, 2, '制作完成', 40, 10);
INSERT INTO `order_detail` VALUES (52, 2, '制作中', 1, 11);
INSERT INTO `order_detail` VALUES (53, 2, '制作中', 20, 11);
INSERT INTO `order_detail` VALUES (54, 1, '制作中', 30, 11);
INSERT INTO `order_detail` VALUES (55, 2, '制作中', 34, 11);
INSERT INTO `order_detail` VALUES (56, 3, '制作完成', 2, 12);
INSERT INTO `order_detail` VALUES (57, 3, '制作完成', 10, 12);
INSERT INTO `order_detail` VALUES (58, 1, '制作完成', 11, 12);
INSERT INTO `order_detail` VALUES (59, 1, '制作完成', 13, 12);
INSERT INTO `order_detail` VALUES (60, 2, '制作完成', 14, 12);
INSERT INTO `order_detail` VALUES (61, 1, '已上菜', 27, 1);
INSERT INTO `order_detail` VALUES (62, 1, '已上菜', 36, 1);
INSERT INTO `order_detail` VALUES (63, 1, '已上菜', 37, 1);
INSERT INTO `order_detail` VALUES (64, 1, '已上菜', 38, 1);
INSERT INTO `order_detail` VALUES (65, 1, '已上菜', 39, 1);
INSERT INTO `order_detail` VALUES (66, 1, '已上菜', 15, 2);
INSERT INTO `order_detail` VALUES (67, 1, '已上菜', 16, 2);
INSERT INTO `order_detail` VALUES (68, 2, '已上菜', 13, 13);
INSERT INTO `order_detail` VALUES (69, 1, '已上菜', 14, 13);
INSERT INTO `order_detail` VALUES (70, 2, '已上菜', 27, 13);
INSERT INTO `order_detail` VALUES (71, 1, '已上菜', 32, 13);
INSERT INTO `order_detail` VALUES (72, 1, '已上菜', 34, 13);
INSERT INTO `order_detail` VALUES (73, 1, '已上菜', 9, 14);
INSERT INTO `order_detail` VALUES (74, 2, '已上菜', 10, 14);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_time` datetime(6) NULL DEFAULT NULL,
  `pay_time` datetime(6) NULL DEFAULT NULL,
  `phone_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `r1` int(11) NULL DEFAULT NULL,
  `r2` int(11) NULL DEFAULT NULL,
  `r3` int(11) NULL DEFAULT NULL,
  `r4` int(11) NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `urate` int(11) NULL DEFAULT NULL,
  `delivery` int(11) NULL DEFAULT NULL,
  `table_id` int(11) NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK8nr4ciplwgo1o2bq66ltojq7v`(`delivery`) USING BTREE,
  INDEX `FKrkhrp1dape261t3x3spj7l5ny`(`table_id`) USING BTREE,
  INDEX `FK58x4l9shxmkb7pismj4ilt7pj`(`uid`) USING BTREE,
  CONSTRAINT `FK58x4l9shxmkb7pismj4ilt7pj` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK8nr4ciplwgo1o2bq66ltojq7v` FOREIGN KEY (`delivery`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKrkhrp1dape261t3x3spj7l5ny` FOREIGN KEY (`table_id`) REFERENCES `tables` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, NULL, NULL, '2022-08-20 09:37:14.000000', '2022-08-20 18:30:41.000000', NULL, 31, NULL, NULL, NULL, NULL, '已完成', NULL, NULL, 1, 1);
INSERT INTO `orders` VALUES (2, NULL, NULL, '2022-08-20 10:50:17.000000', '2022-08-20 18:33:58.000000', NULL, 20, NULL, NULL, NULL, NULL, '已完成', NULL, NULL, 2, 9);
INSERT INTO `orders` VALUES (3, NULL, '真好吃，还便宜，下次一定', '2022-09-01 10:52:01.000000', '2022-09-01 11:01:37.555000', NULL, 36, 5, 5, 5, 5, '已完成', 5, NULL, 3, 2);
INSERT INTO `orders` VALUES (4, '翻斗花园XX街道', '外卖很快，超人一般的外卖员让我体会机制速度', '2022-09-01 11:04:26.125000', '2022-09-01 11:05:50.765000', '1141516', 47, 5, 5, 5, 5, '已完成', 4, 8, 12, 2);
INSERT INTO `orders` VALUES (5, '梅四D666', '很难吃，还这么贵！！！！！', '2022-09-01 11:07:47.613000', '2022-09-01 11:08:55.984000', '11112223333', 132.5, 1, 1, 1, 1, '已完成', 1, 8, 12, 2);
INSERT INTO `orders` VALUES (6, NULL, '住宅租户组长租户猪猪护照租户知足汉族猪猪汉族', '2022-09-01 11:11:55.000000', '2022-09-01 11:14:50.195000', NULL, 442, 2, 5, 3, 4, '已完成', 3, NULL, 11, 2);
INSERT INTO `orders` VALUES (7, NULL, 'hrhrhrhrhrhrhr猪猪猪组猪猪猪猪做做做做做做做做', '2022-08-31 11:17:10.000000', '2022-08-31 11:18:08.000000', NULL, 287, 5, 5, 5, 5, '已完成', 5, NULL, 5, 10);
INSERT INTO `orders` VALUES (8, '胡图图', '哈哈哈啊哈真的很不错诶贵死我了', '2022-08-26 11:20:37.000000', '2022-08-26 11:23:18.000000', '133304402104', 1060.5, 5, 5, 5, 5, '已完成', 5, 8, 12, 4);
INSERT INTO `orders` VALUES (9, 'hhh', '111113153235', '2022-09-01 11:27:17.615000', '2022-09-01 11:32:44.196000', '我靠真的诶', 291.5, 5, 5, 5, 5, '已完成', 2, 8, 12, 2);
INSERT INTO `orders` VALUES (10, '51阿发', '6124哈哈哈哈以iiii你指尖跃动的电荒\n', '2022-09-01 11:33:03.936000', '2022-09-01 11:34:08.711000', '44443132213', 105, 5, 5, 5, 5, '已完成', 5, 8, 12, 3);
INSERT INTO `orders` VALUES (11, NULL, '15161216', '2022-09-01 11:36:35.000000', '2022-09-01 11:41:27.042000', NULL, 118, 5, 5, 5, 5, '已完成', 5, NULL, 6, 2);
INSERT INTO `orders` VALUES (12, '14135', '5129480185913hhhhhhhh大号我', '2022-09-01 11:36:52.885000', '2022-09-01 11:38:18.453000', 'dsad', 412, 5, 5, 5, 5, '已完成', 5, 8, 12, 3);
INSERT INTO `orders` VALUES (13, NULL, '很棒的体验！', '2022-09-01 18:50:24.000000', '2022-09-01 18:56:40.467000', NULL, 52, 5, 5, 4, 3, '已完成', 5, NULL, 5, 4);
INSERT INTO `orders` VALUES (14, NULL, NULL, '2022-09-01 19:04:06.000000', '2022-09-01 19:04:58.805000', NULL, 202.5, NULL, NULL, NULL, NULL, '已完成', NULL, NULL, 5, 4);

-- ----------------------------
-- Table structure for tables
-- ----------------------------
DROP TABLE IF EXISTS `tables`;
CREATE TABLE `tables`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tables
-- ----------------------------
INSERT INTO `tables` VALUES (1, 'ready', 'A1');
INSERT INTO `tables` VALUES (2, 'ready', 'A2');
INSERT INTO `tables` VALUES (3, 'ready', 'A3');
INSERT INTO `tables` VALUES (4, 'ready', 'A4');
INSERT INTO `tables` VALUES (5, 'ready', 'B1');
INSERT INTO `tables` VALUES (6, 'ready', 'B2');
INSERT INTO `tables` VALUES (7, 'ready', 'B3');
INSERT INTO `tables` VALUES (8, 'ready', 'C1');
INSERT INTO `tables` VALUES (9, 'ready', 'C2');
INSERT INTO `tables` VALUES (10, 'ready', 'D1');
INSERT INTO `tables` VALUES (11, 'ready', 'D2');
INSERT INTO `tables` VALUES (12, 'delivery', 'DELIVERY');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary` double NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123456', 'admin', 0, 'admin');
INSERT INTO `user` VALUES (2, '123456', 'customer', 0, 'alice');
INSERT INTO `user` VALUES (3, '123456', 'customer', 0, 'bob');
INSERT INTO `user` VALUES (4, '123456', 'customer', 0, '张三');
INSERT INTO `user` VALUES (5, '123456', 'customer', 0, '李四');
INSERT INTO `user` VALUES (6, '123456', 'waiter', 0, 'waiter');
INSERT INTO `user` VALUES (7, '123456', 'cook', 0, 'cook');
INSERT INTO `user` VALUES (8, '123456', 'delivery', 0, 'delivery');
INSERT INTO `user` VALUES (9, '123456', 'customer', NULL, 'cy');
INSERT INTO `user` VALUES (10, '202200', 'customer', NULL, 'zzz');

SET FOREIGN_KEY_CHECKS = 1;
