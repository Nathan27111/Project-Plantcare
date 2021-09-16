const db = require("../database");

function rowToRoom(row) {
    return {
        room_id: row.room_id,
        user_id: row.user_id,
        room_description: row.room_description
    }
}

async function getAllRooms(userId) {
    let connection = await db.createConnection(db.getDB());
    let sql = "SELECT * from rooms where `user_id`=?";
    return new Promise((resolve, reject) => {
        connection.query(sql,[userId], (err, result) => {
            connection.end();
            if (err) {
                reject(err);
            } else {
                resolve(result.map(rowToRoom));
            }
        });
    })

}

async function addRoom(userId, roomDescription) {
    let connection = await db.createConnection(db.getDB());
    let sql = "INSERT INTO `rooms`(`user_id`, `room_description`) VALUES(?, ?)";
    return new Promise((resolve, reject) => {
        connection.query(sql, [userId, roomDescription], (err) => {
            connection.end();
            if (err) {
                reject(err);
            }
            resolve(findMostRecentRoom());
        });
    });
}

async function findMostRecentRoom() {
    let connection = await db.createConnection(db.getDB());
    let sql = "SELECT * FROM `rooms` ORDER BY `room_id` DESC LIMIT 1";
    return new Promise((resolve, reject) => {
        connection.query(sql, (err, result) => {
            connection.end();
            if (err) {
                reject(err)
            }
            resolve(result.map(rowToRoom));
        });
    });
}

async function findRoomWithRoomId(room_id) {
    let connection = await db.createConnection(db.getDB());
    let sql = "SELECT * from rooms where room_id = ?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [room_id], (error, result) => {
            connection.end();
            if (error) {
                reject(error);
            }
            resolve(result.map(rowToRoom));
        });
    });
}

async function changeRoom(roomId, userId, roomDescription) {
    let connection = await db.createConnection(db.getDB());
    let sql = "UPDATE `rooms` SET `user_id`=?, `room_description`=? WHERE `room_id`=?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [userId, roomDescription, roomId], (err, result) => {
            connection.end();
            if (err) {
                reject(err);
            }
            resolve(findRoomWithRoomId(roomId));
        });
    });
}

async function deleteRoom(roomId) {
    let connection = await db.createConnection(db.getDB());
    let sql = "DELETE from `rooms` where `room_id`=?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [roomId], (err, result) => {
            connection.end();
            if (err) {
                reject(err);
            }
            resolve();
        });
    });
}


module.exports = {
    getAllRooms,
    addRoom,
    findRoomWithRoomId,
    changeRoom,
    deleteRoom
};