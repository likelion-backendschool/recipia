package com.ll.exam.RecipiaProject.HomeAndSearch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class SearchController {
    @GetMapping("")
    public String home() {
        return "home";

    }

    @PostMapping("/")
    public String  search(@RequestParam String searchInput) throws UnsupportedEncodingException {
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
                searchQuery+="keyword="+ URLEncoder.encode(searchKeyword.get(i),"UTF-8");
            }else{
                searchQuery+=","+URLEncoder.encode(searchKeyword.get(i),"UTF-8");
            }
        }

        return  "redirect:/posts/list?"+searchQuery;
    }
}