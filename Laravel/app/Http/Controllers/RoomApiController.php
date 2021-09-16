<?php

namespace App\Http\Controllers;

use App\Models\Room;
use App\Modules\Rooms\RoomService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\App;

class RoomApiController extends Controller
{
    public function getAllRooms(RoomService $service, Request $request)
    {
        $this->setLanguage($request);
        return $service->getAllRooms();
    }

    public function addRoom(RoomService $service, Request $request)
    {
        $this->setLanguage($request);
        $data = $request->all();
        return $service->addRoom($data);
    }

    public function findRoom(RoomService $service, $id, Request $request)
    {
        $this->setLanguage($request);
        return $service->find((int)$id);
    }

    public function updateRoom(RoomService $service, Request $request, $id)
    {
        $this->setLanguage($request);
        $data = $request->all();
        return $service->updateRoom($data, $id);
    }

    public function deleteRoom(RoomService $service, $id, Request $request)
    {
        $this->setLanguage($request);
        $service->deleteRoom($id);
    }

    private function setLanguage(Request $request)
    {
        $language = $request->get("lang", "en");
        App::setLocale($language);
    }
}
