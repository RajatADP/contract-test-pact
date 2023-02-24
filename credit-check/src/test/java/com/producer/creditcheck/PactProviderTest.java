package com.producer.creditcheck;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("creditCheck")
//@PactFolder("src/main/java/pacts") //pact on local repo
@PactBroker(url = "https://rajatmishra.pactflow.io/",
        authentication = @PactBrokerAuth(token = "ev2_6hqwATAlBVoEW-SqJw"))

public class PactProviderTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    public void setup(PactVerificationContext context) {

        context.setTarget(new HttpTestTarget("localhost", port)
        );
        System.setProperty("pact_do_not_track", "true");
        System.setProperty("pact.verifier.publishResults", "true");
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
