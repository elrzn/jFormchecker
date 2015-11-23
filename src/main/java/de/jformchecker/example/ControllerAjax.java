package de.jformchecker.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.jformchecker.FormChecker;
import de.jformchecker.themes.TwoColumnBootstrapFormBuilder;

@WebServlet("/ajax")
public class ControllerAjax extends BaseController{
  private static final long serialVersionUID = 1L;


  /**
   * @see HttpServlet#HttpServlet()
   */
  public ControllerAjax() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    initRequest(request, response);

    FormChecker fc =
        FormChecker.build("id", request, new ExampleFormShort())
        .setFormBuilder(new TwoColumnBootstrapFormBuilder());
    fc.run();
        

    processResult(fc);



    putFcInTemplate(response, fc, "ajax.ftl");    
  }

}
