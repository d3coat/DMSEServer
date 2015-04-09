drop table users;
drop table messages;

CREATE TABLE users(
	userID INTEGER PRIMARY KEY AUTOINCREMENT,
	username TEXT UNIQUE,
	publickey TEXT);

CREATE TABLE messages(
	messageID INTEGER PRIMARY KEY AUTOINCREMENT, 
	userID INTEGER, 
	message TEXT, 
	msgtime DATETIME DEFAULT (strftime('%Y-%m-%d %H:%M:%f','now')), 
	FOREIGN KEY(userID) REFERENCES users(userID)
);