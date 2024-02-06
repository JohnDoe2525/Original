CREATE DATABASE gaman_banking CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
CREATE USER 'bankuser'@'localhost' IDENTIFIED BY 'bankpass';
GRANT ALL PRIVILEGES ON gaman_banking.* to 'bankuser'@'localhost';