const jwt = require("jsonwebtoken");
const config = require("../config.json");


function verifyToken(bearerToken){
    let userId = null;
    jwt.verify(bearerToken, config.secret, (err, decoded) => {
        if (err) {
            return "Unauthorized";
        }
        userId =decoded.id;
    });
    if(userId == null){
        return "Unauthorized";
    }
    return "Authenticated";
}

function getUserIdFromToken(bearerToken){
    let userId = null;
    jwt.verify(bearerToken, config.secret, (err, decoded) => {
        if (err) {
            return "Unauthorized";
        }
        userId =decoded.id;
    });
    if(userId == null){
        return "Unauthorized";
    }
    return userId;
}

module.exports={
    verifyToken,
    getUserIdFromToken
}