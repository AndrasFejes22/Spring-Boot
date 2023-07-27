package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.model.UserContext;
import com.example.demo.model.exception.NotFoundException;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

//localhost:8080/SPRING-Web/hello
//localhost:8080/SPRING-Web/users

//!!! ezek máshogy néznek ki aszerint hogy itt a UserController-ban hogy néz ki a get metódus!
//localhost:8080/user?id=10
//localhost:8080/post?id=10
//http://localhost:8080/user/10
//http://localhost:8080/users?userId=10 --> ez csak beállítja a current usert

@Controller
public class UserController {

    private final UserService userService;
    private final UserContext userContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, UserContext userContext) {
        this.userService = userService;
        this.userContext = userContext;
    }

    // get all users:(session stuff)
    @GetMapping("/users")
    public String getAllUsers(Model model, @SessionAttribute(required = false) Long visitedUserId,
                              @SessionAttribute(required = false) User latestUser) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("highlighted", visitedUserId);
        LOGGER.info("Latest created user: {}", latestUser);
        LOGGER.info("User context: {}", userContext);
        LOGGER.info("Current user: {}", userContext.getCurrentUser());
        return "users";
    }

    // get user by ID:
    /*
    @RequestMapping("/user/{id}")
    //@RequestMapping("/user")
    public String getUserById(Model model, @PathVariable long id){
        User user = userService.getUserById(id).orElse(null);
        model.addAttribute("user", user);
        return "user";
    }

     */


    // get user by ID_3: (session stuff)
    @GetMapping("/user/{id}")
    public String getUserById(Model model, @PathVariable long id, HttpSession session) {
        //User user = userService.getUserById(id).orElse(null); // ha olyan ID ami nem létezik
        User user = userService.getUserById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        model.addAttribute("user", user);

        if (user != null) {
            session.setAttribute("visitedUserId", user.getId());
        }

        return "user";
    }

    private Cookie createVisitedUserIdCookie(Long userId){
        Cookie cookie = new Cookie("visitedUserId", userId.toString());
        cookie.setPath("/SPRING-Web/");
        //cookie.setDomain("localhost");
        return cookie;
    }



    @RequestMapping("/addUser")
    public String addUserPage(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "add_user";
    }

    //@RequestMapping(value = "/user", method = RequestMethod.POST) //ilyen is van
    @PostMapping("/user") //ez a legcelravezetobb
    public String addUser(CreateUserRequest request) {
        User createdUser = userService.createUser(request);
        return "redirect:/user/" + createdUser.getId();
    }

    //kvázi kivételkezelő metódus
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String handleNotFound(NotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/4xx";
    }





    /*
    @RequestMapping("/hello")
    public String getUser(@RequestParam(required = false) String name, Model model) { // igy az alap John Doe lesz
    //public String getUser(@RequestParam(required = true) String name, Model model) { //http://localhost:8080/SPRING-Web/hello?name=Andris**
        model.addAttribute("user", userService.getUser(name));
        //return "myView";
        return "hello.html"; //thymeleaf
    }
    */
    //** HTTP ERROR 400 Required parameter 'name' is not present. Igy a hello után muszáj neki nevet adni!
}
