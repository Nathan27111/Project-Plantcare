"use strict";

const mysql = require("mysql");
const config = require("./config.json");

async function createConnection() {
    return new Promise((resolve, reject) => {
        let connection = mysql.createConnection(getDB());
        connection.connect((err) => {
            if (err) {
                console.log(err);
                reject(err);
            } else {
                resolve(connection);
            }
        })
    });
}

function getDB() { // retrieves the DB config from the .ENV
    return {
        host: config.db_host,
        port: config.db_port,
        database: config.db_database,
        user: config.db_user,
        password: config.db_password
    }
}

module.exports = {
    createConnection,
        getDB
};