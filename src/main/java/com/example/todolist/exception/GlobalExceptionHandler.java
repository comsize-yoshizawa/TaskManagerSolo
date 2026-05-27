package com.example.todolist.exception;

import java.rmi.NoSuchObjectException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public String handlerLock(ObjectOptimisticLockingFailureException e,
            Model model){
		model.addAttribute("message", "対象データは既に削除または更新されています");
		return "error";
	}
	
	@ExceptionHandler(NoSuchObjectException.class)
	public String deleted(NoSuchObjectException e,
			Model model) {
		model.addAttribute("message", "対象データが見つかりませんでした。");
		return "error";
	}
	
	@ExceptionHandler(NullPointerException.class)
	public String dataNull(NullPointerException e,
			Model model) {
		model.addAttribute("message","対象データが見つかりませんでした。");
		return "error";
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public String accessDenied(AccessDeniedException e, Model model) {
		model.addAttribute("message", "アクセス権限がありません。");
		return "error";
	}
	
//	@ExceptionHandler(Exception.class)
//	public String allException(Exception e,Model model) {
//		model.addAttribute("message","エラーが発生しました。");
//		return "error";
//	}
}
