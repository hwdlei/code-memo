-- Test
-- 
CREATE TABLE page_view (
	viewTime INT, 
	userid BIGINT, 
	page_url STRING, 
	referrer_url STRING, 
	ip STRING COMMENT 'IP Address of the user') 
	COMMENT 'this is the page view table';

--  
CREATE TABLE page_view (
	viewTime INT, 
	userid BIGINT, 
	page_url STRING, 
	referrer_url STRING, 
	ip STRING COMMENT 'IP Address of the user') 
	COMMENT 'this is the page view table' 
	PARTITIONED BY (dt STRING, country STRING)  
	ROW FORMAT DELIMITED FIELDS TERMINATED BY '\001' 
	STORED AS SEQUENCEFILE;

--
CREATE TABLE page_view (
	viewTime INT, 
	userid BIGINT, 
	page_url STRING, 
	referrer_url STRING, 
	ip STRING COMMENT 'IP Address of the user') 
	COMMENT 'this is the page view table' 
	PARTITIONED BY (dt STRING, country STRING)  
	CLUSTERED BY (userid) SORTED BY (viewTime) INTO 32 BUCKETS 
	ROW FORMAT DELIMITED FIELDS TERMINATED BY '\001' 
	COLLECTION ITEMS TERMINATED BY '\002' 
	MAP KEYS TERMINATED BY '\003' 
	STORED AS SEQUENCEFILE;
	
--  实例
--  创建u_data表
CREATE TABLE u_data (
	userid INT, 
	movieid INT, 
	rating INT, 
	unixtime STRING)
	ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' 
	STORED AS TEXTFILE;
	
--  加载数据集
LOAD DATA LOCAL INPATH 'ml-100k/u.data' OVERWRITE INTO TABLE u_data;

--  获取记录的个数
SELECT COUNT(*) FROM u_data;

--  数据分析
CREATE TABLE u_date_new (
	userid INT, 
	movieid INT, 
	rating INT, 
	weekday INT) 
	ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

 add FILE weekday_mapper.py;
 
 INSERT OVERWRITE TABLE u_date_new 
 SELECT 
 	TRANSFORM (userid, movieid, rating, unixtime) 
 	USING 'python weekday_mapper.py' 
 	AS (userid, movieid,ratng, weekday) 
 	FROM u_data;
 
 SELECT weekday, count(*) FROM u_date_new GROUP BY weekday;
	
