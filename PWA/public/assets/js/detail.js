"use strict";

document.addEventListener("DOMContentLoaded", init);

function init() {
    getPlant();
    addEvents();
    document.querySelector(".button").addEventListener("click", enableDetailChanging);
}

async function enableDetailChanging(e) {
    e.preventDefault();
    document.querySelector("#plantDetails").classList.add("hidden");
    document.querySelector("#plantDetails-change").classList.remove("hidden");
    document.querySelector(".button:last-of-type").classList.remove("hidden");
    document.querySelector(".button:first-of-type").classList.add("hidden");
    document.querySelector(".button:last-of-type").addEventListener("click", disableDetailChanging);

    let values = getValuesFromSpan();

    document.querySelector("#plantDescription_change").setAttribute("value", values[0]);
    await getAllRooms()
    document.querySelector("#plantFlowers_change").setAttribute("value", values[2]);
    document.querySelector("#plantHeight_change").setAttribute("value", values[3]);

    document.querySelector("input[type=submit]").addEventListener("click", saveChanges);
}

async function getPlant() {
    let id = JSON.parse(localStorage.getItem("id"));
    let token = JSON.parse(localStorage.getItem("token"))
    let roomId;
    await fetch(`${config.url}/plants/${id}`,
        {
            method: `GET`,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        }).then(response => {
        return response.json()
    }).then(resp => {
        document.querySelector("#plantDescription").innerHTML = resp.plant_description;
        roomId = resp.room_id;
        document.querySelector("#plantFlowers").innerHTML = resp.flowers;
        document.querySelector("#plantHeight").innerHTML = resp.height;
        localStorage.setItem("needsWater", JSON.stringify(resp.needs_water));
        localStorage.setItem("lastWateredAt", JSON.stringify(resp.last_watered_at));
    });
    getRoom(roomId)

}

async function getRoom(roomId) {
    let token = JSON.parse(localStorage.getItem("token"))
    await fetch(`${config.url}/rooms/${roomId}`,
        {
            method: `GET`,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        }).then(response => {
        return response.json()
    }).then(resp => {
        document.querySelector("#plantRoom").innerHTML = resp.room_description;
    })
}

async function getAllRooms() {
    let token = JSON.parse(localStorage.getItem("token"));
    console.log("hallo");
    await fetch(`${config.url}/rooms`,
        {
            method: `GET`,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        }).then(response => {
        return response.json()
    }).then(resp => {
        console.log("hallo");
        let roomlist = document.querySelector("#plantRoom_change");
        roomlist.innerHTML = "";
        resp.forEach(room => {
            roomlist.innerHTML += `<option value="${room.room_id}">${room.room_description}</option>`;
        });
    });
}

async function saveChanges(e) {
    e.preventDefault();
    let values = getValuesFromForm();
    let id = JSON.parse(localStorage.getItem("id"));
    let token = JSON.parse(localStorage.getItem("token"));
    let isBlooming;
    if (values[4] === "true") {
        isBlooming = 1;
    } else {
        isBlooming = 0;
    }
    let body = {
        room_id: values[3],
        plant_description: values[0],
        flowers: parseInt(values[1]),
        height: parseInt(values[2]),
        last_watered_at: JSON.parse(localStorage.getItem("lastWateredAt")),
        is_blooming: isBlooming,
        needs_water: JSON.parse(localStorage.getItem("needsWater"))
    }
    let formBody = [];
    for (let property in body) {

        let encodedKey = encodeURIComponent(property);
        let encodedValue = encodeURIComponent(body[property]);
        formBody.push(encodedKey + "=" + encodedValue);

    }
    formBody = formBody.join("&");
    console.log(formBody);
    await fetch(`${config.url}/plants/${id}`,
        {
            method: `PUT`,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Bearer ' + token
            },
            body: formBody
        }).then(response => {
        return response.json()
    }).then(resp => {
        console.log(resp);
        disableDetailChanging(e);
        getPlant(id);
    })
}

function getValuesFromSpan() {
    let res = [];
    document.querySelectorAll("span").forEach(span => {
        res.push(span.innerHTML);
    });
    return res;
}

function getValuesFromForm() {
    let res = [];
    document.querySelectorAll("input").forEach(input => {
        if (input.type !== "submit") {
            res.push(input.value);
        }
    });
    document.querySelectorAll("select").forEach(input => {
        res.push(input.value);
    });
    console.log(res);
    return res;
}

function disableDetailChanging(e) {
    e.preventDefault();
    document.querySelector("#plantDetails").classList.remove("hidden");
    document.querySelector("#plantDetails-change").classList.add("hidden");
    document.querySelector(".button:last-of-type").classList.add("hidden");
    document.querySelector(".button:first-of-type").classList.remove("hidden");
}