"use strict";

document.addEventListener("DOMContentLoaded", init);

function init() {
    addEvents();
    document.querySelector("input").addEventListener("keydown", searchAlbums);
}

function searchAlbums() {
    let input = document.querySelector("input").value;
    console.log(input);
    // TODO: implement connection to DB
    let results = [];
    loadAlbums(results);
}

function loadAlbums(albumList) {
    // TODO: implement after connection to DB
    let list = document.querySelector("#albums");
    list.innerHTML = "";
    albumList.forEach(album => {
        list.innerHTML += `<div class="album">
            <h2>NAME</h2>
            <div class="plantImages">
                <img src="assets/media/images/${results.image[0]}" alt="first photo of NAME">
            </div>
        </div>`
    })
}