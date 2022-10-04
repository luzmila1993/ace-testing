package test;

import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.ibm.integration.test.v1.NodeSpy;
import com.ibm.integration.test.v1.NodeStub;
import com.ibm.integration.test.v1.SpyObjectReference;
import com.ibm.integration.test.v1.TestMessageAssembly;
import com.ibm.integration.test.v1.TestSetup;
import com.ibm.integration.test.v1.exception.TestException;

import static com.ibm.integration.test.v1.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class APIFlow_Test {

	@AfterEach
	public void cleanupTest() throws TestException {
		// Ensure any mocks created by a test are cleared after the test runs 
		TestSetup.restoreAllMocks();
	}
	
	@Test
	public void TestCase_001() throws TestException {
		// Define the Spy on the HttpInput node we will use for calling tye flow
		SpyObjectReference httpInputReference = new SpyObjectReference().application("ace-apiname")
				.messageFlow("api1").node("HTTP Input");
		NodeSpy httpInputSpy = new NodeSpy(httpInputReference);
		
		// Define the Spy on the Process Results compute node we will use for verifying the result
		//SpyObjectReference resultsReference = new SpyObjectReference().application("ace-apiname")
				//.messageFlow("api1").node("Shared Library Sub Flow");
		//NodeSpy resultsSpy = new NodeSpy(resultsReference);
				
		// Declare a new TestMessageAssembly object for the message being sent into the node
		TestMessageAssembly inputMessageAssembly = new TestMessageAssembly();
		try {
			String messageAssemblyPath = "/HTTPInput_result.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			inputMessageAssembly.buildFromRecordedMessageAssembly(messageStream);
		} catch (Exception ex) {
			throw new TestException("Failed to load input message", ex);
		}
		
		// Create a Message Assembly from the expected output mxml resource
		TestMessageAssembly expectedMessageAssembly = new TestMessageAssembly();
		try {
			String messageAssemblyPath = "/SharedLibrary_result.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			expectedMessageAssembly.buildFromRecordedMessageAssembly(messageStream);
		} catch (Exception ex) {
			throw new TestException("Failed to load input message", ex);
		}
		
		// Define the Stub, and configure it to return it's recorded message
		SpyObjectReference subflowReference = new SpyObjectReference().application("ace-apiname")
				.messageFlow("api1").node("Get properties of Policy");
		NodeStub nodeStub = new NodeStub(subflowReference);
		
		// Create a Message Assembly and load it with the recorded result for 'Get properties of Policy'
		TestMessageAssembly subflowResultMessageAssembly = new TestMessageAssembly();
		try {
			String messageAssemblyPath = "/ComputeGetPolicy_response.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			subflowResultMessageAssembly.buildFromRecordedMessageAssembly(messageStream);
		} catch (Exception ex) {
			throw new TestException("Failed to load input message", ex);
		}
		
		// And program stub to return this dummy result instead of calling the subflow
		nodeStub.onCall().propagatesMessage("in", "out", subflowResultMessageAssembly);
		
		
		// Now call the flow by propagating the message from the HTTPInput out terminal
		httpInputSpy.propagate(inputMessageAssembly, "out");
		
		// Declare a new TestMessageAssembly object for the actual propagated message
		//TestMessageAssembly actualMessageAssembly = resultsSpy.propagatedMessageAssembly("Output", 1);

		// Assert that the actual message tree matches the expected message tree
		//assertThat(actualMessageAssembly, equalsMessage(expectedMessageAssembly));		
	}
}
