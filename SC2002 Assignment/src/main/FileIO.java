package main;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface to implement read and write method.
 * @author BRYAN WU JIAHE
 * @version 1.0
 * @since 2022-04-17
 */
public class FileIO {
	public interface ReadWrite {
		public ArrayList<String> read(String filename);
		public void write(String filename, List<String> data);
	}
}
