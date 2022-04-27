package com.afterverse.api.configuration

import com.github.jasync.sql.db.ConnectionPoolConfiguration
import com.github.jasync.sql.db.asSuspending
import com.github.jasync.sql.db.postgresql.PostgreSQLConnectionBuilder
import io.github.cdimascio.dotenv.dotenv
//import com.typesafe.config.ConfigFactory
//import com.zaxxer.hikari.HikariConfig
//import com.zaxxer.hikari.HikariDataSource
//import io.ktor.config.*
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import org.flywaydb.core.Flyway
//import org.jetbrains.exposed.sql.Database
//import org.jetbrains.exposed.sql.transactions.transaction

val dotenv = dotenv()

object PostgresConfiguration {

    val db = PostgreSQLConnectionBuilder.createConnectionPool(
        ConnectionPoolConfiguration(
            host = dotenv["DB_HOST"],
            port = dotenv["DB_PORT"].toInt(),
            database = dotenv["DB_DATABASE"],
            username = dotenv["DB_USERNAME"],
            password = dotenv["DB_PASSWORD"]
        )
    ).asSuspending


//
//        private val appConfig = HoconApplicationConfig(ConfigFactory.load())
//        private val dbUrl = appConfig.property("db.jdbcUrl").getString()
//        private val dbUser = appConfig.property("db.dbUser").getString()
//        private val dbPassword = appConfig.property("db.dbPassword").getString()
//
//        fun init() {
//            Database.connect(hikari())
//            val flyway = Flyway.configure().dataSource(dbUrl, dbUser, dbPassword).load()
//            flyway.migrate()
//        }
//
//        private fun hikari(): HikariDataSource {
//            val config = HikariConfig()
//            config.driverClassName = "org.postgresql.Driver"
//            config.jdbcUrl = dbUrl
//            config.username = dbUser
//            config.password = dbPassword
//            config.maximumPoolSize = 3
//            config.isAutoCommit = false
//            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
//            config.validate()
//            return HikariDataSource(config)
//        }
//
//    suspend fun <T> dbQuery(block: () -> T): T =
//        withContext(Dispatchers.IO) {
//            transaction { block() }
//        }
}