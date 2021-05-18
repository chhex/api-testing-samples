package com.apgsga.apitestingsample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

// Spring Boot Application
@SpringBootApplication
class ApiTestingSampleApplication

fun main(args: Array<String>) {
    runApplication<ApiTestingSampleApplication>(*args)
}

// Rest Api
@RestController
@RequestMapping("/api/testdata")
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