<?php

namespace App\Modules\Plants;

use App\Models\Plant;
use App\Models\PlantUser;
use App\Modules\Services\Service;
use Illuminate\Support\Facades\Validator;
use function Sodium\add;

class PlantService extends Service
{
    protected $rules = [
        "room_id" => "required",
        "needs_water" => "required|integer",
        "last_watered_at"=> "date",
        "plant_description"=> "required|string",
        "flowers"=> "required|integer",
        "height"=> "required|integer",
        "is_blooming" => "required|boolean"
    ];

    function __construct(Plant $model)
    {
        parent::__construct($model);
    }

    public function getAllPlants()
    {
        if (parent::isAuthenticated()) {
            $user = auth()->user();
            $userId = $user->user_id;
            $model = PlantUser::all();
            $ids = $model->where("user_id", "=", $userId)->pluck("plant_id");
            return response()->json($this->model->whereIn('plant_id', $ids)->get(), 200);
        } else {
            return parent::unauthenticated();
        }
    }

    public function addPlant($data)
    {
        if (parent::isAuthenticated()) {
            $this->validate($data);
            if ($this->hasErrors()) {
                return $this->getErrors();
            }
            $result = $this->model->create($data);
            $user = auth()->user();
            $userId = $user->user_id;
            $model = new PlantUser;
            $model->plant_id = $result->plant_id;
            $model->user_id = $userId;
            $model->save();
            if ($result->plant_id) {
                return response()->json($this->findPlant($result->plant_id)->original, 200);
            } else {
                return response()->json(['message' => 'Unable to add Plant.'], 400);
            }
        } else {
            return parent::unauthenticated();
        }
    }

    public function findPlant($id)
    {
        if (parent::isAuthenticated()) {
            return response()->json($this->model->where('plant_id', '=', $id)
                ->first(), 200);
        } else {
            return parent::unauthenticated();
        }
    }

    public function updatePlant($data, $id)
    {
        if (parent::isAuthenticated()) {
            $this->validate($data);
            if ($this->hasErrors()) {
                return $this->getErrors();
            }
            $plant = $this->find($id);
            $plant->room_id = (int) $data["room_id"];
            $plant->needs_water = (int) $data["needs_water"];
            $plant->last_watered_at = $data["last_watered_at"];
            $plant->plant_description = $data["plant_description"];
            $plant->flowers = (int) $data["flowers"];
            $plant->height = (int) $data["height"];
            $plant->is_blooming = $data["is_blooming"];
            $plant->save();
            return $this->find($id);
        } else {
            return parent::unauthenticated();
        }
    }

    public function deletePlant($id)
    {
        if (parent::isAuthenticated()) {
            return response()->json(parent::delete($id), 200);
        } else {
            return parent::unauthenticated();
        }

    }
}
