package cn.hqweay.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * @description: 绝对路径才行
 * Created by hqweay on 2019/8/15 16:40
 */
@Deprecated
public class FileSystemResource extends AbstractResource {
  private final String path;

  private final File file;

  private final Path filePath;

  public FileSystemResource(String path) {
    assert (path != null) : "Path must not be null";
    this.path = path;
    this.file = new File(path);
    this.filePath = this.file.toPath();
  }

  @Override
  public InputStream getInputStream() throws IOException {
    try {
      return Files.newInputStream(this.filePath);
    }
    catch (NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }
}
