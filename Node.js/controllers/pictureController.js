const pictureRepository = require("../repositories/pictureRepository.js");

async function addPicture(plantId, picture){
    if(plantId === null || plantId < 0){
        return "Invalid request";
    }
    return await pictureRepository.addPicture(plantId,picture);
}

async function findPicturesOfPlant(plantId){
    if(plantId === null || plantId < 0){
        return "Invalid request";
    }
    return await pictureRepository.findPicturesOfPlant(plantId);
}

module.exports = {
    addPicture,
    findPicturesOfPlant
}