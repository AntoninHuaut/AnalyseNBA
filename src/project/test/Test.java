package project.test;

import java.io.IOException;
import java.util.ArrayList;

public interface Test {

	public void test(ArrayList<String> listNames, String trainName);
	public void checkResult(ArrayList<String> listNames, String trainName) throws IOException;
}
