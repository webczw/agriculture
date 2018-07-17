package com.hjd.power.agriculture.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${agriculture.control.url}", name = "light")
public interface IFeignService {
	@RequestMapping(value = "/controlLightHouse", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String connect();

	@RequestMapping(value = "/controlLightHouse", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String updateLightHouse(@RequestParam("site_code") String site_code,
			@RequestParam("on_off_flag") Integer on_off_flag, @RequestParam("delay") Integer delay,
			@RequestParam("boot_date_delay") Integer boot_date_delay);
}
