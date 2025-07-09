FROM quay.io/keycloak/keycloak:26.3.0
LABEL maintainer="iJUG e.V."

# build-time props
ARG KC_DB=postgres
ARG KC_HEALTH_ENABLED=true
ARG KC_FEATURES=passkeys

# Copy build relevant resources
COPY ./target/keycloak-extensions.jar /opt/keycloak/providers/keycloak-extensions.jar

# Do the Keycloak Build
RUN /opt/keycloak/bin/kc.sh build

ENTRYPOINT [ "/opt/keycloak/bin/kc.sh" ]
