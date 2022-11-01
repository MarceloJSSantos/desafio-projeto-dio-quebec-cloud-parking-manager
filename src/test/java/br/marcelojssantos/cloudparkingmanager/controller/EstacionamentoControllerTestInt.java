package br.marcelojssantos.cloudparkingmanager.controller;

import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoCreateDTO;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EstacionamentoControllerTestInt {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = randomPort;
    }

    @Test
    void quandoFindAllEntaoChecaResultado() {
        RestAssured.given()
                .when()
                .get("/estacionamentos")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void qunadoCreateEntaoChecaSeECriado() {
        var createDTO = new EstacionamentoCreateDTO();
        createDTO.setLicenca("LAI-2A45");
        createDTO.setEstado("RJ");
        createDTO.setModelo("Gol");
        createDTO.setCor("Branca");

        RestAssured.given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/estacionamentos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("licenca", Matchers.equalTo("LAI-2A45"));
    }
}