drop table users;
drop table messages;

CREATE TABLE users(
	userID INTEGER AUTOINCREMENT,
	username TEXT PRIMARY KEY,
	publickey TEXT);

CREATE TABLE messages(
	messageID INTEGER PRIMARY KEY AUTOINCREMENT, 
	userID TEXT, 
	message TEXT, 
	msgtime DATETIME DEFAULT (strftime('%Y-%m-%d %H:%M:%f','now')), 
	FOREIGN KEY(userID) REFERENCES users(userID)
);

