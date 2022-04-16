package main;

import java.util.ArrayList;
import java.util.List;

public class FileIO {
	public interface ReadWrite {
		public ArrayList<String> read(String filename);
		public void write(String filename, List<String> data);
	}
}