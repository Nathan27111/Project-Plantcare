<?php

namespace Database\Seeders;

use App\Models\PlantUser;
use Illuminate\Database\Seeder;

class PlantUserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        PlantUser::factory()
            ->count(10)
            ->create();
        $plantUsers = [[
            "plant_id" => 5,
            "user_id" => 11], ["plant_id" => 4,
            "user_id" => 11]
        ];

        foreach ($plantUsers as $plantUser) {
            PlantUser::create($plantUser);

        }

    }
}
