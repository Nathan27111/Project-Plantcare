<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;


class DatabaseSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $this->call([
        UserSeeder::class,
        RoomSeeder::class,
//         RoomsLanguageSeeder::class,
        PlantSeeder::class,
        PictureSeeder::class,
        PlantUserSeeder::class
    ]);
    }
}
