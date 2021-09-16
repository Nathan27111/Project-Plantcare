"use strict";

document.addEventListener("DOMContentLoaded", init);

function init() {
    addEvents();
    getRooms();
    document.querySelector("form").addEventListener("submit", addNewRoom);
}

async function addNewRoom(e) {
    e.preventDefault();
    let token = JSON.parse(localStorage.getItem("token"));
    let description = document.querySelector("#roomDescription").value;
    console.log(description);
    let body = {
        room_description: description,
        user_id: await getUserId()
    }
    let formBody = [];
    for (let property in body) {

        let encodedKey = encodeURIComponent(property);
        let encodedValue = encodeURIComponent(body[property]);
        formBody.push(encodedKey + "=" + encodedValue);

    }
    formBody = formBody.join("&");
    if (description !== null) {
        await fetch(`${config.url}/rooms`,
            {
                method: "post",
                headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: formBody
            }).then(response => {
                return response.json();
            }
        ).then(resp => {
            console.log(resp);
            getRooms();
        })
    }
}

async function getUserId(){
    let token = JSON.parse(localStorage.getItem("token"));
    let id = 0;
    await fetch(`${config.url}/user`,
        {
            method: "get",
            headers: {
                "Authorization": "Bearer " + token
            }
        }).then(response => {
            return response.json();
    }).then(resp => {
        id = resp.user_id
    });
    return id;
}

async function getRooms() {
    let token = JSON.parse(localStorage.getItem("token"));
    await fetch(`${config.url}/rooms`,
        {
            method: "get",
            headers: {
                "Authorization": "Bearer " + token,
            }
        }).then(response => {
        return response.json();
    }).then(resp => {
        let roomList = document.querySelector("ul");
        roomList.innerHTML = "";
        resp.forEach(room => {
            roomList.innerHTML += `<li id="${room.room_id}">${room.room_description}</li>`
        });
    })
}