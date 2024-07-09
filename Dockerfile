# Step 1: Set up the build environment using Amazon Corretto JDK 21
FROM amazoncorretto:21 as build

# Set the working directory in the Docker image
WORKDIR /

# Copy the project files into the Docker image
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Build the application using the Gradle wrapper
RUN ./gradlew build -x test

# Step 2: Prepare the runtime environment using Amazon Corretto JDK 21
FROM amazoncorretto:21

EXPOSE $PORT

# Copy the built JAR from the build stage
COPY --from=build /build/libs/smart-water-plan.jar /app/smart-water-plan-plain.jar

# Command to run the application
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/smart-water-plan-plain.jar"]