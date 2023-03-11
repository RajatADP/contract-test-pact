package com.producer.creditcheck;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("creditCheck")
//@PactFolder("src/main/java/pacts") //pact on local repo
//@PactBroker(url = "https://rajatmishra.pactflow.io/",
//        authentication = @PactBrokerAuth(token = "ev2_6hqwATAlBVoEW-SqJw"))
@PactBroker(url = "http://localhost:9292", providerTags = "latest")
public class PactProviderTest {

    @LocalServerPort
    public int port;

    @BeforeAll
    static void enablePublishingPact() {
        System.setProperty("pact.verifier.publishResults", "true");
        //tags to test. Other consumer tags will be ignored
        System.setProperty("pactbroker.consumerversionselectors.tags", "master");
        // provider version to use when publishing verification results.
        System.setProperty("pact.provider.version", "1.0.0");
        //
        System.setProperty("pact.provider.tag", "prod");

        System.setProperty("pact.provider.branch", "master");



    }

    @BeforeEach
    public void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port)
        );
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void testApplyCreditCard(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State(value = "citizen exist", action = StateChangeAction.SETUP)
    public void citizenExist() {
        //setup data

    }

    @State(value = "citizen exist", action = StateChangeAction.TEARDOWN)
    public void deleteCitizen() {
//delete data

    }
}
