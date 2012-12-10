include hadoop

group { "puppet":
  ensure => "present",
}

exec { 'apt-get update':
  path => $path,
  command => 'apt-get update',
}

package { "openjdk-6-jdk":
  ensure => "present",
  require => Exec['apt-get update']
}

package { "maven2":
  ensure => "present",
  require => Exec['apt-get update']
}

file { "/etc/hosts":
  source => "puppet:///modules/hadoop/hosts",
  mode => 644,
  owner => root,
  group => root,
}

file { "/etc/timezone":
  source => "puppet:///modules/hadoop/timezone",
  mode => 644,
  owner => root,
  group => root,
}

file { "/root/.ssh":
  ensure  => directory,
  owner   => root,
  group   => root,
  mode    => 700,
}

file { "/etc/profile.d/set_java_home.sh":
  source => "puppet:///modules/hadoop/set_java_home.sh",
  mode => 644,
  owner => root,
  group => root,
  require => Exec['apt-get update']
}

file { "/root/.ssh/known_hosts":
  source => "puppet:///modules/hadoop/known_hosts",
  mode => 644,
  owner => root,
  group => root,
  require => Exec['apt-get update']
}

file { "/root/.ssh/id_rsa":
  source => "puppet:///modules/hadoop/id_rsa",
  mode => 600,
  owner => root,
  group => root,
  require => Exec['apt-get update']
}
 
file { "/root/.ssh/id_rsa.pub":
  source => "puppet:///modules/hadoop/id_rsa.pub",
  mode => 644,
  owner => root,
  group => root,
  require => Exec['apt-get update']
}

file { "/root/.ssh/authorized_keys":
  source => "puppet:///modules/hadoop/authorized_keys",
  mode => 644,
  owner => root,
  group => root,
  require => Exec['apt-get update']
}

file { "/etc/ssh/ssh_config":
  source => "puppet:///modules/hadoop/ssh_config",
  mode => 644,
  owner => root,
  group => root,
  require => Exec['apt-get update']
}

node master {
  include hbase
}

node backup {
  include hbase
}

node hadoop1 {
  include hbase
}

node hadoop2 {
  include hbase
}

node hadoop3 {
  include hbase
}