package com.qzlnode.epidemic.miniprogram.controller;

import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qzlzzz
 */
@RestController
public class MainController {

    private Logger logger = LoggerFactory.getLogger(getClass());//日志

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Province>> getAllData(){
        return new ResponseEntity<>(mainService.getAllData(), HttpStatus.ACCEPTED);
    }

//    public ResponseEntity<Province> getProvinceData(@RequestParam("provinceId") Integer provinceId){
//
//    }


}
