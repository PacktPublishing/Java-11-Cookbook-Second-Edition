package com.packt.cookbook.ch15_new_way.c_enum.api;

public interface Car extends Vehicle {
    int getPassengersCount();

    /**
     * Returns the weight of the car.
     *
     * @return the weight of the car in pounds
     * @deprecated As of API 2.1, to avoid adding unit specific methods, use
     *             {@link #getMaxWeight(WeigthUnit weightUnit)} instead.
     */
    @Deprecated(since = "2.1", forRemoval = true)
    int getMaxWeightPounds();

    /**
     <h2>Returns the weight of the car.</h2>
     <article>
     <h3>If life would be often that easy</h3>
     <p>Do you include unit of measurement into the method name or not?</p>
     <p>The new signature demonstrates extensible design of an interface.</p>
     </article>
     <aside><p> A few other examples could be found
     <a href="http://www.nicksamoylov.com/category/programming/">here</a>.
     </p></aside>
     * @param weightUnit an element of the enum Car.WeightUnit
     * @return the weight of the car in the specified units of weight measurement
     */
    int getMaxWeight(WeigthUnit weightUnit);

    enum WeigthUnit {
        Pound,
        Kilogram
    }
}

