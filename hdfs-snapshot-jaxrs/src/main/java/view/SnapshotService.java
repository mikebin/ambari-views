package view;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SnapshotService {
  private Configuration conf;
  private FileSystem fs;

  public SnapshotService(String fsUrl) throws IOException, InterruptedException {
    conf = new Configuration();
    conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
    conf.set("fs.webhdfs.impl", "org.apache.hadoop.hdfs.web.WebHdfsFileSystem");
    conf.set("fs.file.impl", "org.apache.hadoop.fs.LocalFileSystem");
    conf.set("fs.defaultFS", fsUrl);
    fs = FileSystem.get(URI.create(fsUrl), conf, "hdfs");
  }

  public SnapshotInfo[] getSnapshotDirectories() throws IOException {
    List<SnapshotInfo> result = getNonEmptySnapshotDirectories(fs, new Path("/"));
    return result.toArray(new SnapshotInfo[result.size()]);
  }

  private List<SnapshotInfo> getNonEmptySnapshotDirectories(final FileSystem fs, Path path) throws IOException {
    List<SnapshotInfo> results = new ArrayList<>();

    Path snapshot = new Path(path, ".snapshot");
    if (fs.isDirectory(snapshot)) {
      for (FileStatus status : fs.listStatus(snapshot)) {
        results.add(new SnapshotInfo(status.getOwner(), Path.getPathWithoutSchemeAndAuthority(status.getPath()).toString(),
            new Date(status.getModificationTime())));
      }
    }

    FileStatus[] directories = fs.listStatus(path, new PathFilter() {
      @Override
      public boolean accept(Path path) {
        try {
          return fs.isDirectory(path);
        }
        catch (IOException e) {
          return false;
        }
      }
    });
    for (FileStatus directory : directories) {
      results.addAll(getNonEmptySnapshotDirectories(fs, directory.getPath()));
    }

    return results;
  }
}