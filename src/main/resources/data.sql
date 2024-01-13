INSERT INTO gaman_banking.users(id,name,mail_address,role,password,delete_flg,created_at,updated_at)
     VALUES (null,"山田　太郎","taro_yamada@example.co.jp","ADMIN","$2a$10$vY93/U2cXCfEMBESYnDJUevcjJ208sXav23S.K8elE/J6Sxr4w5jO",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.users(id,name,mail_address,role,password,delete_flg,created_at,updated_at)
     VALUES (null,"田中　太郎","taro_tanaka@example.co.jp","GENERAL","$2a$10$HPIjRCymeRZKEIq.71TDduiEotOlb8Ai6KQUHCs4lGNYlLhcKv4Wi",0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.transactions(transaction_id,transaction_date,category,memo,user_id,delete_flg,created_at,updated_at)
     VALUES (null,CURRENT_TIMESTAMP,"お菓子","じゃがりこ",1,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO gaman_banking.transactions(transaction_id,transaction_date,category,memo,user_id,delete_flg,created_at,updated_at)
     VALUES (null,CURRENT_TIMESTAMP,"ジュース","コーラ",2,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);