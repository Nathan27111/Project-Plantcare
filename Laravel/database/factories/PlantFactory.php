<?php

namespace Database\Factories;

use App\Models\Plant;
use Illuminate\Database\Eloquent\Factories\Factory;

class PlantFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Plant::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'room_id'=>$this->faker->numberBetween(1,10),
            'needs_water'=> $this->faker->numberBetween(1,7),
            'last_watered_at'=>$this->faker->dateTimeBetween('-1 days', 'now'),
            'plant_description'=>$this->faker->realText(),
            'flowers'=>$this->faker->numberBetween(0, 5),
            'height'=>$this->faker->numberBetween(1, 80),
            'is_blooming'=>$this->faker->boolean
        ];
    }
}
