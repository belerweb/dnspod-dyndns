package com.belerweb.dnspod.dyndns.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belerweb.dnspod.DNSPodAPI;
import com.belerweb.dnspod.DNSPodAPI.DNSPodAPIFactory;
import com.belerweb.dnspod.RecordLine;
import com.belerweb.dnspod.RecordType;
import com.belerweb.dnspod.result.ModifyRecordResult;

@Controller
@RequestMapping("/nic")
public class DynDNSAPI {

  private static final Logger LOGGER = LoggerFactory.getLogger(DynDNSAPI.class);

  private static String ip;

  @RequestMapping("/update")
  public void update(@RequestHeader String authorization, @RequestParam String myip, Model model) {
    String resultText = "googd";
    if (!authorization.equals(System.getenv("DNSPod.DynDNS.authorization"))) {
      resultText = "badauth";
    } else {
      if (!myip.equals(ip)) {
        try {
          DNSPodAPI api = DNSPodAPIFactory.create();
          int domianId = Integer.parseInt(System.getenv("DNSPod.DynDNS.domianId"));
          int recordId = Integer.parseInt(System.getenv("DNSPod.DynDNS.recordId"));
          String subDomain = System.getenv("DNSPod.DynDNS.subDomain");
          ModifyRecordResult result =
              api.modifyRecord(domianId, recordId, subDomain, RecordType.A, RecordLine.DEFAULT,
                  myip, 1, 600);
          ip = myip;
          if (!result.isSuccess()) {
            LOGGER.error(result.getStatus().getMessage());
            resultText = "dnserr";
          }
          LOGGER.info(result.getStatus().getMessage());
          resultText = "good";
        } catch (Exception e) {
          resultText = "911";
        }
      }
    }

    model.addAttribute("result", resultText);
  }

}
