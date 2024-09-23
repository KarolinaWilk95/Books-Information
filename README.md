# Books-Information

This is simple application to collect information about books like title, author name, category, year of publishing and rating. 

**To properly use app on your computer, please follow instructions and commands in terminal.**
1. Clear the *target* directory, build the project and package the resulting JAR file into the *target* directory.   

   ```cmd
   .\mvnw.cmd clean package
   ```
2. Create, start and attach container to service with a functioning connection PostgreSQL. This command also build an image based on instructions in Dockerfile. 
   ```cmd
   docker compose up
   ```

Sources: 
- (definition) [link] https://jenkov.com/tutorials/maven/maven-commands.html
- (definition) [link] https://docs.docker.com/