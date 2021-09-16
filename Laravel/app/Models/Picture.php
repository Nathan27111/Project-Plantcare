<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Picture extends Model
{
    protected $fillable = [
        'plant_id',
        'photo',
        'taken_at'
    ];

    public function plants(){
    return $this->belongsTo('App\Plant');
    }
    protected $primaryKey = "picture_id";
    use HasFactory;
    public $timestamps = false;
}
