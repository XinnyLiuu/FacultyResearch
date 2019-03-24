const mysql = require('./mysql');

// DB connection
mysql.connect();

// Sample insert
// id 1, name test, desc test, cap 1
let values = [ 1, "test", "test", 1 ];
mysql.insert("insert into equipment values(?, ?, ?, ?)", values);

// Sample update
values = [ "NEW NAME", 1 ];
mysql.update("update equipment set equipmentname = ? where equipid = ?", values);

// Sample query using select
mysql.select("select * from equipment");

// Sample delete
// id 1
values = [ 1 ];
mysql.remove("delete from equipment where equipid = ?", values);

mysql.close();