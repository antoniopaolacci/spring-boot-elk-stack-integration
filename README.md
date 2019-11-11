# spring-boot-elk-stack-integration
Spring Boot Logs with Elasticsearch, Logstash and Kibana.

I want to send logs from a java app to an elastic search/kibana stack... 

![image](https://github.com/antoniopaolacci/spring-boot-elk-stack-integration/blob/master/log-on-elk-architecture.png)

# Configure spring boot’s log #

To have Logstash to ship logs to ElasticSearch then we need to have our application to store logs in a file. 
There are obviously other ways to achieve the same thing as well i.e. 
To ship logs to ElasticSearch using Logstash such as such, as configuring logback to use TCP appender to send logs to a remote Logstash instance via TCP. 
Anyhow, let’s configure Spring Boot’s log file.

 ```
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
			<appender-ref ref="FILE" />
		</root>	
		<logger level="debug" name="it.app.tracing.package.example" additivity="false">
			<appender-ref ref="FILE" />
		</logger>
		<logger level="debug" name="it.app.layer.service" additivity="false">
			<appender-ref ref="FILE" />
		</logger>
	</springProfile>
 ```


# Configure Logstash to understand spring boot’s log format #

![image](https://github.com/antoniopaolacci/spring-boot-elk-stack-integration/blob/master/logstash-config.png)



# Start Elasticsearch #

```
D:\elasticsearch-6.5.2\elasticsearch-6.5.2\bin\elasticsearch.bat
```
 
# Start Kibana #

```
D:\kibana-6.5.2-windows-x86_64\kibana-6.5.2-windows-x86_64\bin\kibana.bat
```

# Start Logstash #

```
D:\logstash-6.5.3\logstash-6.5.3\bin\logstash.bat -f D:\logstash-6.5.3\logstash-6.5.3\config\spring-boot-elk-stack-integration.conf
```