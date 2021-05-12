package com.apgsga.apitestingsample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class ApiTestingSampleApplication

fun main(args: Array<String>) {
    runApplication<ApiTestingSampleApplication>(*args)
}

@RestController
@RequestMapping("/api/testdata")
class TestController(val service: TestDataService) {

    @GetMapping
    fun listAll(): List<TestData> {
        var result = service.findAll()
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
        return result;
    }

    @PostMapping
    fun post(@RequestBody testData: TestData) {
        service.create(testData)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody customer: TestData) {
        assert(customer.id == id)
        service.update(customer)
    }

}

@Service
class TestDataService(val db: TestDataRepository) {

    fun findAll(): List<TestData> = db.findTestDataAll()

    fun findById(id: String): TestData? = db.findByIdOrNull(id)

    fun delete(id: String)  {
        db.deleteById(id)
    }

    fun deleteAll() {
        db.deleteAll()
    }

    fun create(testData: TestData) {
        db.save(testData)
    }

    fun update(testData: TestData) {
        db.save(testData)
    }
}

interface TestDataRepository : CrudRepository<TestData, String> {

    @Query("select * from testdata")
    fun findTestDataAll(): List<TestData>
}

@Table("TESTDATA")
data class TestData(@Id val id: String?, val text: String)