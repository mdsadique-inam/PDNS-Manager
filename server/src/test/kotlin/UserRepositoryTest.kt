import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.test.runTest
import mdsadiqueinam.github.io.repositories.UserRepository
import org.jetbrains.exposed.sql.Database
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.UUID
import kotlin.test.Test

class UserRepositoryTest : KoinTest {
    private val repository by inject<UserRepository>()

    init {
        val datasource = HikariDataSource(HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/powerdns"
            driverClassName = "org.postgresql.Driver"
            username = "struxe"
            password = "strongPassword"
            validate()
        })
        Database.connect(
            datasource = datasource,
        )
        startKoin {
            modules(defaultModule)
        }
    }

    @Test
    fun `should be null`() = runTest {
        val user = repository.findOrNull(UUID.randomUUID().toString())
        assert(user == null)
    }

    @Test
    fun `should throw an error`() = runTest {
        try {
            repository.find(UUID.randomUUID().toString())
        } catch (e: NoSuchElementException) {
            assert(e.message == "User not found")
        }
    }
}