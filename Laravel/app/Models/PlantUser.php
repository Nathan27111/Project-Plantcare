<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PlantUser extends Model
{
    protected $fillable = [
        "plant_id",
        "user_id"
    ];

    public function plants(){
        return $this->hasMany('App\Plant');
    }

    public function users(){
        return $this->hasMany('App\User');
    }

    public $table = "plants_users";
    protected $primaryKey = 'plant_id';
    public $timestamps = false;
    use HasFactory;
}
