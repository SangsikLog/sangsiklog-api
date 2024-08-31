CREATE DATABASE IF NOT EXISTS sangsiklog;

USE sangsiklog;

CREATE USER 'sangsiklog'@'%' IDENTIFIED BY 'sangsik123';
GRANT ALL PRIVILEGES ON sangsiklog.* TO 'sangsiklog'@'%';
FLUSH PRIVILEGES;