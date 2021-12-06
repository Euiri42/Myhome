package com.youngeun.myhome.controller;

import com.youngeun.myhome.model.Board;
import com.youngeun.myhome.model.User;
import com.youngeun.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {
    @Autowired
    private UserRepository repository;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<User> all(){
        return repository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    //added part
    @GetMapping("/consent")
    String consent(Model model){
        List<User> consent = all();
        model.addAttribute("consent",consent);
        return "contact";
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(User -> {
//                    User.setName(newUser.getName());
//                    User.setTitle(newUser.getTitle());
//                    User.setContent(newUser.getContent());
//                    User.setBoards(newUser.getBoards());
                    User.getBoards().clear();
                    User.getBoards().addAll(newUser.getBoards());
                    for(Board board : User.getBoards()){
                        board.setUser(User);
                    }
                    return repository.save(User);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
