<?php

namespace App\Modules\Pictures;

use App\Models\Picture;
use App\Modules\Services\Service;
use Illuminate\Support\Facades\Validator;
use phpDocumentor\Reflection\Types\Parent_;

class PictureService extends Service
{
    protected $rules = [
        "plant_id" => "required|integer"
    ];

    function __construct(Picture $model)
    {
        parent::__construct($model);
    }
    public function addPicture($data)
    {
        //TODO: Test with image
        if (parent::isAuthenticated()) {
            $this->validate($data->all());
            if ($this->hasErrors()) {
                return $this->getErrors();
            }
            $result = [];
            $result->plant_id = $data["plant_id"];
            $result->picture = $this->getImageFromRequest($data);
            $result = $this->model->create($data);
            if ($result->id) {
                return response()->json($this->find($result->id), 200);
            } else {
                return response()->json(['message' => 'Unable to add Picture.'], 400);
            }
        } else {
            return parent::unauthenticated();
        }
    }
    public function findPicture($plantId){
        if(parent::isAuthenticated()){
            return response()->json($this->model->all()->where('plant_id', '=', $plantId), 200);
        }
        else{
            return parent::unauthenticated();
        }
    }

    private function getImageFromRequest($request){
        $image = $request->file('image');
        $name = $image->getClientOriginalname();
        $image->storeAs('public/images', $name);
        return $name;
    }
}
