package com.example.foodorderingapp.utils;

import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AccessToken {
    private static final String[] SCOPES = {"https://www.googleapis.com/auth/firebase.messaging"};


    public String getAccessToken() {
        try{
            String SERVICE_ACCOUNT_JSON = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"javajoy-mobileapp\",\n" +
                    "  \"private_key_id\": \"82320ee72a395e4106fc032b7b4172f1ec0b4c5b\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCKhEep0rpM82L6\\nwzSDHW3xDkIBxBcs1dviEqVLVyjN4Lo6gOVy5Y+FRBScv3QnoL3uiP4fOSIFrXF9\\nqL5UA1ncDx2rkcUS0mUWXs4NrZlgiimZJcAXEP2Yt2gIZkMYJ/e0s49kBUpCiC90\\nz6kLT/K/eZV8E84LbwVoToQGS+SonixsfCXL47+yDP5wPzB3t4TXTWBaPPsKDHSE\\nJltElIJRQ1Iut59fTA4PGBAiwahQp5jae1RR7FRsUXlq7S1oJJBDQrpHvRVIv2fA\\n5j2tK3OfPs4ok2fLIYPvGf+U4cZVQq12ozmJ1QhI+VjOWYlCxjteAJSZOMoGDEvb\\nh2YhCmFfAgMBAAECggEARBLeZUd653Z1nmdw1z8PWPh42AVWnmI7vc8MDQq4svVO\\nkJHoMZ8/+W6L8rzkiNUmgqQwv4eEgOXMcMUNRJyg/NUw77Cj+awMogHVokM+rYM5\\nDlrPHIbtGrCIIBihwK1jvxq/zZgL9saXDAFCQpz338IRBPl2uYfGUIzFtZJ2vabF\\nWkUGjR9Xp/XBC1GlPM5p23h0BzkACRZk69kRah/0XpE2riR1qubaa/BMBwNKSVe2\\nlyINn8O2TE5bNB2AE124niwdKmCT+eiX4JQtsnPloAT9Fjn2SrI4HuV4QdpJyT9l\\nAVVGy9krwYiyvsi+FAU+5e1sZYhrVig8MGB2Vw52gQKBgQDC/JsaRIvDsZ7xOUGd\\nNX+z+jNNc2rRwIUPCuB7pdekRlsUJAR4ZDREJu6UwLMRvhpLVB957aWQayuhpUjn\\n2d1SqAtaAtS6X4MS3Y2sMAkNstGKVFLgcsD+xMtDs2WRx+jScYFjRlEilau+2YTx\\nLfWX+OtSD8d3F3Po4B9IzYqxZQKBgQC13CX3axB5l0cxMkOEIyihGFilqYwH84Iy\\ntrays9Zw8Im1+K4I+RX3SRwxT14wOYPD7rMOZCN9jN/WewyFQ0N1FifFUgZBAvOz\\njzBEn5XvPVoVtAVrtinQME3Jbh5i0K4O5qfwtPRCEgMLNVfcp0rUI//gVeYwerZA\\n9VQ43O9dcwKBgAgyXEbPaNyyI89aK9LzA8pKE0wHNQ9aO321ZFhuUVFbj8qZ9QIm\\nnDfdNxxTuXNefXZ7M9OJVPNsPepOq3DrrK0j2U5QXibziuwJGkKJxmeoA7z/7Hf4\\n+vn6xdyztupY8AWgteK5ewGsLwsX08nbt75KvYG2nhj4iGvauBX0ZgctAoGBAIWS\\n1TANK2SjgF7aFE0Rpd3tC+9hwMB2UsL9AOPwwyjhA8Tsiqd8jG28/UAak4vIMsfO\\noyWG16p3tlDKwD+BUCLHyx5bSMynZmgUW9eLf4zg+zHFr9WVTtDjKb8xapAZxj6b\\nm+5oep+sqx+oZohjN8rpRh93+ELTXtoHV30MiaORAoGBAIy2bFc3QSueRP+rnRiW\\nBqx1xlwPdwhZKs9vAwp5OAVAzYlBr7ACrXCPmKuV6zVg/Z8j2IgV0GfrHtkZK4zO\\n9x5rn+f1OH/wsMswKZYqqEYM2o/EBhVx0PrnFZUH1YRlY72yxYzMuVWwtaMkjaTv\\nbzzqVWIPryRhmwls0jdi1MYa\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-flutq@javajoy-mobileapp.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"114576990246324675889\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-flutq@javajoy-mobileapp.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}\n";

            InputStream serviceAccountStream = new ByteArrayInputStream(SERVICE_ACCOUNT_JSON.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(serviceAccountStream)
                    .createScoped(Arrays.asList(SCOPES));
            googleCredentials.refresh();

            return googleCredentials.getAccessToken().getTokenValue();
        }
        catch (IOException e) {
            Log.e("error", e.getMessage());
            return null;
        }
    }
}
