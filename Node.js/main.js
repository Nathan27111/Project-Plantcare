const http = require('http');
const express = require('express');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const multer = require('multer');
const forms = multer();
const config = require('./config.json');

const roomController = require("./controllers/roomController.js");
const userController = require("./controllers/userController.js");
const plantController = require("./controllers/plantController.js");
const pictureController = require("./controllers/pictureController.js");

const tokenVerifier = require("./controllers/tokenVerify");

const app = express();
app.use(express.json());
app.use(express.urlencoded({extended: true}));
app.use(forms.array());

const router = express.Router();
app.use('/api', router);

const server = http.createServer(app);

function getStatus(result) {
    if (typeof result === "string") {
        return 400;
    }
    return 200;
}

function checkBearerToken(request) {
    if (request.headers.authorization === undefined) {
        return "";
    }
    return request.headers.authorization.split(" ")[1];
}

router.post('/register', (req, res) => {
    console.log("test")
    let body = req.body;
    bcrypt.hash(body.password, 10, (err, hash) => {
        if (err) {
            res.status(500).json(err);
        } else {
            userController.register(body.first_name, body.last_name, body.email, hash).then(result => {
                res.header("Access-Control-Allow-Origin", "*");
                res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
                const statuscode = getStatus(result);
                res.status(statuscode).json(result);
            });
        }
    });

});

router.post('/login', (req, res) => {
    let body = req.body;
    userController.login(body.email, body.password).then(result => {
        bcrypt.compare(body.password, result.password,).then(isCorrectPwd => {
            if (!isCorrectPwd) {
                res.status(400).json("Incorrect password");
            }

            let token = jwt.sign({id: result.user_id}, config.secret, {
                expiresIn: 3600
            });
            const loginResp = {
                "access_token": token,
                "token_type": "bearer",
                "expires_in": 3600,
                "user": {
                    "user_id": result.user_id,
                    "first_name": result.first_name,
                    "last_name": result.last_name,
                    "email": result.email
                }
            }
            const statuscode = getStatus(res);
            res.status(statuscode).json(loginResp);
        });

    });
});

router.get("/user", (req, res) => {
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        userController.getLoggedInUser(token).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        })
    } else {
        res.status(401).json("Unauthorized")
    }

});

router.get("/rooms", (req, res) => {
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        roomController.getAllRooms(tokenVerifier.getUserIdFromToken(token)).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }

});

router.post("/rooms", (req, res) => {
    let body = req.body;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        roomController.addRoom(body.user_id, body.room_description, token).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }
});

router.get("/rooms/:id", (req, res) => {
    let roomId = req.params.id;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        roomController.findRoom(roomId).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }

});

router.put("/rooms/:id", (req, res) => {
    let body = req.body;
    let roomId = req.params.id;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        roomController.changeRoom(roomId, body.user_id, body.room_description).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }

});

router.delete("/rooms/:id", (req, res) => {
    let roomId = req.params.id;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        roomController.deleteRoom(roomId).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }

});

router.get("/plants", (req, res) => {
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        plantController.getAllPlants(tokenVerifier.getUserIdFromToken(token)).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }

});

router.post("/plants", (req, res) => {
    let body = req.body;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        plantController.addPlant(body.room_id, body.needs_water, body.last_watered_at, body.plant_description, body.flowers, body.height, body.is_blooming)
            .then(result => {
                const statuscode = getStatus(result);
                res.status(statuscode).json(result);
            });
    } else {
        res.status(401).json("Unauthorized");
    }

});

router.get("/plants/:id", (req, res) => {
    let plandId = req.params.id;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        plantController.getPlant(plandId).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }

});

router.put("/plants/:id", (req, res) => {
    let body = req.body;
    let plantId = req.params.id;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        plantController.changePlant(plantId, body.room_id, body.last_watered_at, body.plant_description, body.flowers, body.height,
            body.is_blooming, token)
            .then(result => {
                const statuscode = getStatus(result);
                res.status(statuscode).json(result);
            });
    } else {
        res.status(401).json("Unauthorized");
    }
});

router.delete("/plants/:id", (req, res) => {
    let plantId = req.params.id;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        plantController.deletePlant(plantId).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }
});

router.post("/pictures", (req, res) => {
    let body = req.body;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        pictureController.addPicture(body.plant_id, body.picture).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }

});

router.get("/pictures/:plantId", (req, res) => {
    let plantId = req.params.plantId;
    const token = checkBearerToken(req);
    if (tokenVerifier.verifyToken(token) === "Authenticated") {
        pictureController.findPicturesOfPlant(plantId).then(result => {
            const statuscode = getStatus(result);
            res.status(statuscode).json(result);
        });
    } else {
        res.status(401).json("Unauthorized");
    }
});


server.listen(8888);
console.log("Server is running on port 8888");