const plantRepository = require("../repositories/plantRepository");

async function getAllPlants(userId) {
    let ids = await plantRepository.getPlantIds(userId);
    let plants = []
    for (let id of ids) {
        plants.push(await plantRepository.getPlant(id.plant_id));
    }
    return plants;
}

async function addPlant(roomId, lastWateredAt, plantDescription, flowers, height, isBlooming) {
    if(roomId === null || plantDescription === null || flowers === null || height === null || isBlooming === null || lastWateredAt === null){
        return "Invalid request";
    }
    return await plantRepository.addPlant(roomId, lastWateredAt, plantDescription, flowers, height, isBlooming)
}

async function getPlant(plantId){
    if(plantId === null || plantId < 0){
        return "Invalid request";
    }
    let plant = await plantRepository.getPlant(plantId);
    return plant[0];
}

async function changePlant(plantId, roomId, lastWateredAt, plantDescription, flowers, height, isBlooming){
    if(plantId === null || plantId < 0){
        return "Invalid request";
    }
    return await plantRepository.changePlant(plantId, roomId, lastWateredAt, plantDescription, flowers, height, isBlooming);
}

async function deletePlant(plantId){
    if(plantId === null || plantId < 0){
        return "Invalid request";
    }
    return await plantRepository.deletePlant(plantId);
}

module.exports = {
    getAllPlants,
    addPlant,
    getPlant,
    changePlant,
    deletePlant
}