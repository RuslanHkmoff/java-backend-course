package edu.java.service.updates;

import edu.java.model.Link;
import edu.java.repository.LinkRepository;
import edu.java.response.github.GithubResponse;
import edu.java.response.stackoverflow.QuestionResponse;
import edu.java.response.stackoverflow.StackOverflowResponse;
import edu.java.service.client.GithubService;
import edu.java.service.client.StackOverflowService;
import edu.java.util.GithubRequestParameters;
import edu.java.util.LinkParser;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class LinkUpdateServiceImpl implements LinkUpdatesService {
    private static final String UPDATE_MESSAGE = "The link has been updated\n%s";
    private static final String GITHUB_URL = "https://github";
    private static final String STACKOVERFLOW_URL = "https://stackoverflow";

    private final LinkRepository linkRepository;
    private final GithubService githubService;
    private final StackOverflowService stackOverflowService;

    public List<LinkUpdateDto> getUpdatedLink(int size) {
        List<Link> links = linkRepository.findLastVisited(size);
        return links
            .stream()
            .map(this::handleLink)
            .filter(Objects::nonNull)
            .toList();
    }

    private LinkUpdateDto handleLink(Link link) {
        String linkString = link.getUrl().toString();
        UpdateParameters updateParameters;
        if (linkString.startsWith(GITHUB_URL)) {
            updateParameters = handleGithubLink(link);
        } else if (linkString.startsWith(STACKOVERFLOW_URL)) {
            updateParameters = handleStackOverflowLink(link);
        } else {
            throw new IllegalArgumentException("Unexpected link: " + link.getUrl());
        }
        if (updateParameters != null) {
            OffsetDateTime oldUpdatedAt = link.getUpdatedAt();
            Integer oldUpdateCount = link.getUpdateCount();

            OffsetDateTime newUpdatedAt = updateParameters.updatedAt();
            Integer newUpdateCount = updateParameters.updateCount();

            if (!Objects.equals(oldUpdatedAt, newUpdatedAt) || !Objects.equals(oldUpdateCount, newUpdateCount)) {
                saveLink(link, newUpdatedAt, newUpdateCount);
                return new LinkUpdateDto(
                    link.getUrl(),
                    String.format(UPDATE_MESSAGE, link.getUrl())
                );
            }
        }
        return null;
    }

    private UpdateParameters handleGithubLink(Link link) {
        GithubRequestParameters parameters = LinkParser.extractGithubData(link.getUrl().toString());
        GithubResponse response =
            githubService.fetchGithubRepository(parameters.repoOwner(), parameters.repoName()).block();
        if (response != null) {
            return new UpdateParameters(response.openIssuesCount(), response.updateAt());
        }
        return null;
    }

    private UpdateParameters handleStackOverflowLink(Link link) {
        Long questionId = LinkParser.extractSofData(link.getUrl().toString());
        StackOverflowResponse response = stackOverflowService.fetchSofQuestion(questionId).block();
        if (response != null) {
            QuestionResponse questionResponse = response.items().getFirst();
            return new UpdateParameters(questionResponse.answerCount(), questionResponse.lastActivity());
        }
        return null;
    }

    private void saveLink(Link link, OffsetDateTime updatedAt, Integer updateCount) {
        linkRepository.updateLink(
            link.getId(),
            updatedAt,
            updateCount
        );
    }

    record UpdateParameters(Integer updateCount, OffsetDateTime updatedAt) {
    }
}
