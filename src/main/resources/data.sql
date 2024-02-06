INSERT INTO gaman_banking.users(id,name,mail_address,role,password,delete_flg,created_at,updated_at)
     VALUES (null,"山田　太郎","taro_yamada@example.co.jp","ADMIN","$2a$10$vY93/U2cXCfEMBESYnDJUevcjJ208sXav23S.K8elE/J6Sxr4w5jO",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.users(id,name,mail_address,role,password,delete_flg,created_at,updated_at)
     VALUES (null,"田中　太郎","taro_tanaka@example.co.jp","GENERAL","$2a$10$HPIjRCymeRZKEIq.71TDduiEotOlb8Ai6KQUHCs4lGNYlLhcKv4Wi",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.users(id,name,mail_address,role,password,delete_flg,created_at,updated_at)
     VALUES (null,"野口　尚武","na_noguchi@example.co.jp","GENERAL","$2a$10$rX2DEJ4bpu7jyOobgECaXOoJyehmqP7Y4oNTQruXb43/mI5G0NFU.",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.categories(id,name,delete_flg,created_at,updated_at)
     VALUES (null,"お菓子",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.categories(id,name,delete_flg,created_at,updated_at)
     VALUES (null,"ジュース",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.categories(id,name,delete_flg,created_at,updated_at)
     VALUES (null,"ラーメン",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.categories(id,name,delete_flg,created_at,updated_at)
     VALUES (null,"タバコ",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.transactions(transaction_id,transaction_date,price,category_id,memo,user_id,delete_flg,created_at,updated_at)
     VALUES (null,CURRENT_TIMESTAMP,300,1,"じゃがりこ",1,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.transactions(transaction_id,transaction_date,price,category_id,memo,user_id,delete_flg,created_at,updated_at)
     VALUES (null,CURRENT_TIMESTAMP,150,2,"コーラ",1,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);