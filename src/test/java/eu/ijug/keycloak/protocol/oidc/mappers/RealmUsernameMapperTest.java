package eu.ijug.keycloak.protocol.oidc.mappers;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import de.keycloak.TestBase;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.protocol.oidc.OIDCLoginProtocol;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.ProtocolMapperRepresentation;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Testcontainers
public class RealmUsernameMapperTest extends TestBase {

	private static final String REALM_NAME = "test";

	@Container
	private static final KeycloakContainer keycloak = new KeycloakContainer()
		.withDefaultProviderClasses()
		.withRealmImportFile("/test-realm.json");

	@Test
	public void shouldStartKeycloakWithRealmUsernameMapper() throws Exception {
		keycloak.disableLightweightAccessTokenForAdminCliClient(REALM_NAME);
		Keycloak keycloakClient = keycloak.getKeycloakAdminClient();

		RealmResource realm = keycloakClient.realm(REALM_NAME);
		ClientRepresentation accountClient = realm.clients().findByClientId(KeycloakContainer.ADMIN_CLI_CLIENT).getFirst();

		ProtocolMapperRepresentation mapper = configureRealmUsernameMapper();
		realm.clients().get(accountClient.getId()).getProtocolMappers().createMapper(mapper).close();

		ValidatableResponse validatableResponse = requestToken(keycloak, REALM_NAME, "test", "test");
		String accessToken = validatableResponse.extract().path("access_token");
		Map<String, Object> tokenPayload = parseToken(accessToken);

		assertEquals("test_test", tokenPayload.get("preferred_username"));
	}

	private ProtocolMapperRepresentation configureRealmUsernameMapper() {
		ProtocolMapperRepresentation mapper = new ProtocolMapperRepresentation();
		mapper.setProtocol(OIDCLoginProtocol.LOGIN_PROTOCOL);
		mapper.setProtocolMapper(RealmUsernameMapper.PROVIDER_ID);
		mapper.setName(RealmUsernameMapper.PROVIDER_ID);
		Map<String, String> config = Map.of(
			OIDCAttributeMapperHelper.TOKEN_CLAIM_NAME, "preferred_username",
			OIDCAttributeMapperHelper.INCLUDE_IN_ACCESS_TOKEN, "true"
		);
		mapper.setConfig(config);
		return mapper;
	}
}
