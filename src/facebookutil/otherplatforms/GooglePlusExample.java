package facebookutil.otherplatforms;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;


public abstract class GooglePlusExample {

    private static final String NETWORK_NAME = "G+";
    private static final String PROTECTED_RESOURCE_URL =
            "https://www.googleapis.com/plus/v1/people/me";

    private static ResourceBundle mySecrets;

    public static void main (String ... args) {
        // Replace these with your client id and secret
        mySecrets = ResourceBundle.getBundle("facebookutil/secret");
        final String clientId = mySecrets.getString("googleId");
        final String clientSecret = mySecrets.getString("googleSecret");
        final OAuth20Service service = new ServiceBuilder()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .scope("https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.circles.write https://www.googleapis.com/auth/plus.circles.read https://www.googleapis.com/auth/plus.stream.write https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.stream.read")
                .callback("https://github.com/duke-compsci308-spring2016/voogasalad_GitDepends")
                .build(GoogleApi20.instance());
        Scanner in = new Scanner(System.in, "UTF-8");

        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
        System.out.println();

        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");

        final Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("access_type", "offline");
        // force to retrieve refresh token (if users are asked not the first time)
        additionalParams.put("prompt", "consent");
        final String authorizationUrl = service.getAuthorizationUrl(additionalParams);
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        final String code = in.nextLine();
        System.out.println();

        System.out.println("Trading the Request Token for an Access Token...");
        OAuth2AccessToken accessToken = service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken +
                           ", 'rawResponse'='" + accessToken.getRawResponse() + "')");

        System.out.println("Refreshing the Access Token...");
        accessToken = service.refreshAccessToken(accessToken.getRefreshToken());
        System.out.println("Refreshed the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken +
                           ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        while (true) {
            System.out
                    .println("Paste fieldnames to fetch (leave empty to get profile, 'exit' to stop example)");
            System.out.print(">>");
            final String query = in.nextLine();
            System.out.println();

            final String requestUrl;
            if ("exit".equals(query)) {
                break;
            }
            else if (query == null || query.isEmpty()) {
                requestUrl = PROTECTED_RESOURCE_URL;
            }
            else {
                requestUrl = PROTECTED_RESOURCE_URL + "?fields=" + query;
            }

            final OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            final Response response = request.send();
            System.out.println();
            System.out.println(response.getCode());
            System.out.println(response.getBody());

            System.out.println();
        }
        in.close();
    }
}
