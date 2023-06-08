# Estimation of the Size of Joins

This is a Java application that calculates and prints the estimated join size, actual join size, and the estimation error for two tables in a database. The application takes the name of the two tables as command-line arguments along with the JDBC URL, username, and password for the database connection.

## Run-time Arguments 

* `url`: The JDBC URL for the database connection 
* `username`: The username for the database connection
* `password`: The password for the database connection 
* `firstRelation`: The first relation for the natural join 
* `secondRelation`: The second relation for the natural join

## Prerequisites

To run this application, you need to have the following:

* Java Development Kit (JDK) version 8 or higher
* [PostgreSQL server](https://www.postgresql.org/) 
* [PostgreSQL JDBC driver](https://jdbc.postgresql.org/): The .jar file needs to be accessible when compiling your code. If it is not in the path and you are using the Eclipse IDE, you can add it to your project by going to “Project > Properties > Java Build Path > Add External JARs” and selecting the .jar file.
* [University database](https://www.db-book.com/university-lab-dir/sample_tables-dir/): Download and import the large dataset from the textbook site.

## Usage

To use the application, compile and execute the main method of the Application class with the appropriate command-line arguments.

    java estimator.Application <url> <username> <password> <firstRelation> <secondRelation>
    
## Exceptions

The application may throw the following exceptions:

* `MissingArgumentException`: When any of the required command-line arguments are missing.
* `SQLException`: When an error occurs during the database connection or query execution.
* `IllegalArgumentException`: When the given relation names do not exist in the database.

## Author
This application was created by Evangeli Silva [esilva2@albany.edu](esilva2@albany.edu) as part of the ICSI 508 course at the University at Albany.
