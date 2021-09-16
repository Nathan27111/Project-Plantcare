<?php

namespace App\Http\Controllers;

use App\Models\Plant;
use App\Modules\Plants\PlantService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\App;

class PlantApiController extends Controller
{
    public function getAllPlants(PlantService $service, Request $request){
        $this->setLanguage($request);
        return $service->getAllPlants();
    }

    public function addPlant(PlantService $service, Request $request){
        $this->setLanguage($request);
        $data = $request->all();
        return $service->addPlant($data);
    }

    public function findPlant(PlantService $service, int $id, Request $request){
        $this->setLanguage($request);
        return $service->findPlant($id);
    }

    public function updatePlant(PlantService $service, int $id, Request $request){
        $this->setLanguage($request);
        $data = $request->all();
        return $service->updatePlant($data, $id);
    }

    public function deletePlant(PlantService $service, int $id, Request $request){
        $this->setLanguage($request);
        return $service->deletePlant($id);
    }

    private function setLanguage(Request $request)
    {
        $language = $request->get("lang", "en");
        App::setLocale($language);
    }
}
