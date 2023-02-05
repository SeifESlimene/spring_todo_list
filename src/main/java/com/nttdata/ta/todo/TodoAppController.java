package com.nttdata.ta.todo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoAppController {

	@Autowired
	private TodoItemRepository repository;

	@GetMapping("/")
	public String index(Model model) {
		Iterable<TodoItem> todoList = repository.findAll();
		model.addAttribute("items", new TodoListViewModel(todoList));
		model.addAttribute("newitem", new TodoItem());
		return "index";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute TodoItem requestItem) {
		TodoItem item = new TodoItem(requestItem.getCategory(), requestItem.getName());
		repository.save(item);
		return "redirect:/";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute TodoListViewModel requestItems) {
		for (TodoItem requestItem : requestItems.getTodoList()) {
			TodoItem item = new TodoItem(requestItem.getCategory(), requestItem.getName());
			item.setComplete(requestItem.isComplete());
			item.setId(requestItem.getId());
			repository.save(item);
		}
		return "redirect:/";
	}

	// @PostMapping("/delete")
	// public String delete(@RequestParam Map<String, String> allParams) {
	// for (Map.Entry<String, String> entry : allParams.entrySet()) {
	// if (entry.getKey().startsWith("_id")) {
	// long index = Integer.parseInt(entry.getKey().substring(3));
	// repository.deleteById(index);
	// }
	// }

	// return "redirect:/";
	// }

	@GetMapping("/delete")
	public String delete(@RequestParam Long id) {
		repository.deleteById(id);
		return "redirect:/";
	}

}