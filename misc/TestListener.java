/**
* Author: Douglas Skrypa
*/

package misc;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TestListener extends RunListener
{	
	@Override
	public void testAssumptionFailure(Failure failure) {
		Sys.println("\n\tAssumption FAIL:\t" + failure.getMessage());
		Sys.println("\t" + failure.getTrace());
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		Sys.println("\n\tFAIL:\t" + failure.getMessage());
		Sys.println("\t" + failure.getTrace());
	}

	@Override
	public void testFinished(Description description) throws Exception {
		Sys.println("\t[Done]\n");
	}

	@Override
	public void testIgnored(Description description) throws Exception {
		Sys.println("Testing " + description.getClassName() + "." + description.getMethodName() + "()...\t[Ignored]\n");
	}

	@Override
	public void testRunFinished(Result result) throws Exception {
		Sys.println("\nTests completed.  Time: " + (double)result.getRunTime()/1000 + "s");
		Sys.println("Tests run: " + result.getRunCount() + "\tPassed: " + (result.getRunCount() - result.getFailureCount() - result.getIgnoreCount()) + "\tIgnored: " + result.getIgnoreCount() + "\tFailed: " + result.getFailureCount());
	}

	@Override
	public void testRunStarted(Description description) throws Exception {
		Sys.println("Testing " + description.testCount() + " case(s)...\n");
	}

	@Override
	public void testStarted(Description description) throws Exception {
		Sys.print("Testing " + description.getClassName() + "." + description.getMethodName() + "()...");
	}
}
