const db = require("../database.js");

function rowToPicture(row) {
    return {
        picture_id: row.picture_id,
        plant_id: row.plant_id,
        picture: row.picture,
        taken_at: row.taken_at
    }
}


async function addPicture(plantId, picture) {
    const connection = await db.createConnection(db.getDB());
    let sql = "INSERT INTO `pictures`(`plantId`, `picture`, `takenAt`) VALUES(?,?, CURRENT_TIMESTAMP())";
    return new Promise((resolve, reject) => {
        connection.query(sql, [plantId, picture], (err, result) => {
            if (err) {
                reject(err);
            }
            resolve(getMostRecentPicture());
        });
    });
}

async function findPicturesOfPlant(plantId) {
    const connection = await db.createConnection(db.getDB());
    let sql = "SELECT * FROM `pictures` WHERE `plant_id`= ?";
    return new Promise((resolve, reject) => {
        connection.query(sql, [plantId], (err, result) => {
            if (err) {
                reject(err);
            }
            resolve(result.map(rowToPicture));
        });
    });
}

async function getMostRecentPicture() {
    const connection = await db.createConnection(db.getDB());
    let sql = "SELECT * from `pictures` ORDER BY `picture_id` DESC LIMIT 1";
    return new Promise((resolve, reject) => {
        connection.query(sql, (err, result) => {
            if (err) {
                reject(err);
            }
            resolve(result.map(rowToPicture))
        })
    });
}

module.exports = {
    addPicture,
    findPicturesOfPlant
}