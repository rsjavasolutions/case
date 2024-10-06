package com.example.demo.cases.test.postgres

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class PostgresContainer {

    private static def DB_NAME = "cases"

    private static PostgreSQLContainer postgresSQLContainer

    static PostgreSQLContainer getInstance() {
        if (postgresSQLContainer == null) {
            initializeContainer()
        }
        postgresSQLContainer
    }

    private static void initializeContainer() {
        postgresSQLContainer = new PostgreSQLContainer(DockerImageName.parse("postgres"))
                .withDatabaseName(DB_NAME)
                .withReuse(true)
    }
}
