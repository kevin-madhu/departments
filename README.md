To run the project and access the endpoints, keycloak authorization server is needed.

A temporary keycloak server can be started on port 9080 with the command:
docker run -p 9080:9080 -p 9443:9443 -p 10990:10990 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin  jboss/keycloak -Djboss.socket.binding.port-offset=1000 -Djboss.bind.address.private=127.0.0.1 -Djboss.bind.address=0.0.0.0

After that a new realm has to be imported to keycloak from file present in src/main/docker/realm-config/rivc-realm.json
Following which, a user (user-id: alexey.kamsky, password: admin) can be exported from using partialImport option in the UI from file  src/main/docker/realm-config/rivc-users-0.json

After keycloak has been setup, we need to get hold of an authorization code using any browser by going to:
http://localhost:9080/auth/realms/rivc/protocol/openid-connect/auth?client_id=rivc-client&redirect_uri=http://localhost:8085&response_type=code&scope=openid,profile&state=12345

After successful authentication, the page gets redirected to another url(dummy, because there's no UI) towards the end of which
we can find a query param, namely code, which needs to be provided again to the keycloak server to obtain an access token by using the request:

curl --location --request POST 'http://localhost:9080/auth/realms/rivc/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'client_id=rivc-client' \
--data-urlencode 'client_secret=changeit' \
--data-urlencode 'code=REPLACE_WITH_CODE_FROM_ABOVE' \
--data-urlencode 'redirect_uri=http://localhost:8085' \
--data-urlencode 'scope=openid profile'

The access_token recieved as a response from this could be added as Bearer tokens to further requests to the project for authorization.

A postman collection could be imported to Postman from file departments.postman_collection.json