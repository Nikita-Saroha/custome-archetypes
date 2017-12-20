FROM repo.corp.cox.com:5001/alpine-java8:latest
LABEL maintainer Mayuresh Krishna <mayureshkrishna@gmail.com>
EXPOSE 8080
VOLUME /tmp
COPY newrelic.yml newrelic.yml
RUN       wget -q "http://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic.jar" -O /newrelic.jar
ADD ${artifactId}*.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -javaagent:newrelic.jar -jar /app.jar