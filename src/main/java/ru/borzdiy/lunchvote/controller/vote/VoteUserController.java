package ru.borzdiy.lunchvote.controller.vote;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borzdiy.lunchvote.AuthorizedUser;
import ru.borzdiy.lunchvote.model.Vote;

import java.util.List;

import static ru.borzdiy.lunchvote.controller.vote.VoteUserController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteUserController extends AbstractVoteController {
    public static final String REST_URL = "/rest/votes";

    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthorizedUser user) {
        return super.getAll(user.getId());
    }


}
