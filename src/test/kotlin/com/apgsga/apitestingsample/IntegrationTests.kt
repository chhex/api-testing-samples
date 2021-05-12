package com.apgsga.apitestingsample

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun `Assert get all Testdata`() {
        val response: ResponseEntity<Array<TestData>> = restTemplate.getForEntity(
                "/api/testdata",
                Array<TestData>::class.java)
        val employees: Array<TestData> = response.body!!
        assertThat(employees.isEmpty())
    }


    @Test
    fun `Assert post Testdata , get all`() {
        val testData = TestData(null,"Some Testdata")
        restTemplate.postForLocation("/api/testdata", testData)
        val response: ResponseEntity<Array<TestData>> = restTemplate.getForEntity(
                "/api/testdata",
                Array<TestData>::class.java)
        val testDatas: Array<TestData> = response.body!!
        assertThat(testDatas.size == 1)
        assertThat(testDatas[0].text == "Some Testdata")
    }

    @Test
    fun `Assert post Testdata , get all and by Id`() {
        val testData = TestData(null,"Some Other Testdata")
        restTemplate.postForLocation("/api/testdata", testData)
        val responseAll: ResponseEntity<Array<TestData>> = restTemplate.getForEntity(
                "/api/testdata",
                Array<TestData>::class.java)
        val testDatas: Array<TestData> = responseAll.body!!
        assertThat(testDatas.size == 1)
        assertThat(testDatas[0].text == "Some Other Testdata")
        val id = testDatas[0].id
        val responseEntity = restTemplate.getForEntity(
                "/api/testdata/$id", TestData::class.java)
        val testDataResult: TestData = responseEntity.body!!
        assertThat(testDataResult.text == "Some Other Testdata")

    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }

}