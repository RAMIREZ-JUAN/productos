/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.emergentes.controlador;

import com.emergentes.modelo.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RAMIREZ
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses=request.getSession();
        int id,pos;
        
        if(ses.getAttribute("listaper")==null){
        ArrayList<Persona>listaux=new ArrayList<Persona>();
        ses.setAttribute("listaper", listaux);
        }
        ArrayList<Persona>lista=(ArrayList<Persona>)ses.getAttribute("listaper");
        
        String op=request.getParameter("op");
        String opcion=(op!=null)? op:"view";
        Persona obj1=new Persona();
        
        switch(opcion){
            case"nuevo":
                request.setAttribute("miPersona",obj1);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
                
            case "editar":
                id=Integer.parseInt(request.getParameter("id"));
                pos=buscarIndice(request,id);
                obj1=lista.get(pos);
                request.setAttribute("miPersona", obj1);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "eliminar":
                id=Integer.parseInt(request.getParameter("id"));
                pos=buscarIndice(request,id);
                lista.remove(pos);
                response.sendRedirect("index.jsp");
                break;
            case "view":
                //ses.setAttribute("listaper", lista);
                response.sendRedirect("index.jsp");
                break;
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses=request.getSession();
        ArrayList<Persona>lista=(ArrayList<Persona>)ses.getAttribute("listaper");
        Persona obj1=new Persona();
        int idt;
        
        obj1.setId(Integer.parseInt(request.getParameter("id")));
        obj1.setDescripcion(request.getParameter("descripcion"));
        obj1.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
        obj1.setPrecio(Double.parseDouble(request.getParameter("precio")));
        
        idt=obj1.getId();
        if(idt==0){
            obj1.setId(ultimoId(request)); 
            lista.add(obj1);
        }
        else{
            lista.set(buscarIndice(request,idt), obj1);
        }
        
        response.sendRedirect("index.jsp");
    }
        
    private int ultimoId(HttpServletRequest request){
        HttpSession ses=request.getSession();
        ArrayList<Persona>lista=(ArrayList<Persona>)ses.getAttribute("listaper");
        
        int idaux=0;
        for(Persona item:lista){
            idaux=item.getId();
        }
        return idaux+1;    
    }
    private int buscarIndice(HttpServletRequest request,int id){
        HttpSession ses=request.getSession();
        ArrayList<Persona>lista=(ArrayList<Persona>)ses.getAttribute("listaper");
        
        int i=0;
        if(lista.size()>0){
                while(i<lista.size()){
                    if(lista.get(i).getId()==id){
                        break;
                    }
                    else{
                        i++;
                    }
                }
        }
        return i;
        }

} 
    
                   

