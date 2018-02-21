# RESTClient
Egyszerű példa REST kliens alkalmazás a példa RESTServer-hez

Environment

   - Install Java JDK8 (http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html#javasejdk)
   - Install Maven (https://maven.apache.org/)
   - Install Git (https://git-scm.com/downloads)

Running

  1. Clone the repository: git clone https://github.com/pekmil/RESTServer.git
  2. Run: mvn clean install
  3. Start generated JAR file (from the target directory):
     - REST client mode: java -jar RESTClient-1.0-SNAPSHOT-jar-with-dependencies.jar REST username
     - WebSocket client mode: java -jar RESTClient-1.0-SNAPSHOT-jar-with-dependencies.jar WS
