package br.com.vitor.todoapp.ToDoApp.model;

public enum Status {
	TODO ("TODO"),
	DONE ("DONE");
	
	public String getName() {
		return name;
	}

	private final String name;
	
	Status(String name){
		this.name = name;
	}
}
