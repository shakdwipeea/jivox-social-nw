FROM java:8

ADD target/social-network-1.0-SNAPSHOT.jar /data/server.jar
ADD target/classes/social-network.yml /data/social-network.yml

CMD java -jar /data/server.jar server /data/social-network.yml

EXPOSE 8080
