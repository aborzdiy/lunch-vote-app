package ru.borzdiy.lunchvote;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.borzdiy.lunchvote.model.User;
import ru.borzdiy.lunchvote.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class TestUtil {

    private final JsonUtil jsonUtil;

    public TestUtil(JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    public String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return jsonUtil.readValue(getContent(action.andReturn()), clazz);
    }

    public <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return jsonUtil.readValue(getContent(result), clazz);
    }

    public <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return jsonUtil.readValues(getContent(result), clazz);
    }
    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }
}
