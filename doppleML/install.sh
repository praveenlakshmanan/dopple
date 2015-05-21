
#!/bin/sh
#!bin/bash

#To download and install Java 7

echo debconf shared/accepted-oracle-license-v1-1 select true | \

sudo debconf-set-selections && echo debconf shared/accepted-oracle-license-v1-1 seen true | \

sudo debconf-set-selections && sudo add-apt-repository ppa:webupd8team/java && sudo apt-get update && sudo apt-get install oracle-java7-installer && sudo apt-get install oracle-java7-set-default


#To get the files from elastic search repository
sudo wget https://download.elastic.co/elasticsearch/elasticsearch/elasticsearch-1.4.3.deb

#Run apt-get update and the repository is ready for use
sudo dpkg -i elasticsearch-1.4.3.deb


#Configure Elasticsearch to automatically start during bootup
sudo update-rc.d elasticsearch defaults 95 10

#Download Logstash 1.4.2 and get the files from logstash repository
sudo wget https://download.elastic.co/logstash/logstash/packages/debian/logstash_1.4.2-1-2c0f5a1_all.deb

sudo dpkg -i logstash_1.4.2-1-2c0f5a1_all.deb
