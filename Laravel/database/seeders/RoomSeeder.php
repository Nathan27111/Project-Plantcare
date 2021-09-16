<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Room;

class RoomSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        Room::factory()
            ->count(10)
            ->create();
        $rooms = [
            [
                "user_id" => 11,
                "room_description" => "Bedroom"],
            ["user_id" => 11,
                "room_description" => "Living room"]
        ];
        foreach ($rooms as $room) {
            Room::create($room);

        }
    }
}
