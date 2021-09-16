<?php

use App\Http\Controllers\PictureApiController;
use App\Http\Controllers\PlantApiController;
use App\Http\Controllers\UserApiController;
use App\Http\Controllers\RoomApiController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::group(['prefix' => 'auth'], function () {
    Route::middleware(["auth.jwt"])->group(function () {
        Route::post('/token/refresh', [UserApiController::class,
            'token.refresh']);
    });
});
Route::middleware(["auth.jwt"])->group(
    function () {
        Route::get('/user', [UserApiController::class, 'user']);
    }
);

Route::post('/register', [UserApiController::class, 'register']);
Route::post('/login', [UserApiController::class, 'login']);
Route::post('/logout', [UserApiController::class, 'logout']);

Route::get("/rooms", [RoomApiController::class, "getAllRooms"]);
Route::post("/rooms", [RoomApiController::class, "addRoom"]);

Route::get("/plants", [PlantApiController::class, "getAllPlants"]);
Route::post("/plants", [PlantApiController::class, "addPlant"]);

Route::post("/pictures", [PictureApiController::class, "addPicture"]);

Route::get("/rooms/{id}", [RoomApiController::class, "findRoom"]);
Route::put("/rooms/{id}", [RoomApiController::class, "updateRoom"]);
Route::delete("/rooms/{id}", [RoomApiController::class, "deleteRoom"]);

Route::get("/plants/{id}", [PlantApiController::class, "findPlant"]);
Route::put("/plants/{id}", [PlantApiController::class, "updatePlant"]);
Route::delete("/plants/{id}", [PlantApiController::class, "deletePlant"]);

Route::get("/pictures/{plantId}", [PictureApiController::class, "findPicture"]);




