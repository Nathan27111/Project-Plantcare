"use strict";

document.addEventListener("DOMContentLoaded", init);

function init() {
    document.querySelector("form").addEventListener("submit", loginUser);
    if (!navigator.onLine)
        handleOffline();

    window.addEventListener("online", handleOnline);
    window.addEventListener("offline", handleOffline);
}


function loginUser(e) {
    e.preventDefault();
    let email = document.querySelector("#email").value;
    let password = document.querySelector("#password").value;
    let body = {
        email: email,
        password: password
    }
    fetch(`${config.url}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    }).then(response => {
        return response.json()
    }).then(resp => {
        if(resp.access_token){
            localStorage.setItem("token", JSON.stringify(resp.access_token));
            localStorage.setItem("userId", JSON.stringify(resp.user.user_id));
            window.open("home.html", "_self");
        }
        else {
            let errorMessage = document.querySelector(".errors");
            errorMessage.innerHTML = ``;
            if(resp["Auth error"]){
                errorMessage.innerHTML += "Wrong credentials. Please try again.";
            }
            if(resp.email){
                errorMessage.innerHTML += resp.email;
            }
            if(resp.password){
                errorMessage.innerHTML += resp.password;
            }
        }
    });
}

function handleOffline(){
    let errorMessage = document.querySelector(".errors");
    errorMessage.classList.add("online");
    errorMessage.innerHTML = `No internet connection. Logging in requires internet connection.`;
    document.querySelector("#login").classList.add("hidden");
}

function handleOnline(){
    let errorMessage = document.querySelector(".errors");
    errorMessage.classList.remove("online");
    errorMessage.innerHTML = "";
    document.querySelector("#login").classList.remove("hidden");
}