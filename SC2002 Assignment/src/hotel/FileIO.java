package sc2002Proj;
import java.util.List;

public class FileIO {
	interface ReadWrite {
		public void read(String filename);
		public void write(String filename, List data);
	}
}
