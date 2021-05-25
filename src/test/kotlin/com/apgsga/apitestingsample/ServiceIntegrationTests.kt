package com.apgsga.apitestingsample

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ServiceIntegrationTests(@Autowired val service: TestDataService) {

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun `Assert get all Testdata`() {
        val result = service.findAll()
        assertThat(result.isEmpty())
    }


    @Test
    fun `Assert create (post) Testdata , get all`() {
        val testData = TestData(null,"Some Testdata")
        val resultCreate =  service.create(testData)
        assertThat(resultCreate.id != null)
        val resultFindAll = service.findAll()
        assertThat(resultFindAll.size == 1)
        assertThat(resultFindAll[0].textData == "Some Testdata")
    }

    @Test
    fun `Assert create (post) Testdata , get by Id`() {
        val testData = TestData(null,"Some Testdata")
        val resultCreate =  service.create(testData)
        assertThat(resultCreate.id != null)
        var resultFindById = service.findById(resultCreate.id!!)
        assertThat(resultFindById!!.textData == "Some Other Testdata")

    }

    @Test
    fun `Assert update (put) Testdata , by  get Id`() {
        // Create Initial
        val testData = TestData(null,"Some Testdata")
        val resultCreate =  service.create(testData)
        // Update
        resultCreate.textData = "Updated"
        val resultUpdate = service.update(resultCreate)
        assertThat(resultUpdate == resultCreate)
        // Assert with get
        var resultFindById = service.findById(resultUpdate.id!!)
        assertThat(resultFindById == resultUpdate)

    }

    @Test
    fun `Assert calculation (put) , by  get Id`() {
        val testData = TestData(null,"Yet some more Testdata")
        val resultCreate =  service.create(testData)
        val calculateResult = service.calculate(resultCreate.id!!, "100*3")
        assertThat(calculateResult.textData == "Calculation Result of 100*3 is 300")
    }



    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }

}