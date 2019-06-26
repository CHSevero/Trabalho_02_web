package com.ufc.br.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Item;
import com.ufc.br.service.ItemService;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sacola")
public class SacolaController {

   @Autowired
   private ItemService itemService;
   

   @RequestMapping(value = "index", method = RequestMethod.GET)
   public String index() {
       return "Sacola";
   }

   @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
   public ModelAndView add(@PathVariable("id") long codigo, HttpSession session) {

       ModelAndView mv = new ModelAndView("redirect:/sacola/index");

       Double total = 0.0;

       if(session.getAttribute("sacola") == null) {

           List<Item> sacola = new ArrayList<Item>();
           Item item = itemService.criarItem(codigo);

           Double precoPrato = item.getPrato().getPreco();
           int qtd = item.getQuantidade();
           Double subtotal = precoPrato * qtd;

           item.setValor(subtotal);

           sacola.add(item);

           total = item.getValor();

           session.setAttribute("sacola", sacola);
           session.setAttribute("total", total);

       }else{

           List<Item> sacola = (List<Item>) session.getAttribute("sacola");

           int index = this.exists(codigo, sacola);

           if(index == -1) {

               Item item = itemService.criarItem(codigo);

               Double precoPrato = item.getPrato().getPreco();
               int qtd = item.getQuantidade();
               Double subtotal = precoPrato * qtd;

               item.setValor(subtotal);

               sacola.add(item);

           }else {

               int quantidade = sacola.get(index).getQuantidade() + 1;
               sacola.get(index).setQuantidade(quantidade);

               Double precoPrato = sacola.get(index).getPrato().getPreco();
               Double subtotal = precoPrato*quantidade;
               sacola.get(index).setValor(subtotal);

           }

           for(Item item : sacola) {
               total += item.getQuantidade() * item.getPrato().getPreco();
           }

           session.setAttribute("sacola", sacola);
           session.setAttribute("total", total);

       }

       return mv;

   }

   @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
   public String remove(@PathVariable("id") long codigo, HttpSession session) {

       Double total = 0.0;

       List<Item> sacola = (List<Item>) session.getAttribute("sacola");
       int index = this.exists(codigo, sacola);

       if(sacola.get(index).getQuantidade() > 1){
           int qtd = sacola.get(index).getQuantidade();
           sacola.get(index).setQuantidade(qtd-1);

       }else {
           sacola.remove(index);
       }

       for(Item item : sacola) {
           total += item.getQuantidade() * item.getPrato().getPreco();
       }

       session.setAttribute("sacola", sacola);
       session.setAttribute("total", total);
       return "redirect:/sacola/index";
   }

   private int exists(long codigo, List<Item> sacola) {

       for(int i = 0; i < sacola.size(); i++) {

           if(sacola.get(i).getPrato().getId() == codigo) {
               return i;
           }
       }

       return -1;

   }

}
