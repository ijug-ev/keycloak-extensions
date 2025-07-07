package eu.ijug.keycloak.protocol.oidc.mappers;

import com.google.auto.service.AutoService;
import de.keycloak.util.BuildDetails;
import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.ProtocolMapper;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ServerInfoAwareProviderFactory;
import org.keycloak.representations.IDToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AutoService(ProtocolMapper.class)
public class RealmUsernameMapper extends AbstractOIDCProtocolMapper
	implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper, ServerInfoAwareProviderFactory {

	public static final String PROVIDER_ID = "realm-username";

	private static final List<ProviderConfigProperty> CONFIG_PROPERTIES = new ArrayList<>();

	static {
		OIDCAttributeMapperHelper.addTokenClaimNameConfig(CONFIG_PROPERTIES);
		OIDCAttributeMapperHelper.addIncludeInTokensConfig(CONFIG_PROPERTIES, RealmUsernameMapper.class);

		for (ProviderConfigProperty prop : CONFIG_PROPERTIES) {
			if (OIDCAttributeMapperHelper.TOKEN_CLAIM_NAME.equals(prop.getName())) {
				prop.setDefaultValue("preferred_username");
			}
		}
	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public String getDisplayCategory() {
		return TOKEN_MAPPER_CATEGORY;
	}

	@Override
	public int getPriority() {
		return super.getPriority() + 10;
	}

	@Override
	public String getDisplayType() {
		return "Realm Username";
	}

	@Override
	public String getHelpText() {
		return "Map the username with realm extension";
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return CONFIG_PROPERTIES;
	}

	@Override
	protected void setClaim(IDToken token, ProtocolMapperModel mappingModel, UserSessionModel userSession,
													KeycloakSession keycloakSession, ClientSessionContext clientSessionCtx) {
		String realmUsername = userSession.getUser().getUsername() + "_" + userSession.getRealm().getName();
		OIDCAttributeMapperHelper.mapClaim(token, mappingModel, realmUsername);
	}

	@Override
	public Map<String, String> getOperationalInfo() {
		return BuildDetails.get();
	}
}
