FROM quay.io/keycloak/keycloak:26.5.4
LABEL maintainer="iJUG e.V."

# build-time props
ARG KC_DB=postgres
ARG KC_HEALTH_ENABLED=true
ARG KC_SPI_THEME__WELCOME_THEME=ijug
ARG KC_FEATURES_DISABLED=admin-fine-grained-authz,authorization,ciba,impersonation,kerberos,log-mdc,organization,step-up-authentication,user-event-metrics

# Copy build relevant resources
COPY ./target/keycloak-extensions.jar /opt/keycloak/providers/keycloak-extensions.jar
COPY ./src/main/themes /opt/keycloak/themes

# Do the Keycloak Build
RUN /opt/keycloak/bin/kc.sh build

ENTRYPOINT [ "/opt/keycloak/bin/kc.sh" ]
