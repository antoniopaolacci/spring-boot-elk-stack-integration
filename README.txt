## Setting java 8 ##
setJDK.cmd 8

## Setting maven 3 ##
setMVN.cmd 8

## Import the project structure in Eclipse ##
mvn eclipse:eclipse

# To build and run unit-test #
mvn install


## To build the project without test ##
mvn install -DskipTests

## Run ##
java -jar -DSEQUENZA=1 -Dspring.profiles.active=dev -Xmx96m -Xss512k D:\git-repo\spring-boot-elk-stack-integration.git\target\spring-boot-elk-stack-0.0.1.jar

## Rest Http Endpoint ##
http://localhost:12001/api/v1/test/1
http://localhost:12001/api/v1/exception