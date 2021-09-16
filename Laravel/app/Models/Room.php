<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Room extends Model
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'room_description'
    ];
    protected $primaryKey = "room_id";

    public function users(){
        return $this->belongsTo('App\User');
    }

    public function plants(){
        return $this->hasMany('App\Plant');
    }

//    public function translations(){
//        return $this->hasMany(RoomsLanguage::class);
//    }

    public $timestamps = false;

}
