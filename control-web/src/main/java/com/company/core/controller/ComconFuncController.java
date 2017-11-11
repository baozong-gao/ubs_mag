package com.company.core.controller;

import com.company.core.domain.SelectBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping (value = "/comcon")
public class ComconFuncController {
    
    @Autowired
    AgentService agentService;
    
    @RequestMapping(value = "/select_agent_active", method = RequestMethod.GET)
    public Object selectAgent(HttpServletRequest request, HttpServletResponse response) {
    
        SelectBO selectBO = new SelectBO();
        String instId = request.getParameter("instId");
        
        List<UcAgentDo> ucAgentDos = agentService.getAgentList();
        List<SelectBO> resp = new ArrayList<>();
        for(UcAgentDo ucAgentDo: ucAgentDos){
            if(!"Y".equals(ucAgentDo.getStatus())){
                continue;
            }
            selectBO = new SelectBO();
            selectBO.setLabel(ucAgentDo.getAgentName());
            selectBO.setValue(ucAgentDo.getAgentId());
            resp.add(selectBO);
        }
        return resp;
    }
    
    
    
}
