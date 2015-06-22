package com.kissair.commands;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import com.kissair.dao.DAOFactory;
import com.kissair.dao.EmployeeDAO;
import com.kissair.model.Employee;
import com.kissair.util.Message;

/**
 * The SendEmployees Command. Sends messages with the available employees info
 * to the client.
 */
public class SendEmployees implements Command {

    @RolesAllowed("dispatcher")
    @Override
    public void execute(EndpointConfig endpointConfig, Session session, Message msg) {
	EmployeeDAO dao = DAOFactory.getEmployeeDAO();
	List<Employee> employees = dao.findAll();
	Message message = null;
	
	for (Employee e : employees) {
	    message = new Message(CommandName.SEND_EMPLOYEE, e);
	    session.getAsyncRemote().sendObject(message);
	}
    }
}
