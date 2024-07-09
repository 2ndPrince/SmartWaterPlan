# Step 1: Set up the build environment using Amazon Corretto JDK 21
FROM amazoncorretto:21 as build

# Set the working directory in the Docker image
WORKDIR /workspace/app

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
COPY --from=build /workspace/app/build/libs/*.jar /app/

# Command to run the application
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/app.jar"]