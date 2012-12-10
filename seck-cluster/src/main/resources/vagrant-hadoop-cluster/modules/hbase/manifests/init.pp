class hbase {
  require hadoop
  $hbase_home = "/opt/hbase"
  $hbase_version = "0.94.2"
  $hbase_tarball = "http://apache.mirrors.pair.com/hbase/hbase-${hbase_version}/hbase-${hbase_version}.tar.gz"
	
exec { "download_hbase":
  path => $path,
  command => "wget -O /tmp/hbase.tar.gz $hbase_tarball",
  unless => "ls /opt | grep hbase-${hbase_version}",
  require => Package["openjdk-6-jdk"]
}

exec { "unpack_hbase" :
  path => $path,
  command => "tar -zxf /tmp/hbase.tar.gz -C /opt",
  creates => "${hbase_home}-${hbase_version}",
  require => Exec["download_hbase"]
}

}