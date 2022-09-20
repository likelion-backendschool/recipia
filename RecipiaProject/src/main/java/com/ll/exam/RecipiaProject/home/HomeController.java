package com.ll.exam.RecipiaProject.home;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class HomeController {
    @GetMapping("")
    public String home() {
        return "test";

    }

    @PostMapping("/")
    public String  home2(@RequestParam String searchInput) throws UnsupportedEncodingException {
        List<String> searchKeyword=new ArrayList<>();
        String searchQuery="";
        try {
            JSONParser jsonParser=new JSONParser();
            JSONArray jsonArray=(JSONArray) jsonParser.parse(searchInput);

           for(Object o:jsonArray){
               JSONObject jo=(JSONObject)o;
               searchKeyword.add(String.valueOf(jo.get("value")));
           }
        } catch (ParseException e) {
            return  "redirect:/posts/list";
        }
        for(int i=0;i<searchKeyword.size();i++){
            if(i==0){
                searchQuery+="query="+searchKeyword.get(i);
            }else{
                searchQuery+="&"+searchKeyword.get(i);
            }
        }

        return  "redirect:/posts/list?"+URLEncoder.encode(searchQuery,"UTF-8");
    }
}