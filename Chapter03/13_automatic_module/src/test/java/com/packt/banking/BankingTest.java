package com.packt.banking;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class BankingTest
{
    @Test public void test_simpleInterest()
    {
        Double simpleInterest = Banking.simpleInterest(1000.0, 10.0, 2);
        assertThat(simpleInterest).isEqualTo(200.0);
    }
}
