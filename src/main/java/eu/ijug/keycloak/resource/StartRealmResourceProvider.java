package eu.ijug.keycloak.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.common.Version;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.IdentityProviderQuery;
import org.keycloak.models.IdentityProviderStorageProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.util.CacheControlUtil;
import org.keycloak.theme.Theme;
import org.keycloak.theme.freemarker.FreeMarkerProvider;
import org.keycloak.urls.UrlType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RequiredArgsConstructor
public class StartRealmResourceProvider implements RealmResourceProvider {

	private final KeycloakSession session;

	@Override
	public Object getResource() {
		return this;
	}

	@Override
	public void close() {
	}

	@GET
	@Path("")
	@Produces(MediaType.TEXT_HTML)
	public Response start() {
		RealmModel realm = session.getContext().getRealm();
		if (realm == null || !realm.getName().equals("ijug")) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		try {

			Theme theme = getTheme();
			Properties themeProperties = theme.getProperties();
			String commonPath = themeProperties.getProperty("common", "common/keycloak");

			Map<String, Object> map = new HashMap<>();
			map.put("properties", themeProperties);
			map.put("baseUrl", session.getContext().getUri(UrlType.FRONTEND).getBaseUri());
			map.put("productName", Version.NAME);
			map.put("resourcesPath", "resources/" + Version.RESOURCES_VERSION + "/" + theme.getType().toString().toLowerCase() +"/" + theme.getName());
			map.put("resourcesCommonPath", "resources/" + Version.RESOURCES_VERSION + "/" + commonPath);

			List<IdentityProviderModel> idps = session.getProvider(IdentityProviderStorageProvider.class)
				.getAllStream(IdentityProviderQuery.userAuthentication())
				.toList();
			map.put("jugs", idps);

			FreeMarkerProvider freeMarkerUtil = session.getProvider(FreeMarkerProvider.class);
			String result = freeMarkerUtil.processTemplate(map, "start.ftl", theme);

			return Response.ok().entity(result).cacheControl(CacheControlUtil.noCache()).build();

		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	private Theme getTheme() {
		try {
			return session.theme().getTheme(Theme.Type.WELCOME);
		} catch (IOException e) {
			throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

}
