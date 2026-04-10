package eu.ijug.keycloak.resource;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import de.keycloak.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static io.restassured.RestAssured.given;

@Testcontainers
public class StartRealmResourceProviderTest extends TestBase {

	@Container
	private static final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:latest")
		.withImagePullPolicy(PullPolicy.ageBased(Duration.ofDays(1)))
		.withDefaultProviderClasses();

	@BeforeAll
	static void setup() {
		initTestRealm(keycloak, "ijug", realm -> realm.setLoginTheme("ijug"), null);
		initTestRealm(keycloak, "test", null, null);
	}

	@ParameterizedTest
	@CsvSource( { "ijug,200", "test,404" })
	void testStart(String realm, int expectedStatusCode) {
		given()
			.get(keycloak.getAuthServerUrl() + "/realms/" + realm + "/start")
			.then().statusCode(expectedStatusCode);
	}

}
