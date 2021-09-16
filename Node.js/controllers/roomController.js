const roomRepo = require("../repositories/roomRepository.js");

async function getAllRooms(userId) {
    let rooms = await roomRepo.getAllRooms(userId);
    if (rooms == null) {
        return "Cannot retrieve rooms.";
    }
    return rooms;
}

async function addRoom(userId, roomDescription) {
    if (userId === null || roomDescription === null) {
        return "Invalid userId and/or roomDescription";
    }
    return await roomRepo.addRoom(userId, roomDescription);
}

async function findRoom(roomId) {
    if (roomId == null || roomId <= 0) {
        return "Cannot retrieve room.";
    }
    let room = await roomRepo.findRoomWithRoomId(roomId);
    return room[0];
}

async function changeRoom(roomId, userId, roomDescription) {
    return await roomRepo.changeRoom(roomId, userId, roomDescription);
}

async function deleteRoom(roomId){
    return await roomRepo.deleteRoom(roomId);
}

module.exports = {
    getAllRooms,
    addRoom,
    findRoom,
    changeRoom,
    deleteRoom
}