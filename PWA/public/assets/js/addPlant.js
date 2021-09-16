"use strict";

document.addEventListener("DOMContentLoaded", init);

function init() {
    addEvents();
    fetchRooms();
    document.querySelector("form").addEventListener("submit", checkForm);
}

function fetchRooms() {
    let token = JSON.parse(localStorage.getItem("token"));
    let userId = JSON.parse(localStorage.getItem("userId"));
    fetch(`${config.url}/rooms`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    }).then(response => {
        return response.json()
    }).then(resp => {
        resp.forEach(room => {
            addOption(room);
        });
    });
}

function addOption(room) {
    let rooms = document.querySelector("#rooms");
    rooms.innerHTML += `<option id="${room.room_id}">${room.room_description}</option>`
}

function checkForm(e) {
    e.preventDefault();
    let token = JSON.parse(localStorage.getItem("token"));
    let description = document.querySelector("#plantDescription").value;
    let room = parseInt(document.querySelector("#rooms").selectedOptions[0].getAttribute("id"));
    let isBlooming = document.querySelector("#blooming").value;
    if(isBlooming === "Yes"){
        isBlooming = 1
    } else {
        isBlooming = 0
    }
    let needsWater = parseInt(document.querySelector("#needsWater").value);
    let flowers = parseInt(document.querySelector("#flowers").value);
    let height = parseInt(document.querySelector("#height").value);
    let today = new Date();
    let lastWateredAt = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate() + " " + today.getHours()
    + ":" +today.getMinutes() + ":" + today.getSeconds();
    let body = {
        needs_water: needsWater,
        plant_description: description,
        last_watered_at: lastWateredAt,
        room_id: room,
        is_blooming: isBlooming,
        flowers: flowers,
        height: height
    }
    fetch(`${config.url}/plants`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(body)
        }).then(response => {
            return response.json()
    }).then(resp => {
        console.log(resp);
        window.open("home.html", "_self");
    });
}