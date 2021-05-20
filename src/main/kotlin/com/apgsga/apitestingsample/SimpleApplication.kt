package com.apgsga.apitestingsample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


// Spring Boot Application
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ApiTestingSampleApplication

fun main(args: Array<String>) {
    runApplication<ApiTestingSampleApplication>(*args)
}

// Rest Api
@RestController
@RequestMapping("/api/testdata")
@PreAuthorize("hasRole('ROLE_USER')")
class TestController(val service: TestDataService) {

    @GetMapping
    fun listAll(): List<TestData> {
        val result = service.findAll()
        println(result.toString())
        return result
    }

    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: String) = service.delete(id)

    @DeleteMapping
    fun removeAll() = service.deleteAll()


    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): TestData? {
        val result = service.findById(id)
        println(result.toString())
        return result
    }

    @PostMapping
    fun create(@RequestBody testData: TestData): TestData {
        return service.create(testData)
    }

    @PutMapping("/{id}/{calc}")
    fun calculate(@PathVariable id: String,@PathVariable calc: String):  TestData {
        return service.calculate(id,calc)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody customer: TestData): TestData {
        assert(customer.id == id)
        return service.update(customer)
    }

}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${skipSecurity}")
    val skipSecurity: Boolean = true


    override fun configure(http: HttpSecurity) {

        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests() //
            .anyRequest().authenticated()

        // Apply JWT
        http.apply(JwtTokenFilterConfigurer(skipSecurity))
    }



}

class JwtTokenFilterConfigurer(val skipSecurity : Boolean) :
    SecurityConfigurerAdapter<DefaultSecurityFilterChain?, HttpSecurity>() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val customFilter = JwtTokenFilter(skipSecurity)
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

}
class JwtTokenFilter(val skipSecurity: Boolean) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearerToken = httpServletRequest.getHeader("Authorization")

        if (skipSecurity || bearerToken != null && bearerToken.startsWith("Bearer ")) {
            var userDetails : UserDetails = User
                .withUsername("testuser")//
                .password("testpasswd")//
                .authorities("ROLE_USER")//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities);

        } else {
            SecurityContextHolder.clearContext()
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

}

// Service Layer

@Service
class TestDataService(val db: TestDataRepository) {

    @Autowired
    lateinit var calculator : Calculator

    fun findAll(): List<TestData> = db.findTestDataAll()

    fun findById(id: String): TestData? = db.findByIdOrNull(id)

    fun delete(id: String)  {
        db.deleteById(id)
    }

    fun deleteAll() {
        db.deleteAll()
    }

    fun calculate(id: String, calc: String) : TestData {
        val result =  calculator.calculate(calc)
        val toUpdate = findById(id)
        toUpdate!!.textData = "Calculation Result of $calc is $result"
        return update(toUpdate)
    }

    fun create(testData: TestData): TestData {
        return db.save(testData)
    }

    fun update(testData: TestData): TestData {
        return db.save(testData)
    }
}



interface TestDataRepository : CrudRepository<TestData, String> {

    @Query("select * from testdata")
    fun findTestDataAll(): List<TestData>
}

@Table("TESTDATA")
data class TestData(@Id val id: String?, var textData: String)