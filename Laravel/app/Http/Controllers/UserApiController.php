<?php

namespace App\Http\Controllers;

use App\Http\Middleware\SetLanguage;
use App\Modules\Users\UserService;
use Illuminate\Support\Facades\App;
use Illuminate\Support\Facades\Validator;
use App\Models\User;
use Illuminate\Http\Request;

class UserApiController extends Controller
{
    public function login(UserService $service,Request $request)
    {
        $data = $request->all();
        return $service->login($data);
    }

    public function register(UserService $service, Request $request)
    {
        $this->setLanguage($request);
        $data = $request->all();
        $password = $request->password;
        return $service->register($data, $password);
    }

    public function logout(Request $request)
    {
        $this->setLanguage($request);
        auth()->logout();
        return response()->json(['message' => 'User logged out'], 200);
    }

    public function refresh()
    {
        return $this->generateToken(auth()->refresh());
    }

    public function user(Request $request)
    {
        $this->setLanguage($request);
        return response()->json(auth()->user());
    }

    private function setLanguage(Request $request){
        $language = $request->get("lang", "en");
        App::setLocale($language);
    }
}
