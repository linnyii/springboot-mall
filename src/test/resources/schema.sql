--IF NOT EXIST 一定要加
--因為SpringBoost 會去執行schema.sql 好幾次，可以避免product table 重複被建立
-- 7-10 課程複習-H2 db
CREATE TABLE IF NOT EXISTS product
(
    product_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(128)  NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );

--補充
--MySQL與H2基本上是兩家不同的廠商開發，所以在語法上可能會有一些微小的差異
--所以在創建H2 資料庫時需要稍加注意
--例如MySQL UNIQUE KEY 表示唯一
--則H2 為UNIQUE

CREATE TABLE IF NOT EXISTS user
(
    user_id            INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE,
    password           VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );
