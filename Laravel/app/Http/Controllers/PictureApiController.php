<?php

namespace App\Http\Controllers;

use App\Models\Picture;
use App\Modules\Pictures\PictureService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\App;

class PictureApiController extends Controller
{
    public function addPicture(Request $request, PictureService $service){
        $this->setLanguage($request);
        return $service->addPicture($request);
    }

    public function findPicture($plantId, PictureService $service, Request $request){
        $this->setLanguage($request);
        return $service->findPicture($plantId);
    }

    private function setLanguage(Request $request)
    {
        $language = $request->get("lang", "en");
        App::setLocale($language);
    }
}
