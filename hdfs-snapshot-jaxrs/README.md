HDFS Snapshot View
=======================

Provides a very simple Ambari view which searches the HDFS filesystem for .snapshot directories. 
Currently, there is no simple command-line tool for viewing all the existing snapshots in the system. There is the
```hdfs lsSnapshottableDir``` command, but it does not distinguish between snapshottable directories, and directories
which actually have snapshots.

Building
--------

```
./gradlew build
```

Deploying
---------

1. scp ```build/libs/snapshot-view.jar``` to ```/var/lib/ambari-server/resources/views```.

2. ```ambari-server restart```

3. Log in to Ambari Web as an admin user, go to the Manage Ambari page, click Views, and create a new instance for 
HDFS-SNAPSHOTS (after deployment is complete). When creating a new instance, you will need to provide the filesystem
URL as a parameter. For example, ```hdfs://sandbox.hortonworks.com:8020```

Note: This view only works on Ambari 1.7.0+.