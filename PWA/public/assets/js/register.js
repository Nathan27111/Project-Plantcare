"use strict"

document.addEventListener("DOMContentLoaded", init);

function init() {
    document.querySelector("form").addEventListener("submit", registerUser);
    if (!navigator.onLine)
        handleOffline();

    window.addEventListener("online", handleOnline);
    window.addEventListener("offline", handleOffline);

}

async function registerUser(e) {
    e.preventDefault();
    let email = document.querySelector("#email").value;
    let password = document.querySelector("#password").value;
    let firstName = document.querySelector("#firstName").value;
    let lastName = document.querySelector("#lastName").value;
    console.log(email, password, firstName, lastName);
    let body = {
        first_name: firstName,
        last_name: lastName,
        email: email,
        password: password
    };
    await fetch(`${config.url}/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    }).then(response => {
        if(response.status === 400){
            document.querySelector(".errors").innerHTML += ``;
            document.querySelector(".errors").innerHTML += `Please fill in an email address and a password with at least 6 characters. `
        }
        if (response.status === 201) {
            body = {
                email: email,
                password: password
            }
            fetch(`${config.url}/login`,
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(body)
                }).then(response => {
                    return response.json();
            }).then(resp => {
                localStorage.setItem("token", JSON.stringify(resp.access_token));
                localStorage.setItem("userId", JSON.stringify(resp.user.user_id));
                window.open("home.html", "_self");
            })
        }
    })
}

function handleOffline(){
    let errormessage = document.querySelector(".errors");
    errormessage.classList.add("offline");
    errormessage.innerHTML = "";
    errormessage.innerHTML += `No internet connection. Registering is not available while offline.`;
    document.querySelector("#register").classList.add("hidden");
}

function handleOnline(){
    let errormessage = document.querySelector(".errors");
    errormessage.innerHTML = ``;
    errormessage.classList.remove("offline");
    document.querySelector("#register").classList.remove("hidden");
}