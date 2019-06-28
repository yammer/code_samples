/*******************************************************************************
 // Copyright (c) Microsoft Corporation.
 // All rights reserved.
 //
 // This code is licensed under the MIT License.
 //
 // Permission is hereby granted, free of charge, to any person obtaining a copy
 // of this software and associated documentation files(the "Software"), to deal
 // in the Software without restriction, including without limitation the rights
 // to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
 // copies of the Software, and to permit persons to whom the Software is
 // furnished to do so, subject to the following conditions :
 //
 // The above copyright notice and this permission notice shall be included in
 // all copies or substantial portions of the Software.
 //
 // THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 // IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 // FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
 // AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 // LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 // OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 // THE SOFTWARE.
 ******************************************************************************/
package com.microsoft.yammer.adal4jsample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.microsoft.aad.adal4j.AuthenticationResult;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/secure/aad")
public class AadController {
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String getDirectoryObjects(ModelMap model, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        AuthenticationResult result = (AuthenticationResult) session.getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME);
        if (result == null) {
            model.addAttribute("error", new Exception("AuthenticationResult not found in session."));
            return "/error";
        } else {
            try {
                String tenant = session.getServletContext().getInitParameter("tenant");
                model.put("tenant", tenant);
                List<YammerApiRequest> requests = buildYammerRequests(result.getAccessToken(), session);
                testYammerAPIs(requests, model);
            } catch (Exception e) {
                model.addAttribute("error", e);
                return "/error";
            }
        }
        return "/secure/aad";
    }

    private void testYammerAPIs(List<YammerApiRequest> requests, ModelMap map) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String baseUri = "https://api.yammer.com/";
        for (YammerApiRequest yammerApiRequest : requests) {
            try {
                if (yammerApiRequest.getUriVariable().isEmpty()) {
                    mapResult(restTemplate.exchange(baseUri + yammerApiRequest.getEndpoint(),
                            yammerApiRequest.getHttpMethod(),
                            yammerApiRequest.getHttpEntity(),
                            String.class).getStatusCode(),
                            yammerApiRequest.getSuccessHttpStatus(),
                            map, yammerApiRequest.getModelName());
                } else {
                    mapResult(restTemplate.exchange(baseUri + yammerApiRequest.getEndpoint(),
                            yammerApiRequest.getHttpMethod(),
                            yammerApiRequest.getHttpEntity(),
                            String.class, yammerApiRequest.getUriVariable()).getStatusCode(),
                            yammerApiRequest.getSuccessHttpStatus(),
                            map, yammerApiRequest.getModelName());
                }
            } catch (Exception ex) {
                map.put(yammerApiRequest.getModelName(), "&#x274C;");
                continue;
            }
        }
    }

    private List<YammerApiRequest> buildYammerRequests(String accessToken, HttpSession session) {
        String thread_id = session.getServletContext().getInitParameter("thread_id");
        String message_id = session.getServletContext().getInitParameter("message_id");
        String group_id = session.getServletContext().getInitParameter("group_id");
        String ogo_id = session.getServletContext().getInitParameter("ogo_id");
        String topic_id = session.getServletContext().getInitParameter("topic_id");
        String user_id = session.getServletContext().getInitParameter("user_id");
        String file_id = session.getServletContext().getInitParameter("file_id");
        String file_version_id = session.getServletContext().getInitParameter("file_version_id");
        String email = session.getServletContext().getInitParameter("email");
        String search_query_term = session.getServletContext().getInitParameter("search_query_term");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + accessToken);
        List<YammerApiRequest> requests = new ArrayList<>();
        requests.add(new YammerApiRequest("api/v1/messages/my_feed.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "my_feed", ""));
        requests.add(new YammerApiRequest("api/v1/messages/algo.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "algo", ""));
        requests.add(new YammerApiRequest("api/v1/messages.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "messages", ""));
        requests.add(new YammerApiRequest("api/v1/messages/following.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "following", ""));
        requests.add(new YammerApiRequest("api/v1/messages/sent.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "sent", ""));
        requests.add(new YammerApiRequest("api/v1/messages/received.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "received", ""));
        requests.add(new YammerApiRequest("api/v1/messages/in_thread/{thread_id}.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "in_thread", thread_id));
        requests.add(new YammerApiRequest("api/v1/messages/in_group/{group_id}.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "in_group", group_id));
        requests.add(new YammerApiRequest("api/v1/messages/open_graph_objects/{ogo_id}.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "ogo", ogo_id));
        requests.add(new YammerApiRequest("api/v1/messages/about_topic/{topic_id}.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "about_topic", topic_id));
        requests.add(new YammerApiRequest("api/v1/threads/{thread_id}.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "thread_meta_data", thread_id));
        requests.add(new YammerApiRequest("api/v1/topics/{topic_id}.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "topic_meta_data", topic_id));
        requests.add(new YammerApiRequest("api/v1/users.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "user", ""));
        requests.add(new YammerApiRequest("api/v1/users/current.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "current_user", ""));
        requests.add(new YammerApiRequest("api/v1/users/{user_id}.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "user_by_id", user_id));
        requests.add(new YammerApiRequest("api/v1/users/by_email.json?email={email}", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "user_by_email", email));
        requests.add(new YammerApiRequest("api/v1/users/in_group/{group_id}.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "users_by_group", group_id));
        requests.add(new YammerApiRequest("api/v1/users/liked_message/{message_id}.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "liked_message", message_id));
        requests.add(new YammerApiRequest("api/v1/streams/notifications.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "notifications", ""));
        requests.add(new YammerApiRequest("api/v1/suggestions.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "suggestions", ""));
        requests.add(new YammerApiRequest("api/v1/search.json?search={query}", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "search", search_query_term));
        requests.add(new YammerApiRequest("api/v1/networks/current.json", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "networks", ""));
        requests.add(new YammerApiRequest("api/v1/uploaded_files/{file_id}/version/"+file_version_id+"/preview", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "file_preview", file_id));
        requests.add(new YammerApiRequest("api/v1/uploaded_files/{file_id}/version/"+file_version_id+"/large_preview", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "file_large_preview", file_id));
        requests.add(new YammerApiRequest("api/v1/uploaded_files/{file_id}/version/"+file_version_id+"/thumbnail", HttpMethod.GET, new HttpEntity(headers), HttpStatus.OK, "file_thumbnail", file_id));
        return requests;
    }

    private void mapResult(HttpStatus httpStatus, HttpStatus expected, ModelMap map, String name) {
        if (httpStatus.equals(expected)) {
            map.put(name, "&#x2705;");
        } else {
            map.put(name, "&#x274C;");
        }
    }

}
