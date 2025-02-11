﻿CREATE DATABASE ASM2_Java4
GO
USE ASM2_Java4
CREATE TABLE Video(
	id VARCHAR(255) PRIMARY KEY,
	title NVARCHAR(255) NOT NULL,
	poster VARCHAR(MAX) NOT NULL,
	views INT DEFAULT 0,
	[description] NVARCHAR(MAX) NOT NULL,
	active BIT DEFAULT 1
)
GO
CREATE TABLE Users(
	id VARCHAR(255) PRIMARY KEY,
	[password] VARCHAR(255) NOT NULL,
	email VARCHAR(50) NOT NULL,
	fullname NVARCHAR(255) NOT NULL,
	[admin] BIT DEFAULT 0
)
GO
CREATE TABLE Share(
	id INT IDENTITY(1,1) PRIMARY KEY,
	[user_id] VARCHAR(255) FOREIGN KEY REFERENCES Users(id),
	video_id VARCHAR(255) FOREIGN KEY REFERENCES Video(id),
	emails VARCHAR(50) NOT NULL,
	share_date DATE DEFAULT GETDATE()
)
GO
CREATE TABLE Favorite(
	id INT IDENTITY(1,1) PRIMARY KEY,
	[user_id] VARCHAR(255) FOREIGN KEY REFERENCES Users(id),
	video_id VARCHAR(255) FOREIGN KEY REFERENCES Video(id),
	share_date DATE DEFAULT GETDATE()
)