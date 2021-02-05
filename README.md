# spring-boot-elk-stack-integration
Spring Boot Logs with Elasticsearch, Logstash, Kibana and Spring Cloud Sleuth: a library available as a part of Spring Cloud project permit you to track subsequent microservices by adding the appropriate headers to the HTTP requests: baggage-keys or propagation-keys. The library is based on the MDC (Mapped Diagnostic Context Log4j of a thread), where you can easily extract values put to context 
and display them in the logs.

I want to send my logs (produced with Spring Boot & upgraded by Spring Sleuth) from a java application to an ElasticSearch/Logstash/Kibana stack. 

![image](https://github.com/antoniopaolacci/spring-boot-elk-stack-integration/blob/master/img/log-on-elk-architecture.png)

# Configure spring boot's log4j #

To have Logstash to ship logs to ElasticSearch then we need to have our application to store logs in a file. 
There are obviously other ways to achieve the same thing as well i.e. Anyhow, from first, let's configure Spring Boot's log file.

 ```xml
<springProperty scope="context" name="SPRING_APP_NAME" source="spring.application.name"/>
<property name="LOG_DIR" value="fs/app/logs" />
 
<appender name="FILE" class="ch.qos.logback.core.FileAppender">
	<file>${LOG_DIR}/basic-microservice-one.log</file>
	<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
		<Pattern>
			%d{dd-MM-yyyy HH:mm:ss.SSS}[%5p][${SPRING_APP_NAME}][session-id=%X{session-id}][parentSpanId=%X{X-B3-ParentSpanId}][traceId=%X{X-B3-TraceId}][spanId=%X{X-B3-SpanId}][spanExport=%X{X-Span-Export}][%c{36}::%L::%M] -- %m%n
		</Pattern>
	</encoder>
</appender>

<springProfile name="dev">
	<root level="info">
		<appender-ref ref="CONSOLE" />
	</root>	
	<logger level="debug" name="it.app.tracing.package.example" additivity="false">
		<appender-ref ref="FILE" />
	</logger>
	<logger level="debug" name="it.app.layer.service" additivity="false">
		<appender-ref ref="FILE" />
	</logger>
</springProfile>
 ```


# Configure Logstash to understand spring boot's log format #

![image](https://github.com/antoniopaolacci/spring-boot-elk-stack-integration/blob/master/img/logstash-config.png)


# Start Elasticsearch #

Change the following start cmd depending on your installation folder:

```bat
D:\elasticsearch-6.5.2\elasticsearch-6.5.2\bin\elasticsearch.bat
```
 
# Start Kibana #

Change the following start cmd depending on your installation folder:

```bat
D:\kibana-6.5.2-windows-x86_64\kibana-6.5.2-windows-x86_64\bin\kibana.bat
```

# Start Logstash #

Change the following start cmd depending on your installation folder:

```bat
D:\logstash-6.5.3\logstash-6.5.3\bin\logstash.bat -f D:\logstash-6.5.3\logstash-6.5.3\config\spring-boot-elk-stack-integration.conf
```

# Display available index on ElesticSearch #

```
http://localhost:9200/_cat/indices
```

![image](https://github.com/antoniopaolacci/spring-boot-elk-stack-integration/blob/master/img/elk-index.png)


# Send logs directly to Logstash, use Spring Sleuth, then display on ElasticSearch/Kibana stack

To ship logs to ElasticSearch using Logstash such as such, as configuring <i>logback.xml</i> to use TCP appender to send logs to a remote Logstash instance via TCP. 

In the <i>src/main/resources</i> directory of a java project configure the fragment on a <i>.xml</i> file. We can configure which logging fields are sending 
to Logstash by declaring tags like mdc, logLevel, message, etc. We are also appending service name field for Elasticsearch index creation.

```xml
<appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
	<destination>127.0.0.1:2000</destination>
	<encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
		<providers>
			<mdc />
			<context />
			<logLevel />
			<loggerName />
			<pattern>
				<pattern>{ "serviceName": "one" }</pattern>
			</pattern>
			<threadName />
			<message />
			<logstashMarkers />
			<stackTrace />
		</providers>
	</encoder>
</appender>
```

Start Logstash with the following -f <i>.conf</i> file and with the subsequent command:

```js
	input {
	    tcp {
	        port => 2000 
			codec => "json"
	     }
	 }

	output {
	    elasticsearch {
	        hosts => ["localhost:9200"] 
			index => "micro-%{serviceName}"
	    }
	}
```


```
D:\logstash-6.5.3\logstash-6.5.3\bin\logstash.bat -f D:\logstash-6.5.3\logstash-6.5.3\config\logstash-my-microservice-architecture.conf
```

Search on Kibana Console and then create <b>micro-*</b> index:

![image](https://github.com/antoniopaolacci/spring-boot-elk-stack-integration/blob/master/img/kibana-index.png)

Monitor all the Microservices with Kibana and Sleuth/custom MDC key:

![image](https://github.com/antoniopaolacci/spring-boot-elk-stack-integration/blob/master/img/monitoring-architecture-with-kibana.png)


