package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.ScrapperApplication;
import edu.java.response.stackoverflow.StackOverflowResponse;
import edu.java.response.stackoverflow.QuestionResponse;
import edu.java.response.stackoverflow.StackOverflowOwner;
import edu.java.service.client.StackOverflowService;
import java.time.OffsetDateTime;
import java.util.List;
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
public class StackOverflowClientTest {
    @Autowired
    StackOverflowService stackOverflowService;
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
    void testFetchStackOverflowQuestion() {
        wireMockServer.stubFor(
            get(urlEqualTo("test/questions/744256"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                            """
                                {
                                     "items": [
                                         {
                                             "owner": {
                                                 "display_name": "PPGoodMan",
                                                 "user_id": 708489
                                             },
                                             "question_id": 74425651,
                                             "last_activity_date": "2022-11-14T13:59:03Z",
                                             "answer_count": 1
                                         }
                                     ]
                                 }
                                """
                        )
                )
        );

        QuestionResponse questionResponse = new QuestionResponse(
            new StackOverflowOwner("PPGoodMan", 708489L),
            744256L,
            OffsetDateTime.parse("2022-11-14T13:59:03Z"),
            1
        );
        StackOverflowResponse expected = new StackOverflowResponse(List.of(questionResponse));
        stackOverflowService
            .fetchSofQuestion(744256L)
            .subscribe(response -> assertThat(response).isEqualTo(expected));
    }
}


