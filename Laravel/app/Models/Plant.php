<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Plant extends Model
{

    protected $fillable = [
        'needs_water',
        'last_watered_at',
        'plant_description',
        'room_id',
        'is_blooming',
        'flowers',
        'height'
    ];

    public function pictures(){
    return $this->hasMany('App\Picture');
    }

    public function rooms(){
    return $this->belongsToMany('App\Room');
    }

    public function plantUsers(){
        return $this->belongsTo('App\PlantUser');
    }

    protected $primaryKey = "plant_id";
    public $timestamps = false;
    use HasFactory;

}
