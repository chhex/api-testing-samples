package com.apgsga.apitestingsample

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpRequest
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    @BeforeAll
    fun setup() {
        println(">> Setup")
        restTemplate.restTemplate.interceptors =
            listOf(ClientHttpRequestInterceptor { request: HttpRequest, body: ByteArray?, execution: ClientHttpRequestExecution ->
                request.headers
                    .add("Authorization", "Bearer .... whatever ...")
                execution.execute(request, body!!)
            })
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
    fun `Assert create (post) Testdata , get all`() {
        val testData = TestData(null,"Some Testdata")
        val response: ResponseEntity<TestData> = restTemplate.postForEntity("/api/testdata", testData, TestData::class.java)
        assertThat(response.body?.id != null)
        val responseAll: ResponseEntity<Array<TestData>> = restTemplate.getForEntity(
                "/api/testdata",
                Array<TestData>::class.java)
        val someTestDati: Array<TestData> = responseAll.body!!
        assertThat(someTestDati.size == 1)
        assertThat(someTestDati[0].textData == "Some Testdata")
    }

    @Test
    fun `Assert create (post) Testdata , get by Id`() {
        val testData = TestData(null,"Some Other Testdata")
        val response: ResponseEntity<TestData> = restTemplate.postForEntity("/api/testdata", testData, TestData::class.java)
        assertThat(response.body?.id != null)
        val responseEntity = restTemplate.getForEntity(
                "/api/testdata/${response.body!!.id}", TestData::class.java)
        val testDataResult: TestData = responseEntity.body!!
        assertThat(testDataResult.textData == "Some Other Testdata")

    }

    @Test
    fun `Assert update (put) Testdata , by  get Id`() {
        // Create Initial
        val testData = TestData(null,"Yet some other Testdata")
        val responseCreate: ResponseEntity<TestData> = restTemplate.postForEntity("/api/testdata", testData, TestData::class.java)
        // Update
        val updatedResponse = responseCreate.body!!
        val update = StringBuilder(updatedResponse.textData)
        update.append(" Updated")
        updatedResponse.textData = update.toString()
        val requestUpdate: HttpEntity<TestData> = HttpEntity<TestData>(updatedResponse)
        val responseUpdate = restTemplate.exchange("/api/testdata/${updatedResponse.id}", HttpMethod.PUT, requestUpdate, TestData::class.java)
        assertThat(responseUpdate.equals(updatedResponse))
        // Assert with get
        val responseEntity = restTemplate.getForEntity(
            "/api/testdata/${updatedResponse.id}", TestData::class.java)
        assertThat(responseUpdate.equals(responseEntity.body!!))

    }

    @Test
    fun `Assert calculation (put) , by  get Id`() {
        val testData = TestData(null,"Yet some more Testdata")
        val responseCreate: ResponseEntity<TestData> = restTemplate.postForEntity("/api/testdata", testData, TestData::class.java)
        val responseUpdate = restTemplate.exchange("/api/testdata/${responseCreate.body!!.id}/1+1", HttpMethod.PUT, null, TestData::class.java)
        assertThat(responseUpdate.body!!.textData == "Calculation Result of 1+1 is 2")
    }



    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }

}