<?php

namespace Database\Factories;

use App\Models\Picture;
use Illuminate\Database\Eloquent\Factories\Factory;

class PictureFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Picture::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'plant_id' => $this->faker->numberBetween(1,10),
            'picture' => $this->faker->image(),
            'taken_at'=> $this->faker->dateTimeBetween("-14 days", "now")
        ];
    }
}
