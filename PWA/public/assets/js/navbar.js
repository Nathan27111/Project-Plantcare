"use strict";

function addEvents() {
    document.querySelector("#navbar-collapse").addEventListener("click", openNavBar);
    document.querySelectorAll(".navLink").forEach(link => {
        link.addEventListener("click", openPage);
    });
}

function openNavBar(e) {
    e.preventDefault();
    let navbarPopup = document.querySelector(".navbarPopup");
    navbarPopup.classList.remove("hidden");
    navbarPopup.querySelector(".close").addEventListener("click", closeNavBar);
}

function openPage(e) {
    e.preventDefault();
    let page = e.target.innerHTML;
    if(page === 'Rooms') {
        window.open('rooms.html', "_self")
    }
    else if (page === 'Homepage'){
        window.open('home.html', "_self")
    }
    else if (page === 'Plant album'){
        window.open('album.html', "_self")
    }
    else {
        window.open('index.html', "_self")
    }
}

function closeNavBar(e) {
    e.preventDefault();
    e.target.closest("DIV").classList.add("hidden");
}