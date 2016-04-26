::cd /d d:\DB\MongoDB
::md data\rs1 data\rs2 data\rs3

start mongod --replSet m101 --logpath 1.log --dbpath d:\DB\MongoDB\data\rs1 --port 27017 --smallfiles --oplogSize 64 --journal
start mongod --replSet m101 --logpath 2.log --dbpath d:\DB\MongoDB\data\rs2 --port 27018 --smallfiles --oplogSize 64 --journal
start mongod --replSet m101 --logpath 3.log --dbpath d:\DB\MongoDB\data\rs3 --port 27019 --smallfiles --oplogSize 64 --journal

pause