package com.example.demo.cases.test.postgres

import groovy.sql.Sql
import spock.lang.Specification;

 class PostgresSpecification extends Specification{

     static postgresContainer = PostgresContainer.getInstance()
     static Sql sql

     static {
         postgresContainer.start()
         System.setProperty("spring.datasource.url", postgresContainer::getJdbcUrl())
         System.setProperty("spring.datasource.username", postgresContainer::getUsername())
         System.setProperty("spring.datasource.password", postgresContainer::getPassword())
         System.setProperty("spring.datasource.driver-class-name", postgresContainer::getDriverClassName())

         sql = Sql.newInstance(
                 postgresContainer::getJdbcUrl(),
                 postgresContainer::getUsername(),
                 postgresContainer::getPassword(),
                 postgresContainer::getDriverClassName()
         )
     }

}
