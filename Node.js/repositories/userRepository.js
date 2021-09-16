const db = require("../database.js");

function rowToUser(row) {
    return {
        user_id: row.user_id,
        first_name: row.first_name,
        last_name: row.last_name,
        email: row.email,
        password: row.password
    }
}


async function addUser(firstName, lastName, email, password) {
    console.log(firstName, lastName, email, password)
    const connection = await db.createConnection(db.getDB());
    let sql = "INSERT INTO `users`(`first_name`, `last_name`, `email`, `password`) VALUES(?,?,?,?)";
    return new Promise((resolve, reject) => {
        connection.query(sql, [firstName, lastName, email, password], (err, result) => {
            connection.end();
            if (err) {
                reject(err);
            }
            resolve(findUserByEmail(email));
        });
    });
}

async function findUserByEmail(email) {
    const connection = await db.createConnection(db.getDB());
    let sql = "SELECT `first_name`, `last_name`, `email`, `user_id` from `users` where `email` = ?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [email], (err, result) => {
            connection.end();
            if (err) {
                reject(err);
            }
            resolve(result.map(rowToUser));
        });
    });
}

async function findUserByEmailWithPassword(email) {
    const connection = await db.createConnection(db.getDB());
    let sql = "SELECT * from `users` where `email` = ?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [email], (err, result) => {
            connection.end();
            if (err) {
                reject(err);
            }
            resolve(result.map(rowToUser));
        });
    });
}

async function findUserById(userId) {
    const connection = await db.createConnection(db.getDB());
    let sql = "SELECT `first_name`, `last_name`, `email`, `user_id` from `users` where `user_id` = ?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [userId], (err, result) => {
            connection.end();
            if (err) {
                reject(err);
            }
            resolve(result.map(rowToUser));
        });
    });
}


module.exports = {
    findUserByEmail,
    addUser,
    findUserById,
    findUserByEmailWithPassword
}