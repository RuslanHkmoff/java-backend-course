package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.ScrapperApplication;
import edu.java.response.github.GithubOwner;
import edu.java.response.github.GithubResponse;
import edu.java.service.GithubService;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ScrapperApplication.class)
public class GithubClientTest {

    @Autowired
    GithubService githubService;
    private WireMockServer wireMockServer;

    @BeforeEach
    void init() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @AfterEach
    void destroy() {
        wireMockServer.stop();
    }

    @Test
    void testFetchGithubRepository() {
        wireMockServer.stubFor(
            get(urlEqualTo("test/repos/RuslanHkmoff/java-backend-course"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                            """
                                {
                                    "owner": {
                                        "login": "RuslanHkmoff",
                                        "id": 97846920
                                    },
                                    "updated_at": "2024-02-05T18:57:19Z",
                                    "pushed_at": "2024-02-19T09:51:53Z"
                                    "open_issues_count": 0
                                }
                                """
                        )
                )
        );
        GithubResponse expected = new GithubResponse(
            new GithubOwner("RuslanHkmoff", 97846920L),
            OffsetDateTime.parse("2024-02-05T18:57:19Z"),
            OffsetDateTime.parse("2024-02-19T09:51:53Z"),
            0
        );
        githubService
            .fetchGithubRepository("RuslanHkmoff", "java-backend-course")
            .subscribe(response -> assertThat(response).isEqualTo(expected));
    }
}
