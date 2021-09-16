const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const userRepository = require("../repositories/userRepository.js");
const config = require("../config.json");

async function register(firstName, lastName, email, password) {
    if (firstName === null || lastName === null || email === null || password === null) {
        return "You must enter a firstName, lastName, email and password.";
    }
    let emailCheck = await userRepository.findUserByEmail(email);
    if (emailCheck.length > 0) {
        return "This email is already in use.";
    }
    return await ((userRepository.addUser(firstName, lastName, email, password)).then(r => r[0]));
}

async function login(email, password) {
    let user = await userRepository.findUserByEmailWithPassword(email);
    if (user.length === 0) {
        return "Invalid email.";
    }
    console.log(user, user[0])
    return user[0];
}

async function getLoggedInUser(bearerToken){
    if(!bearerToken){
        return "Unauthorized";
    }
    return jwt.verify(bearerToken, config.secret, async (err, decoded) => {
        if (err) {
            return "Failed to authenticate token.";
        }
        return await userRepository.findUserById(decoded.id);
    });
}


module.exports = {
    register,
    login,
    getLoggedInUser
}