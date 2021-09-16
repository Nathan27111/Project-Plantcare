<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\User;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
    User::factory()
         ->count(10)
         ->create();

    $user = ["email" => "test@test.be",
        "password" => bcrypt("admin123"),
        "first_name" => "test",
        "last_name" => "test"];
       User::create($user);
       }
}
