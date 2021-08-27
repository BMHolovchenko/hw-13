public class Paths {

    private static final String URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS = "/users";
    private static final String POSTS = "/posts";
    private static final String COMMENTS = "/comments";
    private static final String TODOS = "/todos";

    public static String getURL() {
        return URL;
    }
    public static String getUsers(){
        return USERS;
    }

    public static String getPosts() {
        return POSTS;
    }

    public static String getComments() {
        return COMMENTS;
    }

    public static String getTodos() {
        return TODOS;
    }
}