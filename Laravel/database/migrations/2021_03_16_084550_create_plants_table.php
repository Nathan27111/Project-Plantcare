<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreatePlantsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('plants', function (Blueprint $table) {
            $table->id('plant_id');
            $table->foreignId('room_id')->on('rooms');
            $table->integer('needs_water');
            $table->dateTime('last_watered_at')->nullable();
            $table->string('plant_description');
            $table->integer('flowers')->default('0');
            $table->integer('height')->default('20');
            $table->boolean('is_blooming');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('plants');
    }
}
