package com.smoke.web.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smoke.entity.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class VerifyCodeController {

	/**
	 * 获取验证码
	 * 
	 * @param session
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public void getVerifyCode(HttpSession session, HttpServletResponse response)
			throws IOException {
		VerifyCode verifyCode = new VerifyCode();
		VerifyCode.output(verifyCode.getImage(), response.getOutputStream());
		session.setAttribute("code", verifyCode.getText().toLowerCase());
	}



}
