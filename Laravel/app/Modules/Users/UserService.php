<?php

namespace App\Modules\Users;

use App\Models\User;
use App\Modules\Services\Service;
use Illuminate\Support\Facades\Validator;

class UserService extends Service
{

    function __construct(User $model)
    {
        $this->model = $model;
    }

    public function register($data, $password)
    {
        $req = Validator::make($data, [
            'first_name' => 'required|string|between:2,100',
            'last_name' => 'required|string|between:2,100',
            'email' => 'required|string|email|max:100|unique:users',
            'password' => 'required|string|min:6',
        ]);
        if ($req->fails()) {
            return response()->json($req->errors()->toJson(), 400);
        }
        $user = User::create((array_merge(
            $req->validated(),
            ['password' => bcrypt($password)]
        )));
        return response()->json($user, 201);
    }

    public function login($data){
        $req = Validator::make($data, [
            'email' => 'required|email',
            'password' => 'required|string|min:5',
        ]);
        if ($req->fails()) {
            return response()->json($req->errors(), 422);
        }

        if (!$token = auth()->attempt($req->validated())) {
            return response()->json(['Auth error' => 'Unauthorized'],
                401);
        }
        return $this->generateToken($token);
    }

    protected function generateToken($token)
    {
        return response()->json([
            'access_token' => $token,
            'token_type' => 'bearer',
            'expires_in' => auth()->factory()->getTTL() * 60,
            'user' => auth()->user()
        ]);
    }
}
