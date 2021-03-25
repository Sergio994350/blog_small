package com.tarasov.blog_small.controller;

import com.tarasov.blog_small.model.Post;
import com.tarasov.blog_small.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    // вывод всех постов блога
    @GetMapping("/blog")
    public String blogMain(Model model) {
        //передаем все посты в шаблон из БД
        Iterable<Post> posts = postRepository.findAll();

        // передаем в модель массив постов
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    // добавление нового поста - переход на страничку заполнения формы
    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    // добавление нового поста - получение данных из формы и запись в БД
    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    // обновление поста
    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model) {

        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);

        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {

        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

}
