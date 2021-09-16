<?php

namespace Database\Seeders;

use App\Models\RoomsLanguage;
use Illuminate\Database\Seeder;

class RoomsLanguageSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        RoomsLanguage::factory()
            ->count(10)
            ->create();
    }
}
