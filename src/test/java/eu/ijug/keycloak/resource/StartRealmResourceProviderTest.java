package eu.ijug.keycloak.resource;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import de.keycloak.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

@Testcontainers
public class StartRealmResourceProviderTest extends TestBase {

	@Container
	private static final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:latest")
		.withFileSystemBind("src/main/themes/ijug", "/opt/keycloak/themes/ijug", BindMode.READ_ONLY)
		.withEnv("KC_SPI_THEME__WELCOME_THEME", "ijug")
		.withDefaultProviderClasses();

	@BeforeAll
	static void setup() {
		initTestRealm(keycloak, "ijug", realm -> realm.setLoginTheme("ijug"), null);
		initTestRealm(keycloak, "test", null, null);
	}

	@Test
	void testRootRedirectsToIjugStart() {
		given()
			.get(keycloak.getAuthServerUrl() + "/")
			.then().statusCode(200)
			.body(containsString("http-equiv=\"refresh\""))
			.body(containsString("url=/realms/ijug/start/"));
	}

	@ParameterizedTest
	@CsvSource( { "ijug,200", "test,404" })
	void testStart(String realm, int expectedStatusCode) {
		given()
			.get(keycloak.getAuthServerUrl() + "/realms/" + realm + "/start")
			.then().statusCode(expectedStatusCode)
			.body(not(containsString("This is just a development stub and must be replaced in production.")));
	}

}
