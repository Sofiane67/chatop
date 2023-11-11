# Chatop: Spring Boot Project

Follow the steps below to run the Chatop project.

## Cloning the Project

Start by cloning this repository to your local machine:

```shell
git clone https://github.com/Sofiane67/chatop.git
```

## Configuring Environment Variables

1. Create a `.env` file at the root of the project with the configuration parameters for your database (see `.env-example` file for an example):

```properties
DATABASE_URL=jdbc:mysql://localhost:3307/chatop
DATABASE_USERNAME=root
DATABASE_PASSWORD=root
PORT_API=3001
JWT_SECRET=
JWT_EXPIRATION=
CLOUDINARY_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=
```

Make sure to specify appropriate values for `DATABASE_URL`, `DATABASE_USERNAME`, and `DATABASE_PASSWORD`. Other values can be configured according to your needs.

## Installing Required Tools

Make sure to install the following tools on your system:

- [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

## Database Configuration

### Using Docker

1. Go to the `src/main/resources` folder of the project.

2. Launch Docker Compose to start the MySQL database and Adminer:

```shell
docker-compose up -d
```

By executing the `docker-compose.yml` file, MySQL is configured to listen on port 3307. Make sure to update the environment variable in the `.env` file accordingly.

3. Access Adminer at the following address: [http://localhost:8080/](http://localhost:8080/)

4. Log in to Adminer with the following credentials:

   - User: root
   - Password: root

5. Click on the "SQL Command" link in Adminer and execute the SQL script found in the `src/main/resources/init.sql` file. This script creates the `chatop` database, tables, and a test user.

### Without Docker

1. Install MySQL on your machine.

2. Execute the SQL script found in the `src/main/resources/init.sql` file to create the `chatop` database, tables, and a test user.

### User test
```text
email : test@test.com
password: test!31
```

## Starting the Application

1. Open a terminal and navigate to the project folder:

```shell
cd <path_to_project_folder>
```

2. Run the application using Maven in the command line:

```shell
mvn spring-boot:run
```

The Spring Boot application should start and be accessible at [http://localhost:3001](http://localhost:3001).

## Front-end Application

The corresponding front-end project is available at the following link: [https://github.com/Sofiane67/Estate.git](https://github.com/Sofiane67/Estate.git).

To run the front-end application:

1. Clone the front-end repository to your machine.

2. Navigate to the front-end project folder and install Node.js dependencies using the following command:

```shell
npm install
```

3. Start the front-end application with the command:

```shell
npm run start
```

The front-end application will be accessible at [http://localhost:3000](http://localhost:3000).

## Postman

For Postman, import the collection located in the `resources/postman/rental.postman_collection.json` folder.

## Documentation

The API documentation is available at [http://localhost:3001/api/swagger-ui/index.html](http://localhost:3001/api/swagger-ui/index.html).