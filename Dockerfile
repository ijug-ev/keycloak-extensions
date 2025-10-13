FROM quay.io/keycloak/keycloak:26.4.0
LABEL maintainer="iJUG e.V."

# build-time props
ARG KC_DB=postgres
ARG KC_HEALTH_ENABLED=true

# Copy build relevant resources
COPY ./target/keycloak-extensions.jar /opt/keycloak/providers/keycloak-extensions.jar
COPY ./src/main/themes /opt/keycloak/themes

# Do the Keycloak Build
RUN /opt/keycloak/bin/kc.sh build

ENTRYPOINT [ "/opt/keycloak/bin/kc.sh" ]
