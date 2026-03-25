package eu.ijug.keycloak.resource;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.Test;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static io.restassured.RestAssured.given;

@Testcontainers
public class StartRealmResourceProviderTest {

	@Container
	KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:latest")
		.withImagePullPolicy(PullPolicy.ageBased(Duration.ofDays(1)))
		.withDefaultProviderClasses()
		.withRealmImportFile("/test-realm.json");

	@Test
	void testStart() {
		given()
			.get(keycloak.getAuthServerUrl() + "/realms/test/start")
			.then().statusCode(200);
	}

}
