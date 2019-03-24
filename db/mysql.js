'use strict';
const mysql = require('mysql');
const moment = require('moment');

/**
 * Acts as the data layer that connects to MySQL
 */

module.exports = {
    connect,
    close,
    select, 
    insert,
    remove, 
    update
}

let connection; // Holds connection

/**
 * Connects to mysql and uses db travel
 */
function connect() {
    return new Promise((resolve, reject) => {
        // Connection configs
        connection = mysql.createConnection({
            host : 'localhost',
            user :	'root',
            password: 'password',
            database: 'travel'
        });
    
        // Connect to DB
        connection.connect((err) => {
            if(err) {
                return reject(err);
            }
            else {
                resolve( "Connected to MySQL!" );
            }
        });
    });
}

/**
 * Closes mysql connection
 */
function close() {
    connection.end((err) => {
        if(err) {
            console.log(err);
            return;
        }
        else {
            console.log("Closed connection.");
        }
    });
}

/**
 * SQL SELECT statement
 */
function select(sql) {
    const data = []; // Array holding all row information to be returned from this function
    

    return new Promise((resolve, reject) => {
        connection.query( sql )
        .on('error', (err) => {
            return reject(err);
        })
        .on('result', (row) => {
            // // Print each field and value in each row
            // for(let field in row) {
            //     console.log( field + " " + row[field] ); 
            // }
            
            data.push( row );
            resolve( data );
        });
    });
}

/**
 * SQL INSERT statement
 */
function insert(sql, values) {
    connection.query( sql, values )
    .on('error', (err) => {
        console.log(err);
        return;
    })
    .on('result', (result) => {
        console.log(`Inserted ${result.affectedRows} rows`);

        console.log()
    });
}

/**
 * SQL DELETE statement
 */
function remove(sql, values) {
    connection.query( sql, values )
    .on('error', (err) => {
        console.log(err);
        return;
    })
    .on('result', (result) => {
        console.log(`Deleted ${result.affectedRows} rows`);

        console.log()
    });
}

/**
 * SQL UPDATE statement
 */
function update(sql, values) {
    connection.query( sql, values )
    .on('error', (err) => {
        console.log(err);
        return;
    })
    .on('result', (result) => {
        console.log(`Updated ${result.affectedRows} rows`);

        console.log()
    });
}