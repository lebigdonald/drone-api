# Drone Loading System

A Restful Backend Service which provides creation, loading of medications (Tools) on the Drone.

Drone are loaded one at a time with validation applied checking both name and code. Periodic Audit is done 
every 3 minutes in the application. Can be seen in the DroneService file in case needs to be updated.

Integration and Unit Test has been done.

### To Facilitate The Execution of the App
>A script is provided `MavenBash` in other to clean, package application then finally run the
database in a Docker container.
> 
> Open the terminal at the root of the project and type the command `./Launch.sh` to run the file
> 
> The database uses PORT: 3300 in order to avoid port conflicts.
> The App runs on 9000

### Repository
> Here's the link to the repository
> 
> https://github.com/lebigdonald/drone.git

### NB
> To Test Rest Endpoints need to run the application the database will already be launch and running
>
>  Postman Endpoints have been provided to ease testing of endpoints. Just need to import the file API_Drone_Endpoint
