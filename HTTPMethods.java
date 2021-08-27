import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpRequest;

public class HTTPMethods {
    private static final Gson gson = new Gson();

    public static HttpRequest createUser(User user) {
        return HttpRequest.newBuilder()
                .header("Content-type", "application/json; charset=UTF-8")
                .uri(URI.create(Paths.getURL() + Paths.getUsers ()))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                .build();
    }

    public static HttpRequest updateUsers(String id, User user) {
        return HttpRequest.newBuilder()
                .header("Content-type", "application/json; charset=UTF-8")
                .uri(URI.create(Paths.getURL() + Paths.getUsers () + "/" + id))
                .method("PUT", HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                .build();
    }

    public static HttpRequest deleteUser(String id, User user) {
        return HttpRequest.newBuilder()
                .header("Content-type", "application/json; charset=UTF-8")
                .uri(URI.create(Paths.getURL() + Paths.getUsers () + "/" + id))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                .build();
    }

    public static HttpRequest getUsersInfo() {
        return HttpRequest.newBuilder()
                .uri(URI.create(Paths.getURL() + Paths.getUsers ()))
                .GET()
                .build();
    }

    public static HttpRequest getUserInfoById(String id) {
        return HttpRequest.newBuilder()
                .uri(URI.create(Paths.getURL() + Paths.getUsers () + "/" + id))
                .GET()
                .build();
    }

    public static HttpRequest getUserInfoByName(String param, String value) {
        return HttpRequest.newBuilder()
                .header("Content-type", "application/json; charset=UTF-8")
                .uri(URI.create(String.format("%s%s?%s=%s",
                        Paths.getURL(),
                        Paths.getUsers (),
                        param,
                        value.replace(" ", "%20"))))
                .build();
    }

    public static HttpRequest getUserPosts(int id) {
        return HttpRequest.newBuilder()
                .uri(URI.create(String.format(
                        Paths.getURL() + Paths.getUsers () + "/" + id + "/" + "posts")))
                .GET()
                .build();
    }

    public static HttpRequest getUserComments(Post post) {
        return HttpRequest.newBuilder()
                .uri(URI.create(
                        Paths.getURL() + Paths.getPosts () + "/" + post.getId() + Paths.getComments ()
                ))
                .GET()
                .build();
    }

    public static HttpRequest getUserTasks(int id) {

        return HttpRequest.newBuilder()
                .uri(URI.create(
                        Paths.getURL() + Paths.getUsers () + "/" + id + Paths.getTodos ()
                ))
                .GET()
                .build();
    }
}