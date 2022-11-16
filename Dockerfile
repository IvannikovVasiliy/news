FROM openjdk:18
ADD ./target/news-0.0.1-SNAPSHOT.jar news.jar
ENTRYPOINT ["java", "-jar", "news.jar"]