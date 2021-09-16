"use strict";

document.addEventListener('DOMContentLoaded', init);

function init() {
    fetchPlants();

    document.querySelector(".addButton").addEventListener("click", addPlant);
    document.querySelector("#refresh").addEventListener("click", fetchPlants);
    addEvents();
    if (!navigator.onLine)
        handleOffline();

    window.addEventListener("online", handleOnline);
    window.addEventListener("offline", handleOffline);
}

function hidePopup(e) {
    e.preventDefault();
    let target = e.target.closest("DIV");
    target.classList.remove("popupFlex");
    target.classList.add("hidden");
}

function checkButtons(e) {
    e.preventDefault();
    let button = e.target.innerHTML;
    button === "Continue"
        ? deletePlant(e)
        : hidePopup(e)
}

async function deletePlant(e) {
    let plantId = JSON.parse(localStorage.getItem("id"));
    let token = JSON.parse(localStorage.getItem("token"));
    await fetch(`${config.url}/plants/${plantId}`,
        {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + token
            }
        }).then(response => {
        return response.json()
    }).then(resp => {
            hidePopup(e);
            fetchPlants();
        }
    )
}

function addPlant(e) {
    e.preventDefault();
    window.open("addPlant.html", "_self");
}

function showDetailPage(e) {
    e.preventDefault();
    if(navigator.onLine){
        let id = e.target.closest("DIV").getAttribute("dataId");
        localStorage.setItem("id", id);
        window.open("detail.html", "_self");
    }
}

function showDeletePopup(e) {
    e.preventDefault();
    if(navigator.onLine){
        let id = e.target.closest("DIV").getAttribute("dataId");
        localStorage.setItem("id", id);
        let popUp = document.querySelector(".deletePopup");
        popUp.classList.remove("hidden");
        popUp.classList.add("popupFlex");
        document.querySelectorAll(".deletePopup a").forEach(button => {
            button.addEventListener("click", hidePopup);
            button.addEventListener("click", checkButtons);
        });
    }

}

function fetchPlants() {
    let token = JSON.parse(localStorage.getItem("token"));
    fetch(`${config.url}/plants`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    }).then(response => {
        return response.json()
    }).then(resp => {
        document.querySelector("#plantList").innerHTML = ""
        resp.forEach(plant => {
            loadPlant(plant);
        });
        addPlantButtonEventListeners();
    });
    document.querySelectorAll(".deleteButton").forEach(button => {
        console.log(button);
        button.addEventListener("click", showDeletePopup);
    });
}

function addPlantButtonEventListeners() {
    document.querySelectorAll(".plantButtons").forEach(button => {
        if (button.innerHTML === "Details") {
            button.addEventListener("click", showDetailPage);
        }
        if (button.innerHTML === "Water") {
            button.addEventListener("click", waterPlant);
        }
        if (button.innerHTML === "Delete") {
            button.addEventListener("click", showDeletePopup);
        }
    });
}


function loadPlant(plant) {
    let needsWater = calculateTimeUntilWaterNeeded(plant)
    let waterButton = `<a class="plantButtons" href="#">Water</a>`;
    if (needsWater === 0) {
        needsWater = `<p>Needs water<span>now!</span></p>`

    } else {
        needsWater = `<p>Needs water in <span>${needsWater}</span></p>`;
        waterButton = `<a class="plantButtons hidden" href="#">Water</a>`;
    }
    document.querySelector("#plantList").innerHTML +=
        `<div class="plant">
            <div class="image">
                <img src="assets/media/images/testimg.jpg" alt="picture of your plant">
            </div>
            <div class="plantInfo" dataId="${plant.plant_id}">
                ${needsWater}
                <a class="plantButtons" href="#">Details</a>
                ${waterButton}
                <a class="plantButtons" href="#">Delete</a>
            </div>
        </div>`;
}

function calculateTimeUntilWaterNeeded(plant) {
    let lastWatered = plant.last_watered_at
    let today = new Date()
    let date = today.getDate();
    let month = today.getMonth() + 1;
    let plantMonth = parseInt(lastWatered.split("-")[1]);
    let plantDate = parseInt(lastWatered.split("-")[2].split(" ")[0]);
    let plantTime = lastWatered.split(" ")[1];
    if ((date > (plantDate + plant.needs_water)) || (plantMonth !== month)) {
        return 0;
    } else if (date === (plantDate + plant.needs_water)) {
        let hours = today.getHours();
        let plantHours = parseInt(plantTime.split(":")[0])
        if (hours < plantHours) {
            return plantHours - hours + " hours";
        } else {
            let minutes = today.getMinutes();
            let plantMinutes = parseInt(plantTime.split(":")[1])
            return plantMinutes - minutes + " minutes";
        }

    } else {
        return (plantDate + plant.needs_water) - date + " days";
    }
}

async function fetchPlant(plantId, token) {
    let test;
    await fetch(`${config.url}/plants/${plantId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    }).then(response => {
        return response.json();
    }).then(resp => {
        test = resp
    });
    return test;
}

function changeDate(plant) {
    let currentDate = new Date();
    plant.last_watered_at = currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + (currentDate.getDate() + plant.needs_water) + " " +
        currentDate.getHours() + ":" + currentDate.getMinutes() + ":" + currentDate.getSeconds();
}

async function waterPlant(e) {
    e.preventDefault();
    let plantId = parseInt(e.target.closest("div").getAttribute("dataId"));
    let token = JSON.parse(localStorage.getItem("token"));
    let plant = await fetchPlant(plantId, token);
    changeDate(plant);
    fetch(`${config.url}/plants/${plantId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify(plant)
    }).then(response => {
        response.json()
            .then(resp => {
                fetchPlants()
            });
    });
}

function handleOffline(){
    document.querySelector(".roomNavBar").classList.add("hidden");
    document.querySelectorAll(".plantButtons").forEach(plantButton => {
        if(plantButton.innerHTML === "Delete"){
            plantButton.classList.add("offline");
        }
    });
    document.querySelectorAll(".addButton").forEach(button => {
        button.classList.add("hidden");
    });
    let popup = document.querySelector(".offlinePopup");
    popup.classList.remove("hidden");
}

function handleOnline(){
    document.querySelector(".roomNavBar").classList.add("hidden");
    document.querySelectorAll(".plantButtons").forEach(plantButton => {
        plantButton.classList.remove("offline");
    });
    document.querySelectorAll(".addButton").forEach(button => {
        button.classList.remove("hidden");
    });
    let popup = document.querySelector(".offlinePopup");
    popup.classList.add("hidden");
}

