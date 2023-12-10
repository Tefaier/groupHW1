package HW.models;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class DefaultIOOperations {
  public static String getPath(String path) throws IOException {
    if (Path.of(path).isAbsolute()) {
      return path;
    }

    URL resource;
    if ((resource = DefaultIOOperations.class.getClassLoader().getResource(path)) != null) {
      return resource.getPath();
    } else if ((resource = DefaultIOOperations.class.getResource(path)) != null) {
      return  resource.getPath();
    }
    throw new IOException("Can't find file by path: " + path);
  }
}
