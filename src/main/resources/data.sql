INSERT INTO gaman_banking.users(id,name,role,password,delete_flg,created_at,updated_at)
     VALUES (null,"admin","ADMIN","$2a$10$dKQbMmg495yGK7mE33Vlg.K/rdlQLeEHvHRD5NDpviw9dFaBmfgEO",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.users(id,name,role,password,delete_flg,created_at,updated_at)
     VALUES (null,"taro","GENERAL","$2a$10$HPIjRCymeRZKEIq.71TDduiEotOlb8Ai6KQUHCs4lGNYlLhcKv4Wi",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.users(id,name,role,password,delete_flg,created_at,updated_at)
     VALUES (null,"naotake","GENERAL","$2a$10$rX2DEJ4bpu7jyOobgECaXOoJyehmqP7Y4oNTQruXb43/mI5G0NFU.",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.categories(id,name,delete_flg,created_at,updated_at)
     VALUES (null,"お菓子",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.categories(id,name,delete_flg,created_at,updated_at)
     VALUES (null,"飲み物",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.categories(id,name,delete_flg,created_at,updated_at)
     VALUES (null,"外食",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.categories(id,name,delete_flg,created_at,updated_at)
     VALUES (null,"飲み会",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.categories(id,name,delete_flg,created_at,updated_at)
     VALUES (null,"電車",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.transactions(transaction_id,transaction_date,price,category_id,memo,user_id,delete_flg,created_at,updated_at)
     VALUES (null,CURRENT_TIMESTAMP,300,1,"じゃがりこ",1,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.transactions(transaction_id,transaction_date,price,category_id,memo,user_id,delete_flg,created_at,updated_at)
     VALUES (null,CURRENT_TIMESTAMP,150,2,"コーラ",1,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);