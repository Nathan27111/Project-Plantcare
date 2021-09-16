<?php

namespace Database\Factories;

use App\Models\PlantUser;
use Illuminate\Database\Eloquent\Factories\Factory;

class PlantUserFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = PlantUser::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'plant_id'=>$this->faker->numberBetween(1,10),
            'user_id'=>$this->faker->unique()->numberBetween(1,10)
        ];
    }
}
