"use strict";

function registerServiceWorker(){
    if('serviceWorker' in navigator){
        navigator.serviceWorker.register("/sw.js").catch(function (err){
            console.log("Error registering service worker => ", err);
        });
    }
}

registerServiceWorker();