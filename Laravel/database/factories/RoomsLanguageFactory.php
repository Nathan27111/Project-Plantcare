<?php

namespace Database\Factories;

use App\Models\Model;
use App\Models\RoomsLanguage;
use Illuminate\Database\Eloquent\Factories\Factory;

class RoomsLanguageFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = RoomsLanguage::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'room_room_id'=> $this->faker->unique()->numberBetween(1,10),
            'room_description'=> $this->faker->randomElement(["Kitchen", "Living Room", "Hall", "Study"])
        ];
    }
}
