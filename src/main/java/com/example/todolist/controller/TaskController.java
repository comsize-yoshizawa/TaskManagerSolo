package com.example.todolist.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todolist.dto.CommentDto;
import com.example.todolist.dto.TaskDetailDto;
import com.example.todolist.dto.TaskListDto;
import com.example.todolist.form.TaskData;
import com.example.todolist.service.CategoryService;
import com.example.todolist.service.CommentService;
import com.example.todolist.service.StatusService;
import com.example.todolist.service.TaskService;
import com.example.todolist.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class TaskController {
	private final TaskService taskService;
	private final CategoryService categoryService;
	private final UserService userService;
	private final StatusService statusService;
	private final CommentService commentService;
	
	private void setFormMasterData(Model model) {
		model.addAttribute("categoryList", categoryService.getAllCategories());
		model.addAttribute("userList", userService.getAllUsers());
	    model.addAttribute("statusList", statusService.getAllStatus());
	    model.addAttribute("today", LocalDate.now());
	}
	
	private void setToken(Model model, HttpSession session) {
	    String token = UUID.randomUUID().toString();
	    session.setAttribute("token", token);
	    model.addAttribute("token", token);
	}
	
	private boolean isInvalidToken(String token, HttpSession session) {
	    String sessionToken = (String) session.getAttribute("token");
	    return sessionToken == null || !sessionToken.equals(token);
	}

	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}

	@GetMapping("/task/create")
	public String taskRegisterForm(Model model, HttpSession session) {

		setToken(model,session);
		model.addAttribute("mode", "create");
		model.addAttribute("taskData", new TaskData());
		setFormMasterData(model);

		return "taskForm";
	}

	@PostMapping("/task/create")
	public String taskRegister(@ModelAttribute @Validated TaskData taskData,
			BindingResult result, Model model, @RequestParam String token,
			HttpSession session, RedirectAttributes redirectAttributes) {

		if (isInvalidToken(token, session)) {
		    return "redirect:/menu";
		}

		if (!result.hasErrors()) {
			taskService.saveTask(taskData);
			if(taskData.getTaskId() == null) {
				redirectAttributes.addFlashAttribute("mode", "create");
			}else {
				redirectAttributes.addFlashAttribute("mode","update");
			}
			
			session.removeAttribute("token");
			return "redirect:/done";
		} else {
			setFormMasterData(model);
			return "taskForm";
		}
	}

	@GetMapping("/task/list")
	public String list(
			@RequestParam(defaultValue = "0") int page,
			Model model) {
		model.addAttribute("mode","list");
		Page<TaskListDto> taskListDto = taskService.getAllTasks(page);
		model.addAttribute("taskPage", taskListDto);

		return "taskList";
	}

	@GetMapping("/task/detail/{taskId}")
	public String detail(@PathVariable(name = "taskId") Integer taskId,
			Model model,HttpSession session) {
		TaskDetailDto taskDto = taskService.getTaskDetail(taskId);
		List<CommentDto> commentListDto = commentService.getCommentList(taskId);
		model.addAttribute("mode", "detail");
		setToken(model,session);
		model.addAttribute("task", taskDto);
		model.addAttribute("commentList", commentListDto);
		return "taskDetail";
	}

	@GetMapping("/task/alter/{taskId}")
	public String alterForm(@PathVariable(name = "taskId") Integer taskId,
			Model model, HttpSession session) {
		TaskData taskData = taskService.toForm(taskId);

		setToken(model,session);
		model.addAttribute("mode", "alter");
		setFormMasterData(model);
		model.addAttribute("taskData", taskData);
		return "taskForm";
	}

	@GetMapping("/done")
	public String done(Model model) {
		model.addAttribute("taskData", new TaskData());
		return "done";
	}

	@GetMapping("/task/deleteConfirm/{taskId}")
	public String taskDeleteConfirm(@PathVariable(name = "taskId") Integer taskId, 
			Model model,HttpSession session,
			@AuthenticationPrincipal UserDetails userDetails) {
		TaskDetailDto taskDto = taskService.getTaskDetail(taskId);
		if(!taskDto.getUserId() .equals(userDetails.getUsername())) {
			return "redirect:/task/detail/" + taskId;
		}
		setToken(model,session);
		model.addAttribute("mode", "delete");
		model.addAttribute("task", taskDto);
		return "deleteConfirm";
	}

	@PostMapping("/task/delete/{taskId}")
	public String taskDelete(@PathVariable(name = "taskId") Integer taskId,
			RedirectAttributes redirectAttributes,
			@RequestParam String token,
			HttpSession session) {
		if (isInvalidToken(token, session)) {
		    return "redirect:/menu";
		}
		taskService.deleteTask(taskId);
		session.removeAttribute("token");
		redirectAttributes.addFlashAttribute("mode", "delete");
		return "redirect:/done";
	}

//ajaxでコメント送信をするようにしたため過去の遺産
//	@PostMapping("/task/commentPost/{taskId}")
//	public String commentPost(@PathVariable(name = "taskId") Integer taskId,
//			Model model, HttpSession session,
//			@RequestParam("comment") String comment,
//			@RequestParam("token") String token,
//			@AuthenticationPrincipal UserDetails userDetails) {
//		String sessionToken = (String) session.getAttribute("token");
//
//		if (sessionToken == null || !sessionToken.equals(token)) {
//			return "redirect:/menu";
//		}
//
//		commentService.postComment(taskId, userDetails.getUsername(), comment);
//		session.removeAttribute("token");
//		return "redirect:/task/detail/{taskId}";
//	}

		@PostMapping("/task/commentPost/{taskId}")
		public String commentPost(@PathVariable(name = "taskId")Integer taskId,
				Model model,HttpSession session,
				@RequestParam("comment") String comment,
				@RequestParam("token") String token,
				@AuthenticationPrincipal UserDetails userDetails) {
			if (isInvalidToken(token, session)) {
			    return "redirect:/menu";
			}
			
			List<CommentDto> commentList = commentService.postComment(taskId, userDetails.getUsername(), comment);
			model.addAttribute("commentList", commentList);
			
			return "taskDetail :: commentArea";
		}

	@GetMapping("/task/commentDelete/confirm/{commentId}")
	public String commentDeleteConfirm(@PathVariable("commentId") Integer commentId,
			Model model, HttpSession session) {

		CommentDto commentDto = commentService.getComment(commentId);
		TaskDetailDto taskDto = taskService.getTaskDetail(commentDto.getTaskId());
		setToken(model,session);
		model.addAttribute("mode", "commentDelete");
		model.addAttribute("comment", commentDto);
		model.addAttribute("task", taskDto);

		return "commentDeleteConfirm";
	}

	@PostMapping("/task/commentDelete/{commentId}")
	public String commentDelete(@PathVariable(name = "commentId") Integer commentId,
			HttpSession session,
			@RequestParam("token") String token,
			@RequestParam("taskId") int taskId,
			@AuthenticationPrincipal UserDetails userDetails) {

		if (isInvalidToken(token, session)) {
		    return "redirect:/menu";
		}
		commentService.deleteComment(commentId, userDetails.getUsername());
		session.removeAttribute("token");

		return "redirect:/task/detail/" + taskId;
	}
}
