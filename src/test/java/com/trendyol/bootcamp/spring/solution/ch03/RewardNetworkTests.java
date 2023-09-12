package com.trendyol.bootcamp.spring.solution.ch03;

import com.trendyol.bootcamp.spring.solution.ch03.domain.AccountContribution;
import com.trendyol.bootcamp.spring.solution.ch03.domain.Dining;
import com.trendyol.bootcamp.spring.solution.ch03.domain.MonetaryAmount;
import com.trendyol.bootcamp.spring.solution.ch03.domain.RewardConfirmation;
import com.trendyol.bootcamp.spring.solution.ch03.service.RewardNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TODO-09: Make sure the setUp() method in the RewardNetworkTests class is annotated with @BeforeEach.
 * - In the setUp() method, create an application context using
 *   this configuration class - use run(..) static method of
 *   the SpringApplication class
 * - Then get the 'rewardNetwork' bean from the application context
 *   and assign it to a private field for use later.
 *
 * TODO-10: We can test the setup by running an empty test.
 * - Run the test testRewardForDining. If your setup() is working, you get a green bar.
 *
 * TODO-11: Finally run a real test.
 * - Copy the unit test (the @Test method) from
 *   RewardNetworkImplTests#testRewardForDining() - we are testing
 *   the same code, but using a different setup.
 * - Run the test - it should pass if you have configured everything
 *   correctly. Congratulations, you are done.
 * - If your test fails - did you miss the import in TODO-7 above?
 */
class RewardNetworkTests {

    private RewardNetwork rewardNetwork;

    @BeforeEach
    public void setUp() {
        // Create the test configuration for the application:

        ApplicationContext context = SpringApplication.run(TestInfrastructureConfig.class);

        // Get the bean to use to invoke the application
        rewardNetwork = context.getBean(RewardNetwork.class);

    }

    @Test
    void testRewardForDining() {
        Dining dining = Dining.createDining("100.00", "1234123412341234", "1234567890");

        RewardConfirmation confirmation = rewardNetwork.rewardAccountFor(dining);

        assertNotNull(confirmation);
        assertNotNull(confirmation.getConfirmationNumber());

        AccountContribution contribution = confirmation.getAccountContribution();
        assertNotNull(contribution);

        assertEquals("123456789", contribution.getAccountNumber());

        assertEquals(MonetaryAmount.valueOf("8.00"), contribution.getAmount());

        assertEquals(2, contribution.getDistributions().size());

        assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Annabelle").getAmount());
        assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Corgan").getAmount());
    }
}