package eu.ijug.keycloak.resource;

import com.google.auto.service.AutoService;
import de.keycloak.provider.DefaultServerInfoAware;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

@AutoService(RealmResourceProviderFactory.class)
public class StartRealmResourceProviderFactory implements RealmResourceProviderFactory, DefaultServerInfoAware {

	public static final String PROVIDER_ID = "start";

	@Override
	public RealmResourceProvider create(KeycloakSession session) {
		return new StartRealmResourceProvider(session);
	}

	@Override
	public void init(Config.Scope config) {
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {
	}

	@Override
	public void close() {
	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}
}
