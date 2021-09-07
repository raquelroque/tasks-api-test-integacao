package br.r.rrr.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;



import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	// executa uma vez antes de todos os testes, antes mesmo que a classe seja
	// instanciada
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";

	}

	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
			.when()
				.get("/todo")
			.then()
				.statusCode(200);
		;
	}

	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
				.body("{\"task\" : \"Teste via API\", \"dueDate\":\"2021-11-30\"}")
				.contentType(ContentType.JSON)
		.when()
				.post("/todo")
		.then()
				.statusCode(201);
		;
	}

	@Test
	public void naoDeveAdicionarTarefaComSucesso() {
		RestAssured.given()
				.body("{\"task\" : \"Teste via API\", \"dueDate\":\"2010-07-16\"}")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"));
	}

}
