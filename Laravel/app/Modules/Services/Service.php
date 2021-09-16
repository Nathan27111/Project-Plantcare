<?php


namespace App\Modules\Services;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Validator;


class Service
{
    protected $model = null;
    protected $rules = [
    ];
    protected $errors = null;

    public function __construct(Model $model){
        $this->model =  $model;
    }

    public function getErrors(){
        return $this->errors;
    }

    public function hasErrors(){
        return !is_null($this->errors);
    }

    public function all(){
        return $this->model->all();
    }

    public function find($id){
        return $this->model->find($id);
    }

    public function delete($id){
        $this->find($id)->delete();
    }

    public function isAuthenticated(){
        return auth()->check();
    }

    public function unauthenticated(){
        return response()->json("Unauthorized", 401);
    }

    protected function validate($data){
        $this->errors = null;
        $validator = Validator::make($data, $this->rules);
        if($validator->fails())
            $this->errors = $validator->errors();
    }

}
