package com.belerweb.dnspod.dyndns.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belerweb.dnspod.DNSPodAPI;
import com.belerweb.dnspod.DNSPodAPI.DNSPodAPIFactory;
import com.belerweb.dnspod.RecordLine;
import com.belerweb.dnspod.RecordType;
import com.belerweb.dnspod.result.ModifyRecordResult;

/**
 * API implement like members.dyndns.org, members.3322.org
 * 
 * @author jun
 * 
 */
@Controller
@RequestMapping("/nic")
public class DynDNSAPI {

  private static final Logger LOGGER = LoggerFactory.getLogger(DynDNSAPI.class);

  private static String ip;

  /**
   * Update DNS record.
   * 
   * @param authorization Basic authorization.
   * @param myip IP address.
   * @return
   */
  @RequestMapping("/update")
  public ResponseEntity<String> update(@RequestHeader String authorization,
      @RequestParam String myip) {
    if (!authorization.equals(System.getenv("DNSPod.DynDNS.authorization"))) {
      return new ResponseEntity<String>("badauth", HttpStatus.FORBIDDEN);
    }
    if (myip.equals(ip)) {
      LOGGER.info("IP {} no change, keep it and ignore.", ip);
    } else {
      try {
        DNSPodAPI api = DNSPodAPIFactory.create();
        int domianId = Integer.parseInt(System.getenv("DNSPod.DynDNS.domianId"));
        int recordId = Integer.parseInt(System.getenv("DNSPod.DynDNS.recordId"));
        String subDomain = System.getenv("DNSPod.DynDNS.subDomain");
        ModifyRecordResult result =
            api.modifyRecord(domianId, recordId, subDomain, RecordType.A, RecordLine.DEFAULT, myip,
                1, 600);
        if (!result.isSuccess()) {
          LOGGER.error(result.getStatus().getMessage());
          return new ResponseEntity<String>("dnserr", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ip = myip;
        LOGGER.info(result.getStatus().getMessage());
      } catch (Exception e) {
        return new ResponseEntity<String>("911", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    return new ResponseEntity<String>("good", HttpStatus.OK);
  }

}
