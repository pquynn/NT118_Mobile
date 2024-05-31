package com.example.javajoyadmin.utils;

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
                    "  \"private_key_id\": \"cbea1107baa1d5b1cb2541e29f7922b9e7a55584\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCrLolJtkwbC7M4\\n3U/04GbTd40SwjCUUlykunuyAeS+9y1KXVUAAoViIJ7m6BMtThcNU48cZFZ1ogdN\\nS3OfBsdhNgyCYrzRU2PqQgYjVF+8qWXbXbwLbOCjkYTs9WtM4KdBrO+OSyLRHOqv\\nTpsp0eChyI65tNgnRYijaG3sE5CtlgkIGSD12s0wARP9G/ELncHUM1r9leLxeFpC\\nKmOU2n0f2M0qOFkYAkJiLMDYDnjaGCpcQtdSRdqCm/j33lO7CqUtVnFPPs4v+6NX\\n4KnznFnQ2iein+LJ39BihIqJidXafOkg90vvZDjjyvzIjA/px2fpCPHDe5Zjj3F4\\nzfr1LiqtAgMBAAECggEADOm7BgI4Lf8dMzAwsKR/5TpavDxFXV70KaH+pEAz0PsE\\n4dv2BkTqamtTCVCe57dBEmqH/GiORBvnqJtFOs0QW8MvwOMxYqoDer+lxmL+QNVX\\nf2uL/KGnNXeL+zMNN9SnkVL6FDYTR4qCopxPpKE2LIYcTCJ5/sZmRwtg5WLZclqk\\nM+fV5iu6s0Cv+S65pW0ZZ7bK0dnSZj7wp/Dv2SCrW5sTg3hufnwQubBefA2+NyQY\\nojVNS8SlrtMlah7YeGYmNaHP+HV9S7ofUYiXitsl+eWlxM+lv8RDfMt/KA/cBrGH\\nmKGhjZUxLlBkLdQ9hNXwGcptKkaZQ5DFzPA18Pr1WQKBgQDY5Pa6kP78z5d6gMgu\\nmwi1HYHJa4llREMz+jFWT9l2hHfr6HA4+3iKHC98uYXzlaeIqWJumyBicqDRBLuM\\nBU/uY/6TNEvhulWWgYE64ZUa1O4r24ROtbE3KF4XQShUUjfzAT3IcLER6+7cG8uZ\\nveuidJ5rXDzRGRsMgJtkYMMKaQKBgQDKC6YlGImXB0L/NM4hbVH2DjV4UbBOFb0v\\n+HtY/4lr+6H24v8iH1TGM+Ls+SW+jsvo+X+WoUVr3nxd9UV4P/Tpd5CDLItLKJ0G\\nUVJ4qnQMw/2A2R6UJeQYn0T3OL3hqfN3cUiKW6StpDA/rl/KnJ4CziQTvwqzhVr9\\nMwWiXQQtpQKBgEH5i69+cYG3J62kxL+gewqUmikJo/aAyCxkW2OU0GJDszK4NoKu\\nw6DHKA65ZCyPBjIEzCM23qfzlEVgOihCaWjrU8G1M6vLAUPRytx9R9ysjnIayOkk\\nTW6owc4zDLBxfgFWDFQPK113NeYQMmNmqOl2rcWcfgdgqqCn8hybbliZAoGBAMTc\\n9NXQQr/VY4ImgXIwglutHIxPi+mJZP0Ir6bX1Z5RYE6eftlQjdlRVRpw6EXz/NrT\\nv22VVX71GVizUy0HpCqXRUeKb26b8+Pj+Od5rfmCPc79vr267bTP+vKnZtwqK/+F\\ncZjeEmbeQ+TIkq1oTM2O7bgPvO2OUknDHQpGd3vBAoGAe8vUqjjCFQtyIU6gUpcR\\nYp7BgRBmU0Yo8UkVud4Y6TZU0AJw2BWiad5AN65CAgjaP1gsgVGvV4TmFYCK3+1L\\ny1JbsxKKKz4fwEWoPc0rrJXWJw5PeqWJhzvufAMklam/DpDdb8zqYVk90I3ATa8S\\nKxeEMb27xr9HfE2C2oVfrf0=\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-flutq@javajoy-mobileapp.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"114576990246324675889\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-flutq%40javajoy-mobileapp.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}";

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
