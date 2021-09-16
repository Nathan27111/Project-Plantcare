const db = require("../database.js");

function rowToPlant(row) {
    return {
        plant_id: row.plant_id,
        room_id: row.room_id,
        needs_water: row.needs_water,
        last_watered_at: row.last_watered_at,
        plant_description: row.plant_description,
        flowers: row.flowers,
        height: row.height,
        is_blooming: row.is_blooming
    }
}

function rowToPlantId(row){
    return {
        plant_id: row.plant_id
    }
}


async function getAllPlants() {
    const connection = await db.createConnection(db.getDB());
    let sql = "SELECT * FROM `plants`";
    return new Promise((resolve, reject) => {
        connection.query(sql, (err, result) => {
            if (err) {
                reject(err);
            }
            resolve(result.map(rowToPlant));
        });
    });
}

async function getPlantIds(userId) {
    const connection = await db.createConnection(db.getDB());
    let sql = "SELECT `plant_id` from `plants_users` where `user_id` = ?"
    return new Promise((resolve, reject) => {
        connection.query(sql,[userId], (err, result) => {
            if (err) {
                reject(err);
            }
            resolve(result.map(rowToPlantId));
        });
    });
}

async function addPlant(roomId, lastWateredAt, plantDescription, flowers, height, isBlooming) {
    const connection = await db.createConnection(db.getDB());
    let sql = "INSERT INTO `plants`(`room_id`, `last_watered_at`, `plant_description`, `flowers`, `height`, `is_blooming`) " +
        "VALUES(?,?,?,?,?,?)";
    return new Promise((resolve, reject) => {
        connection.query(sql, [roomId, lastWateredAt, plantDescription, flowers, height, isBlooming], (err, result) => {
            if (err) {
                reject(err);
            }
            resolve(getMostRecentPlant);
        });
    });
}

async function getPlant(plantId) {
    const connection = await db.createConnection(db.getDB());
    let sql = "SELECT * FROM `plants` where `plant_id`=?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [plantId], (err, result) => {
            if (err) {
                reject(err);
            }
            resolve(result.map(rowToPlant));
        });
    });
}

async function changePlant(plantId, roomId, lastWateredAt, plantDescription, flowers, height, isBlooming) {
    const connection = await db.createConnection(db.getDB());
    let sql = "UPDATE `plants` SET `room_id`=?, `last_watered_at`=?, `plant_description`=?, `flowers`=?, `height`=?, `is_blooming`=? WHERE `plant_id`=?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [roomId, lastWateredAt, plantDescription, flowers, height, isBlooming, plantId], (err, result) => {
            if (err) {
                reject(err);
            }
            resolve(getPlant(plantId));
        });
    });
}

async function deletePlant(plantId) {
    let connection = await db.createConnection(db.getDB());
    let sql = "DELETE from `plants` where `plant_id`=?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [plantId], (err, result) => {
            connection.end();
            if (err) {
                reject(err);
            }
            resolve();
        });
    });
}

async function getMostRecentPlant() {
    const connection = await db.createConnection(db.getDB());
    let sql = "SELECT * from `plants` ORDER BY `plant_id` DESC LIMIT 1";
    return new Promise((resolve, reject) => {
        connection.query(sql, (err, result) => {
            if (err) {
                reject(err);
            }
            resolve(result.map(rowToPlant));
        });
    });
}


module.exports = {
    getAllPlants,
    addPlant,
    getPlant,
    changePlant,
    deletePlant,
    getPlantIds
}