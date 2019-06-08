## What we will do:
- Create TodoController and list-todos.jsp
- Make TodoService a @Service and inject it

## Pending for Next Step
- ${name} is not available in list-todos.jsp
- in28Minutes is hardcoded in TodoController

## Snippets

Snippet -  /src/main/java/com/in28minutes/springboot/web/model/Todo.java
```
package com.in28minutes.springboot.web.model;

import java.util.Date;

public class Todo {
    private int id;
    private String user;
    private String desc;
    private Date targetDate;
    private boolean isDone;

    public Todo(int id, String user, String desc, Date targetDate,
            boolean isDone) {
        super();
        this.id = id;
        this.user = user;
        this.desc = desc;
        this.targetDate = targetDate;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Todo other = (Todo) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "Todo [id=%s, user=%s, desc=%s, targetDate=%s, isDone=%s]", id,
                user, desc, targetDate, isDone);
    }

}
```

Snippet - /src/main/java/com/in28minutes/springboot/web/service/TodoService.java
```
package com.in28minutes.springboot.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.model.Todo;

@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<Todo>();
    private static int todoCount = 3;

    static {
        todos.add(new Todo(1, "in28Minutes", "Learn Spring MVC", new Date(),
                false));
        todos.add(new Todo(2, "in28Minutes", "Learn Struts", new Date(), false));
        todos.add(new Todo(3, "in28Minutes", "Learn Hibernate", new Date(),
                false));
    }

    public List<Todo> retrieveTodos(String user) {
        List<Todo> filteredTodos = new ArrayList<Todo>();
        for (Todo todo : todos) {
            if (todo.getUser().equals(user)) {
                filteredTodos.add(todo);
            }
        }
        return filteredTodos;
    }

    public void addTodo(String name, String desc, Date targetDate,
            boolean isDone) {
        todos.add(new Todo(++todoCount, name, desc, targetDate, isDone));
    }

    public void deleteTodo(int id) {
        Iterator<Todo> iterator = todos.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getId() == id) {
                iterator.remove();
            }
        }
    }
}
```


## Files List

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.in28minutes.springboot.web</groupId>
	<artifactId>spring-boot-first-web-application</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-first-web-application</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.4.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		
		<dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <scope>provided</scope>
        </dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```
---
### src/main/java/org/dev/tech/controller/LoginController.java

```java
package org.dev.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.dev.tech.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	LoginService service;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model){
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password){
		boolean isValidUser = service.validateUser(name, password);
		
		if (!isValidUser) {
			model.put("errorMessage", "Invalid Credentials");
			return "login";
		}
		
		model.put("name", name);
		model.put("password", password);
		
		return "welcome";
	}

}
```
---
### src/main/java/org/dev/tech/controller.TodoController

```java
package org.dev.tech.controller;

import org.dev.tech.service.LoginService;
import org.dev.tech.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@RestController
@Controller
public class TodoController {

	@Autowired
	TodoService  service;
	
	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodoList(ModelMap model){
		model.put("todos", service.retrieveTodos("devtechsolution"));
		return "list-todos";
	}
	
}

```
---

### src/main/java/org/dev/tech/service/LoginService.java

```java
package org.dev.tech.service;

import org.springframework.stereotype.Component;

@Component
public class LoginService {

	public boolean validateUser(String userid, String password) {
		// aditya, srivastva
		return userid.equalsIgnoreCase("aditya")
				&& password.equalsIgnoreCase("srivastva");
	}

}
```
---

### src/main/java/org/dev/tech/service/TodoService.java

```
package org.dev.tech.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dev.tech.model.Todo;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<Todo>();
    private static int todoCount = 3;

    static {
        todos.add(new Todo(1, "devtechsolution", "Learn Spring MVC", new Date(),
                false));
        todos.add(new Todo(2, "devtechsolution", "Learn Spring boot", new Date(), false));
        todos.add(new Todo(3, "devtechsolution", "Learn Hibernate", new Date(),
                false));
    }

    public List<Todo> retrieveTodos(String user) {
        List<Todo> filteredTodos = new ArrayList<Todo>();
        for (Todo todo : todos) {
            if (todo.getUser().equals(user)) {
                filteredTodos.add(todo);
            }
        }
        return filteredTodos;
    }

    public void addTodo(String name, String desc, Date targetDate,
            boolean isDone) {
        todos.add(new Todo(++todoCount, name, desc, targetDate, isDone));
    }

    public void deleteTodo(int id) {
        Iterator<Todo> iterator = todos.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getId() == id) {
                iterator.remove();
            }
        }
    }
}
```
---
### src/main/java/org/dev/tech/springwebapp/SpringBootFirstWebApplication.java

```java
package org.dev.tech.springwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootFirstWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFirstWebApplication.class, args);
	}
}
```
---
### src/main/resources/application.properties

```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
logging.level.org.springframework.web=DEBUG
```
---
### src/main/webapp/WEB-INF/jsp/login.jsp

```
<html>

<head>
<title>First Web Application</title>
</head>

<body>
	<font color="red">${errorMessage}</font>
	<form method="post">
		Name : <input type="text" name="name" /> 
		Password : <input type="password" name="password" /> 
		<input type="submit" />
	</form>
</body>

</html>
```
---
### src/main/webapp/WEB-INF/jsp/welcome.jsp

```
<html>

<head>
<title>First Web Application</title>
</head>

<body>Welcome ${name}!! <a href="/list-todos">Click Here</a> to manage your todo's
</body>

</html>
```
---
### src/main/webapp/WEB-INF/jsp/list-todos.jsp

```
<html>

<head>
<title>First Web Application</title>
</head>

<body>
	Here are list of todos:
	${todos}
	
</body>

</html>
```
---

### src/test/java/org/dev/tech/springwebapp/SpringBootFirstWebApplicationTests.java

```java
package org.dev.tech.springwebapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootFirstWebApplicationTests {

	@Test
	public void contextLoads() {
	}

}
```
---
### todo.txt

```
Component, Service, Repository, Controller
Autowired
ComponentScan


Spring Boot Starter Parent
Spring Boot Starter Web
@SpringBootApplication
Auto Configuration

Dispatcher Servlet

/login => "login"

"login" => src/main/webapp/WEB-INF/jsp/login.jsp 


Search for a view named "login"



/login => LoginController 
```
---
