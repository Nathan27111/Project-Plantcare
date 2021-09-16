<?php

namespace App\Modules\Rooms;

use App\Models\Room;
use App\Models\RoomsLanguage;
use App\Modules\Services\Service;
use Illuminate\Support\Facades\Validator;

class RoomService extends Service
{
    protected $rules = [
        "user_id" => "required",
        "room_description" => "required"
    ];

    function __construct(Room $model)
    {
        parent::__construct($model);
    }

    public function getAllRooms()
    {
        if (parent::isAuthenticated()) {
            $user = auth()->user();
            $userId = [$user->user_id];
            return response()->json($this->model->whereIn("user_id", $userId)->get(), 200);
        } else {
            return parent::unauthenticated();
        }
    }

    public function addRoom($data)
    {
        if (parent::isAuthenticated()) {
            $this->validate($data);
            if ($this->hasErrors()) {
                return $this->getErrors();
            }
            $result = $this->model->create($data);
            if (!is_null($result->room_id)) {
                return response()->json($this->find($result->room_id)->original, 200);
            } else {
                return response()->json(['message' => 'Unable to create Room.'], 400);
            }
        } else {
            return parent::unauthenticated();
        }
    }

    public function findRoom($id)
    {
        if (parent::isAuthenticated()) {
            return response()->json([$this->model->where('room_id', '=', $id)
                ->first()], 200);
        } else {
            return parent::unauthenticated();
        }
    }

    public function updateRoom($data, $id)
    {
        if (parent::isAuthenticated()) {
            $this->validate($data);
            if ($this->hasErrors()) {
                return $this->getErrors();
            }
            $room = $this->model->find($id);
            $room->user_id = $data["user_id"];
            $room->room_description = $data["room_description"];
            $room->save();
            return $this->findRoom($id);
        } else {
            return parent::unauthenticated();
        }
    }

    public function deleteRoom($id)
    {
        if (parent::isAuthenticated()) {
            return response()->json([parent::delete($id)], 200);
        } else {
            return parent::unauthenticated();
        }

    }
}
