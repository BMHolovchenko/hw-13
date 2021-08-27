import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HTTPMethodsExecutor {

    Gson gson = new Gson();

    HttpClient httpClient = HttpClient.newBuilder().build();
    User user = new User("Bohdan", "BMHolovchenko", "BMHolovchenko@gmail.com", "Kyiv",
            "123 123");

    public static void main(String[] args) {
        HTTPMethodsExecutor execute = new HTTPMethodsExecutor ();
        try {
            execute.createUser ();
            execute.updateUsers ();
            execute.deleteUser ();
            execute.allUsersInfo ();
            execute.userInfoById ();
            execute.userInfoByName();
            execute.userPosts();
            execute.userTasks ();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createUser () throws IOException, InterruptedException {
        HttpResponse<String> create = httpClient.send(HTTPMethods.createUser (user),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("User: \n" + create.body() + "\n created.");
    }

    public void updateUsers () throws IOException, InterruptedException {

        user.setName("Name");
        user.setUsername("Username");
        user.setAddress ("Address");
        user.setEmail ("Email");

        HttpResponse<String> update = httpClient.send(HTTPMethods.updateUsers ("5", user),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("User: \n" + update.body() + "\n was updated.");
    }

    public void deleteUser () throws IOException, InterruptedException {

        HttpResponse<String> delete = httpClient.send(HTTPMethods.deleteUser ("10", user),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("User: \n" + delete.statusCode () + "\n was deleted.");
    }

    public void allUsersInfo () throws IOException, InterruptedException {

        HttpResponse<String> info = httpClient.send(HTTPMethods.getUsersInfo (),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("Information about users: \n" + info.body() + "\n");
    }

    public void userInfoById() throws IOException, InterruptedException {
        HttpResponse<String> userInfoById = httpClient.send(HTTPMethods.getUserInfoById ("5"),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("Information about ID 5 " + userInfoById.body());
    }

    public void userInfoByName() throws IOException, InterruptedException {
        HttpResponse<String> getUserByName = httpClient.send (
                HTTPMethods.getUserInfoByName ("name","Kurtis Weissnat"),
                HttpResponse.BodyHandlers.ofString()
        );
        System.out.println("User information by input name: " + getUserByName.body());
    }

    public void userPosts() throws IOException, InterruptedException {
        int userId = 9;
        HttpResponse<String> getUsersPosts = httpClient.send(HTTPMethods.getUserPosts (userId),
                HttpResponse.BodyHandlers.ofString());
        List<Post> posts = gson.fromJson(getUsersPosts.body(), new TypeToken<List<Post>>() {
        }.getType());
        Post maxPost = Collections.max(posts, Comparator.comparingInt(Post::getId));
        String fileName = String.format("user-%s-post-%s-comments.json", userId, maxPost.getId());
        httpClient.send(HTTPMethods.getUserComments (maxPost),
                HttpResponse.BodyHandlers.ofFile(Path.of("src/" + fileName)));
    }

    public void userTasks() throws IOException, InterruptedException {
        int id = 3;
        HttpResponse<String> tasks = httpClient.send(HTTPMethods.getUserTasks (id),
                HttpResponse.BodyHandlers.ofString());
        List<Task> userTasks = gson.fromJson(tasks.body(), new TypeToken<List<Task>>() {
        }.getType());
        System.out.printf("Open tasks for user ID = %d: \n", id);
        System.out.println(userTasks.stream().filter(task -> !Task.isCompleted())
                .collect(Collectors.toList()));
    }
}